package com.tangdi.production.mpomng.bean;


import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */
public class CustBankInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -6173204781140349141L;
	private String custName;
	private String custType;
	private String usrMobile;	
	private int count;
	/**
	 * 閾惰鍗¤褰旾D
	 */
    private String bankCardId;   
      
    /**
	 * 鍟嗘埛缂栧彿
	 */
    private String custId;   
      
    /**
	 * 鍙戝崱琛屼唬鐮�
	 */
    private String issno;   
      
    /**
	 * 鍙戝崱琛屽悕绉�
	 */
    private String issnam;   
      
    /**
	 * 寮�埛鐪佷唤ID
	 */
    private String provinceId; 
    
    /**
	 * 寮�埛鐪佷唤
	 */
    private String provinceName; 
      
    /**
	 * 寮�埛鍩庡競ID
	 */
    private String cityId;   
    
    /**
	 * 寮�埛鍩庡競
	 */
    private String cityName;
      
    /**
	 * 鑱旇鍙�
	 */
    private String cnapsCode;   
      
    /**
	 * 鍗＄ 01 鍊熻鍗�02 璐疯鍗�03 鍑嗚捶璁板崱 04 棰勪粯鍗�
	 */
    private String cardType;   
      
    /**
	 * 鍗＄墖鐘舵�锛�-榛樿浣跨敤锛�-姝ｅ父锛�1锛氫笉鍙娇鐢�
	 */
    private String cardState;   
      
    /**
	 * 褰撳墠鍗″彿锛堝綋鐢ㄦ埛鍙戣捣閾惰鍗″彿鍙樻洿鏃跺瓨鏀瑰彉鍓嶇殑鍗″彿锛�
	 */
    private String oldCardNo;   
      
    /**
	 * 閾惰鍗″彿
	 */
    private String cardNo;   
      
    /**
	 * 閾惰棰勭暀鎵嬫満鍙�
	 */
    private String mobileNo;   
      
    /**
	 *   缁戝畾鏃堕棿
	 */
    private String bindingTime;   
      
    /**
	 * 瑙ｇ粦鏃堕棿
	 */
    private String unbundlingTime;   
      
    /**
	 * 閾惰鍗℃闈㈢収
	 */
    private String bankcardFront;   
      
    /**
	 * 閾惰鍗″弽闈㈢収
	 */
    private String bankcardBack;   
    
    /**
	 * 鎵嬫寔韬唤璇佺収鐗�
	 */
	private String idcardHandheld;

	/**
	 * 韬唤璇佹闈�
	 */
	private String idcardFront;

	/**
	 * 韬唤璇佸弽闈�
	 */
	private String idcardBack;
      
    public String getIdcardHandheld() {
		return idcardHandheld;
	}

	public void setIdcardHandheld(String idcardHandheld) {
		this.idcardHandheld = idcardHandheld;
	}

	public String getIdcardFront() {
		return idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	/**
	 * 淇敼鍘熷洜鎻忚堪淇℃伅
	 */
    private String updateDesc;   
    
    /**
	 * 鏀鍚嶇О
	 */
    private String subBranch;  
    
    /**
     * 鏀鍚嶇О鐘舵�
     */
    private String bankSubBranchState;  
    
    private String bankSubBranch;
    
    /**
     * 缁撶畻瀹℃牳澶辫触鍘熷洜
     * */
    private String defeatReason;
    
    /**
     * 缁撶畻瀹℃牳澶辫触鍘熷洜璇︾粏鎻忚堪
     * */
    private String defeatReasonDes;
    
    /**
     * 璇佷欢鍙风爜
     */
    public String certificateNo;
    
    /**
     * 鍟嗘埛璁よ瘉鐘舵�
     */
    public String custStatus;
    
    public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getDefeatReason() {
		return defeatReason;
	}

	public void setDefeatReason(String defeatReason) {
		this.defeatReason = defeatReason;
	}

	public String getDefeatReasonDes() {
		return defeatReasonDes;
	}

	public void setDefeatReasonDes(String defeatReasonDes) {
		this.defeatReasonDes = defeatReasonDes;
	}

	public String getBankSubBranch() {
		return bankSubBranch;
	}

	public void setBankSubBranch(String bankSubBranch) {
		this.bankSubBranch = bankSubBranch;
	}

	private String address;
    public String getAddress() {
		String addr = "";
		if(provinceName != null && !provinceName.equals("")){
			addr = provinceName;
		}
		if(cityName != null && !cityName.equals("")){
			addr += ", "+ cityName;
		}
	
		return addr;
	}
 
    public CustBankInf(String custId, String cardType, String cardState) {
		this.custId = custId;
		this.cardType = cardType;
		this.cardState = cardState;
	}

	public CustBankInf() {
		super();
	}

	public String getBankCardId() {  
        return bankCardId;  
    }  
      
    public void setBankCardId(String bankCardId) {  
        this.bankCardId = bankCardId;  
    } 
    public String getCustId() {  
        return custId;  
    }  
      
    public void setCustId(String custId) {  
        this.custId = custId;  
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
    public String getProvinceId() {  
        return provinceId;  
    }  
      
    public void setProvinceId(String provinceId) {  
        this.provinceId = provinceId;  
    } 
    public String getCityId() {  
        return cityId;  
    }  
      
    public void setCityId(String cityId) {  
        this.cityId = cityId;  
    } 
    public String getCnapsCode() {  
        return cnapsCode;  
    }  
      
    public void setCnapsCode(String cnapsCode) {  
        this.cnapsCode = cnapsCode;  
    } 
    public String getCardType() {  
        return cardType;  
    }  
      
    public void setCardType(String cardType) {  
        this.cardType = cardType;  
    } 
    public String getCardState() {  
        return cardState;  
    }  
      
    public void setCardState(String cardState) {  
        this.cardState = cardState;  
    } 
    public String getOldCardNo() {  
        return oldCardNo;  
    }  
      
    public void setOldCardNo(String oldCardNo) {  
        this.oldCardNo = oldCardNo;  
    } 
    public String getCardNo() {  
        return cardNo;  
    }  
      
    public void setCardNo(String cardNo) {  
        this.cardNo = cardNo;  
    } 
    public String getMobileNo() {  
        return mobileNo;  
    }  
      
    public void setMobileNo(String mobileNo) {  
        this.mobileNo = mobileNo;  
    } 
    public String getBindingTime() {  
        return bindingTime;  
    }  
      
    public void setBindingTime(String bindingTime) {  
        this.bindingTime = bindingTime;  
    } 
    public String getUnbundlingTime() {  
        return unbundlingTime;  
    }  
      
    public void setUnbundlingTime(String unbundlingTime) {  
        this.unbundlingTime = unbundlingTime;  
    } 
    public String getBankcardFront() {  
        return bankcardFront;  
    }  
      
    public void setBankcardFront(String bankcardFront) {  
        this.bankcardFront = bankcardFront;  
    } 
    public String getBankcardBack() {  
        return bankcardBack;  
    }  
      
    public void setBankcardBack(String bankcardBack) {  
        this.bankcardBack = bankcardBack;  
    } 
    public String getUpdateDesc() {  
        return updateDesc;  
    }  
      
    public void setUpdateDesc(String updateDesc) {  
        this.updateDesc = updateDesc;  
    }

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getUsrMobile() {
		return usrMobile;
	}

	public void setUsrMobile(String usrMobile) {
		this.usrMobile = usrMobile;
	}

	public String getBankSubBranchState() {
		return bankSubBranchState;
	}

	public void setBankSubBranchState(String bankSubBranchState) {
		this.bankSubBranchState = bankSubBranchState;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	} 
	
	
	
 }