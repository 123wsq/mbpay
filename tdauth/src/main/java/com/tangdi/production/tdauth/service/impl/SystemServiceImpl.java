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

import com.tangdi.production.tdauth.bean.SysInf;
import com.tangdi.production.tdauth.controller.RoleController;
import com.tangdi.production.tdauth.dao.SystemDao;
import com.tangdi.production.tdauth.service.SystemService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 系统模块接口实现
 * @author zhengqiang
 *
 */
@Service
public class SystemServiceImpl implements SystemService {

	private static Logger log = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private SystemDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	public Integer getCount(SysInf entity) throws Exception {
		return dao.countEntity(entity);
	}
	public int addEntity(SysInf entity)  {
		return 0;
	}
	
	public HashMap<String, Object> getDataPage(SysInf entity, int pageNo,
			int numPerPage) throws Exception {
		return null;
	}

	public int addList(List<SysInf> entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
    
	public SysInf getEntity(SysInf entity) throws Exception {
		SysInf sys = null;
		try{
			sys = dao.selectEntity(entity);
		}catch(Exception e){
			log.error("查询系统模块信息出错！",e);
			throw new Exception(e);
		}
		if(sys == null || sys.getId() == null || sys.getId().equals("")){
			throw new Exception("系统模块不存在!");
		}
		return sys;
	}

	public List<SysInf> getList(SysInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int modifyEntity(SysInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	public int modifyList(List<SysInf> entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int removeEntity(SysInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<SysInf> getListPage(SysInf entity) throws Exception {
		return dao.selectList(entity);
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifySysStatus(String ids, int status) throws Exception {

		Map<String, Object> con = new HashMap<String, Object>(2);
		con.put("state", status);
		con.put("id", ids);
		log.debug(ids);
		int num = 0;
		try {
			num = dao.updateSysStatus(con);
		} catch (Exception e) {
			log.error("用户状态更新异常！", e);
			throw new Exception("用户状态更新异常！", e);
		}

		return num;
	}



}
