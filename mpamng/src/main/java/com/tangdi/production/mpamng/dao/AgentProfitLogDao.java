package com.tangdi.production.mpamng.dao;

import java.util.List;
import java.util.Map;

public interface AgentProfitLogDao {
	/***
	 * 获取所有分润记录
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectList(Map<String, Object> map) throws Exception;

	/***
	 * 获取所有记录数
	 * 
	 * @return
	 */
	int selectCount(Map<String, Object> param) throws Exception;
}
