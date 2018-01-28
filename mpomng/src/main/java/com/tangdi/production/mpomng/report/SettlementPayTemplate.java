package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 清算汇款业务报表模版
 * 
 * @author sunhaining
 *
 */
@ReportHead(display = true)
public class SettlementPayTemplate {
	@ReportField(title = "商户名称")
	private String custName;

	@ReportField(title = "商户序号")
	private String custId;
	
	@ReportField(title = "代理商名称")
	private String agentName;

	@ReportField(title = "代理商序号")
	private String agentId;

	@ReportField(title = "结算金额")
	private String amt;

	@ReportField(title = "开户行省 ")
	private String province;

	@ReportField(title = "开户行市")
	private String city;

	@ReportField(title = "开户行联行号")
	private String cnapsCode;

	@ReportField(title = "开户银行")
	private String bankName;
	
	@ReportField(title = "开户支行")
	private String subBranch;

	@ReportField(title = "对公/对私")
	private String type;

	@ReportField(title = "开户名称")
	private String cardName;

	@ReportField(title = "结算账号")
	private String cardNo;
	
	@ReportField(title = "完成时间")
	private String sucDate;
	
	@ReportField(title = "支付方式",dict="PAYTYPE")
	private String acType;

	public String getSucDate() {
		return sucDate;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	
	public void setSucDate(String sucDate) {
		this.sucDate = sucDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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
}
