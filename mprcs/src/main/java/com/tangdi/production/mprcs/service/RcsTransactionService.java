package com.tangdi.production.mprcs.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 交易风控控制
 * 
 * @author zhengqiang 2015/08/19
 *
 */
public interface RcsTransactionService {

	/**
	 * 检查交易限额
	 * 
	 * @param paramMap
	 * <br/>
	 *            { <br/>
	 *            custId:商户编号 <br/>
	 *            merclass:商户等级 <br/>
	 *            agentId:代理商编号 <br/>
	 *            termNo:终端编号 <br/>
	 *            prdordType:业务类型 <br/>
	 *            bizType:子业务类型 <br/>
	 *            payType:支付方式 <br/>
	 *            cradNo:银行卡号 <br/>
	 *            payAmt:交易金额(分) <br/>
	 * @return 商户限额通过返回true，否则返回false，如果还有其他返回参数，约定直接存到传入的map中
	 *         （其中map参数已定义为final）
	 * @throws TranException
	 */
	public boolean checkTxnLimit(final Map<String, Object> paramMap) throws TranException;

	/**
	 * 新增交易记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public int addTxnLimit(Map<String, Object> paramMap) throws TranException;

	/**
	 * 检查商户提现限额</br> 1.check T+1</br> 2.check T+0</br>
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public boolean checkCASLimit(final Map<String, Object> paramMap) throws TranException;
	
	/**
	 * 检查代理商提现限额</br> 1.check T+1</br> 2.check T+0</br>
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public boolean checkAgeCASLimit(final Map<String, Object> paramMap) throws TranException;

	/**
	 * 查询合作机构终端交易总额
	 * 
	 * 如果有合适的终端 则返回
	 * 
	 * @param paramMap
	 *            (rateType 费率类型 1民生类 2一般类 3餐娱类 4批发类 5房产类, agentId 代理商编号)
	 * @return map(cooporgNo 合作机构编号 ,merNo 商户号 ,terNo 终端号)
	 * @throws TranException
	 */
	public Map<String, Object> checkOrgTerm(Map<String, Object> paramMap) throws TranException;

	/**
	 * 修改合作机构终端交易金额
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public int updateOrgTermTxnAmt(Map<String, Object> paramMap) throws TranException;

	/**
	 * 新增合作机构终端交易金额
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public int addOrgTermTxnAmt(Map<String, Object> paramMap) throws TranException;
	
	/**
	 * 回滚合作机构终端交易金额
	 * 
	 * @param paramMap
	 * @return
	 * @throws TranException
	 */
	public int updateOrgTermTxnAmtRoll(Map<String, Object> paramMap) throws TranException;

}
