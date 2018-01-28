package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;


/**
 * 首页获取轮播图接口
 * @author huchunyuan
 *
 */
public interface AppImageService {
	
	/**
	 * 获取轮播图
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList() throws Exception;
}
