package com.tangdi.production.mprcs.service.impl;

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
import com.tangdi.production.mprcs.dao.CustLevelDao;
import com.tangdi.production.mprcs.service.CustLevelService;
import com.tangdi.production.mprcs.bean.CustLevelInf;



/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class CustLevelServiceImpl implements CustLevelService{
	private static final Logger log = LoggerFactory
			.getLogger(CustLevelServiceImpl.class);
	@Autowired
	private CustLevelDao dao;

	@Override
	public List<CustLevelInf> getListPage(CustLevelInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CustLevelInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CustLevelInf getEntity(CustLevelInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustLevelInf custLevelInf = null;
		try{
			custLevelInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		if(custLevelInf != null){
		    log.debug("处理结果:",custLevelInf.debug());
		}
		return custLevelInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CustLevelInf entity) throws Exception {
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
	public int modifyEntity(CustLevelInf entity) throws Exception {
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
	public int removeEntity(CustLevelInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{ 
			String[] levelIds=entity.getLevel().split(",");
			for (int i = 0; i < levelIds.length; i++ ){
				entity.setLevel(levelIds[i]);
				rt = rt + dao.deleteEntity(entity);
			}
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}


	/**
	 * 默认查询所有的商户等级信息
	 * 
	 * @return CustLevelInf的List
	 */
	public List<CustLevelInf> querycustLevelAll() {
		return dao.custLevelAll();
	}

	public String querySelectOption() throws Exception {
		List<CustLevelInf> custLevellist = querycustLevelAll();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HashMap<String,Object> nmap = new HashMap<String,Object>();
	
		for(int i=0;i<custLevellist.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			CustLevelInf key = custLevellist.get(i);
			
				map.put("text", key.getLevelName());
				map.put("value",key.getLevel());
				list.add(map);
			
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap) ;
	}
	
}
