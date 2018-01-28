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

import com.tangdi.production.mprcs.service.CustLevelService;
import com.tangdi.production.mprcs.bean.CustLevelInf;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 商户等级管理控制器
 * @author zhengqiang
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mprcs/")
public class CustLevelController {
	private static final Logger log = LoggerFactory
			.getLogger(CustLevelController.class);

	@Autowired
	private CustLevelService custLevelService;
	

	/**
	 * 商户等级管理跳转页面
	 * @return String
	 */
	@RequestMapping(value = "custLevelManage")
	public String custLevelManageView(){
		return "mprcs/custLevel/custLevelManage";
	} 
	
	/**
	 * 商户等级信息列表
	 * @param custLevelInf
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "custLevelManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("custLevelInf") CustLevelInf custLevelInf){
		int total =0;
		List<CustLevelInf> list = null;
		try {
			log.debug("查询参数:{}",custLevelInf.debug());
			total = custLevelService.getCount(custLevelInf);
			list  = custLevelService.getListPage(custLevelInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 商户等级新增跳转页面
	 * @return
	 */
	@RequestMapping(value = "custLevelManage/addView")
	public String addView() {
		return "mprcs/custLevel/custLevelAdd";
	}
    /**
     * 商户等级新增控制
     * @param custLevelInf
     * @return
     */
	@RequestMapping(value = "custLevelAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("CustLevelInf") CustLevelInf custLevelInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			if(custLevelService.getEntity(custLevelInf) == null){
				rt = custLevelService.addEntity(custLevelInf);
				if (rt > 0){
					rm.setMsg("200", "商户等级添加成功.");
				}else{
					rm.setMsg("201", "商户等级添加失败.");
				}
			}else{
				rm.setMsg("203", "商户等级编号已存在，请重新填写。");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "商户等级添加异常！");
		}
		return rm;
	}
	/**
	 * 商户等级删除控制
	 * @param custLevelInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custLevelManage/delete")
	@ResponseBody
	public ReturnMsg remove(@ModelAttribute("CustLevelInf") CustLevelInf custLevelInf) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = custLevelService.removeEntity(custLevelInf);
			if (rt > 0){
				msg.setMsg("200", "商户等级删除成功.");
			}else{
				msg.setMsg("201", "商户等级删除失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "商户等级删除异常.");
		}
		return msg;
	}
	
	/**
	 * 商户等级修改跳转页面
	 * @return String
	 */
	@RequestMapping(value = "custLevelManage/editView")
	public String editView() {
		return "mprcs/custLevel/custLevelEdit";
	}
	/**
	 * 查询单个商户等级信息
	 * @param custLevelInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "custLevelManage/queryCustLevelByNo")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("CustLevelInf") CustLevelInf custLevelInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		CustLevelInf entity = custLevelService.getEntity(custLevelInf);
		rm.setObj(entity);
		return rm;
	}

	/**
     * 商户等级修改控制器
     * @param custLevelInf
     * @return
     */

	@RequestMapping(value = "custLevelEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("CustLevelInf") CustLevelInf custLevelInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = custLevelService.modifyEntity(custLevelInf);
			if (rt > 0){
				msg.setMsg("200", "商户等级更新成功.");
			}else{
				msg.setMsg("201", "商户等级更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "商户等级更新失败");
		}
		return msg;
	}

	/**
	 * 生成下拉框
	 * @return
	 */
	@RequestMapping(value = "selectoption/custLevel")
	@ResponseBody
	public ReturnMsg query(HttpSession session){
	   ReturnMsg msg = new ReturnMsg();
		
		try {
		
			msg.setObj(custLevelService.querySelectOption());
				
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			msg.setMsg("202", e.getMessage());
		}
		
		return msg;
	} 
	
	
}
