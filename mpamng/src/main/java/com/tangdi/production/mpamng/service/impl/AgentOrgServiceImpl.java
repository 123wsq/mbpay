package com.tangdi.production.mpamng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.bean.AgentOrgInf;
import com.tangdi.production.mpamng.dao.AgentOrgDao;
import com.tangdi.production.mpamng.service.AgentOrgService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class AgentOrgServiceImpl implements AgentOrgService {
	private static final Logger log = LoggerFactory.getLogger(AgentOrgServiceImpl.class);
	@Autowired
	private AgentOrgDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<AgentOrgInf> getListPage(AgentOrgInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(AgentOrgInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public AgentOrgInf getEntity(AgentOrgInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		AgentOrgInf agentOrgInf = null;
		try {
			agentOrgInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", agentOrgInf.debug());
		return agentOrgInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(AgentOrgInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			entity.setAgeorgId(seqNoService.getSeqNoNew("SEQ_MPAMNG_AGENT_ORG_INF", "9", "0"));
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(AgentOrgInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("更新数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(AgentOrgInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.deleteEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("删除数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

}
