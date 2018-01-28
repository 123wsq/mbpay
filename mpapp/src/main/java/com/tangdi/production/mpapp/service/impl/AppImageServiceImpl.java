package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.AppImageDao;
import com.tangdi.production.mpapp.service.AppImageService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;

/**
 * 首页轮播图获取接口实现类
 * author huchunyuan
 *
 */
@Service
public class AppImageServiceImpl implements AppImageService {
	
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	private AppImageDao appImageDao;
	
	/**
	 * 文件上传接口
	 */
	@Autowired
	private FileUploadService fileUploadService;
	

	@Override
	public List<Map<String, Object>> selectList() throws TranException {

		List<Map<String, Object>> resultList = null;
		try {
			resultList = appImageDao.selectList();
		} catch (Exception e) {
			log.error(ExcepCode.EX900000,e);
			throw new TranException(ExcepCode.EX900000,e);
		}
		for(Map<String, Object> pic : resultList){
			try {
				pic.put("appimgPath", 
						fileUploadService.getRemotePath(pic.get("appimgPath").toString(),MsgST.PIC_TYPE_IMG));
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001005,"轮播图地址转换异常！",e);
			}
		}
		log.debug("轮播图列表:{}",resultList);
		return resultList;
	}
}
