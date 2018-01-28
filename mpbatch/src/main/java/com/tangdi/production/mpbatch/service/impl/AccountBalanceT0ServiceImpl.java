package com.tangdi.production.mpbatch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.dao.AccountBalanceDao;
import com.tangdi.production.mpbatch.service.AccountBalanceT0Service;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * T+0 未处理提现订单订单转到T+1未提<br>
 * 
 * @author limiao
 */
@Service
public class AccountBalanceT0ServiceImpl implements AccountBalanceT0Service {
	private static Logger log = LoggerFactory.getLogger(AccountBalanceT0ServiceImpl.class);

	@Autowired
	private AccountBalanceDao accountBalanceDao;

	@Autowired
	private CasPrdService casPrdService;
	
	@Autowired
	private CustAccountService custAccountService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("跑批  T+0 未处理提现订单订单转到T+1未提     begin");

		Map<String, Object> parameterMap = null;

		/*
		 * 将T0 提现订单 状态为[00-未处理] 金额 转到 T+1未提 ,修改 T0提现订单 状态为[05-已取消]
		 */
//		String date = DateUtil.getTheDayBeforeYesterday();
		String ydate = DateUtil.getTheDayBeforeYesterday() + MsgCT.DAY_TIME_START;
		String cdate = DateUtil.getYesterday() + MsgCT.DAY_TIME_END;
//		String cdate = DateUtil.getDay();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("startTime", ydate);
		param.put("endTime", cdate);
		
		log.info("获取跑批订单日期=[{},{}]",ydate,cdate);
		List<Map<String,Object>> casPrdt0 = casPrdService.getCasPrdT0(param);
		log.info("T0未处理订单数=[{}]",casPrdt0.size());
		int num = 0;
		String cno = "";
		for(Map<String,Object> cp:casPrdt0){
			cno = cp.get("casOrdNo").toString();
			custAccountService.t0ToBlanceChange(cp);
			
			parameterMap = new HashMap<String, Object>();
			parameterMap.put("sucDate", TdExpBasicFunctions.GETDATETIME());// 
			parameterMap.put("ordStatus", "05");// 05:已取消
			parameterMap.put("casAudit", "01");// 0:不通过
			parameterMap.put("casOrdNo",cno);
			num = num + casPrdService.modifyCasPrdT0(parameterMap);
			log.info("T0提现订单[{}]处理完成.",cno);
		}
		log.info("跑批  T+0 未处理提现订单订单转到T+1未提     【" + num+ " 】条");
		
		log.info("处理清算中的订单开始");
		parameterMap = new HashMap<String, Object>();
//		parameterMap.put("casDate", date);// 
		parameterMap.put("ordStatus", "06");
		parameterMap.put("casAudit", "02");// 
		parameterMap.put("startTime", ydate);
		parameterMap.put("endTime", cdate);
		casPrdService.modifyCasPrdStatusForDone(parameterMap);
		
		log.info("处理清算中的订单完成");
		
		log.info("跑批  T+0 未处理提现订单订单转到T+1未提     end");
	}

}
