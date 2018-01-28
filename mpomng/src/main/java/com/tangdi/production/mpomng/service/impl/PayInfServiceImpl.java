package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.dao.PayInfDao;
import com.tangdi.production.mpomng.service.PayInfService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class PayInfServiceImpl implements PayInfService {
	private static final Logger log = LoggerFactory.getLogger(PayInfServiceImpl.class);
	@Autowired
	private PayInfDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<PayInf> getListPage(PayInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(PayInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public PayInf getEntity(PayInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		PayInf payInf = null;
		try {
			payInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", payInf.debug());
		return payInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(PayInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String payordno = seqNoService.getSeqNoNew("PAYORDNO", "9", "0");
			entity.setPayordno(payordno);
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
	public int modifyEntity(PayInf entity) throws Exception {
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
	public int removeEntity(PayInf entity) throws Exception {
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
	public List<PayInf> getCountList(PayInf entity) throws Exception{
		List<PayInf> resultList=null;
		try{
			resultList=dao.getCountList(entity);
		}catch(Exception e){
			log.error("收款订单查询失败！{}",e.getMessage());
			throw new Exception(e);
		}
		return resultList;
	}
}
