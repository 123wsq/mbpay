package com.tangdi.production.mpomng.report;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 交易明细业务报表模版
 * @author zhengqiang
 *
 */
@ReportHead(display=true)
public class TransactionTemplate {
	
	@ReportField(title="收单系统名称")
	private String merName;
	
	@ReportField(title="收单系统商户序号")
	private String merId;
	
	@ReportField(title="合作机构商户所属行业")
	private String merType;
	
	@ReportField(title="合作机构商户费率")
	private String merRate;
	
	@ReportField(title="合作机构商户费率封顶")
	private String merRateTop;
	
	@ReportField(title="手刷商户编号")
	private String custId;
	
	@ReportField(title="手刷商户名称")
	private String custName;
	
	@ReportField(title="手刷商户费率")
	private String tranRate;
	
	@ReportField(title="手刷商户费率封顶")
	private String tranRateTop;
	
	@ReportField(title="交易日期")
	private String thirdTranDate;
	
	@ReportField(title="交易时间")
	private String thirdTranTime;
	
	@ReportField(title="交易类型",dict="PRDORDTYPE")
	private String prdOrdType;
	
	@ReportField(title="交易金额(元)")
	private String tranAmt;
	
	@ReportField(title="交易状态",dict="tranState")
	private String tranState;
	
	@ReportField(title="合作机构手续费")
	private String merFee;
	
	@ReportField(title="合作机构商户应结算金额")
	private String merSettleAmt;
	
	@ReportField(title="手刷商户手续费")
	private String tranFee;
	
	@ReportField(title="手刷商户应结算金额")
	private String traSettleAmt;
	
	@ReportField(title="卡类型",dict="BANKCARDTYPE")
	private String cardType;
	
	@ReportField(title="开户银行省市")
	private String address;
	
	@ReportField(title="开户银行联行号")
	private String cnapsCode;
	
	@ReportField(title="开户银行")
	private String subBranch;
	
	@ReportField(title="开户名称")
	private String bankCustName;
	
	@ReportField(title="结算帐号")
	private String bankCardNo;
	
	private String province;
	private String city;
	
	@ReportField(title="支付方式" ,dict="PAYTYPE")
	private String payWay;
	
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
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
		String tmpDate ;
		if(thirdTranDate == null || thirdTranDate.length() != 4 ){
			return  "";
		}
		tmpDate = thirdTranDate.substring(0,2) + "-";
		tmpDate = tmpDate + thirdTranDate.substring(2,  4) + "";
		return tmpDate;
	}
	public void setThirdTranDate(String thirdTranDate) {
		this.thirdTranDate = thirdTranDate;
	}
	public String getThirdTranTime() {
		String tmpDate;
		if(thirdTranTime == null || thirdTranTime.length() != 6 ){
			return  "";
		}
		tmpDate = thirdTranTime.substring(0,2) + ":";
		tmpDate = tmpDate + thirdTranTime.substring(2,4) + ":";
		tmpDate = tmpDate + thirdTranTime.substring(4);
		return tmpDate;
	}
	public void setThirdTranTime(String thirdTranTime) {
		this.thirdTranTime = thirdTranTime;
	}
	public String getPrdOrdType() {
		return prdOrdType;
	}
	public void setPrdOrdType(String prdOrdType) {
		this.prdOrdType = prdOrdType;
	}
	public String getTranAmt() {
		String yuan="";
		if(tranAmt!=null && !tranAmt.equals("0")){
			yuan=MoneyUtils.toStrYuan(tranAmt);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getTranState() {
		return tranState;
	}
	public void setTranState(String tranState) {
		this.tranState = tranState;
	}
	public String getMerFee() {
		String yuan="";
		if(merFee!=null && !merFee.equals("0")){
			yuan=MoneyUtils.toStrYuan(merFee);
		}else {
			yuan="0.00";
		}
		return yuan;
	}
	public void setMerFee(String merFee) {
		this.merFee = merFee;
	}
	public String getMerSettleAmt() {
		String yuan="";
		if(merSettleAmt!=null && !merSettleAmt.equals("0")){
			yuan=MoneyUtils.toStrYuan(merSettleAmt);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setMerSettleAmt(String merSettleAmt) {
		this.merSettleAmt = merSettleAmt;
	}
	public String getTranFee() {
		String yuan="";
		if(tranFee!=null && !tranFee.equals("0")){
			yuan=MoneyUtils.toStrYuan(tranFee);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setTranFee(String tranFee) {
		this.tranFee = tranFee;
	}
	public String getTraSettleAmt() {
		String yuan="";
		if(traSettleAmt!=null && !traSettleAmt.equals("0")){
			yuan=MoneyUtils.toStrYuan(traSettleAmt);
		}else{
			yuan="0.00";
		}
		return yuan;
	}
	public void setTraSettleAmt(String traSettleAmt) {
		this.traSettleAmt = traSettleAmt;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCnapsCode() {
		return cnapsCode;
	}
	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}
	public String getSubBranch() {
		return subBranch;
	}
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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
	public String getBankCustName() {
		return bankCustName;
	}
	public void setBankCustName(String bankCustName) {
		this.bankCustName = bankCustName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
