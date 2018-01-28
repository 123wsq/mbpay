package com.tangdi.production.mpcoop.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */
public class CooporgMerInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public CooporgMerInf() {

	}

	public CooporgMerInf(String cooporgNo, String merNo, String terNo) {
		this.cooporgNo = cooporgNo;
		this.merNo = merNo;
		this.terNo = terNo;
	}

	/**
	 * 合作机构编号
	 */
	private String cooporgNo;

	/**
	 * 商户编号
	 */
	private String merNo;

	/**
	 * 终端编号
	 */
	private String terNo;

	/**
	 * 商户名称
	 */
	private String merName;

	/**
	 * 商户密钥(快捷通道/终端主密钥)
	 */
	private String merKey;

	/**
	 * 主密钥校验值
	 */
	private String checkValue;

	/**
	 * 大商户T0刷卡成本费率(%)
	 */
	private String rateT0;

	/**
	 * 大商户T1刷卡成本费率(%)
	 */
	private String rateT1;

	/**
	 * 费率封顶
	 */
	private String rateTop;

	/**
	 * 商户状态:0-正常，1-关闭
	 */
	private String merStatus;

	/**
	 * 日交易限额
	 */
	private String dtLimit;

	/**
	 * 月交易限额
	 */
	private String mtLimit;

	/**
	 * 单笔最低交易额
	 */
	private String lowLimit;

	/**
	 * 单笔最高交易额
	 */
	private String highLimit;

	/**
	 * 第三方批次号
	 */
	private String batchNo;

	/**
	 * 上次签到时间
	 */
	private String signTime;

	/**
	 * 编辑时间
	 */
	private String editDate;

	/**
	 * 编辑员
	 */
	private String editUserId;

	/**
	 * 签到次数
	 */
	private int signcount;

	private String agentId;

	private String rateType;
	private String rateTypeTop;

	private String mType;
	private String mID;

	/**
	 * 省份ID
	 */
	private String provinceID;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 机构名称
	 */
	private String coopName;

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateTypeTop() {
		return rateTypeTop;
	}

	public void setRateTypeTop(String rateTypeTop) {
		this.rateTypeTop = rateTypeTop;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

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

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerKey() {
		return merKey;
	}

	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getRateT0() {
		return rateT0;
	}

	public void setRateT0(String rateT0) {
		this.rateT0 = rateT0;
	}

	public String getRateT1() {
		return rateT1;
	}

	public void setRateT1(String rateT1) {
		this.rateT1 = rateT1;
	}

	public String getRateTop() {
		return rateTop;
	}

	public void setRateTop(String rateTop) {
		this.rateTop = rateTop;
	}

	public String getMerStatus() {
		return merStatus;
	}

	public void setMerStatus(String merStatus) {
		this.merStatus = merStatus;
	}

	public String getDtLimit() {
		return dtLimit;
	}

	public void setDtLimit(String dtLimit) {
		this.dtLimit = dtLimit;
	}

	public String getMtLimit() {
		return mtLimit;
	}

	public void setMtLimit(String mtLimit) {
		this.mtLimit = mtLimit;
	}

	public String getLowLimit() {
		return lowLimit;
	}

	public void setLowLimit(String lowLimit) {
		this.lowLimit = lowLimit;
	}

	public String getHighLimit() {
		return highLimit;
	}

	public void setHighLimit(String highLimit) {
		this.highLimit = highLimit;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(String editUserId) {
		this.editUserId = editUserId;
	}

	public int getSigncount() {
		return signcount;
	}

	public void setSigncount(int signcount) {
		this.signcount = signcount;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCoopName() {
		return coopName;
	}

	public void setCoopName(String coopName) {
		this.coopName = coopName;
	}

}