package com.tangdi.production.mprcs.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public class CustLevelInf extends BaseBean {  
   
    /**
	 * 定义的等级编号
	 */
    private String level;   
      
    /**
	 * 等级名称
	 */
    private String levelName;   
      
    /**
	 * 等级描述
	 */
    private String levelDesc;   
      
 
    public String getLevel() {  
        return level;  
    }  
      
    public void setLevel(String level) {  
        this.level = level;  
    } 
    public String getLevelName() {  
        return levelName;  
    }  
      
    public void setLevelName(String levelName) {  
        this.levelName = levelName;  
    } 
    public String getLevelDesc() {  
        return levelDesc;  
    }  
      
    public void setLevelDesc(String levelDesc) {  
        this.levelDesc = levelDesc;  
    } 
 }