package com.tangdi.production.mpomng.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.omg.IOP.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.AppInf;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.mpomng.service.PrdInfService;
import com.tangdi.production.mpomng.service.TransationService;
import com.tangdi.production.mprcs.bean.TranSerialRecordInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * 交易明细查询
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class TransactionController {
	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransationService transationService;

	/**
	 * 跳转到交易明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/transactionListView" })
	public String transactionListView() {
		return "mpomng/prdInf/transactionManage";
	}
	
	/**
	 * 交易明细查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryTransactionList")
	@ResponseBody
	public ReturnMsg transactionList(@ModelAttribute("tranSerialRecordInf") TranSerialRecordInf tranSerialRecordInf) {
		int total = 0;
		List<TranSerialRecordInf> list = null;
		try {
			log.debug("查询参数:{}", tranSerialRecordInf.debug());
			total = transationService.getCount(tranSerialRecordInf);
			list = transationService.getListPage(tranSerialRecordInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	/**
	 * 统计合作机构交易总条数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryTransactionCount")
	@ResponseBody
	public ReturnMsg queryTransactionCount(HttpServletRequest request) {
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		try {
			log.debug("查询参数:{}", paramMap);
			countMap = transationService.transactionCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
	
	/**
	 * 跳转到交易明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/historyTransactionListView" })
	public String historyTransactionListView() {
		return "mpomng/prdInf/historyTransactionManage";
	}
	
	/**
	 * 交易明细查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryHistoryTransactionList")
	@ResponseBody
	public ReturnMsg queryHistoryTransactionList(@ModelAttribute("tranSerialRecordInf") TranSerialRecordInf tranSerialRecordInf) {
		int total = 0;
		List<TranSerialRecordInf> list = null;
		try {
			log.debug("开始查询统计:{}", tranSerialRecordInf.toString());
			total = transationService.getHistorytranCount(tranSerialRecordInf);
			log.debug("开始查询数据:{}",tranSerialRecordInf.toString());
			list = transationService.getHistorytranListPage(tranSerialRecordInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	/**
	 * 统计合作机构历史交易总条数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryHistoryTransactionCount")
	@ResponseBody
	public ReturnMsg queryHistoryTransactionCount(HttpServletRequest request) {
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		try {
			log.debug("查询参数:{}", paramMap);
			countMap = transationService.historyTransactionCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
	
	/**
	 * 跳转到交易报表查询
	 * @return
	 */
	@RequestMapping({ "prdInf/transListView" })
	public String transPrdList() {
		return "mpomng/prdInf/transManage";
	}
	

	/**
	 * 报表下载交易明细查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryTransList")
	@ResponseBody
	public ReturnMsg queryTransList(@ModelAttribute("tranSerialRecordInf") TranSerialRecordInf tranSerialRecordInf) {
		int total = 0;
		List<TranSerialRecordInf> list = null;
		try {
			//维护之前版本  到bizType==01  则默认设置成05；
			if (tranSerialRecordInf.getBizType() != null && tranSerialRecordInf.getBizType().equals("01")) {
				tranSerialRecordInf.setBizType("05");
			}
//			tranSerialRecordInf.setBizType(tranSerialRecordInf.getBizType().equals("01") ? "05": tranSerialRecordInf.getBizType());
			log.debug("开始查询统计:{}", tranSerialRecordInf.toString());
			
			total = transationService.gettransCount(tranSerialRecordInf);
			list = transationService.gettransListPage(tranSerialRecordInf);
			
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "prdInf/transCount")
	@ResponseBody
	public ReturnMsg transCount(HttpServletRequest request,HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		
		String startTime = (String)paramMap.get("startTime");
		String endTime = (String)paramMap.get("endTime");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(startTime)){
			startTime = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(startTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			endTime = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(endTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("endTime", endTime);
		}
		
		try {
			if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			//	paramMap.put("agentId", uai.getAgentId());
				paramMap.put("subAgentId", uai.getAgentId());
			}
			log.debug("查询参数:{}", paramMap);
			countMap = transationService.TransCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
	
	
	/**
	 * 跳转到提现报表查询
	 * @return
	 */
	@RequestMapping({ "prdInf/withdrawListView" })
	public String withdrawPrdList() {
		return "mpomng/prdInf/withdrawManage";
	}
	

	/**
	 * 提现明细查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryWithdrawList")
	@ResponseBody
	public ReturnMsg queryWithdrawList(@ModelAttribute("tranSerialRecordInf") TranSerialRecordInf tranSerialRecordInf) {
		int total = 0;
		List<TranSerialRecordInf> list = null;
		try {
			log.debug("开始查询统计:{}", tranSerialRecordInf.toString());
			total = transationService.getWithdrawCount(tranSerialRecordInf);
			list = transationService.getWithdrawListPage(tranSerialRecordInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "prdInf/withdrawCount")
	@ResponseBody
	public ReturnMsg withdrawCount(HttpServletRequest request,HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		
		String startTime = (String)paramMap.get("startTime");
		String endTime = (String)paramMap.get("endTime");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(startTime)){
			startTime = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(startTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			endTime = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(endTime,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("endTime", endTime);
		}
		
		try {
			if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			//	paramMap.put("agentId", uai.getAgentId());
				paramMap.put("subAgentId", uai.getAgentId());
			}
			log.debug("查询参数:{}", paramMap);
			countMap = transationService.withdrawCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
}
