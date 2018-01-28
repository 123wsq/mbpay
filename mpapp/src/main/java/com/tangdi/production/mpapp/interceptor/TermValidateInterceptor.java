package com.tangdi.production.mpapp.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.session.AppSession;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpbase.util.MD5;
import com.tangdi.production.mpbase.util.MSGCODE;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.MD5Util;



/**
 * APP交易拦截器
 * 
 * @author zhengqiang
 * 
 */
@Service
public class TermValidateInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory
			.getLogger(TermValidateInterceptor.class);
	@Autowired
	private MerchantService merchantService;
	/**
	 * 需要拦截的对象的后缀 集合
	 */
	private final static String SUFFIX  = ".json";
	/**
	 * 签到交易（不拦截）
	 */
	private final static String[] TRAN_CODE = new String[]{
		 "SY0002",
		 "SY0022",   //分享注册
		 "SY0012","SY0003",//注册交易
		 "SY0001",
		 "SY0111",  //分享注册中的获取验证码
		 "SY0005","SY0013","TS0001",
		 "SG9002","OD2001","PY2001","PY2002" //机构接入交易
		 ,"TZ0001" //POSP调用交易
		};
	private final static Map<String,String> TRAN_MSG = 
			new HashMap<String,String>(){/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			   {
				put("CM0001","");
				put("TK1020","");
				}};

	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("stime", System.currentTimeMillis());
		String remoteIp =  request.getRemoteHost();
		log.info("交易请求拦截开始...");
		String url=request.getServletPath().substring(1);	
		//检查请求后缀，判断是否需要拦截
		if(!url.endsWith(SUFFIX)){
			log.info("请求地址后缀格式不正确,交易终止！url={{}}",url);
			sendClient(response,"请求地址错误.");
			return false;
		}
		log.info("URL={{},{}}",url,remoteIp);
		for(String TCODE : TRAN_CODE){
			if(url.equals(TCODE+SUFFIX)){
				//签到交易
				log.info("交易请求拦截完成[{},不拦截,通过].",TRAN_MSG.get(TCODE));
				return true ;
			}
		}

		String message = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("请求数据为{}", message);
		if (null == message){
			log.info("请求数据不能为空,交易终止！");
			sendClient(response,"请求数据为空.");
			return false;
		}
		ReqMsg regmsg = Msg.getReqMessage(message);
		
		try{
			Map<String,Object> body = regmsg.getBody();
			//必填参数校验 sysType：1 Andriod 2 IOS
			ParamValidate.doing(body, "sysType","sysVersion",
					"appVersion","txnDate","txnTime","custMobile","custId","sysTerNo");
			
			//登陆校验
			int ispass = AppSession.validate(String.valueOf(body.get("custId")),
					String.valueOf(body.get("sysTerNo")));
			if(ispass == 1){
				log.error("客户端连接超时!");
				sendClient(response,ExcepCode.EX888888);
				return false;
			}
			if(ispass == 2){
				log.error("客户端未登录!");
				sendClient(response,ExcepCode.EX900001);
				return false;
			}
			if(ispass == 3){
				log.error("已在其它设备登录!");
				sendClient(response,ExcepCode.EX888889);
				return false;
			}
			//数字签名校验
			Map<String,Object> head = regmsg.getHead();
			if(head.get(MessageConstants.SIGN_MESSAGE) == null){
				log.error("签名校验未通过,未找到SIGN字段!");
				sendClient(response,ExcepCode.EX900005);
				return false;
			}
			
			String sign     = String.valueOf(head.get(MessageConstants.SIGN_MESSAGE));
			String signdata = JUtil.toJsonString(regmsg.getBody());
			String sign_    = MD5.encryption(signdata+MessageConstants.SIGN_KEY);
			
			log.info("系统签名数据:[{}]",signdata);
			log.info("数字签名校验:客户端={}，系统生成={}",sign,sign_);
			if(!sign.equalsIgnoreCase(sign_)){
				log.error("签名校验未通过,签名错误!");
				sendClient(response,ExcepCode.EX900005);
				return false;
			}
			Map<String,Object> merchant = merchantService.getMerchant(regmsg.getBody());
			if (merchant==null||merchant.size()<=0) {
				throw new TranException(ExcepCode.EX000206);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			sendClient(response,ExcepCode.EX900001);
			log.info("交易请求拦截完成[效验未通过,系统异常！].");
			return false;
		}
	   log.info("交易请求拦截完成[效验通过].");
       return true;
    }

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		long etime = System.currentTimeMillis();
		long stime = (Long) request.getAttribute("stime");
		log.info("交易共耗时:{}s",(etime-stime)/1000.00);

		
	}
	
	private void sendClient(HttpServletResponse response,String rspcod) throws UnsupportedEncodingException, IOException{
		RspMsg rspmessage = new RspMsg();
		rspmessage.setDataV("RSPCOD",rspcod);
		rspmessage.setDataV("RSPMSG",MSGCODE.GET(rspcod));
		String rval = Msg.getRspJson(rspmessage);
		response.getOutputStream().write(rval.getBytes("UTF-8"));  	
	}
	
	public static void main(String[] args){
		String str = "{custId:15081900000550,sysType:1,sysTerNo:fe80::84db:acff:fe4c:ae2a%p2p0,appVersion:2.0,custMobile:15100000000,sysVersion:4.2.2,txnTime:135947,txnDate:150821}11111111111111110123456789ABCDEF";
		System.out.println(MD5Util.digest(str));
	}
	

}