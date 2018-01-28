package com.tangdi.production.mpomng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.mpomng.service.ReportMerCountService;
import com.tangdi.production.mpomng.service.ReportTranCountService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

@Controller
@RequestMapping("/mpomng/")
public class ReportController {
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportMerCountService reportMerCountService;

	@Autowired
	private ReportTranCountService reportTranCountService;

	@RequestMapping(value = "report/000")
	@ResponseBody
	public ReturnMsg report000(HttpServletRequest request,HttpSession session) throws UIDException {
		ReturnMsg rm = new ReturnMsg();
		NoticeInf noticeInf = new NoticeInf();
		UAI uai = UID.getUAI(session);
      //noticeInf.setId(uai.getUserId());
		try {
			 rm.setObj(reportMerCountService.queryNotice(noticeInf));
			log.debug("紧急公告=[{}]", rm.getObj());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rm;
	}
	
	@RequestMapping(value = "notice/save")
	@ResponseBody
	public ReturnMsg saveNotice(@ModelAttribute("NoticeInf") NoticeInf noticeInf, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		try {
			if (noticeInf.getNoticeId() != null ) {
        //noticeInf.setId(uai.getUserId());
				reportMerCountService.saveType(noticeInf);
				log.info("新增浏览公告信息成功！！！");
				rm.setSuccess("新增成功！");
			} 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "添加异常！");
		}
		return rm;
	}

	
	/**
	 * 商户注册量统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "report/001")
	@ResponseBody
	public ReturnMsg report001() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rm.setObj(reportMerCountService.queryReportMerCount());
			log.debug("商户注册统计报表数据=[{}]", rm.getObj());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rm;
	}

	/**
	 * 商户活跃度(收款交易)统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "report/002")
	@ResponseBody
	public ReturnMsg report002() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rm.setObj(reportMerCountService.queryReportMerLiveCount());
			log.debug("商户活跃度(收款交易)统计=[{}]", rm.getObj());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rm;
	}

	/**
	 * 交易额/交易比数 统计(近七天)
	 * 
	 * @return
	 */
	@RequestMapping(value = "report/003")
	@ResponseBody
	public ReturnMsg report003() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rm.setObj(reportTranCountService.queryReportTranCount());
			log.debug("交易金额/交易笔数 （近七天）统计=[{}]", rm.getObj());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rm;
	}

	/**
	 * T0提现
	 * 
	 * @return
	 */
	@RequestMapping(value = "report/004")
	@ResponseBody
	public ReturnMsg report004() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rm.setObj(reportTranCountService.queryT0ReportTranCount());
			log.debug("T0提现 统计(近七天)=[{}]", rm.getObj());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rm;
	}

}
