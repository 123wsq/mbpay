package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 费率服务接口
 * 
 * @author chenlibo
 *
 */
public interface RatesInfService {
	/**
	 * 获取费率
	 * 
	 * @param pmap
	 *            rateCode 费率代码
	 *            rateId   费率ID
	 *            rateType 业务类型 02收单 03 快捷 04 扫码
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getRate(Map<String, Object> pmap) throws TranException;
	
	

}
