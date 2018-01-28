package com.tangdi.production.mpbase.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;


/**
 * Map中的必需字段效验
 * 
 * @author zhengqiang
 * 
 */

public class ParamValidate {
	private static Logger log = LoggerFactory.getLogger(ParamValidate.class);

	/**
	 * 进行效验数据,异常时抛出EX000000
	 * 
	 * @param param
	 *            需要效验的MAP
	 * @param keys
	 *            参数列表key值.
	 * @throws TranException
	 */
	public static void doing(Map<String, Object> param, String... keys) throws TranException {
		for (String key : keys) {
			if (!param.containsKey(key) || param.get(key) == null || param.get(key).toString().trim().equals("")) {
				throw new TranException(ExcepCode.EX900000,"必填字段效验不通过!",key);
			}
		}
	}

	/**
	 * @param parameters
	 * @param keys
	 *            需要重组map的key数组
	 * @return LinkedHashMap 按keys数组中的顺序排列
	 */
	public static Map<String, Object> recombinationMap(Map<String, Object> parameters, String... keys) {
		Map<String, Object> results = new LinkedHashMap<String, Object>();
		for (String key : keys) {
			if (parameters.containsKey(key)) {
				results.put(key, parameters.get(key));
			}
		}
		return results;
	}

}
