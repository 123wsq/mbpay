package com.tangdi.production.mpamng.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Sheet;
import jxl.Workbook;

public class ZDAnalysisExcelUtil {
	private static Logger log = LoggerFactory
			.getLogger(ZDAnalysisExcelUtil.class);
 
	public static List<Map<String,Object>> excel(String fileName){
		log.info("开始解析EXCEL文件...");
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		try {
			File file = new File(fileName); 
			Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			log.info("循环获取Excel表中的内容...");
			 for (int i = 1; i < sheet.getRows(); i++) {
	                for (int j = 0; j < sheet.getColumns(); j++) {  
	                	map = new HashMap<String, Object>();
	                	if("新大陆".equals(sheet.getCell(0, i).getContents().trim())){
	                		map.put("a","XDL");
	                	}else{
	                		map.put("a","XDL");
	                	}
	                	if("音频刷卡器".equals(sheet.getCell(1, i).getContents().trim())){
	                		map.put("b", "1");
	                	}else{
	                		map.put("b", "2");
	                	}
	                    map.put("c", sheet.getCell(2, i).getContents());
	                }  
	                data.add(map);
	            }  
		} catch (Exception e) {
			log.info("解析Excel文件异常！！！");
			e.printStackTrace();
		}
		return data;
	}
	public static void main(String[] args) {
		String fileName = "D:/20160118/excel1.xls";
		List<Map<String,Object>> data = excel(fileName);
		for(Map<String,Object> map:data){
			System.out.println(map);
		}
	}
}
