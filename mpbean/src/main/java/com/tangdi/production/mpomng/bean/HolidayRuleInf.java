package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public class HolidayRuleInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节日日期
	 */
	private String hoDate;

	/**
	 * T0提现限制
	 */
	private String t0Status;

	/**
	 * T1提现限制
	 */
	private String t1Status;
	private String stime;
	private String etime;
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		if(stime != null && !stime.equals("") && stime.length() <=10){
			this.stime = stime.substring(0,4);
			this.stime=this.stime+stime.substring(5,7);
			this.stime=this.stime+stime.substring(8,10);
			
		}else{
			this.stime = stime;
		}
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		if(etime != null && !etime.equals("") && etime.length() <=10){
			this.etime = etime.substring(0,4);
			this.etime=this.etime+etime.substring(5,7);
			this.etime=this.etime+etime.substring(8,10);
			
		}else{
			this.etime = etime;
		}
		
	}
	

	public String getHoDate() {
		return hoDate;
	}

	public void setHoDate(String hoDate) {
		this.hoDate = hoDate;
		
	}

	public String getT0Status() {
		return t0Status;
	}

	public void setT0Status(String t0Status) {
		this.t0Status = t0Status;
	}

	public String getT1Status() {
		return t1Status;
	}

	public void setT1Status(String t1Status) {
		this.t1Status = t1Status;
	}

	
}