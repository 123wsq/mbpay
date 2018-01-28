package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 机构审计菜单实体类 对应表 AUTH_AUDIT_MENU_REL_INF
 * @author songleiheng
 *
 */
public class AuditMenuRelInf extends BaseBean implements Serializable {
	/**
	 * 审计编码
	 */
	private String auditId;
	/**
	 * 菜单编码
	 */
	private String menuId;
	
	public AuditMenuRelInf() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuditMenuRelInf(String auditId, String menuId) {
		super();
		this.auditId = auditId;
		this.menuId = menuId;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
