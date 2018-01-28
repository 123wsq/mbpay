package com.tangdi.production.tdauth.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.tdauth.bean.OrgInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 机构信息 数据库操作接口
 * 
 * @author
 * 
 */
public interface OrgDao extends BaseDao<OrgInf, Exception> {

	public List<OrgInf> selectListPage(Map<String, Object> cond);

	public int deleteEntityList(Map<String, Object> map);

	public List<OrgInf> selectOrgForAll(String orgId);

	public List<OrgInf> selectOrgForAllByMap(Map<String, Object> map);

	public Integer countOrgForAllByMap(Map<String, Object> map);

	public OrgInf selectParentOrgByorgIdLevel(Map<String, String> map);

	public List<OrgInf> selectOrgListPage(Map<String, Object> cond);

	public int countOrg(Map<String, Object> cond);

	public Integer countOrgStateByOrgId(String orgId);

	public Integer updateCheckOrgName(Map<String, String> con);

	public Integer addCheckOrgName(String orgName);

	public Integer checkOrgByOrgId(String orgId);
	
	public List<OrgInf> querySubordinates(Map<String, Object> cond);
	
	public Integer countOrgForQuerySubordinates(Map<String, Object> cond);
	

}
