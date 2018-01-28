package com.tangdi.production.tdauth.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserAppSession;
import com.tangdi.production.tdbase.util.ServletUtil;

/**
 * 权限拦截
 * 
 * @author songleiheng
 * 
 */
@Service
public class UserLogInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory
			.getLogger(UserLogInterceptor.class);
	/**
	 * 不做登陆拦截的对象数组
	 */
	private static String[] LogUrl = { "auth/loginView.do", "auth/login.do",
			"auth/genCaptcha.do", "auth/loginout.do", "auth/appLoad.do", "auth/Posplogin.do"};
	/**
	 * 需要拦截的对象的后缀 集合
	 */
	private static String[] OnFilter = { ".do" };
	
	/**
	 * 创建 com.tangdi.production.tdbase.util.ServletUtil
	 * 	用对象的形式调用方法会比静态方法执行效率高
	 */
	ServletUtil servletUtil = new ServletUtil();

	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
	// TODO Auto-generated method stub
		log.info("登陆拦截开始...");
		String url=request.getServletPath().substring(1);
		
		
		
		boolean filterFlag=true;
		//检查请求后缀，判断是否需要拦截
		for (int i = 0; i <OnFilter.length; i++) {
			if(url.endsWith(OnFilter[i])){
				filterFlag=false;
			}
		}
		if(filterFlag){
			log.info("登陆拦截-结束-url("+url+")不在拦截范围之内");
			return true;
		}
		//登陆放行集合检测， 如果需要放行，则放行。
		for (int i = 0; i < LogUrl.length; i++) {
			if(url.substring(0,url.lastIndexOf(".do")+3).equals(LogUrl[i])){
				log.info("登陆拦截-结束-请求地址放行："+LogUrl[i]);
				return true;
			}
		}
		//拿取session中登陆标志对象
	  Object obj=request.getSession().getAttribute("UID");
	  if(obj==null){ 
          //超时 01
		  log.info("登陆拦截-SESSION已过期.");
//		  pageRedirect(request,response,"01");
		  //运营系统的登录（测试环境）
//		       response.sendRedirect("http://172.20.100.12:8092/posm/login.jsp");
		  //运营系统的登录（生产环境）
			  response.sendRedirect("http://172.20.100.10:8092/posm/login.jsp");
		  //代理商系统的登录（测试环境）
			//  response.sendRedirect("http://172.20.100.12:8092/posa");
		  //代理商系统的登录（生产环境）
//			  response.sendRedirect("http://103.47.137.51:8092/posa/");
		  return false;
	  }else{
		  UAI uAI=(UAI) obj;
		  String UID=uAI.getId();
		  String userId=uAI.getUserId();
		  if(UserAppSession.getUserMap().containsKey(uAI.getId())){
				HttpSession hs = UserAppSession.getUserMap().get(uAI.getId());
				String rd = ((UAI)hs.getAttribute("UID")).getUserRandom();
				log.info("用户随机码Random1:{},Random2:{}",rd,uAI.getUserRandom());
				/*
				if(!(rd.equals(uAI.getUserRandom()))){	
					log.info("用户在别处登陆,SESSION移除:{},{}",uAI.getId(),uAI.toString());
					request.getSession().invalidate();
					//踢出 02
				  pageRedirect(request,response,"02");
				  return false;
				}*/
			}
		  if(UID==null||userId.equals("")){
			  log.info("登陆拦截-结束-未登录，跳转登陆");
			  //如果是ajax请求，返回json通知
			  if(servletUtil.isAjaxRequest(request)){
//				  pageRedirect(request,response,"01");
				  response.sendRedirect("http://172.20.100.10:8092/posm/login.jsp");
//				  response.sendRedirect("http://103.47.137.51:8092/posa/");
			  }else{
				 //运营系统的登录（测试环境）
//			       response.sendRedirect("http://172.20.100.12:8092/posm/login.jsp");
			  //运营系统的登录（生产环境）
				  response.sendRedirect("http://172.20.100.10:8092/posm/login.jsp");
			  //代理商系统的登录（测试环境）
				//  response.sendRedirect("http://172.20.100.12:8092/posa");
			  //代理商系统的登录（生产环境）
//				  response.sendRedirect("http://103.47.137.51:8092/posa/"); 
				//  response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath());
			  }
			  return false;
		  }else{
			  log.info("登陆拦截-结束-已登录");
			  return true;
		  }
	  }
	   
	  

}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 跳转到登陆页面login.jsp
	 * @param request
	 * @param response
	 * @param tp
	 * @throws IOException
	 */
	public void pageRedirect(HttpServletRequest request,HttpServletResponse response,String tp) throws IOException{
		 
		  log.info(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"/auth/loginView.do");
		 // response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"/auth/loginout.do");
		
		  //如果是ajax请求，返回json对象
		  if(servletUtil.isAjaxRequest(request)){
			  pageRedirectByAjax(request, response, tp);
			  return;
		  }
		  
		  /**
		  * 页面跳转 /auth/loginView.do
		  */
		  PrintWriter out = response.getWriter();
	      out.println("<html>");
	      out.println("<script>");
	      out.println("window.open ('"+request.getContextPath()+"/auth/loginView.do?tp="+tp+"', '_top')");
	      out.println("</script>");
	      out.println("</html>");
	}
	
	/**
	 * 针对ajax的请求返回跳转到登录页处理
	 * @param request
	 * @param response
	 * @param tp
	 * @throws IOException
	 */
	public void pageRedirectByAjax(HttpServletRequest request,HttpServletResponse response,String tp) throws IOException{
		PrintWriter out = response.getWriter();
		String msg = "请重新登录";
		if("01".equals(tp)){//账号在其他地方登录
			msg = "登录超时，请重新登录！";
		}else if("02".equals(tp)){
			msg = "您的账号在其他地方登录，如果非本人操作请修改密码，保证账号安全！";
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("tp", tp);
		resMap.put("redirect", request.getContextPath()+"/auth/loginView.do");
		resMap.put("msg",msg);
		out.print(JUtil.toJsonString(resMap));
		out.flush();
	}

}