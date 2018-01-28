package com.tangdi.production.mpbase.service;

import java.util.Map;


/**
 * 渠道错误消息代码接口
 * @author zhengqiang
 *
 */
public interface TranErrorCodeService{
	
	/**
	 * 查询所有的消息代码
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> queryAll() throws Exception;

}
