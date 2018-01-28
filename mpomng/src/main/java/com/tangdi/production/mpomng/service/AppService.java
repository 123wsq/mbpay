package com.tangdi.production.mpomng.service;

import com.tangdi.production.mpomng.bean.AppInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public interface AppService extends BaseService<AppInf, Exception> {
	/***
	 * 获取APP最新版本
	 * @param map
	 * @return
	 * @throws Exception
	 */
	double getLastAppVersion(AppInf appInf) throws Exception;
}
