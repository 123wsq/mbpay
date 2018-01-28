package com.tangdi.production.mpbase.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.service.TermStepService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * 终端记录
 * 
 * @author limiao
 *
 */
@Controller
@RequestMapping("/mpbase/")
public class TermStepController {
	private static Logger log = LoggerFactory.getLogger(TermStepController.class);

	@Autowired
	private TermStepService termStepService;

	/**
	 * 跳转 查询列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "termStepListView")
	public String termStepListView() {
		return "mpbase/termStep/termStepManage";
	}

	/**
	 * 查询列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryTermStepList")
	@ResponseBody
	public ReturnMsg querytermStepList(HttpServletRequest request, HttpSession session) throws Exception {
		Map<String, Object> param = ServletUtil.getParamMap(request);
		log.info("查询的 终端号  {}", param);
		Integer totalRows = termStepService.countEntity(param);
		List<Map<String, Object>> termStepList = termStepService.selectList(param);
		return new ReturnMsg(totalRows, termStepList);
	}
}
