package com.tangdi.production.mprcs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mprcs.bean.DefaultLimitInf;
import com.tangdi.production.mprcs.service.DefaultLimitService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 默认限额控制器
 * @author zhengqiang
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mprcs/")
public class DefaultLimitController {
	private static final Logger log = LoggerFactory
			.getLogger(DefaultLimitController.class);

	@Autowired
	private DefaultLimitService defaultLimitService;
	

	/**
	 * 默认限额跳转页面
	 * @return
	 */
	@RequestMapping(value = "defaultLimitManage")
	public String defaultLimitManageView(){
		return "mprcs/defaultLimit/defaultLimitManage";
	} 
	
	/**
	 *
	 * 默认限额查询
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "defaultLimitManage/query")
	@ResponseBody
	public ReturnMsg query(){
		ReturnMsg rm = new ReturnMsg();
		DefaultLimitInf defaultLimitInf = new DefaultLimitInf();
		try {
			defaultLimitInf = defaultLimitService.queryLimit();
			log.debug("默认限额信息：{}",defaultLimitInf.toMap());
		} catch (Exception e) {
			log.error("查询默认限额信息失败。");
		}
		rm.setObj(defaultLimitInf);
		return rm;
	}
	

	
	@RequestMapping(value = "defaultLimitEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("DefaultLimitInf") DefaultLimitInf defaultLimitInf,HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		int rt = 0;
		try {
			defaultLimitInf.setUpdateName(uai.getUserName());
			defaultLimitInf.setUpdateDatetime(TdExpBasicFunctions.GETDATETIME());
			defaultLimitInf.setUpdateDate(TdExpBasicFunctions.GETDATE());
			rt = defaultLimitService.modifyEntity(defaultLimitInf);
			if (rt > 0){
				msg.setMsg("200", "默认限额更新成功.");
			}else{
				msg.setMsg("201", "默认限额更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

	
	
	
}
