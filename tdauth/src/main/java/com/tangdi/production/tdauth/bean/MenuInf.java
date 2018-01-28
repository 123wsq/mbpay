package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 菜单基本信息
 * @author junyu
 *
 */
public class MenuInf extends BaseBean implements Serializable {



	

	public MenuInf() {
	}
	public MenuInf(String menuId, String menuName,int tmp) {
		this.menuId = menuId;
		this.menuName = menuName;
	}

	public MenuInf(String menuId, String menuIsLeaf) {
		this.menuId = menuId;
		this.menuIsLeaf = menuIsLeaf;
	}
	public MenuInf(String menuId, String menuName, String menuUrl,
			String menuStatus, String menuType, String menuParId,
			String menuCode, String sysId, String menuIsLeaf, String rmk
			) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.menuStatus = menuStatus;
		this.menuType = menuType;
		this.menuParId = menuParId;
		this.menuCode = menuCode;
		this.sysId = sysId;
		this.menuIsLeaf = menuIsLeaf;
		this.rmk = rmk;

	}

	
	public MenuInf(String rmk,String sysId,String flag) {
		this.rmk = rmk;
		this.sysId = sysId;
	}

	public MenuInf(String sysId,int flag){
		this.sysId = sysId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	private String menuId;
	/**
	 * 菜单名
	 */
	private String menuName;
	/**
	 * 菜单地址
	 */
	private String menuUrl;


	/**
	 * 菜单状态（1：禁用 0：启用）
	 */
	private String menuStatus;
	/**
	 * 菜单状态 对应的中文
	 */
	private String menuStatusCh;
	/**
	 * 菜单类型
	 */
	private String menuType;
	/**
	 * 菜单类型对应的中文
	 */
	private String menuTypeCh;
	/**
	 * 父菜单ID 
	 */
	private String menuParId;
	/**
	 * 菜单英文代码
	 */
	private String menuCode;
	/**
	 * 系统ID
	 */
	private String sysId;
	/**
	 * 节点类型（0：叶子节点 1：目录节点）
	 * 是否叶子结点
	 * 0 否
	 * 1 是
	 */
	private String menuIsLeaf ;
	/**
	 * UI使用字段
	 */
	private String rmk;
	
	/**
	 * 菜单相关查询使用，标记是否拥有菜单状态。
	 */
	private boolean checked;

	
	public String getMenuStatusCh() {
		return menuStatusCh;
	}

	public String getMenuTypeCh() {
		return menuTypeCh;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getMenuIsLeaf() {
		return menuIsLeaf;
	}


	public void setMenuIsLeaf(String menuIsLeaf) {
		this.menuIsLeaf = menuIsLeaf;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(String menuStatus) {
		this.menuStatus   = menuStatus;
	//	this.menuStatusCh = DictUtils.get("MSTATUS", menuStatus==null?"":String.valueOf(menuStatus));
	}

	
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType   = menuType;
	//	this.menuTypeCh = DictUtils.get("MTYPE", menuType==null?"":String.valueOf(menuType));
	}

	public String getMenuParId() {
		return menuParId;
	}

	public void setMenuParId(String menuParId) {
		this.menuParId = menuParId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	

}
