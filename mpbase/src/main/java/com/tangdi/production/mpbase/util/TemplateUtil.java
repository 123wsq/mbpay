package com.tangdi.production.mpbase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;


/**
 * 模版替换工具类
 * 
 * @author zhengqiang
 * 
 */

public class TemplateUtil {
	private static Logger log = LoggerFactory.getLogger(TemplateUtil.class);

	/**
	 * 短信模版数据替换
	 * 
	 * @param data
	 *            
	 * @param values
	 *            参数列表value值.
	 */
	public static String convert(Object data, String... values) throws TranException{
		log.info("模版数据开始替换...");
		if(data == null || data.equals("")){
			throw new TranException(ExcepCode.EX900000,"模板数据不能为空！");
		}
		String tempdata = data.toString();
		for (String value : values) {
			tempdata = tempdata.replaceFirst("\\{}", value);
		}
		log.info("模版数据开始替换完成.data={}",tempdata);
		return tempdata;
	}

	

}
