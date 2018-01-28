package com.tangdi.production.mpbase.exception;

/**
 * 获取UID异常信息处理类
 * @author zhengqiang
 *
 */
public class UIDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539836596259427728L;

	public UIDException(){
		super();
	}
	/**
	 * 通过有参构造函数，自定义错误消息。
	 * @param msg
	 */
	public UIDException(String msg){
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public UIDException(String msg ,Throwable cause){
		super(msg,cause);
	}
	public UIDException(Throwable cause) {
        super(cause);
    }

}
