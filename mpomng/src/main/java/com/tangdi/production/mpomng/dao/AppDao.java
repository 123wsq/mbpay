package com.tangdi.production.mpomng.dao;

import com.tangdi.production.mpomng.bean.AppInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author limiao
 * @version 1.0
 */
public interface AppDao extends BaseDao<AppInf, Exception> {
	String selectLastAppVersion(AppInf appInf);
}
