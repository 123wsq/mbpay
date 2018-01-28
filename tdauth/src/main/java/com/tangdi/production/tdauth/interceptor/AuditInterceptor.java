package com.tangdi.production.tdauth.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tangdi.production.tdauth.bean.AuditLogInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.service.AuditLogService;
import com.tangdi.production.tdauth.util.StaticUtils;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 审计拦截器，生成审计日志
 * 
 * @author songleiheng
 * 
 */
@Service
public class AuditInterceptor implements HandlerInterceptor {
	private static Logger log = LoggerFactory.getLogger(AuthMenuInterceptor.class);
	private static Map<String, String> auditMap = new HashMap<String, String>();
	@Autowired
	private AuditLogService auditLogService;

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		log.info("进入审计日志开始。。。");

		// 获取初始化变量 审计日志map
		auditMap = StaticUtils.auditMap;

		String url = request.getServletPath().substring(1);

		Object obj = request.getSession().getAttribute("UID");
		if (obj == null) {
			log.info("UID为空-未登录不拦截 ，离开审计");
			return true;
		}
		UAI uAI = (UAI) obj;
		String UID = uAI.getId();
		String userId = uAI.getUserId();
		String orgNo = uAI.getOrgId();

		if (UID == null || userId.equals("")) {
			log.info("未登录不拦截 ，进入离开审计");
			return true;
		}
		log.info("请求url:" + url);
		log.info("请求参数:" + StaticUtils.toJson(request.getParameterMap()));
		if (auditMap.containsKey(url)) {
			Random rand = new Random();
			// 审计记录主键 系统时间+线程编号+6位随机数
			String auditLogId = System.currentTimeMillis() + "" + Thread.currentThread().getId() + "" + rand.nextInt(999999);
			String ip = getIpAddr(request);
			String operDate = TdExpBasicFunctions.GETDATETIME("yyyyMMddHHmmss");
			String menuName = auditMap.get(url);
			request.setAttribute("auditLogId", auditLogId);
			AuditLogInf auditLog = new AuditLogInf(auditLogId, ip, menuName, UID.toString(), userId, operDate);
			auditLog.setOrgNo(orgNo);
			log.info("建立审计记录对象：auditLog:");
			log.info("auditLogId=" + auditLogId + " ip=" + ip + " menuName=" + menuName + " UID=" + UID + " userId=" + userId + " operDate=" + operDate);
			try {
				log.info("auditLogService==null?" + (auditLogService == null));
				int result = auditLogService.addEntity(auditLog);
				log.info("审计记录入库成功，共入库 " + result + " 条记录");
			} catch (Exception e) {
				log.error("审计报错:{}" , e.getMessage());
				return true;
			}
		}
		log.info("审计-请求拦截结束");
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}