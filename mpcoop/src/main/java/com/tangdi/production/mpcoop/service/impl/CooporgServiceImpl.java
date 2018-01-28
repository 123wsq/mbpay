package com.tangdi.production.mpcoop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpcoop.bean.CooporgInf;
import com.tangdi.production.mpcoop.dao.CooporgDao;
import com.tangdi.production.mpcoop.service.CooporgService;


/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */
@Service
public class CooporgServiceImpl implements CooporgService{
	private static final Logger log = LoggerFactory
			.getLogger(CooporgServiceImpl.class);
	@Autowired
	private CooporgDao dao;

	@Override
	public List<CooporgInf> getListPage(CooporgInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CooporgInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CooporgInf getEntity(CooporgInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CooporgInf cooporgInf = null;
		try{
			cooporgInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",cooporgInf.debug());
		return cooporgInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CooporgInf entity) throws Exception {
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
	public int modifyEntity(CooporgInf entity) throws Exception {
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
	public int removeEntity(CooporgInf entity) throws Exception {
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
	public int identifyId(CooporgInf entity) {
		return dao.identifyEntity(entity);
	}
	
	@Override
	public int findName(CooporgInf entity) {
		return dao.selectName(entity);
	}

	/**
	 * 获取合作机构编号
	 */
	@Override
	public String querySelectOptionCooporgNo() throws Exception {
		List<CooporgInf> cooporgInfList = dao.getCooporgNoList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> nmap = new HashMap<String, Object>();
		for (int i = 0; i < cooporgInfList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			CooporgInf cooporgInf = cooporgInfList.get(i);
			map.put("text", cooporgInf.getCoopname());
			map.put("value", cooporgInf.getCooporgNo());
			list.add(map);
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap);
	}

	/**
	 * 获取省份编号
	 */
	@Override
	public String querySelectOptionProvinceID() throws Exception {
		List<CooporgInf> cooporgInfList = dao.getProvinceNameList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> nmap = new HashMap<String, Object>();
		for (int i = 0; i < cooporgInfList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			CooporgInf cooporgInf = cooporgInfList.get(i);
			map.put("text", cooporgInf.getProvinceName());
			map.put("value", cooporgInf.getProvinceID());
			list.add(map);
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap);
	}
	
	/**
	 * 根据合作机构编号查询状态
	 */
	@Override
	public CooporgInf selectTypeEntity(CooporgInf entity) {
		return dao.selectTypeEntity(entity);
	}



	
	
}
