package com.tangdi.production.mprcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.mprcs.bean.BankCardBlacklistInf;
import com.tangdi.production.mprcs.service.BankCardBlacklistService;
import com.tangdi.production.mprcs.service.RcsTransactionService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 银行卡黑名单控制器
 * @author zhengqiang
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mprcs/")
public class BankCardBlacklistController {
	private static final Logger log = LoggerFactory
			.getLogger(BankCardBlacklistController.class);

	@Autowired
	private BankCardBlacklistService bankCardBlacklistService;
	@Autowired
	private RcsTransactionService rcsTransactionService;
	@Autowired
	private UnionfitService unionfitService;
	

	/**
	 * 银行卡黑名单跳转页面
	 * @return
	 */
	@RequestMapping(value = "bankcardManage")
	public String bankCardBlacklistManageView(){
		return "mprcs/bankcard/bankCardBlacklistManage";
	} 
	
	/**
	 * 银行卡黑名单信息列表
	 * @param bankCardBlacklistInf
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "bankCardBlacklistManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("bankCardBlacklistInf") BankCardBlacklistInf bankCardBlacklistInf, HttpServletRequest request, HttpSession session){
		int total =0;
		List<BankCardBlacklistInf> list = null;
		try {
			//设置用户id
			Map<String,Object> paramMap = bankCardBlacklistInf.toMap();
			String st = request.getParameter("sdate");
			if(st !=null && st!=""){
				String[] str=st.split("-");
				st=str[0]+str[1]+str[2]+"000000";
			}
			String et = request.getParameter("edate");
			if(et !=null && et!=""){
				String[] etr=et.split("-");
				et=etr[0]+etr[1]+etr[2]+"235959";
			}
			paramMap.put("sdate", st);
			paramMap.put("edate", et);
			log.debug("查询参数:{}",bankCardBlacklistInf.debug());
			total = bankCardBlacklistService.getCount(paramMap);
			list  = bankCardBlacklistService.getListPage(paramMap);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 银行卡黑名单新增跳转页面
	 * @return
	 */
	@RequestMapping(value = "bankCardBlacklistManage/addView")
	public String addView() {
		return "mprcs/bankcard/bankCardBlacklistAdd";
	}
    /**
     * 银行卡黑名单新增控制
     * @param bankCardBlacklistInf
     * @return
     */
	@RequestMapping(value = "bankCardBlacklistAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("BankCardBlacklistInf") BankCardBlacklistInf bankCardBlacklistInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			BankCardBlacklistInf bi = bankCardBlacklistService.getEntity(bankCardBlacklistInf);
			if(bi != null){
				rm.setMsg("204", "银行卡号已存在！");
				return rm;
			}
			UnionfitInf unionfitInf=unionfitService.getCardInf(bankCardBlacklistInf.getCardno());
			if(unionfitInf == null){
				rm.setMsg("203", "银行卡号不存在！");
				return rm;
			}
			bankCardBlacklistInf.setCardType(unionfitInf.getDcflag());
			bankCardBlacklistInf.setIssno(unionfitInf.getIssno());
			bankCardBlacklistInf.setIssnam(unionfitInf.getIssnam());
			bankCardBlacklistInf.setCreateDate(TdExpBasicFunctions.GETDATETIME());
			rt = bankCardBlacklistService.addEntity(bankCardBlacklistInf);
			if (rt > 0){
				rm.setMsg("200", "银行卡添加成功.");
			}else{
				rm.setMsg("201", "银行卡添加失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "银行卡添加异常！");
		}
		return rm;
	}
	/**
	 * 银行卡黑名单删除控制
	 * @param bankCardBlacklistInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bankCardBlacklistManage/delete")
	@ResponseBody
	public ReturnMsg remove(@ModelAttribute("BankCardBlacklistInf") BankCardBlacklistInf bankCardBlacklistInf) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = bankCardBlacklistService.removeEntity(bankCardBlacklistInf);
			if (rt > 0){
				msg.setMsg("200", "银行卡删除成功.");
			}else{
				msg.setMsg("201", "银行卡删除失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "银行卡删除异常.");
		}
		return msg;
	}
	
	/**
	 * 银行卡黑名单修改跳转页面
	 * @return
	 */
	@RequestMapping(value = "bankCardBlacklistManage/editeView")
	public String editView() {
		return "mprcs/bankcard/bankCardBlacklistEdit";
	}
	/**
	 * 银行卡黑名单查单单条信息
	 * @param bankCardBlacklistInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bankCardBlacklistManage/queryBankCardByNo")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("BankCardBlacklistInf") BankCardBlacklistInf bankCardBlacklistInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		BankCardBlacklistInf entity = bankCardBlacklistService.getEntity(bankCardBlacklistInf);
		rm.setObj(entity);
		return rm;
	}
    /**
     * 银行卡黑名单修改控制器
     * @param bankCardBlacklistInf
     * @return
     */
	@RequestMapping(value = "bankCardBlacklistEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("BankCardBlacklistInf") BankCardBlacklistInf bankCardBlacklistInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			
			rt = bankCardBlacklistService.modifyEntity(bankCardBlacklistInf);
			if (rt > 0){
				msg.setMsg("200", "银行卡更新成功.");
			}else{
				msg.setMsg("201", "银行卡更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "银行卡更新异常");
		}
		return msg;
	}

	protected Map<String,Object> getParamMap(HttpServletRequest req) {
			Map<String,Object> map =  new HashMap<String, Object>();
			Map<String,String[]> paramMap = req.getParameterMap();
			if(paramMap == null || paramMap.isEmpty()){
				return map;
			}
			
			Set<String> keySet = paramMap.keySet();
			for (String key : keySet) {
				String[] values = paramMap.get(key);
				if(values == null || values.length == 0){
					map.put(key, "");
				}else if(values.length == 1){
					map.put(key, values[0]);
				}else{
					map.put(key, values);
				}
			}
		return map;
	}
	/*
	@RequestMapping(value = "rcsLimit")
	@ResponseBody
	public ReturnMsg rcsLimit(HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String,Object> paramMap = getParamMap(request);
		rcsTransactionService.checkLimit(paramMap);
		return rm;
	}*/
	
}
