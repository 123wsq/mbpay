package com.tangdi.production.mpbase.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求消息实体类
 * @author yu_jk
 *
 */
public class ReqMsg implements Serializable {
   
	private Map<String,Object> headmap=new HashMap<String, Object>();
	private Map<String,Object> bodymap=new HashMap<String, Object>();
    
	public void setHeadV(String key,Object v){
		headmap.put(key, v);
	}
	
	public ReqMsg(Map<String, Object> headmap, Map<String, Object> bodymap) {
		super();
		this.headmap = headmap;
		this.bodymap = bodymap;
	}

	public Object getHeadV(String key){
		return headmap.get(key);
	}
	
	public void setDataV(String key,Object v){
		bodymap.put(key, v);
	}
	
	public Object getdataV(String key){
		return bodymap.get(key);
	}
	
	public Map<String,Object> getHead(){
		return headmap;
		
	}
	
	public Map<String,Object> getBody(){
		return bodymap;
		
	}
	
}
