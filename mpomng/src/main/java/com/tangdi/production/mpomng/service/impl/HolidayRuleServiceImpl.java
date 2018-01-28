package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpomng.bean.HolidayRuleInf;
import com.tangdi.production.mpomng.dao.HolidayRuleDao;
import com.tangdi.production.mpomng.service.HolidayRuleService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class HolidayRuleServiceImpl implements HolidayRuleService {
	private static final Logger log = LoggerFactory.getLogger(HolidayRuleServiceImpl.class);
	@Autowired
	private HolidayRuleDao dao;

	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<HolidayRuleInf> getListPage(HolidayRuleInf entity)
			throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(HolidayRuleInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public HolidayRuleInf getEntity(HolidayRuleInf entity) throws Exception {
		String hoday=entity.getHoDate();
		String dateFormat="";
		if(hoday!=null && !hoday.equals("") && hoday.length()<=11){
			dateFormat = hoday.substring(0,4);
			dateFormat=dateFormat+hoday.substring(5,7);
			dateFormat=dateFormat+hoday.substring(8,10);
			}else{
				dateFormat = hoday;
			}
		entity.setHoDate(dateFormat);
		return dao.selectEntity(entity);
	}

	@Override
	public int addEntity(HolidayRuleInf entity) throws Exception {
		int rt=0;
		try {
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	public int modifyEntity(HolidayRuleInf entity) throws Exception {
		return dao.updateEntity(entity);
	}

	@Override
	public int removeEntity(HolidayRuleInf entity) throws Exception {
		String hoday=entity.getHoDate();
		String dateFormat="";
		if(hoday!=null && !hoday.equals("") && hoday.length()<=11){
			dateFormat = hoday.substring(0,4);
			dateFormat=dateFormat+hoday.substring(5,7);
			dateFormat=dateFormat+hoday.substring(8,10);
			}else{
				dateFormat = hoday;
			}
		entity.setHoDate(dateFormat);
		return dao.deleteEntity(entity);
	}
	@Override
	public int queryHolidayBydate(HolidayRuleInf entity) throws Exception{
		return dao.queryHolidayBydate(entity);
	}

}
