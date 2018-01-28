package com.tangdi.production.tdauth.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.util.DictUtils;

/**
 * RoleInf 对应的为AUTH_ROLE_INF表，为角色信息表
 * @author luoyang
 *
 */
public class RoleInf extends BaseBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2106041016810600077L;

	private String roleId;
	private String roleName;
	private String roleDesc;
	private String roleStatus;
	private String roleStatusName;
	private String orgId;
	
	private String orgIds ;
	private String flag ;
	
	private String sysId;
	private String sysNam;//临时储存数据
	/**所属代理商编号*/
	private String agentId;
	

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * 获取系统ID
	 * @return
	 */
	public String getSysId() {
		return sysId;
	}

	/**
	 * 设置系统ID
	 * @param sysId
	 */
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	

	public RoleInf(String roleId, String roleName, String roleDesc,
			String roleStatus, String orgId, int id) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleStatus = roleStatus;
		this.roleStatusName=DictUtils.get("ROLESTATUS", this.roleStatus);
		this.orgId = orgId;
	}

	public RoleInf(String roleId) {
		this.roleId = roleId;
	}
	public RoleInf() {
	}

	public String getRoleStatusName() {
		return roleStatusName;
	}

	public RoleInf(String roleId, String roleName, String roleDesc,
			String roleStatus, String orgId) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleStatus = roleStatus;
		this.roleStatusName=DictUtils.get("ROLESTATUS", this.roleStatus);
		this.orgId = orgId;
	}

	private List<UserInf> list = new ArrayList<UserInf>();

	public List<UserInf> getList() {
		return list;
	}

	public void setList(List<UserInf> list) {
		this.list = list;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
		this.roleStatusName=DictUtils.get("ROLESTATUS", this.roleStatus);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	
	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getSysNam() {
		return sysNam;
	}

	public void setSysNam(String sysNam) {
		this.sysNam = sysNam;
	}
	


}
