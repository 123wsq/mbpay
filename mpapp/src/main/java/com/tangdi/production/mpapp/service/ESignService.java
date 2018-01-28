package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 电子签名接口
 * @author zhengqiang 2015/04/17
 *
 */
public interface ESignService {
	/**
	 * 1.存储电子签名文件。
	 * 2.保存电子签名ID保持到DB。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int save(Map<String,Object> param) throws TranException;
	
	/**
	 * 查询电子签名ID
	 * @param prdno
	 * @return
	 * @throws Exception
	 */
	public String query(String prdno) throws TranException;

}
