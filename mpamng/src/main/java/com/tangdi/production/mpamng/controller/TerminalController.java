package com.tangdi.production.mpamng.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpamng.constants.CT;
import com.tangdi.production.mpamng.service.AgentService;
import com.tangdi.production.mpamng.service.TerminalCompanyService;
import com.tangdi.production.mpamng.service.TerminalService;
import com.tangdi.production.mpamng.util.PayRateUtil;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;

@Controller
@RequestMapping("/mpamng/")
public class TerminalController {
	private static Logger log = LoggerFactory.getLogger(TerminalCompanyController.class);
	@Autowired
	private TerminalService terminalService;

	@Autowired
	private TerminalCompanyService terminalCompanyService;

	@Autowired
	private AgentService agentService;


	// 异步线程处理
	@Autowired
	private AsynService asynService;

	@RequestMapping({ "term/termListView" })
	public String termListView() {
		return "mpamng/term/termManage";
	}

	@RequestMapping({ "term/termView" })
	public String termView() {
		return "mpamng/term/termView";
	}
	
	@RequestMapping({ "term/termManualView" })
	public String termManualView() {
		return "mpamng/term/termManualView";
	}

	@RequestMapping({ "term/termAllocateView" })
	public String termAllocateView() {
		return "mpamng/term/termAllocate";
	}

	@RequestMapping({ "term/termFeeListView" })
	public String termFeeListView() {
		return "mpamng/term/termFeeManage";
	}

	@RequestMapping({ "term/termFeeView" })
	public String termFeeView() {
		return "mpamng/term/termFeeView";
	}

	@RequestMapping({ "term/agentListView" })
	public String agentListView() {
		return "mpamng/term/agentListView";
	}

	/**
	 * 终端入库
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/eidtTerm" })
	@ResponseBody
	public ReturnMsg eidtTerm(@ModelAttribute("TerminalInf") TerminalInf terminal, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		terminal.setCreateUserId(uai.getUserId());
		terminal.setCreateDate(TdExpBasicFunctions.GETDATETIME());
		try {
			HashMap<String, Object> con = new HashMap<String, Object>();
			con.put("entityParam", terminal);
			con.putAll(terminal.toMap());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("param", con);
			map.put("uid", UID.get(session));
			map.put("service", "terminalServiceImpl");
			// 调用异步线程处理
			asynService.dotran("800001", map);
			rm.setMsg("200", "终端入库提交完成,请到下载页面下载终端密钥文件！");
		} catch (Exception e) {
			rm.setMsg("201", "终端入库提交失败！");
			log.error(e.getMessage(), e);
		}
		return rm;
	}
	
	/**
	 * 手工终端入库
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping({ "term/eidtManualTerm" })
	@ResponseBody
	public ReturnMsg eidtManualTerm(@RequestParam("uploadFileId") String uploadFileId, @ModelAttribute("TerminalInf") TerminalInf terminal, 
			HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		terminal.setCreateUserId(uai.getUserId());
		terminal.setCreateDate(TdExpBasicFunctions.GETDATETIME());
		log.info("获取选择的excel文件ID:"+uploadFileId);
		String fileName = terminalService.getFjpath(uploadFileId);
		log.info("获取excel文件的路径:"+fileName);
		try {
			TerminalInf ter =terminalService.getEidtManual(fileName,terminal);
			rm.setMsg("200", ter.getMsg());
		} catch (Exception e) {
			rm.setMsg("201", "终端入库提交失败！");
			e.printStackTrace();
		}
		return rm;
	}

	/**
	 * 解绑商户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "term/unbindMer" })
	@ResponseBody
	public ReturnMsg unbindMer(HttpServletRequest request, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		String[] termIds = ServletRequestUtils.getStringParameter(request, "termIds", "").split(",");
		UAI uai = (UAI) session.getAttribute("UID");
		TerminalInf terminal = new TerminalInf();
		for (String termId : termIds) {
			terminal.setTerminalId(termId);
			terminal.setEditUserId(uai.getUserId());
			terminal.setEditDate(TdExpBasicFunctions.GETDATETIME());
			try {
				terminalService.unbindMer(terminal);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("解绑商户失败：" + e.getMessage());
				return rm.setFail("解绑商户失败:" + e.getMessage());
			}
		}
		log.info("解绑商户成功");
		return rm.setSuccess("解绑商户成功！");
	}

	/**
	 * 解绑代理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "term/unbindAgent" })
	@ResponseBody
	public ReturnMsg unbindAgent(HttpServletRequest request, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		String[] termIds = ServletRequestUtils.getStringParameter(request, "termIds", "").split(",");
		TerminalInf terminal = new TerminalInf();
		UAI uai = (UAI) session.getAttribute("UID");
		if (uai == null) {
			log.error("终端下拨失败：商户失效，请重新登录");
			return rm.setFail("终端下拨失败：商户失效，请重新登录");
		}

		for (String termId : termIds) {
			terminal.setTerminalId(termId);
			try {
				terminal.setEditDate(TdExpBasicFunctions.GETDATETIME());
				terminal.setEditUserId(uai.getUserId());
				terminalService.unbindAgent(terminal);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("解绑代理商失败：" + e.getMessage());
				return rm.setFail("解绑代理商失败:" + e.getMessage());
			}
		}
		log.info("解绑代理商成功");
		return rm.setSuccess("解绑代理商成功！");
	}

	/**
	 * 终端下拨并绑定费率
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/termAllocate" })
	@ResponseBody
	public ReturnMsg termAllocate(@ModelAttribute("TerminalInf") TerminalInf terminal, HttpServletRequest request, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		AgentInf agentInf = new AgentInf();
		agentInf.setAgentId(terminal.getAgentId());
		try {
			agentInf = agentService.getEntity(agentInf);
			if (agentInf == null || agentInf.getAgentName() == null || agentInf.getAgentName().length() <= 0) {
				log.error("终端下拨失败：找不到该代理商");
				return rm.setFail("终端下拨失败：找不到该代理商");
			}
		} catch (Exception e) {
			log.error("终端下拨失败：" + e.getMessage());
			return rm.setFail("终端下拨失败：" + e.getMessage());
		}

		UAI uai = (UAI) session.getAttribute("UID");
		if (uai == null) {
			log.error("终端下拨失败：商户失效，请重新登录");
			return rm.setFail("终端下拨失败：商户失效，请重新登录");
		}

		terminal.setEditDate(TdExpBasicFunctions.GETDATETIME());
		terminal.setEditUserId(uai.getUserId());
		try {

			PayRateUtil.getTermRate(request, terminal);

			terminal.setSouAgentId(uai.getAgentId());
			Integer termAllocateSt = terminalService.termAllocate(terminal);
			if (termAllocateSt == CT.TERM_ALLOCATE_2) {
				log.error("终端下拨失败：未找到该终端或该终端已绑定");
				return rm.setFail("终端下拨失败：未找到该终端或该终端已绑定");
			} else if (termAllocateSt == CT.TERM_ALLOCATE_FAIL) {
				log.error("终端下拨失败");
				return rm.setFail("终端下拨失败");
			} else if (termAllocateSt == CT.TERM_FEE_FAIL) {
				log.error("终端费率绑定失败");
				return rm.setFail("终端费率绑定失败");
			}
		} catch (Exception e) {
			log.error("终端下拨失败" + e.getMessage());
			return rm.setFail("终端下拨失败" + e.getMessage());
		}
		log.info("终端下拨成功");
		return rm.setSuccess("终端下拨成功！");

	}

	/**
	 * 查询 下拨终端数量
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/queryTermAllocateNum" })
	@ResponseBody
	public ReturnMsg queryTermAllocateNum(HttpServletRequest request, HttpSession session) throws Exception {
		String terminalType = ServletRequestUtils.getStringParameter(request, "terminalType", "");
		log.debug("TerminalCompanyController line 136:   termId:" + terminalType);
		ReturnMsg rm = new ReturnMsg();
		TerminalInf terminalInf = new TerminalInf();
		terminalInf.setTerminalType(terminalType);
		UAI uai = (UAI) session.getAttribute("UID");
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			terminalInf.setAgentId(uai.getAgentId());
		}
		rm.setObj(terminalService.queryTermAllocateNum(terminalInf));
		return rm;
	}

	/**
	 * 查询终端列表
	 * 
	 * @param terminal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/queryTerm" })
	@ResponseBody
	public ReturnMsg queryTerm(@ModelAttribute("TerminalInf") TerminalInf terminal, HttpSession session) throws Exception {
		UAI uai = (UAI) session.getAttribute("UID");
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			terminal.setAgentId(uai.getAgentId());
		}
		List<TerminalInf> terminalList = terminalService.getListPage(terminal);

		if (terminalList != null && terminalList.size() > 0) {
			for (TerminalInf terminalInf1 : terminalList) {
				PayRateUtil.conTermRate(terminalInf1);
			}
		}

		Integer total = terminalService.getCount(terminal);
		log.debug("总记录数:{},数据列表：{}", total, terminalList);
		return new ReturnMsg(total, terminalList);
	}

	/**
	 * 查询终端费率列表
	 * 
	 * @param terminal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/queryTermFee" })
	@ResponseBody
	public ReturnMsg queryTermFee(@ModelAttribute("TerminalInf") TerminalInf terminal, HttpSession session) throws Exception {
		UAI uai = (UAI) session.getAttribute("UID");
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			terminal.setAgentId(uai.getAgentId());
		}
		List<TerminalInf> terminalList = terminalService.getTermFeeListPage(terminal);

		if (terminalList != null && terminalList.size() > 0) {
			for (TerminalInf terminalInf2 : terminalList) {
				PayRateUtil.conTermRate(terminalInf2);
			}
		}

		Integer total = terminalService.getTermFeeCount(terminal);
		log.debug("总记录数:{},数据列表：{}", total, terminalList);
		return new ReturnMsg(total, terminalList);
	}

	/**
	 * 删除 终端费率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "term/deleteTermFee" })
	@ResponseBody
	public ReturnMsg deleteTermFee(HttpServletRequest request) {
		ReturnMsg rm = new ReturnMsg();
		String[] termIds = ServletRequestUtils.getStringParameter(request, "termIds", "").split(",");
		TerminalInf terminal = new TerminalInf();
		for (String termId : termIds) {
			terminal.setTerminalId(termId);
			try {
				terminalService.modifyEntity(terminal);
			} catch (Exception e) {
				log.error("删除终端费率失败：" + e.getMessage());
				return rm.setFail("删除终端费率失败:" + e.getMessage());
			}
		}
		log.info("删除解绑代理商成功");
		return rm.setSuccess("删除终端费率成功！");
	}

	/**
	 * 修改 终端费率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "term/eidtTermFee" })
	@ResponseBody
	public ReturnMsg eidtTermFee(@ModelAttribute("TerminalInf") TerminalInf terminal, HttpServletRequest request, HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		String nowDate = TdExpBasicFunctions.GETDATETIME();
		terminal.setEditUserId(uai.getUserId());
		terminal.setEditDate(nowDate);
		ReturnMsg rm = new ReturnMsg();
		try {
			PayRateUtil.getTermRate(request, terminal);
			
			boolean feeflag = terminalService.checkAgtFee(terminal);
			
			if(!feeflag){
				log.error("终端费率修改失败：终端费率小于代理商费率");
				return rm.setFail("终端费率修改失败:终端费率小于代理商费率");
			}

			terminalService.modifyEntity(terminal);
			
			//终端T0费率信息 同步到商户身上
			if(terminal.getCustId()!=null && !"".equals(terminal.getCustId())){
				terminalService.modifyCustFee(terminal);
			}
			
		} catch (Exception e) {
			log.error("终端费率修改失败：" + e.getMessage());
			return rm.setFail("终端费率修改失败:" + e.getMessage());
		}
		log.info("终端费率修改成功");
		return rm.setSuccess("终端费率修改成功！");
	}

	/**
	 * 查询单个终端费率
	 * 
	 * @param termId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "term/queryTermFeeById" })
	@ResponseBody
	public ReturnMsg queryTermFeeById(@RequestParam("termId") String termId, @RequestParam("agentId") String agentId, @RequestParam("type") String type) throws Exception {
		log.debug("TerminalCompanyController queryTermFeeById line 350:   termId:" + termId);
		ReturnMsg rm = new ReturnMsg();
		TerminalInf terminalInf = new TerminalInf();
		terminalInf.setTerminalId(termId);
		terminalInf.setAgentId(agentId);
		terminalInf = terminalService.getEntity(terminalInf);

		PayRateUtil.conTermRate(terminalInf);

		rm.put("type", type);
		rm.setObj(terminalInf);
		return rm;
	}

	/***
	 * 检查该终端，当天是否存在有效交易
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "term/existEffectivePay" })
	@ResponseBody
	public boolean queryTermPay(HttpServletRequest request) {
		try {
			String terminalNo = request.getParameter("terminalNo");
			boolean isExistEffectivePay = terminalService.isExistEffectivePay(terminalNo);
			return isExistEffectivePay;
		} catch (Exception e) {
			return false;
		}
	}
}
