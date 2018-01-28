package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpomng.report.CasPrdTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface CasPrdDao extends BaseDao<Object, Exception>{

	Integer getCasPrdCount(Map<String, Object> paramMap);

	List<Map<String, Object>> getCasPrdListPage(Map<String, Object> paramMap);

	Integer getCasPrdLimit(Map<String, Object> paramMap);

	Map<String, Object> casCount(Map<String, Object> paramMap);

	Map<String, Object> casDetails(Map<String, Object> paramMap);
	/**
	 * 修改提现审核状态
	 * @param paramMap
	 * @return
	 */
	public int modifyCasPrdBystauts(Map<String, Object> paramMap);
	
	/**
	 * 订单报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<CasPrdTemplate> selectReport(Map<String, Object> paramMap) throws Exception;
	
	public int updateCasPrdStatus() throws Exception;
	
	public int updateCasPrdStatusById(@Param("casOrdNo") String casOrdNo) throws Exception;

	
	public Map<String, Object> selectCustByCasNo(@Param(value="casOrdNo") String casOrdNo) throws Exception;
	/**
	 * 加锁查询订单信息
	 * @param casOrdNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectCustByCasNoForUpdate(@Param(value="casOrdNo") String casOrdNo) throws Exception;
	
	public List<Map<String, Object>> selectCasPrdT0(Map<String, String> param) throws Exception;
	
	public int updateCasPrdT0(Map<String,Object> param) throws Exception;
	
	public int updateCasPrdStatusForDone(Map<String, Object> param) throws Exception ;
	
	/**
	 * 根据日期 清除商户 余额 提现订单表
	 */
	public int emprtAccounts(Map<String, Object> map) throws Exception;
	
	/***
	 * 设置提现订单状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateCasPrdStatusById(Map<String, Object> param) throws Exception;
	
	/**获取代付提现订单相关信息*/
	public Map<String, Object> getCasPayInfo(String casOrdNo) throws Exception;

	/**删除确认代付重复信息*/
	public int deleteConPay(Map<String, Object> param)throws Exception;
	/**插入确认代付表信息*/
	public int insertConPay(Map<String, Object> param) throws Exception;

	/**获取确认代付表信息*/
	public List<Map<String, Object>> selectConPayInf(Map<String, Object> param) throws Exception;
	
	/**设置确认代付表状态*/
	public int updateConPayStatus(Map<String,Object> param) throws Exception;
	
	/**代付后先将订单状态置为清算中*/
	public int updateCasPrdStatusByCasPay(Map<String,Object> param) throws Exception;
	
	List<Map<String, Object>> getPayTypeList();
	
	/**获取代付通道信息*/
	public Map<String, Object> getCasPayType(String cooporgNo) throws Exception;
	
	/**查询限制额度*/
	public Map<String, Object> getLImitQuery(String cooporgNo) throws Exception;
	
}
