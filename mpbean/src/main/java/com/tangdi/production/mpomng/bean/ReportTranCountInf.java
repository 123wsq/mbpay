package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public class ReportTranCountInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 6311746414717425970L;

	/**
	 * 订单类型
	 */
    private String ordType;   
      
    /**
	 * 统计月
	 */
    private String cmonth;
    /**
     * 统计年
     */
    private String year;
    /**
     * 交易金额
     */
    private String amt;
	public String getOrdType() {
		return ordType;
	}
	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}
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
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}


	
   
 }