package com.tangdi.production.mpaychl.schedule.service;

import java.util.Map;


/**
 * 节假日检查当日是否有未审核的交易，计算笔数并发送给指定的手机号
 * */
public interface MessageSendService {

	/**
	 * 短信发送
	 * @throws Exception
	 */
	public void process(Map<String, Object> map) throws Exception;
}
