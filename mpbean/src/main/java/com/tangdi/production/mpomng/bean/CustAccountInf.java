package com.tangdi.production.mpomng.bean;


import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 商户账户实体
 * @author zhuji
 * @version 1.0
 *
 */
public class CustAccountInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 2627184242222073063L;

	/**
	 * 商户号
	 */
    private String custId;   
      
    /**
	 * 账户编号
	 */
    private String account;   
      
    /**
	 * 账户类型  01 余额账户
	 */
    private String acType;   
      
    /**
	 * 货币类型
	 */
    private String ccy;   
      
    /**
	 * 总余额
	 */
    private String acBal;   
      
    /**
	 * T+0
	 */
    private String acT0;   
      
    /**
	 * T+1
	 */
    private String acT1;   
      
    /**
	 * T+1未提
	 */
    private String acT1Y;   
      
    /**
	 * 冻结金额
	 */
    private String frozBalance;   
      
    /**
	 * 最后交易时间
	 */
    private String lstTxDatetime;   
      
    /**
	 * 预留字段1
	 */
    private String resField1;   
      
    /**
	 * 预留字段1
	 */
    private String resField2;   
      
    /**
	 * 预留字段1
	 */
    private String resField3;   
      
    /**
	 * 是否冻结：0正常 1冻结
	 */
    private String accountStatus;   

	/**
	 * T1未审核
	 */
    private String  acT1UNA; 
    
    /**
	 * T1已审核
	 */
    private String  acT1AP; 
    
    /**
   	 * T1审核未通过
   	 */
    private String  acT1AUNP; 
    
    /**
   	 * 更新版本
   	 */
    private int version;
    
    public CustAccountInf() {
		super();
	}  
 
    public CustAccountInf(String custId) {
		super();
		this.custId = custId;
	}

	public String getCustId() {  
        return custId;  
    }  
      
    public void setCustId(String custId) {  
        this.custId = custId;  
    } 
    public String getAccount() {  
        return account;  
    }  
      
    public void setAccount(String account) {  
        this.account = account;  
    } 
    public String getAcType() {  
        return acType;  
    }  
      
    public void setAcType(String acType) {  
        this.acType = acType;  
    } 
    public String getCcy() {  
        return ccy;  
    }  
      
    public void setCcy(String ccy) {  
        this.ccy = ccy;  
    } 
    public String getAcBal() {  
        return acBal;  
    }  
      
    public void setAcBal(String acBal) {  
        this.acBal = acBal;  
    } 
    public String getAcT0() {  
        return acT0;  
    }  
      
    public void setAcT0(String acT0) {  
        this.acT0 = acT0;  
    } 
    public String getAcT1() {  
        return acT1;  
    }  
      
    public void setAcT1(String acT1) {  
        this.acT1 = acT1;  
    } 
    public String getAcT1Y() {  
        return acT1Y;  
    }  
      
    public void setAcT1Y(String acT1Y) {  
        this.acT1Y = acT1Y;  
    } 
    public String getFrozBalance() {  
        return frozBalance;  
    }  
      
    public void setFrozBalance(String frozBalance) {  
        this.frozBalance = frozBalance;  
    } 
    public String getLstTxDatetime() {  
        return lstTxDatetime;  
    }  
      
    public void setLstTxDatetime(String lstTxDatetime) {  
        this.lstTxDatetime = lstTxDatetime;  
    } 
    public String getResField1() {  
        return resField1;  
    }  
      
    public void setResField1(String resField1) {  
        this.resField1 = resField1;  
    } 
    public String getResField2() {  
        return resField2;  
    }  
      
    public void setResField2(String resField2) {  
        this.resField2 = resField2;  
    } 
    public String getResField3() {  
        return resField3;  
    }  
      
    public void setResField3(String resField3) {  
        this.resField3 = resField3;  
    } 
    public String getAccountStatus() {  
        return accountStatus;  
    }  
      
    public void setAccountStatus(String accountStatus) {  
        this.accountStatus = accountStatus;  
    }
    public String getAcT1UNA() {
  		return acT1UNA;
  	}

  	public void setAcT1UNA(String acT1UNA) {
  		this.acT1UNA = acT1UNA;
  	}

  	public String getAcT1AP() {
  		return acT1AP;
  	}

  	public void setAcT1AP(String acT1AP) {
  		this.acT1AP = acT1AP;
  	}

  	public String getAcT1AUNP() {
  		return acT1AUNP;
  	}

  	public void setAcT1AUNP(String acT1AUNP) {
  		this.acT1AUNP = acT1AUNP;
  	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
  	
 }