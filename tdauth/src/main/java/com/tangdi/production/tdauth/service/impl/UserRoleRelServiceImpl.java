package com.tangdi.production.tdauth.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.tdauth.bean.UserRoleRelInf;
import com.tangdi.production.tdauth.dao.UserRoleRelDao;
import com.tangdi.production.tdauth.service.UserRoleRelService;

/**
 * 用户、角色关系实现类
 * 
 * @author zheng_qiang
 * 
 */
@Service
public class UserRoleRelServiceImpl implements UserRoleRelService {

	/**
	 * 用户、角色关系数据访问对象
	 */
	@Autowired
	private UserRoleRelDao userRoleRelDao;

	public UserRoleRelInf getEntity(UserRoleRelInf entity) throws Exception {
		return null;
	}

	public List<UserRoleRelInf> getList(UserRoleRelInf entity) {
		return null;
	}

	public int addEntity(UserRoleRelInf entity) {
		return 0;
	}
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addList(List<UserRoleRelInf> list) throws Exception {
		return userRoleRelDao.insertRelList(list);
	}

	public int modifyEntity(UserRoleRelInf entity) throws Exception {

		return 0;
	}

	public int removeEntity(UserRoleRelInf entity) throws Exception {
		return 0;
	}



	public Integer getCount(UserRoleRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, Object> getDataPage(UserRoleRelInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserRoleRelInf> getListPage(UserRoleRelInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int removeRelList(List<UserRoleRelInf> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}




}
