package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 *
 * 
 *
 * @author tuyangyang
 * @version 1.0
 */
public interface HolidayRuleDao {
	/**
	 * 根据日期查询是否是节假日
	 * @param entity
	 * @return
	 */
	public int queryHoliday(Map<String, Object> param) throws Exception;
}
