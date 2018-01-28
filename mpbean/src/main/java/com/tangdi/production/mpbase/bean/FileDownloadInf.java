package com.tangdi.production.mpbase.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 文件下载实体对象（业务报表）
 * @author zhengqiang
 * @version 1.0
 *
 */
public class FileDownloadInf extends BaseBean {

	private static final long serialVersionUID = 1L;
	/**
	 * 序号
	 */
	private String dId;
	/**
	 * 文件名
	 */
	private String dName;
	/**
	 * 物理名称
	 */
	private String dFileName;
	/**
	 *  文件路径
	 */
	private String dPath;
	/**
	 * 文件大小
	 */
	private String dSize;
	/**
	 * 文件类型
	 */
	private String dType;
	/**
	 * 文件生成时长
	 */
	private String dTime;
	/**
	 * 文件生成开始时间
	 */
	private String dSTime;
	/**
	 * 文件生成结束时间
	 */
	private String dETime;
	/**
	 * 状态（1 制作中 2失败 0成功)
	 */
	private String status;
	/**
	 *  0= 业务数据表，1=交易数据表
	 */
	private String RPTType; 
	/**
	 *  机构号
	 */
	private String orgNo;
	/**
	 * 用户编号
	 */
    private String uid; 

    /**
     * 查询使用字段 开始时间（特殊字段与数据库无关）
     */
    private String startDate ;  
    /**
     * 查询使用字段 结束时间（特殊字段与数据库无关）
     */
    private String endDate;     
    
    
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getdId() {
		return dId;
	}

	public void setdId(String dId) {
		this.dId = dId;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getdFileName() {
		return dFileName;
	}

	public void setdFileName(String dFileName) {
		this.dFileName = dFileName;
	}

	public String getdPath() {
		return dPath;
	}

	public void setdPath(String dPath) {
		this.dPath = dPath;
	}

	public String getdSize() {
		return dSize;
	}

	public void setdSize(String dSize) {
		this.dSize = dSize;
	}

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public String getdTime() {
		return dTime;
	}

	public void setdTime(String dTime) {
		this.dTime = dTime;
	}

	public String getdSTime() {
		return dSTime;
	}

	public void setdSTime(String dSTime) {
		this.dSTime = dSTime;
	}

	public String getdETime() {
		return dETime;
	}

	public void setdETime(String dETime) {
		this.dETime = dETime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRPTType() {
		return RPTType;
	}

	public void setRPTType(String rPTType) {
		RPTType = rPTType;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public FileDownloadInf(String dId) {
		this.dId = dId;
	}
	public FileDownloadInf() {
	}

}
