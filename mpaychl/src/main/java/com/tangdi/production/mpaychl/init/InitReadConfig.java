package com.tangdi.production.mpaychl.init;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;



/**
 * 初始化加载数据库数据至内容的服务
 * @author Jason
 *
 */

@Service
public class InitReadConfig implements InitializingBean, ServletContextAware{


	private static final Logger log = LoggerFactory.getLogger(InitReadConfig.class);
	public void afterPropertiesSet() throws Exception {
		//在这个方法里面写 初始化的数据也可以。
	}

	public void setServletContext(ServletContext context) {
		try {
			//加载数据字典信息至内存
			log.info("begin ");
			//
			//SystemParam.setPath(context.getContextPath());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}
