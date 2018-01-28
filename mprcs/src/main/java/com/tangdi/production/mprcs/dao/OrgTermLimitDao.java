package com.tangdi.production.mprcs.dao;

import java.util.Map;

/**
 * 合作机构终端风控DAO
 * 
 * @author wangguangwei
 *
 */
public interface OrgTermLimitDao {

	/**
	 * 添加终端交易记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 修改终端交易记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;

	/**
	 * 查询 合适的大商户号 ，终端号。
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectTxnAmt(Map<String, Object> param) throws Exception;
	/**
	 * 修改终端交易记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntityAmt(Map<String, Object> param);

}
