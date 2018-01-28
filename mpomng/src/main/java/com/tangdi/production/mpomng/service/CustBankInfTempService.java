package com.tangdi.production.mpomng.service;




import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 商户银行卡信息
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustBankInfTempService extends BaseService<CustBankInf,Exception>{
	public CustBankInf getEntityById(CustBankInf entity) throws Exception;

	public int addCount(CustBankInf entity) throws Exception;
}
