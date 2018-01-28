package com.tangdi.production.mprcs.dao;

import com.tangdi.production.mprcs.bean.DefaultLimitInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author zhengqiang
* @version 1.0
*/
public interface DefaultLimitDao extends BaseDao<DefaultLimitInf,Exception>{
	
	public DefaultLimitInf selectDefaultLimit() throws Exception;

}
