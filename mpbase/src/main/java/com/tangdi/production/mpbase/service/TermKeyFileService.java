package com.tangdi.production.mpbase.service;

import java.util.HashMap;

/**
 * 生成终端密钥文件接口
 * @author zhengqiang 2015/04/17
 *
 */
public interface TermKeyFileService {
	
	/**
	 * 生产密钥文件。
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int create(HashMap<String, Object> con) throws Exception;

}
