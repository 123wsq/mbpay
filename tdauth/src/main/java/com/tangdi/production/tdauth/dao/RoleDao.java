package com.tangdi.production.tdauth.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 角色信息 数据库操作接口
 * 
 * @author
 * 
 */
public interface RoleDao extends BaseDao<RoleInf, Exception> {

	/**
	 * 批量逻辑删除角色信息.
	 * 
	 * @param list
	 *            角色列表信息
	 * @return 删除行数
	 */
	int deleteEntityList(Map<String, Object> map);

	/**
	 * 默认查询所有的角色信息
	 * 
	 * @return RoleInf的List
	 */
	List<RoleInf> queryEntityAll(@Param("sysId")String sysId,@Param("agentId")String agentId);

	/**
	 * 带条件的查询角色信息
	 * 
	 * @param map
	 *            查询条件
	 * @return RoleInf的List
	 */
	List<RoleInf> queryEntityByCon(Map<Object, Object> map);

	/**
	 * 通过角色ID查询角色信息,并且查询该角色下的所有用户
	 * 
	 * @param roleId
	 *            角色roleId
	 * @return 角色信息
	 */
	RoleInf selectEntityById(String roleId);
	
	/**
	 * 通过角色信息（机构号，角色名称）查询角色信息,并且查询该角色下的所有用户
	 * 
	 * @return 角色信息 list
	 */
	Integer countRole(HashMap <String,Object> cond);
	
	List<RoleInf> selectListPage(HashMap <String,Object> cond);

	int insertRef(HashMap<String,String> map)throws Exception ;

	int deleteMenuRefByRole(String roleId)throws Exception ;
	
	public List<RoleInf> selectRoleInfByUser(String uid);

	int selectRoleStateByUserId(String uid);
	public List<RoleInf> selectRoleInfByUserId(UserInf userInf);
	/**
	 * 添加到角色用户表中
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int insertRu(RoleInf roleInf)throws Exception ;
}
