package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>银行卡余额管理基类接口</b><br>
 * 接口规范支持：蓝牙和音频设备。
 *
 * @author zhengqiang 2015/3/20
 *
 */
public interface BankCardBalanceService {
	

	/**
	 * 
	 * 银行卡余额查询
	 * @param param
	 * @return map{balance}
	 * @throws TranException
	 */
	public Map<String,Object> query(Map<String,Object> param) throws TranException;

}
