package com.tangdi.production.mpomng.report;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 下载提现明细业务报表模版
 * @author zhaoqichao
 *
 */
@ReportHead(display=true)
public class WithdrawTemplate {
	
	
	@ReportField(title="提现日期")
	private String txDate;
	
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
	
	@ReportField(title="通道ID")
	private String payChannel;
	
	@ReportField(title="通道名称")
	private String payName;
	
	@ReportField(title="提现费率")
	private String rate;
	
	@ReportField(title="提现类型")
	private String casType;
	
	@ReportField(title="商户提现金额/元")
	private String txAmt;
	
	@ReportField(title="商户提现手续费/元")
	private String fee;
	
	@ReportField(title="实际划款金额/元")
	private String netrecAmt;
	
	@ReportField(title="支付方式" ,dict="PAYTYPE")
	private String acType;
	
	
	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
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

	public String getCasType() {
		return casType;
	}

	public void setCasType(String casType) {
		this.casType = casType;
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
