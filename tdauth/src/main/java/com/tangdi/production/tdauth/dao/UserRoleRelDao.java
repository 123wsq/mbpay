package com.tangdi.production.tdauth.dao;


import java.util.Map;

import com.tangdi.production.tdauth.bean.UserRoleRelInf;
import com.tangdi.production.tdbase.dao.BaseRelDao;

/**
 * 用户角色关系 数据库操作接口
 * 
 * @author zheng_qiang
 * 
 */
public interface UserRoleRelDao extends BaseRelDao<UserRoleRelInf, Exception> {
	
	/**
	 * 根据用户ID列表删除用户角色关系
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int deleteUsersRels(Map<String, Object> con) throws Exception;
	
	/**
	 * 根据角色ID列表删除用户角色关系
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int deleteRolesRels(Map<String, Object> con) throws Exception;
	 

	
}
