package com.tangdi.production.mpbatch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZLDZFileResolve {
	private static Logger log = LoggerFactory.getLogger(ZLDZFileResolve.class);
	public static List<Map<String,Object>> resolve(String filePath){
		List<Map<String,Object>> returnList = new ArrayList<Map<String, Object>>();
		//初始化dataMap
		Map<String,Object> dataMap = null;
		String[] secDatas = null;
		File listFile = new File(filePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(listFile),"GBK"));
			String str = "";
			while((str = br.readLine()) != null){
				if(str.trim().equals("")){
					continue;
				}
				secDatas = str.split(",");
				dataMap = new HashMap<String,Object>();
				if("交易时间".equals(secDatas[0])){
					//表头
					continue;
				}
				dataMap.put("tranTime", secDatas[0]);
				dataMap.put("tranType", secDatas[1]);
				dataMap.put("merNo", secDatas[2]);
				dataMap.put("payAmt", covertAmt(secDatas[3].toString()));
				dataMap.put("fee", covertAmt(secDatas[4].toString()));
				dataMap.put("payNo", secDatas[5]);
				dataMap.put("opayNo", secDatas[6]);
				dataMap.put("mpayNo", secDatas[7]);
				dataMap.put("cardType", secDatas[8]);
				dataMap.put("issName", secDatas[9]);
				dataMap.put("cardNo", secDatas[10]);
				returnList.add(dataMap);
			}
			
		} catch (Exception e) {
			log.info("解析对账文件异常！");
			log.info("解析对账文件异常："+e.getMessage());
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return returnList;
	} 
	
	/**
	 * 元转化为分
	 * @param amt
	 * @return
	 */
	public static String covertAmt(String amt){
		if(amt == null){
			return null;
		}
		amt = amt.trim();
		int dotInx = amt.indexOf(".");
		if(dotInx < 0){ //整数
			return amt + "00";
		}else{
			return amt.substring(dotInx+1).length()==2?
					amt.replaceAll("\\.", ""):amt.replaceAll("\\.", "")+"0";
		}
	}
	
	public static void main(String[] args) throws ParseException{
		String d = new SimpleDateFormat("yyyy-MM-dd").format(
				new SimpleDateFormat("yyyyMMdd").parse(
						"20151228"));
		System.out.println(d);
		System.out.println(covertAmt("1001.01"));
	}
}
