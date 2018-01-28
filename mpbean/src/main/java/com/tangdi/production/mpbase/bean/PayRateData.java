package com.tangdi.production.mpbase.bean;

import java.io.Serializable;

/**
 * 支付费率数据, 主要是统计PayFeeInf中的数据(与数据库表无关)</br>
 * 支持：CH01 一般类、CH02 民生类、CH03 批发类封顶手续费、CH04 餐娱类费率
 * 
 * @author zhengqiang
 *
 */
public class PayRateData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String feeCode;  //费率代码
	private String relType;  //关联类型  00 平台成本费率 01 代理商费成本率  02代理商下的商户费率 03 终端费率 04 商户费率
	private String relId;    //关联编码
	private String feeName;  //费率名称
	
	private String ch01;       //CH01 一般类
	private String percent01;  //比例
	private String feeLow01;   //最低
	private String feeTop01;   //封顶
	
	private String ch02;      //CH02 民生类
	private String percent02;  //比例
	private String feeLow02;   //最低
	private String feeTop02;   //封顶

	private String ch03;      //CH03 批发类封顶手续费
	private String pricing;   //定额
	
	private String ch04;   //CH04 餐娱类费率
	private String percent04;  //比例
	private String feeLow04;   //最低
	private String feeTop04;   //封顶
	
	private String ch05;   //CH05 备用
	private String ch06;   //CH06 备用
	private String ch07;   //CH07 备用
	private String uid ;   //用户编号
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getRelType() {
		return relType;
	}
	public void setRelType(String relType) {
		this.relType = relType;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getCh01() {
		return ch01;
	}
	public void setCh01(String ch01) {
		this.ch01 = ch01;
	}
	public String getPercent01() {
		return percent01;
	}
	public void setPercent01(String percent01) {
		this.percent01 = percent01;
	}
	public String getFeeLow01() {
		return feeLow01;
	}
	public void setFeeLow01(String feeLow01) {
		this.feeLow01 = feeLow01;
	}
	public String getFeeTop01() {
		return feeTop01;
	}
	public void setFeeTop01(String feeTop01) {
		this.feeTop01 = feeTop01;
	}
	public String getCh02() {
		return ch02;
	}
	public void setCh02(String ch02) {
		this.ch02 = ch02;
	}
	public String getPercent02() {
		return percent02;
	}
	public void setPercent02(String percent02) {
		this.percent02 = percent02;
	}
	public String getFeeLow02() {
		return feeLow02;
	}
	public void setFeeLow02(String feeLow02) {
		this.feeLow02 = feeLow02;
	}
	public String getFeeTop02() {
		return feeTop02;
	}
	public void setFeeTop02(String feeTop02) {
		this.feeTop02 = feeTop02;
	}
	public String getCh03() {
		return ch03;
	}
	public void setCh03(String ch03) {
		this.ch03 = ch03;
	}
	public String getPricing() {
		return pricing;
	}
	public void setPricing(String pricing) {
		this.pricing = pricing;
	}
	public String getCh04() {
		return ch04;
	}
	public void setCh04(String ch04) {
		this.ch04 = ch04;
	}
	public String getPercent04() {
		return percent04;
	}
	public void setPercent04(String percent04) {
		this.percent04 = percent04;
	}
	public String getFeeLow04() {
		return feeLow04;
	}
	public void setFeeLow04(String feeLow04) {
		this.feeLow04 = feeLow04;
	}
	public String getFeeTop04() {
		return feeTop04;
	}
	public void setFeeTop04(String feeTop04) {
		this.feeTop04 = feeTop04;
	}
	public String getCh05() {
		return ch05;
	}
	public void setCh05(String ch05) {
		this.ch05 = ch05;
	}
	public String getCh06() {
		return ch06;
	}
	public void setCh06(String ch06) {
		this.ch06 = ch06;
	}
	public String getCh07() {
		return ch07;
	}
	public void setCh07(String ch07) {
		this.ch07 = ch07;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "PayRateData [feeCode=" + feeCode + ", relType=" + relType
				+ ", relId=" + relId + ", feeName=" + feeName + ", ch01="
				+ ch01 + ", percent01=" + percent01 + ", feeLow01=" + feeLow01
				+ ", feeTop01=" + feeTop01 + ", ch02=" + ch02 + ", percent02="
				+ percent02 + ", feeLow02=" + feeLow02 + ", feeTop02="
				+ feeTop02 + ", ch03=" + ch03 + ", pricing=" + pricing
				+ ", ch04=" + ch04 + ", percent04=" + percent04 + ", feeLow04="
				+ feeLow04 + ", feeTop04=" + feeTop04 + ", ch05=" + ch05
				+ ", ch06=" + ch06 + ", ch07=" + ch07 + ", uid=" + uid + "]";
	}
	
	
	
	
	
	

}
