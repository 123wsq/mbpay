package com.tangdi.production.mpbase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.dao.UnionfitDao;
import com.tangdi.production.mpbase.service.UnionfitService;

@Service
public class UnionfitServiceImpl implements UnionfitService {
	private static final Logger log = LoggerFactory
			.getLogger(UnionfitServiceImpl.class);
	@Autowired
	private UnionfitDao unionfitDao;

	@Override
	public List<UnionfitInf> getListPage(UnionfitInf entity) throws Exception {
		return unionfitDao.selectList(entity);
	}

	@Override
	public Integer getCount(UnionfitInf entity) throws Exception {
		return unionfitDao.countEntity(entity);
	}

	@Override
	public UnionfitInf getEntity(UnionfitInf entity) throws Exception {
		return unionfitDao.selectEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(UnionfitInf entity) throws Exception {
		return unionfitDao.insertEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(UnionfitInf entity) throws Exception {
		return unionfitDao.updateEntity(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(UnionfitInf entity) throws Exception {
		return unionfitDao.deleteEntity(entity);
	}

	@Override
	public UnionfitInf getCardInf(String cardNo) throws Exception {
//		log.info("开始获取卡BIN信息,参数:track2={}",track2);
//		String cardNo = "";
//		if(track2.contains("D")){
//			cardNo = track2.substring(0, track2.indexOf("D"));
//		}else if(track2.contains("=")){
//			cardNo = track2.substring(0, track2.indexOf("="));
//		}
		log.info("解析卡号信息为:[{}]",cardNo);
		UnionfitInf bin = null;
		try{
			bin= unionfitDao.getCardInf(cardNo);
		}catch(Exception e){
			log.error("卡BIN信息查询异常!",e);
			throw new Exception(e.getMessage(),e);
		}
		if(bin != null){
			log.info("获取卡BIN信息:[{}]",bin.toMap());
		}
		return bin;
	}

}
