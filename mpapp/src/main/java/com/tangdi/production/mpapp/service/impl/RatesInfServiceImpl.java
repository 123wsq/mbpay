package com.tangdi.production.mpapp.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.RatesInfDao;
import com.tangdi.production.mpapp.service.RatesInfService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 费率服务接口实现类
 * 
 * @author chenlibo
 *
 */
@Service
public class RatesInfServiceImpl implements RatesInfService {
	private static Logger log = LoggerFactory.getLogger(RatesInfServiceImpl.class);

	@Autowired
	private RatesInfDao ratesInfDao;
	

	@Override
	public Map<String, Object> getRate(Map<String, Object> pmap)
			throws TranException {
		log.info("费率查询接口参数：{}",pmap);
		ParamValidate.doing(pmap, "rateCode","rateId","rateType");
		
		Map<String , Object> rsp = null;
		try {
			rsp = ratesInfDao.selectEntity(pmap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060002,"费率查询异常",e);
		}
		if(rsp == null || rsp.size() <= 0){
			throw new TranException(ExcepCode.EX060002,"费率查询失败");
		}
		return rsp;
	}
	
}
