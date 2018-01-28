package com.tangdi.production.mpbatch.service;

/**
 * T+0 未处理提现订单订单转到T+1未提接口<br>
 * <br>
 * 将T0 提现订单 状态为[00-未处理] 金额 转到 T+1未提 ,修改 T0提现订单 状态为[05-已取消]
 * 
 * @author limiao
 */
public interface AccountBalanceT0Service {
	/**
	 * 资金转移T0 到 TY
	 */
	public void process() throws Exception;

}
