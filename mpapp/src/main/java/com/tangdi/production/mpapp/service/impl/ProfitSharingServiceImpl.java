package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.dao.AgentDao;
import com.tangdi.production.mpapp.dao.ProfitSharingDao;
import com.tangdi.production.mpapp.service.PlatformParameterService;
import com.tangdi.production.mpapp.service.ProfitSharingService;
import com.tangdi.production.mpapp.service.RatesInfService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 分润 计算业务实现
 * 
 * @author limiao
 * @version 1.0
 */
@Service
public class ProfitSharingServiceImpl implements ProfitSharingService {
	private static final Logger log = LoggerFactory.getLogger(ProfitSharingServiceImpl.class);
	@Autowired
	private ProfitSharingDao profitSharingDao;

	@Autowired
	private AgentDao agentDao;

	@Autowired
	private TerminalService terminalService;
	/**
	 * 平台参数
	 */
	@Autowired
	private PlatformParameterService platformParameterService;
	
	/**
	 * 费率查询接口
	 * */
	@Autowired
	private RatesInfService ratesInfService;

	/**
	 * 
	 * @param paramMap
	 *            (agentId,payNo,rate,termRate,payAmt,payFee)
	 *            代理商Id,支付订单号,费率类型,终端费率,金额,手续费
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void profitSharing(Map<String, Object> argMap) {
		log.info("计算分润金额参数：{}", argMap);
		log.info("支付订单号:[" + argMap.get("payNo") + "]计算分润金额");
		Map<String, Object> paramMap = null;
		boolean bool = true;
		try {
			paramMap = new HashMap<String, Object>();
			/* 1.查询出代理商费率 */
			paramMap.put("agentId", argMap.get("agentId"));
//			Integer nextAgentSharAmt = 0;
			do {
				Map<String, Object> agentRateMap = agentDao.selectAgentRate(paramMap);
				if(MsgCT.PAYTYPE_SM.equals(argMap.get("payType"))){
					agentRateMap.put("rateCode", "00"); //代理商
					agentRateMap.put("rateId", argMap.get("agentId"));
					agentRateMap.put("rateType", argMap.get("payType"));
					agentRateMap = ratesInfService.getRate(agentRateMap);
				}
				log.info("代理商费率参数：{}", agentRateMap);
				if (agentRateMap == null || agentRateMap.get("agentId") == null || agentRateMap.get("agentId").equals(agentRateMap.get("fathAgentId"))) {
					bool = false;
				}
				/* 2.根据代理商费率计算出手续费 */
				Map<String, Object> rateMap = terminalService.calcRateFee(argMap, agentRateMap);// 计算手续费
				paramMap.put("sharDate", TdExpBasicFunctions.GETDATE());// 分润计算日(年月日)
				paramMap.put("agentId", agentRateMap.get("agentId"));// 代理商ID
				paramMap.put("payno", argMap.get("payNo"));// 支付订单号
				paramMap.put("payamt", argMap.get("payAmt"));// 订单金额
				paramMap.put("payRate", argMap.get("termRate"));// 交易费率
				paramMap.put("payFee", argMap.get("payFee"));// 手续费
				
				paramMap.put("agentRate", rateMap.get("rate"));// 代理商费率
				paramMap.put("agentDgr", agentRateMap.get("agentDgr"));// 代理商等级
				paramMap.put("fathAgentId", agentRateMap.get("fathAgentId"));// 上级代理商
				paramMap.put("firstAgentId", agentRateMap.get("firstAgentId"));// 一级代理商
				paramMap.put("sharTime", TdExpBasicFunctions.GETDATETIME());
				
				Integer payFee = NumberUtils.toInt(paramMap.get("payFee").toString());
				Integer agentFee = NumberUtils.toInt(rateMap.get("fee").toString());
				Integer sharamt = (int) (payFee - agentFee);
				paramMap.put("agentFee", agentFee);
				 paramMap.put("sharamt", sharamt);
//				paramMap.put("sharamt", sharamt - nextAgentSharAmt);
				 
				paramMap.put("payType", argMap.get("payType"));
				
				log.info("分润数据:" + paramMap.toString());
				/* 3.insert 分润表 */
				profitSharingDao.insertEntity(paramMap);
				paramMap.put("agentId", agentRateMap.get("fathAgentId"));
//				nextAgentSharAmt = sharamt;// 下一次分润金额基数
			} while (bool);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("计算分润 异常:" + e.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 * @param paramMap
	 *            (agentId,payNo)
	 *            代理商Id,支付订单号
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void profitSharingCancel(Map<String, Object> argMap) {
		log.info("删除分润参数：{}", argMap);
		log.info("支付订单号:[" + argMap.get("payNo") + "]删除分润数据");
		Map<String, Object> paramMap = null;
		try {
			paramMap = new HashMap<String, Object>();
			paramMap.put("agentId", argMap.get("agentId"));
			paramMap.put("payno", argMap.get("payNo"));// 支付订单号

			profitSharingDao.deleteEntity(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除分润 异常:" + e.getLocalizedMessage());
		}
	}

//	/**
//	 * 
//	 * @param paramMap
//	 *            (agentId,payNo,rate,termRate,payAmt,payFee)
//	 *            代理商Id,支付订单号,费率类型,终端费率,金额,手续费
//	 */
//	public void profitSharing1(Map<String, Object> argMap) {
//		System.out.println("计算分润金额参数" + argMap);
//		Map<String, Object> paramMap = null;
//		boolean bool = true;
//		try {
//			paramMap = new HashMap<String, Object>();
//			/* 1.查询出代理商费率 */
//			paramMap.put("agentId", argMap.get("agentId"));
//			Integer nextAgentSharAmt = 0;
//			int i=0;
//			do {
////				Map<String, Object> agentRateMap = agentDao.selectAgentRate(paramMap);
//				Map<String, Object> agentRateMap=null;
//				if(i==0){
//					agentRateMap=new HashMap<String, Object>();
//					agentRateMap.put("agentId", "33");
//					agentRateMap.put("agentDgr", 3);
//					agentRateMap.put("fathAgentId", "22");
//					agentRateMap.put("firstAgentId", "11");
//					agentRateMap.put("profitRatio", "0");
//					agentRateMap.put("rateLivelihood", "0.4");
//					agentRateMap.put("rateGeneral", "0.78");
//					agentRateMap.put("rateGeneralTop", "0.7");
//					agentRateMap.put("rateGeneralMaximun", "3500");
//					agentRateMap.put("rateEntertainTop", "1.2");
//					agentRateMap.put("rateEntertainMaximun", "80");
//				}
//				if(i==1){
//					agentRateMap=new HashMap<String, Object>();
//					agentRateMap.put("agentId", "22");
//					agentRateMap.put("agentDgr", 2);
//					agentRateMap.put("fathAgentId", "11");
//					agentRateMap.put("firstAgentId", "11");
//					agentRateMap.put("profitRatio", "0");
//					agentRateMap.put("rateLivelihood", "0.35");
//					agentRateMap.put("rateGeneral", "0.78");
//					agentRateMap.put("rateGeneralTop", "0.7");
//					agentRateMap.put("rateGeneralMaximun", "3500");
//					agentRateMap.put("rateEntertainTop", "1.2");
//					agentRateMap.put("rateEntertainMaximun", "80");
//				}
//				if(i==2){
//					agentRateMap=new HashMap<String, Object>();
//					agentRateMap.put("agentId", "11");
//					agentRateMap.put("agentDgr", 1);
//					agentRateMap.put("fathAgentId", "11");
//					agentRateMap.put("firstAgentId", "11");
//					agentRateMap.put("profitRatio", "0");
//					agentRateMap.put("rateLivelihood", "0.3");
//					agentRateMap.put("rateGeneral", "0.78");
//					agentRateMap.put("rateGeneralTop", "0.7");
//					agentRateMap.put("rateGeneralMaximun", "3500");
//					agentRateMap.put("rateEntertainTop", "1.2");
//					agentRateMap.put("rateEntertainMaximun", "80");
//				}
//				
//				log.info("代理商费率参数：{}", agentRateMap);
//				if (agentRateMap == null || agentRateMap.get("agentId") == null || agentRateMap.get("agentId").equals(agentRateMap.get("fathAgentId"))) {
//					bool = false;
//				}
//				/* 2.根据代理商费率计算出手续费 */
//				Map<String, Object> rateMap = new TerminalServiceImpl().calcRateFee(argMap, agentRateMap);// 计算手续费
//				paramMap.put("sharDate", TdExpBasicFunctions.GETDATE());// 分润计算日(年月日)
//				paramMap.put("agentId", agentRateMap.get("agentId"));// 代理商ID
//				paramMap.put("payno", argMap.get("payNo"));// 支付订单号
//				paramMap.put("payamt", argMap.get("payAmt"));// 订单金额
//				paramMap.put("payRate", argMap.get("termRate"));// 交易费率
//				paramMap.put("payFee", argMap.get("payFee"));// 手续费
//				paramMap.put("fathAgentId", agentRateMap.get("fathAgentId"));// 上级代理商
//				paramMap.put("agentDgr", agentRateMap.get("agentDgr"));// 代理商等级
//				paramMap.put("agentRate", rateMap.get("rate"));// 代理商费率
//				Integer payFee = NumberUtils.toInt(paramMap.get("payFee").toString());
//				Integer agentFee = NumberUtils.toInt(rateMap.get("fee").toString());
//				Integer sharamt = (int) (payFee - agentFee);
//				paramMap.put("agentFee", agentFee);
//				// paramMap.put("sharamt", sharamt);
//				
//				System.out.println(payFee+"------"+agentFee);
//				paramMap.put("sharamt", sharamt - nextAgentSharAmt);
//
//				paramMap.put("sharStatus", "00");
//				paramMap.put("sharTime", TdExpBasicFunctions.GETDATETIME());
//				System.out.println("分润数据:" + paramMap);
//				/* 3.insert 分润表 */
////				profitSharingDao.insertEntity(paramMap);
//				paramMap.put("agentId", agentRateMap.get("fathAgentId"));
//				nextAgentSharAmt = sharamt;// 下一次分润金额基数
//				
//				i++;
//			} while (bool);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("计算分润 异常:" + e.getLocalizedMessage());
//		}
//	}
//
//	public static void main(String[] args) {
//		ProfitSharingServiceImpl impl = new ProfitSharingServiceImpl();
//		Map<String, Object> argMap = new HashMap<String, Object>();
//		// agentId,payNo,rate,termRate,payAmt,payFee
//
//		argMap.put("agentId", "33");
//		argMap.put("payNo", "101010");
//		argMap.put("rate", "1");
//		argMap.put("termRate", "0.5");
//		argMap.put("payAmt", "100000");
//		argMap.put("payFee", "500");
//
//		impl.profitSharing1(argMap);
//	}

}
