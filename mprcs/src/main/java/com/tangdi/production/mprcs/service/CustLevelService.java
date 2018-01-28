package com.tangdi.production.mprcs.service;



import com.tangdi.production.mprcs.bean.CustLevelInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
public interface CustLevelService extends BaseService<CustLevelInf,Exception>{
	
	
	/**
	 * 生成下拉框
	 * @return
	 * @throws Exception
	 */
	public String querySelectOption() throws Exception;

}
