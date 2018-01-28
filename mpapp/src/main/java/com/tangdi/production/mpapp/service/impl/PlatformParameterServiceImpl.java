package com.tangdi.production.mpapp.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.PlatformParameterDao;
import com.tangdi.production.mpapp.service.PlatformParameterService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 费率接口实现
 * author huchunyuan
 *
 */
@Service
public class PlatformParameterServiceImpl implements PlatformParameterService {
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	@Autowired
	private PlatformParameterDao tdRateDao;
	
	@Override
	public Map<String, Object> getRate(Map<String, Object> param)
			throws TranException {
		log.info("费率查询接口参数：{}",param);
		ParamValidate.doing(param, "paraCode");
		
		Map<String , Object> rsp=null;
		try {
			rsp=tdRateDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060002,"费率查询异常",e);
		}
		if(rsp==null||rsp.size()<=0){
			throw new TranException(ExcepCode.EX060002,"费率查询失败");
		}
		return rsp;
	}

	@Override
	public Map<String, Object> getCasTime(Map<String, Object> param)
			throws TranException {
		log.info("提现（消费）时间查询接口参数：{}",param);
		ParamValidate.doing(param, "paraCode");
		
		Map<String , Object> rsp=null;
		try {
			rsp=tdRateDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060003,"提现（消费）时间查询异常",e);
		}
		if(rsp==null||rsp.size()<=0){
			throw new TranException(ExcepCode.EX060003,"提现（消费）时间查询失败");
		}
		return rsp;
	}
	@Override
	public Map<String, Object> getTunnel(Map<String, Object> param)throws TranException {
		log.info("通道查询接口参数：{}",param);
		ParamValidate.doing(param, "paraCode");
		
		Map<String , Object> rsp=null;
		try {
			rsp=tdRateDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060004,"获取通道参数失败",e);
		}
		if(rsp==null||rsp.size()<=0){
			throw new TranException(ExcepCode.EX060003,"获取通道参数失败");
		}
		return rsp;
	}
}
