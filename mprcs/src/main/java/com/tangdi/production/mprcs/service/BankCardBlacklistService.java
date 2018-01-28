package com.tangdi.production.mprcs.service;



import java.util.List;
import java.util.Map;

import com.tangdi.production.mprcs.bean.BankCardBlacklistInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public interface BankCardBlacklistService extends BaseService<BankCardBlacklistInf,Exception>{
	
	Integer getCount(Map<String,Object> paramMap);
	List<BankCardBlacklistInf> getListPage(Map<String,Object> paramMap);

}
