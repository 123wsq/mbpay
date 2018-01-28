package com.tangdi.production.mpaychl.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 通信模块返回代码转换
 * @author zhengqiang
 *
 */
public class ThirdCode {
	 
	private final static Map<String,String> map = new HashMap<String,String>();
	static{
		map.put("0", "正常返回");
		map.put("-1", "要发送的渠道不存在");
		map.put("1", "发送超时");
		map.put("2", "返回报文长度不正确");
		map.put("10", "连接异常");
	}
	
	public static String get(String code){
		return map.get(code) == null ? "":map.get(code);
	}
}
