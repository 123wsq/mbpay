package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 */
public class PayInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 支付订单号
	 */
	private String payordno;

	/**
	 * 商户ID
	 */
	private String custId;
	/**
	 * 商户名称
	 */
	private String custName;

	/**
	 * 订单状态
	 */
	private String paystatus;
	

	

	/**
	 * 审核状态
	 */
	private String auditStatus;
	

	/**
	 * 审核意见
	 */
	private String auditIdea;

	/**
	 * 支付订单类型
	 */
	private String payordtype;

	/**
	 * 支付方式
	 */
	private String paytype;

	/**
	 * 订单金额
	 */
	private String txamt;

	/**
	 * 费率
	 */
	private String rate;

	/**
	 * 手续费
	 */
	private String fee;

	/**
	 * 实际金额
	 */
	private String netrecamt;
	/**
	 * 验证状态
	 */
	private String checkAudit;
	/**
	 * 审核时间
	 */
	private String auditTime;

	public String getCheckAudit() {
		return checkAudit;
	}

	public void setCheckAudit(String checkAudit) {
		this.checkAudit = checkAudit;
	}

	/**
	 * 支付渠道编号
	 */
	private String payChannel;

	/**
	 * 支付日期
	 */
	private String payDate;

	/**
	 * 支付时间
	 */
	private String payTime;

	/**
	 * 支付时间（年月日时分秒）
	 */
	private String payordtime;

	/**
	 * 最后更新时间
	 */
	private String modifyTime;
	/**
	 * 代理商编号
	 */
	private String agentId;
	/**
	 * 代理商名称
	 */
	private String agentName;
	/**
	 * 银行账号
	 */
	private String bankpayacno;
	/**
	 * 银行名称
	 */
	private String bankcode;
	
	/**
	 * 终端号
	 */
	private String terminalSeq;
	
	/**
	 * 银行卡照片
	 */
	private String cardSignPic;

	public String getCardSignPic() {
		return cardSignPic;
	}

	public void setCardSignPic(String cardSignPic) {
		this.cardSignPic = cardSignPic;
	}

	public String getPayordno() {
		return payordno;
	}

	public void setPayordno(String payordno) {
		this.payordno = payordno;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getPayordtype() {
		return payordtype;
	}

	public void setPayordtype(String payordtype) {
		this.payordtype = payordtype;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getTxamt() {
		return txamt;
	}

	public void setTxamt(String txamt) {
		this.txamt = txamt;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getNetrecamt() {
		return netrecamt;
	}

	public void setNetrecamt(String netrecamt) {
		this.netrecamt = netrecamt;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayordtime() {
		return payordtime;
	}

	public void setPayordtime(String payordtime) {
		this.payordtime = payordtime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getBankpayacno() {
		return bankpayacno;
	}

	public void setBankpayacno(String bankpayacno) {
		this.bankpayacno = bankpayacno;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getTerminalSeq() {
		return terminalSeq;
	}

	public void setTerminalSeq(String terminalSeq) {
		this.terminalSeq = terminalSeq;
	}
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditIdea() {
		return auditIdea;
	}

	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}