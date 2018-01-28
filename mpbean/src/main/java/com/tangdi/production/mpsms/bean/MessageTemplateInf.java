package com.tangdi.production.mpsms.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class MessageTemplateInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -8631652497085519018L;

	/**
	 * 短信模版类型 01注册 02 找回密码 03 消费
	 */
    private String messageCode;   
      
    /**
	 * 短信模版内容
	 */
    private String messageContent;   
      
 
    public String getMessageCode() {  
        return messageCode;  
    }  
      
    public void setMessageCode(String messageCode) {  
        this.messageCode = messageCode;  
    } 
    public String getMessageContent() {  
        return messageContent;  
    }  
      
    public void setMessageContent(String messageContent) {  
        this.messageContent = messageContent;  
    } 
 }