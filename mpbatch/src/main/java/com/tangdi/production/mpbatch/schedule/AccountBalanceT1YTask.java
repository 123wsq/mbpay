package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AccountBalanceT1YService;

/**
 * 跑批 T+0&T+1 金额 转到 T+1 未提 Task <br>
 * <br>
 * 
 * @author limiao
 */
public class AccountBalanceT1YTask {
	private static Logger log = LoggerFactory.getLogger(AccountBalanceT1YTask.class);

	@Autowired
	private AccountBalanceT1YService accountBalanceT1YService;

	public void run() {
		log.debug("跑批  T+0&T+1 金额 转到 T+1 未提   Task start");
		try {
			accountBalanceT1YService.process();
		} catch (Exception e) {
			log.error("跑批  T+0&T+1 金额 转到 T+1 未提   Exception:  " + e.getMessage());
			e.printStackTrace();
		}

		log.debug("跑批  T+0&T+1 金额 转到 T+1 未提   Task End");
	}
}
