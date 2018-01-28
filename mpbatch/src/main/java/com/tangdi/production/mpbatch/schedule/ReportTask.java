package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.MerClassReportService;
import com.tangdi.production.mpbatch.service.MerCountReportService;
import com.tangdi.production.mpbatch.service.TranCountReportService;

/**
 * 跑批 统计报表 Task<br>
 * <br>
 * 
 * @author 
 */
public class ReportTask {
	private static Logger log = LoggerFactory.getLogger(ReportTask.class);

	@Autowired
	private MerClassReportService merClassReportService;

	@Autowired
	private MerCountReportService merCountReportService;
	
	@Autowired
	private TranCountReportService tranCountReportService;

	public void run() {
		log.debug("跑批  T统计报表   Task Start");
		try {
			log.debug("商户等级统计报表执行中...");
			merClassReportService.process();
			log.debug("商户等级统计报表完成.");
			
			log.debug("商户注册量统计报表执行中...");
			merCountReportService.process();
			log.debug("商户注册量统计报表完成.");
			
			log.debug("订单交易统计报表执行中...");
			tranCountReportService.process();
			log.debug("订单交易统计报表完成.");
			
		} catch (Exception e) {
			log.error("跑批  统计报表 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  统计报表   Task End");

	}
}
