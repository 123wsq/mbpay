package com.tangdi.production.mpcoop.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import jxl.Sheet;
import jxl.Workbook;

public class JGAnalysisExcelUtil {
	private static Logger log = LoggerFactory
			.getLogger(JGAnalysisExcelUtil.class);
	public static List<CooporgMerInf> excel(String fileName){
		log.info("开始解析EXCEL文件...");
		List<CooporgMerInf> data = new ArrayList<CooporgMerInf>();
		CooporgMerInf cooporgMerInf= null;
		try {
			File file = new File(fileName); 
			Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			log.info("循环获取Excel表中的内容...");
			 for (int i = 1; i < sheet.getRows(); i++) {
	                for (int j = 0; j < sheet.getColumns(); j++) {  
	                	cooporgMerInf = new CooporgMerInf();
	                	cooporgMerInf.setCoopName(sheet.getCell(0, i).getContents());
	                    cooporgMerInf.setMerNo(sheet.getCell(1, i).getContents());
	                    cooporgMerInf.setTerNo(sheet.getCell(2, i).getContents());
	                    cooporgMerInf.setMerName(sheet.getCell(3, i).getContents());
	                    cooporgMerInf.setRateType(sheet.getCell(4, i).getContents());
	                    cooporgMerInf.setmType(sheet.getCell(5, i).getContents());
	                    cooporgMerInf.setMerKey(sheet.getCell(6, i).getContents());
	                    cooporgMerInf.setCheckValue(sheet.getCell(7, i).getContents());
	                    cooporgMerInf.setDtLimit(sheet.getCell(8, i).getContents());
	                    cooporgMerInf.setMtLimit(sheet.getCell(9, i).getContents());
	                    cooporgMerInf.setLowLimit(sheet.getCell(10, i).getContents());
	                    cooporgMerInf.setHighLimit(sheet.getCell(11, i).getContents());
	                    cooporgMerInf.setRateTop(sheet.getCell(12, i).getContents());
	                    cooporgMerInf.setRateTypeTop(sheet.getCell(13, i).getContents());
	                    cooporgMerInf.setRateT0(sheet.getCell(14, i).getContents());
	                    cooporgMerInf.setRateT1(sheet.getCell(15, i).getContents());
	                    cooporgMerInf.setProvinceName(sheet.getCell(16, i).getContents());
	                }  
	                data.add(cooporgMerInf);
	            }  
		} catch (Exception e) {
			log.info("解析Excel文件异常！！！");
			e.printStackTrace();
		}
		return data;
	}
}
