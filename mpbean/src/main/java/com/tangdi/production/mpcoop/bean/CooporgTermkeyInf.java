package com.tangdi.production.mpcoop.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class CooporgTermkeyInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 合作机构编号
	 */
	private String cooporgNo;

	/**
	 * 第三方机构商户号
	 */
	private String merNo;

	/**
	 * 终端号
	 */
	private String terNo;

	/**
	 * PIN密钥
	 */
	private String pinkey;

	/**
	 * MAC密钥
	 */
	private String mackey;

	/**
	 * TDK密钥
	 */
	private String tdkey;

	/**
	 * TDK效验值
	 */
	private String tkCheckValue;

	/**
	 * PIN校验值
	 */
	private String pkCheckValue;

	/**
	 * MAC校验值
	 */
	private String mkCheckValue;

	/**
	 * 预留字段
	 */
	private String resField3;

	/**
	 * 预留字段
	 */
	private String resField1;

	/**
	 * 预留字段
	 */
	private String resField2;

	public String getCooporgNo() {
		return cooporgNo;
	}

	public void setCooporgNo(String cooporgNo) {
		this.cooporgNo = cooporgNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getTerNo() {
		return terNo;
	}

	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}

	public String getPinkey() {
		return pinkey;
	}

	public void setPinkey(String pinkey) {
		this.pinkey = pinkey;
	}

	public String getMackey() {
		return mackey;
	}

	public void setMackey(String mackey) {
		this.mackey = mackey;
	}

	public String getTdkey() {
		return tdkey;
	}

	public void setTdkey(String tdkey) {
		this.tdkey = tdkey;
	}

	public String getTkCheckValue() {
		return tkCheckValue;
	}

	public void setTkCheckValue(String tkCheckValue) {
		this.tkCheckValue = tkCheckValue;
	}

	public String getPkCheckValue() {
		return pkCheckValue;
	}

	public void setPkCheckValue(String pkCheckValue) {
		this.pkCheckValue = pkCheckValue;
	}

	public String getMkCheckValue() {
		return mkCheckValue;
	}

	public void setMkCheckValue(String mkCheckValue) {
		this.mkCheckValue = mkCheckValue;
	}

	public String getResField3() {
		return resField3;
	}

	public void setResField3(String resField3) {
		this.resField3 = resField3;
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
}