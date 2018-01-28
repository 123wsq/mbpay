package com.tangdi.production.mpbase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 卡BIN
 * 
 * @author limiao
 *
 */
@Controller
@RequestMapping("/mpbase/")
public class UnionfitController {
	private static Logger log = LoggerFactory.getLogger(UnionfitController.class);

	@Autowired
	private UnionfitService unionfitService;

	/**
	 * 跳转 查询列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "unionfitListView")
	public String unionfitListView() {
		return "mpbase/unionfit/unionfitManage";
	}

	/**
	 * 跳转 添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addUnionfitView")
	public String addUnionfitView() {
		return "mpbase/unionfit/unionfitAdd";
	}

	/**
	 * 跳转 编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "eidtUnionfitView")
	public String eidtUnionfitView() {
		return "mpbase/unionfit/unionfitEdit";
	}

	/**
	 * 添加
	 * 
	 * @param UnionfitInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addUnionfit")
	@ResponseBody
	public ReturnMsg addUnionfit(@ModelAttribute("UnionfitInf") UnionfitInf unionfitInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			unionfitService.addEntity(unionfitInf);
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
	 * @param UnionfitInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "eidtUnionfit")
	@ResponseBody
	public ReturnMsg eidtUnionfit(@ModelAttribute("UnionfitInf") UnionfitInf unionfitInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			unionfitService.modifyEntity(unionfitInf);
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
	 * @param UnionfitInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "removeUnionfit")
	@ResponseBody
	public ReturnMsg removeUnionfit(@ModelAttribute("UnionfitInf") UnionfitInf unionfitInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		try {
			unionfitService.removeEntity(unionfitInf);
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
	 * @param UnionfitInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryUnionfitById")
	@ResponseBody
	public ReturnMsg queryUnionfitById(@ModelAttribute("UnionfitInf") UnionfitInf unionfitInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		rm.setObj(unionfitService.getEntity(unionfitInf));
		return rm;
	}

	/**
	 * 查询列表
	 * 
	 * @param UnionfitInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryUnionfitList")
	@ResponseBody
	public ReturnMsg queryUnionfitList(@ModelAttribute("UnionfitInf") UnionfitInf unionfitInf) throws Exception {
		Integer totalRows = unionfitService.getCount(unionfitInf);
		List<UnionfitInf> UnionfitInfList = unionfitService.getListPage(unionfitInf);
		return new ReturnMsg(totalRows, UnionfitInfList);
	}
}
