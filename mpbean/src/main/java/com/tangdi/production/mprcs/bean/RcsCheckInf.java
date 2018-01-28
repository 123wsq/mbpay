package com.tangdi.production.mprcs.bean;

/**
 * 风控校验项配置
 * 
 * @author xiejinzhong
 *
 */
public class RcsCheckInf {
	
	/**
	 * 最后一个校验类型（根据优先级规则，优先级高的最先检查，优先级高的检查通过后不进入优先级低的检查，同等优先级的总之检查）
	 */
	private String lastLimitType = "";
	
	/**
	 * 是否检查单笔最小金额
	 */
	private boolean checkMinAmt = true;
	/**
	 * 是否检查单笔最大金额
	 */
	private boolean checkMaxAmt = true;
	
	/**
	 * 是否检查单日交易次数
	 */
	private boolean checkDayNum = true;
	
	/**
	 * 是否检查单日交易金额
	 */
	private boolean checkDayAmt = true;
	
	/**
	 * 是否检查每月交易次数
	 */
	private boolean checkMonthNum = true;
	
	/**
	 * 是否检查每月交易金额
	 */
	private boolean checkMonthAmt = true;
	
	/**
	 * 是否检查每年交易次数
	 */
	private boolean checkYearNum = true;
	
	/**
	 * 是否检查每年交易金额
	 */
	private boolean checkYearAmt = true;
	
	public void setCheckAll(boolean ok){
		this.checkDayAmt = ok;
		this.checkDayNum = ok;
		this.checkMonthAmt = ok;
		this.checkMonthNum = ok;
		this.checkMinAmt = ok;
		this.checkMaxAmt = ok;
		this.checkYearAmt = ok;
		this.checkYearNum = ok;
	}

	public boolean isCheckMinAmt() {
		return checkMinAmt;
	}


	public void setCheckMinAmt(boolean checkMinAmt) {
		this.checkMinAmt = checkMinAmt;
	}


	public boolean isCheckMaxAmt() {
		return checkMaxAmt;
	}


	public void setCheckMaxAmt(boolean checkMaxAmt) {
		this.checkMaxAmt = checkMaxAmt;
	}


	public boolean isCheckDayNum() {
		return checkDayNum;
	}

	public void setCheckDayNum(boolean checkDayNum) {
		this.checkDayNum = checkDayNum;
	}

	public boolean isCheckDayAmt() {
		return checkDayAmt;
	}

	public void setCheckDayAmt(boolean checkDayAmt) {
		this.checkDayAmt = checkDayAmt;
	}

	public boolean isCheckMonthNum() {
		return checkMonthNum;
	}

	public void setCheckMonthNum(boolean checkMonthNum) {
		this.checkMonthNum = checkMonthNum;
	}

	public boolean isCheckMonthAmt() {
		return checkMonthAmt;
	}

	public void setCheckMonthAmt(boolean checkMonthAmt) {
		this.checkMonthAmt = checkMonthAmt;
	}

	public boolean isCheckYearNum() {
		return checkYearNum;
	}

	public void setCheckYearNum(boolean checkYearNum) {
		this.checkYearNum = checkYearNum;
	}

	public boolean isCheckYearAmt() {
		return checkYearAmt;
	}

	public void setCheckYearAmt(boolean checkYearAmt) {
		this.checkYearAmt = checkYearAmt;
	}

	public String getLastLimitType() {
		return lastLimitType;
	}

	public void setLastLimitType(String lastLimitType) {
		this.lastLimitType = lastLimitType;
	}

}
