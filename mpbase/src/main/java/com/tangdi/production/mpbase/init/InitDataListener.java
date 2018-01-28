package com.tangdi.production.mpbase.init;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.tangdi.production.mpbase.service.MsgCodeService;
import com.tangdi.production.mpbase.service.TranErrorCodeService;

/**
 * 初始化加载数据库数据至内容的服务
 * @author zhengqiang
 *
 */

@Service
public class InitDataListener implements InitializingBean, ServletContextAware{
	/**
	 * 消息码接口
	 */
	@Autowired
	private MsgCodeService msgCodeService;
	/**
	 * 渠道错误码
	 */
	@Autowired
	private TranErrorCodeService tranErrorCodeService;
	
	public void afterPropertiesSet() throws Exception {
		//在这个方法里面写 初始化的数据也可以。
	}

	public void setServletContext(ServletContext arg0) {
		try {
			//加载数据代码信息至内存
			 msgCodeService.queryAll();
			 tranErrorCodeService.queryAll();
		} catch (Exception e) {
		
			System.err.println(e);
		}
	}
}
