package com.tangdi.production.mpaychl.ws.client.header;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * WS公用消息头类,
 * 主要用于设置Soap消息头参数和报文日志输出。
 * 
 * @author zhengqiang  2014/07/23
 * 
 */
public class SOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static Logger log = LoggerFactory
			.getLogger(SOAPHeaderHandler.class);
	private Map<String, String> params = null;
	private String localName = null;
	private String prefix = null;
	private String namespace = null;

	/**
	 * 通过构造参数生成Header
	 * @param localName  标签名
	 * @param prefix  前缀
	 * @param namespace 命名空间
	 * @param map  消息头参数
	 */
	public SOAPHeaderHandler(String localName, 
								String prefix, 
								String namespace,
								Map<String, String> map) {
		this.params = map;
		this.localName = localName;
		this.prefix = prefix;
		this.namespace = namespace;
	}

	public boolean handleMessage(SOAPMessageContext context) {
		Boolean outboundProperty = (Boolean) context
				.get("javax.xml.ws.handler.message.outbound");

		ByteArrayOutputStream bo = null;
		if (outboundProperty.booleanValue()) {
			SOAPMessage message = context.getMessage();
			try {
				SOAPEnvelope envelope = context.getMessage().getSOAPPart()
						.getEnvelope();
				//设置前缀
			    envelope.setPrefix(prefix);
			    //删掉默认的命名空间
			    envelope.removeNamespaceDeclaration("S"); 
			 
				SOAPHeader header = envelope.addHeader();

				SOAPElement security = header.addChildElement(
						localName,"", namespace);

				/**
				 * 组消息头Head部分
				 */
				for (String key : params.keySet()) {
					SOAPElement username = security.addChildElement(key);
					username.addTextNode(params.get(key));
				}
                /**
                 * 输入报文到log中
                 */
				bo = new ByteArrayOutputStream();
				message.writeTo(bo);
				log.info(new String(bo.toByteArray()));

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				bo.reset();
				try {
					bo.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}

		} else {
			try {
				SOAPMessage message = context.getMessage();
				bo = new ByteArrayOutputStream();
				message.writeTo(bo);
				log.info(new String(bo.toByteArray()));
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			} finally {
				bo.reset();
				try {
					bo.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}

		}

		return outboundProperty.booleanValue();
	}

	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	public void close(MessageContext context) {
	}

	public Set<QName> getHeaders() {
		return null;
	}
}