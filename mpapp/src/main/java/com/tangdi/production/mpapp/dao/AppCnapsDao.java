package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;


/**
 * 联行号dao
 * @author zhuji
 *
 */
public interface AppCnapsDao {
	/**
	 * 查询支行列表
	 * @param param（bankProId,bankCityId,bankName）省市id（不可空） 所属银行名称（可空）
	 * @return
	 * @throws Exception
	 */
	public List<Map<String , Object>> selectList(Map<String , Object> param) throws Exception;
	/**
	 * 查询银行名称
	 * @param param（bankProId,bankCityId）省市id（不可空）
	 * @return
	 * @throws Exception
	 */
	public List<String> selectBankName(Map<String , Object> param) throws Exception;
}
