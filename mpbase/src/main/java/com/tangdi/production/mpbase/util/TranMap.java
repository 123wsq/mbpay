package com.tangdi.production.mpbase.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.ArgumentNullException;
import com.tangdi.production.mpbase.exception.TranException;

/**
 * 对HashMap的get方法进行封装。
 * @author zhengqianbg
 *
 * @param <K>
 * @param <V>
 */
public final class TranMap<K, V> extends HashMap<K, V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8500578538046992831L;

	/**
	 * 通过key获取map中的值,当key为空时,或key对应的value为空时返回NULL<br/>
	 * @param key     
	 * @return V
	 * @throws ArgumentNullException
	 */
	public String getValue(K key) {
		V v = super.get(key);
		if(v == null || v.toString().equals("")){
			return null;
		}
		return v.toString();
	}
	/**
	 * 返回整个Map数据,并将Map<String,Object> 转 Map<String,String>
	 * @return Map<K,String>
	 */
	public Map<K,String> getData(){
		Map<K,String> smap = new HashMap<K,String>();
		
		Iterator<Entry<K, V>> iterator = this.entrySet().iterator();
		for(;iterator.hasNext();){
			java.util.Map.Entry<K, V> entry = iterator.next();
			K key = entry.getKey();
			smap.put(key, this.getValue(key));
		}
		return smap;
	}
	
	public String getHexLen(String... keys){
		String hex = "";
		StringBuffer data = new StringBuffer();
		for (String key : keys) {
			if (this.containsKey(key) && this.get(key) != null &&
					!this.get(key).toString().trim().equals("")) {
				data.append(this.get(key));
			}
		}
		hex = getLen(data.toString(),4,1);
		return hex;
	}
	
	private static String getLen(String data,int len,int dev){
		String hex1 = Integer.toHexString(data.length()/dev).toString();
		String hex2 = "0000"+hex1;
		return hex2.substring(hex2.length()-len,hex2.length());
	}
	/**
	 * 从制定map中获取指定字段的长度
	 * @param map
	 * @param len 
	 * @param dev 
	 * @param keys
	 * @return hex
	 */
	public static String getHexLen(Map<String,Object> map,int len,int dev,String... keys){
		
		String hex = "";
		StringBuffer data = new StringBuffer();
		for (String key : keys) {
			if (map.containsKey(key) && map.get(key) != null &&
					!map.get(key).toString().trim().equals("")) {
				data.append(map.get(key));
			}
		}
		
		hex = getLen(data.toString(),len,dev);
		return hex;
	}
	

}
