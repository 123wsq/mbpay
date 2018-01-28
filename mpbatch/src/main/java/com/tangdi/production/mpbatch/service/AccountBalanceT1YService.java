package com.tangdi.production.mpbatch.service;

/**
 * T+0&T+1金额 转到 T+1未提接口 <br>
 * <br>
 * 将T+0&T+1金额 金额 转到 T+1未提
 * 
 * @author limiao
 */
public interface AccountBalanceT1YService {
	
	/**
	 * 资金转移T+0&T+1 到 TY
	 */
	public void process() throws Exception;

}
