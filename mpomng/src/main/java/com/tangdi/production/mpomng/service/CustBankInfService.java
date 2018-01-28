package com.tangdi.production.mpomng.service;



import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustBankInfService extends BaseService<CustBankInf,Exception>{
	
	/**
	 * 
	 * @param custBankInf
	 * @param status
	 * @param updateDesc
	 * @return
	 * @throws Exception
	 */
	public int audit(CustBankInf custBankInf,String status,String updateDesc) throws Exception;
	
	/**
	 * 查询银行卡数量
	 * @return
	 * @throws Exception
	 */
	public int bankNum(String custIds) throws Exception;

}
