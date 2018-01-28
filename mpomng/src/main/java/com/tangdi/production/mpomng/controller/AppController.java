package com.tangdi.production.mpomng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.bean.AppInf;
import com.tangdi.production.mpomng.service.AppService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class AppController {
	private static final Logger log = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private AppService appService;

	@RequestMapping({ "app/appListView" })
	public String termListView() {
		return "mpomng/app/appManage";
	}

	@RequestMapping(value = "app/appView")
	public String termComView() {
		return "mpomng/app/appView";
	}

	@RequestMapping(value = "app/AppDetailView")
	public String editAppView() {
		return "mpomng/app/appDetailView";
	}

	@RequestMapping(value = "app/queryAppList")
	@ResponseBody
	public ReturnMsg queryAppList(@ModelAttribute("appInf") AppInf appInf) {
		int total = 0;
		List<AppInf> list = null;
		try {
			log.debug("查询参数:{}", appInf.debug());
			total = appService.getCount(appInf);
			list = appService.getListPage(appInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}

	@RequestMapping(value = "app/saveApp")
	@ResponseBody
	public ReturnMsg saveApp(@ModelAttribute("AppInf") AppInf appInf,HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai=(UAI) session.getAttribute("UID");
		BeanDebugger.dump(appInf);
		try {
			double lastVersion = appService.getLastAppVersion(appInf);
			if(Double.valueOf(appInf.getAppVersion()) < lastVersion){
				rm.setMsg("203", "APP版本过低！");
				return rm;
			}
			if(appInf.getAppId()==null||appInf.getAppId().length()<=0){
				appInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				appInf.setCreateUserId(uai.getUserId());
				appInf.setAppIssueDate(TdExpBasicFunctions.GETDATETIME());
				appService.addEntity(appInf);
			}else{
				appInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				appInf.setCreateUserId(uai.getUserId());
				appInf.setAppIssueDate(TdExpBasicFunctions.GETDATETIME());
				appService.modifyEntity(appInf);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm.setSuccess("保存成功！");
	}

	@RequestMapping(value = "app/deleteApp")
	@ResponseBody
	public ReturnMsg deleteApp(@ModelAttribute("AppInf") AppInf appInf,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String[] appIds = ServletRequestUtils.getStringParameter(request, "appIds", "").split(",");
		for (String appId : appIds) {
			appInf.setAppId(appId);
			try {
				appService.removeEntity(appInf);
			} catch (Exception e) {
				log.error("删除失败：" + e.getMessage());
				return rm.setFail("删除失败:" + e.getMessage());
			}
		}
			log.info("删除成功");
			return rm.setSuccess("删除成功！");
	}

	@RequestMapping(value = "app/queryAppById")
	@ResponseBody
	public ReturnMsg queryAppById(@ModelAttribute("AppInf") AppInf appInf,HttpServletRequest request) throws Exception {
		String appId = ServletRequestUtils.getStringParameter(request, "appId", "");
		log.debug("TerminalCompanyController line 136:   appId:" + appId);
		ReturnMsg rm = new ReturnMsg();
		appInf.setAppId(appId);
		appInf = appService.getEntity(appInf);
		rm.setObj(appInf);
		return rm;
	}
}
