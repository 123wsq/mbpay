package com.tangdi.production.mpbase.service;

import com.tangdi.production.mpbase.bean.CnapsInf;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 联行号
 * 
 * @author limiao
 *
 */
public interface CnapsService extends BaseService<CnapsInf, Exception> {
	
	/**
	 * 支行名称校验
	 * @param custBankInf
	 * @return
	 * @throws Exception
	 */
	public int validateBranchState(CustBankInf custBankInf) throws Exception;
	/**
	 * 校验联行号是否存在
	 * @param custBankInf
	 * @return
	 * @throws Exception
	 */
	public int validateCnapsCode(CnapsInf cnapsInf) throws Exception;
	

}
