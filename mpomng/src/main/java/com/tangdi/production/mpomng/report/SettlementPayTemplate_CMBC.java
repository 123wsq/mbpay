package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 民生银行-清算汇款业务报表模版
 * 
 * @author sunhaining
 *
 */
@ReportHead(display = true)
public class SettlementPayTemplate_CMBC {
	
	@ReportField(title = "*收款方户名")
	private String cardName;
	
	@ReportField(title = "*金额")
	private String amt;
	
	@ReportField(title = "*收款方银行账号")
	private String cardNo;
	
	@ReportField(title = "*收款方银行编码")
	private String bankCode;
	
	@ReportField(title = "*开户行所在省")
	private String provinceCode;
	
	@ReportField(title = "*开户行所在市")
	private String cityCode;
	
	@ReportField(title = "*开户行支行名称")
	private String brchBnkName;
	
	@ReportField(title = "开户行支行编码")
	private String cnapsCode;
	
	@ReportField(title = "*对公（2）、对私（1）")
	private String type;
	
	@ReportField(title = "付款摘要")
	private String summary;
	
	@ReportField(title = "交易方式"  ,dict="PAYTYPE")
	private String acType;
	
	
	
	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBrchBnkName() {
		return brchBnkName;
	}

	public void setBrchBnkName(String brchBnkName) {
		this.brchBnkName = brchBnkName;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
