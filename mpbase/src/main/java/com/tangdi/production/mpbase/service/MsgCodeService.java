package com.tangdi.production.mpbase.service;

import java.util.Map;

import com.tangdi.production.mpbase.bean.MsgCodeInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 消息代码接口
 * @author zhengqiang
 *
 */
public interface MsgCodeService extends BaseService<MsgCodeInf,Exception>{
	
	/**
	 * 查询所有的消息代码
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> queryAll() throws Exception;
	
	public int modifyFlag ( String msgId , int  msgFlag) throws Exception;

}
