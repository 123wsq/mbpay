package com.tangdi.production.mpaychl.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 获取SOAP消息头参数和值
 * @author zhengqiang
 */
public class SOAPHeaderParamUtil {
	
	private static final Logger log = LoggerFactory
			.getLogger(SOAPHeaderParamUtil.class);
	/**
	 * 根据属性名获取属性值
	 * @param fieldName 字段名称
	 * @param obj 
	 * @return
	 */
    private static Object getFieldValueByName(String fieldName, Object obj) {
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = obj.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(obj, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
            return null;  
        }  
    } 

    /**
     * 属性名(name)，属性值(value)
     * @param object
     * @return
     */
    public static Map<String,Object> getFileds(Object obj){
    	Field[] fields=obj.getClass().getDeclaredFields();
    	Map<String,Object> infoMap = new HashMap<String,Object>();
    	for(int i=0;i<fields.length;i++){
    		infoMap.put(fields[i].getName(), getFieldValueByName(fields[i].getName(), obj));
    	}
    	return infoMap;
    }

}
