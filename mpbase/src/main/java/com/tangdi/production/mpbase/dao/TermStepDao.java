package com.tangdi.production.mpbase.dao;

import java.util.List;
import java.util.Map;

/**
 * 终端操作日志
 * 
 * @author limiao
 *
 */
public interface TermStepDao {
	/**
	 * 添加终端操作日志
	 * 
	 * @param param
	 *            termNo,termStep,termDesc,termOper,termDate
	 * @return
	 * @throws Exception
	 */
	public Integer insertEntity(Map<String, Object> param) throws Exception;

	/**
	 * 根据终端号，查询终端操作日志总条数
	 * 
	 * @param param
	 *            termNo
	 * @return int
	 * @throws Exception
	 */
	public Integer countEntity(Map<String, Object> param) throws Exception;

	/**
	 * 根据终端号，查询终端操作日志
	 * 
	 * @param param
	 *            termNo
	 * @return map( termNo,termStep,termDesc,termOper,termDate)
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(Map<String, Object> param) throws Exception;

}
