package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.LoginDao;
import com.tangdi.production.mpapp.service.AppLoginService;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.session.AppSession;
import com.tangdi.production.mpapp.session.Cust;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * app登录接口实现类
 * @author zhengqiang
 *
 */
@Service
public class AppLoginServiceImpl implements AppLoginService {
	private static Logger log = LoggerFactory
			.getLogger(AppLoginServiceImpl.class);
	@Value("#{mpappConfig}")
	private Properties prop;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private LoginDao loginDao ;
	

	@Override
	public Map<String, Object> login(Map<String, Object> map) throws TranException {
		Map<String, Object> target=null;
		Map<String, Object> validateMap=new HashMap<String, Object>();

		/**
		 * 登陆字段信息效验
		 * custMobile 用户名(手机号)
		 * custPwd    用户密码
		 */
		ParamValidate.doing(map, "custMobile","custPwd");

		//验证手机号是否存在
		validateMap.put("custMobile", map.get("custMobile"));

		target = merchantService.getMerchant(validateMap);
		if (target==null||target.size()<=0) {
			throw new TranException(ExcepCode.EX000206);
		}
		log.debug("target=[{}]",target);
		if (target.get("isLoginFlag").equals(MsgST.IS_LOGIN_NG)) {
			throw new TranException(ExcepCode.EX000101);
		}
		//验证是否满足冻结条件
		if (Integer.parseInt(target.get("custPwdNum").toString())>=5) {
			validateMap.put("isLoginFlag", MsgST.IS_LOGIN_NG);
			merchantService.updateLoginInfo(validateMap);
			throw new TranException(ExcepCode.EX000101);
		}
		/**
		 * 登录处理业务逻辑
		 */
		if (!target.get("custPwd").toString().equalsIgnoreCase(map.get("custPwd").toString())) {
			validateMap.put("custPwdNum", Integer.parseInt(target.get("custPwdNum").toString())+1);
			//更新登陆次数
			merchantService.updateLoginInfo(validateMap);
			throw new TranException(ExcepCode.EX000102);
		}

		Map<String,Object> pmap = new HashMap<String,Object>();
		String lastTime = TdExpBasicFunctions.GETDATETIME();
		pmap.put("lastOperTime",lastTime);
		pmap.put("custPwdNum","0");
		pmap.put("custId",target.get("custId"));
		//成功后更新登陆信息
		merchantService.updateLoginInfo(pmap);
		
		//登陆成功后写入session
		String timeout = prop.getProperty("APP_LOGIN_TIMEOUT");
		if(timeout == null || timeout.equals("")){
			timeout = "1800";
		}
		long logintime = Long.valueOf(TdExpBasicFunctions.GETDATETIME());
		AppSession.put(new Cust(String.valueOf(pmap.get("custId"))
				,Long.valueOf(timeout),logintime,String.valueOf(map.get("ip")),
				String.valueOf(map.get("sysType")),String.valueOf(map.get("sysTerNo"))));
		Map<String,Object> login = new HashMap<String,Object>();
		login.put("custId", pmap.get("custId"));
		login.put("ip", map.get("ip"));
		login.put("lastLoginDate", lastTime.substring(0, 8));
		login.put("lastLoginTime", lastTime.substring(8));
		login.put("sysType", map.get("sysType"));
		login.put("mac", map.get("sysTerNo"));
		login.put("logintime", logintime);
		addLoginInf(login);
		
	    Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("custId", target.get("custId"));
		returnMap.put("custLogin", target.get("custLogin"));
		returnMap.put("custName", target.get("custName"));
		returnMap.put("authStatus", target.get("authStatus"));
		return returnMap;
	}


	@Override
	public int addLoginInf(Map<String, Object> map) throws TranException {
		log.info("记录APP登陆信息.");
		int rt = 0;
		try {
			rt = loginDao.insertEntity(map);
		} catch (Exception e) {
			log.error("记录APP登陆信息异常.");
		}
		return rt;
	}


}
