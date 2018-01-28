package com.tangdi.production.tdauth.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdauth.bean.MenuInf;
import com.tangdi.production.tdauth.bean.SysInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserAppSession;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.service.MenuService;
import com.tangdi.production.tdauth.service.RoleService;
import com.tangdi.production.tdauth.service.SystemService;
import com.tangdi.production.tdauth.service.UserLoginService;
import com.tangdi.production.tdauth.service.UserService;
import com.tangdi.production.tdauth.service.exception.CaptchaException;
import com.tangdi.production.tdauth.service.exception.UserLoginException;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 用户登陆Controller，用于管理用户登陆的业务调用。
 * 
 * @author zhengqiang
 *
 */
@Controller
@RequestMapping("/auth/")
public class UserLoginController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SystemService systemService;

	@Value("#{tdauthConfig}")
	private Properties prop;
	
	/**
	 * 手机app下载页面跳转
	 * @return
	 */
	@RequestMapping(value = "appLoad")
	public String appLoad() {
		return "auth/app/appLoad";
	}

	/**
	 * 生成验证码
	 * 
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "genCaptcha")
	public void generateCaptcha(HttpSession session, HttpServletResponse response) {
		String captcha = userLoginService.generateCaptcha();
		BufferedImage img = userLoginService.generateCaptchaImg(captcha);
		// 将验证码保存到session中 便于以后验证
		// 需考虑session共享的问题
		session.setAttribute("captcha", captcha);
		try {
			// 发送图片
			ImageIO.write(img, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户退出
	 * 
	 * @param session
	 * @param user
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value = "loginout")
	public ModelAndView loginout(HttpSession session) {
		ModelAndView model = new ModelAndView();
		UAI uid = (UAI) session.getAttribute("UID");
		String login=uid.getLoginAdr();
		String agentId=uid.getAgentId();
		log.debug("退出的地址==============="+login);
		if(login.equals("posm")){
			if(agentId.equals("2015000000")){
				//运营平台的测试地址
//				model.setViewName("redirect:http://172.20.100.12:8092/posm/login.jsp");
				//运营平台的生产地址
				model.setViewName("redirect:http://172.20.100.10:8092/posm/login.jsp");
			}else{
				//代理商的测试地址
//				model.setViewName("redirect:http://172.20.100.12:8092/posa");
				//代理商的生产地址
				model.setViewName("redirect:http://103.47.137.51:8092/posa/");
			}
		}else{
			// 重定向到登陆界面
			model.setViewName("redirect:http://172.20.100.10:8092/posm/login.jsp");
		}
		// 移除Session
		session.removeAttribute("UID");
		session.invalidate();
		return model;
	}

	/**
	 * 用户登录验证视图
	 * 
	 * @param session
	 * @param user
	 * @param captcha
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "loginView")
	public ModelAndView verifyLogin(HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		String tp = request.getParameter("tp");
		if (tp != null && tp.equals("01")) {
			model.addObject("info", "用户会话超时, 请重新登入！");
		} else if (tp != null && tp.equals("02")) {
			model.addObject("info", "用户已在其它地点登陆！");
		} else if (tp != null && tp.equals("09")) {
			model.addObject("info", "用户已安全退出！");
		}

		return model;
	}

	/**
	 * 用户登录效验
	 * 
	 * @param session
	 * @param user
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value = "login")
	public @ResponseBody ModelAndView login(HttpSession session, UserInf user, String captcha, String reqType, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		String serverCaptcha = (String) session.getAttribute("captcha");
		UAI uai = null;
		Map<String, Object> rspMsg = new HashMap<String, Object>();
		
		try {
			// 机构号固定000000001
			user.setOrgId(UAI.TOP_ORG_ID);
			/**
			 * 屏蔽验证码
			 */
			// captcha = serverCaptcha;

			// 登录第一步就校验验证码
			if (captcha == null || "".equals(captcha)) {
				throw new CaptchaException("验证码不能为空！");
			} else if (!captcha.equals(serverCaptcha)) { //
				throw new CaptchaException("验证码错误！");
			}
			String url = "http://" + request.getRemoteHost() + ":" + request.getLocalPort() + request.getContextPath();
			log.debug("请求地址:{}", url);
			
			// 全路径验证改为应用名称验证 xiejinzhong 2015-03-16
			url = request.getContextPath();
			// 查询模块ID
			SysInf sys = systemService.getEntity(new SysInf(url));
			log.debug("系统模块ID:[{}]", sys.getId());
			user.setSysId(sys.getId());
			user.setLastLoginIp(request.getRemoteHost());
			user.setLastLoginTime(TdExpBasicFunctions.GETDATETIME());
			if (user.getAgentId() == null || user.getAgentId().equals("")) {
				user.setAgentId(Constant.SYS_AGENT_ID);
			}
			uai = userLoginService.logIn(user, captcha, serverCaptcha);
		} catch (CaptchaException e) {
			log.error(e.getMessage(), e);
			model.addObject("info", e.getMessage());
			rspMsg.put("rspcod", "201");
			rspMsg.put("rspmsg", "验证码错误");
		} catch (UserLoginException e) {
			log.error(e.getMessage(), e);
			model.addObject("info", e.getMessage());
			rspMsg.put("rspcod", "201");
			rspMsg.put("rspmsg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addObject("info", e.getMessage());
			rspMsg.put("rspcod", "201");
			rspMsg.put("rspmsg", "登录失败");
		}
		if (uai == null) {
			model.addObject("user", user);
			model.setViewName("login");

		} else {
			uai.setOrgId(UAI.TOP_ORG_ID);
			session.setAttribute("UID", uai);

			// 保存session到application
			UserAppSession.setUserSession(uai.getId(), session);
			// 设置session有效时间15分钟
			String timeout = prop.getProperty("loginTimeout");
			if (timeout == null) {
				timeout = "3600";
			}
			// session.setMaxInactiveInterval(Integer.getInteger(timeout));
			// 重定向
			model.setViewName("redirect:auth/mainPanel.do");
			rspMsg.put("rspcod", "200");
			rspMsg.put("rspmsg", "登录成功");
			rspMsg.put("redirect", "auth/mainPanel.do");
		}

		/*
		 * 之前因为直接做的跳转，导致失败信息无法提示到页面， 现在支持ajax登录请求， 登录成功由js负责跳转到主页， 同时不会影响之前的登录方式
		 */
		if ("ajax".equalsIgnoreCase(reqType)) {
			try {
				response.getWriter().write(JUtil.toJsonString(rspMsg));
				response.getWriter().flush();
				// response.getWriter().close();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return model;
	}
   /**
    * posp跳转到后台
    */
	@RequestMapping(value = "Posplogin",method=RequestMethod.GET) 
	public @ResponseBody ModelAndView  Posplogin(HttpSession session,String userId,String agentId,UserInf user ,String SYS_ID, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		UAI uai = null;

		Map<String, Object> rspMsg = new HashMap<String, Object>();
		try {
			// 机构号固定000000001
			user.setUserId(userId);
			user.setOrgId(UAI.TOP_ORG_ID);
			if(agentId==null || " ".equals(agentId)){
				agentId="2015000000";
			}
			user.setAgentId(agentId);
			String url = "http://" + request.getRemoteHost() + ":" + request.getLocalPort() + request.getContextPath();
			log.debug("请求地址:{}", url);
			
			// 全路径验证改为应用名称验证 xiejinzhong 2015-03-16
			url = request.getContextPath();
			// 查询模块ID
			//SysInf sys = systemService.getEntity(new SysInf(url));
		//	log.debug("系统模块ID:[{}]", sys.getId());
		//	user.setSysId(sys.getId());
			user.setLastLoginIp(request.getRemoteHost());
			user.setLastLoginTime(TdExpBasicFunctions.GETDATETIME());
			user.setSysId(SYS_ID);
			if (user.getAgentId() == null || user.getAgentId().equals("")) {
				user.setAgentId(Constant.SYS_AGENT_ID);
			}
			log.info("===================3");
			uai = userLoginService.PosplogIn(user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addObject("info", e.getMessage());
			rspMsg.put("rspcod", "201");
			rspMsg.put("rspmsg", "登录失败");
		}
		if (uai == null) {
			model.addObject("user", user);
			model.setViewName("login");

		} else {
			uai.setOrgId(UAI.TOP_ORG_ID);
			log.info("============================================================"+uai);
			uai.setLoginAdr("posm");
			uai.setSysId(SYS_ID);
			session.setAttribute("UID", uai);

			// 保存session到application
			UserAppSession.setUserSession(uai.getId(), session);
			// 设置session有效时间15分钟
			String timeout = prop.getProperty("loginTimeout");
			if (timeout == null) {
				timeout = "3600";
			}
			// session.setMaxInactiveInterval(Integer.getInteger(timeout));
			// 重定向
			model.setViewName("redirect:mainPanel.do");
			rspMsg.put("rspcod", "200");
			rspMsg.put("rspmsg", "登录成功");
			rspMsg.put("redirect", "mainPanel.do");
		}
		return model;
	}
	
	/**
	 * 登陆成功后重定向跳转
	 * 
	 * @param request
	 * @return index.jsp
	 */
	@RequestMapping(value = "mainPanel")
	public String mainPanel(HttpSession session) {
		log.debug("进入菜单模块==================：{}");
		UAI uai = (UAI) session.getAttribute("UID");
		// 查询菜单
		List<MenuInf> menus = menuService.queryAuthAccording(uai.getId(), uai.getSysId());
		log.debug("用户拥有的菜单树：{}", menus);
		// request.setAttribute("menus", menus);
		// 查询用户的权限
		uai.setMenuAuth(menuService.queryAuthMap(uai.getId(), uai.getSysId()));
		session.setAttribute("UID", uai);
		log.debug("用户信息:[{}]", uai.toString());

		return "index";
	}

}
