package com.tangdi.production.mprcs.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tangdi.production.mprcs.bean.BankCardBlacklistInf;
import com.tangdi.production.mprcs.dao.BankCardBlacklistDao;
import com.tangdi.production.mprcs.service.BankCardBlacklistService;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class BankCardBlacklistServiceImpl implements BankCardBlacklistService{
	private static final Logger log = LoggerFactory
			.getLogger(BankCardBlacklistServiceImpl.class);
	@Autowired
	private BankCardBlacklistDao bankCardBlacklistDao;

	@Override
	public List<BankCardBlacklistInf> getListPage(BankCardBlacklistInf entity) throws Exception {
		return bankCardBlacklistDao.selectList(entity);
	}

	@Override
	public Integer getCount(BankCardBlacklistInf entity) throws Exception {
		return bankCardBlacklistDao.countEntity(entity);
	}

	@Override
	public BankCardBlacklistInf getEntity(BankCardBlacklistInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		BankCardBlacklistInf bankCardBlacklistInf = null;
		
		try{
			bankCardBlacklistInf = bankCardBlacklistDao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		if(bankCardBlacklistInf != null){
		   log.debug("处理结果:",bankCardBlacklistInf.debug());
		}
		return bankCardBlacklistInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(BankCardBlacklistInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = bankCardBlacklistDao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("插入数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(BankCardBlacklistInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = bankCardBlacklistDao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(BankCardBlacklistInf entity) throws Exception {
		 int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
				String[] idlist = entity.getCardno().split(",");
				for (String id : idlist) {
					rt = rt + bankCardBlacklistDao.deleteEntity(new BankCardBlacklistInf(id));
				}
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		try {
			return bankCardBlacklistDao.getCount(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<BankCardBlacklistInf> getListPage(Map<String, Object> paramMap) {
		
		try {
			return bankCardBlacklistDao.getListPage(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	
	
}
