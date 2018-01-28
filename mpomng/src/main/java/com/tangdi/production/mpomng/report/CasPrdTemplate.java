package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 提现订单业务报表模版
 * @author zhengqiang
 *
 */
@ReportHead(display=true)
public class CasPrdTemplate {
	@ReportField(title="提现订单号")
	private String casOrdNo ;
	
	@ReportField(title="提现类型",dict="CASTYPE")
	private String casType;
	
	@ReportField(title="下单时间")
	private String casDate;
	
	@ReportField(title="最后操作时间")
	private String modifyDate;
	
	@ReportField(title="订单完成时间")
	private String sucDate;
	
	@ReportField(title="订单状态 ",dict="CASORDSTATUS")
	private String ordStatus;//00:未处理 01:成功 02:失败 03:可疑 04:处理中 05:已取消 
	
	@ReportField(title="商户编号")
	private String custId;
	
	@ReportField(title="商户名称")
	private String custName;
	
	@ReportField(title="订单金额")
	private String txamt;
	
	@ReportField(title="费率")
	private String rate;
	
	@ReportField(title="手续费")
	private String fee;
	
	
	@ReportField(title="服务费 ")
	private String serviceFee;
	
	
	@ReportField(title="实际到账金额")
	private String netrecAmt;
	
	@ReportField(title="发卡行代码")
	private String issno;
	
	@ReportField(title="发卡行名称")
	private String issNam;
	
	@ReportField(title="提现银行卡号")
	private String cardNo;
	
	@ReportField(title="提现描述")
	private String casDesc;
	
	@ReportField(title="提现审核状态" ,dict="CASAUDIT")
	private String casAudit;
	
	@ReportField(title="审核描述")
	private String auditDesc;
	
	@ReportField(title="T0部分")
	private String t0Amt;
	
	@ReportField(title="T1部分")
	private String t1Amt;
	
	private String casRel;
	public String getCasOrdNo() {
		return casOrdNo;
	}
	public void setCasOrdNo(String casOrdNo) {
		this.casOrdNo = casOrdNo;
	}
	public String getCasType() {
		return casType;
	}
	public void setCasType(String casType) {
		this.casType = casType;
	}
	public String getCasDate() {
		return casDate;
	}
	public void setCasDate(String casDate) {
		this.casDate = casDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getSucDate() {
		return sucDate;
	}
	public void setSucDate(String sucDate) {
		this.sucDate = sucDate;
	}
	public String getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
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
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getNetrecAmt() {
		return netrecAmt;
	}
	public void setNetrecAmt(String netrecAmt) {
		this.netrecAmt = netrecAmt;
	}
	public String getIssno() {
		return issno;
	}
	public void setIssno(String issno) {
		this.issno = issno;
	}
	public String getIssNam() {
		return issNam;
	}
	public void setIssNam(String issNam) {
		this.issNam = issNam;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCasDesc() {
		return casDesc;
	}
	public void setCasDesc(String casDesc) {
		this.casDesc = casDesc;
	}
	public String getCasAudit() {
		return casAudit;
	}
	public void setCasAudit(String casAudit) {
		this.casAudit = casAudit;
	}
	public String getAuditDesc() {
		return auditDesc;
	}
	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	public String getCasRel() {
		return casRel;
	}
	public void setCasRel(String casRel) {
		this.casRel = casRel;
	}
	public String getTxamt() {
		return txamt;
	}
	public void setTxamt(String txamt) {
		this.txamt = txamt;
	}
	public String getT0Amt() {
		return t0Amt;
	}
	public void setT0Amt(String t0Amt) {
		this.t0Amt = t0Amt;
	}
	public String getT1Amt() {
		return t1Amt;
	}
	public void setT1Amt(String t1Amt) {
		this.t1Amt = t1Amt;
	}
	
	
}
