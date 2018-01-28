package com.tangdi.production.mprcs.service;



import com.tangdi.production.mprcs.bean.DefaultLimitInf;


/**
 * 默认限额
 * @author zhengqiang
 * @version 1.0
 *
 */
public interface DefaultLimitService {
	
	/**
	 * 更新限额信息
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int modifyEntity(DefaultLimitInf entity) throws Exception;
	/**
	 * 查询限额信息
	 * @return DefaultLimitInf
	 * @throws Exception
	 */
	public DefaultLimitInf queryLimit() throws Exception;

}
