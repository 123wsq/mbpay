package com.tangdi.production.mpbatch.dao;

import java.util.Map;

public interface PrdUntreateDao {

	/***
	 * 未处理订单转入未处理订单表
	 * 
	 * @return
	 * @throws Exception
	 */
	public int insertPrdUntreate(Map<String, Object> param) throws Exception;

}
