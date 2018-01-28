package com.tangdi.production.mprcs.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 交易记录实体bean
 * @author huchunyuan
 * @version 1.0
 *
 */
public class TranSerialRecordInf extends BaseBean {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String recId;
	/**
	 * 业务类型  00：所有，01：收款,02:消费,03:提现
	 */
	private String busType;
	/**
	 *子业务类型
	 */
	private String subBus;
	/**
	 * 支付方式：01 支付账户   02 终端   03 快捷支付
	 */
	private String payWay;
	/**
	 * 该笔交易所属代理商编号
	 */
	private String agentId;
	/**
	 * 交易用户编号(手刷商户编号)
	 */
	private String custId;
	/**
	 * 交易用户名称(手刷商户名称)
	 */
	private String custName;
	/**
	 * 消费终端号（手刷商户终端编号）
	 */
	private String terId;
	/**
	 * 交易银行卡号
	 */
	private String bankCardNo;
	/**
	 * 卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡
	 */
	private String cardType;
	/**
	 * 交易金额，单位：分
	 */
	private String tranAmt;
	/**
	 * 系统交易年份
	 */
	private String tarnYear;
	/**
	 * 系统交易月份
	 */
	private String tarnMonth;
	/**
	 * 系统交易日
	 */
	private String tarnDay;
	/**
	 * 系统交易时间
	 */
	private String tarnTime;
	/**
	 * 系统交易类型
	 */
	private String prdOrdType;
	
	/**
	 * 交易编号，收款存支付订单号，提现可存提现订单号
	 */
	private String tranCode;
	/**
	 * 交易状态，0：失败，1：成功，2：退货(交易成功但退款后，应该修改该交易记录状态）
	 */
	private String tranState;
	/**
	 * 合作机构编号
	 */
	private String cooporgNo;
	/**
	 * 合作机构M编码
	 */
	private String merId;
	/**
	 * 合作机构M编码
	 */
	private String merType;
	/**
	 * 合作机构商户号
	 */
	private String merNo;
	/**
	 * 合作机构商户名称
	 */
	private String merName;
	/**
	 * 合作机构终端号
	 */
	private String terNo;
	/**
	 * 合作机构商户费率类型
	 */
	private String merRateType;
	/**
	 * 合作机构商户费率
	 */
	private String merRate;
	/**
	 * 合作机构商户费率-封顶
	 */
	private String merRateTop;
	/**
	 * 商户费率类型
	 */
	private String tranRateType;
	/**
	 * 商户费率
	 */
	private String tranRate;
	/**
	 * 商户费率-封顶
	 */
	private String tranRateTop;
	/**
	 * 第三方交易日期
	 */
	private String thirdTranDate;
	/**
	 * 第三方交易时间
	 */
	private String thirdTranTime;
	/**
	 * 合作机构手续费=合作机构费率*交易金额
	 */
	private String merFee;
	/**
	 * 合作机构商户应结算金额=交易金额-合作机构费率*交易金额
	 */
	private String merSettleAmt;
	/**
	 * 商户手续费=商户费率*交易金额
	 */
	private String tranFee;
	/**
	 * 商户应结算金额=交易金额-商户费率*交易金额
	 */
	private String traSettleAmt;
	/**
	 * 交易日期（报表下载）
	 * */
	private String prddate;
	/**
	 * 一级代理商
	 * */
	private String firstAgentId;
	/**
	 * 一级代理商名称
	 * */
	private String firstAgentName;
	/**
	 * 代理商名称
	 * */
	private String agentName;
	
	/**
	 * 终端号
	 * */
	private String terminalNO;
	/**
	 * 订单状态
	 * */
	private String ordStatus;
	/**
	 * 交易状态
	 * */
	private String payStatus;
	
	/**
	 * 通道ID
	 * */
	private String payChannel;
	/**
	 * 通道名称
	 * */
	private String payName;
	/**
	 * 交易费率
	 * */
	private String rate;
	/**
	 * 交易类型
	 * */
	private String rateType;
	
	/**
	 * 交易金额/元
	 * */
	private String txAmt;
	/**
	 * 交易手续费/元
	 * */
	private String fee;
	/**
	 * 实际金额/元
	 * */
	private String netrecAmt;
	/**
	 * 提现日期
	 * */
	private String txDate;

	/**
	 * 提现类型
	 * */
	private String casType;
	
	private String bizType;
	private String acType;
	
	private String province;
	private String city;
	private String address;
	private String cnapsCode;
	private String issnam;
	private String time;
	private String stime;
	private String etime;
	
	
	
	
	public String getAcType() {
		return acType;
	}
	public void setAcType(String actype) {
		this.acType = actype;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getPrdno() {
		return prdno;
	}
	public void setPrdno(String prdno) {
		this.prdno = prdno;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	/**
	 * 收款订单号
	 */
	private String prdno;
	/**
	 * 检索参考号
	 */
	private String refno;
	
	/**
	 * 支行名称
	 */
	private String subBranch;
	/**
	 * 开户名称
	 */
	private String bankCustName;
	
	/**
	 * 封顶费率
	 */
	private String rateGeneralTop;
	/**
	 * 封顶金额
	 */
	private String rateGeneralMaximun;
	
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getSubBus() {
		return subBus;
	}
	public void setSubBus(String subBus) {
		this.subBus = subBus;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
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
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTerId() {
		return terId;
	}
	public void setTerId(String terId) {
		this.terId = terId;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getTarnYear() {
		return tarnYear;
	}
	public void setTarnYear(String tarnYear) {
		this.tarnYear = tarnYear;
	}
	public String getTarnMonth() {
		return tarnMonth;
	}
	public void setTarnMonth(String tarnMonth) {
		this.tarnMonth = tarnMonth;
	}
	public String getTarnDay() {
		return tarnDay;
	}
	public void setTarnDay(String tarnDay) {
		this.tarnDay = tarnDay;
	}
	public String getTarnTime() {
		return tarnTime;
	}
	public void setTarnTime(String tarnTime) {
		this.tarnTime = tarnTime;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTranState() {
		return tranState;
	}
	public void setTranState(String tranState) {
		this.tranState = tranState;
	}
	public String getCooporgNo() {
		return cooporgNo;
	}
	public void setCooporgNo(String cooporgNo) {
		this.cooporgNo = cooporgNo;
	}
	
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getMerRateType() {
		return merRateType;
	}
	public void setMerRateType(String merRateType) {
		this.merRateType = merRateType;
	}
	public String getMerRate() {
		return merRate;
	}
	public void setMerRate(String merRate) {
		this.merRate = merRate;
	}
	public String getMerRateTop() {
		return merRateTop;
	}
	public void setMerRateTop(String merRateTop) {
		this.merRateTop = merRateTop;
	}
	public String getTranRateType() {
		return tranRateType;
	}
	public void setTranRateType(String tranRateType) {
		this.tranRateType = tranRateType;
	}
	public String getTranRate() {
		return tranRate;
	}
	public void setTranRate(String tranRate) {
		this.tranRate = tranRate;
	}
	public String getTranRateTop() {
		return tranRateTop;
	}
	public void setTranRateTop(String tranRateTop) {
		this.tranRateTop = tranRateTop;
	}
	public String getThirdTranDate() {
		return thirdTranDate;
	}
	public void setThirdTranDate(String thirdTranDate) {
		this.thirdTranDate = thirdTranDate;
	}
	public String getThirdTranTime() {
		return thirdTranTime;
	}
	public void setThirdTranTime(String thirdTranTime) {
		this.thirdTranTime = thirdTranTime;
	}
	public String getMerFee() {
		return merFee;
	}
	public void setMerFee(String merFee) {
		this.merFee = merFee;
	}
	public String getMerSettleAmt() {
		return merSettleAmt;
	}
	public void setMerSettleAmt(String merSettleAmt) {
		this.merSettleAmt = merSettleAmt;
	}
	public String getTranFee() {
		return tranFee;
	}
	public void setTranFee(String tranFee) {
		this.tranFee = tranFee;
	}
	public String getTraSettleAmt() {
		return traSettleAmt;
	}
	public void setTraSettleAmt(String traSettleAmt) {
		this.traSettleAmt = traSettleAmt;
	}
	
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getPrdOrdType() {
		return prdOrdType;
	}
	public void setPrdOrdType(String prdOrdType) {
		this.prdOrdType = prdOrdType;
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
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		String addr = "";
		if(province != null && !province.equals("")){
			addr = province;
		}
		if(city != null && !city.equals("")){
			addr += ", "+ city;
		}
		return addr;
	}
	public String getTime() {
		String addr = "";
		if(tarnYear != null && !tarnYear.equals("")){
			addr =tarnYear;
		}
		if(tarnMonth != null && !tarnMonth.equals("")){
			addr +=tarnMonth;
		}
		if(tarnDay != null && !tarnDay.equals("")){
			addr +=tarnDay;
		}
		return addr;
	}
	public String getCnapsCode() {
		return cnapsCode;
	}
	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}
	public String getIssnam() {
		return issnam;
	}
	public void setIssnam(String issnam) {
		this.issnam = issnam;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getSubBranch() {
		return subBranch;
	}
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBankCustName() {
		return bankCustName;
	}
	public void setBankCustName(String bankCustName) {
		this.bankCustName = bankCustName;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
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
	public String getPrddate() {
		return prddate;
	}
	public void setPrddate(String prddate) {
		this.prddate = prddate;
	}
	public String getFirstAgentId() {
		return firstAgentId;
	}
	public String getFirstAgentName() {
		return firstAgentName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public void setFirstAgentName(String firstAgentName) {
		this.firstAgentName = firstAgentName;
	}
	public void setFirstAgentId(String firstAgentId) {
		this.firstAgentId = firstAgentId;
	}
	public String getTerminalNO() {
		return terminalNO;
	}
	public void setTerminalNO(String terminalNO) {
		this.terminalNO = terminalNO;
	}
	public String getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
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
	
	public String getTxDate() {
		return txDate;
	}
	public void txDate(String txDate) {
		this.txDate = txDate;
	}
	public String getCasType() {
		return casType;
	}
	public void setCasType(String casType) {
		this.casType = casType;
	}
 }