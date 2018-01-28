package com.tangdi.production.mpomng.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.service.MerProfitRuleService;
import com.tangdi.production.mpomng.bean.MerProfitRuleInf;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 分润规则管理控制器
 * @author zhengqiang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/mpomng/")
public class MerProfitRuleController {
	private static final Logger log = LoggerFactory
			.getLogger(MerProfitRuleController.class);

	@Autowired
	private MerProfitRuleService merProfitRuleService;
	

	/**
	 * 分润规则管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "merProfitRuleManage")
	public String merProfitRuleManageView(){
		return "mpomng/merProfitRule/merProfitRuleManage";
	} 
	
	/**
	 *
	 * 分润规则管理信息列表
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "merProfitRuleManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("merProfitRuleInf") MerProfitRuleInf merProfitRuleInf){
		int total =0;
		List<MerProfitRuleInf> list = null;
		try {
			log.debug("查询参数:{}",merProfitRuleInf.debug());
			total = merProfitRuleService.getCount(merProfitRuleInf);
			list  = merProfitRuleService.getListPage(merProfitRuleInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	                       
	@RequestMapping(value = "merProfitRuleManage/addView")
	public String addView() {
		return "mpomng/merProfitRule/merProfitRuleAdd";
	}
    /**
     * 分润规则添加
     * @param merProfitRuleInf
     * @return
     */
	@RequestMapping(value = "merProfitRuleAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("MerProfitRuleInf") MerProfitRuleInf merProfitRuleInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			if(merProfitRuleService.getEntity(merProfitRuleInf) == null){
				rt = merProfitRuleService.addEntity(merProfitRuleInf);
				if (rt > 0){
					rm.setMsg("200", "分润规则添加成功.");
				}else{
					rm.setMsg("201", "分润规则添加失败.");
				}
			}else{
				rm.setMsg("203", "分润规则已存在，请重新填写。");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "文件信息添加异常！");
		}
		return rm;
	}
	
	/**
	 * 分润规则修改页面跳转
	 * ReturnMsg
	 * @return
	 */
	@RequestMapping(value = "merProfitRuleManage/editView")
	public String editView() {
		return "mpomng/merProfitRule/merProfitRuleEdit";
	}
	/**
	 * 查询单个分润规则信息
	 * @param merProfitRuleInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "merProfitRuleManage/queryById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("MerProfitRuleInf") MerProfitRuleInf merProfitRuleInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		MerProfitRuleInf entity = merProfitRuleService.getEntity(merProfitRuleInf);
		rm.setObj(entity);
		return rm;
	}
	/**
	 * 更新分润规则
	 * @param merProfitRuleInf
	 * @return
	 */
	@RequestMapping(value = "merProfitRuleEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("MerProfitRuleInf") MerProfitRuleInf merProfitRuleInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = merProfitRuleService.modifyEntity(merProfitRuleInf);
			if (rt > 0){
				msg.setMsg("200", "分润规则更新成功.");
			}else{
				msg.setMsg("201", "分润规则更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "分润规则更新失败");
		}
		return msg;
	}
	
	
}
