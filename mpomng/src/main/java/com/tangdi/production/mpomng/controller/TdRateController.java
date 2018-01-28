package com.tangdi.production.mpomng.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.bean.TdRateInf;
import com.tangdi.production.mpomng.service.TdRateService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class TdRateController {
	private static final Logger log = LoggerFactory
			.getLogger(TdRateController.class);

	@Autowired
	private TdRateService tdRateService;
	

	/**
	 * 平台费率管理跳转页面
	 * @return
	 */
	 
	@RequestMapping(value = "tdRateManage")
	public String tdRateManageView(){
		return "mpomng/tdrate/tdRateManage";
	} 
	
	/**
	 * 平台费率信息列表
	 * @param tdRateInf
	 * @return
	 */
	
	@RequestMapping(value = "tdRateManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("tdRateInf") TdRateInf tdRateInf){
		int total =0;
		List<TdRateInf> list = null;
		try {
			log.debug("查询参数:{}",tdRateInf.debug());
			total = tdRateService.getCount(tdRateInf);
			list  = tdRateService.getListPage(tdRateInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	@RequestMapping(value = "tdRateManage/addView")
	public String addView() {
		return "mpomng/tdrate/tdRateAdd";
	}

	@RequestMapping(value = "tdRateAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("TdRateInf") TdRateInf tdRateInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			if(tdRateService.getEntity(tdRateInf) == null){
				rt = tdRateService.addEntity(tdRateInf);
				if (rt > 0){
					rm.setMsg("200", "添加成功.");
				}else{
					rm.setMsg("201", "添加失败.");
				}	
			}else{
				rm.setMsg("203", "费率代码已存在，请重新编写。");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm;
	}
	
	@RequestMapping(value = "tdRateManage/delete")
	@ResponseBody
	public ReturnMsg remove(@ModelAttribute("TdRateInf") TdRateInf tdRateInf) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = tdRateService.removeEntity(tdRateInf);
			if (rt > 0){
				msg.setMsg("200", "删除成功.");
			}else{
				msg.setMsg("201", "删除失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "删除异常.");
		}
		return msg;
	}
	
	/**
	 * ReturnMsg
	 * @return
	 */
	@RequestMapping(value = "tdRateManage/editeView")
	public String editView() {
		return "mpomng/tdrate/tdRateEdit";
	}       
	/**
	 * 查询单个文件下载信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "tdRateManage/queryById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("TdRateInf") TdRateInf tdRateInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		TdRateInf entity = tdRateService.getEntity(tdRateInf);
		rm.setObj(entity);
		return rm;
	}

	@RequestMapping(value = "tdRateEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("TdRateInf") TdRateInf tdRateInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = tdRateService.modifyEntity(tdRateInf);
			if (rt > 0){
				msg.setMsg("200", "更新成功!");
			}else{
				msg.setMsg("201", "更新失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "更新失败");
		}
		return msg;
	}

	
	
	
}
