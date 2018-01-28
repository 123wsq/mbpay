package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 *
 * 
 *
 * @author limiao
 * @version 1.0
 */
public interface PayDao {
	/**
	 * 创建支付订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 修改支付订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 更新电子签名
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateESign(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询支付订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询支付订单和对应账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectPayInfAcc(Map<String, Object> param) throws Exception;
}
