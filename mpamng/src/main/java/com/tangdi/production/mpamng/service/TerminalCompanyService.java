package com.tangdi.production.mpamng.service;

import com.tangdi.production.mpamng.bean.TerminalCompanyInf;
import com.tangdi.production.tdbase.service.BaseService;

public interface TerminalCompanyService extends BaseService<TerminalCompanyInf, Exception> {

	/**
	 * 获取终端类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String querySelectOptionTermType() throws Exception;

	/**
	 * 获取终端厂商
	 * 
	 * @return
	 * @throws Exception
	 */
	public String querySelectOptionTermCom() throws Exception;

}
