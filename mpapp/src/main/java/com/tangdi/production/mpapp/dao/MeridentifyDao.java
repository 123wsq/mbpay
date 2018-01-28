package com.tangdi.production.mpapp.dao;

import java.util.Map;



/**
*
* 实名认证信息数据访问
*
* @author zhuji
* @version 1.0
*/
public interface MeridentifyDao{
	/**
	 * 新建实名认证信息
	 * @param param
	 * @return
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;
	/**
	 * 更新实名认证信息
	 * @param param
	 * @return
	 */
	public int updateEntity(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询实名认证信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectEntity(Map<String, Object> param) throws Exception;
}
