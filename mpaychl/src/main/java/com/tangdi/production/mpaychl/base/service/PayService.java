package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>支付基类接口</b><br>
 * 接口规范支持：快捷支付和终端支付方法。
 * 
 * @author zhengqiang 2015/3/18
 *
 */
public interface PayService {

	/**
	 * <b>支付</b><br/> 快捷支付和终端支付都实现该方法。
	 * @param param 支付参数:详细参数待定
	 * @return 支付结果:详细参数待定
	 * @throws TranException 支付异常
	 */
	public Map<String,Object> pay(Map<String,Object> param) throws TranException;

}
