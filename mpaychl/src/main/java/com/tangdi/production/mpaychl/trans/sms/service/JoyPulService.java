package com.tangdi.production.mpaychl.trans.sms.service;

import java.util.Map;

import com.tangdi.production.mpaychl.base.service.SMSService;
import com.tangdi.production.mpbase.exception.TranException;

/**
 * 乐搜短信接口
 * @author youdd 2015/11/17
 *
 */
public interface JoyPulService extends SMSService{
	
	/**
	 * 序列号注册
	 * @param param
	 * @return true 成功 false 失败
	 * @throws TranException
	 */
	public boolean regist(Map<String,Object> param) throws TranException;
}
