package com.tangdi.production.mpomng.dao;

import java.util.Map;


/**
 * 商户信息管理接口
 * @author zhengqiang
 *
 */
public interface MerchantDao {
	
	/**
	 * 查询用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectEntity(Map<String,Object> param) throws Exception ;
	
	/**
	 * 更新用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String,Object> param) throws Exception ;
	
	/**
	 * 商户信息添加
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String,Object> param) throws Exception ;
	
}
