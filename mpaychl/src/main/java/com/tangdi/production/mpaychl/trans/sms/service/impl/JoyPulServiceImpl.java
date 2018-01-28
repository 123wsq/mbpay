package com.tangdi.production.mpaychl.trans.sms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.trans.sms.service.JoyPulService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.TranMap;
import com.tangdi.production.tdcomm.channel.CallThrids;

/**
 * 乐搜短信接口实现类
 * @author youdd 2015/11/17
 *
 */
@Service
public class JoyPulServiceImpl implements JoyPulService {
	private static Logger log = LoggerFactory.getLogger(JoyPulServiceImpl.class);

	/**
	 * 短信通道返回正常结果00
	 */
	private final static String SMS_OK = "00";
	
	@Override
	public Map<String, Object> sendsms(Map<String, Object> param)
			throws TranException {
		//定义返回结果
		Map<String,Object> result = null; 
		Map<String,Object> dataPack = new HashMap<String,Object>();

	
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("dataPack", dataPack);
		dataPack.put("phone", param.get("custMobile"));
		dataPack.put("message", param.get("smsdata"));
		dataPack.put("seqid", param.get("seq"));
		dataPack.put("smspriority", "1");
		Map<String,Object> rmap = CallThrids.CallThirdOther("sendsms", 
				"STHDJP", dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		if(MsgSubST.RETCODE_OK.equals(rmap.get("RETCODE").toString())&&SMS_OK.equals(rmap.get("rsp").toString())){
			result = new HashMap<String,Object>();
			//交易成功后处理
			//设置处理结果到result中
			result.put("rsp", rmap.get("rsp"));
		}else if(tranMap.get("RETCODE").equals(MsgSubST.RETCODE_TIMEOUT)){
			throw new TranException(ExcepCode.EX100302,"短信验证码获取超时!");
		}else{
			throw new TranException(ExcepCode.EX100301,"短信验证码获取异常!");
		}
		log.debug("处理结果:{}",result);
		return result;
	}

	@Override
	public Map<String, Object> getmo(Map<String, Object> param)
			throws TranException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> querybalance(Map<String, Object> param)
			throws TranException {	
		//定义返回结果
		Map<String,Object> result = null; 
		//组包
		Map<String,Object> dataPack = new HashMap<String,Object>();

		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("dataPack", dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther("querybalance", 
						"STHDYM", dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		if(tranMap.get("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				SMS_OK.equals(tranMap.get("error").toString())){
			result = new HashMap<String,Object>();
			//交易成功后处理
			//设置处理结果到result中
			result.put("error",   rmap.get("error"));
			result.put("message", rmap.get("message"));
		}else if(tranMap.get("RETCODE").equals(MsgSubST.RETCODE_TIMEOUT)){
			throw new TranException(ExcepCode.EX100302,"查询短信账户余额超时!");
		}else{
			throw new TranException(ExcepCode.EX100301,"查询短信账户余额异常!");
		}
		log.debug("处理结果:{}",result);
		return result;
	}

	@Override
	public boolean regist(Map<String, Object> param) throws TranException {
		//组包
		Map<String,Object> dataPack = new HashMap<String,Object>();

		Map<String,Object> data = new HashMap<String,Object>();
		data.put("dataPack", dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther("regist", 
				"STHDYM", dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		if(tranMap.get("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				SMS_OK.equals(tranMap.get("error").toString())){
			//交易成功后处理
			//设置处理结果到result中
			
		}else if(tranMap.get("RETCODE").equals(MsgSubST.RETCODE_TIMEOUT)){
			throw new TranException(ExcepCode.EX100302,"短信通道注册超时!");
		}else{
			throw new TranException(ExcepCode.EX100301,"短信通道注册异常!");
		}
		return false;
	}

}
