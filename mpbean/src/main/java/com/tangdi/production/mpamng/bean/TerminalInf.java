package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class TerminalInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 终端ID(序列号)
	 */
	private String terminalId;

	/**
	 * 终端号
	 */
	private String terminalSeq;

	/**
	 * 终端物理号
	 */
	private String terminalNo;

	/**
	 * 代理商ID
	 */
	private String agentId;

	/**
	 * 商户ID
	 */
	private String custId;

	/**
	 * 终端类型
	 */
	private String terminalType;

	/**
	 * 终端厂商
	 */
	private String terminalCom;

	/**
	 * 终端状态 0 入库 1下拨 2绑定
	 */
	private String terminalStatus;

	/**
	 * 民生类 0.38%
	 */
	private String rateLivelihood;

	/**
	 * 一般类 0.78%
	 */
	private String rateGeneral;

	/**
	 * 批发类 0.78% 封顶
	 */
	private String rateGeneralTop;

	/**
	 * 批发类 0.78% 封顶金额 分
	 */
	private String rateGeneralMaximun;

	/**
	 * 餐娱类 1.25%
	 */
	private String rateEntertain;

	/**
	 * 房产汽车类 1.25% 封顶
	 */
	private String rateEntertainTop;

	/**
	 * 房产汽车类 1.25% 封顶金额 分
	 */
	private String rateEntertainMaximun;

	private Integer allocateType;
	private Integer termAllocateNum;

	private String agentName;
	private String custName;
	private String rateTCas;
	
	private String souAgentId; 
	
	private String msg;
	/**
	 * 单笔提现收费
	 */
	private String maxTCas;//hg add 20160408
	
	public String getMaxTCas() {
		return maxTCas;
	}

	public void setMaxTCas(String maxTCas) {
		this.maxTCas = maxTCas;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSouAgentId() {
		return souAgentId;
	}

	public void setSouAgentId(String souAgentId) {
		this.souAgentId = souAgentId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalSeq() {
		return terminalSeq;
	}

	public void setTerminalSeq(String terminalSeq) {
		this.terminalSeq = terminalSeq;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getTerminalCom() {
		return terminalCom;
	}

	public void setTerminalCom(String terminalCom) {
		this.terminalCom = terminalCom;
	}

	public String getTerminalStatus() {
		return terminalStatus;
	}

	public void setTerminalStatus(String terminalStatus) {
		this.terminalStatus = terminalStatus;
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

	public Integer getAllocateType() {
		return allocateType;
	}

	public void setAllocateType(Integer allocateType) {
		this.allocateType = allocateType;
	}

	public Integer getTermAllocateNum() {
		return termAllocateNum;
	}

	public void setTermAllocateNum(Integer termAllocateNum) {
		this.termAllocateNum = termAllocateNum;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRateTCas() {
		return rateTCas;
	}

	public void setRateTCas(String rateTCas) {
		this.rateTCas = rateTCas;
	}
}