package com.tangdi.production.mpbase.trans;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.service.BaseReportService;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;

/**
 * 报表文件、密钥文件交易
 * @author zhengqiang
 *
 */
@Component
@IProcess
public class ReportTran {
	private static Logger log = LoggerFactory.getLogger(ReportTran.class);

	/**
	 * 生成报表CSV交易流水报表
	 * @throws  
	 */
	
	@ICode(TranCode.TRAN_REPORT) @SuppressWarnings("unchecked")
	public Map<String,Object> trans_800001(Map<String,Object> map)  {
		log.info("进入文件生成交易...");
		log.info("进入主控时上下文数据为:{}", map.toString());
		HashMap<String,Object> param = null;
		String service =null;
		String uid = null;
		try {
			param   = (HashMap<String, Object>) map.get("param");
			uid     = (String) map.get("uid");
			service = (String) map.get("service");
			BaseReportService baseReportservice  = SpringContext.getBean(service,BaseReportService.class);
			baseReportservice.report(param, uid);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("文件生成交易完成...");
		return param;
	}
	
	
}
