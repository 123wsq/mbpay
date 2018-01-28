package com.tangdi.production.mpbase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据格式化方法
 * @author zhengqiang
 *
 */
public class DataFormatUtil {

	/**
	 * 卡号屏蔽
	 * @param bankAccount
	 * @return 
	 */
	public static String getCardNo(String bankAccount){
		int length=bankAccount.length(); 
		String cno=bankAccount.substring(0, length-10)+"******"+bankAccount.substring(length-4);
		StringBuilder printcno = new StringBuilder();
		for(int i=0 ; i<cno.length();i++){
			if(i != 0 && i % 4 == 0){
				printcno.append("  ");
			}
			printcno.append(cno.substring(i, i+1));
		}
		return printcno.toString();
	}
	
	/**
	 * 格式化时间
	 * @param datetime
	 * @return
	 */
	public static String getDateTime(String datetime){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = null;
		try {
			now = sdf2.parse(datetime);
		} catch (ParseException e) {
			return "";
		}
		return sdf1.format(now);
		
	}
	
	
	
}