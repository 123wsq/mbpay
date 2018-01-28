package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 代理商信息接口
 * 
 * @author zhuji
 *
 */
public interface AgentInfDao {
	/***
	 * 根据当前代理商ID获取一级代理商ID
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntityByAgentId(@Param("agentId") String agentId) throws Exception;
	
	public Map<String, Object> selectAgentRate(Map<String,Object> param) throws Exception;
	
	/***
	 * 根据代理类型查询代理商
	 * @param filed1
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListByFiled1(@Param("filed1") String filed1) throws Exception;

}
