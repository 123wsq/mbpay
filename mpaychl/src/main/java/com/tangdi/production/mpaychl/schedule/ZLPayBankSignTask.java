package com.tangdi.production.mpaychl.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpaychl.schedule.service.ZLPayBankSignService;

/**
 * 中联商户自动签到 Task <br>
 * <br>
 * 
 * @author chenlibo
 */
public class ZLPayBankSignTask {
	private static Logger log = LoggerFactory.getLogger(ZLPayBankSignTask.class);

	@Autowired
	private ZLPayBankSignService zLPayBankSignService;

	public void bankSign() {
		log.debug("中联商户自动签到   Task start");
		try {
			zLPayBankSignService.process();
		} catch (Exception e) {
			log.error("中联商户自动签到   Exception:  " + e.getMessage());
			e.printStackTrace();
		}

		log.debug("中联商户自动签到   Task End");
	}
}
