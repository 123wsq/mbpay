package com.tangdi.production.mpapp.dao;

import java.util.Map;


/**
 * 费率dao
 * @author zhuji
 *
 */
public interface PlatformParameterDao {
	
	/**
	 * 查询费率信息
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object>  selectEntity(Map<String , Object> param) throws Exception ;
	
}
