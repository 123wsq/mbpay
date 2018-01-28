package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

/**
 * app版本维护获取
 * 
 * @author huchunyuan
 *
 */
public interface AppDao {

	/**
	 * 查询app信息 最新数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity() throws Exception;

	/***
	 * 根据appName获取app信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectAppInf(Map<String, Object> map);

}
