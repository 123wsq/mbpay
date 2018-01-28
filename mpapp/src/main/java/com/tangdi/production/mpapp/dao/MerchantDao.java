package com.tangdi.production.mpapp.dao;

import java.util.Map;


/**
 * 商户信息管理接口
 * @author zhengqiang
 *
 */
public interface MerchantDao {
	
	/**
	 * 查询用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectEntity(Map<String,Object> param) throws Exception ;
	
	/**
	 * 更新用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity(Map<String,Object> param) throws Exception ;
	
	/**
	 * 商户信息添加
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String,Object> param) throws Exception ;
	/**
	 * 查询1级代理商id
	 * @param param #{custId}
	 * @return
	 * @throws Exception
	 */
    public String selectAgentId(Map<String,Object> param) throws Exception ;
	
    /**
	 * 查询用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String selectReference(Map<String,Object> param) throws Exception ;
	
	/**
	 * 绑定终端时，更新商户T0提现费率
	 * */
	public int udpateTCas(Map<String,String> param) throws Exception;
	
	/**
	 * 商户提现时，查询T0提现费率
	 * */
	public Map<String,String> selectRateT0(Map<String,Object> param) throws Exception;
	
	/**
	 * 商户提现时，查询T0提现费率
	 * */
	public Map<String,String> selectRateT0new(Map<String,Object> param) throws Exception;
}
