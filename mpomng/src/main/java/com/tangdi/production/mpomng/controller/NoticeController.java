package com.tangdi.production.mpomng.controller;

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

import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.mpomng.service.NoticeService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author limiao
 * @version 1.0
 * 
 */

@Controller
@RequestMapping("/mpomng/")
public class NoticeController {
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;

	@RequestMapping({ "notice/NewFile" })
	public String newFilePay() {
		return "mpomng/notice/NewFile";
	}
	@RequestMapping({ "NewFile" })
	public String newFilePrm() {
		return "mpomng/NewFile";
	}
	
	@RequestMapping({ "notice/noticeListView" })
	public String noticeListView() {
		return "mpomng/notice/noticeManage";
	}

	@RequestMapping(value = "notice/noticeView")
	public String noticeView() {
		return "mpomng/notice/noticeView";
	}

	@RequestMapping(value = "notice/NoticeDetailView")
	public String editeNoticeView() {
		return "mpomng/notice/noticeView";
	}

	@RequestMapping(value = "notice/queryNoticeList")
	@ResponseBody
	public ReturnMsg queryNoticeList(@ModelAttribute("noticeInf") NoticeInf noticeInf, HttpServletRequest request) throws Exception {
		Integer total = noticeService.getCount(noticeInf);
		List<NoticeInf> list = noticeService.getListPage(noticeInf);
		log.debug("总记录数:{},数据列表：{}", total, list.toString());
		return new ReturnMsg(total, list);
	}

	@RequestMapping(value = "notice/saveNotice")
	@ResponseBody
	public ReturnMsg saveNotice(@ModelAttribute("NoticeInf") NoticeInf noticeInf, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		BeanDebugger.dump(noticeInf);
		try {
			if (noticeInf.getNoticeId() == null || noticeInf.getNoticeId().length() <= 0) {
				noticeInf.setNoticeIssue(uai.getUserId());
				noticeInf.setNoticeIssueDate(TdExpBasicFunctions.GETDATETIME());
				noticeInf.setCreateUserId(uai.getUserId());
				noticeInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				noticeService.addEntity(noticeInf);
				log.info("新增成功");
				rm.setSuccess("新增成功！");
			} else {
				noticeInf.setCreateUserId(uai.getUserId());
				noticeInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
				noticeService.modifyEntity(noticeInf);
				log.info("修改成功");
				rm.setSuccess("修改成功！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm;
	}

	@RequestMapping(value = "notice/deleteNotice")
	@ResponseBody
	public ReturnMsg deleteNotice(@ModelAttribute("NoticeInf") NoticeInf noticeInf, HttpServletRequest request) throws Exception {

		ReturnMsg rm = new ReturnMsg();
		String[] noticeIds = ServletRequestUtils.getStringParameter(request, "noticeIds", "").split(",");
		try {
			for (String noticeId : noticeIds) {
				noticeInf.setNoticeId(noticeId);
				noticeService.removeEntity(noticeInf);
			}
		} catch (Exception e) {
			log.error("删除失败：" + e.getMessage());
			return rm.setFail("删除失败:" + e.getMessage());
		}
		log.info("删除成功");
		return rm.setSuccess("删除成功！");
	}

	@RequestMapping(value = "notice/queryNoticeById")
	@ResponseBody
	public ReturnMsg queryNoticeById(@ModelAttribute("NoticeInf") NoticeInf noticeInf, HttpServletRequest request) throws Exception {
		String noticeId = ServletRequestUtils.getStringParameter(request, "noticeId", "");
		log.debug("TerminalCompanyController line 136:   noticeId:" + noticeId);
		ReturnMsg rm = new ReturnMsg();
		noticeInf.setNoticeId(noticeId);
		noticeInf = noticeService.getEntity(noticeInf);
		rm.setObj(noticeInf);
		return rm;
	}
}
