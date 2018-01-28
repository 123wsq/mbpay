package com.tangdi.production.mpaychl.dao;

import java.util.List;
import java.util.Map;

/**
 * 审核中的订单
 * */
public interface MessageSendDao {

	/**
	 * 查询审核中的订单数量
	 * */
	public int selectAccount(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询订单审核短信接收人的手机号码
	 * */
	public List<String> selectAuditor() throws Exception;
	
	/**
	 * 查询商户审核短信接收人的手机号码
	 * */
	public List<String> selectCustAuditor() throws Exception;
	
	/**
	 * 查询未审核商户银行卡数量
	 * */
	public int selectBankCard(Map<String, Object> map) throws Exception;
}
