package com.tangdi.production.mpbase.constants;

/**
 * map传参时约定属性的key常量
 * @author xiejinzhong
 *
 */
public interface MapKey {
	/**
	 * map参数中key值对应的属性：商户Id
	 */
	String CUST_ID = "custId";
	/**
	 * map参数中key值对应的属性：商户等级
	 */
	String MER_CLASS = "merclass";
	/**
	 * map参数中key值对应的属性：代理商Id
	 */
	String AGENT_ID = "agentId";
	/**
	 * map参数中key值对应的属性：终端号
	 */
	String TERM_NO = "termNo";
	/**
	 * map参数中key值对应的属性：业务类型
	 */
	String PRDORD_TYPE = "prdordType";
	/**
	 * map参数中key值对应的属性：子业务类型
	 */
	String BIZ_TYPE = "bizType";
	/**
	 * map参数中key值对应的属性：支付方式
	 */
	String PAY_TYPE = "payType";
	/**
	 * map参数中key值对应的属性：银行卡号
	 */
	String CRAD_NO = "cradNo";
	/**
	 * map参数中key值对应的属性：交易金额
	 */
	String PAY_AMT = "payAmt";
}
