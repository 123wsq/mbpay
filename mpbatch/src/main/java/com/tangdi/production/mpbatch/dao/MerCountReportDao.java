package com.tangdi.production.mpbatch.dao;

import java.util.Map;

/**
 * 跑批 商户注册量
 * @author huchunyuan
 *
 */
public interface MerCountReportDao {
	/**
	 * 删除当前日期的商户注册量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delete(Map<String,Object> map) throws Exception;
	/**
	 * 添加最新的商户注册量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String,Object> map) throws Exception;

}
