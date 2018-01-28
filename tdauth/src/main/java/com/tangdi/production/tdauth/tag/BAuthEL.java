package com.tangdi.production.tdauth.tag;

import javax.servlet.http.HttpSession;

public class BAuthEL {
	public static String auth(HttpSession session){
		//UAI uai = (UAI) session.getAttribute("UID");
		return "00";
	}
}
