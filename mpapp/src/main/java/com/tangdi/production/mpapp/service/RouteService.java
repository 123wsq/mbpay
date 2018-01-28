/**
 * 
 */
package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 路由接口
 * 
 * @author zhengqiang 2013/3/18
 *
 */
public interface RouteService {
	/**
	 * <b>支付路由信息查询</b><br/>
	 * 1.查询合作机构<br/>
	 * 2.检查限额<br/>
	 * 3.选择合作机构路由<br/>
	 *
	 * @param param
	 *            {txncd 交易码 , agentId 代理商Id , rateType 费率类型 , rtrType 路由类型}
	 * @return {TMercId 商户号,TTermId终端号， batchNo 批次号,MACKEY ,PINKEY ,rtrsvr
	 *         路由服务名,rtrcod 路由交易码,txncd 交易码}
	 * @throws RouteStopException
	 * @throws NotFoundBankRtrException
	 */
	public Map<String, Object> route(Map<String, Object> param) throws TranException;

}
