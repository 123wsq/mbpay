package com.tangdi.production.mpomng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.mpomng.bean.CustRatesInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;

/**
 * 手机商户信息控制器（代理商平台）
 * @author zhengqiang
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class AgentMobileMerController {
	private static final Logger log = LoggerFactory
			.getLogger(AgentMobileMerController.class);

	@Autowired
	private MobileMerService mobileMerService;
	
	// 异步线程处理
	@Autowired
	private AsynService asynService;

	/**
	 * 手机商户管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage")
	public String mobileMerView(){
		return "mpomng/mobilemer/agentMobileMerManage";
	} 
	
	/**
	 * 手机商户管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/view")
	public String toView(){
		return "mpomng/mobilemer/agentmobileMerView";
	}
	
	/**
	 * 代理商系统-商户修改页面
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/updateView")
	public String updateView(){
		return "mpomng/mobilemer/mobileMerEdit";
	}
	
	/**
	 * 代理商系统-商户费率修改页面
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/updateFeeView")
	public String updateFeeView(){
		return "mpomng/mobilemer/termCustFeeView";
	}
	
	/**
	 * 代理商系统-商户修改
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/update")
	@ResponseBody
	public ReturnMsg merUpdate(@ModelAttribute("MobileMerInf") MobileMerInf merInf){
		
		ReturnMsg rm = new ReturnMsg();
		Map<String,Object> param = new HashMap<String,Object>();
		
		try {
			//校验商户和代理商提现费率大小
			param.put("custId", merInf.getCustId());
			param.put("custRate", merInf.getRateTCas());
			param.put("maxTCas", merInf.getMaxTCas());// hg add 20160406  提现单笔金额
			log.debug("...节假日费率：" + merInf.getRateHolidayTCas());
			if(mobileMerService.checkRateForT(param)==false){
				rm.setMsg("210", "商户提现费率不得小于代理商提现费率！");
				return rm;
			}
			mobileMerService.modifyEntity(merInf);
			rm.setMsg("200", "商户提现费率修改成功!");
		} catch (Exception e) {
			log.error("提现费率修改失败：" + e.getMessage());
			rm.setMsg("201", "商户提现费率修改失败!");
			e.printStackTrace();
		}
		return rm;
	}
	
	
	/**
	 *
	 * 查询手机商户信息列表
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/query")
	@ResponseBody
	public ReturnMsg  query(@ModelAttribute("MobileMerInf")  MobileMerInf mobileMerInf,HttpSession session){
		int total =0;
		List<MobileMerInf> list = null;
		try {
			UAI uai=UID.getUAI(session);
			mobileMerInf.setAgentId(uai.getAgentId());
			log.debug("代理商商户查询请求参数：{}",mobileMerInf);
			total=mobileMerService.getCountAgent(mobileMerInf);
			list=mobileMerService.queryMobileMerAgent(mobileMerInf);
		} catch (UIDException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReturnMsg(total,list);
	}
	
	/**
	 * 手机商户管理数据报表生成
	 * @return
	 */
	@RequestMapping(value = "agentMobileMerManage/report")
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
	
	/**
	 * 修改商户的费率
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "gentMobileMerManage/updateCustFee")
	@ResponseBody
	public ReturnMsg updateCustFee(@ModelAttribute("CustRatesInf") CustRatesInf custRatesInf, HttpServletRequest request){
		
		log.debug("开始修改商户费率");
		ReturnMsg msg = new ReturnMsg();
		try {
			mobileMerService.updateCustRate(custRatesInf);
			msg.setSuccess();
			log.debug("商户费率修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("修改费率失败：" +e.getMessage());
			msg.setFail(e.getMessage());
		}
		
		return msg;
	}
	
	/**
	 * 查询商户费率
	 * @param request
	 * @return
	 */
	@RequestMapping(value="gentMobileMerManage/selectCustRate")
	@ResponseBody
	public ReturnMsg selectCustRate(HttpServletRequest request){
		
		
		ReturnMsg msg = new ReturnMsg();
		
		Map<String, Object> requestParam = new HashMap<String, Object>();
			
		requestParam.put("rateCode", request.getParameter("rateCode"));
		requestParam.put("custId", request.getParameter("custId"));
		requestParam.put("rateType", request.getParameter("rateType"));
		
		try {
			CustRatesInf rate = mobileMerService.queryCustRate(requestParam);
			log.debug("查询的结果：  {}", rate);
			msg.setObj(rate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setFail(e.getMessage());
		}
		return msg;
	}
	
}
