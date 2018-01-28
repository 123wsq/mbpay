package com.tangdi.production.mpomng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.bean.HolidayRuleInf;
import com.tangdi.production.mpomng.service.HolidayRuleService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 节日规则维护
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class HolidayRuleController {
	private static final Logger log = LoggerFactory.getLogger(HolidayRuleController.class);

	@Autowired
	private HolidayRuleService holidayRuleService;
	/**
	 * 跳转到显示页面
	 * @return
	 */
	@RequestMapping({ "holidayRule/holidayListView" })
	public String termListView() {
		return "mpomng/holidayRule/holidayManage";
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value = "holidayRule/holidayView")
	public String termComView() {
		return "mpomng/holidayRule/holidayView";
	}
	/**
	 * 跳转到节日修改页面
	 * @return
	 */
	@RequestMapping(value = "holidayRule/holidayEditView")
	public String holidayEditView() {
		return "mpomng/holidayRule/holidayEdit";
	}
	@RequestMapping(value = "holidayRule/queryholidayRuleList")
	@ResponseBody
	public ReturnMsg queryAppList(@ModelAttribute("holidayRuleInf") HolidayRuleInf holidayRuleInf) {
		int total = 0;
		List<HolidayRuleInf> list = null;
		try {
			log.debug("查询参数:{}", holidayRuleInf.debug());
			total = holidayRuleService.getCount(holidayRuleInf);
			list = holidayRuleService.getListPage(holidayRuleInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}

	@RequestMapping(value = "holidayRule/saveholidayRule")
	@ResponseBody
	public ReturnMsg saveApp(@ModelAttribute("holidayRuleInf") HolidayRuleInf holidayRuleInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt=0;
		try {
			rt=holidayRuleService.queryHolidayBydate(holidayRuleInf);
			if(rt>0){
				log.debug("查询结果:{}", rt);
				rm.setMsg("201", "日期已经存在，请重新添加！");
			}else{
				holidayRuleService.addEntity(holidayRuleInf);
			}
				
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm.setSuccess("添加成功！");
	}
	
	@RequestMapping(value = "holidayRule/editHolidayRule")
	@ResponseBody
	public ReturnMsg editApp(@ModelAttribute("holidayRuleInf") HolidayRuleInf holidayRuleInf) {
		ReturnMsg rm = new ReturnMsg();
		try {
		String date=	holidayRuleInf.getHoDate().replaceAll("-", "");
		holidayRuleInf.setHoDate(date);
				holidayRuleService.modifyEntity(holidayRuleInf);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm;
	}

	@RequestMapping(value = "holidayRule/deleteholidayRule")
	@ResponseBody
	public ReturnMsg deleteApp(@ModelAttribute("holidayRuleInf") HolidayRuleInf holidayRuleInf,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String[] hoDates = ServletRequestUtils.getStringParameter(request, "hoDate", "").split(",");
		for (String hoDate : hoDates) {
			holidayRuleInf.setHoDate(hoDate);
			try {
				holidayRuleService.removeEntity(holidayRuleInf);
			} catch (Exception e) {
				log.error("删除失败：" + e.getMessage());
				return rm.setFail("删除失败:" + e.getMessage());
			}
		}
			log.info("删除成功");
			return rm.setSuccess("删除成功！");
	}

	@RequestMapping(value = "holidayRule/queryholidayRuleById")
	@ResponseBody
	public ReturnMsg queryAppById(@ModelAttribute("holidayRuleInf") HolidayRuleInf holidayRuleInf,HttpServletRequest request) throws Exception {
		String hoDate = ServletRequestUtils.getStringParameter(request, "hoDate", "");
		log.debug("HolidayRuleController line 136:   hoDate:" + hoDate);
		ReturnMsg rm = new ReturnMsg();
		holidayRuleInf.setHoDate(hoDate);
		holidayRuleInf = holidayRuleService.getEntity(holidayRuleInf);
		rm.setObj(holidayRuleInf);
		return rm;
	}
}
