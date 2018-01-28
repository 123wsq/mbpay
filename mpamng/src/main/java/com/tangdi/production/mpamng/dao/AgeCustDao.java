package com.tangdi.production.mpamng.dao;

import java.util.Map;

import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface AgeCustDao extends BaseDao<TerminalInf, Exception> {
	/***
	 * 获取代理商、商户关系数
	 * 
	 * @param map
	 * @return
	 */
	public int getTerminalCount(Map<String, Object> map);

	/***
	 * 插入商户、代理商、终端关系
	 * 
	 * @return
	 */
	public int insertAgeCustInfo(Map<String, Object> map);

	/***
	 * 更新商户、代理商、终端关系
	 * 
	 * @param map
	 * @return
	 */
	public int updateAgeCustInfo(Map<String, Object> map);

	/***
	 * 删除商户、代理商、终端关系
	 * 
	 * @param map
	 * @return
	 */
	public int deleteAgeCustInfo(Map<String, Object> map);

	/***
	 * 更新代理商信息
	 * 
	 * @param map
	 * @return
	 */
	public int updateAgent(Map<String, Object> map);

	/**
	 * 根据 terminalId 获取 custId,agentId
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectAgeCustEntity(Map<String, Object> map);

}
