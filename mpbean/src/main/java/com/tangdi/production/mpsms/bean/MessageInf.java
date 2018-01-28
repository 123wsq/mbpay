package com.tangdi.production.mpsms.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public class MessageInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String smsId;
	
	/**
	 *短信类型
	 */
	private String smsType;

	/**
	 * 发送手机号
	 */
	private String smsMoblie;

	/**
	 * 发送内容
	 */
	private String smsBody;

	/**
	 * 消息发送状态 0 成功 1失败
	 */
	private String smsStatus;

	/**
	 * 发送时间
	 */
	private String smsDate;

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getSmsMoblie() {
		return smsMoblie;
	}

	public void setSmsMoblie(String smsMoblie) {
		this.smsMoblie = smsMoblie;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(String smsDate) {
		this.smsDate = smsDate;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	
	
	
	
	
}