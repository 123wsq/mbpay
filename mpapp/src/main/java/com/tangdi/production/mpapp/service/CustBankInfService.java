package com.tangdi.production.mpapp.service;




import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;



/**
 * 银行卡交易接口
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustBankInfService{
	/**
	 * 绑定银行卡
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int addCustBankInfo(Map<String, Object> param) throws TranException;
	/**
	 * 修改银行卡
	 * @param param
	 * @return
	 * @throws CustBankInfAddException
	 */
	public int modifyCustBankInfo(Map<String, Object> param) throws TranException;
	/**
	 * 查询银行卡
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getCustBankInfo(Map<String, Object> param) throws TranException;
	/**
	 * 解除绑定银行卡
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int removeCustBankInfo(Map<String, Object> param) throws TranException;
	
	/**
	 * 商户所持银行卡数量
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int getCount(Map<String, Object> param) throws TranException;
	/**
	 * 商户审核银行卡数量
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int getTempCount(Map<String, Object> param) throws TranException;
	/**
	 * 业务分发
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public void distribution(Map<String, Object> param) throws TranException;
	/**
	 * 查询银行卡列表
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public List<Map<String, Object>> getCustBankInfoList(Map<String, Object> param) throws TranException;
	/**
	 * 查询银行卡
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getCustBankTempInfo(Map<String, Object> param)
			throws TranException;
	/**
	 * 查询待审核银行卡列表
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public List<Map<String, Object>> getCustBankInfoTempList(Map<String, Object> param)
			throws TranException;
	/**
	 * 查询最新绑定银行卡拒绝原因
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public String selectErrorReason(String custId) throws TranException;
	/**
	 * 查询最新绑定银行卡状态
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public String selectCardState(String custId) throws TranException;
}
