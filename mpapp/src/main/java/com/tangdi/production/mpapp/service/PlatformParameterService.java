package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 *	费率获取接口
 * @author zhuji
 *
 */
public interface PlatformParameterService {
	
	/**
	 * 获取费率
	 * @param param rateCode 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getRate(Map<String , Object> param) throws TranException;

	/**
	 * 获取提现时间
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getCasTime(Map<String , Object> param) throws TranException;

	/**
	 * 获取通道选择参数
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getTunnel(Map<String, Object> param)throws TranException;
}
