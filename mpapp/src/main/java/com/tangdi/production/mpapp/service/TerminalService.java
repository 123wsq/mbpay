package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 终端服务接口
 * 
 * @author shanbeiyi
 *
 */
public interface TerminalService {
	/**
	 * 终端绑定
	 * 
	 * @param map
	 * @throws TerminalException
	 */
	public void binding(Map<String, Object> pmap) throws TranException;

	public List<Map<String, Object>> getlist(Map<String, Object> pmap) throws TranException;

	/**
	 * 终端检查
	 * 
	 * @param pmap
	 *            termNo 终端号 custId 商户号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> check(Map<String, Object> pmap) throws TranException;

	/**
	 * 
	 * @param param
	 *            ordAmt 订单金额 rate 费率类型
	 * @param terminalMap
	 * @return Map rate 费率 fee 手续费
	 * @throws TranException
	 */
	public Map<String, Object> calcRateFee(Map<String, Object> param, Map<String, Object> terminalMap) throws TranException;

	/**
	 * 更新终端工作密钥
	 * @param pmap
	 * @return
	 * @throws TranException
	 */
	public int modiftyKey(Map<String, Object> pmap) throws TranException;
	
	/**
	 * 获取终端费率
	 * 
	 * @param pmap
	 *            termNo 终端号
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getTermRate(Map<String, Object> pmap) throws TranException;
	
	/**
	 * 查询刷卡头的费率类型
	 * @param termNo
	 * @return
	 * @throws TranException
	 */
	public List<Map<String, Object>> queryChlRate(String termNo) throws TranException;
	
	/**
	 * 查询刷卡头蓝牙地址
	 * @param termNo
	 * @return
	 * @throws TranException
	 */
	public String queryMacAddress(String custId) throws TranException;

}
