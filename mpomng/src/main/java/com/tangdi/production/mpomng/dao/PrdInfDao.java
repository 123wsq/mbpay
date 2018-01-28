package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.mpomng.report.PrdTemplate;
import com.tangdi.production.mpomng.report.RechargePrdTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface PrdInfDao extends BaseDao<PrdInf, Exception> {
	public List<PrdInf> getCountList(PrdInf entity) throws Exception;

	public List<Map<String, Object>> getRechargeListPage(
			Map<String, Object> paramMap);

	public int getRechargeCount(Map<String, Object> paramMap);

	public Map<String, Object> rechargeCount(Map<String, Object> paramMap);

	public Map<String, Object> rechargeDetails(Map<String, Object> paramMap);

	public int getAgentPrdCount(Map<String, Object> paramMap);

	public List<Map<String, Object>> getAgentPrdList(
			Map<String, Object> paramMap);

	public Map<String, Object> prmPrdCount(Map<String, Object> paramMap);

	public Map<String, Object> prmPrdDetails(Map<String, Object> paramMap);
	
	/**
	 * 商品订单报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<PrdTemplate> selectPrdReport(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 收款订单报表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<RechargePrdTemplate> selectRechargePrdReport(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 查询商品订单详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectPrdDetails(Map<String, Object> paramMap)throws Exception;
	
	public int updateStatus(PayInf payInf)throws Exception;
	/**
	 * 查询状态
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectStatus(PayInf payInf)throws Exception;
	
}
