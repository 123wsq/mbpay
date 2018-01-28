package com.tangdi.production.mpbase.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlBase {
	/**
	 * frame doc to XmlFile
	 * @param document
	 * @param filename
	 * @return
	 */
	 public static boolean doc2XmlFile(Document document,String filename) 
	    { 
	      boolean flag = true; 
	      try 
	       { 
	            /**  document create factory */ 
	             TransformerFactory tFactory = TransformerFactory.newInstance();    
	             Transformer transformer = tFactory.newTransformer();  
	            /** end */ 
//	            transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312"); 
//	             transformer.setOutputProperty("standalone", "yes");
//	             transformer.setOutputProperty("doctype-public", "configure");
//	             transformer.setOutputProperty("doctype-system", "configure.dtd");
//	             transformer.setOutputProperty("media-type", "SYSTEM");
	             DOMSource source = new DOMSource();  
	             source.setNode(document);
	             StreamResult result = new StreamResult(new File(filename));    
	             transformer.transform(source, result);  
	         }catch(Exception ex) 
	         { 
	             flag = false; 
	             ex.printStackTrace(); 
	         } 
	        return flag;       
	    }
	 
	 /**
	  * 
	  * @param filename name of the file which is for load ;
	  * @return
	  */
	 public static Document load(String filename) 
	    { 
	       Document document = null; 
	      try  
	       {  
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
//	            factory.setValidating(false);
	            DocumentBuilder builder=factory.newDocumentBuilder();  
	            
	            builder.setEntityResolver(new IgnoreDTDEntityResolver());
	            document=builder.parse(new File(filename)); 
	            document.normalize(); 
	       } 
	      catch (Exception ex){ 
	           ex.printStackTrace(); 
	       }   
	      return document; 
	    }
	 
	 	/**
	 	 * for example :<arg name="LogToPage" value="//192.168.0.148"/>
	 	 * @param node: 节点 (arg);
	 	 * @param str: String[] 键的字符串数组( new String[]{"name","value"} )
	 	 * @return :String[]对应值的字符串数组(new String[]{"LogToPage","//192.168.0.148"})，
	  	 */
	 	public static String[] queryAttrStr(Node node,String[] str){
//	 		System.out.println("node.getNodeType()+node.getNodeName()+node.getNodeValue():"+node.getNodeType()+node.getNodeName()+node.getNodeValue());
	 		NamedNodeMap nameNodeMap=node.getAttributes();
	 		for (int i = 0; i < nameNodeMap.getLength(); i++) {
	 			Node clNode=nameNodeMap.item(i);
	 			for (int j = 0; j < str.length; j++) {
	 				if(clNode.getNodeType()==Node.ATTRIBUTE_NODE&&clNode.getNodeName().equals(str[j])){
	 					str[j]=clNode.getNodeValue();
	 				}
				}
			}
	 		return str;
	 	}
//	 public static void main(String[] args) {
//		Document doc1=load("C:/test/server-env.xml");
//		Document doc=load("C:/server-env.xml");
//		doc2XmlFile(doc, "C:/server-env.xml");
//		File file=new File("C:/server-env.xml");
//	}
}
