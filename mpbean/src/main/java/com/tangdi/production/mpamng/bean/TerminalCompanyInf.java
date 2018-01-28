package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao 终端厂商
 */
public class TerminalCompanyInf extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String termId;
	private String termcomCode;
	private String termcomName;
	private String termType;
	private String termTypeName;

	private Integer type;

	/**
	 * 厂商 1 终端2
	 * 
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 厂商 1 终端2
	 * 
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * 
	 * @param termId
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * 厂商简写
	 * 
	 * @return termcomCode
	 */
	public String getTermcomCode() {
		return termcomCode;
	}

	/**
	 * 厂商简写
	 * 
	 * @param termcomCode
	 */
	public void setTermcomCode(String termcomCode) {
		this.termcomCode = termcomCode;
	}

	/**
	 * 厂商名称
	 * 
	 * @return termcomName
	 */
	public String getTermcomName() {
		return termcomName;
	}

	/**
	 * 厂商名称
	 * 
	 * @param termcomName
	 */
	public void setTermcomName(String termcomName) {
		this.termcomName = termcomName;
	}

	/**
	 * 终端类型
	 * 
	 * @return termType
	 */
	public String getTermType() {
		return termType;
	}

	/**
	 * 终端类型
	 * 
	 * @param termType
	 */
	public void setTermType(String termType) {
		this.termType = termType;
	}

	/**
	 * 终端类型名
	 * 
	 * @return termTypeName
	 */
	public String getTermTypeName() {
		return termTypeName;
	}

	/**
	 * 终端类型名
	 * 
	 * @param termTypeName
	 */
	public void setTermTypeName(String termTypeName) {
		this.termTypeName = termTypeName;
	}

}
