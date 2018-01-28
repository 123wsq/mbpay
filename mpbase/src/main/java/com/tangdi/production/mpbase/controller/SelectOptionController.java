package com.tangdi.production.mpbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.service.SelectOptionService;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.service.DictService;

/**
 * 生成下拉框
 * 
 * @author zhengqiang
 *
 */

@Controller
@RequestMapping("/mpbase/")
public class SelectOptionController {
	private static Logger log = LoggerFactory.getLogger(SelectOptionController.class);

	@Autowired
	private SelectOptionService selectOptionService;

	@Autowired
	private DictService dictService;

	/**
	 * 从码表中查询select option
	 * 
	 * @return
	 */
	@RequestMapping(value = "selectoption/query")
	@ResponseBody
	public ReturnMsg query(@RequestParam("did") String did, @RequestParam("value") String value, @RequestParam("type") String type) {
		ReturnMsg msg = new ReturnMsg();

		try {
			msg.setObj(selectOptionService.querySelectOption(did, value, type));

			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}

		return msg;
	}

	/**
	 * 从码表中查询select option
	 * 
	 * @return
	 */
	@RequestMapping(value = "dict/query")
	@ResponseBody
	public ReturnMsg queryDict() {
		ReturnMsg msg = new ReturnMsg();

		try {
			String dict = JUtil.toJsonString(selectOptionService.queryDCIT());
			msg.setObj(dict);
			log.debug(dict);
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

	/**
	 * 更新 数据字典
	 * 
	 * @return
	 */
	@RequestMapping(value = "dict/up")
	@ResponseBody
	public ReturnMsg upDict() {
		ReturnMsg msg = new ReturnMsg();

		try {
			dictService.getList();

			String dict = JUtil.toJsonString(selectOptionService.queryDCIT());
			msg.setObj(dict);
			log.debug(dict);
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

}
