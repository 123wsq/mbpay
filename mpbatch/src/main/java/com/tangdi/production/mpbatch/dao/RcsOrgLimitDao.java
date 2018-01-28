package com.tangdi.production.mpbatch.dao;

import java.util.Map;

/**
 * 
 * @author limiao
 *
 */
public interface RcsOrgLimitDao {
	/**
	 * 修改每天的 DAY MONTH && 日成交额 和 月成交额
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateRcsOrgLimit(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectRcsOrgLimit(Map<String, Object> map) throws Exception;

}
