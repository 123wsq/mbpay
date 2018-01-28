package com.tangdi.production.mpbase.dao;


import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.bean.MsgCodeInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 消息代码DAO层
 * @author zhengqiang
 *
 */
public interface MsgCodeDao extends BaseDao<MsgCodeInf, Exception>{
	
	/**
	 * 查询所有的消息代码
	 * @return
	 * @throws Exception
	 */
	public List<MsgCodeInf> selectAll() throws Exception;
	
	/**
	 * 根据msgId切换错误消息标记
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int updateModifyFlag(Map<String, Object> con) throws Exception;

}
