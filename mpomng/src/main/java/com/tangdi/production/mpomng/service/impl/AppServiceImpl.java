package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpomng.bean.AppInf;
import com.tangdi.production.mpomng.dao.AppDao;
import com.tangdi.production.mpomng.service.AppService;
import com.tangdi.production.tdbase.domain.Attachment;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class AppServiceImpl implements AppService {
	private static final Logger log = LoggerFactory.getLogger(AppServiceImpl.class);
	@Autowired
	private AppDao dao;
	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<AppInf> getListPage(AppInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(AppInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public AppInf getEntity(AppInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		AppInf appInf = null;
		try {
			appInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", appInf.debug());
		return appInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(AppInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String appId = seqNoService.getSeqNoNew("APP_ID", "9", "0");
			entity.setAppId(appId);
			entity = setAppFilePath(entity);

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
	public int modifyEntity(AppInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			entity = setAppFilePath(entity);

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
	public int removeEntity(AppInf entity) throws Exception {
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

	/***
	 * 设置app文件路径
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private AppInf setAppFilePath(AppInf entity) throws Exception {
		if (("1").equals(entity.getAppPlatform())) {
			// IOS
			entity.setAppFilePath(entity.getAppFileName());
		} else {
			// Android
			Attachment am = new Attachment();
			am.setId(entity.getAppFileId());
			String fjPath = fileUploadService.getFilePath(entity.getAppFileId());
			if (null != fjPath) {
				entity.setAppFilePath(fjPath);
			}
		}
		return entity;
	}

	@Override
	public double getLastAppVersion(AppInf appInf) throws Exception {
		String lastVersion = dao.selectLastAppVersion(appInf);
		if(null == lastVersion){
			return 0;
		}else{
			return Double.valueOf(lastVersion);
		}
	}
}
