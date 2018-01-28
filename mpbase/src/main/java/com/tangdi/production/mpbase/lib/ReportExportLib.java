package com.tangdi.production.mpbase.lib;

import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.tangdi.production.tdbase.report.ReportExport;
import com.tangdi.production.tdbase.util.DictUtils;

/**
 * 导出文件
 * @author zhengqiang
 *
 * @param <T>
 * 
 */
public class ReportExportLib<T> extends ReportExport<T> {
	private static final Logger log = LoggerFactory
			.getLogger(ReportExportLib.class);
	/**
	 * 外部调用方法
	 * @param title
	 * @param data
	 * @param head
	 * @param outPutPath
	 * @param filename
	 * @param suffix
	 * @return
	 */
	public long createFileToExcl(String title, List<T> data, LinkedHashMap<String, String> head, String outPutPath, String filename,String suffix,String[] sumData) {
		if(sumData != null && sumData.length >=1){
			log.debug("自定义报表生成中...");
			return createFileToExcl(title, data, head, outPutPath, filename,suffix ,"UTF-8", 0, 0, 0,sumData);
		}else{
			log.debug("通用报表生成中...");
			return createEXCLFile(title, data, head, outPutPath, filename,suffix);
		}
		
	}
	/**
	 * 具体生成文件方法
	 */
    @SuppressWarnings("unchecked")
	public long createFileToExcl(String title, List<T> data, 
			LinkedHashMap<String, String> head, 
			String outPutPath, String filename,
			String suffix, String encode,
			int type, int style, int cwidth,
			String[] sumData){
    	
		log.info("[title={},data={},head={},outPutPath={},filename={},suffix={},encode={},cwidth={}]", title, data.toArray(), head==null?"":head.toString(), outPutPath, filename,suffix, encode, cwidth);
		// 列宽 默认12
		if (cwidth <= 0) {
			cwidth = 12;
		}
		WritableWorkbook wwb = null;
		String[] headParam = null;
		File excelFile = null;
		Map<String,Object> map = null;
		Map<String,String> dict= null;
		try {
			// 创建Excel工作薄 new FileOutputStream();
			excelFile = new File(outPutPath + filename + suffix);
			File parent = excelFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			excelFile.createNewFile();
			wwb = Workbook.createWorkbook(new FileOutputStream(excelFile));
			// 添加工作表
			WritableSheet sheet = wwb.createSheet(title, 0);
			SheetSettings sheetSettings = sheet.getSettings();// 设置sheet表格式
			sheetSettings.setVerticalFreeze(2);
			
			
			if(head == null){
				for (T obj : data) {
					map = getColumnName(obj);
					break;
				}
				if(map != null ){
					head = (LinkedHashMap<String, String>) map.get(COLUMN);
					dict = (Map<String, String>) map.get(DICT);
				}
			}
			WritableFont fontHead = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat wc = new WritableCellFormat();
			wc.setFont(fontHead);
			if(map != null){
				//
				titleToExcl(title, cwidth, head.size(), sheet);
							
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				Label labelDate = new Label(0, 1, "清算日期", wc);
				Label labelData = new Label(1, 1, sumData[0], wc);

				// 将定义好的单元格添加到工作表中
				sheet.addCell(labelDate);
				sheet.addCell(labelData);
				
				//生成Head
				headParam = headToExcl(head,2,sheet);
			}

			
			//生成Data
			int rows = dataToExcl(data,3,dict,headParam,sheet);
			
			
			
			//写入统计
			sheet.addCell(new Label(0,rows+4, "消费总笔数", wc));
			sheet.addCell(new Label(1,rows+4, sumData[1], wc));
			
			sheet.addCell(new Label(2,rows+4, "消费总金额", wc));
			sheet.addCell(new Label(3,rows+4, sumData[2], wc));
			
			sheet.addCell(new Label(4,rows+4, "消费总手续费", wc));
			sheet.addCell(new Label(5,rows+4, sumData[3], wc));
			
			sheet.addCell(new Label(0,rows+5, "消费撤销总笔数", wc));
			sheet.addCell(new Label(1,rows+5, sumData[4], wc));
			
			sheet.addCell(new Label(2,rows+5, "消费撤销总金额", wc));
			sheet.addCell(new Label(3,rows+5, sumData[5], wc));
			
			sheet.addCell(new Label(4,rows+5, "消费撤销总手续费", wc));
			sheet.addCell(new Label(5,rows+5, sumData[6], wc));
			
			sheet.addCell(new Label(0,rows+7, "交易总笔数", wc));
			sheet.addCell(new Label(1,rows+7, sumData[7], wc));
			
			sheet.addCell(new Label(2,rows+7, "交易总金额", wc));
			sheet.addCell(new Label(3,rows+7, sumData[8], wc));
			
			sheet.addCell(new Label(4,rows+7, "交易总手续费", wc));
			sheet.addCell(new Label(5,rows+7, sumData[9], wc));
			
			sheet.addCell(new Label(6,rows+7, "结算金额", wc));
			sheet.addCell(new Label(7,rows+7, sumData[10], wc));

			// 写入数据
			wwb.write();
			log.info("Excel文件写入完成, 文件大小:{}", excelFile.length());
		} catch (Exception e) {
			log.error("Excel文件写入出错了," + e.getMessage(), e);
			return 0;
		} finally {
			if (wwb != null) {
				try {
					// 关闭文件
					wwb.close();
					log.info("Excel文件关闭");
				} catch (WriteException e) {
					log.error("Excel文件关闭出错了," + e.getMessage(), e);
				} catch (IOException e) {
					log.error("Excel文件关闭出错了," + e.getMessage(), e);
				}
			}
		}

		return excelFile.length();
    }
    
    /**
     * 生成格式文件 默认txt
     * @param data
     * @param head
     * @param outPutPath
     * @param filename
     * @param encode
     * @param type
     * @param suffix
     * @param regex 分隔符
     * @param regexSpe 是否特殊字符
     * @return
     */
	public long createFormatFile(List<T> data, String head, String tail,
			String outPutPath, String filename, String encode,String regex, boolean regexSpe) {
		log.info("[data={},outPutPath={},filename={},encode={}]", new Object[] {
				data.toArray(), outPutPath, filename, encode });
		String[] headParam = null;
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		Map<String, Object> map = null;
		LinkedHashMap<String, String> titles = null;
		Map<String, String> dict = null;
		String reg = "";
		
		try {
			if(regex == null || "".equals(regex)){
				regex = ",";
				reg = ",";
			}else if(regexSpe){
				reg = "\\" + regex;
			}
			
			csvFile = new File(outPutPath + filename);
			File parent = csvFile.getParentFile();
			if ((parent != null) && (!parent.exists())) {
				parent.mkdirs();
			}
			csvFile.createNewFile();
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), encode), 1024);

			if(head != null && !head.trim().equals("")){
				log.info("文件头写入开始...");
				csvFileOutputStream.write(head);
				log.info("文件头写入完成.");
				csvFileOutputStream.newLine();
			}
			
			Iterator<T> localIterator1 = data.iterator();
			if (localIterator1.hasNext()) {
				T obj =  localIterator1.next();
				map = getColumnName(obj);
			}

			if (map != null) {
				titles = (LinkedHashMap) map.get("field");
				dict = (Map) map.get("dict");
			}
			
			if (titles != null) {
				log.debug("表头数据：{}", titles.toString());
				headParam = new String[titles.size()];
				int indexHead = 0;
				for (Object iterator = titles.entrySet().iterator(); ((Iterator) iterator)
						.hasNext();) {
					Map.Entry<String, String> iteratorEntry = (Map.Entry) ((Iterator) iterator)
							.next();
					headParam[indexHead] = ((String) iteratorEntry.getKey());
					indexHead++;
				}
			}
			
			log.info("文件内容写入开始...");
			int size = data.size();
			int index = 1;
			for (T obj : data) {
				Class<? extends Object> clazz = obj.getClass();

				for (int i = 0; i < headParam.length; i++) {
					PropertyDescriptor pd = null;
					Method getMethod = null;
					Object value = null;
					try {
						pd = new PropertyDescriptor(headParam[i], clazz);
						getMethod = pd.getReadMethod();
						value = getMethod.invoke(obj, new Object[0]);
					} catch (Exception e) {
						log.error("headParam[{}]={}", Integer.valueOf(i),
								headParam[i]);
						log.error(e.getMessage(), e);
						return 0L;
					}
					String text = null;
					if (getMethod.getReturnType().getName()
							.equals(String.class.getName())) {
						if ((value == null)
								|| (value.toString().trim().equals(""))) {
							text = "";
						} else if (dict.containsKey(headParam[i])) {
							text = DictUtils.get(
											dict.get(headParam[i]),
											String.valueOf(value));
						} else {
							text = value.toString();
						}

					} else if ((value == null)
							|| (value.toString().trim().equals(""))) {
						text = "";
					} else if (dict.containsKey(headParam[i])) {
						text = DictUtils.get(
										dict.get(headParam[i]),
										String.valueOf(value));
					} else {
						text = String.valueOf(value);
					}

					text = text.replaceAll(reg, "");
					csvFileOutputStream.write(text);
					csvFileOutputStream.write(regex);
				}

				if (size != index) {
					csvFileOutputStream.newLine();
				}
			}
			log.info("文件内容写入完成.");
			
			if(tail != null && !tail.trim().equals("")){
				log.info("文件尾写入开始...");
				csvFileOutputStream.write(tail);
				log.info("文件尾写入完成.");
				csvFileOutputStream.newLine();
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			log.error("文件写入出错了," + e.getMessage(), e);
			return 0L;
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				log.error("文件关闭出错了," + e.getMessage());
				return 0L;
			}
		}

		return csvFile.length();
	}


}
