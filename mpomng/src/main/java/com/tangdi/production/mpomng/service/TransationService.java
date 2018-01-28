package com.tangdi.production.mpomng.service;


import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.service.BaseReportService;
import com.tangdi.production.mprcs.bean.TranSerialRecordInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface TransationService extends BaseReportService, BaseService<TranSerialRecordInf, Exception> {
	/**
	 * 合作机构交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> transactionCount(Map<String, Object> paramMap);
	
	/**
	 * 查询历史交易明细
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<TranSerialRecordInf> getHistorytranListPage(TranSerialRecordInf entity) throws Exception;

	/**
	 * 查询历史明细交易总数
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer getHistorytranCount(TranSerialRecordInf entity) throws Exception;
	
	/**
	 * 合作机构历史交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> historyTransactionCount(Map<String, Object> paramMap);

	
	/**
	 * 查询报表下载交易明细
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<TranSerialRecordInf> gettransListPage(TranSerialRecordInf entity) throws Exception;
	
	/**
	 * 查询报表下载明细交易总数
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int gettransCount(TranSerialRecordInf entity) throws Exception;
	
	/**
	 * 报表下载交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> TransCount(Map<String, Object> paramMap);
	

	/**
	 * 提现报表明细
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<TranSerialRecordInf> getWithdrawListPage(TranSerialRecordInf entity) throws Exception;
	
	/**
	 * 提现报表提现总数
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer getWithdrawCount(TranSerialRecordInf entity) throws Exception;
	
	/**
	 * 提现报表提现明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> withdrawCount(Map<String, Object> paramMap);

}
