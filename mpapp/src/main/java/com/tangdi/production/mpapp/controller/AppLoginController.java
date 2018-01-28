package com.tangdi.production.mpapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




import com.tangdi.production.mpapp.service.AppLoginService;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;


/**
 * 
 * APP手机用户登录
 * @author zhengqiang
 *
 */
@Controller
public class AppLoginController {
	private static Logger log = LoggerFactory.getLogger(AppLoginController.class);
	

	@Autowired
	private AppLoginService appLoginService;
	
	/**
	 * 登录交易
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SY0003")
	@ResponseBody
	public String login(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("APP登陆交易开始..." );
		log.info("请求数据为：{}" ,msg);
		
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			pmap.put("ip", request.getRemoteHost());
			rmap = appLoginService.login(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "登录成功!");
			rspmessage.addDataAll(rmap);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
			log.error(e.getMessage(),e);
		}  catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("APP登陆交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
}
