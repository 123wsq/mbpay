package com.tangdi.production.mpapp.dao;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;



/**
 *
 * 短信DAO
 *
 * @author zhengqiang
 * @version 1.0
 */
public interface MessageDao  {
	
	/**
	 * 插入发送短信记录
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public int insertSendMssage(Map<String,Object> parm) throws Exception;
	
	/**
	 * 通过短信类型,查询短信模版
	 * @param parm
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> selectTemplate(Map<String,Object> parm) throws TranException;
	
	/**
	 * 更新发送状态
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public int updateMessage(Map<String,Object> parm) throws Exception;
	
}
