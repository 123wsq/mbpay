package com.tangdi.production.mpbase.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpbase.util.FileCopyUtil;
import com.tangdi.production.mpbase.util.PICHandler;
import com.tangdi.production.mpbase.bean.FileInf;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.dao.FileUploadDao;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.FileUploadUtil;
import com.tangdi.production.tdbase.util.FileUtils;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 文件上传接口实现类
 * @author zhengqiang
 *
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadServiceImpl.class);
	@Autowired
	private GetSeqNoService seqNoService;
	@Autowired
	private FileUploadDao dao ;
	
	@Value("#{mpbaseConfig}")
	private Properties prop;
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addFile(FileInf file) throws Exception {
		int rt = 0;
		try{
			log.debug("写入数据:{}",file.toString());
			rt = dao.insertEntity(file);
		}catch(Exception e){
			throw new Exception("文件写入数据库失败!"+e.getMessage(),e);
		}
		if(rt <=0){
			throw new Exception("文件写入数据库成功!");
		}
		
		return rt;
	}


	@Override
	public String getFilePath(String fid, int type) throws Exception {
		log.debug("文件ID:{}",fid);
		if(null == fid || "".equals(fid)){
			throw new Exception("文件ID不能为空！");
		}
		String path = "";
		FileInf fileInf = dao.selectEntity(new FileInf(fid));
		
		path =  fileInf.getFjPath();
		log.debug("文件路径:{}",path);
		return getRemotePath(path,type) ;
	}
	
	@Override
	public HashMap<String,Object> upload(MultipartFile MFile,String uid) throws Exception {
		String FID = TdExpBasicFunctions.GETDATETIME("YYMMDD") + 
			seqNoService.getSeqNoNew("FILE_ID_SEQ", "6", "1");
		String path = prop.getProperty("FILE_UPLOAD_PATH_1") +
				TdExpBasicFunctions.GETDATETIME("YYMMDD")+"/";
		
		//文件上传
		String filename = FileUploadUtil.fileUp(MFile, path, FID);
		
		FileInf file = new FileInf();

		log.debug("生成文件ID：[{}]",FID);
		file.setId(FID);
		file.setFjPath(path+filename);
		file.setDx(MFile.getSize());
		file.setFjName(MFile.getOriginalFilename());
		file.setOrderNum("0");
		file.setSfsx("0");  //0生效
		file.setFjo(uid);
		file.setFjt(TdExpBasicFunctions.GETDATETIME());
		file.setLx(MFile.getContentType());
		String sFileType = FileUtils.getFileType(MFile.getOriginalFilename());
		if(sFileType.toUpperCase().equals("JPG") 
				|| sFileType.toUpperCase().equals("GIF")
				|| sFileType.toUpperCase().equals("PNG")){
			int width = 0;
			int hight = 0;
			BufferedImage sourceImg =ImageIO.read(MFile.getInputStream());
			width = sourceImg.getWidth();
			hight = sourceImg.getHeight();
			file.setDpi(width + " x " +hight);
		}
		addFile(file);
		HashMap<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("fid", FID);
		rmap.put("fname", file.getFjName());
		rmap.put("fsize", file.getDx());
		return rmap;
	}

	@Override
	public HashMap<String, Object> upload(String img,int type) throws Exception {
		String path   = "";
		String path_1 = "";
		String path_2 = "";
		
		String date   = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		String fileName = TdExpBasicFunctions.GETDATETIME()+TdExpBasicFunctions.RANDOM(2, "4");
		String suffix = ".jpg";
		if(MsgCT.PIC_TYPE_CERT == type){       //身份证
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CERT_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CERT_2"));
			 suffix = MsgCT.PIC_SUFFIX_JPG;
			 path = date+"/"+fileName;
		}else if(MsgCT.PIC_TYPE_CARD == type){ //银行卡
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CARD_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CARD_2"));
			 suffix = MsgCT.PIC_SUFFIX_JPG;
			 path = date+"/"+fileName;
		}else if(MsgCT.PIC_TYPE_ESIGN == type){ //电子签名
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_1")) ;
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_2")) ;
			 suffix = MsgCT.PIC_SUFFIX_PNG;
			 path = date+"/"+fileName;
		}else{
			return null;
		}
		

		log.info("获取文件物理路径配置:[path_1={},path_2={},fileName={}]",path_1,path_2,fileName);
		boolean bool= PICHandler.generateImage(img, path_1+path);
		log.info("文件处理结果：[{}]",bool);
		if(!bool){
			return null;
		}else{
			String FID = TdExpBasicFunctions.GETDATETIME("YYMMDD") + 
					seqNoService.getSeqNoNew("FILE_ID_SEQ", "6", "1");
			String filePath= path_1+path;
			FileInf file = new FileInf();
			File MFile=new File(filePath);
			FileInputStream fis=new FileInputStream(MFile);
			log.debug("生成文件ID：[{}]",FID);
			file.setId(FID);
			file.setFjPath(filePath);
			file.setDx(MFile.length());
			file.setFjName(fileName+suffix);
			file.setOrderNum("0");
			file.setSfsx("0");  //0生效
			file.setFjo("");
			file.setFjt(TdExpBasicFunctions.GETDATETIME());
			file.setLx("image/jpeg");
			String sFileType = FileUtils.getFileType(path);
			if(sFileType.toUpperCase().equals("JPG")){
				int width = 0;
				int hight = 0;
				BufferedImage sourceImg =ImageIO.read(fis);
				width = sourceImg.getWidth();
				hight = sourceImg.getHeight();
				file.setDpi(width + " x " +hight);
			}
			addFile(file);
			HashMap<String,Object> rmap = new HashMap<String,Object>();
			rmap.put("fid", FID);
			return rmap;
		}
	}


	@Override
	public String getRemotePath(String dbpath,int type) throws Exception {
		String path = "",path_1="",path_2="";
		if(MsgCT.PIC_TYPE_CERT == type){       //身份证
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CERT_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CERT_2"));
		}else if(MsgCT.PIC_TYPE_CARD == type){ //银行卡
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CARD_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CARD_2"));
		}else if(MsgCT.PIC_TYPE_ESIGN == type){ //电子签名
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_2"));	 
		}else if(MsgCT.PIC_TYPE_IMG == type || MsgCT.PIC_TYPE_FILE == type){ //电子签名或APK文件
			 path_1 = String.valueOf(prop.get("FILE_UPLOAD_PATH_1"));
			 path_2 = String.valueOf(prop.get("FILE_UPLOAD_PATH_2"));	 
		}else{
			return dbpath;
		}
		path = dbpath.replace(path_1, path_2);
		return path;
	}
	

	@Override
	public String getFilePath(String fid) throws Exception {
		log.debug("文件ID:{}",fid);
		if(null == fid || "".equals(fid)){
			throw new Exception("文件ID不能为空！");
		}
		String path = "";
		FileInf fileInf = dao.selectEntity(new FileInf(fid));
		path =  fileInf.getFjPath();
		log.debug("文件路径:{}",path);
		
		return path;
	}


	@Override
	public HashMap<String, Object> upload(String fname,String tname) throws Exception {
		String path   = "";
		String path_1 = "";
		String path_2 = "";
		
		String date   = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		String suffix = MsgCT.PIC_SUFFIX_PNG;
		path = date +"/"+ tname +"/" +fname + suffix;
		
		
		path_1 = String.valueOf(prop.get("PIC_SIGN_PATH_1")) ;
		path_2 = String.valueOf(prop.get("PIC_SIGN_PATH_2")) ;

		log.info("获取文件物理路径配置:[path_1={},path_2={},fileName={}]",path_1,path_2,fname);
		
		String FID = TdExpBasicFunctions.GETDATETIME("YYMMDD") + 
					seqNoService.getSeqNoNew("FILE_ID_SEQ", "6", "1");
		String filePath= path_1+path;
		FileInf file = new FileInf();
		File MFile=new File(filePath);
		FileInputStream fis=new FileInputStream(MFile);
		log.debug("生成文件ID：[{}]",FID);
		file.setId(FID);
		file.setFjPath(filePath);
		file.setDx(MFile.length());
		file.setFjName(fname+suffix);
		file.setOrderNum("0");
		file.setSfsx("0");  //0生效
		file.setFjo("");
		file.setFjt(TdExpBasicFunctions.GETDATETIME());
		file.setLx("image/jpeg");
		String sFileType = FileUtils.getFileType(path);
		if(sFileType.toUpperCase().equals("JPG")){
			int width = 0;
			int hight = 0;
			BufferedImage sourceImg =ImageIO.read(fis);
			width = sourceImg.getWidth();
			hight = sourceImg.getHeight();
			file.setDpi(width + " x " +hight);
		}
		addFile(file);
		HashMap<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("fid", FID);
		return rmap;
		
	}


	@Override
	public HashMap<String, Object> upload(MultipartFile img,int type) throws Exception {
		String path   = "";
		String path_1 = "";
		String path_2 = "";
		
		String date   = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		String fileName = TdExpBasicFunctions.GETDATETIME()+TdExpBasicFunctions.RANDOM(2, "4");
		String suffix = ".jpg";
		if(MsgCT.PIC_TYPE_CERT == type){       //身份证
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CERT_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CERT_2"));
			 suffix = MsgCT.PIC_SUFFIX_JPG;
			 path = date+"/"+fileName;
		}else if(MsgCT.PIC_TYPE_CARD == type){ //银行卡
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_CARD_1"));
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_CARD_2"));
			 suffix = MsgCT.PIC_SUFFIX_JPG;
			 path = date+"/"+fileName;
		}else if(MsgCT.PIC_TYPE_ESIGN == type){ //电子签名
			 path_1 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_1")) ;
			 path_2 = String.valueOf(prop.get("PIC_UPLOAD_ESIGN_2")) ;
			 suffix = MsgCT.PIC_SUFFIX_PNG;
			 path = date+"/"+fileName;
		}else{
			return null;
		}
		

		log.info("获取文件物理路径配置:[path_1={},path_2={},fileName={}]",path_1,path_2,fileName);
        
     // 生成jpeg图片
	//	String path=outputPath.substring(0, outputPath.lastIndexOf("/"));
		File targetFile=new File(path_1+path); 
		if (!targetFile.exists()&& !targetFile .isDirectory()) {
			targetFile.mkdirs();
		}

		//boolean bool= PICHandler.generateImage(img, path_1+path);
		img.transferTo(targetFile);
		log.info("文件处理完成.");
		
		String FID = TdExpBasicFunctions.GETDATETIME("YYMMDD") + 
					seqNoService.getSeqNoNew("FILE_ID_SEQ", "6", "1");
		String filePath= path_1+path;
		FileInf file = new FileInf();
		File MFile=new File(filePath);
		FileInputStream fis=new FileInputStream(MFile);
		log.debug("生成文件ID：[{}]",FID);
		file.setId(FID);
		file.setFjPath(filePath);
		file.setDx(MFile.length());
		file.setFjName(fileName+suffix);
		file.setOrderNum("0");
		file.setSfsx("0");  //0生效
		file.setFjo("");
		file.setFjt(TdExpBasicFunctions.GETDATETIME());
		file.setLx("image/jpeg");
		String sFileType = FileUtils.getFileType(path);
		if(sFileType.toUpperCase().equals("JPG")){
			int width = 0;
			int hight = 0;
			BufferedImage sourceImg =ImageIO.read(fis);
			width = sourceImg.getWidth();
			hight = sourceImg.getHeight();
			file.setDpi(width + " x " +hight);
		}
		addFile(file);
		HashMap<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("fid", FID);
		return rmap;
	}
	

}
