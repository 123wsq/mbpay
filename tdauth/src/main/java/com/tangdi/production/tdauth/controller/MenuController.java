package com.tangdi.production.tdauth.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.MenuInf;
import com.tangdi.production.tdauth.bean.Tree;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.service.MenuService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 菜单Controller，用于管理菜单业务的调用。
 * @author zhengqiang
 *
 */
@Controller
@RequestMapping("/auth/")
public class MenuController {

	private static Logger  log = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private MenuService menuService;

	
	/**
	 * 获取用户对应的菜单树 
	 * @param request
	 * @param session
	 * @return List<Tree>
	 */
	@RequestMapping(value = "queryAuthMenu")
	@ResponseBody
	public List<Tree> queryAuthMenu(HttpServletRequest request,HttpSession session){
		String parentId = request.getParameter("parentId");
		//从session中拿取用户id
		UAI uAI=(UAI) session.getAttribute("UID");
		String u_id=uAI.getId();
		List <Tree> tree = menuService.queryAuthMenuTree(parentId,u_id,uAI.getSysId(),uAI.getAgentId());
		return tree;
	}
	/**
	 * 菜单管理视图方法
	 * @param request
	 * @return menuManage视图
	 */
	@RequestMapping(value = "menuManage")
	public String menuManage(HttpServletRequest request){
      
		return "auth/menu/menuManage";
	} 
	/**
	 * 查询菜单列表
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "menuManage/query")
	@ResponseBody
	public HashMap<String,Object> queryMenu(@ModelAttribute("menuInf")MenuInf menuInf,
			HttpServletRequest request,HttpSession session) throws Exception{
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		//String menuName = request.getParameter("menuName")==null?null:request.getParameter("menuName");
		log.info(menuInf.toString());
		//menuInf.setSysId(UID.getUAI(session).getSysId());
//		int pageNo = Integer.parseInt(request.getParameter("page"));
//		int numPerPage = Integer.parseInt(request.getParameter("rows"));
		//oracle 分页
		//int start = (pageNo-1)*numPerPage+1;
		//mysql分页
//		int start = (pageNo-1)*numPerPage;
//		int end = (pageNo)*numPerPage;
		//查询总记录数
		int total = menuService.getCount(menuInf);
		//查询菜单数据
		//List<MenuInf> menuList = menuService.getListPage(menuInf,start,end);
		List<MenuInf> menuList = menuService.getListPage(menuInf);
		
		result.put("records", total);
		result.put("rows", menuList);
		return result;
	}
	
	/**
	 * 返回菜单编辑视图
	 */
	@RequestMapping(value = "menuManage/editMenuView")
	public String editMenuView(){
		return "auth/menu/menuEdit";
	} 
	
	/**
	 * 通过菜单id查询菜单详细信息。
	 * @param menuInf 菜单
	 * @param timestamp 时间戳
	 * @param request
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "menuManage/queryMenuById")
	@ResponseBody
	public ReturnMsg queryMenuById(@ModelAttribute("menuInf")MenuInf menuInf,
			@RequestParam("timestamp")String timestamp,
			HttpServletRequest request){
		ReturnMsg msg = new ReturnMsg();
		MenuInf mi = null;
		try {
			 mi = menuService.getEntity(menuInf);
			 msg.setMsg("200", "信息查询成功！");
			 log.info(mi.toString());
		} catch (Exception e) {
			 msg.setMsg("201", "无记录信息！");
			log.error(e.getMessage(),e);
		}
		msg.setObj(mi);
		
		return msg;
	} 


	
	@RequestMapping(value = "menuEdit/edit")
	public @ResponseBody ReturnMsg editMenu(@ModelAttribute("menuInf")MenuInf menuInf) {
		ReturnMsg msg = new ReturnMsg();
		try {
			log.info(menuInf.toString());
			menuService.modifyEntity(menuInf);
			msg.setMsg("200", "更新成功！");
			
			
		} catch (Exception e) {
			msg.setMsg("201", "更新失败！");
			log.error(e.getMessage(),e);
		}
		return msg;
	} 
	/**
	 * 菜单批量删除
	 * @param menuInf 菜单对象
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "menuManage/delete")
	public @ResponseBody ReturnMsg deleteMenu(@RequestBody List<MenuInf> menuInf){
		ReturnMsg msg = new ReturnMsg();
		try {

			menuService.removeList(menuInf);
			msg.setMsg("200", "删除成功！");
		} catch (Exception e) {
			msg.setMsg("201", "删除失败！");
			log.error(e.getMessage(),e);
		}
		return msg ;
	} 
	
	/**
	 * 菜单添加</br>
	 * 调用菜单添加视图
	 * @param menuInf
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "menuManage/addMenuView")
	public String addMenuView(){
		return "auth/menu/menuAdd";
	} 
	
	
	@RequestMapping(value = "menuManage/addButtonView")
	public String addButtonView(){
		return "auth/menu/buttonAdd";
	} 
	
	@RequestMapping(value = "menuManage/addURLView")
	public String addURLView(@ModelAttribute("menuInf")MenuInf menuInf,
            HttpServletRequest request){
		MenuInf mi = null;

		try {
			 mi = menuService.getEntity(menuInf);
			 log.debug(mi.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		request.setAttribute("menui",mi);
		return "auth/menu/otherURLAdd";
	} 
	
	
	@RequestMapping(value = "menuAdd/init")
	public @ResponseBody Map<String,Object> addMenuInit(@ModelAttribute("menuInf")MenuInf menuInf,
			                   HttpServletRequest request){
        log.info(menuInf.toString());
		List<MenuInf> list = new ArrayList<MenuInf>(); 
		MenuInf mi = null;
		 try {
	        	if(menuInf.getMenuId() != null && !menuInf.getMenuId().equals("")
	        			){
	        		mi =  menuService.getEntity(menuInf) ;
	        		//log.info(mi.toString());
	        	}
			} catch (Exception e) {
				// mess = "{rspcode:'02',rspmsg:'无记录信息！'}";
				log.error(e.getMessage(),e);
			}
		
		
		for(int i=0;i<10;i++){
			if(mi == null)
				list.add(new MenuInf("一级菜单",menuInf.getSysId(),""));
			else{
				log.info(mi.toString());
				list.add(new MenuInf(mi.getMenuName(),menuInf.getSysId(),""));
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		//result.put("total", 10);
		result.put("rows", list);
		//msg.setRows(list);
		
		return result;
		
		
	} 

	@RequestMapping(value = "otherURLAdd/init")
	public @ResponseBody ReturnMsg addURLInit(
			                   HttpServletRequest request){
		ReturnMsg msg = new ReturnMsg();
		List<MenuInf> list = new ArrayList<MenuInf>(); 
		MenuInf mi = new MenuInf();
		for(int i=0;i<10;i++){
				list.add(mi);
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("rows", list);
		msg.setRows(list);
		return msg;
	} 
	
	@RequestMapping(value = "buttonAdd/init")
	public @ResponseBody ReturnMsg addButtonInit(@ModelAttribute("menuInf")MenuInf menuInf,
			                   HttpServletRequest request){
        log.info(menuInf.toString());
		ReturnMsg msg = new ReturnMsg();
		List<MenuInf> list = new ArrayList<MenuInf>(); 
		MenuInf mi = null;
		 try {
	        	if(menuInf.getMenuId() != null && !menuInf.getMenuId().equals("")
	        			){
	        		mi =  menuService.getEntity(menuInf) ;
	        		log.info(mi.toString());
	        	}
			} catch (Exception e) {
				// mess = "{rspcode:'02',rspmsg:'无记录信息！'}";
				log.error(e.getMessage(),e);
			}
		
		
		for(int i=0;i<10;i++){
				list.add(new MenuInf(mi.getMenuName(),menuInf.getSysId(),""));
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("rows", list);
		msg.setRows(list);
		
		return msg;
		
		
	} 	

	@RequestMapping(value = "menuAdd/add",method = {RequestMethod.POST })
	public @ResponseBody ReturnMsg addMenu(@RequestBody List<MenuInf> menuInf,HttpSession session
			){
		ReturnMsg msg = new ReturnMsg();
		try {

			log.info(menuInf.toString());
			//String sysid = UID.getUAI(session).getSysId();
			//log.debug("系统模块ID：[{}]",sysid);
			menuService.addList(menuInf,"");
			
			msg.setMsg("200", "添加菜单成功！");
		} catch (Exception e) {
			msg.setMsg("201", "添加菜单失败！");
			log.error(e.getMessage(),e);
		}
		
		return msg;

	} 
	@RequestMapping(value = "buttonAdd/add",method = {RequestMethod.POST })
	public @ResponseBody ReturnMsg addbutton(@RequestBody List<MenuInf> menuInf,HttpSession session
			){
		ReturnMsg msg = new ReturnMsg();
		try {

			log.info(menuInf.toString());
		//	String sysid = UID.getUAI(session).getSysId();
		//	log.debug("系统模块ID：[{}]",sysid);
			menuService.addList(menuInf,"");
			msg.setMsg("200", "添加按钮成功！");
		} catch (Exception e) {
			msg.setMsg("201", "添加按钮失败！");
			log.error(e.getMessage(),e);
		}
		
		return msg;

	} 
	@RequestMapping(value = "otherURLAdd/add",method = {RequestMethod.POST })
	public @ResponseBody ReturnMsg addOtherUrl(@RequestBody List<MenuInf> menuInf,HttpSession session
			){
		ReturnMsg msg = new ReturnMsg();
		try {

			log.info(menuInf.toString());
			String sysid = UID.getUAI(session).getSysId();
			log.debug("系统模块ID：[{}]",sysid);
			menuService.addList(menuInf,sysid);
			msg.setMsg("200", "添加地址成功！");
		} catch (Exception e) {
			msg.setMsg("201", "添加地址失败！");
			log.error(e.getMessage(),e);
		}
		
		return msg;

	} 
	
	@RequestMapping(value = "queryCombobox")
	public void queryCombobox(HttpServletResponse resp
			) throws IOException{
		PrintWriter out=resp.getWriter();
		try {
			out.write("[{\"id\":\"123\",\"name\":\"123\"}]");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	} 
	
	@RequestMapping(value = "menuManage/modifyMenuStatus0")
	@ResponseBody
	public ReturnMsg modifyMenuStatus0(@RequestParam("menuId")String ids, 
									  @RequestParam("status")String status) {
		ReturnMsg msg = new ReturnMsg();
		String strmsg = "";
		try {
			menuService.modifyMenuStatus(ids, status);
			if(status == "1")
				strmsg ="菜单禁用操作成功！";
			else
				strmsg ="菜单启用操作成功！";
			msg.setMsg("200", strmsg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = "menuManage/modifyMenuStatus1")
	@ResponseBody
	public ReturnMsg modifyMenuStatus1(@RequestParam("menuId")String ids, 
									  @RequestParam("status")String status) {
		ReturnMsg msg = new ReturnMsg();
		String strmsg = "";
		try {
			menuService.modifyMenuStatus(ids, status);
			if(status == "1")
				strmsg ="菜单禁用操作成功！";
			else
				strmsg ="菜单启用操作成功！";
			msg.setMsg("200", strmsg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
	
}
