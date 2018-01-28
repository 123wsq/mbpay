package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface ReportTranCountDao {

	public List<Map<String, Object>> selectReportTranCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectT0ReportTranCount(Map<String, Object> paramMap) throws Exception;

}
