package com.tangdi.production.tdauth.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdauth.bean.Tree;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.service.MenuService;
import com.tangdi.production.tdauth.service.RoleService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;

/**
 * 角色控制访问层
 * 
 * @author songleiheng
 * 
 */
@Controller
@RequestMapping("/auth/")
public class RoleController {

	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	/**
	 * 角色接口
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 菜单接口
	 */
	@Autowired
	private MenuService menuService;

	/**
	 * 角色管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "roleManage")
	public String menuManage() {
		return "auth/role/roleManage";
	}

	/**
	 * 添加角色信息
	 * 
	 * @param roleInf
	 *            角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "role/addRoleView")
	public String addRoleView(HttpSession session, HttpServletRequest req) throws Exception {
		req.setAttribute("DO", "add");
		req.setAttribute("TITLE", "添加角色");
		return "auth/role/roledo";
	}

	/**
	 * 添加角色信息
	 * 
	 * @param roleInf
	 *            角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roledo/addRole")
	public @ResponseBody
	ReturnMsg addRole(HttpSession session,@ModelAttribute("roleInf") RoleInf roleInf, @RequestParam(value = "roleadd_checkValue", required = false) String checkValue, HttpServletResponse resp) {
		ReturnMsg rm = new ReturnMsg();
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String roleId = sdf.format(date);
		
//		log.debug("[roleId]:" + roleId);
//		roleInf.setRoleId(roleId);
		UAI uai = (UAI) session.getAttribute("UID");
		roleInf.setAgentId(uai.getAgentId());//添加角色时如果是代理商添加，要同时把代理商Id存入角色表
		BeanDebugger.dump(roleInf);
		try {
			roleService.addEntity(roleInf, checkValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("添加角色失败：" + e.getMessage());
			return rm.setFail("添加角色失败:" + e.getMessage());
		}
		log.info("添加角色成功");
		return rm.setSuccess("添加角色成功！");
	}

	/**
	 * 跳转角色修改视图
	 * 
	 * @param roleInf
	 *            角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "role/updateRoleView")
	public String updateRoleView(HttpSession session, HttpServletRequest req) throws Exception {
		String roleId = (String) (req.getParameter("roleId") == null ? "" : (String) req.getParameter("roleId"));
		RoleInf roleInf = new RoleInf();
		roleInf.setRoleId(roleId);
		roleInf = roleService.getEntity(roleInf);
		req.setAttribute("DO", "update");
		req.setAttribute("TITLE", "修改角色");
		req.setAttribute("roleInf", roleInf);
		return "auth/role/roledo";
	}

	/**
	 * 更新角色信息
	 * 
	 * @param roleInf
	 *            角色
	 * @return
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roledo/updateRole")
	public @ResponseBody
	ReturnMsg updateRole(HttpSession session,@ModelAttribute("roleInf") RoleInf roleInf, @RequestParam(value = "roleadd_checkValue", required = false) String checkValue, HttpServletResponse resp) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		int result = 0;
		try {
			UAI uai = (UAI) session.getAttribute("UID");
			roleInf.setAgentId(uai.getAgentId());//添加角色时如果是代理商添加，要同时把代理商Id存入角色表
			result = roleService.modifyRoleInf(roleInf, checkValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新角色失败：" + e.getMessage());
			rm.setFail("更新角色失败：" + e.getMessage());
			return rm;
		}
		rm.setSuccess("成功更新" + result + "个角色信息");
		return rm;
	}

	/**
	 * 批量禁用角色
	 * 
	 * @author 未知
	 * @param rid
	 *            角色ID集合
	 * @param status
	 *            需要修改成X状态
	 * @return
	 */
	@RequestMapping(value = "updateRoleStatusBatch")
	public ReturnMsg updateRoleStatusBatch(@RequestParam(value = "rid", required = false) String[] rid, @RequestParam(value = "status", required = false) String status) {
		ReturnMsg msg = new ReturnMsg();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		for (String id : rid) {
			list.add(id);
		}
		list.remove("0001");//删除管理员角色,管理员角色不能被修改
		
		if(list.isEmpty()){
			msg.setMsg("200", "无修改记录！");
		}
		map.put("list", list);
		map.put("status", status);
		
		try {
			roleService.modifyList(map);
			msg.setMsg("200", "角色状态修改成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

	/**
	 * 通过角色ID查询角色信息
	 * 
	 * @author 未知
	 * @param rid
	 *            角色ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryRole")
	public ReturnMsg queryRole(@RequestParam(value = "roleId") String roleId) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RoleInf role = roleService.getEntity(new RoleInf(roleId));
		dataMap.put("roleinf", role);
		rm.setObj(role);
		return rm.setSuccess("角色信息查询成功", dataMap);
	}

	/**
	 * 根据条件查询角色列表
	 * 
	 * @param request
	 * @param session
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "role/queryRoleList")
	@ResponseBody
	public HashMap<String, Object> queryRoleList(HttpServletRequest request, @ModelAttribute("roleInf") RoleInf roleInf, HttpSession session) throws Exception {

		// 通过机构分离角色
		UAI uai = (UAI) session.getAttribute("UID");
		HashMap<String, Object> result = new HashMap<String, Object>();

		BeanDebugger.dump(roleInf);
		/**
		int pageNo = Integer.parseInt(request.getParameter("page"));

		int numPerPage = Integer.parseInt(request.getParameter("rows"));

		int start = (pageNo - 1) * numPerPage + 1;

		int end = (pageNo) * numPerPage;
	    */
		String uSysId = uai.getSysId();
		//如果不是运营平台的用户，只有操作自己平台的权限
		if(!Constant.SYS_TDWEB.equals(uSysId)){
			roleInf.setSysId(uSysId);
		}
		//如果是代理商系统，查询当前代理商下的角色
		if(Constant.SYS_TDPRM.equals(uSysId)){
			roleInf.setAgentId(uai.getAgentId());
		}

		int total = roleService.getCount(roleInf);

		List<RoleInf> roleList = roleService.getListPage(roleInf);
		//List<RoleInf> roleList = roleService.getListPage(roleInf);

		result.put("records", total);

		result.put("rows", roleList);

		return result;
	}

	/**
	 * 查询权限菜单 for 角色添加
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roledo/getRoleMenuForadd")
	@ResponseBody
	public List<Tree> getRoleMenuForadd(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {

		// 从session中拿取用户id
		UAI uAI = (UAI) session.getAttribute("UID");

		String uID = uAI.getId();
		String sysId = req.getParameter("sysId");

		List<Tree> listTree = menuService.getMenuByUid(uID,sysId,uAI.getAgentId());

		log.info("showListTree:" + showListTree(listTree));

		return listTree;
	}

	/**
	 * 查询权限菜单 for 角色修改
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roledo/getRoleMenuForupdate")
	@ResponseBody
	public List<Tree> getRoleMenuForupdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {

		// 从session中拿取用户id
		UAI uAI = (UAI) session.getAttribute("UID");
		String sysId = req.getParameter("sysId");

		String uID = uAI.getId();

		String roleId = req.getParameter("roleId") == null ? null : req.getParameter("roleId");

		log.info("   uID=" + uID + "   roleId=" + roleId);

		List<Tree> listTree = menuService.getMenuByRidUid(roleId, uID,sysId);

		log.info("showListTree:" + showListTree(listTree));

		return listTree;
	}

	/**
	 * 批量禁用角色
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "role/disableRole")
	@ResponseBody
	public ReturnMsg disableRole(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {

		ReturnMsg rm = new ReturnMsg();

		String roleIds = req.getParameter("roleIds") == null ? null : req.getParameter("roleIds");

		// state 1: 禁用 0启用
		int state = 1;

		log.info("   state=" + state + "   roleIds=" + roleIds);

		String[] str = roleIds.split(",");

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				list.add(str[i]);
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("roleStatus", state);

		map.put("list", list);

		int result = roleService.modifyList(map);

		rm.setSuccess("禁用" + result + "条记录成功！");

		return rm;
	}

	/**
	 * 批量启动角色
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "role/ableRole")
	@ResponseBody
	public ReturnMsg ableRole(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {

		ReturnMsg rm = new ReturnMsg();

		String roleIds = req.getParameter("roleIds") == null ? null : req.getParameter("roleIds");

		// state 1: 禁用 0启用
		int state = 0;

		log.info("   state=" + state + "   roleIds=" + roleIds);

		String[] str = roleIds.split(",");

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				list.add(str[i]);
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("roleStatus", state);

		map.put("list", list);

		int result = roleService.modifyList(map);

		rm.setRspcod("200");

		rm.setSuccess("启用" + result + "条记录成功！");

		return rm;
	}

	/**
	 * 将List<Tree> 转换成String ,便于打印。
	 * 
	 * @param listTree
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private String showListTree(List<Tree> listTree) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < listTree.size(); i++) {
			Tree tree = listTree.get(i);
			str.append(BeanUtils.describe(tree));
			if (tree.getChildren() != null) {
				List<Tree> listTree1 = tree.getChildren();
				showListTree(listTree1);
			}
		}
		return str.toString();
	}
	
	/**
	 * 生成下拉框
	 * @return
	 */
	@RequestMapping(value = "selectoption/role")
	@ResponseBody
	public ReturnMsg query(HttpSession session){
	   ReturnMsg msg = new ReturnMsg();
		try {
			if (!UID.getUAI(session).getAgentId().equals(Constant.SYS_AGENT_ID)) {
				msg.setObj(roleService.querySelectOption(UID.getUAI(session).getSysId(),UID.getUAI(session).getAgentId()));
			}else {
				msg.setObj(roleService.querySelectOption(UID.getUAI(session).getSysId(),""));
			}
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			msg.setMsg("202", e.getMessage());
		}
		
		return msg;
	} 
		
	
}
