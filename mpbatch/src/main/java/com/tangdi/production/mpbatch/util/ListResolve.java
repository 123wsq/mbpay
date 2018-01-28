package com.tangdi.production.mpbatch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

public class ListResolve {
	private static Logger log = LoggerFactory.getLogger(ListResolve.class);
	public static List<Map<String,Object>> resolve(String filePath){
		List<Map<String,Object>> returnList = new ArrayList<Map<String, Object>>();
		//初始化dataMap
		Map<String,Object> dataMap = null;
		//初始化data
		String data = "";
		//初始化data1[]
		String[] firDatas = null;
		//初始化data2[]
		String[] secDatas = null;
		File listFile = new File(filePath);
		try {
			InputStream is = new FileInputStream(listFile);
			byte[] b =  new byte[(int)listFile.length()];
			is.read(b);
			is.close();
			data = new String(b);
			firDatas = data.split("\n");
			for(int i=1;i<firDatas.length;i++){
				secDatas = firDatas[i].split("\\|");
				dataMap = new HashMap<String,Object>();
				dataMap.put("merNo", secDatas[0]);
				dataMap.put("terNo", secDatas[1]);
				dataMap.put("tranTime", secDatas[2]);
				dataMap.put("tranSeq", secDatas[3]);
				dataMap.put("cardNo", secDatas[4]);
				dataMap.put("payNo", secDatas[5]);
				dataMap.put("payAmt", secDatas[6]);
				dataMap.put("incAmt", secDatas[7]);
				dataMap.put("settleDt", secDatas[8]);
				dataMap.put("refNo", secDatas[9]);
				if("POV".equals(secDatas[10])){
					dataMap.put("tranType", "1");
				}else{
					dataMap.put("tranType", "2");
				}
				returnList.add(dataMap);
			}
		} catch (Exception e) {
			log.info("解析对账文件异常！");
			log.info("解析对账文件异常："+e.getMessage());
		}
		return returnList;
	} 
}
