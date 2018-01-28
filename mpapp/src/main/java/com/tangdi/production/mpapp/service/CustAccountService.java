package com.tangdi.production.mpapp.service;




import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * 账户查询业务层接口
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustAccountService{
	/**
	 * 修改账户
	 * @param param custId
	 * @return
	 * @throws TranException
	 */
	public int modifyCustAccount(Map<String , Object> param) throws TranException;
	/**
	 * 并发修改账户
	 * @param param custId
	 * @return
	 * @throws TranException
	 */
	public int modifyCustAccountSync(Map<String , Object> param) throws TranException;
	/**
	 * 添加账户信息
	 * @param param 
	 * @return
	 * @throws TranException
	 */
	public int addCustAccount(Map<String , Object> param) throws TranException;
	/**
	 * 查询账户信息详情
	 * @param param
	 * @return map 详情
	 * @throws TranException
	 */
	public Map<String, Object> queryCustAccount(Map<String , Object> param) throws TranException;
	/**
	 * 查询账户信息详情
	 * @param param
	 * @return map 详情
	 * @throws TranException
	 */
	public Map<String, Object> queryCustAccountNew(Map<String , Object> param) throws TranException;
	/**
	 * 加锁查询账户信息详情
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> queryCustAccountLock(Map<String , Object> param) throws TranException;

	
	/**
	 * 收款成功时,修改账户余额。
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int modifyCustAccountBalanceByPay(Map<String, Object> param)throws TranException;
	
	/**
	 * 提现成功时,修改账户余额。
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int modifyCustAccountBalanceByCash(Map<String, Object> param)throws TranException;
	
	/**
	 * 订单撤销成功时,扣除账户余额。
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int deductCustAccountBalanceByPay(Map<String, Object> param)throws TranException;
	
}
