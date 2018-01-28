package com.tangdi.production.mpbase.exception;

/**
 * 值不能为空异常信息处理类
 * @author zhengqiang
 *
 */
public class ArgumentNullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539836596259427728L;

	public ArgumentNullException(){
		super();
	}
	/**
	 * 通过有参构造函数，自定义错误消息。
	 * @param msg
	 */
	public ArgumentNullException(String msg){
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public ArgumentNullException(String msg ,Throwable cause){
		super(msg,cause);
	}
	public ArgumentNullException(Throwable cause) {
        super(cause);
    }

}
