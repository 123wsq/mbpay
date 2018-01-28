package com.tangdi.production.tdauth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tangdi.production.tdauth.bean.SysInf;
import com.tangdi.production.tdauth.service.SystemService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 系统模块
 * 
 * @author zhengqiang
 * 
 */
@Controller
@RequestMapping("/auth/")
public class SystemController {

	private static Logger log = LoggerFactory.getLogger(SystemController.class);

	/**
	 * 系统模块接口
	 */
	@Autowired
	private SystemService systemService;


	/**
	 * 系统管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "sysManage")
	public String sysManage() {
		return "auth/sys/sysManage";
	}
	/**
	 * 实现查询功能
	 * @param sys
	 * @return
	 */
	@RequestMapping(value = "sysManage/query")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("sysInf") SysInf sys) {
		int total =0;
		List<SysInf> list = null;
		try {
			total = systemService.getCount(sys);
			list = systemService.getListPage(sys);
			log.debug("总记录数:{},数据列表：{}",total,list.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);

	}
	
	
	/**
	 * 根据SYS_ID查询
	 * @param sysInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sysManage/queryUserById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("SysInf") SysInf sysInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		SysInf entity = systemService.getEntity(sysInf);
		rm.setObj(entity);
		return rm;
	}

	/**
	 * 系统维护修改跳转页面
	 * @return String
	 */
	@RequestMapping(value = "sysManage/editView")
	public String editView() {
		return "auth/sys/sysEdit";
	}
    /**
     * 系统维护中数据修改
     * @param sysInf
     * @return
     */
	@RequestMapping(value = "sysEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("SyslInf") SysInf sysInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = systemService.modifyEntity(sysInf);
			if (rt > 0){
				msg.setMsg("200", "系统信息更新成功.");
			}else{
				msg.setMsg("201", "系统信息更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "系统信息更新失败");
		}
		return msg;
	}
	
	/**
	 * 系统维护中系统状态修改
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "sysManage/status")
	@ResponseBody
	public ReturnMsg modifySysStatus(@RequestParam("id") String ids, @RequestParam("state") int status) {
		ReturnMsg msg = new ReturnMsg();
		String strmsg = "";
		try {
			int num = systemService.modifySysStatus(ids, status);
			if (num > 0) {
				if (status == 1)
					strmsg = "系统禁用操作成功！";
				else
					strmsg = "系统启用操作成功！";
				    msg.setMsg("200", strmsg);
			} else {
				if (status == 1)
					strmsg = "系统禁用操作失败！";
				else
					strmsg = "系统启用操作失败！";

				msg.setMsg("201", strmsg);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
}
