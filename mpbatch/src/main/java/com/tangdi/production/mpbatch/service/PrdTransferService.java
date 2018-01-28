package com.tangdi.production.mpbatch.service;

/***
 * 未处理订单转到未处理支付订单表
 * @author Administrator
 *
 */
public interface PrdTransferService {
	/**
	 * 未处理的支付订单转移到未处理支付订单表
	 */
	public void process() throws Exception;
}
