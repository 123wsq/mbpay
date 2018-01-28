package com.tangdi.production.mprcs.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 限额记录实体bean
 * @author xiejinzhong
 * @version 1.0
 *
 */
public class CustLimitInf extends BaseBean {  
   
    /**
	 * 限额记录ID
	 */
    private String limitId;  
    
    /**
     * 限额类型 00：默认限额，10：商户等级限额，20：商户限额，30：银行卡限额
     */
    private String limitType;
      
    /**
	 * 商户级别，按商户限额时为空
	 */
    private String limitCustLevel;   
      
    /**
	 * 用户编号,根据等级限额时为空
	 */
    private String limitCustId;
    
    /**
   	 * 商户名称
   	 */
       private String custName;
      
    /**
	 * 业务类型 00：所有，01：收款,02:消费
	 */
    private String limitBusType;   
      
    /**
	 * 业务类型下的子业务类型，00：默认所有
	 */
    private String limitSubBus;   
      
    /**
	 * 支付方式：00 所有 01 支付账户   02 终端   03 快捷支付
	 */
    private String limitPayWay;   
      
    /**
	 * 单笔最小金额，单位：分
	 */
    private String limitMinAmt;   
      
    /**
	 * 单笔最大金额，单位：分
	 */
    private String limitMaxAmt;   
      
    /**
	 * 每日次数
	 */
    private String limitDayTimes;   
      
    /**
	 * 每日金额，单位：分
	 */
    private String limitDayAmt;   
      
    /**
	 * 每月交易次数
	 */
    private String limitMonthTimes;   
      
    /**
	 * 每月交易金额,单位：分
	 */
    private String limitMonthAmt;   
      
    /**
	 * 每年交易次数
	 */
    private String limitYearTimes;   
      
    /**
	 * 每年交易金额，单位：分
	 */
    private String limitYearAmt;   
      
    /**
	 * 生效开始日期
	 */
    private String limitStartDate;   
      
    /**
	 * 生效结束日期
	 */
    private String limitEndDate;   
      
    /**
	 * 描述信息
	 */
    private String limitDesc;   
      
    /**
	 * 启用状态， 1：启用 0 ： 关闭
	 */
    private String isUse;   
      
    /**
	 * 创建人
	 */
    private String createName;   
      
    /**
	 * 创建日期
	 */
    private String createDate;   
      
    /**
	 * 创建时间
	 */
    private String createDatetime;   
      
    /**
	 * 修改人
	 */
    private String updateName;   
      
    /**
	 * 修改日期
	 */
    private String updateDate;   
      
    /**
	 * 修改时间
	 */
    private String updateDatetime;   
    
    /**
	 * 代理商限额（限额时使用）
	 */
    private String limitAgentId; 
    private String agentName;
 
    public CustLimitInf(String limitType, String limitBusType,
			String limitSubBus, String limitPayWay, String isUse) {
		super();
		this.limitType = limitType;
		this.limitBusType = limitBusType;
		this.limitSubBus = limitSubBus;
		this.limitPayWay = limitPayWay;
		this.isUse = isUse;
	}

	public CustLimitInf(String limitType, String limitCustLevel,
			String limitCustId, String limitBusType, String limitSubBus,
			String limitPayWay, String isUse) {
		super();
		this.limitType = limitType;
		this.limitCustLevel = limitCustLevel;
		this.limitCustId = limitCustId;
		this.limitBusType = limitBusType;
		this.limitSubBus = limitSubBus;
		this.limitPayWay = limitPayWay;
		this.isUse = isUse;
	}

	public CustLimitInf() {
		super();
	}

	public String getLimitId() {  
        return limitId;  
    }  
      
    public void setLimitId(String limitId) {  
        this.limitId = limitId;  
    } 
    public String getLimitCustLevel() {  
        return limitCustLevel;  
    }  
      
    public void setLimitCustLevel(String limitCustLevel) {  
        this.limitCustLevel = limitCustLevel;  
    } 
    public String getLimitCustId() {  
        return limitCustId;  
    }  
      
    public void setLimitCustId(String limitCustId) {  
        this.limitCustId = limitCustId;  
    } 
    public String getLimitBusType() {  
        return limitBusType;  
    }  
      
    public void setLimitBusType(String limitBusType) {  
        this.limitBusType = limitBusType;  
    } 
    public String getLimitSubBus() {  
        return limitSubBus;  
    }  
      
    public void setLimitSubBus(String limitSubBus) {  
        this.limitSubBus = limitSubBus;  
    } 
    public String getLimitPayWay() {  
        return limitPayWay;  
    }  
      
    public void setLimitPayWay(String limitPayWay) {  
        this.limitPayWay = limitPayWay;  
    } 
    public String getLimitMinAmt() {  
        return limitMinAmt;  
    }  
      
    public void setLimitMinAmt(String limitMinAmt) {  
        this.limitMinAmt = limitMinAmt;  
    } 
    public String getLimitMaxAmt() {  
        return limitMaxAmt;  
    }  
      
    public void setLimitMaxAmt(String limitMaxAmt) {  
        this.limitMaxAmt = limitMaxAmt;  
    } 
    public String getLimitDayTimes() {  
        return limitDayTimes;  
    }  
      
    public void setLimitDayTimes(String limitDayTimes) {  
        this.limitDayTimes = limitDayTimes;  
    } 
    public String getLimitDayAmt() {  
        return limitDayAmt;  
    }  
      
    public void setLimitDayAmt(String limitDayAmt) {  
        this.limitDayAmt = limitDayAmt;  
    } 
    public String getLimitMonthTimes() {  
        return limitMonthTimes;  
    }  
      
    public void setLimitMonthTimes(String limitMonthTimes) {  
        this.limitMonthTimes = limitMonthTimes;  
    } 
    public String getLimitMonthAmt() {  
        return limitMonthAmt;  
    }  
      
    public void setLimitMonthAmt(String limitMonthAmt) {  
        this.limitMonthAmt = limitMonthAmt;  
    } 
    public String getLimitYearTimes() {  
        return limitYearTimes;  
    }  
      
    public void setLimitYearTimes(String limitYearTimes) {  
        this.limitYearTimes = limitYearTimes;  
    } 
    public String getLimitYearAmt() {  
        return limitYearAmt;  
    }  
      
    public void setLimitYearAmt(String limitYearAmt) {  
        this.limitYearAmt = limitYearAmt;  
    } 
    public String getLimitStartDate() {  
        return limitStartDate;  
    }  
      
    public void setLimitStartDate(String limitStartDate) {  
        this.limitStartDate = limitStartDate;  
    } 
    public String getLimitEndDate() {  
        return limitEndDate;  
    }  
      
    public void setLimitEndDate(String limitEndDate) {  
        this.limitEndDate = limitEndDate;  
    } 
    public String getLimitDesc() {  
        return limitDesc;  
    }  
      
    public void setLimitDesc(String limitDesc) {  
        this.limitDesc = limitDesc;  
    } 
    public String getIsUse() {  
        return isUse;  
    }  
      
    public void setIsUse(String isUse) {  
        this.isUse = isUse;  
    } 
    public String getCreateName() {  
        return createName;  
    }  
      
    public void setCreateName(String createName) {  
        this.createName = createName;  
    } 
    public String getCreateDate() {  
        return createDate;  
    }  
      
    public void setCreateDate(String createDate) {  
        this.createDate = createDate;  
    } 
    public String getCreateDatetime() {  
        return createDatetime;  
    }  
      
    public void setCreateDatetime(String createDatetime) {  
        this.createDatetime = createDatetime;  
    } 
    public String getUpdateName() {  
        return updateName;  
    }  
      
    public void setUpdateName(String updateName) {  
        this.updateName = updateName;  
    } 
    public String getUpdateDate() {  
        return updateDate;  
    }  
      
    public void setUpdateDate(String updateDate) {  
        this.updateDate = updateDate;  
    } 
    public String getUpdateDatetime() {  
        return updateDatetime;  
    }  
      
    public void setUpdateDatetime(String updateDatetime) {  
        this.updateDatetime = updateDatetime;  
    }

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Override
	public String toString() {
		return "限额信息 [limitId=" + limitId + ", limitType=" + limitType
				+ ", limitCustLevel=" + limitCustLevel + ", limitCustId="
				+ limitCustId + ", limitBusType=" + limitBusType
				+ ", limitSubBus=" + limitSubBus + ", limitPayWay="
				+ limitPayWay + ", limitMinAmt=" + limitMinAmt
				+ ", limitMaxAmt=" + limitMaxAmt + ", limitDayTimes="
				+ limitDayTimes + ", limitDayAmt=" + limitDayAmt
				+ ", limitMonthTimes=" + limitMonthTimes + ", limitMonthAmt="
				+ limitMonthAmt + ", limitYearTimes=" + limitYearTimes
				+ ", limitYearAmt=" + limitYearAmt + ", limitStartDate="
				+ limitStartDate + ", limitEndDate=" + limitEndDate
				+ "]";
	}

	public String getLimitAgentId() {
		return limitAgentId;
	}

	public void setLimitAgentId(String limitAgentId) {
		this.limitAgentId = limitAgentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

 }