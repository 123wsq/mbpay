package com.tangdi.production.mpapp.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpapp.service.AppImageService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.RspMsg;


/**
 * 
 * 首页轮播图获取
 * @author huchunyuan
 *
 */
@Controller
public class AppImageInfController {
	private static Logger log = LoggerFactory.getLogger(AppImageInfController.class);
	@Autowired
	AppImageService appImageInfService;
	
	@RequestMapping(value="SY0010")
	@ResponseBody
	public String dynamicImg(HttpServletRequest request){
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("首页轮播图获取...");
		log.info("请求数据为：{}",msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
	    List<Map<String,Object>> appImageInfList = new ArrayList<Map<String,Object>>();
		RspMsg rspmessage=new RspMsg();
		try {
			appImageInfList = appImageInfService.selectList(); 
			rspMap.put("imgList", appImageInfList);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "获取首页轮播图成功!");
			rspmessage.addDataAll(rspMap);
		}catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("获取首页轮播图完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
}
