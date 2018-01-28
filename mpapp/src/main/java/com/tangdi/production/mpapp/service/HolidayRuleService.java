package com.tangdi.production.mpapp.service;

import java.util.Map;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface HolidayRuleService {
	
	/**
	 * 校验是否为节日
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int queryHoliday() throws Exception;
	
}
