package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.dao.CasInfDao;
import com.tangdi.production.mpapp.service.CasService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;



/**
 * 
 * @author zhengqiang 2015/9/17
 * @version 1.0
 *
 */
@Service
public class CasServiceImpl implements CasService{
	private static final Logger log = LoggerFactory
			.getLogger(CasServiceImpl.class);
	@Autowired
	private CasInfDao casInfDao;
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCasInf(Map<String, Object> param) throws TranException {
		log.info("提现订单创建开始：{}",param);
		String casId =null;
		int rt=0;
		try {
			casId= TdExpBasicFunctions.GETDATE()+ seqNoService.getSeqNoNew("CASID", "9", "1");
			param.put("casOrdNo", casId);
			param.put("casDate", TdExpBasicFunctions.GETDATETIME());
			rt=casInfDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060000,e);
		} 
		if(rt==0){
			throw new TranException(ExcepCode.EX060001);
		}
		return rt;
	}

	@Override
	public Map<String,Object> totalT0Amt(String custId) throws TranException {
		Map<String,Object> t0 = null;
		String date = TdExpBasicFunctions.GETDATE();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("custId", custId);
			param.put("casDate", date);
			t0 = casInfDao.sumT0Amt(param);
			Object totalTxnAmt = t0.get("totalTxnAmt");
			if(totalTxnAmt == null){
				t0.put("totalTxnAmt", 0);
			}
			
			log.info("商户[{}|{}]共提现[{}元 | {}次]",custId,date,
					MoneyUtils.toStrYuan(t0.get("totalTxnAmt")),t0.get("totalTxnNum"));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060005,e);
		}
		return t0;
	}
	
	@Override
	public Map<String,Object> totalAgeT0Amt(String custId) throws TranException {
		Map<String,Object> t0 = null;
		String date = TdExpBasicFunctions.GETDATE();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("custId", custId);
			param.put("casDate", date);
			t0 = casInfDao.sumAgentT0Amt(param);
			Object totalTxnAmt = t0.get("totalTxnAmt");
			if(totalTxnAmt == null){
				t0.put("totalTxnAmt", 0);
			}
			
			log.info("商户[{}|{}]代理商{}共提现[{}元 | {}次]",custId,date,t0.get("agentId"),
					MoneyUtils.toStrYuan(t0.get("totalTxnAmt")),t0.get("totalTxnNum"));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060005,e);
		}
		return t0;
	}
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addConCasInf(Map<String, Object> param) throws TranException {
		log.info("确认提现订单创建开始：{}",param);
		int rt=0;
		try {
			rt=casInfDao.insertConPay(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060000,e);
		} 
		if(rt==0){
			throw new TranException(ExcepCode.EX060001);
		}
		return rt;
	}
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateCasInfStatus(Map<String, Object> param) throws TranException {
		log.info("确认提现订单创建开始：{}",param);
		int rt=0;
		try {
			rt=casInfDao.modifyCasPrdBystauts(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060000,e);
		} 
		if(rt==0){
			throw new TranException(ExcepCode.EX060001);
		}
		return rt;
	}
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteConPayByCasId(String casId) throws TranException {
		log.info("确认提现订单创建开始：{}",casId);
		int rt=0;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("casOrdNo", casId);
			rt=casInfDao.deleteConPay(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060000,e);
		} 
		return rt;
	}
	
}
