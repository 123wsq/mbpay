package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 代理商服务接口
 * @author zhuji
 *
 */
public interface AgentService {
	/**
	 * 代理商检查验证返回
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> checkAgent(Map<String, Object> param) throws TranException;
	/**
	 * 根据代理商编号获取三级代理商编号的拼接
	 * @param param
	 * @return
	 */
	public String getThreeAgentId(Map<String, Object> param)  throws TranException;
	
	/**
	 * 代理商检查验证返回
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public String checkWebAgent(Map<String, Object> param) throws TranException;
	
}
