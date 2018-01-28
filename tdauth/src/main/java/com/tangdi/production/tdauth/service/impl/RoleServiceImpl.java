package com.tangdi.production.tdauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.controller.RoleController;
import com.tangdi.production.tdauth.dao.MenuDao;
import com.tangdi.production.tdauth.dao.RoleDao;
import com.tangdi.production.tdauth.service.RoleService;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	public RoleInf getEntity(RoleInf entity) {
		return roleDao.selectEntityById(entity.getRoleId());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(RoleInf entity) throws Exception {
		return roleDao.updateEntity(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(RoleInf entity) throws Exception {
		Integer resultCount = 0;
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.putAll(entity.toMap());
		//con.put("orgId", entity.getOrgId());
		//con.put("roleName", entity.getRoleName());
		//con.put(key, value)
		try {
			resultCount = roleDao.countRole(con);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("数据库校验角色信息失败" + e.getMessage());
			throw new Exception("数据库验证角色信息失败");
		}
		if (resultCount > 0) {
			throw new Exception("该机构下该角色名已存在");
		}
		try {
			resultCount = roleDao.insertEntity(entity);
		} catch (Exception e) {
			log.error("添加角色失败" + e.getMessage());
			throw new Exception("添加角色失败");
		}
		return resultCount;
	}

	/**
	 * 默认查询所有的角色信息
	 * 
	 * @return RoleInf的List
	 */
	public List<RoleInf> queryRoleAll(String sysId,String agentId) {
		return roleDao.queryEntityAll(sysId,agentId);
	}

	public Integer getCount(RoleInf roleInf) {
		int result = -1;
		try {
		String 	agentId="'"+roleInf.getAgentId()+"'";
		roleInf.setAgentId(agentId);
			result = roleDao.countEntity(roleInf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 带条件的查询角色信息
	 * 
	 * @param map
	 *            查询条件
	 * @return RoleInf的List
	 */
	public List<RoleInf> queryRolesByCon(Map<Object, Object> map) {
		return roleDao.queryEntityByCon(map);
	}

	public List<RoleInf> getList(RoleInf entity) {
		return null;
	}




	/**
	 * 逻辑删除,改变脚色状态<br/>
	 * 单个删除
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(RoleInf entity) throws Exception {
		return roleDao.deleteEntity(entity);
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyList(Map<String, Object> map) {
		return roleDao.deleteEntityList(map);
	}

	public List<RoleInf> getListPage(RoleInf roleInf, int start, int end) {
		// TODO Auto-generated method stub
		HashMap<String, Object> cond = new HashMap<String, Object>();
		try {
			cond.putAll(roleInf.toMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	cond.put("roleInf", roleInf);
	//	cond.put("start", start);
	//	cond.put("end", end);

		return roleDao.selectListPage(cond);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEntity(RoleInf roleInf, String checkValue) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		if(!"0".equals(roleInf.getRoleStatus())){
			roleInf.setRoleStatus("1");
		}
		roleInf.setOrgId(Constant.TOP_ORG_ID);//设置默认机构
		if(StringUtils.isEmpty(roleInf.getRoleId())){
			String roleId = seqNoService.getSeqNoNew("ROLE_ID", "4", "0");
			roleInf.setRoleId(roleId);
		}
		if(StringUtils.isEmpty(checkValue)){
			checkValue = "-1";
		}else{
			checkValue="'"+checkValue.replace(",", "','")+"'";
		}
//		checkValue = menuGetParent(checkValue);
		map.put("menuId", checkValue);
		map.put("roleId", roleInf.getRoleId());
		Integer resultCount = 0;
		HashMap<String, Object> con = new HashMap<String, Object>();
//		con.put("roleId", roleInf.getRoleId());
		con.put("roleName", roleInf.getRoleName());
		con.put("agentId", roleInf.getAgentId());
		try {
			resultCount = roleDao.countRole(con);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("数据库校验角色信息失败" + e.getMessage());
			throw new Exception("数据库验证角色信息失败");
		}
		if (resultCount > 0) {
			throw new Exception("该机构下该角色名已存在");
		}
		try {
			resultCount = roleDao.insertEntity(roleInf);
			roleDao.insertRef(map);
		} catch (Exception e) {
			log.error("添加角色失败" + e.getMessage());
			throw new Exception("添加角色失败");
		}
	}

	private String menuGetParent(String checkValue) {
		// TODO Auto-generated method stub
		HashSet<String> menuSet = new HashSet<String>();
		StringBuffer strBuffer = new StringBuffer();
		String[] str = checkValue.split(",");
		for (int i = 0; i < str.length; i++) {
			String st = str[i];
			putStrToSet(st, menuSet);
		}
		Iterator<String> it = menuSet.iterator();
		while (it.hasNext()) {
			if (strBuffer.length() > 0) {
				strBuffer.append(',');
			}
			strBuffer.append("'" + it.next() + "'");
		}
		return strBuffer.toString();
	}

	private void putStrToSet(String st, HashSet<String> menuSet) {
		if (st != "") {
			int len = st.length() / 2;
			// System.out.println(len);
			for (int j = 1; j <= len; j++) {
				menuSet.add(st.substring(0, j * 2));
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyRoleInf(RoleInf roleInf, String checkValue) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		BeanDebugger.dump(roleInf);
		if(!"0".equals(roleInf.getRoleStatus())){
			roleInf.setRoleStatus("1");
		}
		HashMap<String, String> map = new HashMap<String, String>();
//		checkValue = menuGetParent(checkValue);
		if(StringUtils.isEmpty(checkValue)){
			checkValue = "-1";
		}else{
			checkValue="'"+checkValue.replace(",", "','")+"'";
		}
		map.put("menuId", checkValue);
		map.put("roleId", roleInf.getRoleId());
		RoleInf role = new RoleInf();
		try {
			role.setRoleId(roleInf.getRoleId());
			role = this.getEntity(role);
			
			//如果角色名没有修改，不用考虑重复问题
			if(roleInf.getRoleName() != null && roleInf.getRoleName().equals(role.getRoleName())){
				;
			}else{
				HashMap<String, Object> con = new HashMap<String, Object>();
				con.put("roleName", roleInf.getRoleName());
				con.put("agentId", roleInf.getAgentId());
				result = roleDao.countRole(con);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("数据库校验角色信息失败" + e.getMessage());
			throw new Exception("数据库验证角色信息失败");
		}
		if (result > 0) {
			log.error("该角色名已存在" + result);
			throw new Exception("该机构下该角色名已存在");
		}
		try {
			result = roleDao.updateEntity(roleInf);
			roleDao.deleteMenuRefByRole(roleInf.getRoleId());
			roleDao.insertRef(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新角色信息失败：" + e.getMessage());
			throw new Exception("更新角色信息失败");
		}
		return result;
	}

	public List<RoleInf> getRoleInfByUser(String uid) {
		return roleDao.selectRoleInfByUser(uid);
	}
	public List<RoleInf> getRoleInfByUserId(UserInf userInf) {
		return roleDao.selectRoleInfByUserId(userInf);
	}
	public void checkRoleStateByUserId(String uid) throws Exception {
		// TODO Auto-generated method stub
		log.debug("参与用户角色校验的用户是" + uid);
		int Result = roleDao.selectRoleStateByUserId(uid);
		if (Result < 1) {
			throw new Exception("该用户无可用角色");
		}
	}

	public String querySelectOption(String sysId,String agentId) throws Exception {
		List<RoleInf> rolelist = queryRoleAll(sysId,agentId);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HashMap<String,Object> nmap = new HashMap<String,Object>();
	
		for(int i=0;i<rolelist.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			RoleInf key = rolelist.get(i);
			if(!key.getRoleId().equals(UAI.TOP_ROLE_ID) && !key.getRoleId().equals(UAI.TOP_AGENT_ROLE_ID)){
				map.put("text", key.getRoleName());
				map.put("value",key.getRoleId());
				list.add(map);
			}
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap) ;
	}
	

	public List<RoleInf> getListPage(RoleInf roleInf) throws Exception {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		int PageSize = roleInf.getPageSize();    //
		int numPerPage = roleInf.getCurrentPages();
		int start = (numPerPage - 1) * PageSize + 1;
		int end =numPerPage* PageSize;
		cond.put("sysId", roleInf.getSysId());
		cond.put("agentId", roleInf.getAgentId());
		cond.put("start", start);
		cond.put("end",end);
		return roleDao.selectListPage(cond);
	}
	
}
