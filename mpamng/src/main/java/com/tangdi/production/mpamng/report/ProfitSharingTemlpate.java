package com.tangdi.production.mpamng.report;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.tdbase.annotation.ReportField;

/***
 * 代理商分润业务报表
 * 
 * @author sunhaining
 *
 */
public class ProfitSharingTemlpate {
	
	@ReportField(title = "代理商编号")
	private String agentId;
	
	@ReportField(title = "代理商名称")
	private String agentName;
	
	@ReportField(title = "分润计算日期")
	private String sharDate;
	
	@ReportField(title = "等级")
	private String agentDgr;
	
	@ReportField(title = "分润总金额"  )
	private String sharAmt;
	
//	@ReportField(title = "分润状态", dict="SHAR_STATUS")
//	private String sharStatus;
	
	@ReportField(title = "支付订单号")
	private String payNo;    
	
	@ReportField(title = "交易金额")
	private String payAmt; 
	
	@ReportField(title = "交易费率")
	private String payRate; 
	
	@ReportField(title = "交易手续费")
	private String payFee;
	
	@ReportField(title = "代理商费率")
	private String agentRate;
	
	@ReportField(title = "代理商手续费")
	private String agentFee;

	@ReportField(title = "分润类型")
	private String payType;
	
//	@ReportField(title = "代理商分润比")
//	private String sharProfitRatio;
	
	
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
	public String getSharDate() {
		return sharDate;
	}
	public void setSharDate(String sharDate) {
		this.sharDate = sharDate;
	}
	public String getAgentDgr() {
		return agentDgr;
	}
	public void setAgentDgr(String agentDgr) {
		this.agentDgr = agentDgr;
	}
	public String getSharAmt() {
		String yuan="";
		if(sharAmt!=null && !sharAmt.equals("0")){
			yuan=MoneyUtils.toStrYuan(sharAmt);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setSharAmt(String sharAmt) {
		this.sharAmt = sharAmt;
	}
//	public String getSharStatus() {
//		return sharStatus;
//	}
//	public void setSharStatus(String sharStatus) {
//		this.sharStatus = sharStatus;
//	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getPayAmt() {
		String yuan="";
		if(payAmt!=null && !payAmt.equals("0")){
			yuan=MoneyUtils.toStrYuan(payAmt);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getPayRate() {
		return payRate;
	}
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	public String getPayFee() {
		String yuan="";
		if(payFee!=null && !payFee.equals("0")){
			yuan=MoneyUtils.toStrYuan(payFee);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getAgentRate() {
		return agentRate;
	}
	public void setAgentRate(String agentRate) {
		this.agentRate = agentRate;
	}
	public String getAgentFee() {
		String yuan="";
		if(agentFee!=null && !agentFee.equals("0")){
			yuan=MoneyUtils.toStrYuan(agentFee);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setAgentFee(String agentFee) {
		this.agentFee = agentFee;
	}
//	public String getSharProfitRatio() {
//		return sharProfitRatio;
//	}
//	public void setSharProfitRatio(String sharProfitRatio) {
//		this.sharProfitRatio = sharProfitRatio;
//	}


}
