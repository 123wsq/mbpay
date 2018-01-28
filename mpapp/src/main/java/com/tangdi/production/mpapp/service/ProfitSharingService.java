package com.tangdi.production.mpapp.service;

import java.util.Map;
/**
 * 分润计算业务接口
 * @author limiao
 *
 */
public interface ProfitSharingService {
	/**
	 * 
	 * @param paramMap
	 *            (agentId,payNo,rate,termRate,payAmt,payFee)
	 *            代理商Id,支付订单号,费率类型,终端费率,金额,手续费
	 */
	public void profitSharing(Map<String, Object> argMap);
	
	/**
	 * 
	 * @param paramMap
	 *            (agentId,payNo,rate,termRate,payAmt,payFee)
	 *            代理商Id,支付订单号
	 */
	public void profitSharingCancel(Map<String, Object> argMap);
	

}
