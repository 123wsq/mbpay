package com.tangdi.production.mpapp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.ValidateCodeDao;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.ValidateCodeService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 验证码服务接口实现类
 * @author zhuji
 *
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

	private static Logger log = LoggerFactory
			.getLogger(ValidateCodeServiceImpl.class);
	@Autowired
	private ValidateCodeDao validateCodeDao;
	@Autowired
	private MerchantService merchantService;
	
	/**
	 * 1.查询是否存在未被使用的验证码 （若存在执行步骤2）
	 * 2.修改验证码状态为过期
	 * 3.新增验证码数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCode(Map<String, Object> param) throws TranException {
		log.debug("写入验证码... 参数:{}",param);
		Map<String, Object> rspMap=null;

		log.info("1.查询验证码.");
		try {
			rspMap=validateCodeDao.selectEntity(param);
			log.info("验证码查询结果：{}",rspMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000412,"验证验证码异常",e);
		}
		
		log.info("2.修改验证码状态为过期.");
		if (rspMap!=null) {
			rspMap.put("codeStatus", MsgST.VALIDATE_CODE_STATUS_1);
			try {
				validateCodeDao.updateEntity(rspMap);
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001101,"验证码过期状态更新失败");
			}
		}
		
		log.info("3.新增验证码数据.");
		int rt=0;
		try {
			param.put("sendTime", TdExpBasicFunctions.GETDATETIME());
			param.put("msgCode", param.get("validateCode"));
			param.put("codeStatus", MsgST.VALIDATE_CODE_STATUS_0	);
			rt=validateCodeDao.insertEntity(param);
			log.debug("写入验证码完成:{}",param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000411,"验证码插入异常",e);
		}
		if (rt<=0) {
			throw new TranException(ExcepCode.EX000411,"获取验证码失败");
		}
		return rt;
	}
	
	@Override
	public boolean validate(Map<String, Object> param)
			throws TranException {
		boolean result=false;

		ParamValidate.doing(param, "custMobile","codeType","msgCode");

		
		Map<String, Object> rspMap=null;
		/**
		 * 1.查询验证码
		 */
		try {
			rspMap=validateCodeDao.selectEntity(param);
			log.info("验证码查询结果：{}",rspMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000412,"验证验证码异常",e);
		}
		
		/**
		 * 2.验证验证码：
		 * 1）验存 
		 * 2）验证时间（时间若不符合则进行步骤4）  
		 * 3）验证验证码是否符合
		 * 4）修改验证码状态
		 */
		//1）验存 
		if (rspMap==null||rspMap.size()<=0) {
			throw new TranException(ExcepCode.EX000412,"验证码验证失败");
		}		
		//2）验证时间（时间若不符合则进行步骤4）
		String send=rspMap.get("sendTime").toString();
		String last=TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(),"-","m","15");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date sendTime=null;
		Date lastTime=null;
		try {
			sendTime = sdf.parse(send);
			lastTime = sdf.parse(last);
		} catch (ParseException e1) {
			throw new TranException(ExcepCode.EX900001);
		}
		if (sendTime.before(lastTime)) {
			rspMap.put("codeStatus", MsgST.VALIDATE_CODE_STATUS_1);
			try {
				validateCodeDao.updateEntity(rspMap);

			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001106,"验证码状态修改异常",e);
			}
			throw new TranException(ExcepCode.EX001103,"验证码已过期");
		}
		//3）验证验证码是否符合
		if (!rspMap.get("msgCode").equals(param.get("msgCode"))) {
			throw new TranException(ExcepCode.EX001102,"验证不成功");
		}
		//4）修改验证码状态
		rspMap.put("codeStatus", MsgST.VALIDATE_CODE_STATUS_1);
		int rt=0;
		try {
			rt=validateCodeDao.updateEntity(rspMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001106,"验证码状态修改异常",e);
		}
		if (rt!=0) {
			result=true;
		}
		return result;
	}
}
