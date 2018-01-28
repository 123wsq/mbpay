package com.tangdi.production.mpaychl.trans.process;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.http.client.HttpRequestClient;
import com.tangdi.production.mpbase.http.client.HttpResp;
import com.tangdi.production.tdcomm.channel.OutProcess;
import com.tangdi.production.tdcomm.util.Msg;

/**
 * <b>乐搜短信渠道</b>
 * 
 * 
 * 渠道交易码：103001 ~ 103100
 * 
 * @author youdd 2015/11/17
 * 
 */
public class HttpJPOutProcess extends OutProcess {
	private static Logger log = LoggerFactory.getLogger(HttpJPOutProcess.class);

	/**
	 * http请求路径
	 */
	private String requestPath;


	/**
	 * http请求
	 */
	private HttpRequestClient httpRequestClient;



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
			
			String code = Msg.getItfCode();
			Map<String, Object> pmap = Msg.getdatas();
			
			log.info("PROCESS交易码:{}", code);
			log.info("接收到参数{}", pmap);
			
			
			HttpResp resp = null;
			String url = requestPath + "?username="+cdkey
									 + "&pwd="+password
									 + "&mobile="+pmap.get("phone").toString()
									 + "&content="+URLEncoder.encode(pmap.get("message").toString(),"utf-8");

			// 调用第三方接口
			try {
				
				resp = httpRequestClient.sendGet(url);
				
				//resp = httpRequestClient.sendPost(url, smsmap.getData(),connectTimeout, readTimeout, encoded);
				if (resp != null) {
					log.info("HttpResp->" + resp.toString());
					String xml = resp.getContent();
					log.info("第三方返回值:" + xml);
					String rsp = xml.substring(0,1);
					if("0".equals(rsp)){
						Msg.set("rsp", "00");
					}else{
						Msg.set("rsp", "02");
						throw new Exception(xml);
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
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public void setHttpRequestClient(HttpRequestClient httpRequestClient) {
		this.httpRequestClient = httpRequestClient;
	}

	public static void setLog(Logger log) {
		HttpJPOutProcess.log = log;
	}

	public void setCdkey(String cdkey) {
		this.cdkey = cdkey;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
