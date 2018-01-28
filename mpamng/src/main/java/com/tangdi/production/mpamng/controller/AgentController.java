package com.tangdi.production.mpamng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpamng.bean.AgentFeeInfo;
import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.AgentProfitInf;
import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpamng.constants.CT;
import com.tangdi.production.mpamng.service.AgentService;
import com.tangdi.production.mpamng.service.TerminalService;
import com.tangdi.production.mpamng.util.PayRateUtil;
import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

@Controller
@RequestMapping("/mpamng/")
public class AgentController {
	private static Logger log = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	private AgentService agentService;

	@Autowired
	private UnionfitService unionfitService;
	@Autowired
	private TerminalService terminalService;
	
	@RequestMapping(value = "agent/agentListView")
	public String agentListView() {
		return "mpamng/agent/agentManage";
	}

	@RequestMapping(value = "agent/agentTempListView")
	public String agentTempListView() {
		return "mpamng/agent/agentTempManage";
	}

	@RequestMapping(value = "agent/agentAddView")
	public String agentAddView() {
		return "mpamng/agent/agentView";
	}

	@RequestMapping(value = "agent/agentView")
	public String agentView() {
		return "mpamng/agent/agentView";
	}

	@RequestMapping(value = "agent/agentTempView")
	public String agentTempView() {
		return "mpamng/agent/agentTempView";
	}

	@RequestMapping(value = "agent/agentEditView")
	public String agentEditView() {
		return "mpamng/agent/agentEditView";
	}
	
	@RequestMapping(value="agent/agentShareFl")
	public String agentShareView(){
		
		return "mpamng/agent/agentShareFeeRate";
	}
	

	@RequestMapping(value = "agent/queryAgentById")
	@ResponseBody
	public ReturnMsg queryAgentById(@RequestParam("agentId") String agentId, @RequestParam(value = "type", required = false) String type) throws Exception {
		log.debug("AgentController queryAgentById:   agentId:" + agentId + "    type:" + type);

		ReturnMsg rm = new ReturnMsg();
		if (type == null || "".equals(type)) {
			type = "add";
		}
		rm.put("type", type);
		AgentInf agent = new AgentInf();
		agent.setAgentId(agentId);
		agent = agentService.getEntity(agent);
		PayRateUtil.conAgentRate(agent);
		rm.setObj(agent);
		return rm;
	}

	@RequestMapping(value = "agent/queryAgentFeeById_KJ")
	@ResponseBody
	public ReturnMsg queryAgentFeeById_KJ(@RequestParam("agentId") String agentId, @RequestParam(value = "type", required = false) String type) throws Exception {
		log.debug("AgentController queryAgentById:   agentId:" + agentId + "    type:" + type);

		ReturnMsg rm = new ReturnMsg();
		if (type == null || "".equals(type)) {
			type = "add";
		}
		rm.put("type", type);
		AgentFeeInfo agentFee = new AgentFeeInfo();
		agentFee = agentService.selectAgentFeeGrade(agentId, "03");
		PayRateUtil.conAgentFeeRate(agentId, agentFee);
		rm.setObj(agentFee);
		return rm;
	}

	@RequestMapping(value = "agent/queryAgentFeeById_SM")
	@ResponseBody
	public ReturnMsg queryAgentFeeById_SM(@RequestParam("agentId") String agentId, @RequestParam(value = "type", required = false) String type) throws Exception {
		log.debug("AgentController queryAgentById:   agentId:" + agentId + "    type:" + type);

		ReturnMsg rm = new ReturnMsg();
		if (type == null || "".equals(type)) {
			type = "add";
		}
		rm.put("type", type);
		AgentFeeInfo agentFee = new AgentFeeInfo();
		agentFee = agentService.selectAgentFeeGrade(agentId, "04");
		PayRateUtil.conAgentFeeRate(agentId, agentFee);
		rm.setObj(agentFee);
		return rm;
	}
	
	@RequestMapping(value = "agent/queryAgentTempById")
	@ResponseBody
	public ReturnMsg queryAgentTempById(@RequestParam("agentId") String agentId, @RequestParam(value = "type", required = false) String type) throws Exception {
		log.debug("AgentController queryAgentTempById:   agentId:" + agentId);

		ReturnMsg rm = new ReturnMsg();
		if (type == null || "".equals(type)) {
			type = "audit";
		}
		rm.put("type", type);
		AgentInf agent = new AgentInf();
		agent.setAgentId(agentId);
		agent = agentService.getAgentTemp(agent);
		PayRateUtil.conAgentRate(agent,true);
		rm.setObj(agent);
		return rm;
	}
	
	
	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "agent/queryAgentAllocate")
	@ResponseBody
	public ReturnMsg queryAgentAllocate(@ModelAttribute("AgentInf") AgentInf agentInf, HttpSession session) throws Exception {
		ReturnMsg rm=new ReturnMsg();
		int total =0;
		
		List<AgentInf> menuList = null;
		UAI uai = UID.getUAI(session);
		// 判断是否为代理商角色
		if (!uai.getAgentId().equals(Constant.SYS_AGENT_ID)) {
			AgentInf param = new AgentInf();
			param.setAgentId(uai.getAgentId());
			AgentInf current = agentService.getEntity(param);
			agentInf.setAgentCode(current.getAgentCode());
		}
		try {
			log.debug("查询参数:{}",agentInf.debug());
			total = agentService.getCount(agentInf);
			menuList  = agentService.getListPage(agentInf);
			
			//处理元分转换
			if (menuList != null && menuList.size() > 0) {
				for (AgentInf agentInf1 : menuList) {
					PayRateUtil.conAgentRate(agentInf1);
				}
			}
			
			log.debug("总记录数:{},数据列表：{}",total,menuList);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		//return new ReturnMsg(total, menuList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.ALWAYS);
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			rm.setObj(mapper.writeValueAsString(menuList));
			rm.setRecords(total);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
		
	}

	
	

	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "agent/queryAgent")
	@ResponseBody
	public ReturnMsg queryAgent(@ModelAttribute("AgentInf") AgentInf agentInf, HttpSession session) throws Exception {
		UAI uai = UID.getUAI(session);
		// 判断是否为代理商角色
		if (!uai.getAgentId().equals(Constant.SYS_AGENT_ID)) {
			AgentInf param = new AgentInf();
			param.setAgentId(uai.getAgentId());
			AgentInf current = agentService.getEntity(param);
			agentInf.setAgentCode(current.getAgentCode());
		}
		int total =0;
		List<AgentInf> menuList = null;
		try {
			log.debug("查询参数:{}",agentInf.debug());
			total = agentService.getCount(agentInf); 
			menuList  = agentService.getListPage(agentInf);
			if (menuList != null && menuList.size() > 0) {
				for (AgentInf agentInf1 : menuList) {
					PayRateUtil.conAgentRate(agentInf1);
				}
			}
			log.debug("总记录数:{},数据列表：{}",total,menuList);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,menuList);
		
	}

	/**
	 * 代理商复核查询列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "agent/queryAgentTemp")
	@ResponseBody
	public HashMap<String, Object> queryAgentTemp(@ModelAttribute("AgentInf") AgentInf agentInf, HttpSession session) throws Exception {
		//agentInf.setAuditAgentid("0");

		HashMap<String, Object> result = new HashMap<String, Object>();

		UAI uai = UID.getUAI(session);
		// 判断是否为代理商角色
		if (!uai.getAgentId().equals(Constant.SYS_AGENT_ID)) {
			agentInf.setAuditAgentid(uai.getAgentId());
			agentInf.setAuditStatus("2");
		}
		Integer totalRows = agentService.countAgentTemp(agentInf);
		List<AgentInf> menuList = agentService.getAgentTempPage(agentInf);

		if (menuList != null && menuList.size() > 0) {
			for (AgentInf agentInf1 : menuList) {
				PayRateUtil.conAgentRate(agentInf1);
			}
		}

		result.put("records", totalRows);
		result.put("rows", menuList);
		return result;
	}

	@RequestMapping({ "agent/eidtAgent" })
	@ResponseBody
	public ReturnMsg eidtAgent(@ModelAttribute("agentInf") AgentInf agentInf, HttpServletRequest request, HttpSession session) throws UIDException {
		log.debug("AgentController eidtAgent:   agentId:" + agentInf.getAgentId() + "   logonName:" + agentInf.getLogonName());
		ReturnMsg rm = new ReturnMsg();
/*		Map<String, Object> map=new HashMap<String, Object>();
		map.put("taxNo", agentInf.getTaxNo());
		int rows = agentService.GetAgentCount(map);
		if(rows<=0){
			map.put("licNo", agentInf.getLicNo());
		}else{
			rm.setMsg("203", "税务登记号已存在！请重新添加");
			return rm;
		}
		rows = agentService.GetAgentCount(map);
		if(rows>0){
			rm.setMsg("203", "营业执照号已存在！请重新添加");
			return rm;
		}*/
		UAI uai = UID.getUAI(session);
		String uid = uai.getId().toString() + "";
		agentInf.setEditUserId(uid);
		agentInf.setEditDate(TdExpBasicFunctions.GETDATETIME());
		agentInf.setAuditAgentid("0");
		agentInf.setAuditStatus(CT.AUDIT_STATUS_APPLY);

		try {
			PayRateUtil.getAgentRate(request, agentInf);
			
			boolean feeflag = agentService.checkParentFeeInfo(agentInf);
			if(!feeflag){
				rm.setFail("代理商费率信息需要不小于上级代理商费率信息");
				return rm;
			}
			
			log.info("打印的总额是==========={}"+agentInf);
			//查询代理商的费率是否改变
			int checkAgentFee=agentService.checkAgentFee(agentInf);
			if(checkAgentFee==0){
			//计算代理商的左侧的和所需的参数
			Double rateTCas=0.0;
			if("".equals(agentInf.getRateTCas()) || agentInf.getRateTCas()==null){
				
			}else{
				rateTCas=Double.parseDouble(agentInf.getRateTCas());
			}
			Double rateLivelihood=0.0;
			if("".equals(agentInf.getRateLivelihood()) || agentInf.getRateLivelihood()==null){
				
			}else{
				rateLivelihood=Double.parseDouble(agentInf.getRateLivelihood());
			}
			Double rateGeneral=0.0;
			if("".equals(agentInf.getRateGeneral()) || agentInf.getRateGeneral()==null){
				
			}else{
				rateGeneral=Double.parseDouble(agentInf.getRateGeneral());
			}
			Double rateEntertain=0.0;
			if("".equals(agentInf.getRateEntertain()) || agentInf.getRateEntertain()==null){
				
			}else{
				rateEntertain=Double.parseDouble(agentInf.getRateEntertain());
			}
			Double rateGeneralTop=0.0;
			if("".equals(agentInf.getRateGeneralTop()) || agentInf.getRateGeneralTop()==null){
				
			}else{
				rateGeneralTop=Double.parseDouble(agentInf.getRateGeneralTop());
			}
			Double rateEntertainTop=0.0;
			if("".equals(agentInf.getRateEntertainTop()) || agentInf.getRateEntertainTop()==null){
				
			}else{
				rateEntertainTop=Double.parseDouble(agentInf.getRateEntertainTop());
			}
			
			Double agentRateSum=rateTCas+rateLivelihood+rateGeneral+rateEntertain+rateGeneralTop+rateEntertainTop;
			String agentSum=agentRateSum+"";
			log.info(agentSum+"代理和----------");
			agentInf.setTerminalSum(agentSum);
			//更新代理商左侧和比终端大的终端费率
			agentService.updateTerminalSum(agentInf);

			//修改代理商下的终端费率
			log.info(agentInf+"----------");
			//每一笔的费率（代理商的）
		
           String agentMaxCas="0";
           if("".equals(agentInf.getMaxTCas()) || agentInf.getMaxTCas()==null){
				
			}else{
				agentMaxCas=agentInf.getMaxTCas();
			}
           agentInf.setTerminalMaxTCas(agentMaxCas);
              //当代理商费率修改时右侧比终端大的要更新终端费率
			agentService.updateTerminal(agentInf);
					
			}
			rm = agentService.modifyAgentInf(agentInf, uai.getAgentId(), request);
			return rm;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改代理商信息失败：" + e.getMessage());
			rm.setFail("修改代理商信息失败：" + e.getMessage());
			return rm;
		}
	}

	/**
	 * 代理商开户 申请
	 * 
	 * @param agentInf
	 * @param request
	 * @param session
	 * @return
	 * @throws UIDException
	 */
	@RequestMapping({ "agent/addAgent" })
	@ResponseBody
	public ReturnMsg addAgent(@ModelAttribute("agentInf") AgentInf agentInf, HttpServletRequest request, HttpSession session) throws UIDException {
		log.debug("AgentController addAgent:   agentId:" + agentInf.getAgentId() + "   logonName:" + agentInf.getLogonName());
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map=new HashMap<String, Object>();
		if(agentInf.getTaxNo()!=null&&!"".equals(agentInf.getTaxNo())){
			map.put("taxNo", agentInf.getTaxNo());
			int rows = agentService.GetAgentCount(map);
			if(rows<=0){
				map.put("licNo", agentInf.getLicNo());
			}else{
				rm.setMsg("203", "税务登记号已存在！请重新添加");
				return rm;
			}
			
			rows = agentService.GetAgentCount(map);
			if(rows>0){
				rm.setMsg("203", "营业执照号已存在！请重新添加");
				return rm;
			}
		}
		
		
		try {
			PayRateUtil.getAgentRate(request, agentInf);
		} catch (Exception e) {
			log.error("代理商开户失败：" + e.getMessage());
			rm.setFail("代理商开户失败：" + e.getMessage());
			e.printStackTrace();
			return rm;
		}

		UAI uai = UID.getUAI(session);
		String uid = uai.getId() + "";
		agentInf.setCreateUserId(uid);
		agentInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
		agentInf.setEditUserId(uid);
		agentInf.setEditDate(TdExpBasicFunctions.GETDATETIME());
		agentInf.setAuditAgentid("0");
		agentInf.setAuditStatus(CT.AUDIT_STATUS_APPLY);
		UnionfitInf unionfitInf;
		try {
			unionfitInf = unionfitService.getCardInf(agentInf.getBankpayacno());
			if(unionfitInf == null){
				rm.setMsg("203", "代理商结算银行账号不存在，请重新添加！");
				return rm;
			}
			
			unionfitInf = unionfitService.getCardInf(agentInf.getBondCardNo());
			if(unionfitInf == null){
				rm.setMsg("203", "保证金银行账号不存在，请重新添加！");
				return rm;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			rm = agentService.addAgentInf(agentInf, uai.getAgentId(), request);
		} catch (Exception e) {
			log.error("代理商开户失败：" + e.getMessage());
			rm.setFail("代理商开户失败：" + e.getMessage());
			e.printStackTrace();
		}
		return rm;
	}

	/**
	 * 代理商开户 答复
	 * 
	 * @param agentInf
	 * @param request
	 * @param session
	 * @return
	 * @throws UIDException
	 */
	@RequestMapping({ "agent/agentTempReply" })
	@ResponseBody
	public ReturnMsg agentTempReply(@RequestParam("auditFailReason") String auditFailReason,@RequestParam("agentId") String agentId, @RequestParam("approved") String approved, @RequestParam("editUserId") String editUserId, HttpSession session) throws UIDException {
		log.debug("AgentController agentTempReply:   agentId:" + agentId + "   approved:" + approved + "  editUserId:" + editUserId);
		ReturnMsg rm = new ReturnMsg();
		UAI uai = UID.getUAI(session);
		
		
		if (editUserId == uai.getId()) {
			rm.setFail("申请人和审核人不能为同一人！");
			return rm;
		}
		try {
			rm = agentService.agentTempReply(agentId, approved, uai.getAgentId(), auditFailReason,uai.getUserId());
		} catch (Exception e) {
			log.error("代理商审核失败：" + e.getMessage());
			rm.setFail("代理商审核失败：" + e.getMessage());
			e.printStackTrace();
		}
		return rm;
	}

	/**
	 * 代理商密码重置
	 * 
	 * @param agentIds
	 * @return
	 */
	@RequestMapping({ "agent/agentPasswdReset" })
	@ResponseBody
	public ReturnMsg agentPasswdReset(@RequestParam("agentIds") String agentIds,@RequestParam("logonNames") String logonNames,HttpSession session) {
		log.debug("AgentController agentPasswdReset:   agentIds:" + agentIds);
		ReturnMsg rm = new ReturnMsg();
		UAI uaii = (UAI) session.getAttribute("UID");
		try {
			rm = agentService.agentPasswdReset(agentIds,logonNames,uaii.getUserId());
		} catch (Exception e) {
			log.error("代理商密码重置失败：" + e.getMessage());
			rm.setFail("代理商密码重置失败：" + e.getMessage());
			e.printStackTrace();
		}
		return rm;
	}

	/**
	 * 获取省份
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectoption/getProvince")
	@ResponseBody
	public ReturnMsg getProvince(@RequestParam(value = "id", required = false) String id) throws Exception {
		log.debug("AgentController getProvince:   id:" + id);

		ReturnMsg rm = new ReturnMsg();
		rm.setObj(agentService.getProvince(id));
		return rm;
	}

	/**
	 * 获取城市
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectoption/getCity")
	@ResponseBody
	public ReturnMsg getCity(@RequestParam(value = "id", required = false) String id) throws Exception {
		log.debug("AgentController getCity:   id:" + id);
		ReturnMsg rm = new ReturnMsg();
		rm.setObj(agentService.getCity(id));
		return rm;
	}

	@RequestMapping(value = "subagent/queryOem")
	@ResponseBody
	public ReturnMsg queryOem(HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = UID.getUAI(session);
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("agentId", uai.getAgentId());
			rm.setObj(agentService.queryOem(map));
		}
		return rm;
	}

	/**
	 * 代理商开户限制（费率） 1、在运营平台开户（一级代理商），代理商开户费率受平台参数中的费率限制（必须大于等于平台费率）
	 * 2、代理商平台开户（下级代理商），代理商开户费率受上级代理商的费率限制（必须大于等于平台费率）
	 * 注意：分润比只能小于等于上级，若为一级代理商，分润比默认为10
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "agent/outOfRate")
	@ResponseBody
	public ReturnMsg outOfRate(HttpSession session, @RequestParam(value = "rateType", required = false) String rateType, @RequestParam(value = "agentId", required = false) String agentId) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = UID.getUAI(session);
		Map<String, Object> map = new HashMap<String, Object>();
		// 运营平台
		if (Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			// 运营系统的 终端
			if ("term".equals(rateType)) {
				map.put("agentId", agentId);
				rm.setObj(agentService.outOfRateAgent(map));
			} else {
				AgentInf agentInf = new AgentInf();
				List<Map<String, Object>> list = agentService.outOfRatePlatform(map);
				map = new HashMap<String, Object>();
				for (Map<String, Object> map2 : list) {
					if (CT.RATE_LIVELIHOOD.equals(map2.get("paraCode"))) {
						agentInf.setRateLivelihood(String.valueOf(map2.get("paraVal")));
					}
					if (CT.RATE_GENERAL.equals(map2.get("paraCode"))) {
						agentInf.setRateGeneral(String.valueOf(map2.get("paraVal")));
					}
					if (CT.RATE_GENERAL_TOP.equals(map2.get("paraCode"))) {
						agentInf.setRateGeneralTop(String.valueOf(map2.get("paraVal")));
						agentInf.setRateGeneralMaximun(String.valueOf(map2.get("paraMaxmoney")));
					}
					if (CT.RATE_ENTERTAIN.equals(map2.get("paraCode"))) {
						agentInf.setRateEntertain(String.valueOf(map2.get("paraVal")));
					}
					if (CT.RATE_ENTERTAIN_TOP.equals(map2.get("paraCode"))) {
						agentInf.setRateEntertainTop(String.valueOf(map2.get("paraVal")));
						agentInf.setRateEntertainMaximun(String.valueOf(map2.get("paraMaxmoney")));
					}
				}
				agentInf.setProfitRatio("10");
				agentInf.setAgentName("");
				rm.setObj(agentInf);
			}
		} else {
			// 代理商系统
			map.put("agentId", uai.getAgentId());
			rm.setObj(agentService.outOfRateAgent(map));
		}
		return rm;
	}
	/**
	 * 查询分润阶梯
	 *
	 * @param request
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("agent/queryGrade")
	@ResponseBody
	public ReturnMsg getProfitGrade(HttpServletRequest request,@RequestParam("agentId") String agentId) throws Exception{
        if(agentId.isEmpty() || agentId.equals("")) {
            return null;	
        }
        
		List<AgentProfitInf> profitGrade = agentService.selectAgentProfitGrade(agentId, "02");
		return new ReturnMsg(profitGrade.size(),profitGrade);
	}

	@RequestMapping("agent/queryGrade_KJ")
	@ResponseBody
	public ReturnMsg getProfitGrade_KJ(HttpServletRequest request,@RequestParam("agentId") String agentId) throws Exception{
        if(agentId.isEmpty() || agentId.equals("")) {
            return null;	
        }
        
		List<AgentProfitInf> profitGrade = agentService.selectAgentProfitGrade(agentId, "03");
		return new ReturnMsg(profitGrade.size(),profitGrade);
	}

	@RequestMapping("agent/queryGrade_SM")
	@ResponseBody
	public ReturnMsg getProfitGrade_SM(HttpServletRequest request,@RequestParam("agentId") String agentId) throws Exception{
        if(agentId.isEmpty() || agentId.equals("")) {
            return null;	
        }
        
		List<AgentProfitInf> profitGrade = agentService.selectAgentProfitGrade(agentId, "04");
		return new ReturnMsg(profitGrade.size(),profitGrade);
	}

	@RequestMapping("agent/queryAgentFeeGrade_KJ")
	@ResponseBody
	public ReturnMsg getAgentFeeGrade_KJ(HttpServletRequest request,@RequestParam("agentId") String agentId) throws Exception{
        if(agentId.isEmpty() || agentId.equals("")) {
            return null;	
        }
        
		AgentFeeInfo feeGrade = agentService.selectAgentFeeGrade(agentId, "03");
		return new ReturnMsg(feeGrade);
	}

	@RequestMapping("agent/queryAgentFeeGrade_SM")
	@ResponseBody
	public ReturnMsg getAgentFeeGrade_SM(HttpServletRequest request,@RequestParam("agentId") String agentId) throws Exception{
        if(agentId.isEmpty() || agentId.equals("")) {
            return null;	
        }
        
		AgentFeeInfo feeGrade = agentService.selectAgentFeeGrade(agentId, "04");
		return new ReturnMsg(feeGrade);
	}
	
	/**
	 * 进入代理商审核意见页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "agent/agentAuditSubmit")
	public String agentAuditView() {
		return "mpamng/agent/agentAuditView";
	}
	
	/**
	 *
	 * 修改代理商登录状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "agent/modifyAgentStatus")
	@ResponseBody
	public ReturnMsg  modifyAgentStatus(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status, HttpSession session){
		ReturnMsg rm=new ReturnMsg();

		 UAI uai = (UAI) session.getAttribute("UID");
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("agentStatus", status);
			map.put("operId",uai.getUserId());
			agentService.modifyAgentStatus(map);
			rm.setMsg("200", "操作成功！");
		} catch (Exception e) {
			rm.setMsg("201", "操作失败！");
			log.error("停用或启用登录状态失败",e.getMessage());
		}
		return rm;
	}
	/**
	 *
	 * 修改代理商交易状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "agent/modifyFrozStateStatus")
	@ResponseBody
	public ReturnMsg  modifyFrozStateStatus(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status){
		ReturnMsg rm=new ReturnMsg();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("frozState", status);
			agentService.modifyAgentStatus(map);
			rm.setMsg("200", "操作成功！");
		} catch (Exception e) {
			rm.setMsg("201", "操作失败！");
			log.error("禁用或启用交易状态失败",e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 跳转到指定代理商费率同比调整页面
	 * @return
	 */
	@RequestMapping({ "agent/orgRateAdjust" })
	public String orgRateAdjust() {
		return "mpamng/agent/orgRateAdjustInf";
	}
	
	/**
	 * 指定代理商费率同比调整
	 * */
	@RequestMapping(value =  "agent/agentRateAdjust" )
	@ResponseBody
	public ReturnMsg agentRateAdjust(@ModelAttribute("agentInf") AgentInf agentInf,HttpServletRequest request, HttpSession session) throws UIDException {
		log.debug("AgentController agentTempReply:   agentId:" + agentInf.getAgentId() + "   logonName:" + agentInf.getLogonName());
		ReturnMsg rm = new ReturnMsg();
		String firAgentId = ServletRequestUtils.getStringParameter(request, "agentId", "");//指定代理商代理商编号
		String rateAjst = ServletRequestUtils.getStringParameter(request, "rateAjst", "");// 费率调整比例
		String secAgent = ServletRequestUtils.getStringParameter(request, "secAgent", "");// 一级代理商所属商户及下级代理商选择
		String thdAgent = ServletRequestUtils.getStringParameter(request, "thdAgent", "");// 二级代理商所属商户及下级代理商选择
		String fourCust = ServletRequestUtils.getStringParameter(request, "fourCust", "");// 三级代理商所属商户选择
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		//指定代理商编号
		map.put("fathAgentId", firAgentId);
		//指定代理商下级代理商编号
		List<AgentInf> secAgentId = new ArrayList<AgentInf>();
		if (!"".equals(secAgent)) {
			secAgentId.addAll(agentService.queryNextAgent(map));//指定代理商下属代理商编号
			map.put(firAgentId, secAgentId);
			log.info("fathAgentId :"+ firAgentId + "  secAgentId :" +secAgentId);
		}
		//
//		List<AgentInf> thdAgentId = new ArrayList<AgentInf>();
		if (!"".equals(thdAgent)) {
			for (int i = 0; i < secAgentId.size(); i++) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("fathAgentId", secAgentId.get(i).getAgentId());
//				thdAgentId.addAll(agentService.queryNextAgent(m));
				List<AgentInf> list = agentService.queryNextAgent(m);//二级代理商下属代理商
				map.put(secAgentId.get(i).getAgentId(), list);
				log.info("fathAgentId :"+ secAgentId.get(i).getAgentId() + "  thdAgentId :"+ list);
				
				if(!"".equals(fourCust)){
					map.put("_"+secAgentId.get(i).getAgentId(), list);
				}
				
			}
		}

		
		
		try {
			//修改代理商费率
			agentService.agentRateAdjust(map, rateAjst);
			
			//修改商户费率
			agentService.custRateAdjust(map, rateAjst);
				
			//修改终端费率
			agentService.termRateAdjust(map, rateAjst);
			
			rm.setMsg("200","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			rm.setMsg("201","操作失败");
		}
		
		return rm;
	}
	
	/**
	 * 跳转到部分代理商费率同比调整页面
	 * @return
	 */
	@RequestMapping({ "agent/orgRateAdjustAll" })
	public String orgRateAdjustAll() {
		return "mpamng/agent/orgRateAdjustAllInf";
	}
	
	/**
	 * 部分代理商费率同比调整
	 * @throws ServletRequestBindingException 
	 * */
	@RequestMapping(value =  "agent/agentRateAdjustAll" )
	@ResponseBody
	public ReturnMsg agentRateAdjustAll(@ModelAttribute("agentInf") AgentInf agentInf,HttpServletRequest request, HttpSession session) throws UIDException, ServletRequestBindingException {
		log.debug("AgentController agentTempReply:   agentId:" + agentInf.getAgentId() + "   logonName:" + agentInf.getLogonName());
		ReturnMsg rm = new ReturnMsg();
		String rateAjst = ServletRequestUtils.getStringParameter(request, "rateAjst", "");// 费率调整比例
		String secAgent = ServletRequestUtils.getStringParameter(request, "secAgent", "");// 一级代理商所属商户及下级代理商选择
		String thdAgent = ServletRequestUtils.getStringParameter(request, "thdAgent", "");// 二级代理商所属商户及下级代理商选择
		String fourCust = ServletRequestUtils.getStringParameter(request, "fourCust", "");// 三级代理商所属商户选择
		//1;
		
		
		String num1;
		try{
			if("".equals(secAgent)){
				throw new Exception("1");
			}
			if("".equals(thdAgent)) {
				throw new Exception("2");
			}
			if("".equals(fourCust)) {
				throw new Exception("3");
			}
			num1 = "4";
		}catch(Exception e){
			num1 = e.getMessage();
		}
		Integer num = Integer.parseInt(num1);
		try {
			//修改代理商费率
			agentService.agentRateAdjustAll(num, rateAjst);
			
			//修改商户费率
			agentService.custRateAdjustAll(num, rateAjst);
				
			//修改终端费率
			agentService.termRateAdjustAll(num, rateAjst);
			
			rm.setMsg("200","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			rm.setMsg("201","操作失败");
		}
		
		return rm;
	}
	
	/**
	 * 税务登记号 营业执照好 唯一性校验
	 * @param agentInf 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "agent/getValiTaxNo")
	@ResponseBody
	public int getValiTaxNo(@ModelAttribute("AgentInf") AgentInf agentInf, HttpServletRequest request){
		ReturnMsg rm=new ReturnMsg();
		int rows=0;
		String ma=	request.getParameter("TaxNo");
		String data=	request.getParameter("data");
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("taxNo", agentInf.getTaxNo());
			map.put("licNo", agentInf.getLicNo());
			rows = agentService.GetAgentCount(map);
			rm.setMsg("200","查询成功！");
		} catch (Exception e) {
			rm.setMsg("201", "系统异常！");
			log.error("系统异常",e.getMessage());
		}
		return rows;
	}
	
	/**
	 * 设置代理商的分享费率
	 * @param request
	 * @return
	 */
	@RequestMapping(value="agent/setAgentShareFee")
	@ResponseBody
	public ReturnMsg setShareFee(HttpServletRequest request){
		
		ReturnMsg msg = new ReturnMsg();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rateCode", request.getParameter("rateCode"));
		map.put("agentId", request.getParameter("agentId"));
		map.put("rateType", request.getParameter("rateType"));
		map.put("rateGeneralTop", request.getParameter("rateGeneralTop"));
//		map.put("rateGeneralMaximun", request.getParameter("rateGeneralMaximun"));
		
		log.debug("设置代理商的分享费率： 请求参数: {}", map);
		
		try {
			agentService.setAgentShareFee(map);
			msg.setSuccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setFail(e.getMessage());
		}
		
		return msg;
	}
	
	/**
	 * 获取代理商的分享费率
	 * @param request
	 * @return
	 */
	@RequestMapping(value="agent/getAgentShareFee")
	@ResponseBody
	public ReturnMsg getShareFee(HttpServletRequest request){
		
		ReturnMsg msg = new ReturnMsg();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rateCode", request.getParameter("rateCode"));
		map.put("agentId", request.getParameter("agentId"));
		map.put("rateType", request.getParameter("rateType"));
		log.debug("获取代理商的分享费率: 请求参数: {}", map);
		try {
			Map<String, Object> resultMap = agentService.getAgentShareFee(map);
			msg.setSuccess();
			msg.setObj(resultMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setFail(e.getMessage());
		}
		return msg;
	}
}
