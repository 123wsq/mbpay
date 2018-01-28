package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 商品 订单
 * 
 * @author limiao
 * 
 * @version 1.0
 */
public interface PrdDao {
	/**
	 * 创建商品订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 修改商品订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;

	/**
	 * 查询商品订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param) throws Exception;

	/**
	 * 根据商品订单号 查询商品订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryPrdInfByprdNo(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @param map
	 *            prdordNo 商品订单号
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> check(Map<String, Object> map) throws Exception;
}
