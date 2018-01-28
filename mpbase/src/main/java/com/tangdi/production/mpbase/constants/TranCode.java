package com.tangdi.production.mpbase.constants;

/**
 * 主控交易代码定义
 * 
 * @author shan
 *
 */
public class TranCode {

	/**
	 * 银联或第三方合作机构终端签到交易 150001
	 */
	public static final String TRAN_SIGN = "150001";

	/**
	 * 终端密钥下发交易 150002
	 */
	public static final String TRAN_TERMKEY = "150002";

	/**
	 * 发送推荐码短信 150003
	 */
	public static final String TRAN_SENDSMS = "150003";

	/**
	 * 冲正交易 110001
	 */
	public static final String TRAN_RDO = "110001";
	
	/**
	 * 实时代付远程调用110002
	 * */
	public static final String TRAN_PAY = "110002";
	
	/**
	 * 确认代付远程调用110003
	 * */
	public static final String TRAN_CONPAY = "110003";
	/**
	 * 额度查询远程调用110004
	 * */
	public static final String TRAN_LMTQRY = "110004";
	/**
	 * 实时代付异步800003
	 * */
	public static final String TRAN_PAY_ASYN = "800003";
	/**
	 * 报表交易 800001
	 */
	public static final String TRAN_REPORT = "800001";

	/**
	 * 创建传输密钥文件交易 800002
	 */
	public static final String TRAN_CREATE_KEY_FILE = "800002";

	/**
	 * 重新调用 日分润
	 */
	public static final String TRAN_CALL_DAYSHARE = "080001";

	/**
	 * 重新调用 月分润
	 */
	public static final String TRAN_CALL_MONTHSHARE = "080002";

	/**
	 * 重新调用 清算(上一个工作日)
	 */
	public static final String TRAN_CALL_SETTLE = "080003";
	
	/**
	 * 额度查询
	 * */
	public static final String TRAN_LIMIT_QUERY = "800004";
    
	
	/**
	 * 运营平台用户注册是外发posp
	 */
	public static final String  POSP_USER_ADD= "151001";
	/**
	 * 运营平台用户删除外发posp
	 */
	public static final String  POSP_USER_DEL= "151002";
	/**
	 * 运营平台用户密码重置外发posp
	 */
	public static final String  POSP_USER_RES= "151003";
	/**
	 * 运营平台用户修改状态重置外发posp
	 */
	public static final String  POSP_USER_STATUS= "151004";
	/**
	 * 运营平台用户修改密码外发posp
	 */
	public static final String  POSP_USER_UPD= "151005";
}
