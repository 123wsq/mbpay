package com.tangdi.production.mpbatch.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.GenOrgSettleReportService;
import com.tangdi.production.mpbatch.service.ZLPayCheckAccountService;

/**
 * 生成机构对账文件<br>
 * 
 * @author chenlibo
 */
public class GenOrgSettleReportTask {
	private static Logger log = LoggerFactory.getLogger(GenOrgSettleReportTask.class);

	@Autowired
	private GenOrgSettleReportService genOrgSettleReportService;

	public void run() {
		log.debug("生成机构对账文件  Task Start");
		try {
			genOrgSettleReportService.process();
		} catch (Exception e) {
			log.error("生成机构对账文件   Exception:  " + e.getMessage());
			e.printStackTrace();
		}

		log.debug("生成机构对账文件 Task End");
	}
}
