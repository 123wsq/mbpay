package com.tangdi.production.tdauth.service;

import java.util.List;

import com.tangdi.production.tdauth.bean.UserRoleRelInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 用户、角色关系接口
 * @author zheng_qiang
 *
 */
public interface UserRoleRelService extends BaseService<UserRoleRelInf,Exception>{
       
	/**
	 * 批量删除用户角色关系
	 * @param list 用户角色关系List
	 * @return
	 * @throws Exception 
	 */
	public int removeRelList(List<UserRoleRelInf> list) throws Exception;
	

}
