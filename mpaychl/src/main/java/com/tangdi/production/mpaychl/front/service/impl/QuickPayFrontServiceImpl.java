package com.tangdi.production.mpaychl.front.service.impl;


import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.base.service.QuickPayService;
import com.tangdi.production.mpaychl.front.service.QuickPayFrontService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.context.SpringContext;

/**
 * <b>快捷支付前置处理实现（无SDK）<b>
 * @author zhengqiang  2015/3/18
 *
 */
@Service
public class QuickPayFrontServiceImpl implements QuickPayFrontService {
	private static Logger log = LoggerFactory
			.getLogger(QuickPayFrontServiceImpl.class);

	@Value("#{mpaychlConfig}")
	private Properties prop;

	@Override
	public Map<String, Object> pay(Map<String, Object> param)
			throws TranException {
		log.info("进入快捷支付程序.支付参数:[{}]",param);

		/**
		 * channel 快捷支付渠道
		 */
		ParamValidate.doing(param, "channel");

		String serviceName = null;
		try{
			 /**
			  * 渠道(厂家)
			  * channel : 0001 
			  *           0002 
			  */
			 serviceName = prop.get(param.get("channel")).toString();
		}catch(Exception e){
			log.error("选取快捷支付厂家配置信息异常！",e);
			throw new TranException(ExcepCode.EX900001,e);
		}
		log.info("选取快捷支付厂家配置信息[serviceName={}]",serviceName);
		
		QuickPayService service = SpringContext.getBean(serviceName, QuickPayService.class);
		return service.pay(param);
	}	
	






}
