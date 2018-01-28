package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author 
 * @version 1.0
 */
public class RecInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  第三方标识
	 */
	private String bkcode;
	/**
	 *  商户号
	 */
	private String merno;
	/**
	 *  终端号
	 */
	private String terno;
	/**
	 *  交易时间
	 */
	private String trantime;
	/**
	 *  支付订单号
	 */
	private String locpayno;
	/**
	 *  交易流水
	 */
	private String transeq;
	/**
	 *  转出卡卡号
	 */
	private String cardno;
	/**
	 *  缴费号
	 */
	private String payno;
	/**
	 *  交易金额 单位：分
	 */
	private String payamt;
	/**
	 *  结算金额 单位：分
	 */
	private String incamt;
	/**
	 *  结算日期
	 */
	private String settledt;
	/**
	 *  检索参考号
	 */
	private String refno;
	/**
	 *  交易类型：1消费 2代付 
	 */
	private String trantype;
	/**
	 *  疑账类型：01 银行有-系统无，02系统有-银行无
	 */
	private String ckdoubttype;
	
	/**
	 *  对账次数
	 */
	private String cknum;
	/**
	 *  对账日期
	 */
	private String ckdt;
	/**
	 *  对账状态
	 */
	private String recstatus;
	public String getBkcode() {
		return bkcode;
	}
	public void setBkcode(String bkcode) {
		this.bkcode = bkcode;
	}
	public String getMerno() {
		return merno;
	}
	public void setMerno(String merno) {
		this.merno = merno;
	}
	public String getTerno() {
		return terno;
	}
	public void setTerno(String terno) {
		this.terno = terno;
	}
	public String getTrantime() {
		return trantime;
	}
	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}
	public String getLocpayno() {
		return locpayno;
	}
	public void setLocpayno(String locpayno) {
		this.locpayno = locpayno;
	}
	public String getTranseq() {
		return transeq;
	}
	public void setTranseq(String transeq) {
		this.transeq = transeq;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getPayno() {
		return payno;
	}
	public void setPayno(String payno) {
		this.payno = payno;
	}
	public String getPayamt() {
		return payamt;
	}
	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}
	public String getIncamt() {
		return incamt;
	}
	public void setIncamt(String incamt) {
		this.incamt = incamt;
	}
	public String getSettledt() {
		return settledt;
	}
	public void setSettledt(String settledt) {
		this.settledt = settledt;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	public String getTrantype() {
		return trantype;
	}
	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}
	public String getCkdoubttype() {
		return ckdoubttype;
	}
	public void setCkdoubttype(String ckdoubttype) {
		this.ckdoubttype = ckdoubttype;
	}
	public String getCknum() {
		return cknum;
	}
	public void setCknum(String cknum) {
		this.cknum = cknum;
	}
	public String getCkdt() {
		return ckdt;
	}
	public void setCkdt(String ckdt) {
		this.ckdt = ckdt;
	}
	public String getRecstatus() {
		return recstatus;
	}
	public void setRecstatus(String recstatus) {
		this.recstatus = recstatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}