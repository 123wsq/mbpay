package com.tangdi.production.mpbatch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.dao.MerClassReportDao;
import com.tangdi.production.mpbatch.service.MerClassReportService;
import com.tangdi.production.mpomng.service.MobileMerService;
@Service
public class MerClassReportServiceImpl implements MerClassReportService {
	
	private static Logger log = LoggerFactory.getLogger(MerClassReportServiceImpl.class);
	
	@Autowired
	private MerClassReportDao dao;
	
	@Autowired
	private MobileMerService MobileMerService;

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		String date = DateUtil.getYesterday();
		String year = date.substring(0,4);
		String month = date.substring(4,6);
		log.info("获取前一天的日期=[{}]",date);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("date", year + month);
		paramMap.put("year", year );
		paramMap.put("cmonth", month);
		log.info("获取的参数=[{}]",paramMap);
		
		int rt=0;
		//删除当前日期的统计数据
		rt=dao.delete(paramMap);
		if(rt>0){
			log.info("删除当前日期的统计数据成功！",rt);
		}
		
		//查询最新的统计数据
		List<Map<String,Object>> list=null;
		
		//list= MobileMerService.queryMerClassByDate(paramMap);
		if(list != null){
			//插入当前日期的统计数据
			for(Map<String,Object> insertMap:list){
				insertMap.put("year",year);
				insertMap.put("cmonth",month);
				log.info("插入数据insertMap=[{}]",insertMap);
				dao.insert(insertMap);
			}
		}
		
	
	}

}
