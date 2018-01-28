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

import com.tangdi.production.tdauth.bean.OrgInf;
import com.tangdi.production.tdauth.controller.OrgController;
import com.tangdi.production.tdauth.dao.OrgDao;
import com.tangdi.production.tdauth.service.OrgService;
import com.tangdi.production.tdbase.test.BeanDebugger;

@Service
public class OrgServiceImpl implements OrgService {
	private static Logger log = LoggerFactory.getLogger(OrgController.class);

	@Autowired
	private OrgDao orgDao;

	public OrgInf getEntity(OrgInf entity) throws Exception {
		return orgDao.selectEntity(entity);
	}

	public List<OrgInf> getList(OrgInf entity) throws Exception {
		return orgDao.selectList(entity);
	}

	/**
	 * 添加机构信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(OrgInf entity) throws Exception {
		String orgLevel = orgLevelAdd(entity.getOrgLevel());
		entity.setOrgLevel(orgLevel);
		int result = 0;
		OrgInf org = new OrgInf();
		org.setOrgId(entity.getOrgId());
		this.checkOrgByOrgId(entity.getOrgId());
		this.addCheckOrgName(entity.getOrgName());
		try {
			result = orgDao.insertEntity(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("添加机构数据库异常" + e.getMessage());
			throw new Exception("添加机构数据库异常");
		}
		return result;
	}

	/**
	 * 机构号查询，确保机构号未被使用
	 * 
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void checkOrgByOrgId(String orgId) throws Exception {
		Integer result = 0;
		try {
			result = orgDao.checkOrgByOrgId(orgId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构信息失败" + e.getMessage());
			throw new Exception("查询机构信息数据错误");
		}
		if (result > 0) {
			throw new Exception("机构编码已被占用");
		}
	}

	/**
	 * 添加时确保机构名称未被使用
	 * 
	 * @param orgId
	 * @param orgName
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCheckOrgName(String orgName) throws Exception {
		Integer result = 0;
		try {
			result = orgDao.addCheckOrgName(orgName);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构信息失败" + e.getMessage());
			throw new Exception("查询机构信息数据错误");
		}
		if (result > 0) {
			log.error("该机构名称已被使用");
			throw new Exception("该机构名称已被使用");
		}
	}

	/**
	 * 修改时确保机构名称未被使用
	 * 
	 * @param orgId
	 * @param orgName
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCheckOrgName(String orgId, String orgName) throws Exception {
		Integer result = 0;
		try {
			HashMap<String, String> con = new HashMap<String, String>();
			con.put("orgId", orgId);
			con.put("orgName", orgName);
			result = orgDao.updateCheckOrgName(con);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构信息失败" + e.getMessage());
			throw new Exception("查询机构信息数据错误");
		}
		if (result > 0) {
			log.error("该机构名称已被使用");
			throw new Exception("该机构名称已被使用");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void checkOrgStateByOrgId(String orgId) throws Exception {
		Integer result = 0;
		try {
			result = orgDao.countOrgStateByOrgId(orgId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构信息失败" + e.getMessage());
			throw new Exception("查询机构信息数据错误");
		}
		if (result > 0) {
			throw new Exception("机构号[" + orgId + "]非法");
		}
	}

	private static String orgLevelAdd(String level) {
		Integer le = Integer.parseInt(level);
		le++;
		if (le < 10) {
			return "0" + le;
		} else {
			return le + "";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addList(List<OrgInf> list) throws Exception {
		return orgDao.insertList(list);
	}

	/**
	 * 修改机构信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(OrgInf entity) throws Exception {
		this.updateCheckOrgName(entity.getOrgId(), entity.getOrgName());
		int result = 0;
		try {
			result = orgDao.updateEntity(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("更新机构信息失败" + e.getMessage());
			throw new Exception("更新机构信息失败");
		}
		return result;
	}

	public int removeEntity(OrgInf entity) {
		return 0;
	}


	public int countEntity(OrgInf entity) throws Exception {
		return orgDao.countEntity(entity);
	}

	public Integer getCount(OrgInf entity) {
		int result = -1;
		try {
			result = orgDao.countEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, Object> getOrgList(OrgInf entity, String operOrgId, int pageNo, int numPerPage) throws Exception {
		// TODO Auto-generated method stub
		int total = 0;
		BeanDebugger.dump(entity);
		HashMap<String, Object> cond = new HashMap<String, Object>();
		List<OrgInf> list = null;
		try {
			int start = (pageNo - 1) * numPerPage + 1;
			int end = (pageNo) * numPerPage;
			cond.put("orgName", entity.getOrgName());
			cond.put("orgId", entity.getOrgId());
			cond.put("currentOrgId", operOrgId);
			cond.put("orgStatus", '0');
			cond.put("start", start);
			cond.put("end", end);
			total = orgDao.countOrg(cond);
			list = orgDao.selectOrgListPage(cond);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			throw new Exception("操作数据库异常");
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("records", total);
		result.put("rows", list);
		return result;
	}

	public HashMap<String, Object> getListPage(OrgInf entity, int pageNo, int numPerPage) throws Exception {
		// TODO Auto-generated method stub
		int total = 0;
		BeanDebugger.dump(entity);
		List<OrgInf> list = null;
		try {
			total = orgDao.countEntity(entity);
			int start = (pageNo - 1) * numPerPage + 1;
			int end = (pageNo) * numPerPage;
			total = orgDao.countEntity(entity);
			Map<String, Object> cond = entity.toMap();
			cond.put("start", start);
			cond.put("end", end);
			list = orgDao.selectListPage(cond);
		} catch (Exception e) {
			// TODO: handle exception
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("records", total);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyList(Map<String, Object> map) throws Exception {
		int result = 0;
		try {
			result = orgDao.deleteEntityList(map);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("修改机构状态数据库错误" + e.getMessage());
			throw new Exception("修改机构状态错误");
		}
		return result;
	}

	public List<OrgInf> getOrgForAll(String orgId) throws Exception {
		List<OrgInf> list = null;
		try {
			list = orgDao.selectOrgForAll(orgId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构信息失败" + e.getMessage());
			throw new Exception("查询机构信息失败");
		}
		return list;
	}

	public Map<String, Object> getOrgForAll(OrgInf entity, int pageNo, int numPerPage, String currentOrgId) {
		int total = 0;
		List<OrgInf> list = null;
		try {
			Map<String, Object> cond = entity.toMap();
			int start = (pageNo - 1) * numPerPage + 1;
			int end = (pageNo) * numPerPage;
			cond.put("start", start);
			cond.put("end", end);
			cond.put("currentOrgId", currentOrgId);
			 
			list = orgDao.selectOrgListPage(cond);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("records", total);
		result.put("rows", list);
		return result;
	}

	public OrgInf getParentOrgId(String orgId, String orgLevel) throws Exception {
		// TODO Auto-generated method stub
		// 默认orgLevel 为 01，即总机构
		if (orgLevel == null || orgLevel == "") {
			orgLevel = "01";
		}
		HashMap<String, String> orgMap = new HashMap<String, String>();
		orgMap.put("orgId", orgId);
		orgMap.put("orgLevel", orgLevel);
		OrgInf org = null;
		try {
			org = orgDao.selectParentOrgByorgIdLevel(orgMap);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询机构错误" + e.getMessage());
			throw new Exception("查询机构错误");
		}
		BeanDebugger.dump(org);
		return org;
	}

	public Map<String, Object> querySubordinates(OrgInf entity, int pageNum, int numPerPage,
			String currentOrgId) throws Exception {
		int total = 0;
		List<OrgInf> list = null;
		int start = (pageNum - 1) * numPerPage + 1;
		int end = (pageNum) * numPerPage;
		Map<String, Object> con = entity.toMap();
		con.put("start", start);
		con.put("end", end);
		con.put("orgParId", entity.getOrgParId());
		total = orgDao.countOrgForQuerySubordinates(con);
	    list=orgDao.querySubordinates(con);
	    Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("records", total);
		result.put("rows", list);
		return result;
	}

	public List<OrgInf> getListPage(OrgInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	
}
