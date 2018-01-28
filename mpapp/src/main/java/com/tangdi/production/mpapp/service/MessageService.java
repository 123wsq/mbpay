package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
*
* 短信业务接口
*
* @author zhengqiang 2015/04/16
* @version 1.0
*/
public interface MessageService {
	
	
	/**
	 * 发送短信方法
	 * @param param {mobile 手机号, smsType 短信类型 01 注册 02找回密码 03消费}
	 * @throws TranException
	 */
	public String sendsms(Map<String,Object> param) throws TranException;

}
