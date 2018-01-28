package com.tangdi.production.mpbatch.dao;

import java.util.List;
import java.util.Map;

public interface AgentProfitDayLogDao {
	/**
	 * 查询当月所有日分润记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectDayLogList(Map<String , Object > param) throws Exception;
}
