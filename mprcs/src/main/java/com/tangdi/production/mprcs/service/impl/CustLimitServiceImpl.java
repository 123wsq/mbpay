package com.tangdi.production.mprcs.service.impl;


import java.util.HashMap;
import java.util.List;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mprcs.bean.CustLimitInf;
import com.tangdi.production.mprcs.dao.CustLimitDao;
import com.tangdi.production.mprcs.service.CustLimitService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;


/**
 * 
 * @author zhengqiang 2015/9/11
 * @version 1.0
 *
 */
@Service
public class CustLimitServiceImpl implements CustLimitService{
	private static final Logger log = LoggerFactory
			.getLogger(CustLimitServiceImpl.class);
	@Autowired
	private CustLimitDao dao;

	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<CustLimitInf> getListPage(CustLimitInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CustLimitInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CustLimitInf getEntity(CustLimitInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustLimitInf userLimitInf = null;
		try{
			userLimitInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		return userLimitInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CustLimitInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		//查询商户是否存在
		String custName = dao.selectCust(entity.getLimitCustId());
		if(custName == null || custName.equals("")){
			throw new Exception("商户编号不存在!");
		}
		
		//查询限额信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("limitCustId", entity.getLimitCustId());
		map.put("limitBusType", entity.getLimitBusType());
		map.put("limitPayWay", entity.getLimitPayWay());
		map.put("limitType", "20");
		
		int is = dao.validate(map);
		log.info("商户限额存在状态:[{}]",is);
		if(is > 0){
			throw new Exception("商户对应的限额业务已经存在!");
		}
		try{

			String limitId=seqNoService.getSeqNoNew("LIMIT_ID", "9", "0");
			entity.setLimitId(limitId);
			entity.setLimitType("20");   //20 商户限额
			entity.setLimitSubBus("00"); //00 所有子业务
			entity.setIsUse("1");         //1 启用
			entity.setLimitMinAmt(MoneyUtils.toStrCent(entity.getLimitMinAmt()));
			entity.setLimitMaxAmt(MoneyUtils.toStrCent(entity.getLimitMaxAmt()));
			entity.setLimitDayAmt(MoneyUtils.toStrCent(entity.getLimitDayAmt()));
			entity.setLimitMonthAmt(MoneyUtils.toStrCent(entity.getLimitMonthAmt()));
			entity.setLimitAgentId(" ");

			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("添加商户限额数据异常!",e);
		}
		return rt;
	}
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addAgentEntity(CustLimitInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		//查询商户是否存在
		String custName = dao.selectAgent(entity.getLimitAgentId());
		if(custName == null || custName.equals("")){
			throw new Exception("代理商编号不存在不存在!");
		}
		
		//查询限额信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("limitAgentId", entity.getLimitAgentId());
		map.put("limitBusType", entity.getLimitBusType());
		map.put("limitPayWay", entity.getLimitPayWay());
		map.put("limitType", "40");
		
		int is = dao.validateAgent(map);
		log.info("代理商限额存在状态:[{}]",is);
		if(is > 0){
			throw new Exception("代理商对应的限额业务已经存在!");
		}
		try{

			String limitId=seqNoService.getSeqNoNew("LIMIT_ID", "9", "0");
			entity.setLimitId(limitId);
			entity.setLimitType("40");   //40 代理商限额
			entity.setLimitSubBus("00"); //00 所有子业务
			entity.setIsUse("1");         //1 启用
			entity.setLimitMinAmt(MoneyUtils.toStrCent(entity.getLimitMinAmt()));
			entity.setLimitMaxAmt(MoneyUtils.toStrCent(entity.getLimitMaxAmt()));
			entity.setLimitDayAmt(MoneyUtils.toStrCent(entity.getLimitDayAmt()));
			entity.setLimitMonthAmt(MoneyUtils.toStrCent(entity.getLimitMonthAmt()));
			entity.setLimitCustId(" ");

			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("添加代理商限额数据异常!",e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(CustLimitInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			entity.setLimitMinAmt(MoneyUtils.toStrCent(entity.getLimitMinAmt()));
			entity.setLimitMaxAmt(MoneyUtils.toStrCent(entity.getLimitMaxAmt()));
			entity.setLimitDayAmt(MoneyUtils.toStrCent(entity.getLimitDayAmt()));
			entity.setLimitMonthAmt(MoneyUtils.toStrCent(entity.getLimitMonthAmt()));
			
			 rt = dao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(CustLimitInf entity) throws Exception {
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyUserLimitManageStatus(Map<String, Object> map)
			throws Exception {
		log.info("更新启用状态值:[{}]", map);
		return dao.modifyUserLimitManageStatus(map);

	}

	@Override
	public int validateCust(CustLimitInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	
}
