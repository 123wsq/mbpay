package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.ZLPayCheckAccountService;

/**
 * 对账 Task<br>
 * 
 * @author limiao
 */
public class ZLPayCheckAccountTask {
	private static Logger log = LoggerFactory.getLogger(ZLPayCheckAccountTask.class);

	@Autowired
	private ZLPayCheckAccountService zlPayCheckAccountService;

	public void run() {
		log.debug("中联对账  Task Start");
		try {
			zlPayCheckAccountService.process();
		} catch (Exception e) {
			log.error("中联对账   Exception:  " + e.getMessage());
			e.printStackTrace();
		}

		log.debug("中联对账 Task End");
	}
}
