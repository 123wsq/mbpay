package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class AppimgInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String appimgId;

	/**
	 * APP 轮播图 路径
	 */
	private String appimgPath;

	/**
	 * APP 轮播图 描述
	 */
	private String appimgDesc;
	
	/**
	 * APP 轮播图 是否显示（0显示  1不显示）
	 */
	private String appimgDisplay;
	
	/**
	 * 页面显示图片的名称
	 */
	private String appimgName;
	

	public String getAppimgName() {
		return appimgName;
	}

	public void setAppimgName(String appimgName) {
		this.appimgName = appimgName;
	}

	public String getAppimgId() {
		return appimgId;
	}

	public void setAppimgId(String appimgId) {
		this.appimgId = appimgId;
	}

	public String getAppimgPath() {
		return appimgPath;
	}

	public void setAppimgPath(String appimgPath) {
		this.appimgPath = appimgPath;
	}

	public String getAppimgDesc() {
		return appimgDesc;
	}

	public void setAppimgDesc(String appimgDesc) {
		this.appimgDesc = appimgDesc;
	}

	public String getAppimgDisplay() {
		return appimgDisplay;
	}

	public void setAppimgDisplay(String appimgDisplay) {
		this.appimgDisplay = appimgDisplay;
	}

}