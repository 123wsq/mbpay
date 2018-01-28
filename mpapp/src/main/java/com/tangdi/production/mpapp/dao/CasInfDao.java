package com.tangdi.production.mpapp.dao;

import java.util.Map;


/**
*
* 
*提现订单dao
* @author zhuji
* @version 1.0
*/
public interface CasInfDao{
	/**
	 * 插入提现订单信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String , Object> param) throws Exception;
	
	/**
	 * 汇总当天商户T0提现金额
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sumT0Amt(Map<String,Object> param) throws Exception;
	
	/**删除确认代付重复信息*/
	public int deleteConPay(Map<String, Object> param)throws Exception;
	/**插入确认代付表信息*/
	public int insertConPay(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改提现审核状态
	 * @param paramMap
	 * @return
	 */
	public int modifyCasPrdBystauts(Map<String, Object> paramMap);
	
	/**
	 * 汇总当天代理商T0提现金额
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sumAgentT0Amt(Map<String,Object> param) throws Exception;
}
