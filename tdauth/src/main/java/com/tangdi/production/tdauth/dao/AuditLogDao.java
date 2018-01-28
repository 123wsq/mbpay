package com.tangdi.production.tdauth.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.tdauth.bean.AuditLogInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 审计日志 数据库操作接口
 * 
 * @author songleiheng
 * 
 */
public interface AuditLogDao extends BaseDao<AuditLogInf, Exception> {

	List<AuditLogInf> selectListPage(Map<String, Object> cond);

	public int countEntity(Map<String, Object> map) throws Exception;
}
