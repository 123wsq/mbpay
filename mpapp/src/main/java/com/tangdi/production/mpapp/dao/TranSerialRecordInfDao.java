package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * app版本维护获取
 * @author huchunyuan
 *
 */
public interface TranSerialRecordInfDao {
	
	/**
	 * 交易信息查询
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>>  selectList(Map<String,Object> param) throws Exception ;
	
	/**
	 * 交易信息插入
	 * @return
	 * @throws Exception
	 */
	public  int  insertEntity(Map< String, Object> param) throws Exception ;
	
}
