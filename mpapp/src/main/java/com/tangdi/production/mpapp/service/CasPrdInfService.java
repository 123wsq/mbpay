package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;



/**
 * 交易信息查询
 * @author huchunyuan
 *
 */
public interface CasPrdInfService {
	
	/**
	 *获取交易信息
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>>  selectList(Map<String,Object> param) throws Exception ;


}
