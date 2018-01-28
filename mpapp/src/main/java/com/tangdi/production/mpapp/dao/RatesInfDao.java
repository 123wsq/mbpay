package com.tangdi.production.mpapp.dao;

import java.util.Map;


/**
 * 费率信息管理
 * 
 * @author chenlibo
 * 
 * @version 1.0
 */
public interface RatesInfDao {

	/**
	 * 查询终端信息
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> map) throws Exception;
	
	/**
	 * 新增默认商户费率
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public int insertDefMerRate(Map<String, Object> map) throws Exception;


}
