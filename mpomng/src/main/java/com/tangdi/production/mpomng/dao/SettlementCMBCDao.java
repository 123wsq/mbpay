package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.SettleCountInf_CMBC;
import com.tangdi.production.mpomng.report.SettlementTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface SettlementCMBCDao extends BaseDao<Object, Exception>{

	/**
	 * 订单报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<SettlementTemplate> selectReport(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 更新订单状态为清算中
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int modifyOrderStatus(Map<String, Object> paramMap) throws Exception;
	/**
	 * 更新订单状态为已清算
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int modifyByCasOrdNo(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 更新导出文件中的订单状态为已清算
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int modifyBySettle(Map<String, Object> paramMap) throws Exception;
	/**
	 * 统计商户提现订单
	 * @return
	 * @throws Exception
	 */
	public List<SettleCountInf_CMBC> selectSettleCount(Map<String, Object> paramMap)  throws Exception;
	/**
	 * 更新状态为清算中
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusToProcessing(Map<String, Object> paramMap)  throws Exception;
	
	/**
	 * 更新状态为清算中
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusToProcessingByOrdNo(Map<String, Object> paramMap)  throws Exception;
	
	public List<SettleCountInf_CMBC> selectT0T1Count(Map<String, Object> paramMap)  throws Exception;
	
	/***
	 * 筛选提现订单
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<SettleCountInf_CMBC> selectCasPrd(Map<String, Object> paramMap)  throws Exception;
}
