package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 用户角色关联实体，对应AUTH_USER_ROLE_REL_INF
 * @author luoyang
 *
 */
public class UserRoleRelInf extends BaseBean implements Serializable {

	public UserRoleRelInf(String roleId, String userId,String agentId) {
		this.roleId = roleId;
		this.userId = userId;
		this.agentId=agentId;
	}
	public UserRoleRelInf() {
	}
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7005300613218792375L;


	private String roleId;
	private String userId;
	
	private RoleInf role ;
	private String agentId;
	
	
	
	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public RoleInf getRole() {
		return role;
	}
	public void setRole(RoleInf role) {
		this.role = role;
	}

}
