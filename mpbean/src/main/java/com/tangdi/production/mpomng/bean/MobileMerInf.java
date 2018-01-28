package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 手机商户信息报表文件 
 * @author zhengqiang
 *
 */
public class MobileMerInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentId;//代理商编号
	private String agentName;//代理商名称
	private String custId ;        //商户编号
	private String custName;       //商户名称
	private String custType;       //商户类型
	private String custCardno;     //身份证号
	private String custLogin;      //账号
	private String custPwd;        //密码
	private String payPwdStrength; //密码强度
	private String usrEmail;       //电子邮件
	private String activCode;      //激活码
	private String usrGender;      //性别
	private String usrZip;         //邮编
	private String usrTel;         //固定电话
	private String usrMobile;      //手机
	private String usrBirthday;    //生日
	private String usrRandom;      //随机码
	private String isLoginFlag;    //登录状态
	private String custStatus;     //商户状态 0 正常 1禁用
	private String lastOperTime;   //上次登录时间
	private String custRegDateTime;//注册时间
	private int custPwdNum;        //密码效验次数
	private String merClass;      // 商户等级
	private String resField1;        
	private String resField2;
	private String resField3;
	private String province; //省份
	private String city;   //城市
	private String region; //地区
	private String usrAddress;     //通信地址
	private String address; // 省份 +  城市 + 地区 + 通信地址
	private String authStatus; //认证状态
	private String referrer;  //推荐人
	private String cardNo;//银行卡号
	private String authThroughTime;//认证通过时间
	private String identifyTime;//认证时间
	private String rateHolidayTCas;//节假日提现费率
	private String rateTCas; //提现费率
	
	private String maxTCas;//提现单笔收费金额 hg add 20160406
	
	public String getMaxTCas() {
		return maxTCas;
	}
	public void setMaxTCas(String maxTCas) {
		this.maxTCas = maxTCas;
	}
	public String getRateTCas() {
		return rateTCas;
	}
	public void setRateTCas(String rateTCas) {
		this.rateTCas = rateTCas;
	}
	public String getRateHolidayTCas() {
		return rateHolidayTCas;
	}
	public void setRateHolidayTCas(String rateHolidayTCas) {
		this.rateHolidayTCas = rateHolidayTCas;
	}
	public String getIdentifyTime() {
		return identifyTime;
	}
	public void setIdentifyTime(String identifyTime) {
		this.identifyTime = identifyTime;
	}
	public String getAuthThroughTime() {
		return authThroughTime;
	}
	public void setAuthThroughTime(String authThroughTime) {
		this.authThroughTime = authThroughTime;
	}
	/**
	 * 民生类 0.38%
	 */
	private String rateLivelihood;

	/**
	 * 一般类 0.78%
	 */
	private String rateGeneral;

	/**
	 * 批发类 0.78% 封顶
	 */
	private String rateGeneralTop;

	/**
	 * 批发类 0.78% 封顶金额 分
	 */
	private String rateGeneralMaximun;

	/**
	 * 餐娱类 1.25%
	 */
	private String rateEntertain;

	/**
	 * 房产汽车类 1.25% 封顶
	 */
	private String rateEntertainTop;

	/**
	 * 房产汽车类 1.25% 封顶金额 分
	 */
	private String rateEntertainMaximun;
    /**
	 * 证件类型:1-身份证
	 */
    private String certificateType;   
      
    /**
	 * 证件号码
	 */
    private String certificateNo;   
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getAddress() {
		String addr = "";
		if(province != null && !province.equals("")){
			addr = province;
		}
		if(city != null && !city.equals("")){
			addr += ", "+ city;
		}
		if(region != null && !region.equals("")){
			addr += ", "+ region;
		}
		if(usrAddress != null && !usrAddress.equals("")){
			addr += ", "+ usrAddress;
		}
		return addr;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCustCardno() {
		return custCardno;
	}
	public void setCustCardno(String custCardno) {
		this.custCardno = custCardno;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getLastOperTime() {
		return lastOperTime;
	}
	public void setLastOperTime(String lastOperTime) {
		this.lastOperTime = lastOperTime;
	}
	public String getCustRegDateTime() {
		return custRegDateTime;
	}
	public void setCustRegDateTime(String custRegDateTime) {
		this.custRegDateTime = custRegDateTime;
	}
	public String getMerClass() {
		return merClass;
	}
	public void setMerClass(String merClass) {
		this.merClass = merClass;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustLogin() {
		return custLogin;
	}
	public void setCustLogin(String custLogin) {
		this.custLogin = custLogin;
	}
	public String getCustPwd() {
		return custPwd;
	}
	public void setCustPwd(String custPwd) {
		this.custPwd = custPwd;
	}
	public String getPayPwdStrength() {
		return payPwdStrength;
	}
	public void setPayPwdStrength(String payPwdStrength) {
		this.payPwdStrength = payPwdStrength;
	}
	public String getUsrEmail() {
		return usrEmail;
	}
	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}
	public String getActivCode() {
		return activCode;
	}
	public void setActivCode(String activCode) {
		this.activCode = activCode;
	}
	public String getUsrAddress() {
		return usrAddress;
	}
	public void setUsrAddress(String usrAddress) {
		this.usrAddress = usrAddress;
	}
	public String getUsrGender() {
		return usrGender;
	}
	public void setUsrGender(String usrGender) {
		this.usrGender = usrGender;
	}
	public String getUsrZip() {
		return usrZip;
	}
	public void setUsrZip(String usrZip) {
		this.usrZip = usrZip;
	}
	public String getUsrTel() {
		return usrTel;
	}
	public void setUsrTel(String usrTel) {
		this.usrTel = usrTel;
	}
	public String getUsrMobile() {
		return usrMobile;
	}
	public void setUsrMobile(String usrMobile) {
		this.usrMobile = usrMobile;
	}
	public String getUsrBirthday() {
		return usrBirthday;
	}
	public void setUsrBirthday(String usrBirthday) {
		this.usrBirthday = usrBirthday;
	}
	public String getUsrRandom() {
		return usrRandom;
	}
	public void setUsrRandom(String usrRandom) {
		this.usrRandom = usrRandom;
	}
	public String getIsLoginFlag() {
		return isLoginFlag;
	}
	public void setIsLoginFlag(String isLoginFlag) {
		this.isLoginFlag = isLoginFlag;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public int getCustPwdNum() {
		return custPwdNum;
	}
	public void setCustPwdNum(int custPwdNum) {
		this.custPwdNum = custPwdNum;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getRateLivelihood() {
		return rateLivelihood;
	}
	public void setRateLivelihood(String rateLivelihood) {
		this.rateLivelihood = rateLivelihood;
	}
	public String getRateGeneral() {
		return rateGeneral;
	}
	public void setRateGeneral(String rateGeneral) {
		this.rateGeneral = rateGeneral;
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
	public String getRateEntertain() {
		return rateEntertain;
	}
	public void setRateEntertain(String rateEntertain) {
		this.rateEntertain = rateEntertain;
	}
	public String getRateEntertainTop() {
		return rateEntertainTop;
	}
	public void setRateEntertainTop(String rateEntertainTop) {
		this.rateEntertainTop = rateEntertainTop;
	}
	public String getRateEntertainMaximun() {
		return rateEntertainMaximun;
	}
	public void setRateEntertainMaximun(String rateEntertainMaximun) {
		this.rateEntertainMaximun = rateEntertainMaximun;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


}
