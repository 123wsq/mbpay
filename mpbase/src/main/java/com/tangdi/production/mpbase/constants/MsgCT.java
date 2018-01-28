package com.tangdi.production.mpbase.constants;

/**
 * 自定义常量
 * 
 * @author zhengqiang
 *
 */
public class MsgCT {

	/**
	 * 路由类别：01 签到
	 */
	public static final String RTR_TYPE_SIGN = "01";
	/**
	 * 路由类别：02 消费
	 */
	public static final String RTR_TYPE_CONSUME = "02";
	/**
	 * 路由类别：03 冲正
	 */
	public static final String RTR_TYPE_REVERSE = "03";
	/**
	 * 路由类别：04 余额查询
	 */
	public static final String RTR_TYPE_QUERY = "04";
	/**
	 * 路由类别：05 撤销
	 */
	public static final String RTR_TYPE_UNDO = "05";

	/**
	 * 路由类别：06 退货
	 */
	public static final String RTR_TYPE_REFUND = "06";
	/** 
	 * 路由类别：07 代付
	 */
	public static final String RTR_TYPE_CASPAY = "07";
	/**
	 * 路由类别：08确认代付
	 * */
	public static final String RTR_TYPE_CONPAY = "08";
	/**
	 * 费率状态：00 正常
	 */
	public static final String RATE_ST_00 = "00";
	/**
	 * 费率状态：01 审核或处理中
	 */
	public static final String RATE_ST_01 = "01";
	/**
	 * 费率状态：02 历史
	 */
	public static final String RATE_ST_02 = "02";

	/**
	 * 费率类型：CH00 供余额查询使用
	 */
	public static final String RATE_TYPE_CH00 = "CH00";

	/**
	 * 费率类型：CH01 一般类
	 */
	public static final String RATE_TYPE_CH01 = "CH01";
	/**
	 * 费率类型：CH02 民生类
	 */
	public static final String RATE_TYPE_CH02 = "CH02";
	/**
	 * 费率类型：CH03 批发类封顶手续费
	 */
	public static final String RATE_TYPE_CH03 = "CH03";
	/**
	 * 费率类型：CH04 餐娱类费率
	 */
	public static final String RATE_TYPE_CH04 = "CH04";

	// ---------------------------------------------------------
	/**
	 * 文件生成状态：0 生成中
	 */
	public static final String FILE_CREATE_ING = "0";

	/**
	 * 文件生成状态：1 失败
	 */
	public static final String FILE_CREATE_FAILED = "1";

	/**
	 * 文件生成状态：2 完成
	 */
	public static final String FILE_CREATE_SUCCESS = "2";

	/**
	 * 报表类型: 1 交易报表
	 */
	public static final String REPORT_TYPE_1 = "1";
	/**
	 * 报表类型: 2 数据报表
	 */
	public static final String REPORT_TYPE_2 = "2";
	/**
	 * 报表类型: 3 密钥文件
	 */
	public static final String REPORT_TYPE_3 = "3";

	/**
	 * 文件类型: 1 CSV
	 */
	public static final String FILE_TYPE_CSV = "1";
	/**
	 * 文件类型: 2 EXCEL
	 */
	public static final String FILE_TYPE_EXCEL = "2";

	/**
	 * 文件类型: 3 TXT
	 */
	public static final String FILE_TYPE_TXT = "3";

	/**
	 * 文件类型: 4 ZIP
	 */
	public static final String FILE_TYPE_ZIP = "4";
	/**
	 * 交易平台渠道返回码Key RETCODE
	 */
	public static final String RETCOE = "RETCODE";
	/**
	 * 交易平台渠道 000000 成功
	 */
	public static final String ERT_SUCCESS = "000000";

	// -------------------------------------------------------

	/**
	 * 文件类型 JPEG 图片
	 */
	public static final String FILE_TYPE_JPEG = "JPEG";
	/**
	 * 文件类型 PNG 图片
	 */
	public static final String FILE_TYPE_PNG = "PNG";
	/**
	 * 文件类型 GIF 图片
	 */
	public static final String FILE_TYPE_GIF = "GIF";

	/**
	 * 文件类型 BMP 图片
	 */
	public static final String FILE_TYPE_BMP = "BMP";

	/**
	 * 文件类型 其他
	 */
	public static final String FILE_TYPE_OTHER = "FIL";

	/**
	 * 图片后缀名 JPG
	 */
	public final static String PIC_SUFFIX_JPG = ".jpg";
	/**
	 * 图片后缀名 PNG
	 */
	public final static String PIC_SUFFIX_PNG = ".png";

	/**
	 * 存储类型 1身份证
	 */
	public final static int PIC_TYPE_CERT = 1;
	/**
	 * 存储类型 2银行卡
	 */
	public final static int PIC_TYPE_CARD = 2;
	/**
	 * 存储类型 3 轮播图
	 */
	public final static int PIC_TYPE_IMG = 3;
	/**
	 * 存储类型 4电子签名
	 */
	public final static int PIC_TYPE_ESIGN = 4;
	/***
	 * 存储类型 5白名单
	 */
	public final static int PIC_TYPE_WHITELIST = 5;

	public final static int PIC_TYPE_FILE = 5;

	/**
	 * 存储类型 6 其它(不需要地址转换)
	 */
	public final static int PIC_TYPE_OTHER = 6;

	// ------------------------------------------------------
	/**
	 * 银行卡审核信息成功
	 */
	public static final String BANK_CARD_AUDIT_SUCCESS = "0";
	/**
	 * 银行卡审核信息失败
	 */
	public static final String BANK_CARD_AUDIT_FAIL = "1";

	/**
	 * 金额存储单元
	 */
	public static final String MONEY_UNIT = "100";

	/**
	 * 账户余额变动类型 1001-收款
	 * 
	 */
	public static final String ACCOUT_CHANGE_TYPE_1001 = "1001";
	/**
	 * 账户余额变动类型 1002-其他虚拟账户转入
	 */
	public static final String ACCOUT_CHANGE_TYPE_1002 = "1002";
	/**
	 * 账户余额变动类型1003-退款
	 */
	public static final String ACCOUT_CHANGE_TYPE_1003 = "1003";
	/**
	 * 账户余额变动类型 1004-分润清算
	 */
	public static final String ACCOUT_CHANGE_TYPE_1004 = "1004";
	/**
	 * 账户余额变动类型 2001-提现
	 */
	public static final String ACCOUT_CHANGE_TYPE_2001 = "2001";
	/**
	 * 账户余额变动类型 2002-虚拟账户转出
	 */
	public static final String ACCOUT_CHANGE_TYPE_2002 = "2002";
	/**
	 * 账户余额变动类型 2003-虚拟账号支付
	 */
	public static final String ACCOUT_CHANGE_TYPE_2003 = "2003";
	/**
	 * 账户余额变动类型 3001-T0审核退回
	 */
	public static final String ACCOUT_CHANGE_TYPE_3001 = "3001";
	/**
	 * 账户余额变动类型 3011-T0审核通过减余额
	 */
	public static final String ACCOUT_CHANGE_TYPE_3011 = "3011";
	/**
	 * 账户余额变动类型 3002-提现订单当天未清算退回
	 */
	public static final String ACCOUT_CHANGE_TYPE_3002 = "3002";

	/**
	 * 账户余额变动类型 3003-T1余额转T1Y
	 */
	public static final String ACCOUT_CHANGE_TYPE_3003 = "3003";

	/**
	 * 账户余额变动类型 3004-商户分润金额转T1
	 */
	public static final String ACCOUT_CHANGE_TYPE_3004 = "3004";

	/**
	 * 账户余额变动类型 3005-T1Y自动提现
	 */
	public static final String ACCOUT_CHANGE_TYPE_3005 = "3005";

	/**
	 * 账户余额变动类型 4001-清算失败
	 */
	public static final String ACCOUT_CHANGE_TYPE_4001="4001";
	
	/**
	 * 日切开始时间
	 */
	public static final String DAY_TIME_START = "230000";
	/**
	 * 日切结束时间
	 */
	public static final String DAY_TIME_END = "225959";

	/**
	 * 手续费最低收取1分
	 */
	public static final double FEE_LOW = 0;

	/**
	 * 交易最大金额[10000000.00]元。
	 */
	public static final double AMT_HIG = 1000000000;

	/**
	 * 订单类型 收款
	 */
	public final static String PRDORDTYPE_PAYMENTS = "01";

	/**
	 * 订单类型 商品( 便民商品 类)
	 */
	public final static String PRDORDTYPE_GOODS = "02";
	/**
	 * 订单类型 提现
	 */
	public final static String PRDORDTYPE_CASH = "03";

	/**
	 * 支付方式 支付账户
	 */
	public final static String PAYTYPE_AC = "01";

	/**
	 * 支付方式 终端
	 */
	public final static String PAYTYPE_TE = "02";

	/**
	 * 支付方式 快捷支付
	 */
	public final static String PAYTYPE_LI = "03";

	/**
	 * 提现方式 00 T+0
	 */
	public final static String CAS_TYPE_T0 = "00";
	/**
	 * 提现方式 01 T+1
	 */
	public final static String CAS_TYPE_T1 = "01";
	/**
	 * 提现方式 02 T+1和T+0混合
	 */
	public final static String CAS_TYPE_ALL = "02";

	/**
	 * 默认交易失败代码。[返回码转换使用]
	 */
	public final static String TRAN_FAILD = "99";

	/**
	 * 日切开始时间
	 */
	public static final String DAY_START_TIME = "000000";
	/**
	 * 日切结束时间
	 */
	public static final String DAY_END_TIME = "235959";
	
	public static final String RETCODE_OK = "0";
	
	/**
	 * 剩余额度查询
	 * **/
	public static final String LIMIT_QUERY = "04";
	
	/**
	 * 未支付
	 * **/
	public static final String PAY_STATUS_U = "00";
	
	/**
	 * 支付成功
	 * **/
	public static final String PAY_STATUS_S = "02";
	
	/**
	 * 支付失败
	 * **/
	public static final String PAY_STATUS_F = "03";
	
	/**
	 * 订单支付中
	 * **/
	public static final String PRD_STATUS_U = "00";
	
	/**
	 * 订单成功
	 * **/
	public static final String PRD_STATUS_S = "01";
	
	/**
	 * 订单失败
	 * **/
	public static final String PRD_STATUS_F = "02";
	
	/**
	 * 订单类型 所有账户
	 */
	public final static String AC_TYPE_ALL = "01";

	/**
	 * 订单类型 普通收单账户
	 */
	public final static String AC_TYPE_PT = "02";
	/**
	 * 订单类型 快捷交易账户
	 */
	public final static String AC_TYPE_KJ = "03";
	
	/**
	 * 账户类型 扫码支付账户
	 */
	public final static String AC_TYPE_SM = "04";
	
	/**
	 * 支付方式 支付账户
	 */
	public final static String PAYTYPE_DEF = "01";

	/**
	 * 支付方式 终端
	 */
	public final static String PAYTYPE_TM = "02";

	/**
	 * 支付方式 快捷支付
	 */
	public final static String PAYTYPE_KJ = "03";
	
	/**
	 * 支付方式 扫码
	 */
	public final static String PAYTYPE_SM = "04";
	
	/**
	 * 费率类型 代理商
	 */
	public final static String RATECODE_AGE = "00";
	
	/**
	 * 费率类型 商户
	 */
	public final static String RATECODE_MER = "01";

}