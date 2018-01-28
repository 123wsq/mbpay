package com.tangdi.production.mpbase.service.impl;

import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.EsignService;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.ImgFont;
import com.tangdi.production.mpbase.util.ImgUtil;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

@Service
public class EsignServiceImpl implements EsignService{

	private static final Logger log = LoggerFactory
			.getLogger(EsignServiceImpl.class);
	@Value("#{mpbaseConfig}")
	private Properties prop;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String save(LinkedList<ImgFont> list,String spath,String ctype) throws TranException {
		// TODO Auto-generated method stub
		
		String signTemplate = prop.getProperty("SIGN_TEMPLATE").toString();
		String path_1 = prop.getProperty("PIC_SIGN_PATH_1").toString();
		String path_2 = prop.getProperty("PIC_SIGN_PATH_2").toString();
		String date = TdExpBasicFunctions.GETDATETIME("YYMMDD");
		log.debug("spath={},ctype={},signTemplate={}",spath,ctype,signTemplate);
		String fname = null;
		try {
			fname = date+ 
					seqNoService.getSeqNoNew("SIGN_ID_SEQ", "6", "1");
		} catch (Exception e) {
			fname = TdExpBasicFunctions.RANDOM(8, "2");
		}
		
		String cpath = path_1 + date +"/"+ctype+"/"+ fname + MsgCT.PIC_SUFFIX_PNG;
		
		log.debug("生产签名文件路径={}",cpath);
		ImgUtil sign = new ImgUtil();//SIGN_PIC_PATH
		sign.saveEsign(signTemplate
				,spath,cpath,list);
		Map<String,Object> map = null;
		try {
			map = fileUploadService.upload(fname, ctype);
		} catch (Exception e) {
			log.error("电子签名上传失败!{}",e.getMessage());
		}
		if(map != null){
			return map.get("fid").toString();
		}
		
		return "";
	}



}
