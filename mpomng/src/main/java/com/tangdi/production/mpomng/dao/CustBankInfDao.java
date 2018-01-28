package com.tangdi.production.mpomng.dao;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author zhuji
* @version 1.0
*/
public interface CustBankInfDao extends BaseDao<CustBankInf,Exception>{
	
	public int selectBankNum(@Param("custId") String custId) throws Exception;

}
