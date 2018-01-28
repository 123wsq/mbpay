package com.tangdi.production.mpomng.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.report.TransTemplate;
import com.tangdi.production.mpomng.report.TransactionTemplate;
import com.tangdi.production.mpomng.report.WithdrawTemplate;
import com.tangdi.production.mprcs.bean.TranSerialRecordInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface TransactionDao extends BaseDao<TranSerialRecordInf, Exception> {
	
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
	 * 报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<TransactionTemplate> selectReport(Map<String, Object> paramMap) throws Exception;
	/**
	 * 历史交易报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<TransactionTemplate> selectHistoryReport(Map<String, Object> paramMap) throws Exception;
	/**
	 * 合作机构交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> transactionCount(Map<String, Object> paramMap);
	/**
	 * 合作机构历史交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> historyTransactionCount(Map<String, Object> paramMap);
	
	/**
	 * 下载交易报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<TransTemplate> selectTransReport(Map<String, Object> paramMap) throws Exception;

	/**
	 * 报表下载交易明细统计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> TransCount(Map<String, Object> paramMap);

	/**
	 * 查询报表下载明细交易
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int gettransCount(TranSerialRecordInf entity_tmp);

	/**
	 * 查询报表下载交易明细
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<TranSerialRecordInf> gettransListPage(TranSerialRecordInf entity);
	
	/**
	 * 提现报表明细
	 * @param entity
	 * @return
	 * @throws Exception
	 * */
	public List<TranSerialRecordInf> getWithdrawListPage(TranSerialRecordInf entity);
	
	/**
	 * 查询提现总数
	 * @param entity
	 * @return
	 * @throws Exception
	 * */
	public Integer getWithdrawCount(TranSerialRecordInf entity_tmp);
	
	/**
	 * 提现总数统计
	 * @param entity
	 * @return
	 * @throws Exception
	 * */
	public Map<String, Object> withdrawCount(Map<String, Object> paramMap);

	/**
	 * 下载提现报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * */
	public List<WithdrawTemplate> selectWithdrawReport(
			Map<String, Object> con);


}
