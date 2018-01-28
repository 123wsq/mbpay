package com.tangdi.production.mpbase.exception;

/**
 * 下拉框查询异常信息处理类
 * @author zhengqiang
 *
 */
public class SelectOptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539836596259427728L;

	public SelectOptionException(){
		super();
	}
	/**
	 * 通过有参构造函数，自定义错误消息。
	 * @param msg
	 */
	public SelectOptionException(String msg){
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public SelectOptionException(String msg ,Throwable cause){
		super(msg,cause);
	}
	public SelectOptionException(Throwable cause) {
        super(cause);
    }

}
