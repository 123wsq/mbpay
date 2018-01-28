package com.tangdi.production.mpapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpapp.service.MisTranService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.MSGCODE;


/**
 * 
 * <b>机构业务处理</b> 机构签到、订单消费、订单消费撤销
 * @author chenlibo
 *
 */
@Controller
public class MisTranController {
	private static Logger log = LoggerFactory.getLogger(MisTranController.class);
	
	@Autowired
	private AppTranService service;
	
	@Autowired
	private MisTranService mservice;
	
	
	/**
	 * <b>机构签到交易</b> 交易代码：SG9002
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SG9002")
	@ResponseBody
	public String sign(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("机构签到交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			rmap = service.termsign(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "机构签到成功");
	
			rspmessage.addDataAll(rmap);//
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("机构签到交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}

	
	/**
	 * <b>支付交易</b> 交易代码：PY2001
	 * 
	 * custMobile  手机号
	 * termNo   终端号
	 * pinblk  密码密文   
	 * track   磁道信息
	 * mediaType  介质类型
	 * rateType 费率类型
	 * period 有效期
	 * icdata IC卡数据域
	 * seqnum 序列号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "PY2001")
	@ResponseBody
	public String payment(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("支付交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			mservice.payment(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "支付成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("支付交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}

	/**
	 * <b>订单消费撤销交易</b> 交易代码：PY2002
	 * 
	 * custMobile  手机号
	 * prdordNo_old 原商品订单号
	 * termNo   终端号
	 * pinblk  密码密文   
	 * track   磁道信息
	 * mediaType  介质类型
	 * rateType 费率类型
	 * period 有效期
	 * icdata IC卡数据域
	 * seqnum 序列号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "PY2002")
	@ResponseBody
	public String dispayment(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("订单撤销交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			mservice.dispayment(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "订单撤销成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("订单撤销交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
}
