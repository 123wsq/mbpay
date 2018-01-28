package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/***
 * 浠ｇ悊鍟嗗垎娑﹂樁姊�
 * 
 * @author 
 *
 */
public class AgentFeeInfo  extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8088246235342257451L;
	/**
	 * 00 浠ｇ悊鍟� 01鍟嗘埛
	 */
	private String rateCode;
	/**
	 * 璐圭巼ID 浠ｇ悊鍟嗘椂涓轰唬鐞嗗晢缂栧彿  鍟嗘埛鏃朵负鍟嗘埛缂栧彿
	 */
	private String rateId;	
	/**
	 * 涓氬姟绫诲瀷 02鏀跺崟 03蹇嵎 04鎵爜
	 */
	private String rateType;		
	/**
	 * T0鎻愮幇璐圭巼
	 */
	private String rateTCas;

	/**
	 * T0鎻愮幇鍗曠瑪璐圭敤
	 */
	private String maxTCas;
	/**
	 * 姘戠敓绫� 0.38%
	 */
	private String rateLivelihood;

	/**
	 * 涓�鑸被 0.78%
	 */
	private String rateGeneral;

	/**
	 * 鎵瑰彂绫� 0.78% 灏侀《
	 */
	private String rateGeneralTop;

	/**
	 * 鎵瑰彂绫� 0.78% 灏侀《閲戦 鍒�
	 */
	private String rateGeneralMaximun;

	/**
	 * 椁愬ū绫� 1.25%
	 */
	private String rateEntertain;

	/**
	 * 鎴夸骇姹借溅绫� 1.25% 灏侀《
	 */
	private String rateEntertainTop;
	/**
	 * 鎴夸骇姹借溅绫� 1.25% 灏侀《閲戦 鍒�
	 */
	private String rateEntertainMaximun;
	/**
	 * 娣诲姞鏃堕棿
	 */
	private String insTim;	
	/**
	 * 鏇存柊鏃堕棿
	 */
	private String ipdTim;
	
	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getInsTim() {
		return insTim;
	}

	public void setInsTim(String insTim) {
		this.insTim = insTim;
	}

	public String getIpdTim() {
		return ipdTim;
	}

	public void setIpdTim(String ipdTim) {
		this.ipdTim = ipdTim;
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

	public String getRateEntertain() {
		return rateEntertain;
	}

	public void setRateEntertain(String rateEntertain) {
		this.rateEntertain = rateEntertain;
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

	public String getRateTCas() {
		return rateTCas;
	}

	public void setRateTCas(String rateTCas) {
		this.rateTCas = rateTCas;
	}

	public String getMaxTCas() {
		return maxTCas;
	}

	public void setMaxTCas(String maxTCas) {
		this.maxTCas = maxTCas;
	}
}
