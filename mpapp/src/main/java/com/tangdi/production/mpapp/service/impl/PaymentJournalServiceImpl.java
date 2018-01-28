package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.dao.PaymentJournalDao;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;



/**
 * 
 * @author zhengqiang 
 * @version 1.0
 *
 */
@Service
public class PaymentJournalServiceImpl implements PaymentJournalService{
	private static final Logger log = LoggerFactory
			.getLogger(PaymentJournalServiceImpl.class);
	@Autowired
	private PaymentJournalDao paymentJournalDao;

	@Autowired
	private GetSeqNoService seqNoService;
	
	

	@Override
	public String addPaymentJournal(Map<String, Object> param) throws TranException{
		log.info("新增交易流水业务开始，请求参数：{}",param);
		String paymentId = "";
		int rt=0;
		try {
			paymentId= "PMJ"+TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("PAYMET", "9", "1");
			param.put("paymentId", paymentId);
			rt=paymentJournalDao.insertEntity(param);
		} catch (Exception e) {
			log.error("交易流水新增异常：{}",e);
			throw new TranException(ExcepCode.EX000814,e);
		}
		if (rt<=0) {
			throw new TranException(ExcepCode.EX000815,"交易流水信息新增失败");
		}
		return paymentId;
	}

	/**
	 * 事物隔离级别
	 * REQUIRES_NEW 开启新的事物
	 * 
	 */
	@Override @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int modifyPaymentJournal(Map<String, Object> param)
			throws TranException{
		log.info("更新交易流水业务开始，请求参数：{}",param);
		int rt=0;
		try {
			String txndate = TdExpBasicFunctions.GETDATETIME();
			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("payordno", param.get("payNo"));
			updateMap.put("txnDate", txndate.substring(0, 8));  //系统交易日期
			updateMap.put("txnTime", txndate.substring(8));     //系统交易时间
			updateMap.put("txnType", param.get("txnType"));
			updateMap.put("txnSrefno", "000000");
			updateMap.put("txnCrdflg", param.get("dcflag"));
			updateMap.put("txnActno",  param.get("cardNo"));
			updateMap.put("txnAmt", param.get("txamt"));
			updateMap.put("txnFee", "");   //手续费
			updateMap.put("txnStatus", param.get("txnstatus"));
			updateMap.put("inmod", param.get("InMod"));
			updateMap.put("ttxndt", param.get("CTxnDt"));     //第三方交易日期
			updateMap.put("ttxntm", param.get("CTxnTm"));     //第三方交易时间
			updateMap.put("tmercid", param.get("TMercId"));
			updateMap.put("ttermid", param.get("TTermId"));
			updateMap.put("tlogno", param.get("posSeqNo"));   //pos流水号
			updateMap.put("tbatno", param.get("batchNo"));
			updateMap.put("tautcod", param.get("AutCod"));    //授权码
			updateMap.put("toprid", null);
			updateMap.put("tcpscod", param.get("TCPSCod"));
			updateMap.put("tsrefno", param.get("SRefNo"));
			updateMap.put("paymentId", param.get("paymentId"));
			updateMap.put("cause", param.get("cause"));
			rt=paymentJournalDao.updateEntity(updateMap);
		} catch (Exception e) {
			log.error("交易流水更新异常：{}",e);
			throw new TranException(ExcepCode.EX000814,e);
		}
		log.info("交易流水更新完成.结果:[{}]",rt);
		return rt;
	}

	@Override
	public Map<String, Object> queryOrnPaymentJournal(Map<String, Object> param)
			throws TranException {
		Map<String, Object> map = null;
		try {
			map = paymentJournalDao.queryOrnPaymentJournal(param);
			if (map == null) {
				throw new TranException(ExcepCode.EX080010, "支付流水不存在");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080010, e);
		}
		return map;
	}
	
	/**
	 * 事物隔离级别
	 * REQUIRES_NEW 开启新的事物
	 * 
	 */
	@Override @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int modifyPaymentJournalStatus(Map<String, Object> param)
			throws TranException{
		log.info("更新交易流水业务开始，请求参数：{}",param);
		int rt=0;
		try {
			Map<String,Object> updateMap = new HashMap<String,Object>();
			updateMap.put("txnStatus", param.get("txnstatus"));
			updateMap.put("paymentId", param.get("paymentId"));
			
			rt=paymentJournalDao.updatePayJnlStatus(updateMap);
		} catch (Exception e) {
			log.error("交易流水更新异常：{}",e);
			throw new TranException(ExcepCode.EX000814,e);
		}
		log.info("交易流水更新完成.结果:[{}]",rt);
		return rt;
	}
	
	
}
