package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.SettleAccountsService;

/**
 * 商户 清算 Task<br>
 * <br>
 * 
 * @author limiao
 */
public class SettleAccountsTask {
	private static Logger log = LoggerFactory.getLogger(SettleAccountsTask.class);

	@Autowired
	private SettleAccountsService settleAccountsService;

	public void run() {
		log.debug("跑批  商户余额  清算 Task Start");
		try {
			settleAccountsService.process();
		} catch (Exception e) {
			log.error("跑批  商户余额  清算   Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  商户余额  清算 Task End");
	}
}
