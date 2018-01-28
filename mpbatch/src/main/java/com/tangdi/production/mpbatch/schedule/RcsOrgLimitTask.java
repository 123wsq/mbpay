package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.RcsOrgLimitService;

/**
 * 
 * 每天修改合作机构限额 的 DAY MONTH 改成当日
 * 
 * @author limiao
 *
 */
public class RcsOrgLimitTask {
	private static Logger log = LoggerFactory.getLogger(RcsOrgLimitTask.class);

	@Autowired
	private RcsOrgLimitService rcsOrgLimitService;

	public void run() {
		log.debug("跑批   修改合作机构限额 DAY MONTH  Task Start");
		try {
			rcsOrgLimitService.process();
		} catch (Exception e) {
			log.error("跑批  修改合作机构限额 DAY MONTH Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  修改合作机构限额 DAY MONTH   Task End");

	}
}
