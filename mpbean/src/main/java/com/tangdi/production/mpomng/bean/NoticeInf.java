package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public class NoticeInf extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
	private String noticeId;

	/**
	 * 公告平台 1代理商 2商户(手机APP)
	 */
	private String noticePlatform;

	/**
	 * 公告标题
	 */
	private String noticeTitle;

	/**
	 * 公告内容
	 */
	private String noticeBody;

	/**
	 * 公告发布人
	 */
	private String noticeIssue;

	/**
	 * 公告发布时间
	 */
	private String noticeIssueDate;
	
	/**
	 * 附件ID
	 */
	private String attachmentId;
	
	/**
	 * 公告状态 1、紧急 2、正常
	 */
	private String noticeType;
	

	public NoticeInf() {
		super();
	}

	public NoticeInf(String noticeId) {
		super();
		this.noticeId = noticeId;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticePlatform() {
		return noticePlatform;
	}

	public void setNoticePlatform(String noticePlatform) {
		this.noticePlatform = noticePlatform;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeBody() {
		return noticeBody;
	}

	public void setNoticeBody(String noticeBody) {
		this.noticeBody = noticeBody;
	}

	public String getNoticeIssue() {
		return noticeIssue;
	}

	public void setNoticeIssue(String noticeIssue) {
		this.noticeIssue = noticeIssue;
	}

	public String getNoticeIssueDate() {
		return noticeIssueDate;
	}

	public void setNoticeIssueDate(String noticeIssueDate) {
		this.noticeIssueDate = noticeIssueDate;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getnoticeType() {
		return noticeType;
	}

	public void setnoticeType(String noticeType) {
		this.noticeType = noticeType;
	}


}