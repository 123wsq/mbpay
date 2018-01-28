package com.tangdi.production.mpbase.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigUtils {
	private static Map<String, Map<String,Map<String,String>>> configMap = new HashMap<String, Map<String,Map<String,String>>>();
	private static Logger  log = LoggerFactory.getLogger(ConfigUtils.class);
	public static Map<String,String> getConfig(String path,String key) throws Exception{
		log.info("configMap:"+configMap.toString());
		path=path.replace("/", "\\");//解决文件 路径符号引起的无法找到key的问题
		if(!configMap.containsKey(path)){
			log.error("不存在配置文件"+path);
			throw new Exception("不存在配置文件"+path);
		}else{
			Map<String,Map<String,String>> fileMap=configMap.get(path);
			if(fileMap.containsKey(key)){
				log.error("配置文件【"+path+"】中不存在"+key);
				throw new Exception("配置文件【"+path+"】中不存在"+key);
			}
			return fileMap.get(key);
		}
	}
	public static void pushFile(String path,Map<String,Map<String,String>> file){
		path=path.replace("/", "\\");//解决文件 路径符号引起的无法找到key的问题
		configMap.put(path.replace("", ""), file);
		log.debug("加载配置文件"+configMap.toString());
	}
}
