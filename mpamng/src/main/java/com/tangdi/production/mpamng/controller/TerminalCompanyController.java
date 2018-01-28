package com.tangdi.production.mpamng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpamng.bean.TerminalCompanyInf;
import com.tangdi.production.mpamng.service.TerminalCompanyService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

@Controller
@RequestMapping("/mpamng/")
public class TerminalCompanyController {
	private static Logger log = LoggerFactory.getLogger(TerminalCompanyController.class);
	@Autowired
	private TerminalCompanyService terminalCompanyService;

	@RequestMapping(value = "term/termComListView")
	public String termComListView() {
		return "mpamng/term/termComManage";
	}

	@RequestMapping(value = "term/termTypeListView")
	public String termTypeListView() {
		return "mpamng/term/termTypeManage";
	}

	@RequestMapping(value = "term/termComView")
	public String termComView() {
		return "mpamng/term/termComView";
	}

	@RequestMapping(value = "term/termTypeView")
	public String termTypeView() {
		return "mpamng/term/termTypeView";
	}

	@RequestMapping({ "selectoption/termCom" })
	@ResponseBody
	public ReturnMsg termCom(HttpServletRequest request, HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		try {
			msg.setObj(terminalCompanyService.querySelectOptionTermCom());
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

	@RequestMapping({ "selectoption/termType" })
	@ResponseBody
	public ReturnMsg termType(HttpServletRequest request, HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		try {
			msg.setObj(terminalCompanyService.querySelectOptionTermType());
			msg.setMsg("200", "查询成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

	@RequestMapping({ "term/eidtTermCom" })
	@ResponseBody
	public ReturnMsg eidtTermCom(@ModelAttribute("TerminalCompanyInf") TerminalCompanyInf terminalCompany, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");

		BeanDebugger.dump(terminalCompany);
		try {

			String termcomCode = terminalCompany.getTermcomCode();
			if (terminalCompany.getTermcomName() != null && terminalCompany.getTermcomName().trim().length() > 0) {
				terminalCompany.setTermcomCode("");
				if (terminalCompanyService.getCount(terminalCompany) > 0) {
					log.error("保存失败：有已经有存在的信息");
					return rm.setFail("保存失败: 已存在厂商名称【" + terminalCompany.getTermcomName() + "】");
				}
				terminalCompany.setTermcomCode(termcomCode);
			}

			String termcomName = terminalCompany.getTermcomName();
			if (terminalCompany.getTermcomCode() != null && terminalCompany.getTermcomCode().trim().length() > 0) {
				terminalCompany.setTermcomName("");
				if (terminalCompanyService.getCount(terminalCompany) > 0) {
					log.error("保存失败：有已经有存在的信息");
					return rm.setFail("保存失败: 已存在厂商【" + terminalCompany.getTermcomCode() + "】");
				}
				terminalCompany.setTermcomName(termcomName);
			}

			String termType = terminalCompany.getTermType();
			if (terminalCompany.getTermTypeName() != null && terminalCompany.getTermTypeName().trim().length() > 0) {
				terminalCompany.setTermType("");
				if (terminalCompanyService.getCount(terminalCompany) > 0) {
					log.error("保存失败：有已经有存在的信息");
					return rm.setFail("保存失败: 已存在刷卡器名称【" + terminalCompany.getTermTypeName() + "】");
				}
				terminalCompany.setTermType(termType);
			}

			String termTypeName = terminalCompany.getTermTypeName();
			if (terminalCompany.getTermType() != null && terminalCompany.getTermType().trim().length() > 0) {
				terminalCompany.setTermTypeName("");
				if (terminalCompanyService.getCount(terminalCompany) > 0) {
					log.error("保存失败：有已经有存在的信息");
					return rm.setFail("保存失败: 已存在刷卡器类型【" + terminalCompany.getTermType() + "】");
				}
				terminalCompany.setTermTypeName(termTypeName);
			}

			if (terminalCompany.getTermId() == null || terminalCompany.getTermId().length() <= 0) {
				terminalCompany.setCreateUserId(uai.getUserId());
				terminalCompany.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				terminalCompanyService.addEntity(terminalCompany);
			} else {
				terminalCompany.setEditUserId(uai.getUserId());
				terminalCompany.setEditDate(TdExpBasicFunctions.GETDATETIME());
				terminalCompanyService.modifyEntity(terminalCompany);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("保存失败：" + e.getMessage());
			return rm.setFail("保存失败:" + e.getMessage());
		}
		log.info("保存成功");
		return rm.setSuccess("保存成功！");
	}

	@RequestMapping({ "term/deleteTermCom" })
	@ResponseBody
	public ReturnMsg deleteTermCom(HttpServletRequest request, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		String[] termIds = ServletRequestUtils.getStringParameter(request, "termIds", "").split(",");

		TerminalCompanyInf terminalCompany = new TerminalCompanyInf();

		for (String termId : termIds) {
			terminalCompany.setTermId(termId);
			try {
				terminalCompanyService.removeEntity(terminalCompany);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("删除失败：" + e.getMessage());
				return rm.setFail("删除失败:" + e.getMessage());
			}
		}
		log.info("删除成功");
		return rm.setSuccess("删除成功！");
	}

	@RequestMapping({ "term/queryTermComById" })
	@ResponseBody
	public ReturnMsg queryTermComById(HttpServletRequest request) throws Exception {
		
		String termId = ServletRequestUtils.getStringParameter(request, "termId", "");
		log.debug("TerminalCompanyController line 136:   termId:" + termId);

		ReturnMsg rm = new ReturnMsg();
		TerminalCompanyInf terminalCompany = new TerminalCompanyInf();
		terminalCompany.setTermId(termId);
		terminalCompany = terminalCompanyService.getEntity(terminalCompany);
		rm.setObj(terminalCompany);
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
	@RequestMapping({ "term/queryTermCom" })
	@ResponseBody
	public ReturnMsg queryTermCom(@ModelAttribute("TerminalCompanyInf") TerminalCompanyInf terminalCompany) throws Exception {
		Integer total = terminalCompanyService.getCount(terminalCompany);
		List<TerminalCompanyInf> list = terminalCompanyService.getListPage(terminalCompany);
		log.debug("总记录数:{},数据列表：{}", total, list.toString());
		return new ReturnMsg(total, list);
	}
}
