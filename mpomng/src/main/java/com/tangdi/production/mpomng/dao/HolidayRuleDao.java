package com.tangdi.production.mpomng.dao;

import com.tangdi.production.mpomng.bean.HolidayRuleInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface HolidayRuleDao extends BaseDao<HolidayRuleInf, Exception> {
	/**
	 * 根据日期查询节日是否存在
	 * @param entity
	 * @return
	 */
	public int queryHolidayBydate(HolidayRuleInf entity) throws Exception;
}
