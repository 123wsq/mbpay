package com.tangdi.production.mpbatch.dao;

import java.util.Map;

/**
 * 跑批 商户等级量
 * @author huchunyuan
 *
 */
public interface MerClassReportDao {
	/**
	 * 删除当前日期的统计数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delete(Map<String,Object> map) throws Exception;
	/**
	 * 添加最新日期的统计数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String,Object> map) throws Exception;

}
