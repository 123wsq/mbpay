package com.tangdi.production.mpomng.controller;




import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.service.CnapsService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpomng.bean.Authentication;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.mpomng.service.CustBankInfService;
import com.tangdi.production.mpomng.service.CustBankInfTempService;
import com.tangdi.production.mpomng.util.AuthenticationUtil;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 商户绑定银行卡管理
 * @author zhuji
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class CustBankInfController {
	private static final Logger log = LoggerFactory
			.getLogger(CustBankInfController.class);

	@Autowired
	private CustBankInfTempService custBankInfTempService;
	@Autowired
	private CustBankInfService custBankInfService;
	@Autowired
	private CnapsService cnapsService;

	/**
	 * 银行卡修改审核管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempManage")
	public String custBankInfTempManageView(){
		return "mpomng/custbank/custBankTempManage";
	} 
	/**
	 * 银行卡管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage")
	public String custBankInfManageView(){
		return "mpomng/custbank/custBankManage";
	} 
	/**
	 * 银行卡信息列表
	 * @param custBankInf
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage/query")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("custBankInf") CustBankInf custBankInf,HttpServletRequest request){
		int total =0;
		List<CustBankInf> list = null;
		try{
			if(StringUtils.isNotEmpty(custBankInf.getStartTime())){
				custBankInf.setStartTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(custBankInf.getStartTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			custBankInf.setStartTime(null);
		}
		try{
			if(StringUtils.isNotEmpty(custBankInf.getEndTime())){
				custBankInf.setEndTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(custBankInf.getEndTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			custBankInf.setEndTime(null);
		}
		try {	
				
			
			//设置用户id
			log.debug("查询参数:{}",custBankInf.debug());
			total = custBankInfService.getCount(custBankInf);
			list  = custBankInfService.getListPage(custBankInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 银行卡审核信息列表
	 * @param custBankInf
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempManage/query")
	@ResponseBody
	public ReturnMsg queryTempList(@ModelAttribute("custBankInf") CustBankInf custBankInf){
		int total =0;
		List<CustBankInf> list = null;
		try{
			if(StringUtils.isNotEmpty(custBankInf.getStartTime())){
				custBankInf.setStartTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(custBankInf.getStartTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			custBankInf.setStartTime(null);
		}
		try{
			if(StringUtils.isNotEmpty(custBankInf.getEndTime())){
				custBankInf.setEndTime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(custBankInf.getEndTime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			custBankInf.setEndTime(null);
		}
		try {
			//设置用户id
			log.debug("查询参数:{}",custBankInf.debug());
			total = custBankInfTempService.getCount(custBankInf);
			list  = custBankInfTempService.getListPage(custBankInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 银行卡信息审核详情页面跳转
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempDetail/view")
	public String toTempDetailView() {
		return "mpomng/custbank/custBankDetailView";
	}
	/**
	 * 银行卡信息修改审核页面跳转
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempManage/view")
	public String toTempView() {
		return "mpomng/custbank/custBankTempView";
	}
	/**
	 * 银行卡信息修改审核详情
	 * @param bankCardId
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempManage/queryById")
	@ResponseBody
	public ReturnMsg viewTemp(@RequestParam(value = "bankCardId", required = false) String bankCardId) {
		ReturnMsg rm=new ReturnMsg();
		CustBankInf custBankInf=new CustBankInf();
		custBankInf.setBankCardId(bankCardId);
		CustBankInf entity=null;
		int rt=0;
		try {
			entity = custBankInfTempService.getEntity(custBankInf);
			custBankInf.setSubBranch(entity.getSubBranch());
			rt=cnapsService.validateBranchState(custBankInf);
			if(rt>0){
				entity.setBankSubBranchState("01");
			}else{
				entity.setBankSubBranchState("02");
			}
			log.info("查询结果：{}",entity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	/**
	 * 银行卡审核失败原因界面跳转
	 * 
	 * */
	@RequestMapping(value = "custBankInfManage/auditSubmit")
	public String custBankAuditView() {
		return "mpomng/custbank/custBankAuditView";
	}
	
	/**
	 * 银行卡信息详情跳转
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage/view")
	public String toView() {
		return "mpomng/custbank/custBankView";
	}
	/**
	 * 银行卡信息详情
	 * @param bankCardId
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage/queryById")
	@ResponseBody
	public ReturnMsg view(@RequestParam(value = "bankCardId", required = false) String bankCardId) {
		ReturnMsg rm=new ReturnMsg();
		CustBankInf custBankInf=new CustBankInf();
		custBankInf.setBankCardId(bankCardId);
		CustBankInf entity=null;
		try {
			entity = custBankInfService.getEntity(custBankInf);
			log.info("查询结果：{}",entity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		rm.setObj(entity);
		return rm;
	}
	/**
	 * 银行卡信息审核
	 * @param bankCardId
	 * @param status
	 * @param updateDesc
	 * @return
	 */
	@RequestMapping(value = "custBankInfTempManage/audit")
	@ResponseBody
	public ReturnMsg audit(@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "cardNo", required = false) String cardNo,
			@RequestParam(value = "oldCardNo", required = false) String oldCardNo,
			@RequestParam(value = "status", required = false)String status,
			@RequestParam(value = "updateDesc", required = false)String updateDesc,
			@RequestParam(value = "defeatReasonDes", required = false)String defeatReasonDes,
			@RequestParam(value = "defeatReason", required = false)String defeatReason){
		ReturnMsg rm=new ReturnMsg();
		CustBankInf custBankInf=new CustBankInf();
		custBankInf.setCardNo(cardNo);
		custBankInf.setCustId(custId);
		custBankInf.setDefeatReason(defeatReason);
		custBankInf.setDefeatReasonDes(defeatReasonDes);
		try {
			custBankInfService.audit(custBankInf, status, updateDesc);
			rm.setMsg("200", "审核成功!");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setMsg("201", "审核失败!");
		}
		
		return rm;
	} 
	
	/**
	 * 银行卡信息修改页面跳转
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage/custBankEdit")
	public String updateCustBank() {
		return "mpomng/custbank/custBankEdit";
	}
	/**
	 * 银行卡信息审核
	 * @param bankCardId
	 * @param status
	 * @param updateDesc
	 * @return
	 */
	@RequestMapping(value = "custBankInfManage/custBankEditView")
	@ResponseBody
	public ReturnMsg custBankEditView(@ModelAttribute("AppInf") CustBankInf custBankInf){
		ReturnMsg rm=new ReturnMsg();
		try {
			custBankInfService.modifyEntity(custBankInf);
			rm.setMsg("200", "银行卡信息修改成功!");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			rm.setMsg("201", "银行卡信息修改失败!");
		}
		return rm;
	} 
	/**
	 * 去第三方认证
	 * @param bankCardId
	 * @return
	 */
	@RequestMapping(value = "identificationJHF/jhfManage")
	@ResponseBody
	public ReturnMsg identificationJHF(@RequestParam(value = "bankCardId", required = false) String bankCardId) {
		ReturnMsg rm=new ReturnMsg();
		CustBankInf custBankInf=new CustBankInf();
		custBankInf.setBankCardId(bankCardId);
		CustBankInf entity=null;
		try {
			entity = custBankInfTempService.getEntity(custBankInf);
			log.info("查询结果：{}",entity);
			Authentication authentication = new Authentication();
			authentication.setSvcName("paygate.realnameauth");
			authentication.setMerId("M10000197");
			authentication.setTranTime(AuthenticationUtil.getTranTime());
			authentication.setMerchOrderId(AuthenticationUtil.getMerchOrderId());
			authentication.setCardType("0");
			authentication.setPayAcc(entity.getCardNo());
			authentication.setPayPhone(entity.getUsrMobile());
			authentication.setKey("YHOOJDI1L4LMGLK1XKP4LAA40LKTAMBK");
			authentication.setMd5value(AuthenticationUtil.getMd5Value(authentication));
			String res = null;
			try {
				res = AuthenticationUtil.sendPost("http://pay.juhefu.cn/api/", AuthenticationUtil.transferToJson(AuthenticationUtil.getJson(authentication)));
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			Map <String, Object> map = JUtil.toMap(res);
			if("000000".equals(map.get("retCode").toString())){
				if("0".equals(map.get("status").toString())){
					rm.setMsg("200", "去第三方认证成功!");
					custBankInf.setCount(entity.getCount()+1);
					custBankInfTempService.addCount(custBankInf);
				}else{
					rm.setMsg("200", "去第三方认证失败!");
				}
			}else{
				rm.setMsg("201",map.get("retMsg").toString());
			}
			log.info("认证结果：{}",res);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return rm;
	}
}
