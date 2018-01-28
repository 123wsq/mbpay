package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.util.DictUtils;

/** 机构 */
@SuppressWarnings("serial")
public class OrgInf extends BaseBean implements Serializable {
	/**
	 * 机构号
	 */
	private String orgId;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 父机构编号
	 */
	private String orgParId;
	/**
	 * 父机构名称
	 */
	private String orgParName;

	/**
	 * 机构级别
	 */
	private String orgLevel;

	/**
	 * 机构级别中文名称
	 */
	private String orgLevelName;

	/**
	 * 机构状态
	 */
	private Integer orgStatus;

	/**
	 * 机构状态中文名称
	 */
	private String orgStatusName;

	/**
	 * 机构描述
	 */
	private String orgDesc;

	private String logo;// logo
	private String phone;// 手机

	public OrgInf() {
	}
	public OrgInf(String orgId) {
		super();
		this.orgId = orgId;
	}

	public OrgInf(String orgId, Integer orgStatus) {
		super();
		this.orgId = orgId;
		this.orgStatus = orgStatus;
	}

	public OrgInf(String orgId, String orgName, String orgParId, String orgLevel, Integer orgStatus, String orgDesc) {
		super();
		this.orgId = orgId;
		this.orgName = orgName;
		this.orgParId = orgParId;
		this.orgLevel = orgLevel;
		this.orgLevelName = DictUtils.get("ORGLEVEL", this.orgLevel + "");
		this.orgStatus = orgStatus;
		this.orgStatusName = DictUtils.get("ORGSTATUS", this.orgStatus + "");
		this.orgDesc = orgDesc;
	}

	public String getOrgParName() {
		return orgParName;
	}

	public void setOrgParName(String orgParName) {
		this.orgParName = orgParName;
	}

	public String getOrgLevelName() {
		return orgLevelName;
	}

	public String getOrgStatusName() {
		return orgStatusName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgParId() {
		return orgParId;
	}

	public void setOrgParId(String orgParId) {
		this.orgParId = orgParId;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
		this.orgLevelName = DictUtils.get("ORGLEVEL", this.orgLevel + "");
	}

	public Integer getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(Integer orgStatus) {
		this.orgStatus = orgStatus;
		this.orgStatusName = DictUtils.get("ORGSTATUS", this.orgStatus + "");
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "OrgInf [orgId=" + orgId + ", orgName=" + orgName + ", orgParId=" + orgParId + ", orgParName=" + orgParName + ", orgLevel=" + orgLevel + ", orgLevelName=" + orgLevelName + ", orgStatus=" + orgStatus + ", orgStatusName=" + orgStatusName + ", orgDesc=" + orgDesc + ", logo=" + logo + ", phone=" + phone + "]";
	}

}
