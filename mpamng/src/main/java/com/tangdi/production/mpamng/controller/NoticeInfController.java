package com.tangdi.production.mpamng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpamng.service.NoticeInfService;
import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/***
 * 公告信息Controller
 * 
 * @author sunhaining
 *
 */
@Controller
@RequestMapping("/mpamng/notice/")
public class NoticeInfController {
	@Autowired
	private NoticeInfService noticeService;

	/***
	 * 获取公告信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "queryNoticeList" })
	@ResponseBody
	public ReturnMsg queryNoticeList(@ModelAttribute("noticeInf") NoticeInf noticeInf, HttpServletRequest request) throws Exception {
		noticeInf.setNoticePlatform("1");
		Integer total = noticeService.getCount(noticeInf);
		List<NoticeInf> list = noticeService.getListPage(noticeInf);
		return new ReturnMsg(total, list);
	}

	/***
	 * 进入公告信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public String getNoticeList() {
		return "mpamng/notice/list";
	}

	/***
	 * 根据ID获取公告信息
	 * 
	 * @param noticeInf
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryNoticeById")
	@ResponseBody
	public ReturnMsg queryNoticeById(@ModelAttribute("NoticeInf") NoticeInf noticeInf, HttpServletRequest request) throws Exception {
		noticeInf.setNoticePlatform("1");
		String noticeId = ServletRequestUtils.getStringParameter(request, "noticeId", "");
		ReturnMsg rm = new ReturnMsg();
		noticeInf.setNoticeId(noticeId);
		noticeInf = noticeService.getEntity(noticeInf);
		rm.setObj(noticeInf);
		return rm;
	}

	/***
	 * 进入公告详细页
	 * 
	 * @return
	 */
	@RequestMapping(value = "NoticeDetailView")
	public String editeNoticeView() {
		return "mpamng/notice/noticeView";
	}
}
