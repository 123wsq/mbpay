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

import com.tangdi.production.mpbatch.dao.AccountBalanceDao;
import com.tangdi.production.mpbatch.service.AccountBalanceT1YService;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.mpomng.service.CustBankInfService;

/**
 * T+0&T+1金额 转到 T+1未提 <br>
 * <br>
 * 
 * @author limiao
 */
@Service
public class AccountBalanceT1YServiceImpl implements AccountBalanceT1YService {
	private static Logger log = LoggerFactory.getLogger(AccountBalanceT1YServiceImpl.class);

	@Autowired
	private AccountBalanceDao accountBalanceDao;
	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private CustBankInfService custBankInfService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("跑批  T+1 金额 转到 T+1 未提     begin");
		/* T+0&T+1 金额 转到 T+1未提 */

		List<Map<String,Object>> cas = custAccountService.queryT1();
		int num = 0;
		for(Map<String,Object> ca:cas){
			String custId = ca.get("custId").toString();
			int banknum = custBankInfService.bankNum(custId);
			if(banknum <= 0){
				log.info(" 商户编号：{}未绑定银行卡，不能转余额",custId);
				continue;
			}
			custAccountService.t1ToBlanceChange(ca);
			log.info("账户[{}]T1AP余额转T1Y处理完成.",ca.get("account"));
			num = num + 1;
		}
		log.info("跑批  T+1 金额 转到 T+1 未提    【" + num + " 】条");

		log.info("跑批  T+1 金额 转到 T+1 未提     end");
	}
}
