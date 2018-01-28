package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

public class CustShareInf extends BaseBean {
	/***
	 * 浠ｇ悊鍟嗙紪鍙�
	 */
	private String custId;

	/***
	 * 浠ｇ悊鍟嗗悕绉�
	 */
	private String custName;
	/***
	 * 鏈堝垎娑﹂噾棰�
	 */
	private String sharAmt;
	/***
	 * 鏈堜氦鏄撴�婚
	 */
	private String payAmt;
	/***
	 * 褰撴湀鍒嗘鼎姣斾緥
	 */
	private String rate;
	/***
	 * 褰撴湀鍒嗘鼎鐘舵��
	 */
	private String status;
	/***
	 * 澶勭悊鏃ユ湡
	 */
	private String updateTime;
	/***
	 * 澶勭悊浜�
	 */
	private String audituser;
	/***
	 * 鎵嬬画璐�
	 */
	private String payFee;
	/***
	 * 鍒嗘鼎鏃堕棿
	 */
	private String sharDate;
	/***
	 * 浠ｇ悊鍟嗙紪鍙�
	 */
	private String agentId;
	/***
	 * 浠ｇ悊鍟嗗悕绉�
	 */
	private String agentName;
	/***
	 * 浠ｇ悊鍟嗙瓑绾�
	 */
	private String agentDgr;
	/***
	 * 涓�绾т唬鐞嗗晢缂栧彿
	 */
	private String firstAgentId;
	/***
	 * 鐖剁骇浠ｇ悊鍟嗙紪鍙�
	 */
	private String fatherAgentId;
	private String payType;
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentDgr() {
		return agentDgr;
	}

	public void setAgentDgr(String agentDgr) {
		this.agentDgr = agentDgr;
	}

	public String getFirstAgentId() {
		return firstAgentId;
	}

	public void setFirstAgentId(String firstAgentId) {
		this.firstAgentId = firstAgentId;
	}

	public String getFatherAgentId() {
		return fatherAgentId;
	}

	public void setFatherAgentId(String fatherAgentId) {
		this.fatherAgentId = fatherAgentId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getSharDate() {
		return sharDate;
	}

	public void setSharDate(String sharDate) {
		this.sharDate = sharDate;
	}

	public String getSharAmt() {
		return sharAmt;
	}

	public void setSharAmt(String sharAmt) {
		this.sharAmt = sharAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
}
