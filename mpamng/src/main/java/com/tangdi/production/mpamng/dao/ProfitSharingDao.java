package com.tangdi.production.mpamng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpamng.report.ProfitSharingTemlpate;

/**
 * @author limiao
 * @version 1.0
 */
public interface ProfitSharingDao {

	public abstract List<Map<String, Object>> queryProfitSharingPage(Map<String, Object> map) throws Exception;

	public abstract Integer queryProfitSharingPageCount(Map<String, Object> map) throws Exception;

	public abstract Map<String, Object> queryProfitSharingPageAMT(Map<String, Object> map) throws Exception;

	
	public abstract List<Map<String, Object>> queryProfitSharing(Map<String, Object> map) throws Exception;

	public abstract Integer queryProfitSharingCount(Map<String, Object> map) throws Exception;

	public abstract Map<String, Object> queryProfitSharingAMT(Map<String, Object> map) throws Exception;
	
	public abstract List<Map<String,Object>> queryProfitDaySharing(Map<String,Object> map) throws Exception;
	
	public abstract int selectProfitDaySharingCount(Map<String, Object> param) throws Exception;
	
	public abstract List<Map<String,Object>> queryProfitMonthSharing(Map<String,Object> map) throws Exception;
	
	public abstract int selectProfitMonthSharingCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 收益查询报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<ProfitSharingTemlpate> selectProfitSharingReport(Map<String, Object> paramMap) throws Exception;
	
	public abstract List<Map<String, Object>> queryProfitSharingDetail(Map<String, Object> map) throws Exception;
	public abstract Integer queryProfitSharingDetailCount(Map<String, Object> map) throws Exception;
	public abstract List<Map<String, Object>> queryProfitDaySharingDetail(Map<String, Object> map) throws Exception;
	public abstract Integer queryProfitDaySharingDetailCount(Map<String, Object> map) throws Exception;	
	public abstract int selectProfitDaySharingDetailCount(Map<String, Object> param) throws Exception;
	public abstract Map<String, Object> queryProfitSharingDetailAMT(Map<String, Object> map) throws Exception;

}
