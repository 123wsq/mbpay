package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * 获取交易记录
 * @author huchunyuan
 *
 */
public interface TranOrderDao {
	
	/**
	 * 交易信息查询
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>>  selectList(Map<String,Object> param) throws Exception ;
	
	/**
	 * 商品订单和收款订单详情
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object>  selectPrdDetail(Map<String,Object> param) throws Exception ;
	
	/**
	 * 提现订单详情
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object>  selectCasDetail(Map<String,Object> param) throws Exception ;
	
	public int selectCount(Map<String,Object> param) throws Exception ;
	
	/**
	 * 查询订单支付状态
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object>  selectPrdPayStatus(Map<String,Object> param) throws Exception ;
}
