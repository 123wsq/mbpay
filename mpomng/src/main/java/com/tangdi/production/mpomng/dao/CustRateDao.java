package com.tangdi.production.mpomng.dao;

import java.util.Map;

import com.tangdi.production.mpomng.bean.CustRatesInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 商户费率
 * @author wsq
 *
 */
public interface CustRateDao extends BaseDao<CustRatesInf, Exception>{

	/**
	 * 更新费率
	 * @param custRatesInf
	 * @return
	 */
	public int updateRate(CustRatesInf custRatesInf) throws Exception;
	
	/**
	 * 插入费率
	 * @param custRatesInf
	 * @return
	 * @throws Exception
	 */
	public int insertEntry(CustRatesInf custRatesInf) throws Exception;
	
	/**
	 * 查看费率
	 * @param param
	 * @return
	 */
	public CustRatesInf selectEntry(Map<String, Object> param) throws Exception;
	
	/**
	 * 查看上级代理商费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectCustUpRate(Map<String, Object> param) throws Exception; 
}
