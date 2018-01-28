package com.tangdi.production.tdauth.bean;

import java.io.Serializable;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 机构角色关联实体，对应AUTH_ORG_ROLE_REL_INF
 * @author luoyang
 *
 */
public class OrgRoleRelInf extends BaseBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 589695948544917310L;

	private String orgId;
	private String roleId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
