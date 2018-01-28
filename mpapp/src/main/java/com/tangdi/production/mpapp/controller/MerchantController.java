package com.tangdi.production.mpapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.AgentService;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpapp.service.ValidateCodeService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpbase.util.MSGCODE;
import com.tangdi.production.tdbase.domain.ReturnMsg;


/**
 * 
 * 商户信息管理
 * @author zhengqiang
 *
 */
@Controller
public class MerchantController {
	private static Logger log = LoggerFactory.getLogger(MerchantController.class);
	

	@Autowired
	private MerchantService merchantService;
	
	
	
	
	/**
	 * 商户注册交易[SY0002]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0002")
	@ResponseBody
	public String register(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("商户注册交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			String pmapStr=JUtil.toJsonString(pmap);
			log.info("商户转换信息：{}",pmapStr);
			merchantService.register(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "商户注册成功!");
			rspmessage.addDataAll(rmap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("商户注册交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 修改密码与找回交易[SY0005]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0005")
	@ResponseBody
	public String updatePwd(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("商户密码修改与找回交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			merchantService.updatePwd(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "商户修改或找回密码成功!");
			rspmessage.addDataAll(rmap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	    return  Msg.getRspJson(rspmessage);
	}
	/**
	 * 实名认证交易
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0007")
	@ResponseBody
	public String certification(HttpServletRequest request,
			@RequestParam(value = "cardHandheld", required = false) MultipartFile cardHandheldFile,
			@RequestParam(value = "cardFront",    required = false) MultipartFile cardFrontFile,
			@RequestParam(value = "cardBack",     required = false) MultipartFile cardBackFile) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("商户实名认证交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		
		boolean error = false;
		try{
			if(cardHandheldFile == null || cardHandheldFile.getSize() <= 0){
				log.error("手持身份证图片接收错误...");
				error = true;
			}
			if(cardFrontFile == null || cardFrontFile.getSize() <= 0){
				log.error("身份证正面图片接收错误...");
				error = true;
			}
			if(cardBackFile == null || cardBackFile.getSize() <= 0){
				log.error("身份证反面图片接收错误...");
				error = true;
			}
			log.info("手持身份证图片：[{}],大小：[{}b]",cardHandheldFile.getOriginalFilename(),cardHandheldFile.getSize());
			log.info("身份证正面图片：[{}],大小：[{}b]",cardFrontFile.getOriginalFilename(),cardFrontFile.getSize());
			log.info("身份证背面图片：[{}],大小：[{}b]",cardBackFile.getOriginalFilename(),cardBackFile.getSize());
				
		}catch(Exception e){
			log.error("身份证图片接收错误...");
			error = true;
		}finally{
			if(error){
				String ecode = ExcepCode.EX000227;
				rspmessage.setDataV("RSPCOD",ecode); //获取异常代码
				rspmessage.setDataV("RSPMSG",MSGCODE.GET(ecode));  //获取异常信息
			}
		}
		
		
		try {
		
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			//设置上传图片
			pmap.put("cardHandheld", cardHandheldFile);
			pmap.put("cardFront", cardFrontFile);
			pmap.put("cardBack", cardBackFile);
			
			merchantService.certification(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "商户实名认证申请提交成功!");
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	    return  Msg.getRspJson(rspmessage);
	}
	/**
	 * 查询商户信息交易機
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0004")
	@ResponseBody
	public String getCustInf(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("商户查询交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			rmap =merchantService.getMerchantInfo(pmap);
			log.info("返回数据为：{}",rmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "查询成功!");
			rspmessage.addDataAll(rmap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		}
		return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 注册协议获取[AG0001]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "AG0001")
	@ResponseBody
	public String getAgreement() {
		
		Map<String,Object> m1 = new HashMap<String,Object>();
		m1.put("fileUrl","http://127.0.0.1:8098/pay/test/agreement.html");
		m1.put("fileDesc", "文件描述");
		Map<String,Object> m2 = new HashMap<String,Object>();
		m2.put("agreement",m1);
		
		RspMsg rspmessage = new RspMsg();
		rspmessage.setDataV("RSPCOD", "000000");
		rspmessage.setDataV("RSPMSG", "注册协议获取成功!");
		rspmessage.setDataV("agreement", m2);

		return Msg.getRspJson(rspmessage);
	}
	/**
	 * 扫描二维码进行注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0022", method=RequestMethod.POST)
	@ResponseBody
	public ReturnMsg webScanRegitser(HttpServletRequest request, ServletResponse servletResponse) {
		
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		
		String tel = request.getParameter("custMobile");
		String pwd = request.getParameter("custPwd");
		String cv = request.getParameter("checkValue");
		String cust_Id = request.getParameter("cust_Id");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custMobile", tel);
		map.put("custPwd", pwd);
		map.put("codeType", MsgST.SMS_TYPE_REGIST);
		map.put("msgCode", cv);
		map.put("c_Id", cust_Id);
		map.put("url", request.getRequestURL().toString());
		log.debug("商户扫描注册开始。。。");
		log.info("请求交易的参数 {}", map);
		ReturnMsg msg = new ReturnMsg();
		 try {
			 msg = merchantService.webScanRegitser(map);
		} catch (TranException e) {
			// TODO Auto-generated catch block
			msg.setFail(e.getMsg());
			e.printStackTrace();
		}
		
		 log.debug("返回参数：  {}",msg);
		return msg;
	}
	
	
}
