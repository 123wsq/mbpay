package com.tangdi.production.mpbase.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {
    
	public static HashMap<String,String> parser(String xml,String[] nodes){
	    
		HashMap<String,String> pdata =null;	
		InputStream is ;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    
		try {
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			Document document = documentBuilder.parse(is);
			//list(document);
			pdata = readXmlNode(document,nodes);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pdata ;
	}
	
	private static HashMap<String,String> readXmlNode(Document doc,String[] nodeName){
		HashMap<String,String> hm = new HashMap<String,String>();	
		NodeList nl=doc.getElementsByTagName("channelResponse");
	
		Element stu=(Element) nl.item(0);
		for(int i=0;i<nodeName.length;i++){
			Element name=(Element) stu.getElementsByTagName(nodeName[i]).item(0);
		//	hm.put(nodeName[i], name.getTextContent());
		}
		return hm ;
	}
	
	//遍历该xml文件
	@SuppressWarnings({ "unused", "static-access" })
	private static void list(Node node){
		if((node.getNodeType()==node.ELEMENT_NODE)){
			//System.out.println(node.getNodeName());
		}
		//取出node的子节点
		NodeList nodeList=node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			//再去显示
			Node n=nodeList.item(i);
			list(n);
		}
	}
	


}
