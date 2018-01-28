package com.tangdi.production.mpomng.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class CustAccountController {
	private static final Logger log = LoggerFactory
			.getLogger(CustAccountController.class);

	@Autowired
	private CustAccountService custAccountService;
	

	/**
	 * 文件下载管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "custAccountManage")
	public String custAccountManageView(){
		return "mpomng/custaccount/custAccountManage";
	} 
	
	@RequestMapping(value = "custAccountManage/queryCustId")
	@ResponseBody
	public ReturnMsg queryCustId(@RequestParam(value="custId")String custId){
		ReturnMsg rm=new ReturnMsg();
		MobileMerInf mmi=new MobileMerInf();
		mmi.setCustId(custId);
		rm.setObj(mmi);
		return rm;
	}
	/**
	 * 商户账户余额查看跳转页面（入口在商户查询）
	 * @return
	 */
	@RequestMapping(value = "custAccountForCustView")
	public String custAccountForCustView(){
		return "mpomng/custaccount/custAccountViewByCust";
	} 
	/**
	 *
	 * 商户列表
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "custAccountManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("custAccountInf") CustAccountInf custAccountInf,HttpSession session){
		int total =0;
		ReturnMsg rm=new ReturnMsg();
		List<CustAccountInf> list = null;
		try {
			log.debug("查询参数:{}",custAccountInf.debug());
			total = custAccountService.getCount(custAccountInf);
			list  = custAccountService.getListPage(custAccountInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.ALWAYS);
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			rm.setObj(mapper.writeValueAsString(list));
			rm.setRecords(total);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
	}
	
	@RequestMapping(value = "custAccountManage/addView")
	public String addView() {
		return "mpbase/custAccount/custAccountAdd";
	}

	
	@RequestMapping(value = "custAccountManage/delete")
	@ResponseBody
	public ReturnMsg remove(@ModelAttribute("CustAccountInf") CustAccountInf custAccountInf) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			custAccountService.removeEntity(custAccountInf);
			if (rt > 0){
				msg.setMsg("200", "文件信息删除成功.");
			}else{
				msg.setMsg("201", "文件信息删除失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "文件信息删除异常.");
		}
		return msg;
	}
	
	/**
	 * 查询单个文件下载信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "custAccountManage/queryById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("CustAccountInf") CustAccountInf custAccountInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		CustAccountInf entity = custAccountService.getEntity(custAccountInf);
		rm.setObj(entity);
		return rm;
	}

	@RequestMapping(value = "custAccountManage/editStatus")
	@ResponseBody
	public ReturnMsg editStatus(@RequestParam(value="custId")String custId,
			@RequestParam(value="status")String status){
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			CustAccountInf entity=new CustAccountInf();
			entity.setCustId(custId);
			entity.setAccountStatus(status);
			rt=custAccountService.modifyEntity(entity);
			if (rt > 0){
				msg.setMsg("200", "状态更新成功.");
			}else{
				msg.setMsg("201", "状态更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "文件信息更新失败");
		}
		return msg;
	}

	
	
	
}
