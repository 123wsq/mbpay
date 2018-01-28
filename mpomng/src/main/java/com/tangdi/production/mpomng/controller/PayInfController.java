package com.tangdi.production.mpomng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.mpomng.service.PayInfService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 商品订单
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class PayInfController { 
	private static final Logger log = LoggerFactory.getLogger(PayInfController.class);

	@Autowired
	private PayInfService payInfService;
	
	@Autowired
	private MobileMerService mobileMerService;

	@RequestMapping({ "payInf/payInfListView" })
	public String prdInfListView() {
		return "mpomng/payInf/payInfManage";
	}
	
	@RequestMapping({ "payInf/payInfDetailsView" })
	public String payInfDetailsView() {
		return "mpomng/payInf/payInfDetailsView";
	}
	@RequestMapping(value = "payInf/queryPayInfList")
	@ResponseBody
	public ReturnMsg queryPrdInfList(@ModelAttribute("payInf") PayInf payInf) {
		int total = 0;
		List<PayInf> list = null;
		try {
			log.debug("查询参数:{}", payInf.debug());
			total = payInfService.getCount(payInf);
			list = payInfService.getListPage(payInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "payInf/queryPayInfCountList")
	@ResponseBody
	public ReturnMsg queryPayInfCountList(@ModelAttribute("payInf") PayInf payInf) {
		List<PayInf> list = null;
		ReturnMsg rm=new ReturnMsg();
		try {
			log.debug("查询参数:{}", payInf.debug());
			list = payInfService.getCountList(payInf);
			log.debug("数据列表：{}", list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(list);
		return rm;
	}
	
	@RequestMapping(value = "payInf/queryPayInfById")
	@ResponseBody
	public ReturnMsg queryPayInfById(@ModelAttribute("payInf") PayInf payInf,HttpServletRequest request) throws Exception {
		String payordno = ServletRequestUtils.getStringParameter(request, "payordno", "");
		log.debug("TerminalCompanyController line 136:   payordno:" + payordno);
		ReturnMsg rm = new ReturnMsg();
		payInf.setPayordno(payordno);
		payInf = payInfService.getEntity(payInf);
		rm.setObj(payInf);
		return rm;
	}
	
	
	@RequestMapping(value = "payInf/queryPayInfDetails")
	@ResponseBody
	public ReturnMsg queryPayInfDetails(@RequestParam(value = "payordno", required = false) String payordno) throws Exception {
		ReturnMsg rm=new ReturnMsg();
		PayInf payInf=new PayInf();
		payInf.setPayordno(payordno);
		PayInf entity=null;
		try {
			entity = payInfService.getEntity(payInf);
			log.info("查询结果：{}",entity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	
}
