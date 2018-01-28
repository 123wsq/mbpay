package com.tangdi.production.mpomng.service;


import java.util.Map;

import com.tangdi.production.mpbase.service.BaseReportService;

public interface SettlementService extends BaseReportService{
	
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

}
