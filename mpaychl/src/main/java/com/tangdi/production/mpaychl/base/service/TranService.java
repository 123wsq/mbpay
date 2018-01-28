package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>交易服务基础类接口</b></br>
 * 接口规范支持：签到[下发工作密钥]、支付、余额查询</br></br>
 * 接口继承:TermSignService(设备签到接口)、BankSignService(通道签到接口)、PayService(支付基类接口)、BankCardBalanceService(银行卡余额管理基类接口)
 * 
 * @author zhengqiang 2015/3/25
 *
 */
public interface TranService extends PayService,BankCardBalanceService,SignService,PayCancelService{
	/**
	 * 终端实现类KEY
	 */
	public final static String TERM_SERVICE_NAMEKEY = "termServiceName";
	
	/**
	 * 自动冲正调用接口（系统调用）
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> reverse(Map<String,Object> param) throws TranException;
	
	/**
	 * 冲正接口, 将冲正数据写入DB中。
	 * @param param
	 * @throws TranException
	 */
	public void redo(Map<String,Object> param) throws TranException;
	
	/**
	 * 实时代付接口
	 * */
	public Map<String,Object> casPay(Map<String,Object> param) throws TranException;
	
	/**
	 * 确认实时代付接口
	 * */
	public Map<String,Object> conCasPay(Map<String,Object> param) throws TranException;

	/**
	 * 额度查询接口
	 * */
	public Map<String, Object> limitQuery(Map<String, Object> param) throws TranException;
	
	/**
	 * 微信扫码接口
	 * */
	public Map<String, Object> wxsmPay(Map<String, Object> param) throws TranException;
	
	/**
	 * 微信扫码查询接口
	 * */
	public Map<String, Object> wxsmQuery(Map<String, Object> param) throws TranException;
	
}
