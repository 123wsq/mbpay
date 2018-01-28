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

import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpapp.service.TranOrderService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;

/**
 * 
 * 订单交易控制器
 * @author zhengqiang
 *
 */
@Controller
public class TranOrderController {
	private static Logger log = LoggerFactory.getLogger(TranOrderController.class);

	@Autowired
	private TranOrderService tranOrderService;

	@Autowired
	private TermPrdService termPrdService;

	/**
	 * 商品订单交易
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value = "OD0001")
	@ResponseBody
	public String prdOrder(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("APP订单交易开始...");
		log.info("请求数据为：{}", msg);
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			String prdordNo = termPrdService.createPrd(pmap);
			rspmessage.setDataV("prdordNo", prdordNo);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "订单创建成功!");
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			rspmessage.setDataV("RSPCOD", "900001");
			rspmessage.setDataV("RSPMSG", "处理异常!");
			log.error(e.getMessage(), e);
		}
		log.info("APP商品订单交易完成.");
		return Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 商品订单交易
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value = "OD2001")
	@ResponseBody
	public String misPrdOrder(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("创建机构订单交易开始...");
		log.info("请求数据为：{}", msg);
		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			String prdordNo = termPrdService.createPrd(pmap);
			rspmessage.setDataV("prdordNo", prdordNo);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "订单创建成功!");
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); 
			rspmessage.setDataV("RSPMSG",e.getMsg()); 
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			rspmessage.setDataV("RSPCOD", "900001");
			rspmessage.setDataV("RSPMSG", "处理异常!");
			log.error(e.getMessage(), e);
		}
		log.info("商品订单交易完成.");
		return Msg.getRspJson(rspmessage);
	}

	/**
	 * 交易记录查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TR0001")
	@ResponseBody
	public String getTranSerialList(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("交易记录查询开始...");
		log.info("请求数据为：{}", msg);
		Map<String, Object> rspMap = new HashMap<String, Object>();
		List<Map<String,Object>> tranList = new ArrayList<Map<String,Object>>();
		ReqMsg reqmessage=null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> map = reqmessage.getBody();
			tranList=tranOrderService.queryList(map);
			rspMap.put("tranList",tranList );
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "交易信息查询成功!");
			rspmessage.addDataAll(rspMap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("交易信息完成.");
		return Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 交易详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TR0002")
	@ResponseBody
	public String getTranDetail(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("交易详细查询开始...");
		log.info("请求数据为：{}", msg);
		Map<String,Object> tranDetail = new HashMap<String,Object>();
		ReqMsg reqmessage=null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> map = reqmessage.getBody();
			tranDetail=tranOrderService.queryDetail(map);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "交易详细查询成功!");
			rspmessage.addDataAll(tranDetail);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("交易详细查询完成.");
		return Msg.getRspJson(rspmessage);
	}

}
