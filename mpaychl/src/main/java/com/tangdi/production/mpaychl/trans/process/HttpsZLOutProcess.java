package com.tangdi.production.mpaychl.trans.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.utils.ZLJsonParser;
import com.tangdi.production.mpbase.http.client.HttpResp;
import com.tangdi.production.mpbase.http.client.ZLHttpsRequestClient;
import com.tangdi.production.tdcomm.channel.OutProcess;
import com.tangdi.production.tdcomm.util.Msg;

/**
 * <b>中联支付渠道</b>
 * 
 * 
 * 渠道交易码：103001 ~ 103100
 * 
 * @author chenlibo 2015/12/10
 * 
 */
public class HttpsZLOutProcess extends OutProcess {
	private static Logger log = LoggerFactory.getLogger(HttpsZLOutProcess.class);

	/**
	 * http请求路径
	 */
	private String requestPath;
	/**
	 * 订单支付
	 */
	private String payAction;
	/**
	 * 创建订单
	 */
	private String createOrderAction;
	/**
	 * 获取令牌
	 */
	private String signAction;
	
	/**
	 * http撤销请求路径
	 */
	private String canelRequestPath;

	/**
	 * https请求
	 */
	private ZLHttpsRequestClient httpsRequestClient;

	public void run() {
		try {
			
			String code = Msg.getItfCode();
			Map<String, Object> pmap = Msg.getdatas();
			
			log.info("PROCESS交易码:{}", code);
			log.info("接收到参数{}", pmap);
			
			HttpResp resp = null;
			Map<String, Object> repJsonMap = null;
			
			try {
				String url = requestPath;
				String zlcod = pmap.get("ZL_RTRCOD").toString();
				pmap.remove("ZL_RTRCOD");
				
				if("0003".equals(zlcod)){ //消费撤销
					url = canelRequestPath;
					log.info("发送报文{}", pmap);
					
					// 调用第三方接口
					resp = httpsRequestClient.sendPostParams(url, pmap);
				}else{
					url = requestPath;
					if("9999".equals(zlcod)){  //签到
						url += this.signAction;
					}else if("0001".equals(zlcod)){  //创建订单
						url += this.createOrderAction;
					}else if("0002".equals(zlcod)){  //订单支付
						url += this.payAction;
					}
					String jsonStr = ZLJsonParser.convertMap2Json(pmap);
					if(jsonStr == null || "".equals(jsonStr)){
						throw new Exception("转换Json报文出错");
					}
					log.info("发送报文{}", jsonStr);
					
					// 调用第三方接口
					resp = httpsRequestClient.sendPostJson(url, jsonStr);
				}
				
				if (resp != null) {
					log.info("HttpResp->" + resp.toString());
					String resJson = resp.getContent();
					log.info("第三方返回值:" + resJson);
					repJsonMap = new HashMap<String, Object>();
					ZLJsonParser.convertJson2Map(resJson, repJsonMap);
					String rsp = (String) repJsonMap.get("code");
					
					if("00".equals(rsp)){
						Msg.set("rsp", "00");
						log.info("第三方返回成功！");
					}else{
						Msg.set("rsp", "02");
						throw new Exception(resJson);
					}
				} else {
					Msg.set("rsp", "04");
					log.error("第三方返回值为空对象:[HttpResp]!");
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				Msg.set("rsp", "02");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				Msg.set("rsp", "03");
			}finally{
				if(repJsonMap != null && repJsonMap.size() > 0){
					Msg.set(MsgSubST.ZL_RSP_NM, repJsonMap);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public ZLHttpsRequestClient getHttpsRequestClient() {
		return httpsRequestClient;
	}

	public void setHttpsRequestClient(ZLHttpsRequestClient httpsRequestClient) {
		this.httpsRequestClient = httpsRequestClient;
	}

	public static void setLog(Logger log) {
		HttpsZLOutProcess.log = log;
	}

	public void setCanelRequestPath(String canelRequestPath) {
		this.canelRequestPath = canelRequestPath;
	}

	public void setPayAction(String payAction) {
		this.payAction = payAction;
	}

	public void setCreateOrderAction(String createOrderAction) {
		this.createOrderAction = createOrderAction;
	}

	public void setSignAction(String signAction) {
		this.signAction = signAction;
	}
	
}
