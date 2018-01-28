package com.tangdi.production.mpomng.dao;


import java.util.List;
import java.util.Map;


/**
 * 交易记录统计
 * @author huchunyuan
 *
 */
public interface TranSerialRecordDao{
	
	/**
	 * 根据日期统计商户注册量
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTranCountByDate(Map<String,Object> paramMap) throws Exception;
	
}

