package com.tangdi.production.mpapp.service;

import java.util.Map;


/**
 *app信息获取接口
 * @author huchunyuan
 *
 */
public interface AppService {
	
	/**
	 * 获取app信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String,Object> pmap) throws Exception;

}
