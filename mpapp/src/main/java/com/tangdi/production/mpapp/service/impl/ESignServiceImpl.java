package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.ESignDao;
import com.tangdi.production.mpapp.service.ESignService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;

/**
 * 电子签名接口实现类
 * @author zhengqiang
 *
 */
@Service
public class ESignServiceImpl implements ESignService {
	private static Logger log = LoggerFactory.getLogger(ESignServiceImpl.class);
	@Autowired
	private ESignDao dao;
	/**
	 * 文件上传接口
	 */
	@Autowired
	private FileUploadService fileUploadService;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int addESign(Map<String, Object> param) throws TranException {
		try {
			//查询是否存在该订单号
			//不存在时插入
			//存在是更新
			log.debug("保存电子签名ID,DATA:",param);
			return dao.insertESign(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001003,"保存电子签名记录异常!",e);
		}
	}

	@Override
	public String query(String prdno) throws TranException {
		String esign = null;
		try {
			esign = dao.selectESign(prdno);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001004,e);
		}
		log.info("电子签名ID:[{}]",esign);
		return esign ;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int save(Map<String, Object> param) throws TranException {
		log.info("电子签名存储中...");
		
		/**
		 * 1.存储文件
		 */
		String fid = saveImg((MultipartFile)param.get("content"));
		
		Map<String,Object> pmap = new HashMap<String,Object>();
		pmap.put("esign", fid);
		pmap.put("prdordNo", param.get("prdordNo").toString());
		log.info("商品订单号:[{}]",param.get("prdordNo").toString());
		/**
		 * 2.存储ID到DB
		 */
		addESign(pmap);
		log.info("电子签名存储完成.");
		return 1;
	}
	
	/**
	 * 保持电子签名图片
	 * @param img
	 * @return
	 * @throws TranException 
	 */
	private String saveImg(String img) throws TranException{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_ESIGN);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001003,"存储电子签名文件异常!",e);
		}
		if(rmap != null){
			fid = String.valueOf(rmap.get("fid"));
		}else{
			throw new TranException(ExcepCode.EX001003,"返回值对象rmap为空!");
		}
		//
		log.info("电子签名ID=[{}]",fid);
		return fid;

	}
	/**
	 * 保持电子签名图片
	 * @param img
	 * @return
	 * @throws TranException 
	 */
	private String saveImg(MultipartFile img) throws TranException{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_ESIGN);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001003,"存储电子签名文件异常!",e);
		}
		if(rmap != null){
			fid = String.valueOf(rmap.get("fid"));
		}else{
			throw new TranException(ExcepCode.EX001003,"返回值对象rmap为空!");
		}
		//
		log.info("电子签名ID=[{}]",fid);
		return fid;

	}
	
	

}
