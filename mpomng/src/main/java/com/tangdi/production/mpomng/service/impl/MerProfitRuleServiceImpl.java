package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpomng.dao.MerProfitRuleDao;
import com.tangdi.production.mpomng.service.MerProfitRuleService;
import com.tangdi.production.mpomng.bean.MerProfitRuleInf;


/**
 * 分润规则管理业务实现
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class MerProfitRuleServiceImpl implements MerProfitRuleService{
	private static final Logger log = LoggerFactory
			.getLogger(MerProfitRuleServiceImpl.class);
	@Autowired
	private MerProfitRuleDao dao;

	@Override
	public List<MerProfitRuleInf> getListPage(MerProfitRuleInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(MerProfitRuleInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public MerProfitRuleInf getEntity(MerProfitRuleInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		MerProfitRuleInf merProfitRuleInf = null;
		try{
			merProfitRuleInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		if(merProfitRuleInf != null){
			log.debug("处理结果:",merProfitRuleInf.debug());
		}
		return merProfitRuleInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(MerProfitRuleInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 entity.setRateTop(MoneyUtils.toStrCent(entity.getRateTop()));           
			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("插入数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(MerProfitRuleInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 entity.setRateTop(MoneyUtils.toStrCent(entity.getRateTop()));
			 rt = dao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(MerProfitRuleInf entity) throws Exception {
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
