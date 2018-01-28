package com.tangdi.production.mpomng.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;

/**
 * 手机商户信息控制器
 * @author zhengqiang
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class MobileMerController {
	private static final Logger log = LoggerFactory.getLogger(MobileMerController.class);

	@Autowired
	private MobileMerService mobileMerService;
	// 异步线程处理
	@Autowired
	private AsynService asynService;

	/**
	 * 手机商户管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage")
	public String mobileMerView(){
		return "mpomng/mobilemer/mobileMerManage";
	} 
	
	@RequestMapping(value = "mobilemer/agreementView")
	public String agreement() {
		return "mpomng/mobilemer/agreement";
	}
	
	/**
	 * 手机商户管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage/view")
	public String toView(){
		return "mpomng/mobilemer/mobileMerView";
	} 
	
	/**
	 *
	 * 查询手机商户信息列表
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage/query")
	@ResponseBody
	public ReturnMsg  query(@ModelAttribute("MobileMerInf") MobileMerInf mer,HttpSession session){
		UAI uai = (UAI) session.getAttribute("UID");
		int total =0;
		List<MobileMerInf> list = null;
		if (!Constant.SYS_AGENT_ID.equals(uai.getAgentId())) {
			mer.setAgentId(uai.getAgentId()); 
		}
		try{	
			if(StringUtils.isNotEmpty(mer.getStartTime())){
				mer.setStartTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(mer.getStartTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			mer.setStartTime(null);
			
		}
		try{
			if(StringUtils.isNotEmpty(mer.getEndTime())){
				mer.setEndTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(mer.getEndTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			mer.setEndTime(null);
		}
		
		String auth=mer.getAuthStatus();
		if(StringUtils.isEmpty(auth)){
		   mer.setAuthStatus(CT.AUTH_STATUS_2);
		}
		try {
			total = mobileMerService.getCount(mer);
			list  = mobileMerService.getListPage(mer);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 查询单个实名认证信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "mobileMerManage/queryById")
	@ResponseBody
	public ReturnMsg query(@RequestParam(value = "custId", required = false) String custId) {
		ReturnMsg rm = new ReturnMsg();
		MobileMerInf mi=new MobileMerInf();
		mi.setCustId(custId);
		MobileMerInf entity=null;
		try {
			entity = mobileMerService.getEntity(mi);
		} catch (Exception e) {
			log.error("查询商户详细信息异常："+e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	
	/**
	 * 根据商户查询信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "mobileMerManage/queryCustId")
	@ResponseBody
	public ReturnMsg queryCustId(@RequestParam(value = "limitCustId", required = false) String limitCustId) {
		ReturnMsg rm = new ReturnMsg();
		MobileMerInf mi=new MobileMerInf();
		mi.setCustId(limitCustId);
		MobileMerInf entity=null;
		int rt=0;
		try {
			rt=mobileMerService.getCount(mi);
			if(rt>0){
				entity = mobileMerService.getEntity(mi);
			}else{
				rm.setMsg("201","");
			}
		} catch (Exception e) {
			log.error("查询商户详细信息异常："+e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	
	/**
	 * 根据代理商查询信息
	 * @param limitAgentId
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "mobileMerManage/queryAgentId")
	@ResponseBody
	public ReturnMsg queryAgentId(@RequestParam(value = "limitAgentId", required = false) String limitAgentId) {
		ReturnMsg rm = new ReturnMsg();
		MobileMerInf mi=new MobileMerInf();
		mi.setAgentId(limitAgentId);
		MobileMerInf entity=null;
		try {
			entity = mobileMerService.getAgentEntity(mi);
		} catch (Exception e) {
			log.error("查询代理商详细信息异常："+e.getMessage(),e);
			e.printStackTrace();
		}
		rm.setObj(entity);
		return rm;
	}
	
	
	
	/**
	 *
	 * 修改商户状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage/modifyCustStatus")
	@ResponseBody
	public ReturnMsg  setStatus(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status){
		ReturnMsg rm=new ReturnMsg();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("custStatus", status);
			mobileMerService.modifyMobileMerStatus(map);
			rm.setMsg("200", "操作成功！");
		} catch (Exception e) {
			rm.setMsg("201", "操作失败！");
			log.error("禁用或启用商户失败",e.getMessage());
		}
		return rm;
	}
	
	/**
	 *
	 * 锁定或解锁商户
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage/unlockLoginStatus")
	@ResponseBody
	public ReturnMsg  unlock(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "status", required = false) String status){
		ReturnMsg rm=new ReturnMsg();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("isLoginFlag", status);
			map.put("custPwdNum", "0");
			mobileMerService.modifyMobileMerStatus(map);
			rm.setMsg("200", "操作成功！");
		} catch (Exception e) {
			rm.setMsg("201", "操作失败！");
			log.error("锁定或解锁商户失败",e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 手机商户管理数据报表生成
	 * @return
	 */
	@RequestMapping(value = "mobileMerManage/report")
	@ResponseBody
	public ReturnMsg report(@ModelAttribute("MobileMerInf") MobileMerInf mer,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			HttpSession session){
		ReturnMsg msg = new ReturnMsg();
		try {
			HashMap<String,Object> con = new HashMap<String,Object>();
			con.putAll(mer.toMap());
			con.put("sdate", sdate);
			con.put("edate", edate);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("param", con);
			map.put("uid", UID.get(session));
			map.put("service", "mobileMerServiceImpl");
			// 调用异步线程处理
			asynService.dotran(TranCode.TRAN_REPORT, map);
			 
			msg.setMsg("200", "报表已提交完成,请到下载页面查看报表文件！");
		} catch (Exception e) {
			 msg.setMsg("201", "数据报表提交失败！");
			log.error(e.getMessage(),e);
		}
		return msg;
	} 
}
