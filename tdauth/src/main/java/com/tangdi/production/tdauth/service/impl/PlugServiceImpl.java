package com.tangdi.production.tdauth.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.tdauth.dao.PlugDao;
import com.tangdi.production.tdauth.service.PlugService;
@Service
public class PlugServiceImpl implements PlugService{
	
	@Autowired
	private PlugDao plugDao;
	
	/**
	 * 获取省市信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getProvince(Map<String,Object> paramMap) throws Exception{
		return plugDao.getProvince(paramMap);
	}
}
