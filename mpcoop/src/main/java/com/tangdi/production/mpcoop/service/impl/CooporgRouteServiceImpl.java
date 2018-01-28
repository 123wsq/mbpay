package com.tangdi.production.mpcoop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpcoop.bean.CooporgRouteInf;
import com.tangdi.production.mpcoop.dao.CooporgRouteDao;
import com.tangdi.production.mpcoop.service.CooporgRouteService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class CooporgRouteServiceImpl implements CooporgRouteService {
	private static final Logger log = LoggerFactory.getLogger(CooporgRouteServiceImpl.class);
	@Autowired
	private CooporgRouteDao dao;
	@Autowired
	private GetSeqNoService seqNoService;
	@Override
	public List<CooporgRouteInf> getListPage(CooporgRouteInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CooporgRouteInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CooporgRouteInf getEntity(CooporgRouteInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		CooporgRouteInf cooporgRouteInf = null;
		try {
			cooporgRouteInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		//log.debug("处理结果:", cooporgRouteInf.debug());
		return cooporgRouteInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(CooporgRouteInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String rtrid = seqNoService.getSeqNoNew("RTRID", "4", "0");
			entity.setRtrid(rtrid);
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
	public int modifyEntity(CooporgRouteInf entity) throws Exception {
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
	public int removeEntity(CooporgRouteInf entity) throws Exception {
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
