package com.tangdi.production.mpbase.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.bean.MsgCodeInf;
import com.tangdi.production.mpbase.constants.ExcepCode;

/**
 * 消息代码
 * @author zhengqiang
 *
 */
public class MSGCODE {

	private static final Logger log = LoggerFactory
			.getLogger(MSGCODE.class);
	private static Map<String,MsgCodeInf> msgMap = null;
	
	static{
		msgMap = new HashMap<String,MsgCodeInf>();
	}
	/**
	 * 获取所有消息代码
	 * @return
	 */
	public static Map<String,MsgCodeInf> GET(){
		return msgMap;
	}
	
	/**
	 * 获取消息码内容
	 * @param msgId 消息代码
	 * @return 消息内容
	 */
	public static String GET(String msgId){
		if(msgMap.containsKey(msgId)){
			String content = msgMap.get(msgId).getMsgContent();
			log.info("消息码[{}={}]",msgId,content);
			return content;
		}else{
			log.info("未配置消息码内容,返回固定错误码消息[{}]",ExcepCode.EX900004);
			return msgMap.get(ExcepCode.EX900004).getMsgContent();
		}
	}
	/**
	 * 获取消息码内容
	 * @param msgId 消息代码
	 * @param flag 0 不显示给前端  1显示给前端
	 * @return
	 */
	public static String GET(String msgId,String flag){
		if(msgMap.containsKey(msgId)){
			if(msgMap.get(msgId).getMsgFlag().equals(flag)){
				return msgMap.get(msgId).getMsgContent();
			}else{
				return msgMap.get(ExcepCode.EX900001).getMsgContent();
			}
			
		}else{
			return msgMap.get(ExcepCode.EX900004).getMsgContent();
		}
	}
	
	public static void put(Map<String,MsgCodeInf> msg){
		if(msgMap == null){
			msgMap = new HashMap<String,MsgCodeInf>();
		}
		msgMap.putAll(msg);
	}
	

}
