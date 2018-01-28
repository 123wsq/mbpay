package com.tangdi.production.tdauth.service;

import java.util.HashMap;
import java.util.List;

import com.tangdi.production.tdauth.bean.MenuInf;
import com.tangdi.production.tdauth.bean.RoleMenuButtonRelInf;
import com.tangdi.production.tdauth.bean.Tree;
import com.tangdi.production.tdbase.service.BaseService;
/**
 * 菜单管理接口
 * @author zhengqiang 
 *
 */
public interface MenuService extends BaseService<MenuInf, Exception> {
	
	/**
	 * 添加菜单角色关系
	 * @param roleMenuButtonRelInf
	 * @return
	 */
	public int addRoleMenuButtonRelInf(List<RoleMenuButtonRelInf> roleMenuButtonRelInf)  throws Exception;
	/**
	 * 批量删除菜单角色关系
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	public int removeRoleMenuButtonRelInf(List<String> menuIds)  throws Exception;

	/**
	 * 获取用户对应得权限树
	 * @param parentId
	 * @param u_Id
	 * @param sysId 系统编号
	 * @param agentId 代理商编号
	 * @return
	 */
	List<Tree> queryAuthMenuTree(String parentId,String u_Id,String sysId,String agentId);
	
	List<MenuInf> queryAuthAccording(String u_Id,String sysId);
	/**
	 * 根据用户id 获取用户的权限url集合。
	 * @param u_Id
	 * @return
	 */
	 HashMap<String, Boolean> queryAuthMap(String u_Id,String sysId);


	/**
	 * 通过菜单名称查询
	 * @param menuName
	 * @param start
	 * @param end
	 * @return 菜单list
	 */
	List<MenuInf> getListPage(String menuName, int start, int end);
	/**
	 * 通过菜单属性查询
	 * @param menuInf
	 * @param start
	 * @param end
	 * @return 菜单list
	 */
	List<MenuInf> getListPage(MenuInf menuInf,int start,int end);
	/**
	 * 通过菜单属性查询
	 */
	List<MenuInf> getListPage(MenuInf menuInf);
	
	public int removeList(String[] menuId) throws Exception ;
	
	/**
	 * 根据角色编码，用户表中的id 查询权限树
	 * @param roleId 角色编号
	 * @param u_Id  用户的唯一标识 编码
	 * @return
	 */
	public  List <Tree> getMenuByRidUid(String roleId,String u_Id,String sysId);
	/**
	 * 根据用户表中的id 查询权限树
	 * @param u_Id 用户的唯一标识 编码
	 * @return 菜单树
	 */
	public  List <Tree> getMenuByUid(String u_Id);
	public  List <Tree> getMenuByUid(String u_Id,String sysId,String agentId);
	/**
	 * 根据审计编码 查询审计树
	 * @param auditId 审计编码 
	 * @return 菜单树
	 */
	public  List <Tree> getMenuByAudit(String auditId) ;
	/**
	 * 根据审计编码查询被选中的审计树，并将该map放置到缓存变量
	 * @param auditId
	 */
	public  void  getCheckMenuByAudit(String auditId) ;

	/**
	 * 修改菜单状态
	 * @param ids 菜单ID
	 * @param status 状态
	 * @return
	 */
	public int modifyMenuStatus(String ids,String status)  throws Exception ;
	
	/**
	 * 批量删除菜单
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int removeList(List<MenuInf> list) throws Exception;
	
	/**
	 * 添加菜单到指定系统中
	 * @param list
	 * @param sysId 模块ID
	 * @return
	 * @throws Exception
	 */
	public int addList(List<MenuInf> list,String sysId) throws Exception;
	
	/**
	 * 通过系统ID查询所有的
	 * @param sysId
	 * @return
	 * @throws Exception
	 */
	public String[] queryMenuIds(String sysId) throws Exception;
	
}
