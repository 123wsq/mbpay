package com.tangdi.production.mpbatch.service;

import java.util.List;
import java.util.Map;


/**
 * 商户余额 清算接口<br>
 * <br>
 * 1.将商户 的 账户金额 清算<br>
 * 2.商户 余额 清0
 * 
 * @author limiao
 */
public interface AccountMerBalanAt1Service {

	/**
	 * 获取备份表所有的账户信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCustCopyAccount(Map<String, Object> map) throws Exception ;


	/**
	 *  账户余额 清算批处理
	 */
	public void processAccount(Map<String, Object> map) throws Exception;
	
	/**
	 * 清除 商户余额 提现订单表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int  emptyAccountAt1(Map<String, Object> map) throws Exception;
	
	/**
	 * 批处理T1提现
	 * 
	 * */
	public void processCas(Map<String,Object> map) throws Exception;
}

	
