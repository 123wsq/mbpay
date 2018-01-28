package com.tangdi.production.mpomng.report;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 下载交易明细业务报表模版
 * @author zhaoqichao
 *
 */
@ReportHead(display=true)
public class TransTemplate {
	
	@ReportField(title="交易日期")
	private String prddate;
	
	@ReportField(title="一级代理商")
	private String firstAgentId;
	
	@ReportField(title="一级代理商名称")
	private String firstAgentName;
	
	@ReportField(title="代理商编号")
	private String agentId;
	
	@ReportField(title="代理商名称")
	private String agentName;
	
	@ReportField(title="终端号")
	private String terminalNO;
	
	@ReportField(title="商户号")
	private String custId;
	
	@ReportField(title="商户名称")
	private String custName;
	
	
	@ReportField(title="订单状态")
	private String ordStatus;
	
	@ReportField(title="交易状态")
	private String payStatus;
	
	@ReportField(title="通道ID")
	private String payChannel;
	
	@ReportField(title="通道名称")
	private String payName;
	
	@ReportField(title="交易费率")
	private String rate;
	
	@ReportField(title="交易类型")
	private String rateType;
	
	@ReportField(title="交易金额/元")
	private String txAmt;
	
	@ReportField(title="交易手续费/元")
	private String fee;
	
	@ReportField(title="实际金额/元")
	private String netrecAmt;
	
	@ReportField(title="支付方式", dict="PAYTYPE")
	private String bizType;
	
	
	
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getPrddate() {
		return prddate;
	}
	public void setPrddate(String prddate) {
		this.prddate = prddate;
	}
	public String getFirstAgentId() {
		return firstAgentId;
	}
	public void setFirstAgentId(String firstAgentId) {
		this.firstAgentId = firstAgentId;
	}
	public String getFirstAgentName() {
		return firstAgentName;
	}
	public void setFirstAgentName(String firstAgentName) {
		this.firstAgentName = firstAgentName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	public String getTerminalNO() {
		return terminalNO;
	}
	public void setTerminalNO(String terminalNO) {
		this.terminalNO = terminalNO;
	}
	public String getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getNetrecAmt() {
		return netrecAmt;
	}
	public void setNetrecAmt(String netrecAmt) {
		this.netrecAmt = netrecAmt;
	}
}
