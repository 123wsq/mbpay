package com.tangdi.production.mpomng.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpomng.bean.MeridentifyInf;
import com.tangdi.production.mpomng.dao.MeridentifyDao;
import com.tangdi.production.mpomng.service.MeridentifyService;



/**
 * 
 * @author luoyang
 * @version 1.0
 *
 */
@Service
public class MeridentifyServiceImpl implements MeridentifyService{
	private static final Logger log = LoggerFactory
			.getLogger(MeridentifyServiceImpl.class);
	@Autowired
	private MeridentifyDao dao;

	@Override
	public List<MeridentifyInf> getListPage(MeridentifyInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(MeridentifyInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public MeridentifyInf getEntity(MeridentifyInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		MeridentifyInf meridentifyInf = null;
		try{
			meridentifyInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",meridentifyInf.debug());
		return meridentifyInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(MeridentifyInf entity) throws Exception {
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
	public int modifyEntity(MeridentifyInf entity) throws Exception {
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
	public int removeEntity(MeridentifyInf entity) throws Exception {
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



	
	
}
