package com.tangdi.production.mpamng.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.CustShareInf;
import com.tangdi.production.mpamng.service.AgentService;
import com.tangdi.production.mpamng.service.ProfitSharingService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * @author limiao
 * @version 1.0
 */

@Controller
@RequestMapping("/mpamng/")
public class ProfitSharingController {
	private static final Logger log = LoggerFactory.getLogger(ProfitSharingController.class);

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private ProfitSharingService profitSharingService;
	
	/**
	 * 代理商日分润跳转
	 * */
	@RequestMapping({ "agent/profitSharingDayListView" })
	public String sharingDayListView() {
		return "mpamng/agent/profitSharingDayListManage";
	}
	
	/**
	 * 代理商日分润查询
	 * */
	@RequestMapping({ "agent/queryProfitDaySharing" })
	@ResponseBody
	public ReturnMsg queryProfitDaySharing(HttpServletRequest request, HttpSession session, CustShareInf agentProfitLogInf) throws Exception {
		Map<String, Object> map = ServletUtil.getParamMap(request);
		
		String startTime =(String) map.get("sTime");
		String endTime =(String) map.get("eTime");
		
		
		try{
			if(StringUtils.isNotEmpty(startTime)){
				map.put("startTime", DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(startTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			map.put("startTime","");
		}
		
		try{
			if(StringUtils.isNotEmpty(endTime)){
				map.put("endTime", DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(endTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			map.put("endTime","");
		}
		if(null !=agentProfitLogInf.getAgentName() && !"".equals(agentProfitLogInf.getAgentName())){
			map.put("agentName", agentProfitLogInf.getAgentName());
		}
		
		log.info("queryProfitDaySharing  请求参数:" + map);
//		if(map.get("agentDgr")==null){
//			map.put("agentDgr", "1");
//		}

		UAI uai = UID.getUAI(session);
		String cust_AgentId = uai.getAgentId();
		if (!Constant.SYS_AGENT_ID.equals(cust_AgentId)) {
			map.put("agentId", cust_AgentId);
		}
		
		List<Map<String, Object>> profitDaySharingList = profitSharingService.queryProfitDaySharing(map);
		Integer total = profitSharingService.getProfitDaySharingCount(map);
		log.debug("总记录数:{},数据列表：{}", total, profitDaySharingList.toString());
		return new ReturnMsg(total, profitDaySharingList);
	}
	
	/**
	 * 代理商日分润详细跳转
	 * */
	@RequestMapping({ "agent/queryProfitDaySharingDetailView" })
	public String queryProfitDaySharingDetailView() {
		return "mpamng/agent/profitDaySharingDetail";
	}
	
	/**
	 * 日分润 详细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "agent/queryProfitDaySharingDetail" })
	@ResponseBody
	public ReturnMsg queryProfitDaySharingDetail(HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map = ServletUtil.getParamMap(request);
		log.info("queryProfitDaySharingDetail  请求参数:" + map);
		List<Map<String, Object>> profitSharingList = profitSharingService.queryProfitSharingDetail(map);
		Integer total = profitSharingService.queryProfitSharingDetailCount(map);
		log.debug("总记录数:{},数据列表：{}", total, profitSharingList.toString());
		rm.setRecords(total);
		rm.setRows(profitSharingList);
		rm.setObj(profitSharingService.queryProfitSharingDetailAMT(map));
		return rm;
	}
	
	/**
	 * 代理商月分润跳转
	 * */
	@RequestMapping({ "agent/profitSharingMonthListView" })
	public String profitSharingMonthListView() {
		return "mpamng/agent/profitSharingMonthManage";
	}
	
	/**
	 * 代理商月份润查询
	 * queryProfitMonthSharing
	 * */
	@RequestMapping({ "agent/queryProfitMonthSharing" })
	@ResponseBody
	public ReturnMsg queryProfitMonthSharing(HttpServletRequest request, HttpSession session, CustShareInf agentProfitLogInf) throws Exception {
		Map<String, Object> map = ServletUtil.getParamMap(request);
		
		if(null !=agentProfitLogInf.getAgentName() && !"".equals(agentProfitLogInf.getAgentName())){
			map.put("agentName", agentProfitLogInf.getAgentName());
		}
		
//		if(map.get("agentDgr")==null){
//			map.put("agentDgr", "1");
//		}
		log.info("queryProfitMonthSharing  请求参数:" + map);

		UAI uai = UID.getUAI(session);
		String cust_AgentId = uai.getAgentId();
		if (!Constant.SYS_AGENT_ID.equals(cust_AgentId)) {
			map.put("agentId", cust_AgentId);
		}
		log.info("queryProfitMonthSharing  请求参数:" + map);
		
		String sTime = (String)map.get("sTime");
		String eTime =(String) map.get("eTime");
		try{
			if(StringUtils.isNotEmpty(sTime)){
				map.put("sTime", map.get("sTime")+"01");
			}
		}catch(Exception e){
			map.put("sTime","");
		}
		
		try{
			if(StringUtils.isNotEmpty(eTime)){
				map.put("eTime", map.get("eTime")+"01");
			}
		}catch(Exception e){
			map.put("eTime","");
		}
		
		List<Map<String, Object>> profitMonthSharingList = profitSharingService.queryProfitMonthSharing(map);
		Integer total = profitSharingService.getProfitMonthSharingCount(map);
		log.debug("总记录数:{},数据列表：{}", total, profitMonthSharingList.toString());
		return new ReturnMsg(total, profitMonthSharingList);
	}
	
	/**
	 * 月分润详细跳转
	 * 
	 * */
	@RequestMapping({ "agent/queryProfitMonthSharingDetailView" })
	public String queryProfitMonthSharingDetailView() {
		return "mpamng/agent/profitMonthSharingDetail";
	}
	
	/**
	 * 月分润 详细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "agent/queryProfitMonthSharingDetail" })
	@ResponseBody
	public ReturnMsg queryProfitMonthSharingDetail(HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map = ServletUtil.getParamMap(request);
		log.info("queryProfitDaySharingDetail  请求参数:" + map);
		List<Map<String, Object>> profitSharingList = profitSharingService.queryProfitDaySharingDetail(map);
		Integer total = profitSharingService.getProfitDaySharingDetailCount(map);
		log.debug("总记录数:{},数据列表：{}", total, profitSharingList.toString());
		rm.setRecords(total);
		rm.setRows(profitSharingList);
		rm.setObj(profitSharingService.queryProfitSharingDetailAMT(map));
		return rm;
	}
	
	/**
	 * 查询分润总金额
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "agent/queryProfitSharingPageAMT" })
	public ReturnMsg queryProfitSharingPageAMT(HttpServletRequest request, HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map = ServletUtil.getParamMap(request);
		log.info("queryProfitSharing  请求参数:" + map);

		UAI uai = UID.getUAI(session);
		String cust_AgentId = uai.getAgentId();
		if (!Constant.SYS_AGENT_ID.equals(cust_AgentId)) {
			map.put("fathAgentId", cust_AgentId);
		}
		Map<String, Object> profitSharing = profitSharingService.queryProfitSharingPageAMT(map);
		log.debug("数据列表：{}", profitSharing.toString());
		rm.setObj(profitSharing);
		return rm;
	}

}
