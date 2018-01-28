package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AccountBalanceT0Service;

/**
 * 跑批 T+0 未处理提现订单订单转到T+1未提 Task<br>
 * <br>
 * 
 * @author limiao
 */
public class AccountBalanceT0Task {
	private static Logger log = LoggerFactory.getLogger(AccountBalanceT0Task.class);

	@Autowired
	private AccountBalanceT0Service accountBalanceT0Service;

	public void run() {
		log.debug("跑批  T+0 未处理提现订单订单转到T+1未提   Task Start");

		try {
			accountBalanceT0Service.process();
		} catch (Exception e) {
			log.error("跑批  T+0 未处理提现订单订单转到T+1未提 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  T+0 未处理提现订单订单转到T+1未提   Task End");

	}
}
