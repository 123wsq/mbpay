package com.tangdi.production.mpbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.bean.CnapsInf;
import com.tangdi.production.mpbase.dao.CnapsDao;
import com.tangdi.production.mpbase.service.CnapsService;
import com.tangdi.production.mpomng.bean.CustBankInf;

@Service
public class CnapsServiceImpl implements CnapsService {

	@Autowired
	private CnapsDao cnapsDao;

	@Override
	public List<CnapsInf> getListPage(CnapsInf entity) throws Exception {
		return cnapsDao.selectList(entity);
	}

	@Override
	public Integer getCount(CnapsInf entity) throws Exception {
		return cnapsDao.countEntity(entity);
	}

	@Override
	public CnapsInf getEntity(CnapsInf entity) throws Exception {
		return cnapsDao.selectEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(CnapsInf entity) throws Exception {
		return cnapsDao.insertEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(CnapsInf entity) throws Exception {
		return cnapsDao.updateEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(CnapsInf entity) throws Exception {
		return cnapsDao.deleteEntity(entity);
	}

	@Override
	public int validateBranchState(CustBankInf custBankInf) throws Exception {
		return cnapsDao.validateBranchState(custBankInf);
	}

	@Override
	public int validateCnapsCode(CnapsInf cnapsInf) throws Exception {
		return cnapsDao.validateCnapsCode(cnapsInf);
	}

}
