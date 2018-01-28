package com.tangdi.production.mpbatch.dao;

import java.util.Map;

/**
 * 跑批 订单交易统计
 * @author huchunyuan
 *
 */
public interface TranCountReportDao {
	/**
	 * 删除当前日期的订单交易
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delete(Map<String,Object> map) throws Exception;
	/**
	 * 添加最新的订单交易
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String,Object> map) throws Exception;

}
