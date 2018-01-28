package com.tangdi.production.mpapp.dao;

import java.util.Map;



/**
*
* 
*交易流水dao
* @author zhuji
* @version 1.0
*/
public interface PaymentJournalDao {
	/**
	 * 新增交易流水
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String , Object> param) throws Exception;
	
	/**
	 * 修改交易流水
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String , Object> param) throws Exception;
	
	/**
	 * 根据支付订单号 查询交易流水
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOrnPaymentJournal(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改交易状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updatePayJnlStatus(Map<String , Object> param) throws Exception;

}
