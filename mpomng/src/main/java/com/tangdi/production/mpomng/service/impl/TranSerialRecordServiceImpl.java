package com.tangdi.production.mpomng.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpomng.dao.TranSerialRecordDao;
import com.tangdi.production.mpomng.service.TranSerialRecordService;

/**
 * 交易记录接口实现类
 * @author zhengqiang
 *
 */
@Service
public class TranSerialRecordServiceImpl implements TranSerialRecordService {

	private static Logger log = LoggerFactory.getLogger(TranSerialRecordServiceImpl.class);

	@Autowired
	private TranSerialRecordDao tranSerialRecordDao;
	
	@Override
	public List<Map<String, Object>> queryTranCountByDate(Map<String, Object> paramMap)
			throws Exception {
		log.debug("参数:[{}]",paramMap);
		List<Map<String,Object>> result = null;
		try{
		result = tranSerialRecordDao.selectTranCountByDate(paramMap);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
		log.debug("商户交易统计结果:[{}]",result);
		
		
		return result;
	}

}
