package com.tangdi.production.tdauth.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.tdauth.bean.AuditLogInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.service.AuditLogService;
import com.tangdi.production.tdauth.service.AuditService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 审计管理
 * 
 * @author songleiheng
 * 
 */
@Controller
@RequestMapping("/auth/")
public class AuditLogController {

	private static Logger log = LoggerFactory.getLogger(AuditLogController.class);

	/**
	 * 审计日志接口
	 */
	@Autowired
	private AuditLogService auditLogService;

	/**
	 * 审计接口
	 */
	@Autowired
	private AuditService auditService;

	/**
	 * 角色管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "auditlog/auditLogView")
	public String auditView(HttpServletRequest request) {
		String endDate = TdExpBasicFunctions.GETDATETIME("yyyy-MM-dd");
		String startDate = endDate;
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		return "auth/auditlog/auditLogManage";
	}

	@RequestMapping(value = "auditlog/queryAuditLogList")
	@ResponseBody
	/**
	 * 获取用户对应的审计菜单 
	 * @param request
	 * @param session
	 * @return
	 */
	public HashMap<String, Object> queryAuditLogList(@ModelAttribute("auditLog") AuditLogInf auditLog, HttpServletRequest request, HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		String orgNo = uai.getOrgId();
		HashMap<String, Object> result = new HashMap<String, Object>();
		log.info("auditLog.getEndDate()" + auditLog.getEndDate());
		log.info("requestEndDate" + request.getParameter("endDate"));
		BeanDebugger.dump(auditLog);
		String startDate = auditLog.getStartDate();
		String endDate = auditLog.getEndDate();
		if (startDate == "") {
			startDate = TdExpBasicFunctions.GETDATETIME("yyyy-MM-dd");
		}
		if (endDate == "") {
			endDate = TdExpBasicFunctions.GETDATETIME("yyyy-MM-dd");
		}
		startDate = TdExpBasicFunctions.FMTTIME(startDate + "000000", "yyyy-MM-ddHHmmss", "yyyyMMddHHmmss");
		endDate = TdExpBasicFunctions.FMTTIME(endDate + "235959", "yyyy-MM-ddHHmmss", "yyyyMMddHHmmss");
		auditLog.setStartDate(startDate);
		auditLog.setEndDate(endDate);
		int pageNo = Integer.parseInt(request.getParameter("page"));
		int numPerPage = Integer.parseInt(request.getParameter("rows"));
		int start = (pageNo - 1) * numPerPage + 1;
		int end = (pageNo) * numPerPage;
		// 查询总记录数
		int total = 0;
		List<AuditLogInf> auditLogList = null;
		try {
			total = auditLogService.getCount(auditLog, orgNo);
			log.info("查询总条数为：" + total + "条");
			// 查询菜单数据
			auditLogList = auditLogService.getListPage(auditLog, start, end, orgNo);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询菜单失败：" + e.getMessage());
		}
		// AuditLogInf sd=auditLogList.get(0);
		// BeanDebugger.dump(sd);
		result.put("total", total);
		result.put("rows", auditLogList);
		return result;
	}

	/**
	 * 更新审计信息
	 * 
	 * @param checkValue
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "audit/updateAudit")
	public @ResponseBody
	ReturnMsg updateAudit(@RequestParam(value = "audit_checkValue", required = false) String checkValue, HttpServletResponse resp) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String auditId = "123";
		int result = auditService.modifyAuditInf(auditId, checkValue);
		// String
		// resultJson="{'rspcod':'200','rspmsg':'成功更新了"+result+"审计信息','url':'audit/audit','data':null}";
		rm.setSuccess("成功更新了" + result + "审计信息");
		//rm.setView("audit/audit");
		return rm;
	}
}
