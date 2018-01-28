package com.tangdi.production.mpbatch.service;

import java.util.Map;

/**
 * 
 * @author limiao
 */
public interface AgentMonthShareAmtService {
	/**
	 * 分润跑批处理
	 * @throws Exception
	 */
	public void process(Map<String, Object> param) throws Exception;
	
	/***
	 * 清空分润数据（测试用）
	 * @return
	 * @throws Exception
	 */
	public int emptyMonthSharingLog(Map<String, Object> param) throws Exception;

}
