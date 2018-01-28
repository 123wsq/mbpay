package com.tangdi.production.mpomng.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.service.impl.ReportServiceImpl;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.mpomng.bean.RecInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.RecInfDao;
import com.tangdi.production.mpomng.report.PrdTemplate;
import com.tangdi.production.mpomng.report.RecInfoTemplate;
import com.tangdi.production.mpomng.report.RechargePrdTemplate;
import com.tangdi.production.mpomng.service.RecInfService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author
 * @version 1.0
 *
 */
@Service
public class RecInfServiceImpl implements RecInfService {
	private static final Logger log = LoggerFactory
			.getLogger(RecInfServiceImpl.class);
	@Autowired
	private RecInfDao dao;

	@Autowired
	private GetSeqNoService seqNoService;

	@Autowired
	private FileReportService<RecInfoTemplate> reportService;

	@Override
	public int report(HashMap<String, Object> con, String uid) throws Exception {

		List<RecInfoTemplate> data = null;
		log.info("-----------------------" + con);
		if (con.get("ckdt") != null && !"".equals(con.get("ckdt"))) {
			con.put("ckdt", String.valueOf(con.get("ckdt")).substring(0, 4)
					+ String.valueOf(con.get("ckdt")).substring(5, 7)
					+ String.valueOf(con.get("ckdt")).substring(8, 10));
		}
		try {
			if (con.get("recstatus") == null || "".equals(con.get("recstatus"))
					|| "01".equals(con.get("recstatus"))) {
				data = dao.selectListTemplate01(con);
			} else if ("02".equals(con.get("recstatus"))) {
				data = dao.selectListTemplate02(con);
			} else {
				data = dao.selectListTemplate03(con);
			}
			log.debug("RecInfServiceImpl.report对账数据：" + data);
		} catch (Exception e) {
			log.error("生成对账文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		return reportService.report(data, uid, "对账文件", "对账报表",
				CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL, null);

	}

	@Override
	public List<RecInf> getListPage(RecInf entity) throws Exception {
		List<RecInf> resultList = null;
		if ("01".equals(entity.getRecstatus()) || entity.getRecstatus() == null
				|| "".equals(entity.getRecstatus())) {
			resultList = dao.selectList(entity);
		} else if ("02".equals(entity.getRecstatus())) {
			resultList = dao.selectList02(entity);
		} else {
			resultList = dao.selectList03(entity);
		}
		return resultList;
	}

	@Override
	public Integer getCount(RecInf entity) throws Exception {
		int count;
		if ("01".equals(entity.getRecstatus()) || entity.getRecstatus() == null
				|| "".equals(entity.getRecstatus())) {
			count = dao.countEntity(entity);
		} else if ("02".equals(entity.getRecstatus())) {
			count = dao.countEntity02(entity);
		} else {
			count = dao.countEntity03(entity);
		}
		return count;
	}

	@Override
	public RecInf getEntity(RecInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		RecInf recInf = null;
		try {
			recInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", recInf.debug());
		return recInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(RecInf entity) throws Exception {

		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(RecInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("更新数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(RecInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.deleteEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("删除数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

}
