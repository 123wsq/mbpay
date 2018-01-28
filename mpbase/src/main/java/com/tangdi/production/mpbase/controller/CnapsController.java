package com.tangdi.production.mpbase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.CnapsInf;
import com.tangdi.production.mpbase.service.CnapsService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 联行号
 * 
 * @author limiao
 *
 */
@Controller
@RequestMapping("/mpbase/")
public class CnapsController {
	private static Logger log = LoggerFactory.getLogger(CnapsController.class);
	@Autowired
	private CnapsService cnapsService;

	/**
	 * 跳转 查询列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "cnapsListView")
	public String cnapsListView() {
		return "mpbase/cnaps/cnapsManage";
	}

	/**
	 * 跳转 添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addCnapsView")
	public String addCnapsView() {
		return "mpbase/cnaps/cnapsAdd";
	}

	/**
	 * 跳转 编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "eidtCnapsView")
	public String eidtCnapsView() {
		return "mpbase/cnaps/cnapsEidt";
	}

	/**
	 * 添加
	 * 
	 * @param cnapsInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addCnaps")
	@ResponseBody
	public ReturnMsg addCnaps(@ModelAttribute("CnapsInf") CnapsInf cnapsInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			Integer rt=cnapsService.validateCnapsCode(cnapsInf);
			if(rt>0){
				rm.setMsg("201", "添加失败，联行号已经存在，请重新添加");
				return rm;
			}
			cnapsService.addEntity(cnapsInf);
			rm.setMsg("200", "添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("201", "添加失败");
		}
		return rm;
	}

	/**
	 * 编辑
	 * 
	 * @param cnapsInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "eidtCnaps")
	@ResponseBody
	public ReturnMsg eidtCnaps(@ModelAttribute("CnapsInf") CnapsInf cnapsInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			cnapsService.modifyEntity(cnapsInf);
			rm.setMsg("200", "修改成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("201", "修改失败");
		}
		return rm;
	}

	/**
	 * 删除
	 * 
	 * @param cnapsInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "removeCnaps")
	@ResponseBody
	public ReturnMsg removeCnaps(@ModelAttribute("CnapsInf") CnapsInf cnapsInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			cnapsService.removeEntity(cnapsInf);
			rm.setMsg("200", "删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("201", "删除失败");
		}
		return rm;
	}

	/**
	 * 根据 id 查询
	 * 
	 * @param cnapsInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryCnapsById")
	@ResponseBody
	public ReturnMsg queryCnapsById(@ModelAttribute("CnapsInf") CnapsInf cnapsInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		rm.setObj(cnapsService.getEntity(cnapsInf));
		return rm;
	}

	/**
	 * 查询列表
	 * 
	 * @param cnapsInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryCnapsList")
	@ResponseBody
	public ReturnMsg queryCnapsList(@ModelAttribute("CnapsInf") CnapsInf cnapsInf) throws Exception {
		Integer totalRows = cnapsService.getCount(cnapsInf);
		List<CnapsInf> cnapsInfList = cnapsService.getListPage(cnapsInf);
		return new ReturnMsg(totalRows, cnapsInfList);
	}

}
