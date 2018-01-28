package com.tangdi.production.mpbase.util;

import java.util.Properties;

/**
 * 
 * @author yu_jk
 *  银联工具类
 */
public class UnionUtil {
     public static final String CPSMESSGE="CPSMESSGE";
     public static final String CPSCODE="CPSCODE_";
	
	/**
	 * 根据银联返回码取对应的返回信息
	 * @param pro
	 * @param cpscode
	 * @return
	 */
	public static String getCpsMessage(Properties pro,String cpscode){
		return pro.getProperty(CPSCODE+cpscode, cpscode);
	}
	
	/**
	 * 第三方状态码转换成消息字符
	 * @param pro
	 * @param cpscode 代码
	 * @param prefix 前缀
	 * @return String
	 */
	public static String getCpsMessage(Properties pro,String cpscode,String prefix){
		return pro.getProperty(prefix+"_"+cpscode, cpscode);
	}
	
	/**
	 * 替换金额左边的0
	 * @param s
	 * @return
	 */
	public static  String   replaseleft0(String s){
		String s1=s;
		int k=0;
		for(int i=0;i<s.length();i++){
		     if(!String.valueOf(s.charAt(i)).equals("0")){
		    	break; 
		     }
           k++;
		}
	    s1=s.substring(k);
	    return s1;
	}
}
