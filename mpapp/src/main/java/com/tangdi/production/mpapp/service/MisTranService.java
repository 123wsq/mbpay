package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 机构通道交易接口[银行签到、终端签到、余额查询、消费] 、电子签名接口
 * @author zhengqiang
 *
 */
public interface MisTranService {
	

	/**
	 * 订单消费
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> payment(Map<String,Object> param) throws TranException;
	
	/**
	 * 订单消费撤销
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> dispayment(Map<String,Object> param) throws TranException;
	
	/**
	 * 通道方签到
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> banksign(Map<String,Object> param) throws TranException;
	
	/**
	 * 终端设备签到
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> termsign(Map<String,Object> param) throws TranException;
	
}
