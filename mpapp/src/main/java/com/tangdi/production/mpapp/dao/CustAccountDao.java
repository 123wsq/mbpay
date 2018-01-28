package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 商户账户信息DAO
 * 
 * @author zhuji
 * @version 1.0
 */
public interface CustAccountDao {
	/**
	 * 新增账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 修改账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;
	
	/**
	 * 并发修改账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntitySync(Map<String, Object> param) throws Exception;

	/**
	 * 查询账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 加锁查询账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntityForUpdate(Map<String, Object> param)
			throws Exception;

	/**
	 * 更新账户金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntityAmt(Map<String, Object> param) throws Exception;
}
