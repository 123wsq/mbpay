package com.tangdi.production.tdauth.service.exception;

/**
 * 用户登陆异常
 * @author zhengqiang
 *
 */
public class UserLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户登陆异常有参构造函数
	 * @param msg 抛出的异常信息
	 */
	public UserLoginException(String msg){
		super(msg);
	}
	/**
	 * 用户登陆异常有参构造函数
	 * @param msg 抛出的异常信息
	 * @param e   
	 */
	public UserLoginException(String msg,Exception e){
		super(msg,e);
	}
}
