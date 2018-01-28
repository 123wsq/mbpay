package com.tangdi.production.mpapp.dao;


import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpomng.bean.CustRatesInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 商户费率
 * @author wsq
 *
 */
public interface CustRateDao extends BaseDao<CustRatesInf, Exception>{

	/**
	 * 查看代理商的费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getAgentRate(Map<String, String> param) throws TranException;
	
	int insertCustRate(Map<String, Object> param) throws TranException;
	
}
