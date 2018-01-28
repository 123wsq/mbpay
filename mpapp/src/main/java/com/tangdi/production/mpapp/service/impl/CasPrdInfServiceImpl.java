package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.CasPrdInfDao;
import com.tangdi.production.mpapp.service.CasPrdInfService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 获取交易记录接口
 * author huchunyuan
 *
 */
@Service
public class CasPrdInfServiceImpl implements CasPrdInfService {
	private static Logger log = LoggerFactory.getLogger(CasPrdInfServiceImpl.class);
	
	@Autowired
	private CasPrdInfDao dao;
	
	/**
	 * 文件上传接口
	 */
	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	public  List<Map<String,Object>>  selectList(Map<String,Object> param) throws Exception {
		
		ParamValidate.doing(param, "start","pageSize");

		List<Map<String, Object>> resultList = null;
		try {
			resultList = dao.selectList(param);
		} catch (Exception e) {
			log.error(ExcepCode.EX900000,e);
			throw new TranException(ExcepCode.EX900000,e);
		}
		/*
		for(Map<String, Object> pic : resultList){
			try {
				//电子签名转换
			//	pic.put("paySignPic", 
			//			fileUploadService.getRemotePath(pic.get("paySignPic").toString(),MsgCT.PIC_TYPE_ESIGN));
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001004,"未找到电子签名记录！",e);
			}
		}*/
		
		log.debug("交易信息:{}",resultList);
		return resultList;
	}


	
}
