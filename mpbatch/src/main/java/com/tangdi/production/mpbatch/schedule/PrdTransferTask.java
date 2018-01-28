package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.PrdTransferService;

/***
 * 跑批 未处理收款订单转移到未处理收款订单表
 * 
 * @author sunhaining
 *
 */
public class PrdTransferTask {
	private static Logger log = LoggerFactory.getLogger(PrdTransferTask.class);

	@Autowired
	private PrdTransferService prdTransferService;

	public void run() {
		log.debug("跑批  未处理支付订单转移   Task Start");

		try {
			prdTransferService.process();
		} catch (Exception e) {
			log.error("跑批 未处理支付订单转移 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  未处理支付订单转移   Task End");

	}
}
