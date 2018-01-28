package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 *联行号信息业务接口
 * @author zhuji
 *
 */
public interface AppCnapsService {
	
	/**
	 * 银行名称列表
	 * @param param（bankProId,bankCityId）省市id（不可空）
	 * @return
	 * @throws Exception
	 */
	public List<String> getBankName(Map<String , Object> param) throws TranException;
	/**
	 * 支行银行列表
	 * @param param（bankProId,bankCityId,bankName）省市id（不可空） 所属银行名称（可空）
	 * @return cnapsCode subBranch
	 * @throws Exception
	 */
	public List<Map<String, Object>> getBankList(Map<String , Object> param) throws TranException;

}
