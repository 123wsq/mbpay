package com.tangdi.production.mpomng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.tangdi.production.mpbase.service.BaseReportService;
import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface PrdInfService extends BaseReportService , BaseService<PrdInf, Exception> {
	public List<PrdInf> getCountList(PrdInf entity) throws Exception;

	public int getRechargeCount(Map<String, Object> paramMap);

	public List<Map<String, Object>> getRechargeListPage(
			Map<String, Object> paramMap);

	public Map<String, Object> rechargeCount(Map<String, Object> paramMap);

	public Map<String, Object> rechargeDetails(Map<String, Object> paramMap);

	public int getAgentPrdCount(Map<String, Object> paramMap);

	public List<Map<String, Object>> getAgentPrdList(
			Map<String, Object> paramMap);

	public Map<String, Object> prmPrdCount(Map<String, Object> paramMap);

	public Map<String, Object> prmPrdDetails(Map<String, Object> paramMap);
	
	/**
	 * 商品订单报表
	 */
	public int report(HashMap<String, Object> con, String uid) throws Exception;
	
	public Map<String, Object> prdDetails(Map<String, Object> paramMap) throws Exception;
	
	public  int modifyAuditStatus(PayInf payInf)throws Exception;


}
