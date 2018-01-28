package com.tangdi.production.mpomng.service;

import com.tangdi.production.mpomng.bean.HolidayRuleInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface HolidayRuleService extends BaseService<HolidayRuleInf, Exception> {
	
	/**
	 * 校验是否为节日
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int queryHolidayBydate(HolidayRuleInf entity) throws Exception;
	
}
