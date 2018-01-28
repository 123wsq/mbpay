package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.AppDao;
import com.tangdi.production.mpapp.service.AppService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;

/**
 * app信息获取接口实现类
 * author huchunyuan
 *
 */
@Service
public class AppServiceImpl implements AppService {
	private static Logger log = LoggerFactory.getLogger(AppServiceImpl.class);
	/**
	 * 文件上传接口
	 */
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private AppDao appInfDao;
	

	@Override
	public Map<String, Object> selectEntity(Map<String,Object> pmap) throws TranException {
//		ParamValidate.doing(pmap, "appName");
		pmap.put("appName", "满e刷");
		log.info("APP版本更新参数：{}", pmap);
		Map<String, Object> rmap   = new HashMap<String,Object>();
		try {
			String checkState = getCheckState(pmap);
			Map<String, Object> appInf = getLastAppInf(pmap);
			if(("0").equals(checkState) || null == appInf){
				log.info("APP版本已最新");
				rmap.put("fileUrl", "");
				rmap.put("checkState", "0");
				rmap.put("fileDesc", "");
			}else{
				rmap.put("fileUrl", appInf.get("fileUrl"));
				rmap.put("checkState", checkState);
				rmap.put("fileDesc", appInf.get("fileDesc"));
				log.info("APP版本不是最新，更新参数：{}", rmap);
			}
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000701,e);
		}
		return rmap;
	}
	
	/***
	 * 获取最新APP版本信息
	 * @param fileName
	 * @return
	 * @throws TranException 
	 */
	private Map<String,Object> getLastAppInf(Map<String,Object> pmap) throws TranException{
		Map<String, Object> appInfMap = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> appInf = appInfDao.selectAppInf(pmap);
			if(null == appInf || 0 == appInf.size() || null == appInf.get(0).get("appFilePath")){
				return null;
			}else{
				String filePathTemp = fileUploadService.getFilePath(appInf.get(0).get("appFileId").toString(), MsgST.PIC_TYPE_OTHER);
				String appFilePath = fileUploadService.getRemotePath(filePathTemp,MsgST.PIC_TYPE_IMG);
				appInfMap.put("fileUrl", appFilePath);
				appInfMap.put("fileDesc", appInf.get(0).get("appDesc"));
				return appInfMap;
			}
		}catch(Exception e){
			throw new TranException(ExcepCode.EX000701,e);
		}
	}
	
	/***
	 * 获取检查结果状态
	 * @param pmap
	 * @return
	 * @throws TranException 
	 */
	private String getCheckState(Map<String,Object> pmap) throws TranException{
		try{
			List<Map<String, Object>> resutlMaps = appInfDao.selectAppInf(pmap);
			
			// 1.已经是最新版本
			if(null == resutlMaps || resutlMaps.size() == 0){
				return "2";
			}
			// 2.存在新版本
			for(Map<String, Object> resutlMap : resutlMaps){
				// 存在强制更新版本
				if(resutlMap.get("appAutoUpdate").equals("3")){
					return "3";
				}
			}
			return "1";
		}catch(Exception e){
			throw new TranException(ExcepCode.EX000701,e);
		}
	}

}
