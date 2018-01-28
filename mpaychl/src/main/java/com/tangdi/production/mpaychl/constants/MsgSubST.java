package com.tangdi.production.mpaychl.constants;

import com.tangdi.production.mpbase.constants.MsgCT;

/**
 * 消息常量定义
 * 
 * @author zhengqiang
 * 
 */
public class MsgSubST extends MsgCT {
	
	//------------CallOther和通通道返回码定义 start----------------------------------------------
	/**
	 * CallOther 返回状态 : 0  正常返回
	 */
	public final static String RETCODE_OK = "0";
	
	/**
	 * CallOther 返回状态 : -1  要发送的渠道不存在
	 */
	public final static String RETCODE_NG = "-1";
	
	/**
	 * CallOther 返回状态 : 1  发送超时
	 */
	public final static String RETCODE_ERROR = "1";
	
	/**
	 * CallOther 返回状态 : 2  接收数据超时,返回报文长度不正确
	 */
	public final static String RETCODE_TIMEOUT = "2";
	
	/**
	 * CallOther 返回状态 : 10  连接异常
	 */
	public final static String RETCODE_CONNECT_EXP = "10";
	
	
	
	/**
	 * 银行状态 : 00  成功
	 */
	public final static String BANK_TRAN_OK = "00";
	
	/**
	 * 银行状态 : 09 处理中
	 */
	public final static String BANK_TRAN_PROCESSING = "09";
	
	/**
	 * 银行状态 : 默认 01 失败 
	 */
	public final static String BANK_TRAN_FAIL = "01";
	
	/**
	 * 银行代付状态 : ERR0000  成功
	 */
	public final static String BANK_TCAS_OK = "ERR0000";
	
	//------------CallOther和通通道返回码定义 end----------------------------------------------





	// 银联39域消息代码
	/**
	 * 银联39:
	 * <P>
	 * 交易成功
	 */
	public final static String UN_RT_SUCCESS = "00";

	// 证件类型-------------------------------------------------------------------------
	/**
	 * 01 身份证
	 */
	public final static String CRED_CID = "01";
	/**
	 * 护照
	 */
	public final static String CRED_PASS = "02";
	/**
	 * 军官证
	 */
	public final static String CRED_OFFI = "03";
	// 01 身份证 02护照 03军官证

	// 支付流水表 [交易类型]------------------------------------------------------------

	/**
	 * [卡类型] :磁条卡
	 */
	public final static String CARD_TYPE_1 = "01";
	/**
	 * [卡类型] :IC卡
	 */
	public final static String CARD_TYPE_2 = "02";
	
	/**
	 * MAC 运算模式CBC
	 */
	public final static String MAC_MODE_CBC = "CBC";
	/**
	 * MAC 运算模式ECB
	 */
	public final static String MAC_MODE_ECB = "ECB";
	
	/**
	 * 中联 response json名称
	 */
	public final static String ZL_RSP_NM = "ZLRspJson";

	
	
}
