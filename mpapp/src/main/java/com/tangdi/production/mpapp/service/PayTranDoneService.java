package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 支付交易完成后业务处理
 * @author zhengqiang
 *
 */
public interface PayTranDoneService {
	/**
	 * 支付完成后，相关业务更新处理。
	 * @param map
	 * @throws TranException
	 */
	public void handler(Map<String,Map<String,Object>> pmap) throws TranException;
	
	/**
	 * 支付撤销完成后，相关业务更新处理。
	 * @param map
	 * @throws TranException
	 */
	public void cancelledHandler(Map<String,Map<String,Object>> pmap) throws TranException;

}
