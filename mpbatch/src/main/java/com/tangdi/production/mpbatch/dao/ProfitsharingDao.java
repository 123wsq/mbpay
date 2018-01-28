package com.tangdi.production.mpbatch.dao;

import java.util.List;
import java.util.Map;

public interface ProfitsharingDao {
	/**
	 * 查询当月可分润金额总计
	 * @param param date 年月，agentDgr代理商等级，
	 * @return
	 * @throws Exception
	 */
	public List<Map<String , Object>> selectSum(Map<String , Object> param) throws Exception;
	/**
	 * 查询代理商分润区间配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public List<Map<String , Object>> selectStage(Map<String , Object > param) throws Exception;
	public Map<String , Object> selectStage(Map<String , Object > param) throws Exception;
	/**
	 * 更新月分润状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateLogStatus(Map<String , Object> param) throws Exception;
	/**
	 * 新增月分润记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertMonthLog(Map<String , Object> param) throws Exception;
	
	/***
	 * 清空分润记录(测试用)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteSharingLog(Map<String , Object> param) throws Exception;
	
	/**
	 * 新增日分润记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertDayLog(Map<String , Object> param) throws Exception;
	
	/**
	 * 备份日分润记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int copyDayLog() throws Exception;
	
	/**
	 * 清楚日分润备份表记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteCopyDayLog() throws Exception;

	/**
	 * 清除当前日期的日分润记录
	 */
	public int deleteDayLog(Map<String,Object> map) throws Exception;
	/**
	 * 查询月分润是否已统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int countProfitMonthLog(Map<String, Object> map) throws Exception;
	/**
	 * 删除已存在的正在处理的代理商月分润数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteProfitMonthLog(Map<String, Object> map) throws Exception;
	/**
	 * 查询日分润总和
	 */
	public List<Map<String, Object>> selectDayLog(Map<String , Object > param) throws Exception;

}
