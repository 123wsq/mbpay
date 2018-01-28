package com.tangdi.production.mprcs.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mprcs.bean.BankCardBlacklistInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author zhengqiang
* @version 1.0
*/
public interface BankCardBlacklistDao extends BaseDao<BankCardBlacklistInf,Exception>{
	
	public Integer getCount(Map<String, Object> paramMap) throws Exception;

	public List<BankCardBlacklistInf> getListPage(Map<String, Object> paramMap)  throws Exception;

}
