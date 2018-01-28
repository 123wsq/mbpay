package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 审计记录实体类
 * 
 * @author songleiheng
 * 
 */
public class AuditLogInf extends BaseBean implements Serializable {
	/**
	 * 审计记录编号
	 */
	private String auditlogId = "";

	private String orgNo;
	/**
	 * 请求客户端ip
	 */
	private String ip = "";
	/**
	 * 操作的菜单
	 */
	private String menuName = "";
	/**
	 * 用户的编号 用户唯一值
	 */
	private String UID = "";
	/**
	 * 用户登陆名
	 */
	private String userId = "";
	/**
	 * 操作时间
	 */
	private String operDate = "";
	/**
	 * ' for 页面查询 开始时间
	 */
	private String startDate = "";
	/**
	 * ' for 页面查询 结束时间
	 */
	private String endDate = "";

	/**
	 * 拿取开始时间
	 * 
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 设置开始时间
	 * 
	 * @return
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 拿取结束时间
	 * 
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束时间
	 * 
	 * @return
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public AuditLogInf(String auditlogId, String ip, String menuName, String uID, String userId, String operDate) {
		super();
		this.auditlogId = auditlogId;
		this.ip = ip;
		this.menuName = menuName;
		UID = uID;
		this.userId = userId;
		this.operDate = operDate;
	}

	public AuditLogInf() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAuditlogId() {
		return auditlogId;
	}

	public void setAuditlogId(String auditlogId) {
		this.auditlogId = auditlogId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

}
