package com.tangdi.production.mpomng.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 商户账户dao
*
* @author zhuji
* @version 1.0
*/
public interface CustAccountDao extends BaseDao<CustAccountInf,Exception>{
	
	/**
	 * 新增账户信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 插入历史记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertHis(Map<String,Object> param) throws Exception;
	/**
	 * 查询所有的T1
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectT1() throws Exception;
	/**
	 * 加锁查询
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public CustAccountInf  selectCustAccountForUpdate(CustAccountInf entity)throws Exception;
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CustAccountInf>  selectCustAccountAllForUpdate()throws Exception;
	
	/**
	 * 查询所有的T1
	 * @return
	 * @throws Exception
	 */
	public int selectT1Account(Map<String, String> paramMap) throws Exception;
	
	/***
	 * 查询商户账户信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> selectCustAccountCount() throws Exception;
	
	/**
	 *查询备份表所有的商户账户信息
	 */
	public List<Map<String,Object>> selectCustCopyAccounts(Map<String,Object> map) throws Exception;
	
	/**
	 * 将商户的账户余额清零
	 */
	public int emprtCustAccount(CustAccountInf cust)throws Exception;
	

	/**
	 * 转移账户余额到备份表
	 */
	public int copyAccount(Map<String, Object> map) throws Exception;
	/**
	 * 清空 备份表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int clearCopyAccount(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询收款订单计算收款金额
	 * */
	public List<Map<String,Object>> selectPayAmt(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询提现单计算提现金额
	 * */
	public Map<String,Object> selectCasAmt(Map<String,Object> map) throws Exception;
	
	public int changeAccount(Map<String, Object> map) throws Exception;
	
	public int changeRiskAmt(String Amt) throws Exception;
	
	public Map<String,Object> selectRiskAmt() throws Exception;
   /**
    * 枷锁查询
    * @return
    * @throws Exception
    */
	public CustAccountInf selectEntityForUpd(CustAccountInf cust) throws Exception;
	
	public int changeAccountByAudit(Map<String,Object> map) throws Exception;
	
	
}
