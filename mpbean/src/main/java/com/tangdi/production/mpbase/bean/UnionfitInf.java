package com.tangdi.production.mpbase.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 *
 */
public class UnionfitInf extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String issnam; // 发卡行名称
	private String issno;
	private String crdnam;// 卡名称
	private String fitcrk;// Fit所在磁道
	private String fitoff;
	private String fitlen;
	private String crdoff;
	private String crdlen;
	private String crdctt;// 主账号
	private String crdcrk;// 主账号所在磁道
	private String binoff;// 发卡行标识起始字节
	private String binlen;// 发卡行标识长度
	private String binctt;// 发卡行标识取值
	private String bincrk;// 发卡行标识读取磁道
	private String dcflag;// 卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡

	public String getIssnam() {
		return issnam;
	}

	public void setIssnam(String issnam) {
		this.issnam = issnam;
	}

	public String getIssno() {
		return issno;
	}

	public void setIssno(String issno) {
		this.issno = issno;
	}

	public String getCrdnam() {
		return crdnam;
	}

	public void setCrdnam(String crdnam) {
		this.crdnam = crdnam;
	}

	public String getFitcrk() {
		return fitcrk;
	}

	public void setFitcrk(String fitcrk) {
		this.fitcrk = fitcrk;
	}

	public String getFitoff() {
		return fitoff;
	}

	public void setFitoff(String fitoff) {
		this.fitoff = fitoff;
	}

	public String getFitlen() {
		return fitlen;
	}

	public void setFitlen(String fitlen) {
		this.fitlen = fitlen;
	}

	public String getCrdoff() {
		return crdoff;
	}

	public void setCrdoff(String crdoff) {
		this.crdoff = crdoff;
	}

	public String getCrdlen() {
		return crdlen;
	}

	public void setCrdlen(String crdlen) {
		this.crdlen = crdlen;
	}

	public String getCrdctt() {
		return crdctt;
	}

	public void setCrdctt(String crdctt) {
		this.crdctt = crdctt;
	}

	public String getCrdcrk() {
		return crdcrk;
	}

	public void setCrdcrk(String crdcrk) {
		this.crdcrk = crdcrk;
	}

	public String getBinoff() {
		return binoff;
	}

	public void setBinoff(String binoff) {
		this.binoff = binoff;
	}

	public String getBinlen() {
		return binlen;
	}

	public void setBinlen(String binlen) {
		this.binlen = binlen;
	}

	public String getBinctt() {
		return binctt;
	}

	public void setBinctt(String binctt) {
		this.binctt = binctt;
	}

	public String getBincrk() {
		return bincrk;
	}

	public void setBincrk(String bincrk) {
		this.bincrk = bincrk;
	}

	public String getDcflag() {
		return dcflag;
	}

	public void setDcflag(String dcflag) {
		this.dcflag = dcflag;
	}
}
