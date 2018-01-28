package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * APP登陆信息记录DAO
 * @author zhengqiang
 *
 */
public interface LoginDao {
	/**
	 * 插入用户登陆信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String,Object> map) throws Exception;

}
