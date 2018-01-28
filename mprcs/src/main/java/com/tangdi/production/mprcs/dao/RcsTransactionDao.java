package com.tangdi.production.mprcs.dao;

import java.util.Map;


public interface RcsTransactionDao {
	
	
	/**
	 * 风控使用, 交易汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> transactionAmtTotal(Map<String,Object> map) throws Exception;
	
	/**
	 * 风控使用, 提现汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object> transactionCasTotal(Map<String,Object> map) throws Exception;

	

}
