package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>终端[蓝牙刷卡器或音频刷卡器]服务基础类接口</b></br>
 * 接口规范支持：刷卡器加解密处理</br></br>
 * 
 * @author zhengqiang 2015/3/18
 *
 */
public interface TermService {
	
	/**
	 * 转加密PIN方法
	 * @param termNo 终端号
	 * @param pinBlock pin密文
	 * @param track2   2磁道（可选）
	 * @return 转加密后的密文PIN
	 * @throws TranException
	 */
	public String convertPIN(Map<String,Object> map) throws TranException;

	/**
	 * 磁道解密方法
	 * @param termNo 终端号
	 * @param tracKInf 磁道密文信息
	 * @return [0] 2磁道信息 [1] 3磁道信息
	 * @throws TranException
	 */
	public String[] decryptTrack(Map<String,Object> map) throws TranException;
	
	/**
	 * 获取终端主密钥
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> getLmkey(Map<String,Object> map) throws TranException;
	
	/**
	 * 获取工作密钥(PINKEY 和 MAKKEY)
	 * @param map
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> getKey(Map<String,Object> map)  throws TranException;
	
	/**
	 * 转PIN方法
	 * @param termNo 终端号
	 * @param pinBlock pin密文
	 * @param track2   2磁道（可选）
	 * @return 转加密后的密文PIN
	 * @throws TranException
	 */
	public String decryptPIN(Map<String,Object> map)  throws TranException;
}
