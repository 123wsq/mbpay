package com.tangdi.production.tdauth.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.tdauth.bean.MenuInf;
import com.tangdi.production.tdauth.bean.RoleMenuButtonRelInf;
import com.tangdi.production.tdbase.dao.BaseDao;


/**
 * 菜单信息 数据库操作接口
 * 
 * @author junyu
 * 
 */
public interface MenuDao extends BaseDao<MenuInf, Exception> {
	/**
	 * 添加菜单角色关系
	 * @param roleMenuButtonRelInf
	 * @return
	 */
	public int insertMenuRoleRel(RoleMenuButtonRelInf roleMenuButtonRelInf)   throws Exception;
	/**
	 * 删除菜单角色关系
	 * @param roleMenuButtonRelInf
	 * @return
	 * @throws Exception
	 */
	public int deleteMenuRoleRel(@Param(value="menuId") String menuId) throws Exception;

	List<MenuInf> selectAuthMenu(HashMap<String, String> map);
	List<MenuInf> findListPage(HashMap<String, Object> cond);
	
	public int deleteMenuList(int[] menuId) throws Exception;
	
	public String selectMenuId(String childId) throws Exception;
	
	public int deleteMenu(String menuId) throws Exception;
	
	public List<MenuInf> selectMenuByUid(Map<String,String> paramMap) throws Exception;
	
	public List<MenuInf> selectMenuByRid(String roleId) throws Exception;
	
	public List<MenuInf>  selectMenuByUidRid(HashMap<String, Object> map) throws Exception;

	public List<MenuInf> selectMenuByAuditId(String auditId)throws Exception;

	public List<MenuInf> selectCheckMenuByAuditId(String auditId);
	/**
	 * 根据用户id查询url
	 * @param u_Id
	 * @return
	 */
	public List<MenuInf> selectMenuUrlByUid(HashMap<String, String> map);
	
	/**
	 * 修改菜单状态
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int updateMenuStatus(Map<String, Object> con) throws Exception;

}
