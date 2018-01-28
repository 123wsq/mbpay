package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * 获取交易记录
 * @author huchunyuan
 *
 */
public interface CasPrdInfDao {
	
	/**
	 * 交易信息查询
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>>  selectList(Map<String,Object> param) throws Exception ;
	
	
}
