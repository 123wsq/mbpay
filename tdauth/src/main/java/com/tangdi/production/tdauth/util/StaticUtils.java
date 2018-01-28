package com.tangdi.production.tdauth.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StaticUtils {
	/**
	 * 审计拦截树菜单。
	 */
	public static HashMap<String,String> auditMap=new HashMap<String, String>();
	
	
	
	public static String toJson(Map<String,String[]> map){
		StringBuilder sb = new StringBuilder();
		Set<String> key = map.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String k = (String) it.next();
            sb.append(k).append("=");
            String[] values = map.get(k);
            for(int i=0;i<values.length;i++){
            	sb.append(values[i]);
            	if(i != values.length-1){
            		sb.append(",");
            	}
            }
            sb.append(" ");
        }

		
		return sb.toString();
	}

}
