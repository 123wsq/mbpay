package com.tangdi.production.mpomng.service;

import java.util.Map;

import com.tangdi.production.mpomng.bean.AppimgInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public interface AppimgService extends BaseService<AppimgInf, Exception> {
	
	public int modifyAppImgStatus(Map<String,Object> map) throws Exception;

}
