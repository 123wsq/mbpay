package com.tangdi.production.mpomng.dao;


import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*商户银行卡信息dao
* @author zhuji
* @version 1.0
*/
public interface CustBankInfTempDao extends BaseDao<CustBankInf,Exception>{
	public CustBankInf getEntityById(CustBankInf entity) throws Exception;

	public int updateCount(CustBankInf entity);
}
