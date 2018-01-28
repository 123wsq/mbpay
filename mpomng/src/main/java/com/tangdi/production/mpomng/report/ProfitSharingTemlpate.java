package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;

/***
 * 代理商分润业务报表
 * 
 * @author sunhaining
 *
 */
public class ProfitSharingTemlpate {
	@ReportField(title = "商户编号")
	private String custId;
	@ReportField(title = "商户名称")
	private String custName;
	@ReportField(title = "交易金额")
	private String payAmt;
	@ReportField(title = "可分润金额")
	private String payFee;
	@ReportField(title = "分润比例")
	private String rate;
	@ReportField(title = "实际分润金额")
	private String sharAmt;
	@ReportField(title = "交易月份")
	private String sharDate;
	@ReportField(title = "所属代理商编号")
	private String agentId;
	@ReportField(title = "所属代理商名称")
	private String agentName;
	@ReportField(title = "所属代理商级别")
	private String agentDgr;
	@ReportField(title = "一级代理商编号")
	private String firstAgentId;
	@ReportField(title = "父级代理商编号")
	private String fatherAgentId;
	@ReportField(title = "分润类型码")
	private String payType;
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSharDate() {
		return sharDate;
	}

	public void setSharDate(String sharDate) {
		this.sharDate = sharDate;
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

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSharAmt() {
		return sharAmt;
	}

	public void setSharAmt(String sharAmt) {
		this.sharAmt = sharAmt;
	}
}
