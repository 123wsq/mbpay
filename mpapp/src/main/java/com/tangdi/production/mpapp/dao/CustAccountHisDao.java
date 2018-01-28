package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 *
 * 
 *
 * @author limiao
 * @version 1.0
 */
public interface CustAccountHisDao {
	public int insertEntity(Map<String, Object> map) throws Exception;

	public int updateEntity(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectEntity(Map<String, Object> map) throws Exception;

	/**
	 * 更新账户金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntityAmt(Map<String, Object> param) throws Exception;
}
