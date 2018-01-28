package com.tangdi.production.mprcs.service;

import java.util.Map;

/**
 * 合作机构终端风控接口
 * 
 * @author zhengqiang
 *
 */
public interface OrgTermLimitService {

	/**
	 * 修改终端交易记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int update(Map<String, Object> param) throws Exception;

	/**
	 * 添加终端交易记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int add(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryTxnAmt(Map<String, Object> param) throws Exception;

	/**
	 * 交易失败时更新限额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateOrgAmt(Map<String, Object> param);

}
