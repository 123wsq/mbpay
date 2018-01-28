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

import com.tangdi.production.mpbatch.service.SettleAccountsService;
import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.mpomng.service.CustBankInfService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 商户 清算[暂时未使用]<br>
 * <br>
 * 
 * @author limiao
 */
@Service
public class SettleAccountsServiceImpl implements SettleAccountsService {

	private static Logger log = LoggerFactory.getLogger(SettleAccountsServiceImpl.class);

	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private CasPrdService casPrdService;
	
	@Autowired
	private CustBankInfService custBankInfService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("跑批  商户余额  清算     begin");
//		log.info("设置清算中的状态为已清算");
//		casPrdService.setSettlementStatusEnd();
//		log.info("设置已清算中的状态结束");
		/* 1.将营业执照认证商户 的 交易金额 清算 */
		List<CustAccountInf> list = custAccountService.getCustAccountAllLock();
		log.info("共查询清算账户:[{}]",list.size());
	
		
		for(CustAccountInf account:list){
				log.info("处理账户：{}",account.toMap());
				CustBankInf bankInf = custBankInfService.getEntity(new CustBankInf(account.getCustId(),"01","1"));
				if(null == bankInf){
					log.info(" 账户：{}未绑定银行卡，不能清算",account.getCustId());
					continue;
				}
				log.info("用户银行卡信息：{}",bankInf.toMap());
				Map<String,Object> inMap = new HashMap<String,Object>();
				String t1 = account.getAcT1Y();
				inMap.put("casType", "01"); // T1 提现
				inMap.put("custId", account.getCustId());
				inMap.put("custName", bankInf.getCustName());
				inMap.put("txamt", t1);
				inMap.put("casDate", TdExpBasicFunctions.GETDATETIME());
				inMap.put("sucDate", TdExpBasicFunctions.GETDATETIME());
				inMap.put("rate", "0");
				inMap.put("fee", "0");
				inMap.put("serviceFee", "0");
				inMap.put("netrecamt", t1);
				inMap.put("issno", bankInf.getIssno());
				inMap.put("issnam", bankInf.getIssnam());
				inMap.put("cardNo", bankInf.getCardNo());
				inMap.put("provinceId", bankInf.getProvinceId());
				inMap.put("casDesc", "");
				inMap.put("auditDesc", "系统自动审核");
				inMap.put("t0Amt", "0");
				inMap.put("t1Amt", t1);
				inMap.put("acType", account.getAcType());
				
				log.info("准备订单数据:{}",inMap);
				casPrdService.addCas(inMap);
				
				Map<String,Object> updateAccountMap = new HashMap<String,Object>();
				updateAccountMap.put("t0", "0");
				updateAccountMap.put("t1", t1);
				updateAccountMap.put("custId", account.getCustId());
				
				custAccountService.t1yToBlanceChange(updateAccountMap);
				
				log.info("商户[{}]清算完成.",bankInf.getCustName());
		}
		log.info("跑批  商户余额  清算     end");
	}

}
