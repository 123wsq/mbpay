package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 机构对账报表模版
 * 
 * @author chenlibo
 *
 */
@ReportHead(display = true)
public class OrgSettleReportTemplate {
	
	@ReportField(title = "机构代码")
	private String agentId;
	
	@ReportField(title = "商户号")
	private String custId;
	
	@ReportField(title = "终端号")
	private String terminalNo;
	
	@ReportField(title = "支付卡号")
	private String payCardno;
	
	@ReportField(title = "交易金额")
	private String txAmt;
	
	@ReportField(title = "结算金额")
	private String netrecamt;
	
	@ReportField(title = "手续费")
	private String fee;
	
	@ReportField(title = "订单号")
	private String prdordNo;
	
	@ReportField(title = "交易日期")
	private String txnDate;
	
	@ReportField(title = "交易时间")
	private String txnTime;
	
	@ReportField(title = "交易流水号")
	private String paymentId;
	
	@ReportField(title = "交易类型")
	private String txnType;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getPayCardno() {
		return payCardno;
	}

	public void setPayCardno(String payCardno) {
		this.payCardno = payCardno;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public String getNetrecamt() {
		return netrecamt;
	}

	public void setNetrecamt(String netrecamt) {
		this.netrecamt = netrecamt;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getPrdordNo() {
		return prdordNo;
	}

	public void setPrdordNo(String prdordNo) {
		this.prdordNo = prdordNo;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	
}
