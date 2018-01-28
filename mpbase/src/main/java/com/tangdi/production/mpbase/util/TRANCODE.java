package com.tangdi.production.mpbase.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.bean.TranErrorCodeInf;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;

/**
 * 渠道错误消息代码
 * @author zhengqiang
 *
 */
public class TRANCODE {
	private static Logger  log = LoggerFactory.getLogger(TRANCODE.class);
	private static Map<String,TranErrorCodeInf> msgMap = null;
	
	static{
		if(msgMap == null)
			msgMap = new HashMap<String,TranErrorCodeInf>();
	}
	/**
	 * 获取所有消息代码
	 * @return
	 */
	public static Map<String,TranErrorCodeInf> GET(){
		return msgMap;
	}
	
	/**
	 * 获取消息码内容
	 * @param msgId 消息代码
	 * @return 消息内容
	 */
	public static String GET(String orgno,String msgId){
		if(msgMap.containsKey(orgno+msgId)){
			TranErrorCodeInf code = msgMap.get(orgno+msgId);
			log.info("交易机构代码[{}],系统错误代码[{}]对应第三方错误码[{}]。",orgno,code.getSid(),code.getTid());
			return code.getSid();
		}else{
			log.info("交易机构代码[{}],系统没有找到该[{}]对应错误码,返回交易失败[{}]。",orgno,msgId,MsgCT.TRAN_FAILD);
			return MsgCT.TRAN_FAILD;
		}
	}

	public static void put(Map<String,TranErrorCodeInf> msg){
		if(msgMap == null){
			msgMap = new HashMap<String,TranErrorCodeInf>();
		}
		msgMap.putAll(msg);
	}
	

}
