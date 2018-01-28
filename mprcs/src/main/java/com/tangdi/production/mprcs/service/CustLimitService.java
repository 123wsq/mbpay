package com.tangdi.production.mprcs.service;




import java.util.Map;

import com.tangdi.production.mprcs.bean.CustLimitInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public interface CustLimitService extends BaseService<CustLimitInf,Exception>{
	
	public int modifyUserLimitManageStatus(Map<String,Object> map) throws Exception;
	
	public int validateCust(CustLimitInf entity) throws Exception;

	int addAgentEntity(CustLimitInf entity) throws Exception;
	
}
