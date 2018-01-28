package com.tangdi.production.tdauth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tangdi.production.tdauth.bean.OrgInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 机构service层
 * 
 * @author slh
 * 
 */
public interface OrgService extends BaseService<OrgInf, Exception> {
	public int countEntity(OrgInf entity) throws Exception;

	public Integer getCount(OrgInf entity) throws Exception;

	/**
	 * 批量更新角色状态
	 * 
	 * @param map
	 *            更新条件
	 * @return 更新行数
	 */
	public int modifyList(Map<String, Object> map) throws Exception;

	public HashMap<String, Object> getListPage(OrgInf orgInf, int pageNo, int numPerPage) throws Exception;

	/**
	 * 根据当前机构号，查询该机构以及该机构下的所有子机构（包含子机构的子机构）
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrgInf> getOrgForAll(String orgId) throws Exception;

	/**
	 * 根据当前机构号，查询该机构以及该机构下的所有子机构（包含子机构的子机构）,并且分页
	 * 
	 * @param OrgInf
	 * @return
	 */
	public Map<String, Object> getOrgForAll(OrgInf entity, int pageNo, int numPerPage, String currentOrgId);

	/**
	 * 根据机构号查询对应等级的父机构编码。
	 * 
	 * @param orgId
	 * @param orgLevel
	 *            机构等级
	 * @return
	 */
	public OrgInf getParentOrgId(String orgId, String orgLevel) throws Exception;

	public HashMap<String, Object> getOrgList(OrgInf orgInf, String operOrgId, int pageNo, int numPerPage) throws Exception;

	public void checkOrgStateByOrgId(String orgId) throws Exception;
	
	/**
	 * 获取某机构的直属下级
	 * @param entity
	 * @param pageNo
	 * @param numPerPage
	 * @param currentOrgId
	 * @throws Exception
	 */
	public Map<String,Object> querySubordinates(OrgInf entity, int pageNo, int numPerPage, String currentOrgId) throws Exception;


}
