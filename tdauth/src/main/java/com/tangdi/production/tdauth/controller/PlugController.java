package com.tangdi.production.tdauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.tdauth.service.PlugService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 系统插件(公共模块使用)数据获取
 * 
 * @author xiejinzhhong
 * 
 */
@Controller
@RequestMapping("/plug/")
public class PlugController {

	private static Logger log = LoggerFactory.getLogger(PlugController.class);
	@Autowired
	public PlugService plugService;
	
	@RequestMapping(value = "getProvince")
	public @ResponseBody
	ReturnMsg getProvince(@RequestParam(value = "provId",required = false) String provId, @RequestParam(value = "cityId",required = false) String cityId) {
		ReturnMsg rm = new ReturnMsg();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("provId", provId);
		paramMap.put("cityId", cityId);
		List<Map<String, Object>> provList = new ArrayList<Map<String, Object>>();
		try {
			provList = plugService.getProvince(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
				log.error("查询省份列表失败：" + e.getMessage());
				return rm.setFail("查询省份列表失败:" + e.getMessage());
		}
		log.info("查询省市列表成功");
		rm.setObj(provList);
		return rm.setSuccess("查询省市列表成功！");
	}
	
}
