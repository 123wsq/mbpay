package com.tangdi.production.mpomng.bean;

public class Authentication {
	
	// 聚禾富通道 - 交易请求URL
	private String reqUrl;
	// 服务名称
	private String svcName;
	// 商户编号
	private String merId;
	// 交易时间
	private String tranTime;
	// 订单编号
	private String merchOrderId;
	// 卡折标志
	private String cardType;
	// 付款人账号
	private String payAcc;
	// MD5密钥
	private String key;

	/****** 以下字段可为空 （账户名、（证件类型、证件号码）、手机号码三选一必输）*******/
	// 付款人名称
	private String payName;
	// 开户证件类型
	private String identityType;
	// 证件号码
	private String identityCode;
	// 付款人手机号
	private String payPhone;
	// 付款行银联机构号
	private String payBankInsCode;
	// 付款省份编码
	private String provNo;
	// MD5校验码
	private String md5value;
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getMerchOrderId() {
		return merchOrderId;
	}
	public void setMerchOrderId(String merchOrderId) {
		this.merchOrderId = merchOrderId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPayAcc() {
		return payAcc;
	}
	public void setPayAcc(String payAcc) {
		this.payAcc = payAcc;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public String getPayPhone() {
		return payPhone;
	}
	public void setPayPhone(String payPhone) {
		this.payPhone = payPhone;
	}
	public String getPayBankInsCode() {
		return payBankInsCode;
	}
	public void setPayBankInsCode(String payBankInsCode) {
		this.payBankInsCode = payBankInsCode;
	}
	public String getProvNo() {
		return provNo;
	}
	public void setProvNo(String provNo) {
		this.provNo = provNo;
	}
	public String getMd5value() {
		return md5value;
	}
	public void setMd5value(String md5value) {
		this.md5value = md5value;
	}
}
