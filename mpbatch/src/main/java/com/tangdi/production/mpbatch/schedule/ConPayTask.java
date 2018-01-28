package com.tangdi.production.mpbatch.schedule;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AccountMerBalanAt1Service;
import com.tangdi.production.mpbatch.service.ConPayService;
/**
 * 确认代付
 * @author youdd
 *
 */
public class ConPayTask {
	private static Logger log = LoggerFactory.getLogger(ConPayTask.class);
	@Autowired
	private ConPayService conPayService;
	
	public void run() {
		log.info("跑批 确认代付交易 开始...");
		try {
			conPayService.process();
		} catch (Exception e) {
			log.error("跑批  确认代付交易 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.info("跑批 确认代付交易 结束...");
	}
}
