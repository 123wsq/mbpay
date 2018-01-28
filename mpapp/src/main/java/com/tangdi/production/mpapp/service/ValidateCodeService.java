package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 验证码接口
 * @author zhuji
 *
 */
public interface ValidateCodeService {
	
	/**
	 * 验证码插入
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int addCode(Map<String,Object> param) throws TranException;
	/**
	 * 验证码插入
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public boolean validate(Map<String,Object> param) throws TranException;
}
