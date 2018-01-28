package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * 省市级联接口
 * @author zhuji
 *
 */
public interface AreaDao {
	
	/**
	 * 查询省
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getProvince() throws Exception ;
}
