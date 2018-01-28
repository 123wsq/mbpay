package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 分润
 * 
 * @author limiao
 *
 */
public interface ProfitSharingDao {
	/**
	 * 添加 分润
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;
	
	/**
	 * 删除 分润
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteEntity(Map<String, Object> param) throws Exception;
}
