package com.tangdi.production.tdauth.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 用户信息 数据库操作接口
 * 
 * @author zheng_qiang
 * 
 */
public interface UserDao extends BaseDao<UserInf, Exception> {
	/**
	 * 分页查询用户信息
	 * 
	 * @param user
	 * @param start
	 * @param end
	 * @return
	 */
	public List<UserInf> selectPageList(Map<String, Object> con) throws Exception;

	/**
	 * 根据ID列表更新用户状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int updateUsersStatus(Map<String, Object> con) throws Exception;

	/**
	 * 根据ID列表删除用户
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int deleteUsers(Map<String, Object> con) throws Exception;

	public int updateUserPwd(Map<String, Object> con) throws Exception;

	/**
	 * 根据用户条件获取用户总数
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public int countByMap(Map<String, Object> parameters) throws Exception;
	/**
	 * 更新代理商密码
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int updateAgentUserPwd(Map<String, Object> con) throws Exception;

	/**
	 * 验证代理商登录名是否重复
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int countLonEntity(UserInf user) throws Exception;
	
	
	public UserInf selectByUserId(UserInf userInf) throws Exception;
	
	/**
	 * 根据删除的用户id查询删除的用户userId（用于发送posp）
	 */
	public List<UserInf> selectUsers(Map<String, Object> con) throws Exception;
	/**
	 * 根据重置代理商密码（用于发送posp）
	 */
	public List<UserInf> selectAgentByUserId(String agentId) throws Exception;
	/**
	 * 根据代理商编号查询代理商的city
	 */
	public String selectCityId(String agentId) throws Exception;
	
	/**
	 * 根据删除的代理商编号查询删除的用户userId（用于发送posp）
	 */
	public List<UserInf> selectAgents(Map<String, Object> con) throws Exception;
}
