package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public class ReportMerClassInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 6311746414717425970L;

	/**
	 * 等级
	 */
    private String classId;   
      
    /**
	 * 数量
	 */
    private String cnum;
    
    private String cmonth;
    private String year;

	public String getCmonth() {
		return cmonth;
	}

	public void setCmonth(String cmonth) {
		this.cmonth = cmonth;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getCnum() {
		return cnum;
	}

	public void setCnum(String cnum) {
		this.cnum = cnum;
	}   
      
   
   
 }