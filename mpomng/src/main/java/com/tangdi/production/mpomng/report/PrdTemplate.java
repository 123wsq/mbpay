package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;


/**
 * 商品订单业务报表
 * @author 
 * @version 1.0
 */
@ReportHead(display=true)
public class PrdTemplate  {
	
	@ReportField(title="商品订单号")
	private String prdordno; //商品订单号
	
	@ReportField(title="订单类型 ",dict="PRDORDTYPE")
	private String prdordtype;//订单类型 01收款 02商品
	
	@ReportField(title="子业务类型",dict="BUSCODE")
	private String biztype; //子业务类型，01：手机充值 02 彩票
	
	@ReportField(title="订单状态 ",dict="ORDSTATUS")
	private String ordstatus ;//订单状态00:未交易 01:成功 02:失败 03:可疑 04:处理中 05:已取消 06:未支付 07:已退货 08:退货中 09:部分退货
	
	@ReportField(title="订单金额")
	private String ordamt ;//订单金额
	
	@ReportField(title="支付订单号")
	private String payordno ;//支付订单号
	
	@ReportField(title="商品单价")
	private String price ;//商品单价
	
	@ReportField(title="商品名称")
	private String goodsName;//商品名称
	
	@ReportField(title="商品简称")
	private String goodsNameShort;//商品简称
	
	@ReportField(title="商户编号")
	private String custId ;//商户编号
	
	@ReportField(title="商户名称")
	private String custName ;//商户名称
	
	@ReportField(title="订单日期")
	private String prddate;//订单日期
	
	@ReportField(title="订单时间")
	private String prdtime;//订单时间
	
	@ReportField(title="支付方式",dict="PAYTYPE")
	private String payType;//支付方式
	
	@ReportField(title="订单状态",dict="PAYSTATUS")
	private String payStatus;//订单状态，00:未支付 01:支付处理中02:支付成功03:支付失败 04:冲正中05:已冲正06:冲正失败07:订单作废98:超时
	
	@ReportField(title="支付终端号")
	private String terNo;//支付终端号
	
	@ReportField(title="订单金额")
	private String txAmt;//订单金额
	
	@ReportField(title="费率")
	private String rate;//费率
	
	@ReportField(title="费率类型",dict="RATETYPE")
	private String rateType;//费率类型
	
	@ReportField(title="手续费")
	private String fee;//手续费
	
	@ReportField(title="实际金额")
	private String netrecAmt;//实际金额
	
	@ReportField(title="支付渠道编号")
	private String payChannel;//支付渠道编号
	
	@ReportField(title="支付银行卡号")
	private String payCardNo;//支付银行卡号
	
	@ReportField(title="发卡行名称")
	private String issNam;//发卡行名称
	
	@ReportField(title="卡名称")
	private String crdNam; //卡名称
	
	@ReportField(title="签名照片")
	private String paySignPic; //签名照片
	
	@ReportField(title="支付日期")
	private String payDate; //支付日期
	
	@ReportField(title="支付时间")
	private String payTime; //支付时间
	
	@ReportField(title="批次号")
	private String txnSrefNo; //批次号
	
	@ReportField(title="接入方式")
	private String inMod; //接入方式
	
	@ReportField(title="交易日期")
	private String ttxndt; //第三方交易日期
	
	@ReportField(title="交易时间")
	private String ttxntm; //第三方交易时间
	
	@ReportField(title="商户号")
	private String tmercId; //第三方商户号
	
	@ReportField(title="终端号")
	private String ttermId;//第三方终端号
	
	@ReportField(title="批次号")
	private String tbatNo; //第三方批次号
	
	@ReportField(title="系统参考号")
	private String tsrefNo; //第三方系统参考号
	
	@ReportField(title="返回码")
	private String tcpscod;//第三方返回码
	
	@ReportField(title="授权码")
	private String tautcod; //第三方授权码
	
	@ReportField(title="交易流水号")
	private String tlogno;//第三方交易流水号
	
	@ReportField(title="交易类型",dict="TXNTYP")
	private String txnType;//交易类型 01 消费 02 消费撤销...

	public String getPrdordno() {
		return prdordno;
	}

	public void setPrdordno(String prdordno) {
		this.prdordno = prdordno;
	}

	public String getPrdordtype() {
		return prdordtype;
	}

	public void setPrdordtype(String prdordtype) {
		this.prdordtype = prdordtype;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getOrdstatus() {
		return ordstatus;
	}

	public void setOrdstatus(String ordstatus) {
		this.ordstatus = ordstatus;
	}

	public String getOrdamt() {
		return ordamt;
	}

	public void setOrdamt(String ordamt) {
		this.ordamt = ordamt;
	}

	public String getPayordno() {
		return payordno;
	}

	public void setPayordno(String payordno) {
		this.payordno = payordno;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNameShort() {
		return goodsNameShort;
	}

	public void setGoodsNameShort(String goodsNameShort) {
		this.goodsNameShort = goodsNameShort;
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

	public String getPrddate() {
		return prddate;
	}

	public void setPrddate(String prddate) {
		this.prddate = prddate;
	}

	public String getPrdtime() {
		return prdtime;
	}

	public void setPrdtime(String prdtime) {
		this.prdtime = prdtime;
	}
	public String getTerNo() {
		return terNo;
	}

	public void setTerNo(String terNo) {
		this.terNo = terNo;
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	
	public String getPaySignPic() {
		return paySignPic;
	}

	public void setPaySignPic(String paySignPic) {
		this.paySignPic = paySignPic;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	
	public String getTtxndt() {
		return ttxndt;
	}

	public void setTtxndt(String ttxndt) {
		this.ttxndt = ttxndt;
	}

	public String getTtxntm() {
		return ttxntm;
	}

	public void setTtxntm(String ttxntm) {
		this.ttxntm = ttxntm;
	}

	public String getTcpscod() {
		return tcpscod;
	}

	public void setTcpscod(String tcpscod) {
		this.tcpscod = tcpscod;
	}

	public String getTautcod() {
		return tautcod;
	}

	public void setTautcod(String tautcod) {
		this.tautcod = tautcod;
	}

	public String getTlogno() {
		return tlogno;
	}

	public void setTlogno(String tlogno) {
		this.tlogno = tlogno;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getTmercId() {
		return tmercId;
	}

	public void setTmercId(String tmercId) {
		this.tmercId = tmercId;
	}

	public String getTtermId() {
		return ttermId;
	}

	public void setTtermId(String ttermId) {
		this.ttermId = ttermId;
	}

	public String getTbatNo() {
		return tbatNo;
	}

	public void setTbatNo(String tbatNo) {
		this.tbatNo = tbatNo;
	}

	public String getTsrefNo() {
		return tsrefNo;
	}

	public void setTsrefNo(String tsrefNo) {
		this.tsrefNo = tsrefNo;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public String getNetrecAmt() {
		return netrecAmt;
	}

	public void setNetrecAmt(String netrecAmt) {
		this.netrecAmt = netrecAmt;
	}

	public String getPayCardNo() {
		return payCardNo;
	}

	public void setPayCardNo(String payCardNo) {
		this.payCardNo = payCardNo;
	}

	public String getIssNam() {
		return issNam;
	}

	public void setIssNam(String issNam) {
		this.issNam = issNam;
	}

	public String getCrdNam() {
		return crdNam;
	}

	public void setCrdNam(String crdNam) {
		this.crdNam = crdNam;
	}

	public String getInMod() {
		return inMod;
	}

	public void setInMod(String inMod) {
		this.inMod = inMod;
	}

	public String getTxnSrefNo() {
		return txnSrefNo;
	}

	public void setTxnSrefNo(String txnSrefNo) {
		this.txnSrefNo = txnSrefNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	

	
	
}