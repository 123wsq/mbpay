package com.tangdi.production.tdauth.bean;

public class Attributes {

	String url;
	
	/**
	 * 菜单类型
	 */
	private String menuType;
	
	/**
	 * 菜单状态（0：禁用 1：启用）
	 */
	private String menuStatus;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(String menuStatus) {
		this.menuStatus = menuStatus;
	}
	
}
