package com.tangdi.production.mpbase.message;

import java.util.HashMap;
import java.util.Map;

import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdbase.util.MD5Util;



/**
 * 消息工具类
 * @author yu_jk
 *
 */
public class Msg {

	/**
	 * RspMessage对象转换成json字符串
	 * @param rspmessages
	 * @return String
	 */
	public static String getRspJson(RspMsg rspmsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(MessageConstants.REP_BODY, rspmsg.getBody());
		rspmsg.getHead().put(MessageConstants.SIGN_MESSAGE, 
				MD5Util.digest(rspmsg.getBody().toString()));
		map.put(MessageConstants.REP_HEAD, rspmsg.getHead());
		return JUtil.toJsonString(map);
	}
	

	/**
	 * ReqMessage对象转换成json字符串
	 * @param reqmessages
	 * @return String
	 */
	public static String getReqJson(ReqMsg reqmessages) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MessageConstants.REQ_HEAD, reqmessages.getHead());
		map.put(MessageConstants.REQ_BODY, reqmessages.getBody());
		return JUtil.toJsonString(map);
	}
	/**
	 * Map对象转换成json字符串
	 * @param map
	 * @return String
	 */
	public static String getJson(Map<String, Object> map) {
		return JUtil.toJsonString(map);
	}

	/**
	 * json字符串转换成ReqMessage对象
	 * @param json
	 * @return ReqMessage
	 */
	public static ReqMsg getReqMessage(String json) {
		Map<String, Object> map = JUtil.toMap(json);
		ReqMsg reqmessage = new ReqMsg(
				(Map<String, Object>) map.get(MessageConstants.REQ_HEAD),
				(Map<String, Object>) map.get(MessageConstants.REQ_BODY));
		return reqmessage;
	}
	
	/**
	 * json字符串转换成map对象
	 * @param json
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> getMap(String json) {
		return JUtil.toMap(json);
	}
		
	

	public static void main(String args[]) {
      RspMsg rspmessage=new RspMsg();
      rspmessage.setHeadV("TRAN_PROCESS", "955599");
      rspmessage.setDataV("TELPHONE", "18701017138");
      rspmessage.setDataV("AMT", "50.00");
      System.out.println(getRspJson(rspmessage));
	}
}
