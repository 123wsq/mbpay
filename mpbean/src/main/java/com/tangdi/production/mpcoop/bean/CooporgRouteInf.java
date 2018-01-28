package com.tangdi.production.mpcoop.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class CooporgRouteInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 路由编号
	 */
	private String rtrid;

	/**
	 * 合作结构编号
	 */
	private String cooporgNo;

	/**
	 * 交易码
	 */
	private String txncd;

	/**
	 * 路由服务名
	 */
	private String rtrsvr;

	/**
	 * 路由交易码
	 */
	private String rtrcod;

	/**
	 * 路由状态
	 */
	private String status;

	/**
	 * 最后编辑时间
	 */
	private String editDate;

	/**
	 * 最后编辑人
	 */
	private String editUserId;
	/**
	 * 路由类型 01 签到 02 消费 03 冲正	04 余额查询
	 */
	private String rtrType;
	/**
	 * 路由描述
	 */
	private String rtrDesc;

	public String getRtrid() {
		return rtrid;
	}

	public void setRtrid(String rtrid) {
		this.rtrid = rtrid;
	}

	public String getCooporgNo() {
		return cooporgNo;
	}

	public void setCooporgNo(String cooporgNo) {
		this.cooporgNo = cooporgNo;
	}

	public String getTxncd() {
		return txncd;
	}

	public void setTxncd(String txncd) {
		this.txncd = txncd;
	}

	public String getRtrsvr() {
		return rtrsvr;
	}

	public void setRtrsvr(String rtrsvr) {
		this.rtrsvr = rtrsvr;
	}

	public String getRtrcod() {
		return rtrcod;
	}

	public void setRtrcod(String rtrcod) {
		this.rtrcod = rtrcod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRtrType() {
		return rtrType;
	}

	public void setRtrType(String rtrType) {
		this.rtrType = rtrType;
	}

	public String getRtrDesc() {
		return rtrDesc;
	}

	public void setRtrDesc(String rtrDesc) {
		this.rtrDesc = rtrDesc;
	}
	
	
}