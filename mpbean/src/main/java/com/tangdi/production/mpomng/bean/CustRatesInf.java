package com.tangdi.production.mpomng.bean;

public class CustRatesInf {

	//商户代理商编号  00表示代理商   01  表示商户
	private String rateCode;
	
	//商户ID
	private String custId;
	
	//业务类型
	private String rateType;
	
	//T0提现费率
	private String rateTCas;
	
	//T0提现费率封顶 分，暂保留
	private String rateTCasMax;
	
	//T0单笔提现费率
	private String maxTCas;
	
	//民生类
	private String rateLivelihood;
	
	//一般类
	private String rateGeneral;
	
	//餐娱类
	private String rateEntertain;
	
	//批发类
	private String rateGeneralTop;
	
	//批发类封顶
	private String rateGeneralMaximun;
	
	//房产类
	private String rateEntertainTop;
	
	//房产类封顶
	private String rateEntertainMaximun;

	//更新时间
	private String updateTime;
	
	//插入时间
	private String insertTime;
	
	
	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateTCas() {
		return rateTCas;
	}

	public void setRateTCas(String rateTCas) {
		this.rateTCas = rateTCas;
	}

	
	public String getRateTCasMax() {
		return rateTCasMax;
	}

	public void setRateTCasMax(String rateTCasMax) {
		this.rateTCasMax = rateTCasMax;
	}

	public String getMaxTCas() {
		return maxTCas;
	}

	public void setMaxTCas(String maxTCas) {
		this.maxTCas = maxTCas;
	}

	public String getRateLivelihood() {
		return rateLivelihood;
	}

	public void setRateLivelihood(String rateLivelihood) {
		this.rateLivelihood = rateLivelihood;
	}

	public String getRateGeneral() {
		return rateGeneral;
	}

	public void setRateGeneral(String rateGeneral) {
		this.rateGeneral = rateGeneral;
	}

	public String getRateEntertain() {
		return rateEntertain;
	}

	public void setRateEntertain(String rateEntertain) {
		this.rateEntertain = rateEntertain;
	}

	public String getRateGeneralTop() {
		return rateGeneralTop;
	}

	public void setRateGeneralTop(String rateGeneralTop) {
		this.rateGeneralTop = rateGeneralTop;
	}

	public String getRateGeneralMaximun() {
		return rateGeneralMaximun;
	}

	public void setRateGeneralMaximun(String rateGeneralMaximun) {
		this.rateGeneralMaximun = rateGeneralMaximun;
	}

	public String getRateEntertainTop() {
		return rateEntertainTop;
	}

	public void setRateEntertainTop(String rateEntertainTop) {
		this.rateEntertainTop = rateEntertainTop;
	}

	public String getRateEntertainMaximun() {
		return rateEntertainMaximun;
	}

	public void setRateEntertainMaximun(String rateEntertainMaximun) {
		this.rateEntertainMaximun = rateEntertainMaximun;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
