package com.tangdi.production.tdauth.dao;

import java.util.List;

import com.tangdi.production.tdauth.bean.OrgRoleRelInf;
import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 机构角色关系 数据库操作接口
 * 
 * @author
 * 
 */
public interface OrgRoleRelDao extends BaseDao<OrgRoleRelInf, Exception> {
     public List<RoleInf> selectListRole(String orgid);
}
