package com.tangdi.production.mpomng.dao;

import java.util.Map;

public interface AgentBindDao {

	/**
	 * 查看商户所绑定的代理商
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCustAgent(Map<String, Object> param);
	
	
	/**
	 * 绑定代理商
	 * @param param
	 * @return
	 */
	public int insertAgeCustInfo(Map<String, Object> param);
	
}
