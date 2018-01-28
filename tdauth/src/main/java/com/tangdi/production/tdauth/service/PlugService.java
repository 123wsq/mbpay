package com.tangdi.production.tdauth.service;

import java.util.List;
import java.util.Map;

public interface PlugService {
	/**
	 * 获取省市信息
	 * 	paramMap.put("provinceId", provId);//省份Id
		paramMap.put("cityId", cityId); 城市ID
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getProvince(Map<String,Object> paramMap) throws Exception;
}
