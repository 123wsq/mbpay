package com.tangdi.production.mpamng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 终端密钥文件模版
 * @author zhengqiang 2015/04/17
 *
 */
@ReportHead(display=false)
public class TermKeyFileTemplate {
	/**
	 * 终端号
	 */
	@ReportField(split="=")
	private String termno;
	/**
	 * 传输密钥
	 */
	@ReportField(split=" ")
	private String zmk;
	/**
	 * 校验值
	 */
	@ReportField(split="")
	private String checkvalue;
	
	public TermKeyFileTemplate(){
		
	}
	/**
	 * 
	 * @param termno 终端号
	 * @param zmk 传输密钥
	 * @param checkvalue 校验值
	 */
	public TermKeyFileTemplate(String termno,String zmk,String checkvalue){
		this.termno = termno;
		this.zmk = zmk;
		this.checkvalue = checkvalue;
	}
	
	
	public String getTermno() {
		return termno;
	}
	public void setTermno(String termno) {
		this.termno = termno;
	}
	public String getZmk() {
		return zmk;
	}
	public void setZmk(String zmk) {
		this.zmk = zmk;
	}
	public String getCheckvalue() {
		return checkvalue;
	}
	public void setCheckvalue(String checkvalue) {
		this.checkvalue = checkvalue;
	}

	
	

}
