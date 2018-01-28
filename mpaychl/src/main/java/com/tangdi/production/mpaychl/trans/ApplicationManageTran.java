package com.tangdi.production.mpaychl.trans;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpaychl.base.service.TermService;
import com.tangdi.production.mpaychl.base.service.TranService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdcomm.channel.CallThrids;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;
import com.tangdi.production.tdcomm.util.Msg;

/**
 * [银行|第三方合作机构]签到(接受由管理台发过来的签到交易)
 * 
 * @author zhengqiang 2015/3/18
 * 
 */
@Component
@IProcess
public class ApplicationManageTran  {
	private static Logger log = LoggerFactory.getLogger(ApplicationManageTran.class);
	
	@Value("#{mpaychlConfig}")
	private Properties prop;

	/**
	 * 前置交易接口
	 */
	@Autowired
	private TranFrontService tranFrontservice;
	
	/**
	 * 合作机构签到 交易码 150001 交易参数为RtrId 路由ID channelname 渠道名
	 */
	@ICode(TranCode.TRAN_SIGN)
	public Map<String,Object> trans_150001(Map<String,Object> map) {
		
		log.info("第三方作机构签到   进入主控时上下文数据为{}", map.toString());
		try {
			// 1 校验参数
			log.info("1---->校验参数");
			/**
			 * RtrId 路由ID
			 * chanelname 合作机构编号
			 * terno 终端号
			 * merno 商户号
			 * rtrsvr  路由渠道名称
			 * rtrcod  路由渠道交易码
			 * lmk     合作机构终端主密钥
			 * 
			 */
			ParamValidate.doing(map, "RtrId","chanelname","terno","merno","rtrcod","rtrsvr","lmk");
		}catch (TranException e) {
			map.put(CallThrids.RETCOE, e.getMessage());
			log.error(e.getMessage(), e);
		}
		// 2 签到
		String chanelname = String.valueOf(map.get("chanelname"));
		Map<String,Object> signMap = new HashMap<String,Object>();
		try {
			log.info("开始签到");
			String serviceName = prop.getProperty("CORG_TRAN_" + chanelname);
			log.info("取得service=[{}]",serviceName);
			TranService service = SpringContext.getBean(serviceName,
					TranService.class);
			if (service == null) {
				log.info("{} 不存在",chanelname);
				signMap.put(CallThrids.RETCOE, "100002");
				return map ;
			}
			map.put("orgNo", chanelname);
			signMap = service.banksign(map);
			signMap.put(CallThrids.RETCOE, "000000");
			log.info("第三方合作机构签到交易成功 data={}", Msg.getdatas().toString());
		}  catch (Exception e) {
			signMap.put(CallThrids.RETCOE, "100002");
			log.error(e.getMessage(), e);
		}
		log.info("第三方合作机构签到   离开时上下文数据为{}", signMap.toString());
		return signMap;
	}
	//终端主密钥申请
	@ICode(TranCode.TRAN_TERMKEY)
	public Map<String,Object> trans_150002(Map<String,Object> map) {
		Map<String,Object> key = new HashMap<String,Object>();
		log.info("第三方作机构签到   进入主控时上下文数据为{}", map.toString());
		try {
			// 1 校验参数
			log.info("1---->校验参数");
			/**
			 * company  刷卡器厂商
			 * 
			 */
			ParamValidate.doing(map, "company");
		}catch (TranException e) {
			key.put(CallThrids.RETCOE, e.getMessage());
			log.error(e.getMessage(), e);
		}
		// 
		String type = String.valueOf(map.get("company"));
		try {
			log.info("开始执行..");
			String serviceName = prop.getProperty("TERM_" + type);
			log.info("取得service=[{}]",serviceName);
			TermService service = SpringContext.getBean(serviceName,
					TermService.class);
			if (service == null) {
				log.info("{} 不存在",type);
				key.put(CallThrids.RETCOE, "100002");
				return map ;
			}
			key = service.getLmkey(map);
			key.put(CallThrids.RETCOE, "000000");
			log.info("终端主密钥下载交易成功 data={}", Msg.getdatas().toString());
		}  catch (Exception e) {
			key.put(CallThrids.RETCOE, "100002");
			log.error(e.getMessage(), e);
		}
		log.info("终端主密钥下载交易   离开时上下文数据为{}", key.toString());
		return key;
	}
	

}
