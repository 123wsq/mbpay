package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 银行路由接口
 * 
 * @author zhengqiang 2015/3/20
 *
 */
public interface RouteDao {

	/**
	 * 支付路由
	 * 
	 * @param param
	 *            {txncd 交易码 , agentId 代理商Id , rateType 费率类型}
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectRoute(Map<String, Object> param) throws Exception;
	
	/**
	 * 额度查询路由
	 * 
	 * @param param
	 *            {txncd 交易码 , agentId 代理商Id}
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectLmtRoute(Map<String, Object> param) throws Exception;
	
	/**
	 * 代付路由
	 * 
	 * @param param
	 *            {txncd 交易码 , agentId 代理商Id , rateType 费率类型}
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectDaiRoute(Map<String, Object> param) throws Exception;

}
