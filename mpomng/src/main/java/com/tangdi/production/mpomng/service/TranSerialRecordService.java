package com.tangdi.production.mpomng.service;

import java.util.List;
import java.util.Map;

/**
 * 交易记录查询
 * @author zhengqiang
 *
 */
public interface TranSerialRecordService {
	
	/**
	 * 按照日期查询交易记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryTranCountByDate(Map<String,Object> paramMap) throws Exception;

}
