package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 终端[手机发起的]商品订单接口
 * 
 * @author zhengqiang
 *
 */
public interface TermPrdService {
	/**
	 * 创建商品订单
	 * 
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public String createPrd(Map<String, Object> map) throws TranException;

	/**
	 * 更新商品订单
	 * 
	 * @param map
	 *            payNo 支付订单号 ordStatus 商品订单状态 
	 *            prdordNo 商品订单号
	 * @return
	 * @throws TranException
	 */
	public Integer modifyPrd(Map<String, Object> map) throws TranException;

	/**
	 * 商品订单不可重复支付校验
	 * @param pmap
	 *            prdordNo 商品订单号 custId 商户号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> check(Map<String, Object> pmap) throws TranException;
	
	/**
	 * 商品订单可重复支付校验。
	 * @param pmap
	 *            prdordNo 商品订单号 custId 商户号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> check(Map<String, Object> pmap, int flag) throws TranException;
	
	/**
	 * 根据商品订单号查找订单
	 * @param pmap
	 *            prdordNo 商品订单号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> queryPrdByOrdNo(Map<String, Object> pmap) throws TranException;
}
