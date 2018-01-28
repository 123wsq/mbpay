package com.tangdi.production.tdauth.bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 保存用户已登陆的session
 * @author zhengqiang
 *
 */
public class UserAppSession {
	private static Logger  log = LoggerFactory.getLogger(UserAppSession.class);
	/**
	 * 存储所有在线用户的信息
	 */
	private static  Map<String,HttpSession> userMap = null; 
	
	/**
	 * 初始化session
	 */
	public static void init(){
		userMap = new HashMap<String,HttpSession>();
		log.info("初始化SESSION OK.");
	}


	
    /**
     * 获取所有用户
     * @return
     */
	public static Map<String, HttpSession> getUserMap() {
		return userMap;
	}
	/**
	 * 保存SESSION信息
	 * @param id 
	 * @param session
	 */
	public static void setUserSession(String id,HttpSession session){
		/*
		if(userMap.containsKey(id)){
			HttpSession hs = userMap.get(id);
			if(hs != null){
				log.info("用户重复登陆,旧SESSION移除:{},{}",id,((UAI)hs.getAttribute("UID")).toString());
				hs.invalidate();
				
			}
		}
		*/
		userMap.put(id, session);
		log.info("用户SESSION加入:{},{}",id,((UAI)session.getAttribute("UID")).toString());
	}
	
	

	

}
