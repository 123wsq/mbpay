package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * @author limiao
 * @version 1.0
 */
public interface PaymentDao {
	/**
	 * 创建 支付流水表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 修改 支付流水表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;
}
