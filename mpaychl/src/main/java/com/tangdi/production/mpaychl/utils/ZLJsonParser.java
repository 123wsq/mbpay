package com.tangdi.production.mpaychl.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZLJsonParser {
	public static final Logger log = LoggerFactory
			.getLogger(ZLJsonParser.class);
	
	public static String convertMap2Json(Map<String, Object> dataMap){
		ObjectMapper objectMapper = null;
		ByteArrayOutputStream bos = null;
		JsonGenerator jsonGenerator = null;
		try {
			objectMapper = new ObjectMapper();
			bos = new ByteArrayOutputStream();
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					bos, JsonEncoding.UTF8);
			jsonGenerator.writeObject(dataMap);
			
			return bos.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {}
			}
			bos = null;
			
			if(!jsonGenerator.isClosed()){
				try {
					jsonGenerator.close();
				} catch (IOException e) {}
			}
			jsonGenerator = null;
			objectMapper = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void convertJson2Map(String jsonStr, Map<String, Object> dataMap){
		ObjectMapper objectMapper = null;
		try {
			objectMapper = new ObjectMapper();
			dataMap.putAll(objectMapper.readValue(jsonStr, Map.class));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			objectMapper = null;
		}
	}
	
	public static void main(String[] args){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");
		map.put("d", "4");
		System.out.println(convertMap2Json(map));
	}

}
