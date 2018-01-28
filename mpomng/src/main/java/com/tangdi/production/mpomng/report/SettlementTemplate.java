package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 清算统计报表模版
 * @author zhengqiang
 *
 */
@ReportHead(display=true)
public class SettlementTemplate {
	
	@ReportField(title="交易类型" ,dict="CASTYPE")
	private String casType ;
	
	@ReportField(title="交易时间")
	private String casDate;
	
	@ReportField(title="商户编号")
	private String custId;
	
	@ReportField(title="终端号")
	private String terNo;
	
	@ReportField(title="收款账号名称")
	private String custName;
	
	@ReportField(title="收款人账号 ")
	private String cardNo;//00:未处理 01:成功 02:失败 03:可疑 04:处理中 05:已取消 
	
	@ReportField(title="收款账号开户行名称")
	private String subBranch;  //支行名称
	
	@ReportField(title="刷卡卡号")
	private String txnCardNo;
	
	@ReportField(title="参考号")
	private String tsrefNo;
	
	@ReportField(title="授权号")
	private String tautCod;
	
	@ReportField(title="终端流水号")
	private String tlogNo;
	
	@ReportField(title="交易金额")
	private String txAmt;
	
	
	@ReportField(title="手续费 ")
	private String fee;
	
	@ReportField(title="结算金额")
	private String netrecAmt;
	
	private String cnapsCode;
	
	private String rate;     //费率
	private String rateType; //费率类型
	
	private String casOrdNo;
	
	private String issnam;
	
	
	public String getIssnam() {
		return issnam;
	}

	public void setIssnam(String issnam) {
		this.issnam = issnam;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
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

	public String getTxnCardNo() {
		return txnCardNo;
	}

	public void setTxnCardNo(String txnCardNo) {
		this.txnCardNo = txnCardNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTerNo() {
		return terNo;
	}

	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	public String getTsrefNo() {
		return tsrefNo;
	}

	public void setTsrefNo(String tsrefNo) {
		this.tsrefNo = tsrefNo;
	}

	public String getTautCod() {
		return tautCod;
	}

	public void setTautCod(String tautCod) {
		this.tautCod = tautCod;
	}

	public String getTlogNo() {
		return tlogNo;
	}

	public void setTlogNo(String tlogNo) {
		this.tlogNo = tlogNo;
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

	public String getCasOrdNo() {
		return casOrdNo;
	}

	public void setCasOrdNo(String casOrdNo) {
		this.casOrdNo = casOrdNo;
	}
	
	
}
