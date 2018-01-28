package com.tangdi.production.mprcs.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mprcs.bean.CustLimitInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 
*
* @author zhengqiang  2015/9/11
* @version 1.0
*/
public interface CustLimitDao extends BaseDao<CustLimitInf,Exception>{
	
	public int modifyUserLimitManageStatus(Map<String,Object> map) throws Exception;
	
	public int selectUserLimit(String limitType,String limitCustLevel,String limitCustId) throws Exception;
	
	/**
	 * 查询商户限额信息是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int validate(Map<String,Object> map) throws Exception;
	
	public String selectCust(@Param(value="custId") String custId) throws Exception;
	
	public String selectAgent(@Param(value="agentId") String agentId) throws Exception;

	public int validateAgent(Map<String, Object> map)throws Exception;
	
}
