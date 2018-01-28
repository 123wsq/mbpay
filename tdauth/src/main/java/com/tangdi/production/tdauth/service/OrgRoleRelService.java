package com.tangdi.production.tdauth.service;

import java.util.List;

import com.tangdi.production.tdauth.bean.OrgRoleRelInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 机构、角色关系接口
 * @author zheng_qiang
 *
 */
public interface OrgRoleRelService extends BaseService<OrgRoleRelInf,Exception>{
	
	/**
	 * 批量删除机构角色关系
	 * @param list
	 * @return 删除行数
	 * @throws Exception 
	 */
	public int deleteRelList(List<OrgRoleRelInf> list) throws Exception ;
	
}
