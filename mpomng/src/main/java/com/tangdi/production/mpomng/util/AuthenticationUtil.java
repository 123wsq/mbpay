package com.tangdi.production.mpomng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpomng.bean.Authentication;

public class AuthenticationUtil {
	private static final Logger log = LoggerFactory
			.getLogger(AuthenticationUtil.class);

	// 交易时间
	public static String getTranTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return sdf.format(new Date());
	}

	// 生成订单号(RA+(订单生成日期)yyyyMMddHHmmss,订单号不支持中文字符，且不可重复)
	public static String getMerchOrderId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());
		return "RA" + date;
	}

	// md5value=MD5(svcName+merId+ merchOrderId + tranTime+ payAcc + cardType
	// +key）
	public static String getMd5Value(Authentication entity) {
		StringBuffer sb = new StringBuffer();
		if (entity.getSvcName() != null) {
			sb.append(entity.getSvcName().trim());
		}
		if (entity.getMerId() != null) {
			sb.append(entity.getMerId().trim());
		}
		if (entity.getMerchOrderId() != null) {
			sb.append(entity.getMerchOrderId().trim());
		}
		if (entity.getTranTime() != null) {
			sb.append(entity.getTranTime().trim());
		}
		if (entity.getPayAcc() != null) {
			sb.append(entity.getPayAcc().trim());
		}
		if (entity.getCardType() != null) {
			sb.append(entity.getCardType().trim());
		}
		if (entity.getKey() != null) {
			sb.append(entity.getKey().trim());
		}
		return DigestUtils.md5Hex(sb.toString()).toUpperCase();
	}

	// 拼接要上传的json字符串
	public static String getJson(Authentication entity) {
		StringBuffer sb = new StringBuffer();
		if (entity.getSvcName() != null) {
			sb.append("svcName=" + entity.getSvcName().trim() + ";");
		}
		if (entity.getMerId() != null) {
			sb.append("merId=" + entity.getMerId().trim() + ";");
		}
		if (entity.getTranTime() != null) {
			sb.append("tranTime=" + entity.getTranTime().trim() + ";");
		}
		if (entity.getMerchOrderId() != null) {
			sb.append("merchOrderId=" + entity.getMerchOrderId().trim() + ";");
		}
		if (entity.getCardType() != null) {
			sb.append("cardType=" + entity.getCardType().trim() + ";");
		}
		if (entity.getPayAcc() != null) {
			sb.append("payAcc=" + entity.getPayAcc().trim() + ";");
		}
		if (entity.getPayPhone() != null) {
			sb.append("payPhone=" + entity.getPayPhone().trim() + ";");
		}
		if (entity.getMd5value() != null) {
			sb.append("md5value=" + entity.getMd5value().trim() + ";");
		}
		return sb.toString();
	}

	/**
	 * Json报文转换
	 * 
	 * @param param
	 * @return
	 */
	public static String transferToJson(String param) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String[] paramPairs = param.split(";");
		for (int i = 0; i < paramPairs.length; i++) {
			String kayname = paramPairs[i].split("=")[0];
			if (paramPairs[i].split("=").length == 1) {
				paraMap.put(kayname, "");
			} else {
				String valuename = paramPairs[i].split("=")[1];
				paraMap.put(kayname, valuename);
			}
		}
		return JUtil.toJsonString(paraMap);
	}

	/**
	 * 进行http post请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static String sendPost(String url, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new Exception("请求异常，稍后重试...");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
//		log.info("结果："+result);
//		result = new String(result.getBytes("gbk"),"utf-8");
		return result;
	}
}
