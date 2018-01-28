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

import com.tangdi.production.mpapp.service.AreaService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.MSGCODE;


/**
 * 
 * APP省市级联
 * @author zhengqiang
 *
 */
@Controller
public class AreaController {
	private static Logger log = LoggerFactory.getLogger(AreaController.class);
	

	@Autowired
	private AreaService areaService;
	
	/**
	 * 获取省份交易
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0012")
	@ResponseBody
	public String getProvince(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("获取省市信息交易开始..." );
		log.info("请求数据为：{}" ,msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
	    List<Map<String,Object>> pros = new ArrayList<Map<String,Object>>();
		RspMsg rspmessage=new RspMsg();
		try {
			pros = areaService.getProvince();
			rspMap.put("province", pros);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "获取省市信息成功!");
			rspmessage.addDataAll(rspMap);
		}catch (TranException e) {
			rspmessage.setDataV("RSPCOD","000012");//商户登陆名或密码不正确!
			rspmessage.setDataV("RSPMSG",MSGCODE.GET("000012"));
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			rspmessage.setDataV("RSPCOD","900000");
			rspmessage.setDataV("RSPMSG",MSGCODE.GET("900000"));
			log.error(e.getMessage(),e);
		}
		log.info("获取省市信息交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
}
