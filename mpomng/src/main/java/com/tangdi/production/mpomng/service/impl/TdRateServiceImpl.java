package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpomng.bean.TdRateInf;
import com.tangdi.production.mpomng.dao.TdRateDao;
import com.tangdi.production.mpomng.service.TdRateService;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class TdRateServiceImpl implements TdRateService {
	private static final Logger log = LoggerFactory.getLogger(TdRateServiceImpl.class);
	@Autowired
	private TdRateDao dao;

	@Override
	public List<TdRateInf> getListPage(TdRateInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(TdRateInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public TdRateInf getEntity(TdRateInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		TdRateInf tdRateInf = null;
		try {
			tdRateInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		if (tdRateInf != null) {
			log.debug("处理结果:", tdRateInf.debug());
		}
		return tdRateInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(TdRateInf entity) throws Exception {
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
	public int modifyEntity(TdRateInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			if ("2".equals(entity.getParaValType())) {
				entity.setParaVal(MoneyUtils.toStrCent(entity.getParaVal()));
			}
			entity.setParaMaxMoney(MoneyUtils.toStrCent(entity.getParaMaxMoney()));
			if("0".equals(entity.getParaMaxMoney())){
				entity.setParaMaxMoney("");
			}
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
	public int removeEntity(TdRateInf entity) throws Exception {
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
