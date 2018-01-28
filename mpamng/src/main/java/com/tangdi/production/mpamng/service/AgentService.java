package com.tangdi.production.mpamng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tangdi.production.mpamng.bean.AgentFeeInfo;
import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.AgentProfitInf;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.service.BaseService;

public interface AgentService extends BaseService<AgentInf, Exception> {

	/**
	 * 修改代理商信息 申请
	 * 
	 * @param agentInf
	 * @return
	 */
	public ReturnMsg modifyAgentInf(AgentInf agentInf,String currentAgentId ,HttpServletRequest request) throws Exception;

	/**
	 * 代理商开户 申请
	 * 
	 * @param agentInf
	 * @return
	 */
	public ReturnMsg addAgentInf(AgentInf agentInf, String agentId, HttpServletRequest request) throws Exception;


	/**
	 * 代理商复核 查询 count
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public Integer countAgentTemp(AgentInf agentInf) throws Exception;

	/**
	 * 代理商复核详情查询
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public AgentInf getAgentTemp(AgentInf agentInf) throws Exception;

	/**
	 * 代理商复核 查询
	 * 
	 * @param agentInf
	 * @return
	 * @throws Exception
	 */
	public List<AgentInf> getAgentTempPage(AgentInf agentInf) throws Exception;

	/**
	 * 代理商开户审核，答复
	 * 
	 * @param agentId
	 * @param approved
	 * @param auditFailReason 
	 * @return
	 * @throws Exception
	 */
	public ReturnMsg agentTempReply(String agentId, String approved,String currentId, String auditFailReason,String operId) throws Exception;

	/**
	 * 代理商重置密码
	 * 
	 * @param agentIds
	 * @return
	 * @throws Exception
	 */
	public ReturnMsg agentPasswdReset(String agentIds,String logonNames,String operId) throws Exception;

	/**
	 * 获取省份
	 * @param id
	 * @return
	 */
	public String getProvince(String id);
	
	/**
	 * 获取城市
	 * @param id
	 * @return
	 */
	public String getCity(String id);

	/**
	 * 查询 OEM 图片
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryOem(Map<String, Object> map) throws Exception;


	/**
	 * 查询运营平台的平台参数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> outOfRatePlatform(Map<String, Object> map) throws Exception;

	/**
	 * 查询代理商费率信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AgentInf outOfRateAgent(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询分润阶梯
	 */
	public List<AgentProfitInf> selectAgentProfitGrade(String agentId, String rateType) throws Exception;

	/**
	 * 查询分润阶梯
	 */
	public AgentFeeInfo selectAgentFeeGrade(String agentId, String rateType) throws Exception;
	/**
	 * 更新状态值
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int modifyAgentStatus(Map<String,Object> map) throws Exception;
	/**
	 * 税务登记号 营业执照好 唯一性校验
	 * @param map
	 * @return
	 */
	public int GetAgentCount(Map<String, Object> map);

	/**
	 * 修改代理商下绑定的终端费率
	 * @param map
	 * @return
	 */
	public int updateTerminal(AgentInf agentInf);

	public boolean checkParentFeeInfo(AgentInf agentInf)  throws Exception;
	/** 
	 * 	根据费率查询代理商的费率是否改变
	 */
	public int checkAgentFee(AgentInf agentInf) throws Exception;
	/**
	 * 修改代理商下绑定的终端费率（当左边和大于终端时）
	 * @param map
	 * @return
	 */
	public int updateTerminalSum(AgentInf agentInf);
	/**
	 * 修改指定代理商费率
	 * */
	public int agentRateAdjust(Map<String, Object> map, String rateAjst) throws Exception;

	/**
	 * 根据代理商查找下级商户
	 * */
	public List<AgentInf> queryCust(Map<String,Object> map);

	/**
	 * 根据代理商查找下属所有代理商
	 * */
	public List<AgentInf> queryNextAgent(Map<String, Object> map);

	/**
	 * 修改代理商所属商户费率
	 * */
	public int custRateAdjust(Map<String, Object> map, String rateAjst) throws Exception;

	/**
	 * 修改代理商所属终端费率
	 * */
	public int termRateAdjust(Map<String, Object> map, String rateAjst) throws Exception;

	/**
	 * 根据等级修改代理商费率 
	 * */
	public int agentRateAdjustAll(Integer num, String rateAjst) throws Exception;

	/**
	 * 根据代理商等级修改所属商户费率
	 * */
	public int custRateAdjustAll(Integer num, String rateAjst) throws Exception;

	/**
	 * 根据代理商等级修改所属终端费率
	 * */
	public int termRateAdjustAll(Integer num, String rateAjst) throws Exception;
	/**
	 * 获取代理商分享费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAgentShareFee(Map<String, Object> param) throws Exception;
	
	/**
	 * 设置代理商分享费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int setAgentShareFee(Map<String, Object> param) throws Exception;
	
}
