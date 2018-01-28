package com.tangdi.production.mpomng.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.service.BaseReportService;

public interface CasPrdService extends BaseReportService {
	public int addCas(Map<String, Object> param) throws Exception;


	Integer getCasPrdCount(Map<String, Object> paramMap);

	List<Map<String, Object>> getCasPrdListPage(Map<String, Object> paramMap);

	Map<String, Object> casCount(Map<String, Object> paramMap);


	/***
	 * 设置清算中的状态为清算完成
	 * @throws Exception
	 */
	void setSettlementStatusEnd() throws Exception;
	
	/***
	 * 设置提现状态为清算完成
	 * @param casOrdNos
	 * @throws Exception
	 */
	void settleCasPrdOver(String casOrdNos) throws Exception;
	
	/***
	 * 设置提现状态为清算失败
	 * @param casOrdNos
	 * @throws Exception
	 */
	void settleCasPrdFalse(String casOrdNos) throws Exception;
	
	Map<String, Object> casDetails(Map<String, Object> paramMap);
	/**
	 * 修改提现审核状态
	 * @param paramMap
	 * @return
	 */
	public int modifyCasPrdBystauts(Map<String, Object> paramMap)  throws Exception;
	
	/**
	 * 通过指定商户查询提现订单信息数量
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int getCasPrdFromMerCount(Map<String, Object> paramMap);

	/**
	 * 通过指定商户查询提现订单信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getCasPrdListFromMerPage(Map<String, Object> paramMap);
	/**
	 * 通过指定商户查询提现订单总额
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> casPrdFromMerCount(Map<String, Object> paramMap);
	
	/**
	 * 获取T0未处理订单
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCasPrdT0(Map<String, String> param) throws Exception;
	
	public int modifyCasPrdT0(Map<String,Object> param) throws Exception;
	/**
	 * 修改提现订单状态为完成状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int modifyCasPrdStatusForDone(Map<String,Object> param) throws Exception;
	/**
	 * 商户余额自动提现生成订单
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int addAccount(Map<String, Object> param) throws Exception;


	public Map<String,Object> casPay(String casOrdNos, String cooporgNo) throws Exception;


	public Map<String, Object> limitQuery(String casOrdNos, String cooporgNo) throws Exception;
	
	/**
	 * 查询额度提现生成订单
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int lmtAddAccount(Map<String, Object> param) throws Exception;
}
