package com.tangdi.production.mpaychl.trans.process;

import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import com.tangdi.production.mpbase.http.client.HttpRequestClient;
import com.tangdi.production.mpbase.http.client.HttpResp;
import com.tangdi.production.mpbase.util.TranMap;
import com.tangdi.production.tdcomm.channel.OutProcess;
import com.tangdi.production.tdcomm.util.Msg;
import com.tangdi.production.tdcomm.util.Xml;

/**
 * <b>亿美短信渠道</b>
 * 
 * 
 * 渠道交易码：103001 ~ 103100
 * 
 * @author zhengqiang 2015/04/15
 * 
 */
public class HttpYMOutProcess extends OutProcess {
	private static Logger log = LoggerFactory.getLogger(HttpYMOutProcess.class);

	/**
	 * http请求路径
	 */
	private String requestPath;

	/**
	 * Http连接超时时间
	 */
	private int connectTimeout;
	/**
	 * Http读超时时间
	 */
	private int readTimeout;

	private String encoded;

	/**
	 * http请求
	 */
	private HttpRequestClient httpRequestClient;

	/**
	 * 版本号
	 */
	private String version;
	
	private String suffix;

	/**
	 * 用户序列号
	 */
	private String cdkey;
	/**
	 * 用户密码
	 */
	private String password;

	public void run() {
		try {
			log.info("亿美短信通道版本:{}",version);
			String code = Msg.getItfCode();
			Map<String, Object> pmap = Msg.getdatas();
			
			log.info("PROCESS交易码:{}", code);
			log.info("接收到参数{}", pmap);
			
			pmap.put("cdkey", cdkey);
			pmap.put("password", password);
			
			HttpResp resp = null;
			String url = requestPath + code + suffix;

			// 调用第三方接口
			try {
				TranMap<String,Object> smsmap = new TranMap<String,Object>();
				smsmap.putAll(pmap);
				resp = httpRequestClient.sendPost(url, smsmap.getData(),connectTimeout, readTimeout, encoded);
				if (resp != null) {
					log.info("HttpResp->" + resp.toString());
					
					String xml = resp.getContent();
					Xml.begin();
					Xml.pushWithroot(xml);
					log.info("第三方返回值:" + xml);
					getUnpack().call();
					
					Msg.set("rsp", "00");
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
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setHttpRequestClient(HttpRequestClient httpRequestClient) {
		this.httpRequestClient = httpRequestClient;
	}

	public static void setLog(Logger log) {
		HttpYMOutProcess.log = log;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setCdkey(String cdkey) {
		this.cdkey = cdkey;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
