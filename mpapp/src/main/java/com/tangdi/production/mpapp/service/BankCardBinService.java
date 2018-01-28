package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 银行卡BIN
 * @author zhengqiang
 *
 */
public interface BankCardBinService {
	
	/**
	 * 银行卡BIN信息查询
	 * @param cardNo 卡号
	 * @return map
	 * @throws TranException
	 */
	public Map<String,Object> query(String cardNo) throws TranException;

}
