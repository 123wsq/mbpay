package com.tangdi.production.mpomng.dao;

import java.util.HashMap;
import java.util.List;

import com.tangdi.production.mpomng.bean.RecInf;
import com.tangdi.production.mpomng.report.RecInfoTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * @author 
 * @version 1.0
 */
public interface RecInfDao extends BaseDao<RecInf, Exception> {
	public List<RecInf> getCountList(RecInf entity) throws Exception;
	
	public List<RecInf> selectList02(RecInf entity) throws Exception;
	public List<RecInf> selectList03(RecInf entity) throws Exception;
	
	public int countEntity02(RecInf entity) throws Exception;
	public int countEntity03(RecInf entity) throws Exception;
	
	/**
	 * 对账成功下载
	 * */
	public List<RecInfoTemplate> selectListTemplate01(HashMap<String, Object> con) throws Exception;
	/**
	 * 对账失败下载
	 * */
	public List<RecInfoTemplate> selectListTemplate02(HashMap<String, Object> con) throws Exception;
	/**
	 * 对账可疑下载
	 * */
	public List<RecInfoTemplate> selectListTemplate03(HashMap<String, Object> con) throws Exception;
}
