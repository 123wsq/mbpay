package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * 交易信息查询
 * @author huchunyuan
 *
 */
public interface TranSerialRecordService {
	
	/**
	 *新增交易信息
	 * @return
	 * @throws Exception
	 */
	public  int addTranSerialRecordInf(Map<String , Object> param) throws TranException ;


}
