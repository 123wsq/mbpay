package com.tangdi.production.tdauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.tdauth.bean.AuditLogInf;
import com.tangdi.production.tdauth.dao.AuditLogDao;
import com.tangdi.production.tdauth.service.AuditLogService;
import com.tangdi.production.tdbase.test.BeanDebugger;

/**
 * 
 * @author songleiheng
 * 
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

	private static Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);

	@Autowired
	private AuditLogDao auditLogDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(AuditLogInf entity) throws Exception {
		// TODO Auto-generated method stub
		BeanDebugger.dump(entity);
		log.info("auditLogDao==null?" + (auditLogDao == null));
		return auditLogDao.insertEntity(entity);
	}

	public int getCount(AuditLogInf auditLog, String currentOrgNo) throws Exception {
		int result = -1;
		Map<String, Object> map = auditLog.toMap();
		map.put("currentOrgNo", currentOrgNo);
		try {
			result = auditLogDao.countEntity(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<AuditLogInf> getListPage(AuditLogInf auditLog, int start, int end, String currentOrgNo) throws Exception {
		Map<String, Object> cond = new HashMap<String, Object>();
		cond.putAll(auditLog.toMap());
		cond.put("start", start);
		cond.put("end", end);
		cond.put("currentOrgNo", currentOrgNo);
		return auditLogDao.selectListPage(cond);
	}

}
