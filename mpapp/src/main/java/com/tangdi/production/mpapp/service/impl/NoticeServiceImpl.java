package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.NoticeDao;
import com.tangdi.production.mpapp.service.NoticeService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpomng.bean.NoticeInf;

/**
 * 公告查询接口实现类
 * @author huchunyuan
 *
 */
@Service
public class NoticeServiceImpl implements NoticeService {
	
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	NoticeDao noticeDao;
	

	@Override
	public List<Map<String, Object>> getNotice(Map<String, Object> param)throws TranException {
		
		try {
			ParamValidate.doing(param, "start","pageSize");
		} catch (Exception e) {
//			throw new TranException(ExcepCode.EX000000,e);
		}
		List<Map<String, Object>> resultList = null;
		if("2".equals(param.get("noticeStatus"))){
			try {
				resultList = noticeDao.selectNotice(param);
			} catch (Exception e) {
				log.error(ExcepCode.EX900000,e);
				throw new TranException(ExcepCode.EX900000,e);
			}
		}else{
			try {
				resultList = noticeDao.selectEntity(param);
				if(resultList != null && resultList.size()!=0){
					param.put("noticeId", resultList.get(0).get("noticeId"));
					param.put("id", param.get("custId"));
					Map<String, Object> map = noticeDao.getNoticeType(param);
					if(map != null){
						resultList = null;
					}
				}
			} catch (Exception e) {
				log.error(ExcepCode.EX900000,e);
				throw new TranException(ExcepCode.EX900000,e);
			}
		}
		
		log.debug("公告信息:{}",resultList);
		return resultList;
	}

	@Override
	public int saveType(Map<String, Object> map)throws TranException {
		int rt = 0;
		log.debug("方法参数:{}", map);
		try {
			map.put("id", map.get("custId"));
			rt = noticeDao.saveType(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rt;
	}
	
}
