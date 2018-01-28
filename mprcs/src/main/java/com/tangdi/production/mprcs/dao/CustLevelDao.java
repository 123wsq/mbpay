package com.tangdi.production.mprcs.dao;

import java.util.List;

import com.tangdi.production.mprcs.bean.CustLevelInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author zhengqiang
* @version 1.0
*/
public interface CustLevelDao extends BaseDao<CustLevelInf,Exception>{
	
	/**
	 * 查询所有商户等级
	 * 
	 * @return CustLevelInf的List
	 */
	List<CustLevelInf> custLevelAll();

}
