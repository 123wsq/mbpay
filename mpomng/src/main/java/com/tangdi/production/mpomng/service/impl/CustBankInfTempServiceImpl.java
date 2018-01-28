package com.tangdi.production.mpomng.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.mpomng.dao.CustBankInfTempDao;
import com.tangdi.production.mpomng.service.CustBankInfTempService;



/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */
@Service
public class CustBankInfTempServiceImpl implements CustBankInfTempService{
	private static final Logger log = LoggerFactory
			.getLogger(CustBankInfTempServiceImpl.class);
	@Autowired
	private CustBankInfTempDao dao;

	@Override
	public List<CustBankInf> getListPage(CustBankInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CustBankInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CustBankInf getEntity(CustBankInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustBankInf custBankInfTemp = null;
		try{
			custBankInfTemp = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",custBankInfTemp.debug());
		return custBankInfTemp;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CustBankInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("插入数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(CustBankInf entity) throws Exception {
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

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(CustBankInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.deleteEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override
	public CustBankInf getEntityById(CustBankInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustBankInf custBankInfTemp = null;
		try{
			custBankInfTemp = dao.getEntityById(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",custBankInfTemp.debug());
		return custBankInfTemp;
	}

	@Override
	public int addCount(CustBankInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.updateCount(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	
	
}
