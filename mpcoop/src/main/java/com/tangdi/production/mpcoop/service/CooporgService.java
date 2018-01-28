package com.tangdi.production.mpcoop.service;



import com.tangdi.production.mpcoop.bean.CooporgInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */
public interface CooporgService extends BaseService<CooporgInf,Exception>{

	int identifyId(CooporgInf coop);

	/**
	 * 获取合作机构编号
	 * @return
	 * @throws Exception
	 */
	public String querySelectOptionCooporgNo() throws Exception;

	CooporgInf selectTypeEntity(CooporgInf coop);

	int findName(CooporgInf entity);

	/**
	 * 获取省份编号
	 * @return
	 * @throws Exception
	 */
	public String querySelectOptionProvinceID() throws Exception;
	

}
