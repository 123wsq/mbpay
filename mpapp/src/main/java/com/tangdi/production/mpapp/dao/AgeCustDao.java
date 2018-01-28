package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface AgeCustDao extends BaseDao<TerminalInf, Exception> {
	/***
	 * 插入商户、代理商、终端关系
	 * 
	 * @return
	 */
	public int insertAgeCustInfo(Map<String, String> map);
	
	/**
	 * 获取关系数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectEntry(Map<String, String> map);

	/***
	 * 更新商户、代理商、终端关系
	 * 
	 * @param map
	 * @return
	 */
	public int updateAgeCustInfo(Map<String, String> map);

	/***
	 * 删除商户、代理商、终端关系
	 * 
	 * @param map
	 * @return
	 */
	public int deleteAgeCustInfo(Map<String, String> map);
	
	/**
	 * 删除代理商商户表中已经存在的商户关系
	 * @param map
	 * @return
	 */
	public int deleteAgeCustInfoDefault(Map<String, String> map);
	
	
}
