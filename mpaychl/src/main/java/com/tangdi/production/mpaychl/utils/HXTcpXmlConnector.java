package com.tangdi.production.mpaychl.utils;

import com.tangdi.production.tdcomm.interceptor.Interceptor;
import com.tangdi.production.tdcomm.tcp.listener.TdMessageInOut;
import com.tangdi.production.tdcomm.util.Msg;
import com.tangdi.production.tdcomm.util.TdWrapException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HXTcpXmlConnector implements Interceptor {
	private String host;
	private int port;
	private int timeOut = 30;
	private int preLen = 0;
	private String preLenType = "asc";
	protected final TdMessageInOut messginout = new TdMessageInOut();

	private static Logger logger = LoggerFactory
			.getLogger(HXTcpXmlConnector.class);
	Callable target;

	public int getPreLen() {
		return this.preLen;
	}

	public void setPreLen(int preLen) {
		this.preLen = preLen;
		this.messginout.setPreLen(preLen);
	}

	public String getPreLenType() {
		return this.preLenType;
	}

	public void setPreLenType(String preLenType) {
		this.preLenType = preLenType;
		this.messginout.setPreLenType(preLenType);
	}

	public Object call() throws Exception {
		Socket socket = createConnection(this.host, this.port);
		byte[] senddata = (byte[]) Msg.get("SENDMSG");
		logger.info("send data={}", new String(senddata));
		try {
			this.messginout.write(socket.getOutputStream(), senddata);
			byte[] recvbyte = this.messginout.read(socket.getInputStream());
			logger.info("recv data ={}", new String(recvbyte));
			parseXML(new String(recvbyte));
		} catch (Exception e) {
			throw TdWrapException.wrap(e);
		} finally {
			try {
				socket.close();
			} catch (Exception localException1) {
			}
		}
		return null;
	}

	private void parseXML(String xml) throws Exception {
		Element root;
		root = DocumentHelper.parseText(xml).getRootElement();
		Iterator iter = root.elementIterator();
		while (iter.hasNext()) {
			Element node = (Element) iter.next();
			Iterator headIter = node.elementIterator();
			while (headIter.hasNext()) {
				Element contentNode = (Element) headIter.next();
				Msg.set(contentNode.getName(), contentNode.getText());
				this.logger.info("[" + contentNode.getName() + "] = ["
						+ contentNode.getTextTrim() + "]");
			}
		}
	}

	private Socket createConnection(String host, int port)
			throws UnknownHostException, IOException {
		Socket socket = new Socket();
		if (this.timeOut > 0)
			socket.connect(new InetSocketAddress(host, port),
					this.timeOut * 1000);
		else
			socket.connect(new InetSocketAddress(host, port));
		if (this.timeOut > 0)
			socket.setSoTimeout(this.timeOut * 1000);
		socket.setTcpNoDelay(true);
		return socket;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeOut() {
		return this.timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public void setTarget(Callable target) {
		this.target = target;
	}

	public Callable getTarget() {
		return this.target;
	}
}
