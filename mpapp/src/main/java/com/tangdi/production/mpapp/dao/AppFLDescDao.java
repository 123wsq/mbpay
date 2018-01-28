package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 终端费率描述信息管理
 * 
 * @author chenlibo
 * 
 * @version 1.0
 */
public interface AppFLDescDao {

	/**
	 * 查询终端费率描述信息
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectAppFlDesc(Map<String, Object> map) throws Exception;

}
