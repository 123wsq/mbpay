package com.tangdi.production.mpbatch.schedule;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AgentMonthShareAmtService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author zhuji
 */
public class AgentMonthShareAmtTask {
	private static Logger log = LoggerFactory.getLogger(AgentMonthShareAmtTask.class);
	
	@Autowired
	private AgentMonthShareAmtService agentShareAmtService;
	
	public void run() {
		log.debug("跑批 代理商分润  Task Start");
		Map<String, Object>param = new HashMap<String, Object>();
		try {
			/**
			 * 因为分润日每月的第一天，所以下列获取上月第一天和最后一天是没有问题，若要测试当日不是每月第一天可指定日期
			 * 1、获取计算分润月份
			 * 2、拼接月的第一天和最后一天
			 */
			String profitMonth = TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(),"-","M","1").substring(0,6);
			String lastDate  = profitMonth + "31";
			String firstDate = profitMonth + "01";
			param.put("lastDate", lastDate);//保存上月最后一天
			param.put("firstDate", firstDate);//保存上月第一天
			param.put("sharDate", profitMonth);//分润年月
			agentShareAmtService.process(param);
		} catch (Exception e) {
			log.error("跑批  代理商分润    Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  代理商分润   Task End");
	}
}
