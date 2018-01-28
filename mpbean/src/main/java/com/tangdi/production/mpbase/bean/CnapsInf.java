package com.tangdi.production.mpbase.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 *
 */
public class CnapsInf extends BaseBean {

	private static final long serialVersionUID = 1L;

	private String cnapsCode;// 联行号
	private String bankName;// 银行名称
	private String subBranch;// 支行信息
	private String bankPro;// 所在省
	private String bankCity;// 所在地市

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	public String getBankPro() {
		return bankPro;
	}

	public void setBankPro(String bankPro) {
		this.bankPro = bankPro;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
}