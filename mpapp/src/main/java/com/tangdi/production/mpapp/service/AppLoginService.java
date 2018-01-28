package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;



/**
 * 支付测试
 * @author zhengqiang
 *
 */
public interface AppLoginService {
	
	/**
	 * app登录接口
	 * @param map {</br>custMobile 用户名(手机号) </br>custPwd 用户密码</br>}
	 * @return map{</br>
	 * 	custId 用户ID,</br>
	 *  custLogin 用户登陆账号</br>
	 * 	}
	 * @throws TranException
	 */
	public Map<String, Object> login(Map<String,Object> map) throws TranException;
	
	/**
	 * 记录登陆信息
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public int addLoginInf(Map<String,Object> map) throws TranException;
	
	


}
