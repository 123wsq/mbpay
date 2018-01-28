package com.tangdi.production.tdauth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.tdauth.bean.Tree;
import com.tangdi.production.tdauth.service.AuditService;
import com.tangdi.production.tdauth.service.MenuService;

/**
 * 审计管理
 * @author songleiheng
 *
 */
@Controller
@RequestMapping("/auth/")
public class AuditController {
	
	private static Logger  log = LoggerFactory.getLogger(AuditController.class);
	

	/**
	 * 菜单接口
	 */
	@Autowired
	private MenuService menuService;
	
	/**
	 * 审计接口
	 */
	@Autowired
	private AuditService auditService;
	
	/**
	 * 角色管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "auditView")
	public String auditView(){
		return "auth/audit/audit";
	} 
	
	/**
	 *
	 * 获取用户对应的审计菜单 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "audit/queryAuditList")
	@ResponseBody
	public List <Tree>  queryAuditList(HttpServletRequest request,HttpSession session){
		String auditId = "123";
		log.debug("请注意，审计编号是写死的");
		List <Tree> tree = menuService.getMenuByAudit(auditId);
		return tree;
	}
}
