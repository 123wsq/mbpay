package com.tangdi.production.mpapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpapp.service.AppCnapsService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;

/***
 * 银行信息查询
 * @author sunhaining
 *
 */
@Controller
public class AppCnapsController {
	@Autowired
	private AppCnapsService appCnapsService;

	/***
	 * 获取省份城市中的所有银行名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "BU0001")
	@ResponseBody
	public String getBankName(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);

		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		Map<String, Object> rspMap = new HashMap<String, Object>();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();
			List<String> resultList = appCnapsService.getBankName(pmap);
			rspMap.put("bankCardList", resultList);
			
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "操作成功!");
			rspmessage.addDataAll(rspMap);
		} catch (Exception e) {
			rspmessage.setDataV("RSPCOD", e.getMessage()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMessage()); // 获取异常信息
		}
		return Msg.getRspJson(rspmessage);
	}

	/***
	 * 获取省份城市内的所有支行联行号信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "BU0002")
	@ResponseBody
	public String getBankList(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);

		ReqMsg reqmessage = null;
		RspMsg rspmessage = new RspMsg();
		Map<String, Object> rspMap = new HashMap<String, Object>();
		try {
			reqmessage = Msg.getReqMessage(msg);
			Map<String, Object> pmap = reqmessage.getBody();

			List<Map<String, Object>> resultList = appCnapsService.getBankList(pmap);
			rspMap.put("bankCardList", resultList);
			
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "操作成功!");
			rspmessage.addDataAll(rspMap);
		} catch (Exception e) {
			rspmessage.setDataV("RSPCOD", e.getMessage()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMessage()); // 获取异常信息
		}
		return Msg.getRspJson(rspmessage);
	}

}
