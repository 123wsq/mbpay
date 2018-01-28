package com.tangdi.production.mpomng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class TdRateInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 6311746414717425970L;

	/**
	 * 参数代码
	 */
    private String paraCode;   
      
    /**
	 * 参数名称
	 */
    private String paraName;   
      
    /**
	 * 收费类型  01 定额收费 02 百分比收费
	 */
    private String paraType;   
      
    /**
	 * 参数值
	 */
  
    private String paraVal; 
    
    /**
	 * 封顶金额
	 */
   private String paraMaxMoney;   
    
    /**
     * 参数值 1代表百分比  2代表元
     */
   private String paraValType;

	public String getParaCode() {
		return paraCode;
	}

	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

	public String getParaVal() {
		return paraVal;
	}

	public void setParaVal(String paraVal) {
		this.paraVal = paraVal;
	}

	public String getParaMaxMoney() {
		return paraMaxMoney;
	}

	public void setParaMaxMoney(String paraMaxMoney) {
		this.paraMaxMoney = paraMaxMoney;
	}

	public String getParaValType() {
		return paraValType;
	}

	public void setParaValType(String paraValType) {
		this.paraValType = paraValType;
	}
   
 }