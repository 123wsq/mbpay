package com.tangdi.production.tdauth.constants;

/**
 * 权限模块常量定义
 * 
 * @author zhengqiang
 *
 */
public class Constant {
	/**
	 * 系统初始角色,最高角色权限
	 */
	public final static String TOP_ROLE_ID = "0001";
	/**
	 * 系统最高机构权限
	 */
	public final static String TOP_ORG_ID = "000000001";

	/**
	 * 系统超级管理员用户
	 */
	// public final static String TOP_USER_ID = "adminauth";
	/**
	 * 系统超级管理员用户编号
	 */
	// public final static Integer TOP_USER_NO = 1000;

	/**
	 * 内置用户
	 */
	public final static String TOPINNER_USER_ID = "subplatformadminauth";

	/**
	 * 运营商系统ID
	 */
	public final static String SYS_TDWEB = "0001";
	/**
	 * 交易系统ID
	 */
	public final static String SYS_TDCCTP = "0003";
	/**
	 * 代理商系统ID
	 */
	public final static String SYS_TDPRM = "0002";

	/**
	 * 系统默认代理商ID
	 */
	public final static String SYS_AGENT_ID = "2015000000";

	/**
	 * 系统默认代理商最高权限角色ID
	 */
	public final static String SYS_AGENT_ADMIN_ROLE = "0002";

	/**
	 * 系统默认随机数
	 */
	public final static String SYS_RANDOM = "000000";
	/**
	 * 系统默认登录时间
	 */
	public final static String SYS_LASTLOGINTIME = "00000000000000";

	/**
	 * 系统默认密码
	 */
	public final static String SYS_PWD = "111111";

	/**
	 * 需要修改密码
	 */
	public final static int SYS_ISFIRSTLOGINFLAG_0 = 0;
	/**
	 * 不需要修改密码
	 */
	public final static int SYS_ISFIRSTLOGINFLAG_1 = 1;

}
