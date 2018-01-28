package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpamng.bean.CustShareInf;

public interface ProfitShareDao {
	/***
	 * 获取日分润所有记录
	 * 
	 * @return
	 * @throws Exception
	 */
	List<CustShareInf> selectCustShareList(Map<String, Object> map) throws Exception;
	/**
	 * 获取 月份润 所有记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CustShareInf> selectCustMonthShareList(Map<String, Object> map) throws Exception;
	/**
	 * 获取 日分润记录中所有代理商id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> querySharingList() throws Exception;
}
