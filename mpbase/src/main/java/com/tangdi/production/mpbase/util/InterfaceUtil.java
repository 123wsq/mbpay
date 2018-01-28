package com.tangdi.production.mpbase.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.tdbase.util.MD5Util;

public class InterfaceUtil {

	private static Logger log = LoggerFactory.getLogger(InterfaceUtil.class);
	/**
	 * 校验是否非空
	 * @param mIntfData
	 * @param mCheck
	 * @return
	 */
	public static String checkEmpty(Map<String, Object> mIntfData, Map<String, Boolean> mCheck){
		String msg = "";
		for (Iterator<Entry<String, Boolean>> iterator = mCheck.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Boolean> iteratorEntry = iterator.next();
			if(iteratorEntry.getValue()){
				if(null == mIntfData.get(iteratorEntry.getKey())||"".equals(mIntfData.get(iteratorEntry.getKey()))){
					msg = iteratorEntry.getKey();
					break;
				}
			}
		}
		return msg;
	}
	
	/**
	 * 校验签名密码
	 * @param mIntfData
	 * @param mCheck
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkSign(Map<String, Object> mIntfData, Map<String, Boolean> mCheck,
			final String password)throws UnsupportedEncodingException {
		boolean checkRight = false;
		int mSize = mCheck.size();
		int index = 0;
		String sign = "";
		StringBuilder sIntf = new  StringBuilder();
		for (Iterator<Entry<String, Boolean>> iterator = mCheck.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Boolean> iteratorEntry = iterator.next(); index++;//遍历
			if(index < mSize && null != mIntfData.get(iteratorEntry.getKey())){
				sIntf.append(mIntfData.get(iteratorEntry.getKey())+"&");
			}else{
				sign = (String) mIntfData.get(iteratorEntry.getKey());
			}
		}
		sIntf.append(password);
		System.out.println("数字签名："+sIntf.toString()+"/"+MD5Util.encode(sIntf.toString().getBytes("GBK")));
		if(sign.equals(MD5Util.encode(sIntf.toString().getBytes("GBK")))){
			checkRight = true;
		}
		return checkRight;
	}
	
	/**
	 * 获取签名密码
	 * @param mIntfData
	 * @param mCheck
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String genIntfSign(Map<String, Object> mIntfData, Map<String, Boolean> mCheck,
			final String password)throws UnsupportedEncodingException {
		int mSize = mCheck.size();
		int index = 0;
		StringBuilder sIntf = new  StringBuilder();
		for (Iterator<Entry<String, Boolean>> iterator = mCheck.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Boolean> iteratorEntry = iterator.next(); index++;//遍历
			if(index < mSize && null != mIntfData.get(iteratorEntry.getKey())){
				sIntf.append(mIntfData.get(iteratorEntry.getKey())+"&");
			}
		}
		log.info("签名前的数据为{}", sIntf+"用户密码");
		sIntf.append(password);
		String sign = MD5Util.encode(sIntf.toString().getBytes("GBK"));
		log.info("签名后的数据为{}", sign);
		return sign;
	}
	
	/*public static void main(String args[]) throws UnsupportedEncodingException{
		String sign = null;
		try {
			sign = MD5Util.encode("123456789012345&01&0&1&%23456".getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merid", "123456789012345");
		map.put("lotterycode", "01");
		map.put("status", "0");
		map.put("size", "1");
		map.put("sign", sign);
		//System.out.println(checkEmpty(map,AgentLottyConstants.MAP_QUERYPERIOD));
		//System.out.println(checkSign(map,AgentLottyConstants.MAP_QUERYPERIOD,"%23456"));
		System.out.println(genIntfSign(map,AgentLottyConstants.MAP_QUERYPERIOD,"%23456"));
		//System.out.println(EncryptAndDecryptUtil.encrypt("111111"));
		//System.out.println(MD5Util.encode("8000000046&01&2014112&111111".toString().getBytes("GBK")));
		//System.out.println(MD5Util.encode("8000000046&01&2014112&2&111111".toString().getBytes("GBK")));
	}*/
}
