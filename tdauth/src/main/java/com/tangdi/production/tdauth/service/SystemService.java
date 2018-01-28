package com.tangdi.production.tdauth.service;


import com.tangdi.production.tdauth.bean.SysInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 系统模块接口
 * @author zhengqiang
 *
 */
public interface SystemService extends BaseService<SysInf, Exception> {
	/**
	 * 根据ID列表修改系统状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int modifySysStatus(String ids, int status) throws Exception;
}
