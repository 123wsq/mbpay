package com.tangdi.production.mpomng.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpomng.bean.AppimgInf;
import com.tangdi.production.mpomng.dao.AppimgDao;
import com.tangdi.production.mpomng.service.AppimgService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class AppimgServiceImpl implements AppimgService {
	private static final Logger log = LoggerFactory.getLogger(AppimgServiceImpl.class);
	@Autowired
	private AppimgDao dao;
	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<AppimgInf> getListPage(AppimgInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(AppimgInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public AppimgInf getEntity(AppimgInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		AppimgInf appimgInf = null;
		try {
			appimgInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", appimgInf.debug());
		return appimgInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(AppimgInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String appimgId=seqNoService.getSeqNoNew("APPIMG_ID", "9", "0");
			entity.setAppimgId(appimgId);
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(AppimgInf entity) throws Exception {
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
	public int removeEntity(AppimgInf entity) throws Exception {
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
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyAppImgStatus(Map<String,Object> map) throws Exception{
		log.info("更新启用状态值:[{}]", map);
		return dao.modifyAppImgStatus(map);
		
	}

}
