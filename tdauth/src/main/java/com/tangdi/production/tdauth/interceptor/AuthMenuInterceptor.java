package com.tangdi.production.tdauth.interceptor;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.util.LoadPropertiesUtils;
/**
 * 权限拦截
 * @author songleiheng
 *
 */
@Service
public class AuthMenuInterceptor    implements HandlerInterceptor   
{
  private static final Logger log = LoggerFactory.getLogger(AuthMenuInterceptor.class); 

public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
	// TODO Auto-generated method stub
	  log.info("进入权限拦截。。。");
	  String url=request.getServletPath().substring(1);
	  
	  Object obj=request.getSession().getAttribute("UID");
	  if(obj==null){
		  log.info("UID为空-未登录不拦截 ，离开权限拦截");
		  return true;
	  }
	  UAI uAI=(UAI) obj;
	  Map<String, Boolean>menuAuth=uAI.getMenuAuth();
	  
	  Set<String> keySet=menuAuth.keySet();
	  
	  log.info("权限拦截-请求url:"+url);
	  
	  if(keySet.contains(url)){
		  if(menuAuth.get(url)){
			  log.info("权限拦截结束--该用户拥有此权限-通过");
			  return true;
		  }else{
			  log.info("权限拦截结束--该用户不拥有此权限-不通过");
			  return false;
		  }
	  }else{
		  log.info("权限拦截结束--请求地址不在拦截范围内-通过");
		  String limitLogPath=System.getProperty("user.dir")+File.separator+"log"+File.separator+"limit.log";
		  log.debug("需要添加权限url:"+url);
		  Properties prop=LoadPropertiesUtils.getproperties(limitLogPath);
		  prop.setProperty(url, "");
		  LoadPropertiesUtils.storeProperties(limitLogPath, prop);
		  return true;
	  }
}

public void postHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler, ModelAndView modelAndView)
		throws Exception {
	// TODO Auto-generated method stub
	
}

public void afterCompletion(HttpServletRequest request,
		HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
	// TODO Auto-generated method stub
	
}

}