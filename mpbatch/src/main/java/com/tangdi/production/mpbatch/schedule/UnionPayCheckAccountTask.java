package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.UnionPayCheckAccountService;

/**
 * 对账 Task<br>
 * 
 * @author limiao
 */
public class UnionPayCheckAccountTask {
	private static Logger log = LoggerFactory.getLogger(UnionPayCheckAccountTask.class);

	@Autowired
	private UnionPayCheckAccountService unionPayCheckAccountService;

	public void run() {
		log.debug("对账  Task Start");
		try {
			unionPayCheckAccountService.process();
		} catch (Exception e) {
			log.error("对账   Exception:  " + e.getMessage());
			e.printStackTrace();
		}

		log.debug("对账 Task End");
	}
}
