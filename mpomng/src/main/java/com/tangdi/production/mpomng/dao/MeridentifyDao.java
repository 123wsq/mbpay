package com.tangdi.production.mpomng.dao;


import java.util.HashMap;
import java.util.Map;

import com.tangdi.production.mpomng.bean.MeridentifyInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*实名认证信息dao
* @author zhuji
* @version 1.0
*/
public interface MeridentifyDao extends BaseDao<MeridentifyInf,Exception>{
	/**
	 * 更新状态值
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateMeridentifyStatus(HashMap<String, Object> map) throws Exception;
	
	
	/**
	 * 新建实名认证信息
	 * @param param
	 * @return
	 */
	public int insertEntityMap(Map<String, Object> param) throws Exception;
}
