package com.tangdi.production.mpbatch.dao;

import java.util.Map;

/**
 * 账户 余额
 * 
 * @author limiao
 *
 */
public interface AccountBalanceDao {
	/**
	 * 每天 T+0 金额 转到 T+1未提
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int accountBalanceT0(Map<String, Object> map) throws Exception;

	/**
	 * 将每天 T+0&T+1 金额 转到 T+1 未提
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int accountBalanceT1Y(Map<String, Object> map) throws Exception;

	/**
	 * 商户余额 清算
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int settleAccounts(Map<String, Object> map) throws Exception;

	public int updateAccounts(Map<String, Object> map) throws Exception;

}
