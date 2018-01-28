package com.tangdi.production.mpomng.controller;

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

import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpomng.service.PrdInfService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * 代理商系统交易查询
 * @author xiejinzhong
 *
 */
@Controller
@RequestMapping("/mpomng/")
public class AgentPrdController {
	
	private static final Logger log = LoggerFactory.getLogger(AgentPrdController.class);
	
	@Autowired
	private PrdInfService prdInfService;
	
	/**
	 * 商品订单明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/prdInfView" })
	public String prdInfView() {
		return "mpomng/prdInf/prdManage";
	}
	
	/**
	 * 商品订单查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/prdInfList")
	@ResponseBody
	public ReturnMsg prdInfList(@ModelAttribute("baseBean") BaseBean baseBean,HttpServletRequest request,HttpSession session) {
		int total = 0;
		List<Map<String,Object>> list = null;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.putAll(baseBean.toMap());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		paramMap = ServletUtil.getParamMap(request, paramMap);
		
		UAI uai = null;
		try {
			uai = UID.getUAI(session);
		} catch (UIDException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		paramMap.put("agentId", uai.getAgentId());//代理商编号
		
		String dateStart = (String)paramMap.get("prdDateStart");
		String dateEnd = (String)paramMap.get("prdDateEnd");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(dateStart)){
			dateStart = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateStart,DateUtil.FORMAT_MM_DD_YYYY_SLASH), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("prdDateStart", dateStart);
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			dateEnd = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateEnd,DateUtil.FORMAT_MM_DD_YYYY_SLASH), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("prdDateEnd", dateEnd);
		}
		
		try {
			log.debug("查询参数:{}", paramMap);
			total = prdInfService.getAgentPrdCount(paramMap);
			list = prdInfService.getAgentPrdList(paramMap);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "prdInf/prmPrdCount")
	@ResponseBody
	public ReturnMsg rechargeCount(HttpServletRequest request,HttpSession session) {
		Map<String,Object> countMap = null;
		ReturnMsg rm=new ReturnMsg();
		Map<String,Object> paramMap = ServletUtil.getParamMap(request);
		UAI uai = null;
		try {
			uai = UID.getUAI(session);
		} catch (UIDException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		paramMap.put("agentId", uai.getAgentId());//代理商编号
		
		String dateStart = (String)paramMap.get("prdDateStart");
		String dateEnd = (String)paramMap.get("prdDateEnd");
		//转换日期格式 dd/MM/yyyy --> yyyyMMdd
		if(StringUtils.isNotEmpty(dateStart)){
			dateStart = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateStart,DateUtil.FORMAT_MM_DD_YYYY_SLASH), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("prdDateStart", dateStart);
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			dateEnd = DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(dateEnd,DateUtil.FORMAT_MM_DD_YYYY_SLASH), DateUtil.FORMAT_YYYYMMDD);
			paramMap.put("prdDateEnd", dateEnd);
		}
		
		try {
			log.debug("查询参数:{}", paramMap);
			countMap = prdInfService.prmPrdCount(paramMap);
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
	@RequestMapping({ "prdInf/prmPrdInfDetailView" })
	public String prdInfDetailView() {
		return "mpomng/prdInf/prdDetails";
	}
	
	@RequestMapping(value = "prdInf/prmPrdDetails")
	@ResponseBody
	public ReturnMsg prmPrdDetails(@RequestParam(value = "prdOrdNo", required = false) String prdOrdNo,HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
//		Map<String,Object> paramMap = ServletUtil.getParamMap(req)
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prdOrdNo", prdOrdNo);
		
		UAI uai = null;
		try {
			uai = UID.getUAI(session);
		} catch (UIDException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		paramMap.put("agentId", uai.getAgentId());//代理商编号
		
		Map<String,Object> reslutMap = null;
		try {
			log.debug("请求参数：{}",paramMap);
			reslutMap = prdInfService.prmPrdDetails(paramMap);
			log.info("查询结果：{}",reslutMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(reslutMap);
		return rm;
	}
}
