package com.tangdi.production.tdauth.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.bean.UserRoleRelInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.controller.MenuController;
import com.tangdi.production.tdauth.dao.UserDao;
import com.tangdi.production.tdauth.dao.UserRoleRelDao;
import com.tangdi.production.tdauth.service.MenuService;
import com.tangdi.production.tdauth.service.RoleService;
import com.tangdi.production.tdauth.service.UserService;
import com.tangdi.production.tdbase.util.TdExprSecurity;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

@Service
public class UserServiceImpl implements UserService {
	private static Logger log = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleRelDao userRoleDao;
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Value("#{tdauthConfig}")
	private Properties prop;
	//hessin 传送到posp
	@Autowired
	private HessinClientBean remoteService;
	
	public UserInf getEntity(UserInf entity) throws Exception {
		return userDao.selectEntity(entity);
	}

	public List<UserInf> getList(UserInf entity) throws Exception {
		return userDao.selectList(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(UserInf entity) throws Exception {
		
		int rv = 0;
		if (entity.getUserPwd() == null || entity.getUserPwd().length() <= 0) {
			entity.setUserPwd(TdExprSecurity.MD5STR(Constant.SYS_PWD));
		}else{
			entity.setUserPwd(TdExprSecurity.MD5STR(entity.getUserPwd()));
		}
		//entity.setUserPwd(TdExprSecurity.MD5STR(entity.getUserPwd()));
		entity.setUserRandom(Constant.SYS_RANDOM);
		entity.setUserStatus(0);
		entity.setLnum(0);
		entity.setLastLoginIp("127.0.0.1");
		entity.setLastLoginTime(Constant.SYS_LASTLOGINTIME);
		String uid = seqNoService.getSeqNoNew("USER_ID_SEQ", "4", "1");
		entity.setId(uid);

		if (entity.getArea() == null || entity.getArea().equals("")) {
			entity.setArea("");
		}

		if (entity.getEmail()== null || entity.getEmail().equals("")) {
			entity.setEmail("");
		}
		if (entity.getPhone()== null || entity.getPhone().equals("")) {
			entity.setPhone("");
		}
		log.debug(entity.debug());

		rv = userDao.insertEntity(entity);
		String roleId[] = null;
		try {
			roleId = entity.getRoleId().split("\\|");
		} catch (Exception e) {
				throw new Exception("分割角色id出错了.", e);
		}
		for (String rid : roleId) {
				userRoleDao.insertRel(new UserRoleRelInf(rid, uid,entity.getAgentId()));
		}
		return rv;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addList(List<UserInf> list) throws Exception {
		return userDao.insertList(list);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(UserInf entity) throws Exception {
		return userDao.updateEntity(entity);
	}

	public int removeEntity(UserInf entity) {
		return 0;
	}

	public int countEntity(UserInf entity) throws Exception {
		int count = userDao.countEntity(entity);
		log.debug("查询当前用户总数:{}", count);
		return count;
	}

	/**
	 * 根据用户条件获取用户总数
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public int countByMap(Map<String, Object> parameters) throws Exception {
		return userDao.countByMap(parameters);
	}

	public List<UserInf> getListPage(UserInf user, String currentOrgId) throws Exception {
		// mysql分页
		// oracle分页
		// int start = (pageNum - 1) * numPerPage + 1;
		Map<String, Object> con = user.toMap();
		int PageSize = user.getPageSize();    //
		int numPerPage = user.getCurrentPages();
		
		int start = (numPerPage - 1) * PageSize + 1;
		int end =numPerPage* PageSize;
		con.put("start", start);
		con.put("end",end);
		return userDao.selectPageList(con);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyUsersStatus(String ids, String status) throws Exception {

		Map<String, Object> con = new HashMap<String, Object>(2);
		con.put("userStatus", status);
		con.put("ids", "("+ids+")");
		log.debug(ids);
		int num = 0;
		try {
			num = userDao.updateUsersStatus(con);
		} catch (Exception e) {
			log.error("用户状态更新异常！", e);
			throw new Exception("用户状态更新异常！", e);
		}
		return num;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeUsers(String ids,String operId) throws Exception {
		Map<String, Object> con = new HashMap<String, Object>(1);
		con.put("ids", ids);
		int rv = 0;
		String[] idlist = ids.split(",");
		UserInf ui = userDao.selectEntity(new UserInf(UAI.TOP_USER_ID, UAI.TOP_PAY_MOUDLE, 0));
		for (String it : idlist) {
			if (String.valueOf(ui.getId()).equals(it)) {
				throw new Exception("超级用户【" + UAI.TOP_USER_ID + "】不能被删除！");
			}
		}
				try {
			      rv = userDao.deleteUsers(con);
			      rv = rv + userRoleDao.deleteUsersRels(con);
		        } catch (Exception e) {
			       log.error("删除用户异常！", e);
			       throw new Exception("删除用户异常！");
		         }
		return rv;
	}

	/**
	 * 修改用户
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyUserRole(UserInf user, String roleId, String roleIdNew) throws Exception {
		int rv = 0;
		try {
			UserInf ui = userDao.selectEntity(new UserInf(UAI.TOP_USER_ID,null, 0));
			if (ui.getId().equals(user.getId())) {
				throw new Exception("超级用户【" + UAI.TOP_USER_ID + "】不能被修改！");
			}

			log.debug(user.toString());
			rv = userDao.updateEntity(user);
			if (user.getRoleId() != null && !user.getRoleId().equals("")) {
				// 修改角色关系
				Map<String, Object> con = new HashMap<String, Object>();
				String ids="'"+user.getId()+"'";
				con.put("ids", ids);
				rv = rv + userRoleDao.deleteUsersRels(con);

				// 添加角色关系
				// String[] idsNew = roleIdNew.split("\\|");
				String[] idsNew = new String[] { user.getRoleId() };
				List<UserRoleRelInf> list = new ArrayList<UserRoleRelInf>();
				for (String id : idsNew) {
					list.add(new UserRoleRelInf(id, user.getId(),user.getAgentId()));
				}
				rv = rv + userRoleDao.insertRelList(list);
			}
			if (rv == 0) {
				throw new Exception("修改用户信息失败！");
			}
		} catch (Exception e) {
			log.error("修改用户信息失败！", e);
			throw new Exception("修改用户信息失败！", e);
		}

		return rv;

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyUserPwd(String ids, String npwd, String opwd,String operId,String agentId) throws Exception {
		boolean flg = false;
		int rv = 0;
		String[] uids = ids.split(",");
		try {
			if (npwd == null) {
				npwd = TdExprSecurity.MD5STR(Constant.SYS_PWD);
				flg = true;
			} else {
				npwd = TdExprSecurity.MD5STR(npwd);
			}
			if (opwd != null) {
				opwd = TdExprSecurity.MD5STR(opwd);
			}
			for (String id : uids) {
				Map<String, Object> con = new HashMap<String, Object>();
				con.put("ids", id);
				con.put("npwd", npwd);
				con.put("random", String.valueOf((int) ((Math.random() * 9 + 1) * 100000)));
				con.put("isFirstLoginFlag", Constant.SYS_ISFIRSTLOGINFLAG_1);
				if (flg) {
					// 如果 flg=true 则是 重置 密码
					con.put("random", Constant.SYS_RANDOM);
					con.put("lastLoginTime", Constant.SYS_LASTLOGINTIME);
					con.put("isFirstLoginFlag", Constant.SYS_ISFIRSTLOGINFLAG_0);
					
				}
				con.put("opwd", opwd);
			
				rv = userDao.updateUserPwd(con);
			}
			
		} catch (Exception e) {
			log.error("密码修改异常！", e);
			throw new Exception("密码修改异常！", e);
		}
		if (rv <= 0) {
			throw new Exception("密码修改失败,用户不存在！");
		}

		return rv;
	}

	public Integer getCount(UserInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserInf> getListPage(UserInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int initAgentAdmin(String agentId, String logonName,String operId,String city,String currentId) throws Exception {
		
		int rt=0;
		/*
		 * //建立角色 String roleId = seqNoService.getSeqNoNew("ROLE_ID", "4", "0");
		 * 
		 * RoleInf role = new RoleInf(); role.setRoleId(roleId);
		 * role.setRoleName("代理商超级管理员"); role.setRoleDesc("代理商系统最高角色权限");
		 * role.setOrgId(Constant.TOP_ORG_ID);//设置默认机构 role.setFlag("0");
		 * role.setSysId(Constant.SYS_TDPRM); role.setRoleStatus("0"); //正常
		 * roleService.addEntity(role);
		 * 
		 * //建立角色菜单关系 String[] mids =
		 * menuService.queryMenuIds(Constant.SYS_TDPRM);
		 * List<RoleMenuButtonRelInf> rmlist = new
		 * ArrayList<RoleMenuButtonRelInf>(); for(String mid:mids){
		 * rmlist.add(new RoleMenuButtonRelInf(roleId,mid)); }
		 * menuService.addRoleMenuButtonRelInf(rmlist);
		 */
		// 建立用户
		//20160503   发往posp代理商登录账号
		UserInf userPosp=new UserInf();
		userPosp.setAgentId(currentId);
		userPosp.setUserId(operId);
	     UserInf sessionInf=	userDao.selectByUserId(userPosp);
	     String operIdposp=sessionInf.getOperId();
	     String userTyp="01";
	     String userRoule="01";

		rt= addEntity(new UserInf(agentId, logonName, Constant.SYS_TDPRM, "代理商超级管理员用户", Constant.SYS_AGENT_ADMIN_ROLE,operIdposp,city,userTyp,userRoule));
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int resetAgentAdminPwd(String agentId,String logonName,String operId) throws Exception {
		if (agentId == null || agentId.equals("")) {
			throw new Exception("代理商ID不能为空！");
		}
		
//		//重置代理商密码传给posp
//		//1.根据用户的id查询用户的operId
//			//平台登录人agentId
//				String agentIduser="2015000000";
//				UserInf userPosp=new UserInf();
//				userPosp.setAgentId(agentIduser);
//				userPosp.setUserId(operId);
//	     UserInf sessionInf=	userDao.selectByUserId(userPosp);
//			Map<String, Object> mapId = new HashMap<String, Object>(1);
//			//查询重置的operid（posp的id）
//			mapId.put("ids", agentId);
//			List<UserInf>	userIdList = userDao.selectAgents(mapId);
//			StringBuffer sbDel=new StringBuffer();
//			for(UserInf userlist: userIdList){
//				sbDel.append(userlist.getOperId()+";");
//			}
//			log.info("将要重置的用户pospid=========="+sbDel);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", sbDel);
//		map.put("operId", sessionInf.getOperId());
//		Map<String, Object> userResCod = remoteService.dotran(TranCode.POSP_USER_RES, map);
//		log.debug("注册成功后posp返回值：{}", userResCod);
//		
//		if (!userResCod.get("RSPCOD").toString().trim().equals("00000")) {
//			throw new Exception(userResCod.get("RSPMSG").toString().trim());
//		}else{
//		
		
		
		Map<String, Object> con = new HashMap<String, Object>();
		con.put("userId", logonName);
		con.put("pwd", TdExprSecurity.MD5STR(Constant.SYS_PWD));
		con.put("random", Constant.SYS_RANDOM);
		con.put("lastLoginTime", Constant.SYS_LASTLOGINTIME);
		con.put("isFirstLoginFlag", Constant.SYS_ISFIRSTLOGINFLAG_0);
		con.put("agentId", agentId);
		int rt = userDao.updateAgentUserPwd(con);
		
		log.debug("返回参数{}", rt);
		return rt;
//		}
	}

	@Override
	public UserInf selectUserInfByUserId(UserInf userInf) throws Exception {
		// TODO Auto-generated method stub
		return userDao.selectByUserId(userInf);
	}
	

}
