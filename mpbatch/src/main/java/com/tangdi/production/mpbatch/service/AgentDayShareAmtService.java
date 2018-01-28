package com.tangdi.production.mpbatch.service;

import java.util.Map;

/**
 * 
 * @author youdd
 */
public interface AgentDayShareAmtService {
	/**
	 * 分润跑批处理
	 * @throws Exception
	 */
	public void process(Map<String , Object > param) throws Exception;
	

	/***
	 * 清除当前日期的日分润记录（测试用）
	 * @return
	 * @throws Exception
	 */
	public int emptySharingLogDay(Map<String,Object> map) throws Exception;


	int emptySharingLog() throws Exception;
	
}
