package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * 短信验证码接口
 * @author zhuji
 *
 */
public interface ValidateCodeDao {
	
	/**
	 * 查询验证码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectEntity(Map<String,Object> param) throws Exception ;
	
	
	/**
	 * 插入验证码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String,Object> param) throws Exception ;
	
    /**
     * 更新验证码状态
     * @param param
     * @return
     * @throws Exception
     */
	public int updateEntity(Map<String,Object> param) throws Exception; 
}
