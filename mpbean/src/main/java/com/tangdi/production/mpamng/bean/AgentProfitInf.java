package com.tangdi.production.mpamng.bean;

/***
 * 代理商分润阶梯
 * 
 * @author sunhaining
 *
 */
public class AgentProfitInf {
	/***
	 * 代理商编号
	 */
	private String agentId;
	/**
	 * 费率
	 */
	private String rates;
	/***
	 * 起始金额
	 */
	private String beginNum;
	/***
	 * 结束金额
	 */
	private String endNum;
	/***
	 * 显示序号
	 */
	private String showNum;
	/***
	 * 状态
	 */
	private String status;
	/***
	 * 备注
	 */
	private String remark;
	/***
	 * 分润业务类型：02收单 03快捷 04扫码
	 */	
	private String rateType;
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getRates() {
		return rates;
	}

	public void setRates(String rates) {
		this.rates = rates;
	}

	public String getBeginNum() {
		return beginNum;
	}

	public void setBeginNum(String beginNum) {
		this.beginNum = beginNum;
	}

	public String getEndNum() {
		return endNum;
	}

	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
