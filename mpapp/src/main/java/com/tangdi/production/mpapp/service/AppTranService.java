package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * APP通道交易接口[银行签到、终端签到、余额查询、消费] 、电子签名接口
 * @author zhengqiang
 *
 */
public interface AppTranService {
	

	/**
	 * 支付
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> payment(Map<String,Object> param) throws TranException;
	
	/**
	 * 通道方签到
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> banksign(Map<String,Object> param) throws TranException;
	
	/**
	 * 余额查询
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> query(Map<String,Object> param) throws TranException;
	
	/**
	 * 终端设备签到
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> termsign(Map<String,Object> param) throws TranException;

	/**
	 * 提现交易
	 * @param param
	 * @throws TranException
	 */
	public void txTran(Map<String , Object> param) throws TranException; 
	
	/**
	 * 电子签名
	 * @param param
	 * @throws TranException
	 */
	public void esign(Map<String,Object> param) throws TranException;
	/**
	 * 余额查询
	 * @param param
	 * @return map acT0  acT1  acT1Y acBal	(t0余额 t1未到账 t1未提  总余额)
	 */
	public Map<String, Object> getAcBalance(Map<String, Object> param) throws TranException;
	/**
	 * 余额查询 为空不抛异常
	 * @param param
	 * @return map acT0  acT1  acT1Y acBal	(t0余额 t1未到账 t1未提  总余额)
	 */
	public Map<String, Object> getAcBalance2(Map<String, Object> param) throws TranException;
	/**
	 * 计算手续费
	 * @param txAmt(金额) custId(商户id)
	 * @return 手续费
	 */
	public Map<String, Object> getPoundage(Map<String , Object> param) throws TranException; 
	
	/**
	 * 冲正接口[自动冲正调用]
	 * @param param
	 * @return 
	 * @throws TranException
	 */
	public Map<String,Object> reverse(Map<String,Object> param) throws TranException;

	public void upBankCard(Map<String, Object> param) throws TranException;
	
	/**
	 * 微信扫码支付
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> wxsmPayment(Map<String,Object> param) throws TranException;
	
	/**
	 * 微信扫码支付结果查询
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> wxsmQuery(Map<String,Object> param) throws TranException;
	
	/**
	 * 微信扫码支付结果处理
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> wxsmResultHandler(Map<String,Object> param) throws TranException;
	
}
