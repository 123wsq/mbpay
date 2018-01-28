package com.tangdi.production.mpamng.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.service.BaseReportService;

/**
 * @author limiao
 * @version 1.0
 */
public interface ProfitSharingService  extends BaseReportService{

	public abstract List<Map<String, Object>> queryProfitSharingPage(Map<String, Object> map) throws Exception;

	public abstract Integer queryProfitSharingPageCount(Map<String, Object> map) throws Exception;

	public abstract List<Map<String, Object>> queryProfitSharing(Map<String, Object> map) throws Exception;

	public abstract Integer queryProfitSharingCount(Map<String, Object> map) throws Exception;

	public abstract Map<String, Object> queryProfitSharingPageAMT(Map<String, Object> map)throws Exception;
	
	public abstract Map<String, Object> queryProfitSharingAMT(Map<String, Object> map) throws Exception;
	
	public abstract List<Map<String, Object>> queryAgentProfitLog(Map<String, Object> map) throws Exception;
	
	public abstract List<Map<String, Object>> queryProfitDaySharing(Map<String, Object> map) throws Exception;
	
	public abstract int getProfitDaySharingCount(Map<String, Object> param) throws Exception;
	
	public abstract List<Map<String, Object>> queryProfitMonthSharing(Map<String, Object> map) throws Exception;
	
	public abstract int getProfitMonthSharingCount(Map<String, Object> param) throws Exception;
	
	public abstract int getCount(Map<String, Object> param) throws Exception;
	
	public abstract List<Map<String, Object>> queryProfitSharingDetail(Map<String, Object> map) throws Exception;
	public abstract Integer queryProfitSharingDetailCount(Map<String, Object> map) throws Exception;
	public abstract List<Map<String, Object>> queryProfitDaySharingDetail(Map<String, Object> map) throws Exception;
	public abstract Integer queryProfitDaySharingDetailCount(Map<String, Object> map) throws Exception;	
	public abstract int getProfitDaySharingDetailCount(Map<String, Object> param) throws Exception;
	public abstract Map<String, Object> queryProfitSharingDetailAMT(Map<String, Object> map) throws Exception;
}
