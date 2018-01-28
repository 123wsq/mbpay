package com.tangdi.production.tdauth.bean;

import com.tangdi.production.mpbase.annotation.Column;
import com.tangdi.production.mpbase.annotation.PK;
import com.tangdi.production.mpbase.annotation.Table;
import com.tangdi.production.mpbase.lib.MBaseBean;

/**
 * 系统模块
 * @author zhengqiang
 *
 */
@Table(name="AUTH_SYS_INF")
public class SysInf extends MBaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统编号
	 */
	@PK @Column(name="SYS_ID")
	private String id ;
	
	/**
	 * 系统名称
	 */
	@Column(name="SYS_NAM")
	private String name;
	
	/**
	 * 系统简称
	 */
	@Column(name="SYS_ABBR")
	private String abbr;
	/**
	 * 系统状态
	 */
	@Column(name="SYS_STATE")
	private String state;
	/**
	 * 系统描述
	 */
	@Column(name="SYS_DESC")
	private String desc;
	/**
	 * 系统访问地址
	 */
	@Column(name="SYS_URL")
	private String url;
	/**
	 * 添加时间
	 */
	@Column(name="SYS_DATE")
	private String datetime;
	
	
	
	
	public SysInf() {
		super();
	}
	public SysInf(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	
}
