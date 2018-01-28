package com.tangdi.production.mpbase.service.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpbase.bean.FileDownloadInf;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.lib.ReportExportLib;
import com.tangdi.production.mpbase.service.FileDownloadService;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.FileUtil;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
/**
 * 数据报表业务接口实现类
 * @author zhengqiang
 * 
 * @param <T> 
 */
@Service
public class ReportServiceImpl<T> implements FileReportService<T>{
	private static final Logger log = LoggerFactory
			.getLogger(ReportServiceImpl.class);
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Autowired
	private FileDownloadService fileDownloadService;
	
	@Value("#{mpbaseConfig}")
	private Properties prop;
	
	@Override
	public int report(List<T> data, String uid, String filename,String title, String type,
			String fileType,String[] cdata) throws Exception {
		if(MsgCT.FILE_TYPE_CSV.equals(fileType)){
			return reportFile(data,
					 filename, type, uid,fileType,".csv");
		}else if(MsgCT.FILE_TYPE_EXCEL.equals(fileType)){
			return reportExcel(data,
					 filename,title, type, uid,".xls",cdata);
		}else if(MsgCT.FILE_TYPE_TXT.equals(fileType)){
			return reportTXTFile(data,
					 filename, type, uid, fileType, ".txt");
		}
		return 1;
	}
	
	@Override
	public int reportZIP(String uid, String filename, String title,
			String type, String fileType, String[] cdata, Map<String,List<T>> datas)
			throws Exception {
		if(MsgCT.FILE_TYPE_EXCEL.equals(fileType)){
			return reportExcel(
					 filename,title, type, uid,".xls",cdata,datas);
		}
		return 1;
	}
	
	private int reportTXTFile(List<T> data,
			String filename,String type,String uid,String fileType,String suffix) throws Exception {
		log.debug("用户UID:{}",uid);
		String sno;
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		try {
			sno = date + seqNoService.getSeqNoNew("REPORT_NO_SEQ", "4", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("获取序列号失败！",e);
		}
		long  sstime = System.currentTimeMillis();
		ReportExportLib<T> report = new ReportExportLib<T>();
		String fileId = getFileName(report);
		String path_2 = prop.getProperty("FILE_DOWNLOAD_PATH_2") +
				date+"/"+fileId+suffix;
		String path_1 = prop.getProperty("FILE_DOWNLOAD_PATH_1") +
				date+"/";
		
		FileDownloadInf file = new FileDownloadInf();
		file.setdId(sno);
		file.setdSTime(TdExpBasicFunctions.GETDATETIME());
		file.setdFileName(fileId+suffix);
		file.setdPath(path_2);
		if(filename == null || filename.equals("")){
			filename = "密钥文件";
		}
		file.setdName(filename);
		file.setStatus(MsgCT.FILE_CREATE_ING);
		if(type == null || type.equals("")){
			type = "2";
		}
		file.setRPTType(type);
		file.setdType(fileType);
		file.setUid(uid);
		fileDownloadService.addEntity(file);

		long v = report.createKeyFile(data, fileId, null, path_1);
		String etime = TdExpBasicFunctions.GETDATETIME();
		//写入数据库
		long  estime = System.currentTimeMillis();
		file = new FileDownloadInf();
		file.setdId(sno);
		file.setdETime(etime);
		file.setdSize(String.valueOf(v));
		file.setdTime(String.valueOf(estime-sstime));
		if(v > 0){
			file.setStatus(MsgCT.FILE_CREATE_SUCCESS);
		}else{
			file.setStatus(MsgCT.FILE_CREATE_FAILED);
		}
		fileDownloadService.modifyEntity(file);
		
		if(v == 0){
			return 1;
		}
		return 0;
	}

	private int reportFile(List<T> data,
			String filename,String type,String uid,String fileType,String suffix) throws Exception {
		log.debug("用户UID:{}",uid);
		String sno;
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		try {
			sno = date + seqNoService.getSeqNoNew("REPORT_NO_SEQ", "4", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("获取序列号失败！",e);
		}
		long  sstime = System.currentTimeMillis();
		ReportExportLib<T> report = new ReportExportLib<T>();
		String fileId = getFileName(report);
		String path_2 = prop.getProperty("FILE_DOWNLOAD_PATH_2") +
				date+"/"+fileId+suffix;
		String path_1 = prop.getProperty("FILE_DOWNLOAD_PATH_1") +
				date+"/";
		
		FileDownloadInf file = new FileDownloadInf();
		file.setdId(sno);
		file.setdSTime(TdExpBasicFunctions.GETDATETIME());
		file.setdFileName(fileId+suffix);
		file.setdPath(path_2);
		if(filename == null || filename.equals("")){
			filename = "报表文件";
		}
		file.setdName(filename);
		file.setStatus(MsgCT.FILE_CREATE_ING);
		if(type == null || type.equals("")){
			type = "2";
		}
		file.setRPTType(type);
		file.setdType(fileType);
		file.setUid(uid);
		fileDownloadService.addEntity(file);

		long v = report.createCSVFile(data, null, path_1,fileId,"GB2312");
		String etime = TdExpBasicFunctions.GETDATETIME();
		//写入数据库
		long  estime = System.currentTimeMillis();
		file = new FileDownloadInf();
		file.setdId(sno);
		file.setdETime(etime);
		file.setdSize(String.valueOf(v));
		file.setdTime(String.valueOf(estime-sstime));
		if(v > 0){
			file.setStatus(MsgCT.FILE_CREATE_SUCCESS);
		}else{
			file.setStatus(MsgCT.FILE_CREATE_FAILED);
		}
		fileDownloadService.modifyEntity(file);
		
		if(v == 0){
			return 1;
		}
		return 0;
	}

	
	private int reportExcel(List<T> data,
			String filename,String title,String type,String uid,String suffix,String[] cdata) throws Exception {
		log.debug("用户UID:{}",uid);
		String sno;
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		try {
			sno = date  + seqNoService.getSeqNoNew("REPORT_NO_SEQ", "4", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("获取序列号失败！",e);
		}
		long  sstime = System.currentTimeMillis();
		ReportExportLib<T> report = new ReportExportLib<T>();
		String fileId = getFileName(report);
		String path_2 = prop.getProperty("FILE_DOWNLOAD_PATH_2") +
				date+"/"+fileId+suffix;
		String path_1 = prop.getProperty("FILE_DOWNLOAD_PATH_1") +
				date+"/";
		
		FileDownloadInf file = new FileDownloadInf();
		file.setdId(sno);
		file.setdSTime(TdExpBasicFunctions.GETDATETIME());
		file.setdFileName(fileId+suffix);
		file.setdPath(path_2);
		if(filename == null || filename.equals("")){
			filename = "报表文件";
		}
		file.setdName(filename);
		file.setStatus(MsgCT.FILE_CREATE_ING);
		if(type == null || type.equals("")){
			type = "2";
		}
		file.setRPTType(type);
		file.setdType(MsgCT.FILE_TYPE_EXCEL);
		file.setUid(uid);
		fileDownloadService.addEntity(file);
		if(title == null || title.equals("")){
			title = "数据报表";
		}
		long v = report.createFileToExcl(title, data, null, path_1, fileId,suffix,cdata);
		String etime = TdExpBasicFunctions.GETDATETIME();
		//写入数据库
		long  estime = System.currentTimeMillis();
		file = new FileDownloadInf();
		file.setdId(sno);
		file.setdETime(etime);
		file.setdSize(String.valueOf(v));
		file.setdTime(String.valueOf(estime-sstime));
		if(v > 0){
			file.setStatus(MsgCT.FILE_CREATE_SUCCESS);
		}else{
			file.setStatus(MsgCT.FILE_CREATE_FAILED);
		}
		fileDownloadService.modifyEntity(file);
		
		if(v == 0){
			return 1;
		}
		return 0;
	}
	/**
	 * 生成多个文件压缩包
	 * @param filename
	 * @param title
	 * @param type
	 * @param uid
	 * @param suffix
	 * @param cdata
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	private int reportExcel(
			String filename,String title,String type,String uid,String suffix,String[] cdata,Map<String,List<T>> datas) throws Exception {
		log.debug("用户UID:{}",uid);
		String sno;
		String zipsuffix = ".zip";
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		try {
			sno = date  + seqNoService.getSeqNoNew("REPORT_NO_SEQ", "4", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("获取序列号失败！",e);
		}
		long  sstime = System.currentTimeMillis();
		ReportExportLib<T> report = new ReportExportLib<T>();
		
		String zipName = getZipName(date);
		String path_2 = prop.getProperty("FILE_DOWNLOAD_PATH_2") +
				date+"/"+zipName+zipsuffix;
		String path_zip = prop.getProperty("FILE_DOWNLOAD_PATH_1") +
				date+"/";
		String path_1 =path_zip +zipName+"/";
		
		FileDownloadInf file = new FileDownloadInf();
		file.setdId(sno);
		file.setdSTime(TdExpBasicFunctions.GETDATETIME());
		file.setdFileName(zipName+zipsuffix);
		file.setdPath(path_2);
		if(filename == null || filename.equals("")){
			filename = "报表文件";
		}
		file.setdName(filename);
		file.setStatus(MsgCT.FILE_CREATE_ING);
		if(type == null || type.equals("")){
			type = "2";
		}
		file.setRPTType(type);
		file.setdType(MsgCT.FILE_TYPE_ZIP);
		file.setUid(uid);
		fileDownloadService.addEntity(file);
		if(title == null || title.equals("")){
			title = "数据报表";
		}
		long v = 0;
		Iterator<String> iterator = datas.keySet().iterator();
	    for(;iterator.hasNext();){
	    	//String fileId  = getFileName(report);
	    	title = iterator.next();
	    	String fileId = title;
	    	v = v + report.createFileToExcl(title, datas.get(title), null, path_1, fileId,suffix,cdata);	
	    }
		FileUtil.doZip(path_1, path_zip+zipName+zipsuffix);
		
		
		String etime = TdExpBasicFunctions.GETDATETIME();
		//写入数据库
		long  estime = System.currentTimeMillis();
		file = new FileDownloadInf();
		file.setdId(sno);
		file.setdETime(etime);
		file.setdSize(String.valueOf(v));
		file.setdTime(String.valueOf(estime-sstime));
		if(v > 0){
			file.setStatus(MsgCT.FILE_CREATE_SUCCESS);
		}else{
			file.setStatus(MsgCT.FILE_CREATE_FAILED);
		}
		fileDownloadService.modifyEntity(file);
		
		if(v == 0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 获取文件名称
	 * @param report
	 * @return
	 * @throws Exception
	 */
	private String getFileName(ReportExportLib<T> report) throws Exception{
		String fileId = report.getFileName();
		String filename =  seqNoService.getSeqNoNew("REPORT_FILENAME_SEQ", "4", "1");
		return fileId + filename ;
	}
	/**
	 * 获取ZIP名称
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private String getZipName(String date) throws Exception{
		String filename =  seqNoService.getSeqNoNew("REPORT_ZIPNAME_SEQ", "6", "1");
		return date + filename ;
	}

	@Override
	public int reportEsign(String uid) throws Exception {
		log.debug("用户UID:{}",uid);
		String sno;
		String zipsuffix = ".zip";
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		try {
			sno = date  + seqNoService.getSeqNoNew("REPORT_NO_SEQ", "4", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("获取序列号失败！",e);
		}
		long  sstime = System.currentTimeMillis();
		
		String zipName = getZipName(date);
		String path_2 = prop.getProperty("FILE_DOWNLOAD_PATH_2") +
				date+"/"+zipName+zipsuffix;
		String path_zip = prop.getProperty("FILE_DOWNLOAD_PATH_1") +
				date+"/";
		
		String path_1_ = prop.getProperty("PIC_SIGN_PATH_1").toString();
		String cpath = path_1_ + date +"/00/";
		
		log.debug("压缩签名文件路径={}",cpath);
		
		FileDownloadInf file = new FileDownloadInf();
		file.setdId(sno);
		file.setdSTime(TdExpBasicFunctions.GETDATETIME());
		file.setdFileName(zipName+zipsuffix);
		file.setdPath(path_2);

		file.setdName("电子签名文件");
		file.setStatus(MsgCT.FILE_CREATE_ING);
		file.setRPTType("0");
		file.setdType(MsgCT.FILE_TYPE_ZIP);
		file.setUid(uid);
		fileDownloadService.addEntity(file);
		
		FileUtil.doZip(cpath, path_zip+zipName+zipsuffix);
		
		
		String etime = TdExpBasicFunctions.GETDATETIME();
		//写入数据库
		long  estime = System.currentTimeMillis();
		file = new FileDownloadInf();
		file.setdId(sno);
		file.setdETime(etime);
		file.setdSize("0");
		file.setdTime(String.valueOf(estime-sstime));
		
		file.setStatus(MsgCT.FILE_CREATE_SUCCESS);
		fileDownloadService.modifyEntity(file);
	
		return 0;
	}
	
	public static void main(String[] args){
		try {
			FileUtil.doZip("F:\\00\\", "F:\\esign\\123.zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	 


}
