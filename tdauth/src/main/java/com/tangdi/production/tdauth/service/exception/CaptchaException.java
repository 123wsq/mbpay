package com.tangdi.production.tdauth.service.exception;

/**
 * 验证码异常
 * @author zhengqiang
 *
 */
public class CaptchaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 验证码异常有参构造函数
	 * @param msg 抛出的异常信息
	 */
	public CaptchaException(String msg){
		super(msg);
	}

}
