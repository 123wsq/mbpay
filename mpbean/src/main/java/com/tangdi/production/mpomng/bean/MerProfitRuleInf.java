package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class MerProfitRuleInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -8099852011213859660L;

	/**
	 * 分润类型 01交易分润 02管理分润
	 */
    private String profitType;   
      
    /**
	 * 商户等级
	 */
    private String custClass;   
      
    /**
	 * 分润比
	 */
    private String ratePercent;   
      
    /**
	 * 封顶
	 */
    private String rateTop;   
      
 
    public String getProfitType() {  
        return profitType;  
    }  
      
    public void setProfitType(String profitType) {  
        this.profitType = profitType;  
    } 
    public String getCustClass() {  
        return custClass;  
    }  
      
    public void setCustClass(String custClass) {  
        this.custClass = custClass;  
    } 
    public String getRatePercent() {  
        return ratePercent;  
    }  
      
    public void setRatePercent(String ratePercent) {  
        this.ratePercent = ratePercent;  
    } 
    public String getRateTop() {  
        return rateTop;  
    }  
      
    public void setRateTop(String rateTop) {  
        this.rateTop = rateTop;  
    } 
 }