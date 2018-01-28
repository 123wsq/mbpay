package com.tangdi.production.tdauth.service;

import java.util.List;

import com.tangdi.production.tdauth.bean.AuditLogInf;

/**
 * 审计日志相关接口
 * 
 * @author songleiheng
 * 
 */
public interface AuditLogService {

	public int addEntity(AuditLogInf entity) throws Exception;

	int getCount(AuditLogInf auditLog, String currentOrgNo) throws Exception;

	/**
	 * 根据审计信息查询审计列表
	 * 
	 * @param auditLog
	 * @param start
	 * @param end
	 * @return
	 */
	List<AuditLogInf> getListPage(AuditLogInf auditLog, int start, int end, String currentOrgNo) throws Exception;
}
