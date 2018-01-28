package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>支付撤销基类接口</b><br>
 * 接口规范支持：支付撤销。
 * 
 * @author chenlibo 2015/3/18
 *
 */
public interface PayCancelService {

	/**
	 * <b>支付撤销</b><br/>
	 * @param param 支付撤销参数:详细参数待定
	 * @return 支付撤销结果:详细参数待定
	 * @throws TranException 支付撤销异常
	 */
	public Map<String,Object> tranCancel(Map<String,Object> param) throws TranException;

}
