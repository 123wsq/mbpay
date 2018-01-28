package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author wangkai 代理商信息
 */
public class AgentInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentId; // 代理商ID
	private String logonName; // 代理商登陆名
	private String agentDgr; // 代理商等级
	private String fathAgentId; // 父代理商ID
	private String firstAgentId; // 一级代理商ID
	private String agentName; // 代理商名称
	private String legalName; // 法人姓名
	private String legalIdentityNo; // 法人身份证号码
	private String licNo; // 营业执照号
	private String taxNo; // 税务登记号
	private String address; // 联系地址
	private String tel; // 联系电话
	private String moblieNo; // 手机号码
	private String email; // 邮箱
	private String frozState; // 冻结状态
	private String margin; // 保证金
	private String contractStrDate; // 合同开始日期
	private String contractEndDate; // 合同结束日期
	private String agentStatus; // 代理商状态： 0正常 1停用
	private String remark; // 代理商说明信息
	private String filed1; // 扩展字段1
	private String filed2; // 扩展字段2
	private String techcontact; // 技术联系人
	private String techtelno; // 技术联系电话
	private String techemail; // 技术联系邮箱
	private String techmobno; // 技术联系手机
	private String city; // 城市
	private String province; // 省份
	private String bizcontact; // 业务联系人
	private String bizmobno; // 业务联系手机
	private String servcontact; // 客服联系人
	private String servmobno; // 客服联系手机
	private String oemState; // oem标识
	private String slttyp; // 结算方式： 0日结 1月结
	private String efftim;
	private String tiflg; // T+0提现标志，0 否 1是
	private String bankpayacno; // 代理商结算账户银行账号
	private String bankpayusernm; // 代理商结算账户用户名
	private String bankcode; // 开户行
	private String agentCode; // 代理商标识码
	private String fatherCode; // 父代理商标识码
	private String applyAgentId; // 申请审核代理商ID
	private String auditAgentid; // 当前审核代理商ID
	private String profitRatio; // 分润比例（十分之几）
	private String oemPicId; // oem图片(图片id)
	private String openingLicensePicId; // 开户许可证(图片id)
	private String businessLicensePicId;// 营业执照(图片id)
	private String legalIdentityPicId; // 法人身份证(图片id)
	private String taxNoPicId; // 税务登记证(图片id)
	private String oemPicName; // oem图片(图片名)
	private String openingLicensePicName; // 开户许可证(图片名)
	private String businessLicensePicName;// 营业执照(图片名)
	private String legalIdentityPicName; // 法人身份证(图片名)
	private String taxNoPicName; // 税务登记证(图片名)
	private String bankCity; //结算账户城市   
	private String bankProvince; //结算账户省份
	private String subBranch; //支行名称
	
	private String rateType;//费率类型
	
	private String rateCode; //
	
	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	
	
	private String custId;//商户号
	
	// 费率相关字段
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

	// 代理商审核相关字段
	private String auditStatus; // 审核状态：0审核中，1审核通过，2审核不通过
	private String auditFailReason; // 审核不通过原因；
	
	
	private String rateTCas; //t0提现费率率
	private String bondCardNo; //保证金账号
	private String bondCardName; //保证金开户名
	private String bondBankCode; //保证金开户行
	
	private String maxTCas; //t0提现单笔费用   hg add 20160406

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

	public String getBondCardNo() {
		return bondCardNo;
	}

	public void setBondCardNo(String bondCardNo) {
		this.bondCardNo = bondCardNo;
	}

	public String getBondCardName() {
		return bondCardName;
	}

	public void setBondCardName(String bondCardName) {
		this.bondCardName = bondCardName;
	}

	public String getBondBankCode() {
		return bondBankCode;
	}

	public void setBondBankCode(String bondBankCode) {
		this.bondBankCode = bondBankCode;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getAgentDgr() {
		return agentDgr;
	}

	public void setAgentDgr(String agentDgr) {
		this.agentDgr = agentDgr;
	}

	public String getFathAgentId() {
		return fathAgentId;
	}

	public void setFathAgentId(String fathAgentId) {
		this.fathAgentId = fathAgentId;
	}

	public String getFirstAgentId() {
		return firstAgentId;
	}

	public void setFirstAgentId(String firstAgentId) {
		this.firstAgentId = firstAgentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalIdentityNo() {
		return legalIdentityNo;
	}

	public void setLegalIdentityNo(String legalIdentityNo) {
		this.legalIdentityNo = legalIdentityNo;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMoblieNo() {
		return moblieNo;
	}

	public void setMoblieNo(String moblieNo) {
		this.moblieNo = moblieNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFrozState() {
		return frozState;
	}

	public void setFrozState(String frozState) {
		this.frozState = frozState;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getContractStrDate() {
		return contractStrDate;
	}

	public void setContractStrDate(String contractStrDate) {
		this.contractStrDate = contractStrDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFiled1() {
		return filed1;
	}

	public void setFiled1(String filed1) {
		this.filed1 = filed1;
	}

	public String getFiled2() {
		return filed2;
	}

	public void setFiled2(String filed2) {
		this.filed2 = filed2;
	}

	public String getTechcontact() {
		return techcontact;
	}

	public void setTechcontact(String techcontact) {
		this.techcontact = techcontact;
	}

	public String getTechtelno() {
		return techtelno;
	}

	public void setTechtelno(String techtelno) {
		this.techtelno = techtelno;
	}

	public String getTechemail() {
		return techemail;
	}

	public void setTechemail(String techemail) {
		this.techemail = techemail;
	}

	public String getTechmobno() {
		return techmobno;
	}

	public void setTechmobno(String techmobno) {
		this.techmobno = techmobno;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getBizcontact() {
		return bizcontact;
	}

	public void setBizcontact(String bizcontact) {
		this.bizcontact = bizcontact;
	}

	public String getBizmobno() {
		return bizmobno;
	}

	public void setBizmobno(String bizmobno) {
		this.bizmobno = bizmobno;
	}

	public String getServcontact() {
		return servcontact;
	}

	public void setServcontact(String servcontact) {
		this.servcontact = servcontact;
	}

	public String getServmobno() {
		return servmobno;
	}

	public void setServmobno(String servmobno) {
		this.servmobno = servmobno;
	}

	public String getOemState() {
		return oemState;
	}

	public void setOemState(String oemState) {
		this.oemState = oemState;
	}

	public String getSlttyp() {
		return slttyp;
	}

	public void setSlttyp(String slttyp) {
		this.slttyp = slttyp;
	}

	public String getEfftim() {
		return efftim;
	}

	public void setEfftim(String efftim) {
		this.efftim = efftim;
	}

	public String getTiflg() {
		return tiflg;
	}

	public void setTiflg(String tiflg) {
		this.tiflg = tiflg;
	}

	public String getBankpayacno() {
		return bankpayacno;
	}

	public void setBankpayacno(String bankpayacno) {
		this.bankpayacno = bankpayacno;
	}

	public String getBankpayusernm() {
		return bankpayusernm;
	}

	public void setBankpayusernm(String bankpayusernm) {
		this.bankpayusernm = bankpayusernm;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getFatherCode() {
		return fatherCode;
	}

	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

	public String getApplyAgentId() {
		return applyAgentId;
	}

	public void setApplyAgentId(String applyAgentId) {
		this.applyAgentId = applyAgentId;
	}

	public String getAuditAgentid() {
		return auditAgentid;
	}

	public void setAuditAgentid(String auditAgentid) {
		this.auditAgentid = auditAgentid;
	}

	public String getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(String profitRatio) {
		this.profitRatio = profitRatio;
	}

	public String getOemPicId() {
		return oemPicId;
	}

	public void setOemPicId(String oemPicId) {
		this.oemPicId = oemPicId;
	}

	public String getOpeningLicensePicId() {
		return openingLicensePicId;
	}

	public void setOpeningLicensePicId(String openingLicensePicId) {
		this.openingLicensePicId = openingLicensePicId;
	}

	public String getBusinessLicensePicId() {
		return businessLicensePicId;
	}

	public void setBusinessLicensePicId(String businessLicensePicId) {
		this.businessLicensePicId = businessLicensePicId;
	}

	public String getLegalIdentityPicId() {
		return legalIdentityPicId;
	}

	public void setLegalIdentityPicId(String legalIdentityPicId) {
		this.legalIdentityPicId = legalIdentityPicId;
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

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditFailReason() {
		return auditFailReason;
	}

	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

	public String getRateGeneralMaximun() {
		return rateGeneralMaximun;
	}

	public void setRateGeneralMaximun(String rateGeneralMaximun) {
		this.rateGeneralMaximun = rateGeneralMaximun;
	}

	public String getRateEntertainMaximun() {
		return rateEntertainMaximun;
	}

	public void setRateEntertainMaximun(String rateEntertainMaximun) {
		this.rateEntertainMaximun = rateEntertainMaximun;
	}

	public String getTaxNoPicId() {
		return taxNoPicId;
	}

	public void setTaxNoPicId(String taxNoPicId) {
		this.taxNoPicId = taxNoPicId;
	}

	public String getOemPicName() {
		return oemPicName;
	}

	public void setOemPicName(String oemPicName) {
		this.oemPicName = oemPicName;
	}

	public String getOpeningLicensePicName() {
		return openingLicensePicName;
	}

	public void setOpeningLicensePicName(String openingLicensePicName) {
		this.openingLicensePicName = openingLicensePicName;
	}

	public String getBusinessLicensePicName() {
		return businessLicensePicName;
	}

	public void setBusinessLicensePicName(String businessLicensePicName) {
		this.businessLicensePicName = businessLicensePicName;
	}

	public String getLegalIdentityPicName() {
		return legalIdentityPicName;
	}

	public void setLegalIdentityPicName(String legalIdentityPicName) {
		this.legalIdentityPicName = legalIdentityPicName;
	}

	public String getTaxNoPicName() {
		return taxNoPicName;
	}

	public void setTaxNoPicName(String taxNoPicName) {
		this.taxNoPicName = taxNoPicName;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	//左侧的和
	private String terminalSum;
	
	//右侧的单笔一个
	private String terminalMaxTCas;
	
	
	public String getTerminalSum() {
		return terminalSum;
	}

	public void setTerminalSum(String terminalSum) {
		this.terminalSum = terminalSum;
	}

	public String getTerminalMaxTCas() {
		return terminalMaxTCas;
	}

	public void setTerminalMaxTCas(String terminalMaxTCas) {
		this.terminalMaxTCas = terminalMaxTCas;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	
	
	
}