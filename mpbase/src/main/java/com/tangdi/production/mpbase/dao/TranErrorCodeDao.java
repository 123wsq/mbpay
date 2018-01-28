package com.tangdi.production.mpbase.dao;

import java.util.List;
import com.tangdi.production.mpbase.bean.TranErrorCodeInf;
/**
 * 渠道错误消息代码DAO层
 * @author zhengqiang
 *
 */
public interface TranErrorCodeDao{
	
	/**
	 * 查询所有的消息代码
	 * @return
	 * @throws Exception
	 */
	public  List<TranErrorCodeInf> selectAll() throws Exception;
	

}
