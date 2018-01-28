package com.tangdi.production.mpaychl.trans.terminal.service;

import java.util.Map;

import com.tangdi.production.mpaychl.base.service.TermAbstractService;
import com.tangdi.production.mpbase.exception.TranException;
/**
 * 音频终端接口规范
 * 
 * @author zhengqiang 2014/3/18
 *
 */
public abstract class AudioTerminalService extends TermAbstractService {

	/**
	 * PIN解密 (用于软加密使用)
	 * @param pinkey   PIN密钥
	 * @param pinBlock pin密文
	 * @param account  账号（可选）
	 * @return 解密后的明文PIN
	 * @throws TranException
	 */
	public abstract String decryPIN(String pinkey,String pinBlock,String account) throws TranException;
	
	/**
	 * 密钥分散
	 * @param map {sourceKey 源密钥, random 分算因子 }
	 * @return LMK加密的子密钥（SUBKEY）
	 * @throws TranException
	 */
	public abstract Map<String,Object> keyDispers(Map<String, Object> map)  throws TranException;
	
}
