package com.tangdi.production.mpaychl.front.service.impl;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.base.service.SMSService;
import com.tangdi.production.mpaychl.front.service.SmsFrontService;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.context.SpringContext;

/**
 * 短信前置接口实现类
 * @author zhengqiang
 *
 */
@Service
public class SmsFrontServiceImpl implements SmsFrontService {

	private static Logger log = LoggerFactory
			.getLogger(SmsFrontServiceImpl.class);
	
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop;
	
	@Override
	public Map<String, Object> sendsms(Map<String, Object> param)
			throws TranException {
		log.info("进入短信发送前置接口,参数:{}",param);
		String service = prop.getProperty(SMS_SERVICE);
		log.info("获取渠道接口[{}]",service);
		return SpringContext.getBean(service, SMSService.class).sendsms(param);
	}
	
	

}
