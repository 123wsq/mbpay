package com.tangdi.production.mpapp.dao;

import java.util.Map;

/**
 * 电子签名DAO
 * @author zhengqiang
 *
 */
public interface ESignDao {
	
	/**
	 * 保存电子签名
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertESign(Map<String,Object> param) throws Exception;
	
	/**
	 * 查询电子签名ID
	 * @param prdno
	 * @return
	 * @throws Exception
	 */
	public String selectESign(String prdno) throws Exception;
	
	

}
