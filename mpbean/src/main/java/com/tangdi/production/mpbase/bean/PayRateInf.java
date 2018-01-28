package com.tangdi.production.mpbase.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 支付费率实体类
 * @author zhengqiang
 *
 */
public class PayRateInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String feeId;    //费率编号
	private String feeCode;  //费率代码
	private String relType;  //关联类型  00 平台成本费率 01 代理商费成本率  02代理商下的商户费率 03 终端费率 04 商户费率
	private String relId;    //关联编码
	private String ccy;      //货币类型
	private String feeName;  //费率名称
	private String feeType;  //费率类型  CH01 一般类 , CH02 民生类, CH03 批发类封顶手续费 CH04 餐娱类费率
	private String pricing;  //定额
	private String percent;  //比例
	private String feeLow;   //最低
	private String feeTop;   //封顶
	private String feeStatus; //费率状态 00 正常 01 处理中(或审核中) 02 历史
	
	
	public PayRateInf() {
		super();
	}
	
	public PayRateInf(String feeCode, String feeStatus) {
		super();
		this.feeCode = feeCode;
		this.feeStatus = feeStatus;
	}
	public PayRateInf(String feeCode, String feeType ,
			String feeStatus,String createUserId,
			String editUserId,String createDate,String editDate) {
		this.feeCode = feeCode;
		this.feeType = feeType;
		this.feeStatus = feeStatus;
		super.setCreateUserId(createUserId);
		super.setCreateDate(createDate);
		super.setEditDate(editDate);
		super.setEditUserId(editUserId);
	}
	public PayRateInf(String feeCode, String feeType ,
			String feeStatus,
			String editUserId,String editDate) {
		this.feeCode = feeCode;
		this.feeType = feeType;
		this.feeStatus = feeStatus;
		super.setEditDate(editDate);
		super.setEditUserId(editUserId);
	}
	public PayRateInf(String feeCode, String feeType ,
			String feeStatus) {
		super();
		this.feeCode = feeCode;
		this.feeType = feeType;
		this.feeStatus = feeStatus;
	}


	public String getFeeId() {
		return feeId;
	}
	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}
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
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getPricing() {
		return pricing;
	}
	public void setPricing(String pricing) {
		this.pricing = pricing;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getFeeLow() {
		return feeLow;
	}
	public void setFeeLow(String feeLow) {
		this.feeLow = feeLow;
	}
	public String getFeeTop() {
		return feeTop;
	}
	public void setFeeTop(String feeTop) {
		this.feeTop = feeTop;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}
	
	
}
