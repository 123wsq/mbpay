package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>终端签到,下载工作密钥</b>
 * 接口支持: 第三方通道[银行]签到 和 蓝牙和音频设备签到
 * 
 * @author zhengqiang 2015/3/25
 *
 */
public interface SignService {
	
	
	
	/**
	 * 
	 * 第三方通道终端签到(下载工作密钥MACKEY和PINKEY)</br>
	 * @param param
	 * @throws TranException
	 */
	public Map<String,Object> banksign(Map<String,Object> param) throws TranException;

	
	/**
	 * 
	 * 设备终端签到(下载工作密钥MACKEY和PINKEY)</br>
	 * @param param
	 * @throws TranException
	 */
	public Map<String,Object> termsign(Map<String,Object> param) throws TranException;

}
