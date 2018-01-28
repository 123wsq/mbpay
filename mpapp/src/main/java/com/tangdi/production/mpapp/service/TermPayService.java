package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * 终端[手机发起的]支付订单接口
 * 
 * @author zhengqiang
 *
 */
public interface TermPayService {

	/**
	 * 创建支付订单
	 * 
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public String createPay(Map<String, Object> map) throws TranException;

	/**
	 * 更新支付订单
	 * 
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public int modifyPay(Map<String, Object> map) throws TranException;
	
	/**
	 * 更新支付订单处理中
	 * 
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public int modifyPaying(Map<String, Object> map) throws TranException;
	
	/**
	 * 冲正时更新支付订单
	 * 
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public int modifyPayByRedo(Map<String, Object> map) throws TranException;
	
	/**
	 * 更新电子签名
	 * @param fid     文件ID
	 * @param payordno支付订单号
	 * @return
	 * @throws TranException
	 */
	public int modifyESign(String payordno ,String fid) throws TranException;
	
	
	/**
	 * 查询支付订单
	 * 
	 * @param map
	 *            prdordNo 商品订单号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> queryPayOrdById(Map<String, Object> map) throws TranException;
	
	/**
	 * 查询支付订单和对应账户余额
	 * 
	 * @param map
	 *            prdordNo 商品订单号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> queryPayInfWithAccById(Map<String, Object> map) throws TranException;

}
