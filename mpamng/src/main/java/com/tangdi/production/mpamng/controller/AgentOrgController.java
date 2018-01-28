package com.tangdi.production.mpamng.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.AgentOrgInf;
import com.tangdi.production.mpamng.service.AgentOrgService;
import com.tangdi.production.mpamng.service.AgentService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpamng/")
public class AgentOrgController {
	private static final Logger log = LoggerFactory.getLogger(AgentOrgController.class);

	@Autowired
	private AgentOrgService agentOrgService;

	@Autowired
	private AgentService agentService;
    // mpamng/agent/agentOrgListView.do
	@RequestMapping(value = "agent/agentOrgListView")
	public String agentOrgListView() {
		
		return "mpamng/agent/agentOrgManage";
	}

	@RequestMapping(value = "agent/editeAgentOrgView")
	public String editeAgentOrgView() {
		return "mpamng/agent/agentOrgEdit";
	}

	/**
	 *
	 * 
	 * 
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "agent/queryAgentOrgList")
	@ResponseBody
	public ReturnMsg queryAgentOrgList(@ModelAttribute("agentOrgInf") AgentOrgInf agentOrgInf) {
		ReturnMsg rm = new ReturnMsg();
		int total = 0;
		List<AgentOrgInf> list = null;
		try {
			log.debug("查询参数:{}", agentOrgInf.debug());
			total = agentOrgService.getCount(agentOrgInf);
			list = agentOrgService.getListPage(agentOrgInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		// return new ReturnMsg(total, list);
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.ALWAYS);
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			rm.setObj(mapper.writeValueAsString(list));
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

	@RequestMapping(value = "agent/saveAgentOrg")
	@ResponseBody
	public ReturnMsg saveAgentOrg(@RequestParam("agentId") String agentId, @RequestParam("agentOrgMer") String agentOrgMer) {
		ReturnMsg rm = new ReturnMsg();
		String[] agentOrgMers = agentOrgMer.split(",");
		try {
			AgentOrgInf agentOrgInf = null;
			for (int i = 0; i < agentOrgMers.length; i++) {
				agentOrgMer = agentOrgMers[i];
				
				agentOrgInf = new AgentOrgInf();
				agentOrgInf.setAgentId(agentId);
				agentOrgInf.setCooporgNo(agentOrgMer.substring(0, agentOrgMer.indexOf("#")));
				
				String merNoString = agentOrgMer.substring(agentOrgMer.indexOf("#")+1);
				agentOrgInf.setMerNo(merNoString.substring(0,merNoString.indexOf("#")));
				agentOrgService.addEntity(agentOrgInf);
			}
			rm.setMsg("200", "添加成功.");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm;
	}

	@RequestMapping(value = "agent/deleteAgentOrg")
	@ResponseBody
	public ReturnMsg deleteAgentOrg(@RequestParam("ageorgId") String ageorgId) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		try {
			String[] ageorgIds = ageorgId.split(",");
			AgentOrgInf agentOrgInf = null;
			for (int i = 0; i < ageorgIds.length; i++) {
				agentOrgInf = new AgentOrgInf();
				agentOrgInf.setAgeorgId(ageorgIds[i]);

				agentOrgService.removeEntity(agentOrgInf);
			}
			msg.setMsg("200", "删除成功.");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "删除异常.");
		}
		return msg;
	}

	@RequestMapping(value = "agent/queryAgentOrgById")
	@ResponseBody
	public ReturnMsg queryAgentOrgById(@ModelAttribute("AgentOrgInf") AgentOrgInf agentOrgInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();

		AgentInf agentInf = new AgentInf();
		agentInf.setAgentId(agentOrgInf.getAgentId());
		agentInf = agentService.getEntity(agentInf);

		rm.setObj(agentInf);
		return rm;
	}

	@RequestMapping(value = "agentOrg/agentOrgEdit")
	@ResponseBody
	public ReturnMsg agentOrgEdit(@ModelAttribute("AgentOrgInf") AgentOrgInf agentOrgInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			agentOrgService.modifyEntity(agentOrgInf);
			if (rt > 0) {
				msg.setMsg("200", "更新成功.");
			} else {
				msg.setMsg("201", "更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "更新失败");
		}
		return msg;
	}
}
