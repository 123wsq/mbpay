package com.tangdi.production.mpbatch.schedule;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AccountMerBalanAt1Service;
/**
 * 根据商户余额 清算跑批 处理
 * @author VoStre
 *
 */
public class AccountMerSettleAT1Task {
	private static Logger log = LoggerFactory.getLogger(AccountBalanceT0Task.class);
	@Autowired
	private AccountMerBalanAt1Service AccountMerBalanAt1Service;
	
	public void run() {
		log.debug("跑批  AT1 账户余额自动提现清算处理   Task Start");
		log.info("跑批  AT1 账户余额自动提现清算处理   Task Start");
		Map<String, Object > map=new HashMap<String,Object>();
		try {
			AccountMerBalanAt1Service.processAccount(map);
			//AccountMerBalanAt1Service.processCas(map);
		} catch (Exception e) {
			log.error("跑批  AT1 账户余额自动提现清算处理 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  AT1 账户余额自动提现清算处理  Task End");
	}
}
