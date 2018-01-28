package com.tangdi.production.mpamng.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.CityInf;
import com.tangdi.production.mpamng.bean.ProvinceInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface AgentDao extends BaseDao<AgentInf, Exception> {

	/**
	 * 插入单个申请数据
	 * 
	 * @param agentInf
	 * @return
	 */
	public int insertTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 删除实体
	 * 
	 * @param agentInf
	 * @return
	 */
	public int deleteTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 统计实体数量
	 * 
	 * @param agentInf
	 * @return
	 */
	public int countTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 查询单个实体
	 * 
	 * @param agentInf
	 * @return
	 */
	public AgentInf selectTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 查询实体列表
	 * 
	 * @param agentInf
	 * @return
	 */
	public List<AgentInf> selectTempList(AgentInf agentInf) throws Exception;

	/**
	 * 修改缓存表
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public int updateTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 修改审核中的代理商审核状态
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public int updateAuditStatus(AgentInf agentInf) throws Exception;

	/**
	 * 获取省份
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ProvinceInf> getProvinceList() throws Exception;

	/**
	 * 获取城市
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CityInf> getCityList(CityInf cityInf) throws Exception;

	/**
	 * 查询代理商信息表临时表 MPAMNG_AGENT_INF_TEMP中登录名是否已存在
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public int countLoNTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 根据agentId获取代理商等级和一级代理商信息
	 * 
	 * @param agtid
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> selectAgentById(String agtid) throws Exception;

	/**
	 * 查询 OEM 图片
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryOem(Map<String, Object> map) throws Exception;

	/**
	 * 查询代理商信息表临时表 MPAMNG_AGENT_INF_TEMP中代理商名是否已存在
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public int countAgeNTempEntity(AgentInf agentInf) throws Exception;

	/**
	 * 查询代理商信息表 MPAMNG_AGENT_INF中代理商名是否已存在
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public int countAgeNEntity(AgentInf agentInf) throws Exception;

	/**
	 * 查询平台信息参数表 MPOMNG_PLATFORM_PARAMETER_INF 中费率信息
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryplatform(Map<String, Object> map) throws Exception;

	/**
	 * 查询代理商信息表 MPAMNG_AGENT_INF 中的代理商费率
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AgentInf queryagentrate(Map<String, Object> map) throws Exception;

	/**
	 * 修改状态
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAgentStatus(Map<String, Object> map) throws Exception;

	/**
	 * 根据 代理商ID 查询出 所有下级代理商编号
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<String> queryNextAgent(Map<String, Object> map) throws Exception;
	
	public List<AgentInf> getAgentList(Map<String,Object> map) throws Exception;

	public int getListAget(Map<String, Object> map);
	
	public int countStatus(AgentInf agentInf) throws Exception;

	public int updateTerminal(AgentInf agentInf);
	/**
	 * 根据代理商的费率来判断代里商的费率是否改变
	 */
	public int checkAgentInfFee(AgentInf agentInf);
	public int checkAgentInfFeeEx(AgentInf agentInf);
	public int updateTerminalSum(AgentInf agentInf);

	/**
	 * 同比调整指定代理商费率
	 * */
	public int agentRateAdjust(Map<String, Object> map) throws Exception;
	public int _agentRateAdjust(Map<String, Object> map) throws Exception;
	
	/**
	 * 同比调整指定代理商所属商户费率
	 * */
	public int custRateAdjust(Map<String, Object> map) throws Exception;
	public int _custRateAdjust(Map<String, Object> map) throws Exception;
	
	/**
	 * 同比调整指定代理商所属终端费率
	 * */
	public int termRateAdjust(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据代理商ID查找所有下属商户ID
	 * */
	public List<AgentInf> queryCust(Map<String, Object> map) throws Exception;

	/**
	 * 根据等级来修改代理商费率
	 * */
	public int agentRateAdjustAll(Map<String, Object> map) throws Exception;
	public int _agentRateAdjustAll(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据代理商等级来修改所属商户费率
	 * */
	public int custRateAdjustAll(Map<String, Object> map) throws Exception;
	public int _custRateAdjustAll(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据代理商等级来修改所属终端费率
	 * */
	public int termRateAdjustAll(Map<String, Object> map) throws Exception;
	
	/**
	 * 查看分享费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryAgentShareFee(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改分享费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateAgentShareFee(Map<String, Object> param) throws Exception;
	
}
