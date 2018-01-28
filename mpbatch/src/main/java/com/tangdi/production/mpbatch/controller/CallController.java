package com.tangdi.production.mpbatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbatch.service.AgentDayShareAmtService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;

/**
 * 
 * @author limiao
 *
 *         重新调用 日分润、月分润、清算
 */
@Controller
@RequestMapping("/call/")
public class CallController {

	/**
	 * 线程
	 */
	@Autowired
	private AsynService asynService;

	@Autowired
	private AgentDayShareAmtService agentDayShareAmtService;

	// 简单的 校验密码s
	private final String pwd = "l";

	/**
	 * 手动通知 日分润 <br>
	 * 需要参数 date 年月日
	 * 
	 * @param p
	 * @param date
	 *            YYYYMMDD
	 * @return
	 */
	@RequestMapping("/d")
	@ResponseBody
	public ReturnMsg dAgentSharing(@RequestParam("p") String p, @RequestParam("date") String date) {
		ReturnMsg rm = new ReturnMsg();
		if (pwd.equals(p)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("date", date);
			asynService.dotran(TranCode.TRAN_CALL_DAYSHARE, param);
			rm.setMsg("200", "通知日分润数据操作成功，请稍后！");
		}
		return rm;
	}

	/**
	 * 手动通知 月分润 <br>
	 * 需要参数 date 年月
	 * 
	 * @param p
	 * @param date
	 *            YYYYMM
	 * @return
	 */
	@RequestMapping("/m")
	@ResponseBody
	public ReturnMsg mAgentSharing(@RequestParam("p") String p, @RequestParam("date") String date) {
		ReturnMsg rm = new ReturnMsg();
		if (pwd.equals(p)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("date", date);
			asynService.dotran(TranCode.TRAN_CALL_MONTHSHARE, param);
			rm.setMsg("200", "通知月分润数据操作成功，请稍后！");
		}
		return rm;
	}

	/**
	 * 手动通知 清算<br>
	 * 上一个工作日到今天
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping("/q")
	@ResponseBody
	public ReturnMsg qCust(@RequestParam("p") String p) {
		ReturnMsg rm = new ReturnMsg();
		if (pwd.equals(p)) {
			Map<String, Object> param = new HashMap<String, Object>();
			asynService.dotran(TranCode.TRAN_CALL_SETTLE, param);
			rm.setMsg("200", "通知清算数据操作成功，请稍后！");
		}
		return rm;
	}
}
