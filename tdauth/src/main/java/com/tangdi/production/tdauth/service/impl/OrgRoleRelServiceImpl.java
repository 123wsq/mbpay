package com.tangdi.production.tdauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.tdauth.bean.OrgRoleRelInf;
import com.tangdi.production.tdauth.dao.OrgRoleRelDao;
import com.tangdi.production.tdauth.service.OrgRoleRelService;

@Service
public class OrgRoleRelServiceImpl implements OrgRoleRelService {

	@Autowired
	OrgRoleRelDao orgRoleRelDao;

	public OrgRoleRelInf getEntity(OrgRoleRelInf entity) {
		return null;
	}

	public List<OrgRoleRelInf> getList(OrgRoleRelInf entity) {
		return null;
	}
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(OrgRoleRelInf entity) throws Exception {
		return orgRoleRelDao.insertEntity(entity);
	}
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addList(List<OrgRoleRelInf> list) throws Exception {
		return orgRoleRelDao.insertList(list);
	}

	public int modifyEntity(OrgRoleRelInf entity) {
		return 0;
	}


	public int removeEntity(OrgRoleRelInf entity) {
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int deleteRelList(List<OrgRoleRelInf> list) throws Exception {
		int count = 0;
		for (OrgRoleRelInf entity : list) {
			int rv = 0;
			rv = orgRoleRelDao.deleteEntity(entity);
			if (rv == 1) {
				count++;
			}
		}
		return count;
	}

	public Integer getCount(OrgRoleRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	public List<OrgRoleRelInf> getListPage(OrgRoleRelInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
