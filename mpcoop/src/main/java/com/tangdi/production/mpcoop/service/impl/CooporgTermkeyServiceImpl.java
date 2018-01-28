package com.tangdi.production.mpcoop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpcoop.bean.CooporgTermkeyInf;
import com.tangdi.production.mpcoop.dao.CooporgTermkeyDao;
import com.tangdi.production.mpcoop.service.CooporgTermkeyService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class CooporgTermkeyServiceImpl implements CooporgTermkeyService {
	private static final Logger log = LoggerFactory.getLogger(CooporgTermkeyServiceImpl.class);
	@Autowired
	private CooporgTermkeyDao dao;

	@Override
	public List<CooporgTermkeyInf> getListPage(CooporgTermkeyInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CooporgTermkeyInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CooporgTermkeyInf getEntity(CooporgTermkeyInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		CooporgTermkeyInf cooporgTermkeyInf = null;
		try {
			cooporgTermkeyInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", cooporgTermkeyInf.debug());
		return cooporgTermkeyInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(CooporgTermkeyInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(CooporgTermkeyInf entity) throws Exception {
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
	public int removeEntity(CooporgTermkeyInf entity) throws Exception {
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
