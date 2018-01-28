package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 商户实名认证信息
 * 
 * @author zhuji
 * @version 1.0
 *
 */
public class MeridentifyInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9081223299651650631L;

	/**
	 * 商户编号(主键)
	 */
	private String custId;

	/**
	 * 商户姓名
	 */
	private String custName;

	/**
	 * 证件类型:1-身份证
	 */
	private String certificateType;

	/**
	 * 证件号码
	 */
	private String certificateNo;

	/**
	 * 手持身份证照片
	 */
	private String idcardHandheld;

	/**
	 * 身份证正面
	 */
	private String idcardFront;

	/**
	 * 身份证反面
	 */
	private String idcardBack;

	/**
	 * 认证状态（0未认证，1审核中，2审核通过，3审核不通过）
	 */
	private String custStatus;

	/**
	 * 认证时间
	 */
	private String identifyTime;

	/**
	 * 认证审核意见
	 */
	private String auditIdea;

	/**
	 * 审核人
	 */
	private String identifyUser;

	/**
	 * 公安部身份认证状态:0-未认证；2-认证通过；3-认证不通过
	 */
	private String policeIdentifystatus;

	/**
	 * 公安部身份认证图片
	 */
	private String policeIdentifypic;

	/**
	 * 预留字段1
	 */
	private String resField1;

	/**
	 * 预留字段2
	 */
	private String resField2;

	/**
	 * 预留字段3
	 */
	private String resField3;

	private String custRegDatetime;// 注册时间

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String cityName;
	private String provinceName;
	private String address;

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

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getIdcardHandheld() {
		return idcardHandheld;
	}

	public void setIdcardHandheld(String idcardHandheld) {
		this.idcardHandheld = idcardHandheld;
	}

	public String getIdcardFront() {
		return idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getIdentifyTime() {
		return identifyTime;
	}

	public void setIdentifyTime(String identifyTime) {
		this.identifyTime = identifyTime;
	}

	public String getAuditIdea() {
		return auditIdea;
	}

	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}

	public String getIdentifyUser() {
		return identifyUser;
	}

	public void setIdentifyUser(String identifyUser) {
		this.identifyUser = identifyUser;
	}

	public String getPoliceIdentifystatus() {
		return policeIdentifystatus;
	}

	public void setPoliceIdentifystatus(String policeIdentifystatus) {
		this.policeIdentifystatus = policeIdentifystatus;
	}

	public String getPoliceIdentifypic() {
		return policeIdentifypic;
	}

	public void setPoliceIdentifypic(String policeIdentifypic) {
		this.policeIdentifypic = policeIdentifypic;
	}

	public String getResField1() {
		return resField1;
	}

	public void setResField1(String resField1) {
		this.resField1 = resField1;
	}

	public String getResField2() {
		return resField2;
	}

	public void setResField2(String resField2) {
		this.resField2 = resField2;
	}

	public String getResField3() {
		return resField3;
	}

	public void setResField3(String resField3) {
		this.resField3 = resField3;
	}

	public String getCustRegDatetime() {
		return custRegDatetime;
	}

	public void setCustRegDatetime(String custRegDatetime) {
		this.custRegDatetime = custRegDatetime;
	}

}