package com.tangdi.production.mpbase.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tangdi.production.mpbase.util.MSGCODE;

/**
 * 交易异常信息处理类
 * @author zhengqiang
 *
 */
public class TranException extends Exception {
	private static Logger log = LoggerFactory.getLogger(TranException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -539836596259427728L;
	
	private String code = null;
	
	private Throwable cause;
	
	/**
	 * 错误描述，不用做客户端展示
	 */
	private String desc = "";
	private String[] args = null;

	public TranException(){
		super();
	}
	/**
	 * 通过有参构造函数，自定义错误消息。
	 * @param msgcod 错误代码
	 */
	public TranException(String msgcod){
		super(msgcod);
		this.code = msgcod;
		this.println();
	}

	/**
	 * 
	 * @param msgcod 错误代码
	 * @param desc 详细错误信息描述
	 * @param args 动态参数
	 */
	public TranException(String msgcod,String desc,String... args){
		super(msgcod);
		this.code = msgcod;
		this.desc = desc;
		this.args = args;
		this.println();
	}

	/**
	 * 
	 * @param msgcod 错误代码：6位
	 * @param desc 详细错误信息描述
	 * @param cause Throwable
	 * @param args 动态参数
	 */
	public TranException(String msgcod,String desc,Throwable cause,String... args){
		super(msgcod,cause);

		this.desc = desc;
		this.args = args;
		this.code = msgcod;
		this.cause = cause;

		this.println();
	}
	public TranException(String msgcod,Throwable cause,String... args){
		super(msgcod,cause);
		this.args = args;
		this.code = msgcod;
		this.cause = cause;

		this.println();
	}
	public void println(){
		
		log.error("错误代码:{} {}",this.getCode(),getLogMsg());
		if(desc != null && !desc.equals("")){
			log.error("错误描述:{}",desc);
		}
		if(this.cause != null){
			log.error(cause.getMessage(),cause);
		}
	}
	public TranException(Throwable cause) {
        super(cause);
    }
	/**
	 * 获取异常代码
	 * @return
	 */
	public String getCode(){
		return this.code;
	}
	/**
	 * APP日志
	 * @return
	 */
	public String getMsg(){
		String msg = MSGCODE.GET(this.code,"1");
		if(this.args != null && this.args.length > 0){
			for (String value : this.args) {
				msg = msg.replaceFirst("\\{}", value);
			}
		}
		return msg;
	}
	/**
	 * 错误详细描述信息
	 * @param desc
	 */
	public TranException detail(String desc){
		this.desc = desc;
		return this;
	}
	/**
	 * 后台日志
	 * @return
	 */
	public String getLogMsg(){
		String msg =  MSGCODE.GET(this.code);
		if(this.args != null && this.args.length > 0){
			for (String value : this.args) {
				msg = msg.replaceFirst("\\{}", value);
			}
		}
		return msg;
	}

	public TranException setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}

}
