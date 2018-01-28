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

import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;

/**
 * 终端相关处理
 * 
 * @author shanbeiyi
 *
 */
@Controller
public class TerminalController {
	private static Logger log = LoggerFactory.getLogger(TerminalController.class);

	@Autowired
	private TerminalService terminalservice;

	/**
	 * 终端绑定[TE0001]
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TE0001")
	@ResponseBody
	public String termBind(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("终端绑定交易开始...");
		log.info("请求数据为：{}", msg);
		Map<String, Object> rmap = new HashMap<String, Object>();
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			terminalservice.binding(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "终端绑定成功!");
			rspmessage.addDataAll(rmap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("终端绑定交易完成.");
		return Msg.getRspJson(rspmessage);
	}

	/**
	 * 终端列表查询[TE0002]
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TE0002")
	@ResponseBody
	public String getTermList(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("商户绑定终端列表查询交易开始...");
		log.info("请求数据为：{}", msg);
		Map<String, Object> rspMap = new HashMap<String, Object>();
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			rspMap.put("termList", terminalservice.getlist(pmap));
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "终端列表查询成功!");
			rspmessage.addDataAll(rspMap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("商户绑定终端列表查询交易完成.");
		return Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 终端费率查询查询[TE0003]
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TE0003")
	@ResponseBody
	public String getTermRate(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("终端费率查询交易开始...");
		log.info("请求数据为：{}", msg);
		Map<String, Object> rspMap = new HashMap<String, Object>();
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			rspMap = terminalservice.getTermRate(pmap);
			
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "终端费率查询成功!");
			rspmessage.addDataAll(rspMap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("终端费率查询交易完成.");
		return Msg.getRspJson(rspmessage);
	}

}
