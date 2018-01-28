package com.tangdi.production.termmng.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求对象
 * 
 * @author zhengqiang
 */
public class HttpRequestClient {
	private static Logger log = LoggerFactory.getLogger(HttpRequestClient.class);
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

	public HttpRequestClient() {
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
	public HttpResp sendPost(String urlString, Map<String, String> params) throws IOException {
		return this.send(urlString, "POST", params, null, 0, 0, "");
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
		urlConnection.setConnectTimeout(connectTimeout);
		urlConnection.setReadTimeout(readTimeout);
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);

		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
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
	 * 
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

			log.debug("byteBufferSize{}",byteBufferSize);
			for (; (k = in.read(bt)) != -1; i++) {
				index = index + k;
				log.debug("index{}",index);
				log.debug("k{}",k);
				if (index >= (byteBufferSize-tempIndex)) { // 当最大容量超过缓冲区存储最大容量时,自动扩充原10倍容量大小
					log.debug("inter.........0");
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
					log.debug("inter.........1");
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

	
	public static void main(String[] rags){
		int byteSize = 9;
		int byteBufferSize = 5;
		byte[] bt = new byte[byteSize];
		ByteBuffer bb = ByteBuffer.allocate(byteBufferSize);
		int k = 0, i = 0;
		int index = 0, tempIndex = 0;
		StringBuffer temp = new StringBuffer();

		bt[0] = 0x01;
		bt[1] = 0x01;
		bt[2] = 0x01;
		bt[3] = 0x01;
		bt[4] = 0x01;
		bt[5] = 0x02;
		bt[6] = 0x02;
		bt[7] = 0x02;
		bt[8] = 0x03;
	    k = 9;
			index = index + k;
			log.debug("index{}",index);
			log.debug("k{}",k);
			if (index >= byteBufferSize) { // 当最大容量超过缓冲区存储最大容量时,自动扩充原10倍容量大小
				log.debug("inter.........0");
				int pindex = byteBufferSize-(index-k);
				byte[] tempByte = new byte[byteBufferSize];
				log.debug("{}",tempByte.length);
				bb.put(bt, 0,pindex);
				log.debug("{}","22222222222222222");
				System.arraycopy(bb.array(), 0, tempByte, 0, byteBufferSize);
				log.debug("{}","111111111111111");
				bb = ByteBuffer.allocate(byteBufferSize * 10);
				log.debug("{}",bb.array().length);
				
				bb.put(tempByte, 0, byteBufferSize);
				bb.put(bt, pindex, k-pindex);
				
				tempIndex = index;
				index = k;
				tempByte = null;
				log.debug("inter.........1");
			} else {
				bb.put(bt, 0, k);
				bb.position(tempIndex + index);
			}

			log.info("接收字节数[{}]:{}", i, k);
			bt = new byte[byteSize];
		}
		
	
}
