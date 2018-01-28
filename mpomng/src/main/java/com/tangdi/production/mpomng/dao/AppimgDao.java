package com.tangdi.production.mpomng.dao;

import java.util.Map;

import com.tangdi.production.mpomng.bean.AppimgInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author limiao
 * @version 1.0
 */
public interface AppimgDao extends BaseDao<AppimgInf, Exception> {
	
	public int modifyAppImgStatus(Map<String,Object> map) throws Exception;

}
