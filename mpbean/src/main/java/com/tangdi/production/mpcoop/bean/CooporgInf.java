package com.tangdi.production.mpcoop.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */
public class CooporgInf extends BaseBean {  
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 合作机构编号
	 */
    private String cooporgNo;   
      
    /**
	 * 合作机构名称
	 */
    private String coopname;   
      
    /**
	 * 合作机构地区码
	 */
    private String areacd;   
      
    /**
	 * 联系人
	 */
    private String contact;   
      
    /**
	 * 联系电话
	 */
    private String phone;   
      
    /**
	 * 合作机构类型：0-收单，1-快捷
	 */
    private String coopType;   
      
    /**
	 * 服务开通状态
	 */
    private String svrsts;   
      
    /**
	 * 编辑时间
	 */
    private String editDate;   
      
    /**
	 * 编辑员
	 */
    private String editUserId;   
    
    /**
	 * 省份编号
	 */
    private String provinceID;
    
    /**
	 * 省份名称
	 */
    private String provinceName;
 
    public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCooporgNo() {  
        return cooporgNo;  
    }  
      
    public void setCooporgNo(String cooporgNo) {  
        this.cooporgNo = cooporgNo;  
    } 
    public String getCoopname() {  
        return coopname;  
    }  
      
    public void setCoopname(String coopname) {  
        this.coopname = coopname;  
    } 
    public String getAreacd() {  
        return areacd;  
    }  
      
    public void setAreacd(String areacd) {  
        this.areacd = areacd;  
    } 
    public String getContact() {  
        return contact;  
    }  
      
    public void setContact(String contact) {  
        this.contact = contact;  
    } 
    public String getPhone() {  
        return phone;  
    }  
      
    public void setPhone(String phone) {  
        this.phone = phone;  
    } 
    public String getCoopType() {  
        return coopType;  
    }  
      
    public void setCoopType(String coopType) {  
        this.coopType = coopType;  
    } 
    public String getSvrsts() {  
        return svrsts;  
    }  
      
    public void setSvrsts(String svrsts) {  
        this.svrsts = svrsts;  
    } 
    public String getEditDate() {  
        return editDate;  
    }  
      
    public void setEditDate(String editDate) {  
        this.editDate = editDate;  
    } 
    public String getEditUserId() {  
        return editUserId;  
    }  
      
    public void setEditUserId(String editUserId) {  
        this.editUserId = editUserId;  
    } 
 }