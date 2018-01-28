package com.tangdi.production.mpomng.bean;

/**
 * 提现订单汇总-民生
 * 
 * @author sunhaining
 *
 */
public class SettleCountInf_CMBC {
	/***
	 * 商户名称
	 */
	private String custName;
	/***
	 * 商户序号
	 */
	private String custId;
	/***
	 * 结算金额 T0
	 */
	private String t0;
	/***
	 * 结算金额 T1
	 */
	private String t1;
	/***
	 * 开户行省
	 */
	private String provinceName;
	/***
	 * 开户行市
	 */
	private String cityName;
	/***
	 * 开户行联行号
	 */
	private String cnapsCode;
	/***
	 * 开户银行
	 */
	private String subBranch;

	/***
	 * 类型
	 */
	private String type;
	/***
	 * 开户名称
	 */
	private String cardName;
	/***
	 * 结算账号
	 */
	private String cardNo;
	
	/***
	 * 应结算金额
	 */
	private String netrecamt;
	
	/***
	 * 代理商编号
	 */
	private String agentId;
	
	/***
	 * 代理商名称
	 */
	private String agentName;
	
	/***
	 * 完成时间
	 */
	private String sucDate;
	
	/**
	 * 开户银行
	 * */
	private String issnam;
	
	/***
	 * 开户行编码
	 */
	private String acBankCode;
	
	/***
	 * 开户支行名称
	 */
	private String acHankName;
	
	/***
	 * 开户行省ID
	 */
	private String provinceID;
	
	/***
	 * 开户行市ID
	 */
	private String cityID;
	
	private String acType;
	
	
	

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getSucDate() {
		return sucDate;
	}

	public void setSucDate(String sucDate) {
		this.sucDate = sucDate;
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

	public String getNetrecamt() {
		return netrecamt;
	}

	public void setNetrecamt(String netrecamt) {
		this.netrecamt = netrecamt;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getT0() {
		return t0;
	}

	public void setT0(String t0) {
		this.t0 = t0;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	public String getAcBankCode() {
		return acBankCode;
	}

	public void setAcBankCode(String acBankCode) {
		this.acBankCode = acBankCode;
	}

	public String getAcHankName() {
		return acHankName;
	}

	public void setAcHankName(String acHankName) {
		this.acHankName = acHankName;
	}

	public String getIssnam() {
		return issnam;
	}

	public void setIssnam(String issnam) {
		this.issnam = issnam;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	
	
}
