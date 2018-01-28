package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.HolidayRuleDao;
import com.tangdi.production.mpapp.service.HolidayRuleService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 判断当前日期是否为节假日
 * @author tuyy
 * @version 1.0
 *
 */
@Service
public class HolidayRuleServiceImpl implements HolidayRuleService{
	
	@Autowired
	private HolidayRuleDao dao;
	@Override
	public int queryHoliday() 
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String cDate = TdExpBasicFunctions.GETDATE();
		param.put("hoDate", cDate);
		return dao.queryHoliday(param);
	}
	
}
