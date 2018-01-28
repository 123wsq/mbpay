package com.tangdi.production.mprcs.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class BankCardBlacklistInf extends BaseBean {  
   
    /**
	 * 银行卡号
	 */
    private String cardno;   
      
    /**
	 * 卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡
	 */
    private String cardType;   
      
    /**
	 * 发卡行代码
	 */
    private String issno;   
      
    /**
	 * 发卡行名称
	 */
    private String issnam;   
      
    /**
	 * 持卡人身份证
	 */
    private String idcardno;   
      
    /**
	 * 持卡人名称
	 */
    private String idcardName;   
      
    /**
	 * 添加时间
	 */
    private String createDate;   
      
    /**
	 * 添加来源说明，比如系统导入、交易返回异常卡
	 */
    private String source;   
      
    /**
	 * 描述信息
	 */
    private String createDesc;   
      
    /**
	 * 备用字段1
	 */
    private String filed1;   
      
    /**
	 * 备用字段2
	 */
    private String filed2;   
      
 
    public BankCardBlacklistInf() {
		super();
	}

	public BankCardBlacklistInf(String cardno) {
		super();
		this.cardno = cardno;
	}

	public String getCardno() {  
        return cardno;  
    }  
      
    public void setCardno(String cardno) {  
        this.cardno = cardno;  
    } 
    public String getCardType() {  
        return cardType;  
    }  
      
    public void setCardType(String cardType) {  
        this.cardType = cardType;  
    } 
    public String getIssno() {  
        return issno;  
    }  
      
    public void setIssno(String issno) {  
        this.issno = issno;  
    } 
    public String getIssnam() {  
        return issnam;  
    }  
      
    public void setIssnam(String issnam) {  
        this.issnam = issnam;  
    } 
    public String getIdcardno() {  
        return idcardno;  
    }  
      
    public void setIdcardno(String idcardno) {  
        this.idcardno = idcardno;  
    } 
    public String getIdcardName() {  
        return idcardName;  
    }  
      
    public void setIdcardName(String idcardName) {  
        this.idcardName = idcardName;  
    } 
    public String getCreateDate() {  
        return createDate;  
    }  
      
    public void setCreateDate(String createDate) {  
        this.createDate = createDate;  
    } 
    public String getSource() {  
        return source;  
    }  
      
    public void setSource(String source) {  
        this.source = source;  
    } 
    public String getCreateDesc() {  
        return createDesc;  
    }  
      
    public void setCreateDesc(String createDesc) {  
        this.createDesc = createDesc;  
    } 
    public String getFiled1() {  
        return filed1;  
    }  
      
    public void setFiled1(String filed1) {  
        this.filed1 = filed1;  
    } 
    public String getFiled2() {  
        return filed2;  
    }  
      
    public void setFiled2(String filed2) {  
        this.filed2 = filed2;  
    } 
 }