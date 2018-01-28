package com.tangdi.production.mpbatch.service;

/**
 * 商户 清算接口<br>
 * <br>
 * 1.将营业执照认证商户 的 交易金额 清算<br>
 * 2.商户 余额 清0
 * 
 * @author limiao
 */
public interface SettleAccountsService {
	/**
	 * 清算处理
	 * @throws Exception
	 */
	public void process() throws Exception;
}
