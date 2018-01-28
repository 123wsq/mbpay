package com.tangdi.production.mpapp.service;




import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * 账户变更历史查询业务层接口
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustAccountHisService{

	/**
	 * 添加账户变更历史信息
	 * @param param 
	 * @return
	 * @throws TranException
	 */
	public int addCustAccountHis(Map<String , Object> param) throws TranException;
	
	
	public int modifyCustAccountAmt(Map<String, Object> param)throws TranException;
	
	

}
