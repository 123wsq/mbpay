package com.tangdi.production.mpbase.util;

import javax.servlet.http.HttpSession;

import com.tangdi.production.mpbase.exception.UIDException;
import com.tangdi.production.tdauth.bean.UAI;

/**
 * 获取用户信息
 * @author zhengqiang
 *
 */
public class UID {
	/**
	 * 获取用户UID
	 * @param session
	 * @return
	 * @throws UIDException
	 */
	public static String get(HttpSession session) throws UIDException{
		UAI uai = null;
		try{
			 uai = (UAI) session.getAttribute("UID");
		}catch(Exception e){
			throw new UIDException("获取UID异常!");
		}
		if(uai == null || uai.getId() == null){
			throw new UIDException("Session中UID为空!");
		}
		return uai.getId().toString();
	}
	/**
	 * 获取UAI
	 * @param session
	 * @return
	 * @throws UIDException
	 */
	public static UAI getUAI(HttpSession session) throws UIDException{
		UAI uai = null;
		try{
			 uai = (UAI) session.getAttribute("UID");
		}catch(Exception e){
			throw new UIDException("获取UID异常!");
		}
		if(uai == null || uai.getId() == null){
			throw new UIDException("Session中UID为空!");
		}
		return uai;
	}
	

}
