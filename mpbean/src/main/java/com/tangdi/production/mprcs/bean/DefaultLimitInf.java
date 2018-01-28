package com.tangdi.production.mprcs.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class DefaultLimitInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 限额记录ID
	 */
    private String limitId;   
      
    /**
	 * 限额类型。00：默认限额
	 */
    private String limitType;   
      
    /**
	 * 商户级别
	 */
    private String limitCustLevel;   
      
    /**
	 * 终端收款 ,单笔最小金额，单位：分
	 */
    private String limitMinAmtTp;   
      
    /**
	 * 终端收款 ,单笔最大金额，单位：分
	 */
    private String limitMaxAmtTp;   
      
    /**
	 * 终端收款 ,每日次数
	 */
    private String limitDayTimesTp;   
      
    /**
	 * 终端收款 ,每日金额，单位：分
	 */
    private String limitDayAmtTp;   
      
    /**
	 * 终端收款 ,每月交易次数
	 */
    private String limitMonthTimesTp;   
      
    /**
	 * 终端收款 ,每月交易金额,单位：分
	 */
    private String limitMonthAmtTp;   
      
    /**
	 * 终端提现 ,单笔最小金额，单位：分
	 */
    private String limitMinAmtTc;   
      
    /**
	 * 终端提现 ,单笔最大金额，单位：分
	 */
    private String limitMaxAmtTc;   
      
    /**
	 * 终端提现 ,每日次数
	 */
    private String limitDayTimesTc;   
      
    /**
	 * 终端提现 ,每日金额，单位：分
	 */
    private String limitDayAmtTc;   
      
    /**
	 * 终端提现 ,每月交易次数
	 */
    private String limitMonthTimesTc;   
      
    /**
	 * 终端提现 ,每月交易金额,单位：分
	 */
    private String limitMonthAmtTc;   
      
    /**
	 * 快捷收款 ,单笔最小金额，单位：分
	 */
    private String limitMinAmtOp;   
      
    /**
	 * 快捷收款 ,单笔最大金额，单位：分
	 */
    private String limitMaxAmtOp;   
      
    /**
	 * 快捷收款 ,每日次数
	 */
    private String limitDayTimesOp;   
      
    /**
	 * 快捷收款 ,每日金额，单位：分
	 */
    private String limitDayAmtOp;   
      
    /**
	 * 快捷收款 ,每月交易次数
	 */
    private String limitMonthTimesOp;   
      
    /**
	 * 快捷收款 ,每月交易金额,单位：分
	 */
    private String limitMonthAmtOp;   
      
    /**
	 * 快捷提现 ,单笔最小金额，单位：分
	 */
    private String limitMinAmtOc;   
      
    /**
	 * 快捷提现 ,单笔最大金额，单位：分
	 */
    private String limitMaxAmtOc;   
      
    /**
	 * 快捷提现 ,每日次数
	 */
    private String limitDayTimesOc;   
      
    /**
	 * 快捷提现 ,每日金额，单位：分
	 */
    private String limitDayAmtOc;   
      
    /**
	 * 快捷提现 ,每月交易次数
	 */
    private String limitMonthTimesOc;   
      
    /**
	 * 快捷提现 ,每月交易金额,单位：分
	 */
    private String limitMonthAmtOc;   

    /*
     * 快捷默认限额
     */
    private String limitMinAmtSp;   
    private String limitMaxAmtSp;   
    private String limitDayTimesSp;   
    private String limitDayAmtSp;
    private String limitMonthTimesSp;   
    private String limitMonthAmtSp;
    private String limitMinAmtSc;   
    private String limitMaxAmtSc;   
    private String limitDayTimesSc;   
    private String limitDayAmtSc;   
    private String limitMonthTimesSc;   
    private String limitMonthAmtSc;   
    
    /**
	 * 生效开始日期
	 */
    private String limitStartDate;   
      
    /**
	 * 生效结束日期
	 */
    private String limitEndDate;   
      
    /**
	 * 启用状态， 1：开启 0 ： 关闭
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
      
 
    public String getLimitId() {  
        return limitId;  
    }  
      
    public void setLimitId(String limitId) {  
        this.limitId = limitId;  
    } 
    public String getLimitType() {  
        return limitType;  
    }  
      
    public void setLimitType(String limitType) {  
        this.limitType = limitType;  
    } 
    public String getLimitCustLevel() {  
        return limitCustLevel;  
    }  
      
    public void setLimitCustLevel(String limitCustLevel) {  
        this.limitCustLevel = limitCustLevel;  
    } 
    public String getLimitMinAmtTp() {  
        return limitMinAmtTp;  
    }  
      
    public void setLimitMinAmtTp(String limitMinAmtTp) {  
        this.limitMinAmtTp = limitMinAmtTp;  
    } 
    public String getLimitMaxAmtTp() {  
        return limitMaxAmtTp;  
    }  
      
    public void setLimitMaxAmtTp(String limitMaxAmtTp) {  
        this.limitMaxAmtTp = limitMaxAmtTp;  
    } 
    public String getLimitDayTimesTp() {  
        return limitDayTimesTp;  
    }  
      
    public void setLimitDayTimesTp(String limitDayTimesTp) {  
        this.limitDayTimesTp = limitDayTimesTp;  
    } 
    public String getLimitDayAmtTp() {  
        return limitDayAmtTp;  
    }  
      
    public void setLimitDayAmtTp(String limitDayAmtTp) {  
        this.limitDayAmtTp = limitDayAmtTp;  
    } 
    public String getLimitMonthTimesTp() {  
        return limitMonthTimesTp;  
    }  
      
    public void setLimitMonthTimesTp(String limitMonthTimesTp) {  
        this.limitMonthTimesTp = limitMonthTimesTp;  
    } 
    public String getLimitMonthAmtTp() {  
        return limitMonthAmtTp;  
    }  
      
    public void setLimitMonthAmtTp(String limitMonthAmtTp) {  
        this.limitMonthAmtTp = limitMonthAmtTp;  
    } 
    public String getLimitMinAmtTc() {  
        return limitMinAmtTc;  
    }  
      
    public void setLimitMinAmtTc(String limitMinAmtTc) {  
        this.limitMinAmtTc = limitMinAmtTc;  
    } 
    public String getLimitMaxAmtTc() {  
        return limitMaxAmtTc;  
    }  
      
    public void setLimitMaxAmtTc(String limitMaxAmtTc) {  
        this.limitMaxAmtTc = limitMaxAmtTc;  
    } 
    public String getLimitDayTimesTc() {  
        return limitDayTimesTc;  
    }  
      
    public void setLimitDayTimesTc(String limitDayTimesTc) {  
        this.limitDayTimesTc = limitDayTimesTc;  
    } 
    public String getLimitDayAmtTc() {  
        return limitDayAmtTc;  
    }  
      
    public void setLimitDayAmtTc(String limitDayAmtTc) {  
        this.limitDayAmtTc = limitDayAmtTc;  
    } 
    public String getLimitMonthTimesTc() {  
        return limitMonthTimesTc;  
    }  
      
    public void setLimitMonthTimesTc(String limitMonthTimesTc) {  
        this.limitMonthTimesTc = limitMonthTimesTc;  
    } 
    public String getLimitMonthAmtTc() {  
        return limitMonthAmtTc;  
    }  
      
    public void setLimitMonthAmtTc(String limitMonthAmtTc) {  
        this.limitMonthAmtTc = limitMonthAmtTc;  
    } 
    public String getLimitMinAmtOp() {  
        return limitMinAmtOp;  
    }  
      
    public void setLimitMinAmtOp(String limitMinAmtOp) {  
        this.limitMinAmtOp = limitMinAmtOp;  
    } 
    public String getLimitMaxAmtOp() {  
        return limitMaxAmtOp;  
    }  
      
    public void setLimitMaxAmtOp(String limitMaxAmtOp) {  
        this.limitMaxAmtOp = limitMaxAmtOp;  
    } 
    public String getLimitDayTimesOp() {  
        return limitDayTimesOp;  
    }  
      
    public void setLimitDayTimesOp(String limitDayTimesOp) {  
        this.limitDayTimesOp = limitDayTimesOp;  
    } 
    public String getLimitDayAmtOp() {  
        return limitDayAmtOp;  
    }  
      
    public void setLimitDayAmtOp(String limitDayAmtOp) {  
        this.limitDayAmtOp = limitDayAmtOp;  
    } 
    public String getLimitMonthTimesOp() {  
        return limitMonthTimesOp;  
    }  
      
    public void setLimitMonthTimesOp(String limitMonthTimesOp) {  
        this.limitMonthTimesOp = limitMonthTimesOp;  
    } 
    public String getLimitMonthAmtOp() {  
        return limitMonthAmtOp;  
    }  
      
    public void setLimitMonthAmtOp(String limitMonthAmtOp) {  
        this.limitMonthAmtOp = limitMonthAmtOp;  
    } 
    public String getLimitMinAmtOc() {  
        return limitMinAmtOc;  
    }  
      
    public void setLimitMinAmtOc(String limitMinAmtOc) {  
        this.limitMinAmtOc = limitMinAmtOc;  
    } 
    public String getLimitMaxAmtOc() {  
        return limitMaxAmtOc;  
    }  
      
    public void setLimitMaxAmtOc(String limitMaxAmtOc) {  
        this.limitMaxAmtOc = limitMaxAmtOc;  
    } 
    public String getLimitDayTimesOc() {  
        return limitDayTimesOc;  
    }  
      
    public void setLimitDayTimesOc(String limitDayTimesOc) {  
        this.limitDayTimesOc = limitDayTimesOc;  
    } 
    public String getLimitDayAmtOc() {  
        return limitDayAmtOc;  
    }  
      
    public void setLimitDayAmtOc(String limitDayAmtOc) {  
        this.limitDayAmtOc = limitDayAmtOc;  
    } 
    public String getLimitMonthTimesOc() {  
        return limitMonthTimesOc;  
    }  
      
    public void setLimitMonthTimesOc(String limitMonthTimesOc) {  
        this.limitMonthTimesOc = limitMonthTimesOc;  
    } 
    public String getLimitMonthAmtOc() {  
        return limitMonthAmtOc;  
    }  
      
    public void setLimitMonthAmtOc(String limitMonthAmtOc) {  
        this.limitMonthAmtOc = limitMonthAmtOc;  
    } 
    
    public String getLimitMinAmtSp() {  
        return limitMinAmtSp;  
    }  
      
    public void setLimitMinAmtSp(String limitMinAmtSp) {  
        this.limitMinAmtSp = limitMinAmtSp;  
    } 
    
    public String getLimitMaxAmtSp() {  
        return limitMaxAmtSp;  
    }  
      
    public void setLimitMaxAmtSp(String limitMaxAmtSp) {  
        this.limitMaxAmtSp = limitMaxAmtSp;  
    } 
    
    public String getLimitDayTimesSp() {  
        return limitDayTimesSp;  
    }  
      
    public void setLimitDayTimesSp(String limitDayTimesSp) {  
        this.limitDayTimesSp = limitDayTimesSp;  
    } 
    
    public String getLimitDayAmtSp() {  
        return limitDayAmtSp;  
    }  
      
    public void setLimitDayAmtSp(String limitDayAmtSp) {  
        this.limitDayAmtSp = limitDayAmtSp;  
    } 
    
    public String getLimitMonthTimesSp() {  
        return limitMonthTimesSp;  
    }  
      
    public void setLimitMonthTimesSp(String limitMonthTimesSp) {  
        this.limitMonthTimesSp = limitMonthTimesSp;  
    } 
    
    public String getLimitMonthAmtSp() {  
        return limitMonthAmtSp;  
    }  
      
    public void setLimitMonthAmtSp(String limitMonthAmtSp) {  
        this.limitMonthAmtSp = limitMonthAmtSp;  
    } 
    
    public String getLimitMinAmtSc() {  
        return limitMinAmtSc;  
    }  
      
    public void setLimitMinAmtSc(String limitMinAmtSc) {  
        this.limitMinAmtSc = limitMinAmtSc;  
    } 
    public String getLimitMaxAmtSc() {  
        return limitMaxAmtSc;  
    }  
      
    public void setLimitMaxAmtSc(String limitMaxAmtSc) {  
        this.limitMaxAmtSc = limitMaxAmtSc;  
    } 
    
    public String getLimitDayTimesSc() {  
        return limitDayTimesSc;  
    }  
      
    public void setLimitDayTimesSc(String limitDayTimesSc) {  
        this.limitDayTimesSc = limitDayTimesSc;  
    } 
    
    public String getLimitDayAmtSc() {  
        return limitDayAmtSc;  
    }  
      
    public void setLimitDayAmtSc(String limitDayAmtSc) {  
        this.limitDayAmtSc = limitDayAmtSc;  
    } 
    
    public String getLimitMonthTimesSc() {  
        return limitMonthTimesSc;  
    }  
      
    public void setLimitMonthTimesSc(String limitMonthTimesSc) {  
        this.limitMonthTimesSc = limitMonthTimesSc;  
    } 
    
    public String getLimitMonthAmtSc() {  
        return limitMonthAmtSc;  
    }  
      
    public void setLimitMonthAmtSc(String limitMonthAmtSc) {  
        this.limitMonthAmtSc = limitMonthAmtSc;  
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
 }