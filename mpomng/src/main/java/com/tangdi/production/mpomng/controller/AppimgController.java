package com.tangdi.production.mpomng.controller;

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

import com.tangdi.production.mpomng.bean.AppimgInf;
import com.tangdi.production.mpomng.service.AppimgService;
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
public class AppimgController {
	private static final Logger log = LoggerFactory.getLogger(AppimgController.class);

	@Autowired
	private AppimgService appimgService;

	@RequestMapping({ "app/appimgListView" })
	public String appimgListView() {
		return "mpomng/app/appimgManage";
	}

	@RequestMapping(value = "app/appimgView")
	public String appimgView() {
		return "mpomng/app/appimgView";
	}

	@RequestMapping(value = "app/appimgDetailView")
	public String editeAppimgView() {
		return "mpomng/app/appimgDetailView";
	}

	@RequestMapping(value = "app/queryAppimgList")
	@ResponseBody
	public ReturnMsg queryAppimgList(@ModelAttribute("appimgInf") AppimgInf appimgInf) {
		int total = 0;
		List<AppimgInf> list = null;
		try {
			log.debug("查询参数:{}", appimgInf.debug());
			total = appimgService.getCount(appimgInf);
			list = appimgService.getListPage(appimgInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}

	@RequestMapping(value = "app/saveAppimg")
	@ResponseBody
	public ReturnMsg saveAppimg(@ModelAttribute("AppimgInf") AppimgInf appimgInf,HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai=(UAI) session.getAttribute("UID");
		BeanDebugger.dump(appimgInf);
		if(!"0".equals(appimgInf.getAppimgDisplay())){
			appimgInf.setAppimgDisplay("1");
		}
		try {
			if(appimgInf.getAppimgId()==null||appimgInf.getAppimgId().length()<=0){
				appimgInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				appimgInf.setCreateUserId(uai.getUserId());
			
				appimgService.addEntity(appimgInf);
			}else{
				appimgInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				appimgInf.setCreateUserId(uai.getUserId());
				appimgService.modifyEntity(appimgInf);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm.setSuccess("保存成功！");
	}

	@RequestMapping(value = "app/deleteAppimg")
	@ResponseBody
	public ReturnMsg deleteApp(@ModelAttribute("AppimgInf") AppimgInf appimgInf,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String[] appimgIds=ServletRequestUtils.getStringParameter(request, "appimgIds","").split(",");
		for (String  appimgId : appimgIds) {
			appimgInf.setAppimgId(appimgId);
		try {
			appimgService.removeEntity(appimgInf);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "删除异常.");
		}
		}
		log.info("删除成功");
		return rm.setSuccess("删除成功！");
	}

	@RequestMapping(value = "app/queryAppimgById")
	@ResponseBody
	public ReturnMsg queryAppimgById(@ModelAttribute("AppimgInf") AppimgInf appimgInf,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String appimgId=ServletRequestUtils.getStringParameter(request, "appimgId","");
		log.debug("TerminalCompanyController line 136:   appimgId:" + appimgId);
		appimgInf.setAppimgId(appimgId);
		appimgInf=appimgService.getEntity(appimgInf);
		rm.setObj(appimgInf);
		return rm;
	}

	@RequestMapping(value = "app/modifyAppImgStatus")
	@ResponseBody
	public ReturnMsg  setStatus(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status){
		int total =0;
		List<AppimgInf> list = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("appimgDisplay", status);
			total = appimgService.modifyAppImgStatus(map);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list).setSuccess("修改成功！");
	}
	

}
