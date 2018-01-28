package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;



/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */
public interface CasService{
	/**
	 * 新建提现订单
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int addCasInf(Map<String , Object> param) throws TranException;
	
	/**
	 * 汇总当天商户T0提现金额
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> totalT0Amt(String custId) throws TranException;
	
	/**
	 * 新建确认提现订单
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int addConCasInf(Map<String , Object> param) throws TranException;
	
	/**
	 * 删除确认提现订单
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public int deleteConPayByCasId(String casId) throws TranException;
	
	/**
	 * 更新提现订单状态
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int updateCasInfStatus(Map<String , Object> param) throws TranException;

	/**
	 * 汇总商户的代理商当天T0提现金额
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> totalAgeT0Amt(String custId) throws TranException;

}
