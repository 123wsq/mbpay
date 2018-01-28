package com.tangdi.production.mpbatch.schedule;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbatch.service.AgentDayShareAmtService;

/**
 * 
 * @author youdd
 */
public class AgentDayShareAmtTask {
	private static Logger log = LoggerFactory.getLogger(AgentDayShareAmtTask.class);
	
	@Autowired
	private AgentDayShareAmtService agentDayShareAmtService;
	
	public void run() {
		log.debug("跑批 代理商日分润  Task Start");
		Map<String , Object > param=new HashMap<String, Object>();
		param.put("date", "");
		try {
			agentDayShareAmtService.process(param);
		} catch (Exception e) {
			log.error("跑批  代理商日分润    Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("跑批  代理日商分润   Task End");
	}
}
