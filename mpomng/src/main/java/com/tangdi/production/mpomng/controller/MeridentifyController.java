package com.tangdi.production.mpomng.controller;


import java.util.Date;
import java.util.List;

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
import com.tangdi.production.mpomng.bean.MeridentifyInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.service.MeridentifyService;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 实名认证信息管理
 * @author zhuji
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class MeridentifyController {
	private static final Logger log = LoggerFactory
			.getLogger(MeridentifyController.class);

	@Autowired
	private MeridentifyService meridentifyService;
	@Autowired
	private MobileMerService mobileMerService;

	/**
	 * 实名认证信息管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "meridentifyManage")
	public String meridentifyManageView(){
		return "mpomng/meridentify/meridentifyManage";
	} 
	
	/**
	 *
	 * 实名认证信息列表
	 * @param request
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "meridentifyManage/query")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("meridentifyInf") MeridentifyInf meridentifyInf){
		int total =0;
		List<MeridentifyInf> list = null;
		// 不选择日期时，StartTime为000000
		try{
			if(StringUtils.isNotEmpty(meridentifyInf.getStartTime())){
				meridentifyInf.setStartTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(meridentifyInf.getStartTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			meridentifyInf.setStartTime(null);
		}
		try{
			if(StringUtils.isNotEmpty(meridentifyInf.getEndTime())){
				meridentifyInf.setEndTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(meridentifyInf.getEndTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			meridentifyInf.setEndTime(null);
		}
		try {
			//默认认证状态审核中的
			if(meridentifyInf.getCustStatus()==null||"".equals(meridentifyInf.getCustStatus())){
				meridentifyInf.setCustStatus(CT.AUTH_STATUS_1);
			}
			if("4".equals(meridentifyInf.getCustStatus())){
				meridentifyInf.setCustStatus("");
			}
			//设置用户id
			log.debug("查询参数:{}",meridentifyInf.debug());
			total = meridentifyService.getCount(meridentifyInf);
			list  = meridentifyService.getListPage(meridentifyInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	/**
	 * 实名认证详情
	 * ReturnMsg
	 * @return
	 */
	@RequestMapping(value = "meridentifyManage/view")
	public String editView() {
		return "mpomng/meridentify/meridentifyView";
	}
	
	/**
	 * 查询单个实名认证信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "meridentifyManage/queryById")
	@ResponseBody
	public ReturnMsg query(@RequestParam(value = "custId", required = true) String custId,@RequestParam(value = "type", required = false) String type) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		if(type == null || "".equals(type)){
			type = "view";
		}
		MeridentifyInf mi=new MeridentifyInf();
		mi.setCustId(custId);
		MeridentifyInf entity = meridentifyService.getEntity(mi);
		rm.setObj(entity);
		rm.put("type", type);
		return rm;
	}
	/**
	 * 实名认证信息审核
	 * @param custId
	 * @param auditIdea
	 * @param custStatus
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "meridentifyManage/audit")
	@ResponseBody
	public ReturnMsg edit(@RequestParam(value = "custId", required = true) String custId,
			@RequestParam(value = "auditIdea", required = false) String auditIdea,
			@RequestParam(value = "custStatus", required = true) String custStatus,HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		UAI uai=null;
		try {
			uai=UID.getUAI(session);
		} catch (UIDException e1) {
			log.error(e1.getMessage(), e1);
			msg.setMsg("202", "审核失败");
		}
		MeridentifyInf meridentifyInf=new MeridentifyInf();
		meridentifyInf.setCustId(custId);
		meridentifyInf.setIdentifyTime(TdExpBasicFunctions.GETDATETIME());
		meridentifyInf.setIdentifyUser(uai.getUserId());
		meridentifyInf.setAuditIdea(auditIdea);
		meridentifyInf.setCustStatus(custStatus);
		//修改商户
		MobileMerInf mobileMerInf=new MobileMerInf();
		mobileMerInf.setAuthStatus(custStatus);
		mobileMerInf.setCustId(custId);
		int rt = 0;
		try {
			rt=meridentifyService.modifyEntity(meridentifyInf);
			mobileMerService.modifyEntity(mobileMerInf);
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

	
	
	
}
