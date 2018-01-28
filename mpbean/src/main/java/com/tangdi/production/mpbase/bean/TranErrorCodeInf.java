package com.tangdi.production.mpbase.bean;

/**
 * 渠道错误消息码
 * @author zhengqiang
 *
 */
public class TranErrorCodeInf {
	/**
	 * 系统错误代码
	 */
	private String sid ;
	/**
	 * 第三方错误代码
	 */
	private String tid ;
	/**
	 * 系统错误信息 
	 * 
	 */
	private String errormsg ;
	/**
	 * 交易机构
	 */
	private String orgno ;
	
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

}
