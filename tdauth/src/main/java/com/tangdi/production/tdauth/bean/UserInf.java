package com.tangdi.production.tdauth.bean;

import java.util.ArrayList;
import java.util.List;

import com.tangdi.production.mpbase.lib.MBaseBean;

public class UserInf extends MBaseBean {

	public UserInf(String userId, String sysId, int flag) {
		this.userId = userId;
		this.sysId = sysId;

	}

	public UserInf(String userId, String userPwd, String orgId, String sysId, String agentId, int flag) {
		this.userId = userId;
		this.orgId = orgId;
		this.userPwd = userPwd;
		this.sysId = sysId;
		this.agentId = agentId;
	}

	public UserInf(String id, String userRandom, String lastLoginTime, String lastLoginIp,String operId) {
		this.id = id;
		this.userRandom = userRandom;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.operId=operId;

	}

	public UserInf(String agentId, String userId, String sysId, String userName, String roleId,String operId,String area,String userTyp,String userRoule) {
		this.agentId = agentId;
		this.userId = userId;
		this.sysId = sysId;
		this.userName = userName;
		this.roleId = roleId;
		this.operId = operId;
		this.area=area;
		this.userTyp=userTyp;
		this.userRoule=userRoule;
	}

	public UserInf(String id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 机构编号
	 */
	private OrgInf orgInf;

	private String orgId;
	private String orgName;

	private String roleId;

	/**
	 * 用户密码
	 */
	private String userPwd;

	/**
	 * 登陆随机码
	 */
	private String userRandom;

	/**
	 * 用户状态
	 * 
	 */
	private Integer userStatus;

	/**
	 * 新密码
	 */
	private String userNewPwd;

	//
	/**
	 * 登录次数
	 */
	private Integer lnum;

	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;

	private String lastLoginIp;
	private String phone;
	private String email;
	
	private String roleName; //临时储存数据
	/**
	 * 代理商ID
	 */
	private String agentId;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserNewPwd() {
		return userNewPwd;
	}

	public void setUserNewPwd(String userNewPwd) {
		this.userNewPwd = userNewPwd;
	}

	private String rids;
	private List<RoleInf> list = new ArrayList<RoleInf>();

	public String getRids() {
		return rids;
	}

	public void setRids(String rids) {
		this.rids = rids;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserInf() {
	}

	public UserInf(String userId, String userName, String orgId, String userPwd, String userRandom, Integer userStatus, List<RoleInf> list) {

		this.userId = userId;
		this.userName = userName;
		this.orgId = orgId;
		this.userPwd = userPwd;
		this.userRandom = userRandom;
		this.userStatus = userStatus;
		this.list = list;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public OrgInf getOrgInf() {
		return orgInf;
	}

	public void setOrgInf(OrgInf orgInf) {
		this.orgInf = orgInf;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserRandom() {
		return userRandom;
	}

	public void setUserRandom(String userRandom) {
		this.userRandom = userRandom;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<RoleInf> getList() {
		return list;
	}

	public void setList(List<RoleInf> list) {
		this.list = list;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getLnum() {
		return lnum;
	}

	public void setLnum(Integer lnum) {
		this.lnum = lnum;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	private String sysId;

	/**
	 * 获取系统ID
	 * 
	 * @return
	 */
	public String getSysId() {
		return sysId;
	}

	/**
	 * 设置系统ID
	 * 
	 * @param sysId
	 */
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	/**
	 * 0 需要修改密码 1 不需要修改
	 */
	private Integer isFirstLoginFlag;

	public Integer getIsFirstLoginFlag() {
		return isFirstLoginFlag;
	}

	public void setIsFirstLoginFlag(Integer isFirstLoginFlag) {
		this.isFirstLoginFlag = isFirstLoginFlag;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	//操作员Id
	private String operId;

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
	//操作员id（posp使用）
	private String sessionById;

	public String getSessionById() {
		return sessionById;
	}

	public void setSessionById(String sessionById) {
		this.sessionById = sessionById;
	}
	//用户类型   1 手刷2 收单3 手刷和收单
	private String userRoule;

	public String getUserRoule() {
		return userRoule;
	}

	public void setUserRoule(String userRoule) {
		this.userRoule = userRoule;
	}
//区域码
	private String area;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	//leixing
	private String userTyp;

	public String getUserTyp() {
		return userTyp;
	}

	public void setUserTyp(String userTyp) {
		this.userTyp = userTyp;
	}
	
	
}
