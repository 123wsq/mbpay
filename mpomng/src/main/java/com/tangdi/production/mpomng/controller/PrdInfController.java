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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.FileInf;
import com.tangdi.production.mpbase.dao.FileUploadDao;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.mpomng.service.PrdInfService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.BaseBean;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * 商品订单
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class PrdInfController {
	private static final Logger log = LoggerFactory.getLogger(PrdInfController.class);

	@Autowired
	private PrdInfService prdInfService;
	
	@Autowired
	private FileUploadDao fileUploadDao;
	/**
	 * 跳转到商品订单查询
	 * @return
	 */
	@RequestMapping({ "prdInf/prdInfListView" })
	public String prdInfListView() {
		return "mpomng/prdInf/prdInfManage";
	}
	
	/**
	 * 跳转到收款（充值）订单查询
	 * @return
	 */
	@RequestMapping({ "prdInf/rechargePrdListView" })
	public String rechargePrdListView() {
		return "mpomng/prdInf/rechargePrdManage";
	}
	
	/**
	 * 商品订单明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/prdInfDetailView" })
	public String prdInfDetailView() {
		return "mpomng/prdInf/prdInfDetailView";
	}
	/**
	 * 商品订单明细查询
	 * @return
	 */
	@RequestMapping({ "prdInf/rechargeDetailsView" })
	public String rechargeDetailsView() {
		return "mpomng/prdInf/rechargePrdDetails";
	}
	@RequestMapping({ "prdInf/rechargeAuditView"})
	public String rechargeAuditView() {
		return "mpomng/prdInf/rechargePrdAuditView";
	}

	/**
	 * 商品订单查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/queryPrdInfList")
	@ResponseBody
	public ReturnMsg queryPrdInfList(@ModelAttribute("prdInf") PrdInf prdInf) {
		int total = 0;
		List<PrdInf> list = null;
		try {
			
			log.debug("查询参数:{}", prdInf.debug());
			try{	
				if(StringUtils.isNotEmpty(prdInf.getStartTime())){
					prdInf.setStartTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(prdInf.getStartTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
				}
			}catch(Exception e){
				prdInf.setStartTime(null);
				
			}
			try{
				if(StringUtils.isNotEmpty(prdInf.getEndTime())){
					prdInf.setEndTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(prdInf.getEndTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
				}
			}catch(Exception e){
				prdInf.setEndTime(null);
			}
			
			total = prdInfService.getCount(prdInf);
			list = prdInfService.getListPage(prdInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	/**
	 * 收款订单查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "prdInf/rechargePrdList")
	@ResponseBody
	public ReturnMsg rechargePrdList(HttpServletRequest request,@ModelAttribute("bean") BaseBean bean,HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		int total = 0;
		List<Map<String,Object>> list = null;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.putAll(bean.toMap());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		paramMap = ServletUtil.getParamMap(request, paramMap);
		try {
			if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
				paramMap.put("subAgentId", uai.getAgentId());
			}
			log.debug("查询参数:{}", paramMap);
			total = prdInfService.getRechargeCount(paramMap);
			list = prdInfService.getRechargeListPage(paramMap);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "prdInf/queryPrdById")
	@ResponseBody
	public ReturnMsg queryPrdById(@ModelAttribute("prdInf") PrdInf prdInf,HttpServletRequest request) throws Exception {
		String prdordno = ServletRequestUtils.getStringParameter(request, "prdordno", "");
		log.debug("TerminalCompanyController line 136:   prdordno:" + prdordno);
		ReturnMsg rm = new ReturnMsg();
		prdInf.setPrdordno(prdordno);
		prdInf = prdInfService.getEntity(prdInf);
		rm.setObj(prdInf);
		return rm;
	}
	
	@RequestMapping(value = "prdInf/queryPrdDetail")
	@ResponseBody
	public ReturnMsg queryPrdDetail(@RequestParam(value = "prdordno", required = false) String prdordno) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		PrdInf prdInf=new PrdInf();
		prdInf.setPrdordno(prdordno);
		PrdInf entity=null;
		try {
			entity = prdInfService.getEntity(prdInf);
			log.info("查询结果：{}",entity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	
	@RequestMapping(value = "prdInf/queryPrdDetails")
	@ResponseBody
	public ReturnMsg queryPrdDetails(@RequestParam(value = "prdordno", required = false) String prdordno) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		PrdInf prdInf=new PrdInf();
		prdInf.setPrdordno(prdordno);
		Map<String,Object> entity=null;
		try {
			entity = prdInfService.prdDetails(prdInf.toMap());
			if (entity.get("cardSignPic")!= null && !"".equals(entity.get("cardSignPic"))) {
				FileInf fileInf = fileUploadDao.selectEntity(new FileInf(String.valueOf(entity.get("cardSignPic"))));
				if (fileInf != null) {
					entity.put("cardSignPicPicName", fileInf.getFjName());
				log.debug("cardSignPic = " + entity.get("cardSignPic") + "  cardSignPicName = " + entity.get("cardSignPicPicName"));
			}
		}
			log.info("查询结果：{}",entity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.putAll(entity);
		return rm;
	}
	@RequestMapping(value = "prdInf/rechargeAudit")
	@ResponseBody
	public ReturnMsg ModifyAuditStatus(@ModelAttribute("payInf") PayInf payInf,HttpServletRequest request, HttpSession session) throws Exception {
		UAI uaii = (UAI) session.getAttribute("UID");
		String userName=uaii.getUserId();
		payInf.setUserName(userName);	
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try{
			rt=prdInfService.modifyAuditStatus(payInf);
			if(rt>0){
				msg.setMsg("200", "审核成功.");
				}
			else{
				msg.setMsg("201", "审核失败.");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "审核失败");
		}
	
	return msg;
	}
	@RequestMapping(value = "prdInf/rechargeDetails")
	@ResponseBody
	public ReturnMsg rechargeDetails(@RequestParam(value = "prdOrdNo", required = false) String prdOrdNo) throws Exception {
		ReturnMsg rm = new ReturnMsg();
//		Map<String,Object> paramMap = ServletUtil.getParamMap(req)
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prdOrdNo", prdOrdNo);
		
		Map<String,Object> reslutMap = null;
		try {
			log.debug("请求参数：{}",paramMap);
			reslutMap = prdInfService.rechargeDetails(paramMap);
			log.info("查询结果：{}",reslutMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(reslutMap);
		return rm;
	}
	
	@RequestMapping(value = "prdInf/queryPrdInfCountList")
	@ResponseBody
	public ReturnMsg queryPrdInfCountList(@ModelAttribute("prdInf") PrdInf prdInf) {
		List<PrdInf> list = null;
		ReturnMsg rm=new ReturnMsg();
		try {
			log.debug("查询参数:{}", prdInf.debug());
			list = prdInfService.getCountList(prdInf);
			log.debug("数据列表：{}",list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(list);
		return  rm;
	}
	@RequestMapping(value = "prdInf/rechargeCount")
	@ResponseBody
	public ReturnMsg rechargeCount(HttpServletRequest request,HttpSession session) {
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
			countMap = prdInfService.rechargeCount(paramMap);
			log.debug("数据列表：{}",countMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(countMap);
		return  rm;
	}
	
	
	
}
