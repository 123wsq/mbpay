package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 短信基类接口
 * @author zhengqiang 2014/04/15
 *
 */
public interface SMSService {
	
	/**
	 * 发送短信接口
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> sendsms(Map<String,Object> param) throws TranException;
	
	/**
	 * 获取上行短信
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> getmo(Map<String,Object> param) throws TranException;
	/**
	 * 查询余额
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> querybalance(Map<String,Object> param) throws TranException;
	
	

}
