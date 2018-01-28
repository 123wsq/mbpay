package com.tangdi.production.mpcoop.dao;

import java.util.List;

import com.tangdi.production.mpcoop.bean.CooporgInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author shanbeiyi
* @version 1.0
*/
public interface CooporgDao extends BaseDao<CooporgInf,Exception>{

	int identifyEntity(CooporgInf entity);
	
	int selectName(CooporgInf entity);

	public List<CooporgInf> getCooporgNoList() throws Exception;

	CooporgInf selectTypeEntity(CooporgInf entity);
	
	public List<CooporgInf> getProvinceNameList() throws Exception;
	
}
