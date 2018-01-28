package com.tangdi.production.mpaychl.base.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.tangdi.production.tdbase.context.SpringContext;

/**
 * 
 * 加密机抽象类接口, 注入配置文件和加密机对象。</br>
 * 实现类中如果需要加密机对象时,继承该接口。在基类中使用getEncryptorService()来获取加密机对象。
 * @author zhengqiang 2015/08/05
 * 
 *
 */
public abstract class EncryptorAbstractService {
	private static Logger log = LoggerFactory
			.getLogger(EncryptorAbstractService.class);
	/**
	 * 配置文件注入
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop_;
	/**
	 * 加密机接口
	 */
	private EncryptorService encryptorService_ = null;
	
	/**
	 * 获取加密机实现类对象
	 * @return EncryptorService
	 */
	protected synchronized EncryptorService getEncryptorService(){
		if(this.encryptorService_ == null){
			log.info("加密机对象[encryptorService_]为空,获取加密机对象...");
			String serviceName = prop_.getProperty(EncryptorService.MANUFACTURER);
			log.info("获取加密机厂商实现类[{}]",serviceName);
		   this.encryptorService_ = SpringContext.getBean(serviceName, 
					EncryptorService.class);
		}
	    return this.encryptorService_;
	}
	
	/**
	 * 获取加密机厂商实现类名称
	 * @return
	 */
	protected String getEncryptorServiceName(){
		String serviceName = prop_.getProperty(EncryptorService.MANUFACTURER);
		log.info("获取加密机厂商实现类[{}]",serviceName);
		return serviceName;
	}

}
