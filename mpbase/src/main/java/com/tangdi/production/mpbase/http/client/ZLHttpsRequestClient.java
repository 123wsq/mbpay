package com.tangdi.production.mpbase.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求对象
 * 
 * @author zhengqiang
 */
public class ZLHttpsRequestClient {
	private static Logger log = LoggerFactory.getLogger(ZLHttpsRequestClient.class);
	private final static String DEFAULT_CONTENT_ENCODING = "utf-8";

	/**
	 * 缓冲区大小
	 */
	private final static int BYTEBUFFER_SIZE = 1024 * 5;
	/**
	 * 一次读出数据的大小
	 */
	private final static int BYTE_SIZE = 1024;

	/**
	 * 连接超时时间 60s
	 */
	private final static int C_TIMEOUT = 1000 * 60;
	/**
	 * 读超时时间 60s
	 */
	private final static int R_TIMEOUT = 1000 * 60;

	private int byteBufferSize;
	private int byteSize;

	public ZLHttpsRequestClient() {
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null, 0, 0, "");
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param ctimeout
	 *            连接超时时间
	 * @param rtimeout
	 *            读超时时间
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendGet(String urlString, int ctimeout, int rtimeout) throws IOException {
		return this.send(urlString, "GET", null, null, ctimeout, rtimeout, "");
	}

	/**
	 * 以GET发送Http请求
	 * 
	 * @param urlString
	 *            请求地址
	 * @param ctimeout
	 *            连接超时时间
	 * @param rtimeout
	 *            请求超时时间
	 * @param encoding
	 *            编码
	 * @return
	 * @throws IOException
	 */
	public HttpResp sendGet(String urlString, int ctimeout, int rtimeout, String encoding) throws IOException {
		return this.send(urlString, "GET", null, null, ctimeout, rtimeout, encoding);
	}
	/**
	 * 以POST发送Http请求
	 * 
	 * @param urlString
	 *            请求地址
	 * @param parameters
	 *            请求参数         
	 * @param ctimeout
	 *            连接超时时间
	 * @param rtimeout
	 *            请求超时时间
	 * @param encoding
	 *            编码
	 * @return
	 * @throws IOException
	 */
	public HttpResp sendPost(String urlString, Map<String, String> parameters,int ctimeout, int rtimeout, String encoding) throws IOException {
		return this.send(urlString, "POST", parameters, null, ctimeout, rtimeout, encoding);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendGet(String urlString, Map<String, String> params) throws IOException {
		return this.send(urlString, "GET", params, null, 0, 0, "");
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return this.send(urlString, "GET", params, propertys, 0, 0, "");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendPost(String urlString, Map<String, String> parameters) throws IOException {
		return this.send(urlString, "POST", null, null, 0, 0, "");
	}
	
	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendPostJson(String urlString, String jsonStr) throws IOException {
		return this.sendJson(urlString, "POST", jsonStr, null, 0, 0, "");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null, 0, 0, "");
	}
	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendPostParams(String urlString, Map<String, Object> params) throws IOException {
		return this.sendP(urlString, "POST", params, null, 0, 0, "");
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpResp sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return this.send(urlString, "POST", params, propertys, 0, 0, "");
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 *            请求地址
	 * @param method
	 *            请求方式
	 * @param parameters
	 *            请求参数
	 * @param propertys
	 * @param connectionTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            读超时时间
	 * @param encoding
	 *            编码格式
	 * @return
	 * @throws IOException
	 */
	private HttpResp send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys, int connectTimeout, int readTimeout, String encoding) throws IOException {
		HttpURLConnection urlConnection = null;

		if (connectTimeout <= 0) {
			connectTimeout = C_TIMEOUT;
		}
		if (readTimeout <= 0) {
			readTimeout = R_TIMEOUT;
		}
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		log.info("Http请求地址:" + urlString);
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(connectTimeout); // 连接超时时间
		urlConnection.setReadTimeout(readTimeout);// 读取结果超时时间
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);// 可写
		urlConnection.setDoInput(true);// 可读
		urlConnection.setUseCaches(false);// 取消缓存

		if (propertys != null){
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
		}
		
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) urlConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
		}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes(encoding));
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		HttpResp resp = new HttpResp();
		resp.setContentCollection(new Vector<String>());

		resp.setUrlString(urlString);
		resp.setDefaultPort(urlConnection.getURL().getDefaultPort());
		resp.setFile(urlConnection.getURL().getFile());
		resp.setHost(urlConnection.getURL().getHost());
		resp.setPath(urlConnection.getURL().getPath());
		resp.setPort(urlConnection.getURL().getPort());
		resp.setProtocol(urlConnection.getURL().getProtocol());
		resp.setQuery(urlConnection.getURL().getQuery());
		resp.setRef(urlConnection.getURL().getRef());
		resp.setUserInfo(urlConnection.getURL().getUserInfo());
		resp.setCode(urlConnection.getResponseCode());
		resp.setMessage(urlConnection.getResponseMessage());
		resp.setContentType(urlConnection.getContentType());
		resp.setMethod(urlConnection.getRequestMethod());
		resp.setConnectTimeout(urlConnection.getConnectTimeout());
		resp.setReadTimeout(urlConnection.getReadTimeout());

		String encode = "";
		if (encoding == null || encoding.equals("")) {
			encode = urlConnection.getContentEncoding();
			if (encode == null)
				encode = DEFAULT_CONTENT_ENCODING;
		} else {
			encode = encoding;
		}

		resp.setContentEncoding(encoding);
		return this.getData(urlConnection, encode, resp);
	}
	
	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 *            请求地址
	 * @param method
	 *            请求方式
	 * @param parameters
	 *            请求参数
	 * @param propertys
	 * @param connectionTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            读超时时间
	 * @param encoding
	 *            编码格式
	 * @return
	 * @throws IOException
	 */
	private HttpResp sendP(String urlString, String method, Map<String, Object> parameters, Map<String, String> propertys, int connectTimeout, int readTimeout, String encoding) throws IOException {
		HttpURLConnection urlConnection = null;

		if (connectTimeout <= 0) {
			connectTimeout = C_TIMEOUT;
		}
		if (readTimeout <= 0) {
			readTimeout = R_TIMEOUT;
		}
		if(null == encoding || "".equals(encoding)){
			encoding = DEFAULT_CONTENT_ENCODING;
		}
		
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		log.info("Http请求地址:" + urlString);
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(connectTimeout); // 连接超时时间
		urlConnection.setReadTimeout(readTimeout);// 读取结果超时时间
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);// 可写
		urlConnection.setDoInput(true);// 可读
		urlConnection.setUseCaches(false);// 取消缓存

		if (propertys != null){
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
		}
		urlConnection.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded;charset=" + encoding);
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) urlConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
		}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes(encoding));
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		HttpResp resp = new HttpResp();
		resp.setContentCollection(new Vector<String>());

		resp.setUrlString(urlString);
		resp.setDefaultPort(urlConnection.getURL().getDefaultPort());
		resp.setFile(urlConnection.getURL().getFile());
		resp.setHost(urlConnection.getURL().getHost());
		resp.setPath(urlConnection.getURL().getPath());
		resp.setPort(urlConnection.getURL().getPort());
		resp.setProtocol(urlConnection.getURL().getProtocol());
		resp.setQuery(urlConnection.getURL().getQuery());
		resp.setRef(urlConnection.getURL().getRef());
		resp.setUserInfo(urlConnection.getURL().getUserInfo());
		resp.setCode(urlConnection.getResponseCode());
		resp.setMessage(urlConnection.getResponseMessage());
		resp.setContentType(urlConnection.getContentType());
		resp.setMethod(urlConnection.getRequestMethod());
		resp.setConnectTimeout(urlConnection.getConnectTimeout());
		resp.setReadTimeout(urlConnection.getReadTimeout());

		String encode = "";
		if (encoding == null || encoding.equals("")) {
			encode = urlConnection.getContentEncoding();
			if (encode == null)
				encode = DEFAULT_CONTENT_ENCODING;
		} else {
			encode = encoding;
		}

		resp.setContentEncoding(encoding);
		return this.getData(urlConnection, encode, resp);
	}
	
	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 *            请求地址
	 * @param method
	 *            请求方式
	 * @param parameters
	 *            请求参数
	 * @param propertys
	 * @param connectionTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            读超时时间
	 * @param encoding
	 *            编码格式
	 * @return
	 * @throws IOException
	 */
	private HttpResp sendJson(String urlString, String method, String json, Map<String, String> propertys, int connectTimeout, int readTimeout, String encoding) throws IOException {
		HttpURLConnection urlConnection = null;

		if (connectTimeout <= 0) {
			connectTimeout = C_TIMEOUT;
		}
		if (readTimeout <= 0) {
			readTimeout = R_TIMEOUT;
		}
		if(null == encoding || "".equals(encoding)){
			encoding = DEFAULT_CONTENT_ENCODING;
		}
		
		log.info("Http请求地址:" + urlString);
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(connectTimeout); // 连接超时时间
		urlConnection.setReadTimeout(readTimeout);// 读取结果超时时间
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);// 可写
		urlConnection.setDoInput(true);// 可读
		urlConnection.setUseCaches(false);// 取消缓存

		if (propertys != null){
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
		}
		
		urlConnection.setRequestProperty("Content-type",
				"application/json;charset=" + encoding);
		
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) urlConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
		}

		if (method.equalsIgnoreCase("POST") && json != null) {
			urlConnection.getOutputStream().write(json.getBytes(encoding));
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		HttpResp resp = new HttpResp();
		resp.setContentCollection(new Vector<String>());

		resp.setUrlString(urlString);
		resp.setDefaultPort(urlConnection.getURL().getDefaultPort());
		resp.setFile(urlConnection.getURL().getFile());
		resp.setHost(urlConnection.getURL().getHost());
		resp.setPath(urlConnection.getURL().getPath());
		resp.setPort(urlConnection.getURL().getPort());
		resp.setProtocol(urlConnection.getURL().getProtocol());
		resp.setQuery(urlConnection.getURL().getQuery());
		resp.setRef(urlConnection.getURL().getRef());
		resp.setUserInfo(urlConnection.getURL().getUserInfo());
		resp.setCode(urlConnection.getResponseCode());
		resp.setMessage(urlConnection.getResponseMessage());
		resp.setContentType(urlConnection.getContentType());
		resp.setMethod(urlConnection.getRequestMethod());
		resp.setConnectTimeout(urlConnection.getConnectTimeout());
		resp.setReadTimeout(urlConnection.getReadTimeout());

		String encode = "";
		if (encoding == null || encoding.equals("")) {
			encode = urlConnection.getContentEncoding();
			if (encode == null)
				encode = DEFAULT_CONTENT_ENCODING;
		} else {
			encode = encoding;
		}

		resp.setContentEncoding(encoding);
		return this.getData(urlConnection, encode, resp);
	}

	/**
	 * 获取请求数据
	 * @param urlConnection
	 * @param encoding
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	private HttpResp getData(HttpURLConnection urlConnection, String encoding, HttpResp resp) throws IOException {

		try {
			InputStream in = urlConnection.getInputStream();
			if (byteBufferSize == 0) {
				byteBufferSize = BYTEBUFFER_SIZE;
			}
			if (byteSize == 0) {
				byteSize = BYTE_SIZE;
			}

			byte[] bt = new byte[byteSize];
			ByteBuffer bb = ByteBuffer.allocate(byteBufferSize);
			int k = 0, i = 0;
			int index = 0, tempIndex = 0;
			StringBuffer temp = new StringBuffer();
			
			for (; (k = in.read(bt)) != -1; i++) {
				index = index + k;
				if (index >= (byteBufferSize-tempIndex)) { 
					int pindex = byteBufferSize-(tempIndex+index-k);
					byte[] tempByte = new byte[byteBufferSize];
					bb.put(bt, 0,pindex);
					System.arraycopy(bb.array(), 0, tempByte, 0, byteBufferSize);
					bb = ByteBuffer.allocate(bb.capacity() * 2);
					log.info("缓冲区容量不足,自动扩充大小：{}*2",byteBufferSize);
					bb.put(tempByte, 0, byteBufferSize);
					bb.put(bt, pindex, k-pindex);
					
					tempIndex = byteBufferSize + k-pindex;
					index = 0;
					tempByte = null;
					byteBufferSize = bb.capacity();
					log.info("当前缓冲区容量为：{}b",bb.capacity());
				} else {
					bb.put(bt, 0, k);
					bb.position(tempIndex + index);
				}

				log.info("接收字节数[{}]:{}", i, k);
				bt = new byte[byteSize];
			}
			log.info("共接收字节数：{}b", tempIndex + index);

			temp.append(new String(bb.array(), encoding).trim());
			bb.clear();
			in.close();
			bb = null;
			resp.setContent(temp.toString());
			return resp;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

	public void setByteBufferSize(int byteBufferSize) {
		this.byteBufferSize = byteBufferSize;
	}

	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}

	
	public static void main(String[] args){
		
	}

}
