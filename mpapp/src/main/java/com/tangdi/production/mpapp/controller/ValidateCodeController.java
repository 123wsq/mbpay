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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.MessageService;
import com.tangdi.production.mpapp.service.ValidateCodeService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.tdbase.domain.ReturnMsg;


/**
 * 
 * 验证码获取
 * @author zhengqiang
 *
 */
@Controller
public class ValidateCodeController {
	private static Logger log = LoggerFactory.getLogger(ValidateCodeController.class);
	

	@Autowired
	private ValidateCodeService validateCodeService;
	@Autowired
	private MessageService messageService;
	
	/**
	 * 获取验证码交易[SY0001]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0001")
	@ResponseBody
	public String getValidateCode(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("验证码获取交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);			
			messageService.sendsms(reqmessage.getBody());
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "获取验证码成功.");
		} catch(TranException e){
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("获取验证码交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 验证验证码交易[SY0013]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0013")
	@ResponseBody
	public String validate(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("验证码验证交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			boolean result=validateCodeService.validate(pmap);
			if (result) {
				rspmessage.setDataV("RSPCOD", "000000");
				rspmessage.setDataV("RSPMSG", "验证成功!");
			}
		} catch(TranException e){
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("验证交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	
	/**
	 * 获取验证码交易[SY0111]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0111", method=RequestMethod.POST)
	@ResponseBody
	public ReturnMsg getWebRegisterValidateCode(HttpServletRequest request, ServletResponse servletResponse) {
		
		
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		
		log.debug("请求参数：  ",request.getParameterMap());
		String tel = request.getParameter("custMobile");
		String codeType = request.getParameter("codeType"); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custMobile", tel);
		map.put("codeType", codeType);
		
		log.info("验证码获取交易开始..." );
		log.info("请求数据为：{}" ,map);
		ReturnMsg msg = new ReturnMsg();
		try {
			messageService.sendsms(map);
			msg.setSuccess("获取成功");
		} catch(TranException e){
			log.error(e.getMessage(),e);
			msg.setFail(e.getMsg());
		}
		
		log.info("获取验证码交易完成." );
	    return  msg;
	}
	
}
