package com.tangdi.production.mprcs.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mprcs.bean.CustLimitInf;
import com.tangdi.production.mprcs.service.CustLimitService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author zhengqiang  2015/9/11
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mprcs/")
public class CustLimitController {
	private static final Logger log = LoggerFactory
			.getLogger(CustLimitController.class);

	@Autowired
	private CustLimitService userLimitService;
	/**
	 * 风控记录跳转页面
	 * @return
	 */
	@RequestMapping(value = "userLimitManage/userLimitManageView")
	public String userLimitManageView(){
		return "mprcs/custLimit/custLimitManage";
	} 
	
	@RequestMapping(value = "userLimitManage/addView")
	public String addView() {
		return "mprcs/custLimit/custLimitEdit";
	}
	
	@RequestMapping(value = "userLimitManage/addDetailView")
	public String addDetailView() {
		return "mprcs/custLimit/custLimitDetail";
	}
	
	//代理商限额
	@RequestMapping(value = "agentLimitManage/agentLimitManageView")
	public String agentLimitManageView(){
		return "mprcs/agentLimit/agentLimitManage";
	} 
	
	@RequestMapping(value = "agentLimitManage/addView")
	public String addAgentView() {
		return "mprcs/agentLimit/agentLimitEdit";
	}
	@RequestMapping(value = "agentLimitManage/addDetailView")
	public String addDetailAgentView() {
		return "mprcs/agentLimit/agentLimitDetail";
	}
	
	@RequestMapping(value = "userLimitManage/userLimitManageList")
	@ResponseBody
	public ReturnMsg userLimitManageList(@ModelAttribute CustLimitInf userLimitInf) {
		int total=0;
		List<CustLimitInf> list=null;
		userLimitInf.setLimitType("20");
		try {
			log.debug("风控记录查询参数：{}",userLimitInf.debug());
			total=userLimitService.getCount(userLimitInf);
			list=userLimitService.getListPage(userLimitInf);
			log.debug("总记录：{}，总数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	@RequestMapping(value = "agentLimitManage/agentLimitManageList")
	@ResponseBody
	public ReturnMsg agentLimitManageList(@ModelAttribute CustLimitInf userLimitInf) {
		int total=0;
		List<CustLimitInf> list=null;
		userLimitInf.setLimitType("40");
		try {
			log.debug("风控记录查询参数：{}",userLimitInf.debug());
			total=userLimitService.getCount(userLimitInf);
			list=userLimitService.getListPage(userLimitInf);
			log.debug("总记录：{}，总数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	@RequestMapping(value = "userLimitEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("custLimitInf") CustLimitInf userLimitInf,@RequestParam(value = "limitCustId", required = false) String limitCustId,
			HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		int rt =0;
		try {
				userLimitInf.setUpdateName(uai.getUserName());
				userLimitInf.setUpdateDatetime(TdExpBasicFunctions.GETDATETIME());
				userLimitInf.setUpdateDate(TdExpBasicFunctions.GETDATE());
				rt = userLimitService.modifyEntity(userLimitInf);
				if (rt > 0){
					rm.setMsg("200", "修改成功.");
				}else{
					rm.setMsg("201", "修改失败.");
				}
				
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "修改异常！");
		}
		return rm;
	}

	@RequestMapping(value = "userLimitAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("userLimitInf") CustLimitInf userLimitInf,@RequestParam(value = "limitCustId", required = false) String limitCustId,HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		int rt = 0;
		try {
				userLimitInf.setCreateName(uai.getUserName());
				userLimitInf.setCreateDatetime(TdExpBasicFunctions.GETDATETIME());
				userLimitInf.setCreateDate(TdExpBasicFunctions.GETDATE());
				rt=userLimitService.addEntity(userLimitInf);
				if (rt > 0){
					rm.setMsg("200", "添加成功.");
				}else{
					rm.setMsg("201", "添加失败.");
				}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "AgentLimitAdd/add")
	@ResponseBody
	public ReturnMsg addAgent(@ModelAttribute("userLimitInf") CustLimitInf userLimitInf,@RequestParam(value = "limitAgentId", required = false) String limitAgentId,HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		int rt = 0;
		try {
				userLimitInf.setCreateName(uai.getUserName());
				userLimitInf.setCreateDatetime(TdExpBasicFunctions.GETDATETIME());
				userLimitInf.setCreateDate(TdExpBasicFunctions.GETDATE());
				rt=userLimitService.addAgentEntity(userLimitInf);
				if (rt > 0){
					rm.setMsg("200", "添加成功.");
				}else{
					rm.setMsg("201", "添加失败.");
				}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "userLimitManage/deleteUserLimitManage")
	@ResponseBody
	public ReturnMsg deleteUserLimitManage(@ModelAttribute("userLimitInf") CustLimitInf userLimitInf,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String[] limitIds = ServletRequestUtils.getStringParameter(request, "limitIds", "").split(",");
		for (String limitId : limitIds) {
			userLimitInf.setLimitId(limitId);
			try {
				userLimitService.removeEntity(userLimitInf);
			} catch (Exception e) {
				log.error("删除失败：" + e.getMessage());
				return rm.setFail("删除失败:" + e.getMessage());
			}
		}
		log.info("删除成功");
		return rm.setSuccess("删除成功！");
	}

	/**
	 * 查询单个记录
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "userLimitManage/queryUserLimitManageById")
	@ResponseBody
	public ReturnMsg queryUserLimitManageById(@ModelAttribute("userLimitInf") CustLimitInf userLimitInf,HttpServletRequest request) throws Exception {
		String limitId = ServletRequestUtils.getStringParameter(request, "limitId", "");
		log.debug("TerminalCompanyController line 136:   limitId:" + limitId);
		ReturnMsg rm = new ReturnMsg();
		userLimitInf.setLimitId(limitId);
		userLimitInf = userLimitService.getEntity(userLimitInf);
		rm.setObj(userLimitInf);
		return rm;
	}

	@RequestMapping(value = "userLimitManage/modifyUserLimitManageStatus")
	@ResponseBody
	public ReturnMsg  setStatus(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status){
		int total =0;
		List<CustLimitInf> list = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("isUse", status);
			total = userLimitService.modifyUserLimitManageStatus(map);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	
	
	
}
