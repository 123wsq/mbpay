package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 */
public class PayMentJournalInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String paymentId;
	/**
	 * 支付订单号
	 */
	private String payordno;
	/**
	 * 交易日期
	 */
	private String txnDate;
	/**
	 * 交易时间
	 */
	private String txnTime;
	/**
	 * 交易类型 01 消费 02 消费撤销...
	 */
	private String txnType;
	/**
	 * 交易卡号
	 */
	private String txnActno;
	/**
	 * 交易金额
	 */
	private String txnAmt;
	/**
	 * 交易状态 0 预计 S成功 F失败 C冲正 R撤销 T超时 E完成
	 */
	private String txnstatus;
	/**
	 * 接入方式
	 */
	private String inmod;
	/**
	 * 第三方交易日期
	 */
	private String ttxndt;
	/**
	 * 第三方交易时间
	 */
	private String ttxntm;
	/**
	 * 第三方流水号
	 */
	private String tseqno;
	/**
	 * 第三方商户号
	 */
	private String tmercid;
	/**
	 * 第三方终端号
	 */
	private String ttermid;
	/**
	 * 第三方交易流水号
	 */
	private String tlogno;
	/**
	 * 第三方批次号
	 */
	private String tbatno;
	/**
	 * 第三方授权码
	 */
	private String tautcod;
	/**
	 *第三方操作员
	 */
	private String toprid;
	/**
	 * 第三方返回码
	 */
	private String tcpscod;
	/**
	 * 第三方系统参考号
	 */
	private String tsrefno;
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPayordno() {
		return payordno;
	}
	public void setPayordno(String payordno) {
		this.payordno = payordno;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnActno() {
		return txnActno;
	}
	public void setTxnActno(String txnActno) {
		this.txnActno = txnActno;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getTxnstatus() {
		return txnstatus;
	}
	public void setTxnstatus(String txnstatus) {
		this.txnstatus = txnstatus;
	}
	public String getInmod() {
		return inmod;
	}
	public void setInmod(String inmod) {
		this.inmod = inmod;
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
	public String getTseqno() {
		return tseqno;
	}
	public void setTseqno(String tseqno) {
		this.tseqno = tseqno;
	}
	public String getTmercid() {
		return tmercid;
	}
	public void setTmercid(String tmercid) {
		this.tmercid = tmercid;
	}
	public String getTtermid() {
		return ttermid;
	}
	public void setTtermid(String ttermid) {
		this.ttermid = ttermid;
	}
	public String getTlogno() {
		return tlogno;
	}
	public void setTlogno(String tlogno) {
		this.tlogno = tlogno;
	}
	public String getTbatno() {
		return tbatno;
	}
	public void setTbatno(String tbatno) {
		this.tbatno = tbatno;
	}
	public String getTautcod() {
		return tautcod;
	}
	public void setTautcod(String tautcod) {
		this.tautcod = tautcod;
	}
	public String getToprid() {
		return toprid;
	}
	public void setToprid(String toprid) {
		this.toprid = toprid;
	}
	public String getTcpscod() {
		return tcpscod;
	}
	public void setTcpscod(String tcpscod) {
		this.tcpscod = tcpscod;
	}
	public String getTsrefno() {
		return tsrefno;
	}
	public void setTsrefno(String tsrefno) {
		this.tsrefno = tsrefno;
	}

}