package com.tangdi.production.mpaychl.ws.client.header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WS公用消息头类, SOAP消息头
 * @author zhengqiang 2014/07/23
 *
 */
public class SOAPHeaderHandlerResolver implements HandlerResolver {
	private static Logger log = LoggerFactory
			.getLogger(SOAPHeaderHandlerResolver.class);
	private SOAPHeaderHandler soapHeaderHandler = null;

	public SOAPHeaderHandlerResolver(String localName, 
								String prefix, 
								String namespace,
								Map<String, String> map) {
		log.info("参数[localName={},prefix={},namespace={},map={}]",localName,prefix,namespace,map.toString());
		soapHeaderHandler = new SOAPHeaderHandler(localName,prefix,namespace,map);
	}
	
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(soapHeaderHandler);
		return handlerChain;
	}
}