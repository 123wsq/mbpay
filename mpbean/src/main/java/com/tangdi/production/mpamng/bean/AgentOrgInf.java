package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class AgentOrgInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String ageorgId;

	/**
	 * 代理商ID
	 */
	private String agentId;

	/**
	 * 合作机构编号
	 */
	private String cooporgNo;

	/**
	 * 合作机构大商户号
	 */
	private String merNo;

	private String rateType;

	private String rateTypeTop;
	
	private String provinceName;
	
	private String merName;
	
	private String coopName;

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getCoopName() {
		return coopName;
	}

	public void setCoopName(String coopName) {
		this.coopName = coopName;
	}

	public String getAgeorgId() {
		return ageorgId;
	}

	public void setAgeorgId(String ageorgId) {
		this.ageorgId = ageorgId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCooporgNo() {
		return cooporgNo;
	}

	public void setCooporgNo(String cooporgNo) {
		this.cooporgNo = cooporgNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateTypeTop() {
		return rateTypeTop;
	}

	public void setRateTypeTop(String rateTypeTop) {
		this.rateTypeTop = rateTypeTop;
	}
}