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

import com.tangdi.production.mpapp.service.NoticeService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;

/**
 * 
 * 公告查询
 * 
 * @author huchunyuan
 *
 */
@Controller
public class NoticeController {
	private static Logger log = LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	NoticeService noticeService;

	@RequestMapping(value = "SY0011")
	@ResponseBody
	public String notice(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("公告查询开始...");
		log.info("请求数据为：{}", msg);
		Map<String, Object> rspMap = new HashMap<String, Object>();
		List<Map<String, Object>> noticeList = new ArrayList<Map<String, Object>>();
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> map = reqmessage.getBody();
			noticeList = noticeService.getNotice(map);
			rspMap.put("noticeList", noticeList);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "查询成功");
			rspmessage.addDataAll(rspMap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode());
			rspmessage.setDataV("RSPMSG", e.getMsg());
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Msg.getRspJson(rspmessage);
	}

	@RequestMapping(value = "SY0020")
	@ResponseBody
	public String noticeQuery(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("插入已阅公告...");
		log.info("请求数据为：{}", msg);
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> map = reqmessage.getBody();
			 noticeService.saveType(map);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "插入成功");
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode());
			rspmessage.setDataV("RSPMSG", e.getMsg());
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Msg.getRspJson(rspmessage);
	}
}
