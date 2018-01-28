package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 代理商信息接口
 * 
 * @author zhuji
 *
 */
public interface AgentDao {

	/**
	 * 查询代理商信息
	 * 
	 * @param param
	 * @return map（agentId firstAgentId frozState ageStatus）
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param) throws Exception;

	/**
	 * 查询代理商费率
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectAgentRate(Map<String, Object> param) throws Exception;

}
