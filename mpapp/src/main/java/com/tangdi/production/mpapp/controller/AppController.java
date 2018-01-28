package com.tangdi.production.mpapp.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpapp.service.AppService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;


/**
 * 
 * app信息获取
 * @author huchunyuan
 *
 */
@Controller
public class AppController {
	private static Logger log = LoggerFactory.getLogger(AppController.class);
	@Autowired
	AppService appInfService;
	
	@RequestMapping(value="SY0009")
	@ResponseBody
	public String checkVersion(HttpServletRequest request){
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("app版本信息获取...");
		log.info("请求数据为：{}",msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
		ReqMsg reqmessage=null;
	    RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			rspMap = appInfService.selectEntity(pmap); 
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "获取APP信息成功!");
			rspmessage.addDataAll(rspMap);
		}catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("获取APP信息完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
}
