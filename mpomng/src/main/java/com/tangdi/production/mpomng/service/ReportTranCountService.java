package com.tangdi.production.mpomng.service;

import java.util.Map;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface ReportTranCountService {
	/**
	 * 交易日报表（显示最近7天）柱状图（交易量） 折线图（交易笔数）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryReportTranCount() throws Exception;

	public Map<String, Object> queryT0ReportTranCount() throws Exception;

}
