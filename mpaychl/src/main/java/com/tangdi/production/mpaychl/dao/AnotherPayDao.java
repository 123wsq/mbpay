package com.tangdi.production.mpaychl.dao;

import java.util.Map;

import com.tangdi.production.tdbase.dao.BaseDao;

public interface AnotherPayDao extends BaseDao<Object, Exception> {
	/**插入代付交易流水表信息*/
	public int insertAnotherPay(Map<String, Object> param) throws Exception;
	
	/**更新代付交易流水表信息*/
	public int updateAnotherPay(Map<String, Object> param) throws Exception;
	
	/**更新商品订单表表信息*/
	public int updatePrdInf(Map<String, Object> param) throws Exception;
	
	/**查询地域码*/
	public String pmctycodCitId(String address) throws Exception;
}
