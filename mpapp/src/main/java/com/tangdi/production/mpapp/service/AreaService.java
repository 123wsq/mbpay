package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;



/**
 * 省市级联接口
 * @author zhuji
 *
 */
public interface AreaService {
	/**
	 * 获取省市列表
	 * @return
	 * @throws TranException
	 */
	public List<Map<String, Object>> getProvince() throws TranException;

}
