package com.tangdi.production.mpbatch.service;

/**
 * 与中联支付对账接口<br>
 * <br>
 * 1. 检查当天是否已经 对账<br>
 * 2. 将银行对账文件 导入 MPBATCH_CHECKACCOUNT_BANK_INF , 将 疑账表中的 CHECKTYPE 为 01 的 导入<br>
 * 3. 当天交易流水 MPOMNG_PAYMENT_JOURNAL_INF 导入 MPBATCH_CHECKACCOUNT_PAY_INF , 将 疑账表中的 CHECKTYPE 为 02 的 导入<br>
 * 4. MPBATCH_CHECKACCOUNT_PAY_INF和MPBATCH_CHECKACCOUNT_BANK_INF 对比<br>
 * 	4.1 成功 ---- MPBATCH_CHECKACCOUNT_INF<br>
 * 	4.2 错账(金额不符) ---- MPBATCH_CHECKACCOUNT_ERROR_INF<br>
 * 	4.3 疑账 ---- MPBATCH_CHECKACCOUNT_DOUBT_INF <br><br>
 * [疑帐数据 在第2天 对不平之后 进入 错账表]<br>
 * 
 * @author limiao
 */
public interface ZLPayCheckAccountService {
	/**
	 * 对账处理
	 * @throws Exception
	 */
	public void process() throws Exception;

}
