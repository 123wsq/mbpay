package com.tangdi.production.mpaychl.dao;

import java.util.Map;

/**
 * 获取节假日
 * 
 * @author limiao
 *
 */
public interface HolidayRuleInfDao {
	public Map<String, Object> selectIsHoliday(Map<String, Object> param) throws Exception;
}
