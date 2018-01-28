package com.tangdi.production.mpamng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.constants.CT;
import com.tangdi.production.mpamng.dao.AgentProfitLogDao;
import com.tangdi.production.mpamng.dao.ProfitSharingDao;
import com.tangdi.production.mpamng.report.ProfitSharingTemlpate;
import com.tangdi.production.mpamng.service.ProfitSharingService;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.tdauth.dao.UserDao;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class ProfitSharingServiceImpl implements ProfitSharingService {
	private static final Logger log = LoggerFactory.getLogger(ProfitSharingServiceImpl.class);
	@Autowired
	private ProfitSharingDao profitSharingDao;
	
	@Autowired
	private AgentProfitLogDao agentProfitLogDao;
	
	@Autowired
	private FileReportService<ProfitSharingTemlpate> reportService;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<Map<String, Object>> queryProfitSharingPage(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingPage:" + map.toString());
		return profitSharingDao.queryProfitSharingPage(map);
	}

	@Override
	public Integer queryProfitSharingPageCount(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingCount:" + map.toString());
		return profitSharingDao.queryProfitSharingPageCount(map);
	}

	@Override
	public List<Map<String, Object>> queryProfitSharing(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharing:" + map.toString());
		return profitSharingDao.queryProfitSharing(map);
	}

	public List<Map<String, Object>> queryProfitSharingDetail(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingDetail:" + map.toString());
		return profitSharingDao.queryProfitSharingDetail(map);
	}

	public List<Map<String, Object>> queryProfitDaySharingDetail(Map<String, Object> map) throws Exception {
		log.info("queryProfitDaySharingDetail:" + map.toString());
		return profitSharingDao.queryProfitDaySharingDetail(map);
	}
	
	@Override
	public Integer queryProfitSharingCount(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingCount:" + map.toString());
		return profitSharingDao.queryProfitSharingCount(map);
	}

	public Integer queryProfitSharingDetailCount(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingDetailCount:" + map.toString());
		return profitSharingDao.queryProfitSharingDetailCount(map);
	}

	public Integer queryProfitDaySharingDetailCount(Map<String, Object> map) throws Exception {
		log.info("queryProfitDaySharingDetailCount:" + map.toString());
		return profitSharingDao.queryProfitDaySharingDetailCount(map);
	}
	
	@Override
	public Map<String, Object> queryProfitSharingPageAMT(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingAmount:" + map.toString());
		return profitSharingDao.queryProfitSharingPageAMT(map);
	}
	
	@Override
	public Map<String, Object> queryProfitSharingAMT(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingAMT:" + map.toString());
		return profitSharingDao.queryProfitSharingAMT(map);
	}

	public Map<String, Object> queryProfitSharingDetailAMT(Map<String, Object> map) throws Exception {
		log.info("queryProfitSharingDetailAMT:" + map.toString());
		return profitSharingDao.queryProfitSharingDetailAMT(map);
	}
	
	@Override
	public List<Map<String, Object>> queryAgentProfitLog(Map<String, Object> map) throws Exception {
		return agentProfitLogDao.selectList(map);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		List<ProfitSharingTemlpate> data = null;
		try {
			data = profitSharingDao.selectProfitSharingReport(con);
		} catch (Exception e) {
			log.error("生成交易查询文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		return reportService.report(data, uid, "交易查询文件", "交易查询报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
	}
	
	@Override
	public int getCount(Map<String, Object> param) throws Exception {
		return agentProfitLogDao.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> queryProfitDaySharing(
			Map<String, Object> map) throws Exception {
		log.info("queryProfitSharing:" + map.toString());
		return profitSharingDao.queryProfitDaySharing(map);
	}
	
	@Override
	public int getProfitDaySharingCount(Map<String, Object> param) throws Exception {
		return profitSharingDao.selectProfitDaySharingCount(param);
	}

	public int getProfitDaySharingDetailCount(Map<String, Object> param) throws Exception {
		return profitSharingDao.selectProfitDaySharingDetailCount(param);
	}
	
	@Override
	public List<Map<String, Object>> queryProfitMonthSharing(
			Map<String, Object> map) throws Exception {
		log.info("queryProfitSharing:" + map.toString());
		return profitSharingDao.queryProfitMonthSharing(map);
	}

	@Override
	public int getProfitMonthSharingCount(Map<String, Object> param)
			throws Exception {
		return profitSharingDao.selectProfitMonthSharingCount(param);
	}
	
}
