package com.tangdi.production.mpaychl.front.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 短信前置接口
 * @author zhengqiang
 *
 */
public interface SmsFrontService {
	/**
	 * 短信实现厂商
	 */
	final static String SMS_SERVICE    = "SMS_MANUFACTURER";
	
	/**
	 * 发送短信接口
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> sendsms(Map<String,Object> param) throws TranException;
}
