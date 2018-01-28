package com.tangdi.production.tdauth.service;

import com.tangdi.production.tdauth.bean.AuditMenuRelInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 审计相关接口
 * @author songleiheng
 *
 */
public interface AuditService extends BaseService<AuditMenuRelInf, Exception>{

	/**
	 * 根据审计id 更新审计管理表
	 * @param auditId
	 * @param checkValue
	 * @return
	 */
	public int modifyAuditInf(String auditId, String checkValue);
	
}
