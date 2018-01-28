package com.tangdi.production.mpamng.bean;

public class AgentProfitLogInf {
	/***
	 * 代理商编号
	 */
	private String agentId;
	
	/***
	 * 代理商名称
	 */
	private String agentName;
	/***
	 * 分润年月
	 */
	private String sharDate;
	/***
	 * 月分润金额
	 */
	private String sharAmt;
	/***
	 * 月交易总额
	 */
	private String payAmt;
	/***
	 * 当月分润比例
	 */
	private String rate;
	/***
	 * 当月分润状态
	 */
	private String status;
	/***
	 * 处理日期
	 */
	private String updateTime;
	/***
	 * 处理人
	 */
	private String audituser;
	
	private String sTime;
	
	private String eTime;
	
	/***
	 * 手续费
	 */
	private String payFee;
	/***
	 * 代理商等级
	 */
	private String agentDgr;
	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getAgentDgr() {
		return agentDgr;
	}

	public void setAgentDgr(String agentDgr) {
		this.agentDgr = agentDgr;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getSharDate() {
		return sharDate;
	}

	public void setSharDate(String sharDate) {
		this.sharDate = sharDate;
	}

	public String getSharAmt() {
		return sharAmt;
	}

	public void setSharAmt(String sharAmt) {
		this.sharAmt = sharAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		if(sTime != null && !sTime.equals("") && sTime.length() <=7){
			this.sTime = sTime.substring(0,4);
			this.sTime=this.sTime+sTime.substring(5,7);
		}else{
			this.sTime = sTime;
		}
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		if(eTime != null && !eTime.equals("") && eTime.length() <=7){
			this.eTime = eTime.substring(0,4);
			this.eTime=this.eTime+eTime.substring(5,7);
		}else{
			this.eTime = eTime;
		}
	}
	
	
}
