package com.tangdi.production.mpbase.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 消息代码维护
 * @author zhengqiang
 *
 */
public class MsgCodeInf extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4610625080397768352L;
	
	/**
	 * 消息代码
	 */
	private String msgId;
	/**
	 * 消息内容
	 */
	private String msgContent;
	/**
	 * 消息flag
	 */
	private String msgFlag;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgFlag() {
		return msgFlag;
	}
	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}
	

}
