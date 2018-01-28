package com.tangdi.production.mpamng.dao;

import com.tangdi.production.mpamng.bean.AgentFeeInfo;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface AgentFeeDao extends BaseDao<AgentFeeInfo, Exception> {
	public AgentFeeInfo selectGrade(AgentFeeInfo entity) throws Exception;
}
