package com.tangdi.production.mpbase.dao;

import java.util.Map;

/**
 * 屏蔽 代理商系统菜单
 * 
 * @author zhuji
 *
 */
public interface AgentShieldMenuDao {

	/**
	 * 查询代理商信息
	 * 
	 * @param param
	 * @return map（agentId firstAgentId frozState ageStatus）
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param) throws Exception;

}
