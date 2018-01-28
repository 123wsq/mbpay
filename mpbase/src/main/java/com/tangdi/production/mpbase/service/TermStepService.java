package com.tangdi.production.mpbase.service;

import java.util.List;
import java.util.Map;

/**
 * 终端操作日志
 * 
 * @author limiao
 *
 */
public interface TermStepService {

	/**
	 * 对外 提供 终端记录 添加
	 * 
	 * @param termNo
	 *            终端号
	 * @param termStep
	 *            步骤
	 * @param args
	 *            操作员,代理商
	 */
	public void saveTermStepDesc(String termNo, int termStep, String... args);

	/**
	 * 添加终端操作日志
	 * 
	 * @param param
	 *            termNo,termStep,termDesc,termOper,termDate
	 * @return
	 * @throws Exception
	 */
	Integer insertEntity(Map<String, Object> param) throws Exception;

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
