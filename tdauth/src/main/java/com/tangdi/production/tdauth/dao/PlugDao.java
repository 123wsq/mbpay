package com.tangdi.production.tdauth.dao;

import java.util.List;
import java.util.Map;

public interface PlugDao {
	public List<Map<String, Object>> getProvince(Map<String,Object> paramMap) throws Exception;
}
