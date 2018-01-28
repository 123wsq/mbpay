package com.tangdi.production.mpcoop.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpcoop.bean.CooporgInf;
import com.tangdi.production.mpcoop.service.CooporgService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpcoop/")
public class CooporgController {
	private static final Logger log = LoggerFactory
			.getLogger(CooporgController.class);

	@Autowired
	private CooporgService cooporgService;
	

	/**
	 * 合作机构管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "coopManage")
	public String cooporgManageView(){
		return "mpcoop/cooporg/coopManage";
	} 
	
	/**
	 *
	 * 合作机构信息列表
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "coopManage/query")
	@ResponseBody
	public ReturnMsg queryCoopList(@ModelAttribute("CooporgInf") CooporgInf coop) throws Exception {
		Integer totalRows = cooporgService.getCount(coop);
		List<CooporgInf> menuList = cooporgService.getListPage(coop);
		return new ReturnMsg(totalRows, menuList);
	}
	
	/**
	 * 合作机构添加界面
	 * @return
	 */
	@RequestMapping(value = "coopManage/addView")
	public String addView() {
		return "mpcoop/cooporg/coopAdd";
	}

	/**
	 * 添加合作机构
	 * @param coop
	 * @return
	 */
	@RequestMapping(value = "coopAdd/add")
	@ResponseBody
	public ReturnMsg addCoop(@ModelAttribute("CooporgInf") CooporgInf coop) {
		ReturnMsg rm = new ReturnMsg();
		try {
			int count = cooporgService.identifyId(coop);
			int countn = cooporgService.findName(coop);
			if (count > 0) {
				rm.setMsg("203", "合作机构添加失败，合作机构["+ coop.getCooporgNo() + "]已存在！");
				return rm;
			}
			if (countn > 0) {
				rm.setMsg("203", "合作机构添加失败，合作机构["+ coop.getCoopname() + "]已存在！");
				return rm;
			}
			if (cooporgService.addEntity(coop) > 0)
				rm.setMsg("200", "合作机构添加成功！编号:" + coop.getCooporgNo());
			else
				rm.setMsg("201", "合作机构添加失败！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "合作机构添加异常！");
		}
		return rm;
	}
	
	/**
	 * 删除/批量删除合作机构
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "coopManage/delete")
	@ResponseBody
	public ReturnMsg removeCoop(@RequestParam("cooporgNo") String ids) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		try {
			String[] cooporgNos = ids.split(",");
			for (int i = 0; i < cooporgNos.length; i++) {
				CooporgInf entity = new CooporgInf();
				entity.setCooporgNo(cooporgNos[i].replace("'", ""));
				cooporgService.removeEntity(entity);
			}
			msg.setMsg("200", "合作机构删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 进入修改合作机构信息界面
	 * ReturnMsg
	 * @return
	 */
	@RequestMapping(value = "coopManage/editeView")
	public String editView() {
		return "mpcoop/cooporg/coopEdit";
	}
	/**
	 * 根据合作机构编号查询
	 * @param 
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "coopManage/queryCoopById")
	@ResponseBody
	public ReturnMsg query(@RequestParam("cooporgNo") String ids) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		CooporgInf coop = new CooporgInf();
		coop.setCooporgNo(ids);
		coop = cooporgService.getEntity(coop);
		rm.setObj(coop);
		return rm;
	}
	
	/**
	 * 根据合作机构编号查询对应状态
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "coopManage/queryCoopTypeById")
	@ResponseBody
	public ReturnMsg queryCoopType(HttpServletRequest request, HttpSession session) throws Exception {
		String cooporgNo = ServletRequestUtils.getStringParameter(request, "cooporgNo", "");
		log.debug("CooporgController queryCoopTypeById:   CooporgNo:" + cooporgNo);
		ReturnMsg rm = new ReturnMsg();
		CooporgInf coop = new CooporgInf();
		coop.setCooporgNo(cooporgNo);
		rm.setObj(cooporgService.selectTypeEntity(coop));
		return rm;
	}

	/**
	 * 更新合作机构信息
	 * @param coop
	 * @return
	 */
	@RequestMapping(value = "coopEdit/edit")
	@ResponseBody
	public ReturnMsg editCoop(@ModelAttribute("CooporgInf") CooporgInf coop) {
		ReturnMsg rm = new ReturnMsg();
		try {
			cooporgService.modifyEntity(coop);
			rm.setMsg("200", "合作机构更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "合作机构更新失败");
		}
		return rm;
	}

	
	/**
	 * 生成合作机构编号下拉框
	 * 
	 */
	@RequestMapping(value = "selectoption/cooporgNo")
	@ResponseBody
	public ReturnMsg cooporgNo(HttpServletRequest request, HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		try {
			msg.setObj(cooporgService.querySelectOptionCooporgNo());
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 生成省份下拉框
	 * 
	 */
	@RequestMapping(value = "selectoption/provinceID")
	@ResponseBody
	public ReturnMsg provinceID(HttpServletRequest request, HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		try {
			msg.setObj(cooporgService.querySelectOptionProvinceID());
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
}
