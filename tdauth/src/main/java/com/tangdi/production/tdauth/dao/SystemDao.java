package com.tangdi.production.tdauth.dao;


import java.util.Map;

import com.tangdi.production.mpbase.dao.MBaseDao;
import com.tangdi.production.tdauth.bean.SysInf;

/**
 * 系统模块DAO
 * @author zhengqiang
 *
 */
public interface SystemDao extends MBaseDao<SysInf,Exception> {
	/**
	 * 根据ID列表更新系统状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int updateSysStatus(Map<String, Object> con) throws Exception;
}
