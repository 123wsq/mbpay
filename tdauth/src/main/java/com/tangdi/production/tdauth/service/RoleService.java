package com.tangdi.production.tdauth.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 角色接口
 * @author zheng_qiang
 *
 */
public interface RoleService extends BaseService<RoleInf, Exception>{

    /**
     * 批量更新角色状态
     * @param map 更新条件
     * @return 更新行数
     */
	public int modifyList(Map<String,Object> map);
	/**
	 *  默认查询所有的角色信息
	 * @return RoleInf的List
	 */
	public List<RoleInf> queryRoleAll(String sysId,String agentId) ;
	
	/**
	 *  带条件的查询角色信息
	 * @param map 查询条件
	 * @return RoleInf的List
	 */
	public List<RoleInf> queryRolesByCon(Map<Object,Object> map); 
	
	/**
	 * 分页查询- 统计满足条件的数据条数。
	 * @param roleInf 角色信息。
	 * @return
	 */
	public Integer getCount(RoleInf roleInf);
	
	/**
	 * 分页查询- 查询满足条件的角色信息列表
	 * @param roleInf 角色信息
	 * @param start  开始编号
	 * @param end 结束编码
	 * @return
	 */
	//public List<RoleInf> getListPage(RoleInf roleInf, int start, int end) throws Exception;
	public List<RoleInf> getListPage(RoleInf roleInf) throws Exception;

	public void addEntity(RoleInf roleInf, String checkValue) throws Exception;
	
	public int modifyRoleInf(RoleInf roleInf, String checkValue) throws Exception;
	
	public List<RoleInf> getRoleInfByUser(String uid) throws Exception;
	public List<RoleInf> getRoleInfByUserId(UserInf userInf) throws Exception;
	
	public void checkRoleStateByUserId(String uid)throws Exception;
	
	/**
	 * 生成下拉框
	 * @return
	 * @throws Exception
	 */
	public String querySelectOption(String sysId,String agentId) throws Exception;
}
