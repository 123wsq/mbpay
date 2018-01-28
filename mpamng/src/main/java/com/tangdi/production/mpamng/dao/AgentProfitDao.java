package com.tangdi.production.mpamng.dao;

import java.util.List;

import com.tangdi.production.mpamng.bean.AgentProfitInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface AgentProfitDao extends BaseDao<AgentProfitInf, Exception> {
	public List<AgentProfitInf> selectGrade(AgentProfitInf entity) throws Exception;
}
