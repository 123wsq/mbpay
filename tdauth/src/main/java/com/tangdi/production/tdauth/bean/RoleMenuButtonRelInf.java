package com.tangdi.production.tdauth.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 角色菜单关联信息（N:N）
 * @author june
 *
 */
public class RoleMenuButtonRelInf extends BaseBean {
	
	public RoleMenuButtonRelInf() {
	}
	public RoleMenuButtonRelInf(String roleId, String menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}
	/**
	 * 角色ID
	 */
	private String roleId;
	
	/**
	 * 菜单ID
	 */
	private String menuId;

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
