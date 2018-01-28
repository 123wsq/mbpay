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

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.AppService;
import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpapp.service.MessageService;
import com.tangdi.production.mpaychl.base.service.SMSService;
import com.tangdi.production.mpaychl.base.service.TermService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.tdbase.context.SpringContext;


/**
 * 
 * 测试专用类
 * @author zhengqiang
 *
 */
@Controller
public class TestController {
	private static Logger log = LoggerFactory.getLogger(TestController.class);
	@Autowired
	MessageService service;
	@Autowired
	AppTranService  appTranService;
	@Autowired
	private TranFrontService tranFrontservice;
	@RequestMapping(value="TS0001")
	@ResponseBody
	public String test(HttpServletRequest request){
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("Test开始...");
		log.info("请求数据为：{}",msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
		ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("custMobile", "18062020930");
			map.put("smsType", MsgST.SMS_TYPE_REGIST);
		//	service.sendsms(map);
			map.put("track", pmap.get("track"));
			map.put("icdata", pmap.get("icdata"));
			map.put("random", pmap.get("random"));
			map.put("crdnum", pmap.get("crdnum"));
			map.put("mediaType", pmap.get("mediaType"));
			
			map.put("terminalLMK", pmap.get("terminalLMK"));
			TermService tservice = SpringContext.getBean("BBPOSAudioTerminalServiceImpl", TermService.class);
			String[] track = tservice.decryptTrack(pmap);
		//	appTranService.termsign(pmap);
		//	appTranService.payment(pmap);
			pmap.put("track2", track[0]);
			tranFrontservice.pay(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "测试成功!");
			rspmessage.addDataAll(rspMap);
		}catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("测试信息完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	@RequestMapping(value="TS0002")
	@ResponseBody
	public String test2(HttpServletRequest request){
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("Test开始...");
		log.info("请求数据为：{}",msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
	    RspMsg rspmessage=new RspMsg();
		try {
			
			service.sendsms(null);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "测试成功!");
			rspmessage.addDataAll(rspMap);
		}catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("测试信息完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
}
