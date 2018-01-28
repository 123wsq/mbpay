package com.tangdi.production.mprcs.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mprcs.dao.OrgTermLimitDao;
import com.tangdi.production.mprcs.service.OrgTermLimitService;

/**
 * 合作机构终端风控接口实现
 * 
 * @author zhengqiang 2014/04/25
 *
 */
@Service
public class OrgTermLimitServiceImpl implements OrgTermLimitService {
	private static final Logger log = LoggerFactory.getLogger(OrgTermLimitServiceImpl.class);

	@Autowired
	private OrgTermLimitDao dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int add(Map<String, Object> param) throws Exception {
		log.debug("添加合作机构终端风控交易记录.");
		return dao.insertEntity(param);
	}

	@Override
	public Map<String, Object> queryTxnAmt(Map<String, Object> param) throws Exception {
		return dao.selectTxnAmt(param);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int update(Map<String, Object> param) throws Exception {
		return dao.updateEntity(param);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateOrgAmt(Map<String, Object> param) {
		return dao.updateEntityAmt(param);
	}

}
