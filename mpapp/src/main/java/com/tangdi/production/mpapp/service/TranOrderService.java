package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;



/**
 * 交易信息查询
 * @author huchunyuan
 *
 */
public interface TranOrderService {
	
	/**
	 *获取交易信息
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>>  queryList(Map<String,Object> param) throws Exception ;
	
	/**
	 * 交易详情
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  queryDetail(Map<String,Object> param) throws Exception ;

	public String redDot(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询询扫码支付结果
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  querySMPayResult(Map<String,Object> param) throws Exception ;


}
