package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * 交易流水业务层接口
 * @author shanbeiyi
 * @version 1.0
 *
 */
public interface PaymentJournalService {

	/**
	 * 添加交易流水
	 * @param param
	 * @return
	 * @throws TranException
	 * @throws Exception 
	 */
	public String addPaymentJournal(Map<String , Object> param) throws TranException;
	
	/**
	 * 修改交易流水
	 * @param param
	 * @return
	 * @throws TranException
	 * @throws Exception 
	 */
	public int modifyPaymentJournal(Map<String , Object> param) throws TranException;
	
	/**
	 * 查找原支付交易流水
	 * @param param
	 *           opayordno  原支付订单号
	 *           otxntype  交易类型 01 消费 02 消费撤销...
	 * @return
	 * @throws TranException
	 * @throws Exception 
	 */
	public Map<String , Object> queryOrnPaymentJournal(Map<String , Object> param) throws TranException;
	
	/**
	 * 修改交易状态
	 * @param param
	 * @return
	 * @throws TranException
	 * @throws Exception 
	 */
	public int modifyPaymentJournalStatus(Map<String , Object> param) throws TranException;
}
