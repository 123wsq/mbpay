package com.tangdi.production.mpapp.constants;

/**
 * 消息常量定义
 * 
 * @author zhengqiang
 * 
 */
public class MsgST extends com.tangdi.production.mpaychl.constants.MsgSubST {
	//费率代码START
	/**
	 * 手续费类型：T+0
	 */
	public final static String T0_TX_RATE_CODE = "T0_RATE_FEE";
	/**
	 * 手续费类型：T+1
	 */
	public final static String T1_TX_RATE_CODE = "T1_RATE_FEE";
	/**
	 * 手续费类型：节假日
	 */
	public final static String HOLIDAY_TX_RATE_CODE = "HOLIDAY_RATE_FEE";
	//费率代码END
	
	//短信START-----------------------------------------------
	/**
	 * 短信类型 : 01 注册
	 */
	public final static String SMS_TYPE_REGIST = "01";
	/**
	 * 短信类型 : 02 找回密码
	 */
	public final static String SMS_TYPE_PWD = "02";
	/**
	 * 短信类型 : 03 支付密码找回
	 */
	public final static String SMS_TYPE_PAY_PWD = "03";
	/**
	 * 短信类型 : 04 消费
	 */
	public final static String SMS_TYPE_PAY = "04";
	/**
	 * 短信类型 : 05 提现
	 */
	public final static String SMS_TYPE_TX = "05";
	
	/**
	 * 短信发送状态 : 00 未发送
	 */
	public final static String SMS_SEND_INIT = "00";
	/**
	 * 短信发送状态 : 01 已发送
	 */
	public final static String SMS_SEND_OK = "01";
	/**
	 * 短信发送状态 : 02 发送失败
	 */
	public final static String SMS_SEND_NG = "02";
	
	
	
	//短信 END-----------------------------------------------

	/**
	 * 路由状态 : 0 正常
	 */
	public final static String ROUTE_STATUS_0 = "0";
	/**
	 * 路由状态: 1 停用
	 */
	public final static String ROUTE_STATUS_1 = "1";
	/**
	 * 卡片类型 01 借记卡
	 */
	public final static String CARD_TYPE_01 = "01";	
	/**
	 * 卡片类型 02 贷记卡
	 */
	public final static String CARD_TYPE_02 = "02";	
	/**
	 * 卡片类型 03 准贷记卡
	 */
	public final static String CARD_TYPE_03 = "03";	
	/**
	 * 卡片类型 04 预付卡
	 */
	public final static String CARD_TYPE_04 = "04";	
	/**
	 * 卡片状态：默认使用
	 */
	public final static String CARD_STATUS_1 = "1";	
	/**
	 * 卡片状态：正常
	 */
	public final static String CARD_STATUS_2 = "0";	
	/**
	 * 卡片状态：不可使用
	 */
	public final static String CARD_STATUS_3= "-1";	
	/**
	 * 卡片绑定状态：0  未绑定
	 */
	public final static String CARD_BUNDING_STATUS_0 = "0";
	/**
	 * 卡片绑定状态：1  绑定未审核
	 */
	public final static String CARD_BUNDING_STATUS_1 = "1";	
	/**
	 * 卡片绑定状态：2  绑定
	 */
	public final static String CARD_BUNDING_STATUS_2 = "2";	
	/**
	 * 卡介质类型：01 磁条卡
	 */
	public final static String MEDIATYPE_MS = "01";
	/**
	 * 卡介质类型：02 IC卡
	 */
	public final static String MEDIATYPE_IC = "02";

	/**
	 * 认证状态 : 0未认证
	 */
	public final static String AUTH_STATUS_NO = "0";
	/**
	 * 认证状态: 1审核中
	 */
	public final static String AUTH_STATUS_IG = "1";
	/**
	 * 认证状态: 2审核通过
	 */
	public final static String AUTH_STATUS_OK = "2";
	/**
	 * 认证状态: 3审核不通过
	 */
	public final static String AUTH_STATUS_NG = "3";

	/**
	 * 商户登录状态:00 正常
	 */
	public final static String IS_LOGIN_OK = "00";

	/**
	 * 商户登录状态:01 冻结
	 */
	public final static String IS_LOGIN_NG = "01";
	
	/**
	 * 商户状态:0 正常
	 */
	public final static String CUST_STATUS_SUC = "0";

	/**
	 * 商户状态:1 禁用
	 */
	public final static String CUST_STATUS_FAL = "1";

	/**
	 * 银行卡操作类型：1 绑定
	 */
	public final static String OPER_TYPE_ADD = "1";
	/**
	 * 银行卡操作类型：2 修改
	 */
	public final static String OPER_TYPE_EDIT = "2";
	/**
	 * 银行卡操作类型：3 设为默认
	 */
	public final static String OPER_TYPE_DEFAULT = "3";
	/**
	 * 银行卡操作类型：4 删除
	 */
	public final static String OPER_TYPE_REMOVE = "4";
	/**
	 * 验证码操作类型：01 注册验证码
	 */
	public final static String VALIDATE_FOR_REGISTER = "01";
	/**
	 * 验证码操作类型：02 登录密码修改或找回
	 */
	public final static String VALIDATE_FOR_UPDATE_PWD = "02";
	/**
	 * 验证码操作类型：02 支付密码修改或找回
	 */
	public final static String VALIDATE_FOR_UPDATE_PAY_PWD = "03";
	/**
	 * 验证码状态0 未使用
	 */
	public final static String VALIDATE_CODE_STATUS_0 = "0";
	/**
	 * 验证码状态1 失效
	 */
	public final static String VALIDATE_CODE_STATUS_1 = "1";
	

	/**
	 * 子订单类型 彩票
	 */
	public final static String BIZTYPE_MOBILE_LOTTERY = "02";


	/**
	 * 费率 民生类 0.38%
	 */
	public final static String RATE_LIVELIHOOD = "1";

	/**
	 * 费率 一般类 0.78%
	 */
	public final static String RATE_GENERAL = "2";
	/**
	 * 费率 餐娱类 1.25%
	 */
	public final static String RATE_ENTERTAIN = "3";
	/**
	 * 费率 批发类 0.78% 封顶
	 */
	public final static String RATE_GENERAL_TOP = "4";
	/**
	 * 费率 房产汽车类 1.25% 封顶
	 */
	public final static String RATE_ENTERTAIN_TOP = "5";
	
	
	/**
	 * 商品订单状态 未交易
	 */
	public final static String ORDSTATUS_NO_TRANSACTION = "00";
	/**
	 * 商品订单状态 成功
	 */
	public final static String ORDSTATUS_SUCCESS = "01";

	/**
	 * 商品订单状态 失败
	 */
	public final static String ORDSTATUS_FAIL = "02";
	/**
	 * 商品订单状态 可疑
	 */
	public final static String ORDSTATUS_TIMEOUT = "03";

	/**
	 * 商品订单状态 处理中
	 */
	public final static String ORDSTATUS_PROCESSING = "04";

	/**
	 * 商品订单状态 已取消
	 */
	public final static String ORDSTATUS_CANCELED = "05";

	/**
	 * 商品订单状态 未支付
	 */
	public final static String ORDSTATUS_NO_PAY = "06";

	/**
	 * 商品订单状态 已退货
	 */
	public final static String ORDSTATUS_RETURNED = "07";
	/**
	 * 商品订单状态 退货中
	 */
	public final static String ORDSTATUS_RETURNING = "08";

	/**
	 * 商品订单状态 部分退货
	 */
	public final static String ORDSTATUS_PART_RETURN = "09";

	
	
	/**
	 * 支付订单状态 未交易 00
	 */
	public final static String PAYSTATUS_NO_PAY = "00";
	/**
	 * 支付订单状态 支付处理中 01
	 */
	public final static String PAYSTATUS_PAY_ING = "01";
	
	/**
	 * 支付订单状态 成功 02
	 */
	public final static String PAYSTATUS_SUCCESS = "02";
	/**
	 * 支付订单状态 失败03
	 */
	public final static String PAYSTATUS_FAIL = "03";
	/**
	 * 支付订单状态 冲正中 04
	 */
	public final static String PAYSTATUS_REVERSE_ING = "04";
	/**
	 * 支付订单状态 已冲正
	 */
	public final static String PAYSTATUS_REVERSE_OK = "05";
	/**
	 * 支付订单状态 冲正失败
	 */
	public final static String PAYSTATUS_REVERSE_NG = "06";

	/**
	 * 支付订单状态 可疑
	 */
	public final static String PAYSTATUS_TIMEOUT = "98";
	
	/**
	 * 支付订单状态 撤销成功
	 */
	public final static String PAYSTATUS_CANCELED = "09";
	
	
	/**
	 * 交易状态  预计 0
	 */
	public final static String TXNSTATUS_0 = "0";
	/**
	 * 交易状态  成功 S
	 */
	public final static String TXNSTATUS_S = "S";
	/**
	 * 交易状态  失败 F
	 */
	public final static String TXNSTATUS_F = "F";
	/**
	 * 交易状态  支付中 U
	 */
	public final static String TXNSTATUS_U = "U";
	/**
	 * 交易状态  冲正 C
	 */
	public final static String TXNSTATUS_C = "C";
	/**
	 * 交易状态  撤销 R
	 */
	public final static String TXNSTATUS_R = "R";
	/**
	 * 交易状态  超时 T
	 */
	public final static String TXNSTATUS_T = "T";
	/**
	 * 交易状态  完成 E
	 */
	public final static String TXNSTATUS_E = "E";
	
	/**
	 * 交易类型  消费
	 */
	public final static String TXNTYPE_CONSUME= "01";
	/**
	 * 交易类型  退款
	 */
	public final static String TXNTYPE_REFUND= "02";
	/**
	 * 交易类型  冲正
	 */
	public final static String TXNTYPE_REVERS= "03";
	
	/**
	 * 终端状态  入库
	 */
	public final static String TERMINALSTATUS = "0";
	/**
	 * 终端状态 出库
	 */
	public final static String TERMINALSTATUS_NO_BIND = "1";
	/**
	 * 终端状态 绑定
	 */
	public final static String TERMINALSTATUS__BIND = "2";

	/**
	 * 账户变更类型 1001-收款
	 */
	public final static String CHANGE_TYP_1001 = "1001";

	/**
	 * 账户变更类型 1002-其他虚拟账户转入
	 */
	public final static String CHANGE_TYPE_1002 = "1002";

	/**
	 * 账户变更类型 1003-退款
	 */
	public final static String CHANGE_TYPE_1003 = "1003";

	/**
	 * 账户变更类型 2001-提现
	 */
	public final static String CHANGE_TYPE_2001 = "2001";

	/**
	 * 账户变更类型 2002-虚拟账户转出
	 */
	public final static String CHANGE_TYPE_2002 = "2002";
	/**
	 * 账户变更类型 2003-虚拟账号支付
	 */
	public final static String CHANGE_TYPE_2003 = "2003";
	
	/**
	 * 提现订单状态 00未处理
	 */
	public final static String CAS_ORD_STATUS_00 = "00";
	/**
	 * 提现订单状态 01待清算
	 */
	public final static String CAS_ORD_STATUS_01 = "01";
	/**
	 * 提现订单状态 02清算失败
	 */
	public final static String CAS_ORD_STATUS_02 = "02";
	/**
	 * 提现订单状态 03可疑
	 */
	public final static String CAS_ORD_STATUS_03 = "03";
	/**
	 * 提现订单状态 04审核中
	 */
	public final static String CAS_ORD_STATUS_04 = "04";
	/**
	 * 提现订单状态 05审核拒绝
	 */
	public final static String CAS_ORD_STATUS_05 = "05";
	/**
	 * 提现订单状态 06清算中
	 */
	public final static String CAS_ORD_STATUS_06 = "06";
	/**
	 * 提现订单状态 06清算完成
	 */
	public final static String CAS_ORD_STATUS_07 = "07";
	
	/**
	 * 提现审核状态 01通过
	 */
	public final static String CAS_ADU_STATUS_PASS = "02";
	/**
	 * 提现审核状态 02不通过
	 */
	public final static String CAS_ADU_STATUS_NOPASS = "01";
	/**
	 * 提现审核状态 00审核中
	 */
	public final static String CAS_ADU_STATUS_ING = "00";
	
	
	
	/**
	 * 商户等级 00：试用期商户
	 */
	public final static String MER_CLASS_00 = "00";
	/**
	 * 商户等级 01：正式商户
	 */
	public final static String MER_CLASS_01 = "01";
	
	/**
	 * 参数值  1  百分比计算
	 */
	public final static String PARA_VAL_TYPE_1 = "1";
	
	/**
	 * 参数值  2 定额（分）计算
	 */
	public final static String PARA_VAL_TYPE_2 = "2";
	

	
	/**
	 * 终端已购买
	 */
	public final static String TERM_PAY_FLAG_OK = "2";
	/**
	 * 终端未购买
	 */
	public final static String TERM_PAY_FLAG_NG = "1";
	
	/**
	 * 限制
	 */
	public final static String CAS_TO_LIMIT = "1";
	/**
	 * 不限制
	 */
	public final static String CAS_TO_NOLIMIT = "2";
	
   //子业务类型-----------------------------------------------------
	/**
	 * 刷卡头购买
	 */
	public final static String BIZTYPE_BUY_TERM = "800001";
	
	/**
	 * app平台软件  IOS
	 */
	public final static String APP_PLATFORM_IOS = "2";
	/**
	 * app平台软件  android
	 */
	public final static String APP_PLATFORM_ANDROID = "1";
	
	
	

}
