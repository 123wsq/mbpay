package com.tangdi.production.mpaychl.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpaychl.schedule.service.MessageSendService;

/**
 * 查询未审核订单Task
 * */
public class MessageSendTask {

	private static Logger log = LoggerFactory.getLogger(MessageSendTask.class);
	
	@Autowired
	private MessageSendService messageSendService;
	
	public void run() {
		log.debug("查询未审核订单   Task Start");
		Map<String, Object > map=new HashMap<String,Object>();
		String date = DateUtil.convertDateToString(new Date(), "yyyyMMdd");
		map.put("date", date);
		try {
			messageSendService.process(map);
		} catch (Exception e) {
			log.error("查询未审核订单 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("查询未审核订单   Task End");
	}
}
