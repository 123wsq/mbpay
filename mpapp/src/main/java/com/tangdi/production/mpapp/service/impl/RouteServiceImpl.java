package com.tangdi.production.mpapp.service.impl;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.RouteDao;
import com.tangdi.production.mpapp.service.RouteService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mprcs.service.RcsTransactionService;

/**
 * [银联|第三方合作机构]路由接口实现
 * 
 * @author zhengqiang 2013/3/20
 *
 */
@Service
public class RouteServiceImpl implements RouteService {
	private static Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);
	@Autowired
	private RouteDao dao;
	/**
	 * 合作机构终端风控接口
	 */
	@Autowired
	private RcsTransactionService rcsTransactionService;
	
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop;
	
	@Override
	public Map<String, Object> route(Map<String, Object> param) throws TranException {
		Map<String, Object> routeMap = null;
		log.info("路由查询. 参数:{}", param);
		/**
		 * agentId 代理商ID rateType 费率类型 payAmt 交易金额 rtrType 路由类型
		 */
		ParamValidate.doing(param, "agentId", "rateType", "rtrType");
		String agentId = String.valueOf(param.get("agentId"));
		String firstAgentId = String.valueOf(param.get("firstAgentId"));

		/******** 判断当前代理商 是否为一级代理商。 子代理商都走一级代理商的路由 ********/
		if (!agentId.equals(firstAgentId)) {
			param.put("agentId", firstAgentId);
		}

		if (!MsgST.RATE_TYPE_CH00.equals(param.get("rateType"))) {
			if(null == param.get("daifu") || "".equals(param.get("daifu"))){
				ParamValidate.doing(param, "payAmt");
				try {
					routeMap = rcsTransactionService.checkOrgTerm(param);
					if (routeMap == null || routeMap.size() <= 0) {
						throw new TranException(ExcepCode.EX000913, "未找到可用路由信息！");
					}
					param.putAll(routeMap);
				} catch (TranException e) {
					throw new TranException(ExcepCode.EX000913, "查询通道路由信息异常！", e);
				}
			}else if("y".equals(param.get("daifu"))){
				param.put("merNo", param.get("cooporgMerNo"));//代付用对应通道的商户号
				try {
					routeMap = dao.selectDaiRoute(param);
				} catch (Exception e) {
					throw new TranException(ExcepCode.EX000913, "查询通道路由信息异常！", e);
				}
				log.info("代付路由信息:{}", routeMap);
				return routeMap;
			}
		}
		try {
			if("y".equals(param.get("lmtQry"))){
				routeMap = dao.selectLmtRoute(param);
			}else{
				routeMap = dao.selectRoute(param);
			}
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000913, "查询通道路由信息异常！", e);
		}
		/******** 判断当前代理商 是否为一级代理商。 子代理商都走一级代理商的路由 ********/
		param.put("agentId", agentId);

		log.info("已选路由信息:{}", routeMap);
		param.putAll(routeMap);
		return param;
	}
	
	
	
	
	
	
	

//	@Override
//	public Map<String, Object> payRoute(Map<String, Object> param) throws TranException {
//		Map<String, Object> routeMap = null;
//		List<Map<String, Object>> routeList = null;
//		log.info("路由查询. 参数:{}", param);
//		/**
//		 * agentId 代理商ID rateType 费率类型 payAmt 交易金额
//		 */
//		ParamValidate.doing(param, "agentId", "rateType", "payAmt");
//		try {
//			// 消费
//			param.put("rtrType", MsgCT.RTR_TYPE_CONSUME);
//			routeList = dao.selectRoute(param);
//		} catch (Exception e) {
//			throw new TranException(ExcepCode.EX000913, "查询通道路由信息异常！", e);
//		}
//		if (routeList == null || routeList.size() <= 0) {
//			throw new TranException(ExcepCode.EX000913, "查询不到相关通道路由信息！");
//		}
//		int pt = 0;
//		double logLowAmt = 0, logHighAmt = 0;
//		log.debug("路由列表:{}", routeList);
//		for (Map<String, Object> route : routeList) {
//			if (route.get("merStatus").toString().equals("0") && route.get("status").toString().equals("0")) {
//
//				/**
//				 * 限额检查. 1.单笔交易限额检查 2.单日、月限额检查
//				 */
//				/*
//				 * log.info("合作机构[{}],终端[{}]检查.",route.get("orgNo"),route.get(
//				 * "TTermId")); log.info("单笔交易限额检查."); String low
//				 * =route.get("lowlimit") == null?"":
//				 * route.get("lowlimit").toString(); String
//				 * high=route.get("highlimit")== null?"":
//				 * route.get("highlimit").toString(); String amt =
//				 * param.get("payAmt").toString(); //交易金额 double payAmt =
//				 * MoneyUtils.toDoubleCent(amt); //终端最低交易金额 double lowPayAmt =
//				 * MoneyUtils.toDoubleCent(low==""?"0":low); //终端最高交易金额 double
//				 * highPayAmt = MoneyUtils.toDoubleCent(high==""?"0":high);
//				 * if(payAmt < lowPayAmt){ pt ++ ; logLowAmt = lowPayAmt;
//				 * continue; } if(payAmt < highPayAmt){ pt ++; logHighAmt =
//				 * highPayAmt; continue; } try{
//				 * rcsTransactionService.checkTxnAmt(param);
//				 * }catch(TranException e){ continue; }
//				 */
//				routeMap = new HashMap<String, Object>();
//				routeMap.putAll(route);
//				break;
//			}
//		}
//		if (routeMap == null) {
//			if (pt > 0) {
//				throw new TranException(ExcepCode.EX000821, String.valueOf(logLowAmt / 100), String.valueOf(logHighAmt / 100));
//			}
//		}
//
//		log.info("已选路由信息:{}", routeMap);
//		param.putAll(routeMap);
//		return routeMap;
//	}
//
//	@Override
//	public Map<String, Object> route(Map<String, Object> param) throws TranException {
//		Map<String, Object> routeMap = null;
//		List<Map<String, Object>> routeList = null;
//		log.info("路由查询. 参数:{}", param);
//		/**
//		 * agentId 代理商ID
//		 */
//		ParamValidate.doing(param, "agentId");
//		try {
//			routeList = dao.selectRoute(param);
//		} catch (Exception e) {
//			throw new TranException(ExcepCode.EX000913, "查询通道路由信息异常！", e);
//		}
//		if (routeList == null || routeList.size() <= 0) {
//			throw new TranException(ExcepCode.EX000913, "查询不到相关通道路由信息！");
//		}
//		log.debug("路由列表:{}", routeList);
//		for (Map<String, Object> route : routeList) {
//			if (route.get("merStatus").toString().equals("0") && route.get("status").toString().equals("0")) {
//				routeMap = new HashMap<String, Object>();
//				routeMap.putAll(route);
//				break;
//			}
//		}
//		if (routeMap == null) {
//			throw new TranException(ExcepCode.EX000913, "未找到可用路由信息！");
//		}
//
//		log.info("已选路由信息:{}", routeMap);
//		param.putAll(routeMap);
//		return routeMap;
//	}

}
