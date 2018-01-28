package com.tangdi.production.tdauth.dao;

import java.util.HashMap;

import com.tangdi.production.tdauth.bean.AuditMenuRelInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 审计 数据库操作接口
 * 
 * @author songleiheng
 * 
 */
public interface AuditDao extends BaseDao<AuditMenuRelInf, Exception> {

	int insertAuditMenuRef(HashMap<String, String> map) throws Exception;

	int deleteMenuRefByAudit(String auditId) throws Exception;

}
