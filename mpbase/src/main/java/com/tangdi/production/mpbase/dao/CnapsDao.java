package com.tangdi.production.mpbase.dao;

import java.util.Map;

import com.tangdi.production.mpbase.bean.CnapsInf;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 联行号
 * 
 * @author limiao
 * 
 */
public interface CnapsDao extends BaseDao<CnapsInf, Exception> {
	/**
	 * 支行名称校验
	 * @param custBankInf
	 * @return
	 * @throws Exception
	 */
	public int validateBranchState(CustBankInf custBankInf) throws Exception;
	
	/***
	 * 根据联行号获取支行名称
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String getSubBranchName(CnapsInf cnapsInf) throws Exception;
	
	public int validateCnapsCode(CnapsInf cnapsInf) throws Exception;
	

}
