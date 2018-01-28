package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public class ReportMerCountInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 6311746414717425970L;

	/**
	 * 统计月
	 */
    private String cmonth;   
      
    /**
	 * 数量
	 */
    private String cnum;
    /**
     * 统计年
     */
    private String year;
	public String getCmonth() {
		return cmonth;
	}
	public void setCmonth(String cmonth) {
		this.cmonth = cmonth;
	}
	public String getCnum() {
		return cnum;
	}
	public void setCnum(String cnum) {
		this.cnum = cnum;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	
   
 }