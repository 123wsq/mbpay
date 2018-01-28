package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 */
public class PrdInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品订单号
	 */
	private String prdordno;

	/**
	 * 订单类型 1收款 2商品
	 */
	private String prdordtype;

	/**
	 * 子业务类型，1：手机充值
	 */
	private String biztype;

	/**
	 * 订单状态，00:未支付 01:支付成功 02:支付失败 03:退款审核中 04:退款处理中 05:退款成功 06:退款失败 07:撤销审核中
	 * 08：同意撤销 09：撤销成功 10:撤销失败 11：订单作废 21：支付处理中 99:超时
	 */
	private String ordstatus;

	/**
	 * 订单金额，单位：分
	 */
	private String ordamt;

	/**
	 * 支付订单号(最后一次发起的支付订单)
	 */
	private String payordno;

	/**
	 * 商品单价，单位：分
	 */
	private String price;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品简称
	 */
	private String goodsNameShort;

	/**
	 * 发起商户ID
	 */
	private String custId;

	/**
	 * 订单日期
	 */
	private String prddate;

	/**
	 * 订单时间
	 */
	private String prdtime;

	/**
	 * 订单时间（年月日时分秒）
	 */
	private String ordtime;

	/**
	 * 最后更新时间
	 */
	private String modifyTime;
	/**
	 * 商户名称
	 */
	private String custName;
	/**
	 * 支付方式
	 */
	private String payType;

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

	public String getOrdtime() {
		return ordtime;
	}

	public void setOrdtime(String ordtime) {
		this.ordtime = ordtime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	
	
}