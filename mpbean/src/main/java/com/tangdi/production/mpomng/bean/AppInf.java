package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class AppInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID 主键
	 */
	private String appId;

	/**
	 * APP 名称
	 */
	private String appName;

	/**
	 * APP 版本号
	 */
	private String appVersion;

	/**
	 * APP 平台 1 IOS 2ANDROID
	 */
	private String appPlatform;

	/**
	 * APP 是否自动更新 1 是 2否 3强制更新
	 */
	private String appAutoUpdate;

	/**
	 * APP 说明
	 */
	private String appDesc;

	/**
	 * APP 发布时间
	 */
	private String appIssueDate;

	/**
	 * APP 文件 大小 单位:MB
	 */
	private String appFileSzie;

	/**
	 * APP 文件ID
	 */
	private String appFileId;
	
	/**
	 * APP 文件名称
	 */
	private String appFileName;
	
	/**
	 * APP 下载路径
	 */
	private String appFilePath;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppPlatform() {
		return appPlatform;
	}

	public void setAppPlatform(String appPlatform) {
		this.appPlatform = appPlatform;
	}

	public String getAppAutoUpdate() {
		return appAutoUpdate;
	}

	public void setAppAutoUpdate(String appAutoUpdate) {
		this.appAutoUpdate = appAutoUpdate;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppIssueDate() {
		return appIssueDate;
	}

	public void setAppIssueDate(String appIssueDate) {
		this.appIssueDate = appIssueDate;
	}

	public String getAppFileSzie() {
		return appFileSzie;
	}

	public void setAppFileSzie(String appFileSzie) {
		this.appFileSzie = appFileSzie;
	}

	public String getAppFileId() {
		return appFileId;
	}

	public void setAppFileId(String appFileId) {
		this.appFileId = appFileId;
	}

	public String getAppFileName() {
		return appFileName;
	}

	public void setAppFileName(String appFileName) {
		this.appFileName = appFileName;
	}

	public String getAppFilePath() {
		return appFilePath;
	}

	public void setAppFilePath(String appFilePath) {
		this.appFilePath = appFilePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}