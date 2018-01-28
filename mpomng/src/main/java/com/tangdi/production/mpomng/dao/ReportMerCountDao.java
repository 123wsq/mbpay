package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.ReportMerCountInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface ReportMerCountDao extends BaseDao<ReportMerCountInf, Exception> {

	public List<ReportMerCountInf> selectReportMerCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectReportMerLiveCount(Map<String, Object> paramMap) throws Exception;

}
