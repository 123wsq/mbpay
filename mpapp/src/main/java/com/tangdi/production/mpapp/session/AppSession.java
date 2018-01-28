package com.tangdi.production.mpapp.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端session管理
 * @author zhengqiang 2015/4/1
 *
 */
public class AppSession {
	private static Logger log = LoggerFactory.getLogger(AppSession.class);
	/**
	 * 定义一个session 用于存储用户登陆信息
	 */
	private static Map<String,Cust> custMap = new ConcurrentHashMap<String,Cust>();
	/**
	 * 登陆成功后保存用户信息
	 * @param cust
	 */
	public synchronized static void put(Cust cust){
		log.info("客户端登陆,客户信息:{}",cust.toString());
		custMap.put(cust.getCustId(), cust);
	}
	/**
	 * 效验是否超时
	 * @param custId 客户编号（商户编号）
	 * @return 0 未超时 1 超时 2 未登录 3异地登陆
	 */
	public static int validate(String custId,String mac){
		//检查登陆
		if(!custMap.containsKey(custId)){
			return 2;
		}
		//检查超时
		Cust cust = custMap.get(custId);
		log.info("客户端连接,客户信息:{}",cust.toString());
		if(cust.getIstimeout()){
			log.info("连接超时,清除客户缓存信息:{}",cust.getCustId());
			custMap.remove(custId);
			return 1;
		}
		//检查是否多设备同时登陆
		if(!cust.getSysTerNo().equals(mac)){
			return 3;
		}
		return 0;
	}
	
	
	
	

}
