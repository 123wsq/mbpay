package com.tangdi.production.mpbase.bean;

import java.io.Serializable;

/**
 * 文件上传Bean, 对应表明ATTACHMENT
 * @author zhengqiang
 */
public class FileInf implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String id;               //主键ID 
    private String moduleName;       //模块名称（弃用）
    private String tableName;        //表名（弃用）
    private String pkId;             //表名中对应的记录ID（弃用）
    private String lx;               //类型 1 图片 2 文件
    private String orderNum;         //排序号
    private String fjName;           //附件名称
    private String fjPath;           //附件路径
    private String fjo;              //创建人
    private String fjt;              //创建时间
    private String sfsx;            //是否生效 0生效 1失效
    private long dx ;               //附件大小
    private String dpi;             //分辨率
    
    
    public String getDpi() {
		return dpi;
	}
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	public FileInf(String tableName, String pkId, String lx, String orderNum) {
		this.tableName = tableName;
		this.pkId = pkId;
		this.lx = lx;
		this.orderNum = orderNum;
	}
	public String getSfsx() {
		return sfsx;
	}
	public void setSfsx(String sfsx) {
		this.sfsx = sfsx;
	}
	public FileInf(){}
	public FileInf(String id){ this.id = id;}
    public FileInf(String id,String moduleName,String tableName,String pkId,
    		String lx,String orderNum,String fjName,String fjPath,String fjo,String fjt){
    	this.id 		= id;
    	this.moduleName = moduleName;
    	this.tableName 	= tableName;
    	this.pkId 		= pkId;
    	this.lx 		= lx;
    	this.orderNum 	= orderNum;
    	this.fjName 	= fjName;
    	this.fjPath 	= fjPath;
    	this.fjo 		= fjo;
    	this.fjt 		= fjt;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getFjName() {
		return fjName;
	}
	public void setFjName(String fjName) {
		this.fjName = fjName;
	}
	public String getFjPath() {
		return fjPath;
	}
	public void setFjPath(String fjPath) {
		this.fjPath = fjPath;
	}
	public String getFjo() {
		return fjo;
	}
	public void setFjo(String fjo) {
		this.fjo = fjo;
	}
	public String getFjt() {
		return fjt;
	}
	public void setFjt(String fjt) {
		this.fjt = fjt;
	}
	public long getDx() {
		return dx;
	}
	public void setDx(long dx) {
		this.dx = dx;
	}
	@Override
	public String toString() {
		return "Attachment [id=" + id + ", moduleName=" + moduleName
				+ ", tableName=" + tableName + ", pkId=" + pkId + ", lx=" + lx
				+ ", orderNum=" + orderNum + ", fjName=" + fjName + ", fjPath="
				+ fjPath + ", fjo=" + fjo + ", fjt=" + fjt + ", sfsx=" + sfsx
				+ ", dx=" + dx + ", dpi=" + dpi + "]";
	}
	
	
}
