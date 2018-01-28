package com.tangdi.production.mpamng.constants;

/**
 * 自定义常量
 * 
 * @author limiao
 *
 */
public class CT extends com.tangdi.production.mpbase.constants.MsgCT {
	/**
	 * 终端下拨状态：-1 失败
	 */
	public static final Integer TERM_ALLOCATE_FAIL = -1;
	/**
	 * 终端下拨状态：0 成功
	 */
	public static final Integer TERM_ALLOCATE_SUCCESS = 0;
	/**
	 * 终端下拨状态：1 代理商不存在
	 */
	public static final Integer TERM_ALLOCATE_1 = 1;
	/**
	 * 终端下拨状态：2 终端不存在
	 */
	public static final Integer TERM_ALLOCATE_2 = 2;
	/**
	 * 终端状态 出库
	 */
	public final static String TERMINALSTATUS_NO_BIND = "1";
	/**
	 * 终端下拨状态：3 终端已下拨
	 */
	public static final Integer TERM_ALLOCATE_3 = 3;
	/**
	 * 终端费率状态：-1 终端费率添加失败
	 */
	public static final Integer TERM_FEE_FAIL = -1;

	/**
	 * 终端费率状态：0 终端费率添加成功
	 */
	public static final Integer TERM_FEE_SUCCESS = 0;

	// --------------------------代理商审核方式-----------------------------------
	/**
	 * 代理商审核方式代码:运营平台审核
	 */
	public static final String ADMIN_AUDIT = "00";
	/**
	 * 代理商审核方式代码:一级代理商初审,运营终审
	 */
	public static final String FIRST_AGENT_AUDIT = "01";
	/**
	 * 代理商审核方式代码:逐级审核
	 */
	public static final String LEVEL_AUDIT = "02";
	
	// --------------------------代理商审核状态：0审核中，1审核通过，2审核不通过-----------------------------------
	/**
	 * 代理商审核状态：0审核中
	 */
	public static final String AUDIT_STATUS_APPLY = "0";
	/**
	 * 代理商审核状态：1审核通过
	 */
	public static final String AUDIT_STATUS_AGREE = "1";
	/**
	 * 代理商审核状态：2审核不通过
	 */
	public static final String AUDIT_STATUS_DISAGREE = "2";
	
	
	
	
	/**
	 * 代理商开户验证
	 */
	public static final String RATE_LIVELIHOOD="RATE_LIVELIHOOD";
	public static final String RATE_GENERAL="RATE_GENERAL";
	public static final String RATE_GENERAL_TOP="RATE_GENERAL_TOP";
	public static final String RATE_ENTERTAIN="RATE_ENTERTAIN";
	public static final String RATE_ENTERTAIN_TOP="RATE_ENTERTAIN_TOP";
}
