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

import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.RspMsg;


/**
 * 
 * <b>POSP交易处理</b> 扫码通知
 * @author chenlibo
 *
 */
@Controller
public class POSPTranController {
	private static Logger log = LoggerFactory.getLogger(POSPTranController.class);
	
	@Autowired
	private AppTranService service;
	
	/**
	 * <b>扫码通知交易</b> 交易代码：TZ0001
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "TZ0001")
	@ResponseBody
	public String smResultNotify(HttpServletRequest request) {
		Map<String,String[]> pmap =request.getParameterMap();
		log.info("扫码通知交易开始..." );
		log.info("请求数据为：{}" ,pmap);
		RspMsg rspmessage=new RspMsg();
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("prdordNo", pmap.get("orderId")[0]);
			param.put("tradeStatus", pmap.get("tradeStatus")[0]);
			param.put("randomKey", pmap.get("randomKey")[0]);
			param.put("payChl", pmap.get("payType")[0]);
			param.put("payTime", pmap.get("payTime")[0]);
			param.put("payAmt", pmap.get("payAmt")[0]);
			
			service.wxsmResultHandler(param);
			
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "扫码通知返回成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("扫码通知交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}

	
}
