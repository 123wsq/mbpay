package com.tangdi.production.mpcoop.dao;

import java.util.List;

import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author shanbeiyi
 * @version 1.0
 */
public interface CooporgMerDao extends BaseDao<CooporgMerInf, Exception> {

	int identifyEntity(CooporgMerInf entity);

	public abstract int countCooporgList(CooporgMerInf entity) throws Exception;

	public abstract List<CooporgMerInf> selectCooporgList(CooporgMerInf entity) throws Exception;

	int updatesigEntity(CooporgMerInf entity);
	
	public int validateMerNo(CooporgMerInf cooporgMerInf) throws Exception;

	public CooporgMerInf selectProvinceID(String pId);
	public CooporgMerInf selectCooporgNo(String cooporgNo);
	public CooporgMerInf selectRateType(String rateType);
	public String getFjpath(String uploadFileId);

}
