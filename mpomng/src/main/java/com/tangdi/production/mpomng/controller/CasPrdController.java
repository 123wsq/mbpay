package com.tangdi.production.mpomng.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpomng.dao.CasPrdDao;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;
import com.tangdi.production.mpbase.util.MoneyUtils;

/**
 * 提现订单
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class CasPrdController {
	private static final Logger log = LoggerFactory.getLogger(CasPrdController.class);

	@Autowired
	private CasPrdService casPrdService;
	
	@Autowired
	private CasPrdDao dao;
	
	// 异步线程处理
	@Autowired
	private AsynService asynService;
	
	/**
	 * 跳转到提现订单查询
	 * @return
	 */
	@RequestMapping({ "prdInf/casPrdListView" })
	public String casPrdListView() {
		return "mpomng/prdInf/casPrdManage";
	}
	
	@RequestMapping({ "prdInf/getPayTypeList" })
	@ResponseBody
	public ReturnMsg getPayTypeList() {
		ReturnMsg rm=new ReturnMsg();
		List<Map<String, Object>> list = dao.getPayTypeList();
		String json = JSONUtils.toJSONString(list);
		rm.setObj(json);
		return rm;
	}
	
	/**
	 * 提现订单查询
	 * @param request 
	 * @param bean 用来获取分页数据
	 * @return
	 */
	@RequestMapping(value = "prdInf/casPrdList")
	@ResponseBody
	public ReturnMsg casPrdList(HttpServletRequest request,@ModelAttribute("bean") BaseBean bean,HttpSession session) {
		int total = 0;
		List<Map<String,Object>> list = null;
		UAI uai = (UAI) session.getAttribute("UID");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			paramMap.put("ageId",uai.getAgentId()); 
		}
		
		try {
			paramMap.putAll(bean.toMap());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		paramMap = ServletUtil.getParamMap(request, paramMap);
//		Object sdate = paramMap.get("sdate");
//		Object edate = paramMap.get("edate");
		
		String dateStart = (String)paramMap.get("sdate");
		String dateEnd = (String)paramMap.get("edate");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(dateStart)){
			dateStart = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateStart,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			dateStart = dateStart+"000000";
			paramMap.put("startTime", dateStart);
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			dateEnd = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateEnd,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			dateEnd = dateEnd+"235959";
			paramMap.put("endTime", dateEnd);
		}
		/*
		String yday = null,cday= null;
		if(sdate != null && !sdate.toString().equals("")){
			yday = DateUtil.getYesterday(sdate.toString());
			paramMap.put("startTime", (yday+MsgCT.DAY_TIME_START));
		}
		
		if(edate != null && !edate.toString().equals("")){
			cday = edate.toString();
			paramMap.put("endTime", cday+MsgCT.DAY_TIME_END);
		}*/
		
		try {
			log.debug("查询参数  列表:{}", paramMap);
			total = casPrdService.getCasPrdCount(paramMap);
			
			list = casPrdService.getCasPrdListPage(paramMap);
			
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	
	@RequestMapping(value = "prdInf/casCount")
	@ResponseBody
	public ReturnMsg casCount(HttpServletRequest request) {
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		
		String dateStart = (String)paramMap.get("sdate");
		String dateEnd = (String)paramMap.get("edate");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(dateStart)){
			dateStart = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateStart,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			dateStart = dateStart+"000000";
			paramMap.put("startTime", dateStart);
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			dateEnd = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateEnd,"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD);
			dateEnd = dateEnd+"235959";
			paramMap.put("endTime", dateEnd);
		}
		try {
			// 默认情况下，只汇总审核通过订单
			if(null == paramMap.get("auditStatus") || "".equals(paramMap.get("auditStatus").toString())){
				paramMap.put("auditStatus", "02");
			}
			log.debug("查询参数 金额 :{}", paramMap);
			countMap = casPrdService.casCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
	
	/**
	 * 商品订单明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/casDetailsView" })
	public String rechargeDetailsView() {
		return "mpomng/prdInf/casPrdDetails";
	}
	
	@RequestMapping(value = "prdInf/casDetails")
	@ResponseBody
	public ReturnMsg rechargeDetails(@RequestParam(value = "casOrdNo", required = false) String casOrdNo) throws Exception {
		ReturnMsg rm = new ReturnMsg();
//		Map<String,Object> paramMap = ServletUtil.getParamMap(req)
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("casOrdNo", casOrdNo);
		
		Map<String,Object> reslutMap = null;
		try {
			log.debug("请求参数：{}",paramMap);
			reslutMap = casPrdService.casDetails(paramMap);
			log.info("查询结果：{}",reslutMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(reslutMap);
		return rm;
	}
	
	@RequestMapping(value = "prdInf/ordStatusCount")
	@ResponseBody
	public ReturnMsg ordStatusCount(HttpServletRequest request) {
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		int rt=0;
		int rt2=0;
		try {
			log.debug("查询参数:{}", paramMap);
			rt = casPrdService.getCasPrdCount(paramMap);
			paramMap.put("ordStatus", "08");
			rt2 = casPrdService.getCasPrdCount(paramMap);
			rt = rt+rt2;
			log.debug("查询结果：{}",rt);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(rt);
		return  rm;
	}
	
	
	/**
	 * 信息审核
	 * @param custId
	 * @param auditIdea
	 * @param custStatus
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "prdInf/casPrdAudit")
	@ResponseBody
	public ReturnMsg edit(@RequestParam(value = "casOrdNos", required = false) String casOrdNos,
			@RequestParam(value = "auditDesc", required = false) String auditDesc,
			@RequestParam(value = "casAudit", required = false) String casAudit,
			@RequestParam(value = "ordStatus", required = false) String ordStatus,HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		UAI uai=null;
		try {
			uai=UID.getUAI(session);
		} catch (UIDException e1) {
			log.error(e1.getMessage(), e1);
			msg.setMsg("202", "审核失败");
		}
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("casOrdNos", casOrdNos);
		paramMap.put("auditDesc", auditDesc);
		paramMap.put("casAudit", casAudit);
		paramMap.put("ordStatus", ordStatus);
		paramMap.put("sucDate", TdExpBasicFunctions.GETDATETIME());
		int rt = 0;
		try {
			rt=casPrdService.modifyCasPrdBystauts(paramMap);
			if (rt > 0){
				msg.setMsg("200", "审核成功.");
			}else{
				msg.setMsg("201", "审核失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "审核失败");
		}
		log.debug(msg.toString());
		return msg;
	}

	@RequestMapping({ "cas/settleOver" })
	@ResponseBody
	public ReturnMsg settleOver(HttpServletRequest request) {
		ReturnMsg rm = new ReturnMsg();
		String casOrdNos = "";
		try {
			casOrdNos = request.getParameter("casOrdNos");
			casPrdService.settleCasPrdOver(casOrdNos);
			rm.setRspcod("200");
			rm.setRspmsg("设置清算状态成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setRspcod("201");
			rm.setRspmsg("设置清算状态失败！");
		}
		return rm;
	}
	
	@RequestMapping({ "cas/settleFail" })
	@ResponseBody
	public ReturnMsg settleFalse(HttpServletRequest request) {
		ReturnMsg rm = new ReturnMsg();
		String casOrdNos = "";
		try {
			casOrdNos = request.getParameter("casOrdNos");
			casPrdService.settleCasPrdFalse(casOrdNos);
			rm.setRspcod("200");
			rm.setRspmsg("设置清算状态成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setRspcod("201");
			rm.setRspmsg("设置清算状态失败！");
		}
		return rm;
	}
	
	@RequestMapping({ "prdInf/casPay" })
	@ResponseBody
	public ReturnMsg casPay(HttpServletRequest request){
		ReturnMsg rm = new ReturnMsg();
		String casOrdNos = "";
		String cooporgNo = "";
		String payType= "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			casOrdNos = request.getParameter("casOrdNos");
			cooporgNo = request.getParameter("cooporgNo");
			//传入代付渠道
			payType = request.getParameter("payType");
			map.put("casOrdNos", casOrdNos);
			map.put("cooporgNo", cooporgNo);
			//添加代付渠道
			map.put("filed3", payType);
			//异步调用前将状态置为代付成功，避免重复发送
			String sucDate = DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss");
			map.put("sucDate", sucDate);
			dao.updateCasPrdStatusByCasPay(map);
			//异步调用
			asynService.dotran(TranCode.TRAN_PAY_ASYN, map);
			rm.setRspcod("200");
			rm.setRspmsg("实时代付提交完成！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setRspcod("201");
			rm.setRspmsg("实时代付提交失败！");
		}
		return rm;
	}
	
	/**
	 * 可用额度查询
	 * */
	@RequestMapping(value =  "prdInf/limitQuery" )
	@ResponseBody
	public ReturnMsg limitQuery(HttpServletRequest request){
		ReturnMsg rm = new ReturnMsg();
		String cooporgNo = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			cooporgNo = request.getParameter("cooporgNo");
			map.put("cooporgNo", cooporgNo);
			//异步调用
			//asynService.dotran(TranCode.TRAN_LIMIT_QUERY, map);
			Map<String,Object> rmap = 
					casPrdService.limitQuery(String.valueOf(map.get("casOrdNos")),
							String.valueOf(map.get("cooporgNo")));
			log.info("limitQuery调用结果：{}",rmap);
			String balInf = rmap.get("BalInf").toString();
			String BalInf1 = balInf.substring(1, 13);
			String balInf1 = MoneyUtils.toStrYuan(BalInf1);
			String BalInf2 = balInf.substring(14,balInf.length());
			String balInf2 = MoneyUtils.toStrYuan(BalInf2);
			rmap.put("txamt1", balInf1);
			rmap.put("txamt2", balInf2);
			rm.setRspcod("200");
			rm.setRspmsg("额度查询完成！");
			rm.setObj(rmap);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			log.error(e.getMessage(),e);
			rm.setRspcod("201");
			rm.setRspmsg("额度查询失败！");
		}
		return rm;
	}
	
	/**
	 * 跳转到查询额度页面
	 * @return
	 */
	@RequestMapping({ "prdInf/orgLmtQuery" })
	public String orgLmtQuery() {
		return "mpomng/prdInf/orgLmtAmtInf";
	}
	
	/**
	 * 创建额度提现订单
	 * */
	@RequestMapping(value =  "prdInf/addLmtCas" )
	@ResponseBody
	public ReturnMsg createLmtCas(HttpServletRequest request){
		ReturnMsg rm = new ReturnMsg();
		String cooporgNo = "";
		try {
			Map<String,Object> paramp = new HashMap<String,Object>();
			String at1 = request.getParameter("txamt");
			String cardNo = request.getParameter("cardNo");
			String ytxamt = request.getParameter("ytxamt");
			String custId = request.getParameter("custId");
			String custName = request.getParameter("custName");
			String provinceId = "";
			String issno = "";
			String issnam = ""; 
			String netrecamt = "";
			
			paramp.put("casType", "03"); // aT1 自动 提现
			paramp.put("custId", custId);
			paramp.put("custName", custName);
			paramp.put("txamt", ytxamt);
			paramp.put("casDate", TdExpBasicFunctions.GETDATETIME());
			paramp.put("sucDate", TdExpBasicFunctions.GETDATETIME());
			paramp.put("rate", "0");
			paramp.put("fee", "0");
			paramp.put("serviceFee", "0");
			paramp.put("netrecamt", netrecamt);
			paramp.put("issno", issno);
			paramp.put("issnam", issnam);
			paramp.put("cardNo", cardNo);
			paramp.put("ytxamt", ytxamt);
			paramp.put("provinceId", provinceId);
			paramp.put("casDesc", "");
			paramp.put("auditDesc", "系统自动审核");
			paramp.put("t0Amt", 0);
			paramp.put("t1Amt", at1);
			casPrdService.lmtAddAccount(paramp);
			rm.setRspcod("200");
			rm.setRspmsg("创建额度提现订单完成！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setRspcod("201");
			rm.setRspmsg("创建额度提现订单失败！");
		}
		return rm;
	}
	
}

