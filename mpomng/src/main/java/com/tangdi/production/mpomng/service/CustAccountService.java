package com.tangdi.production.mpomng.service;




import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 商户账户业务接口
 * @author zhuji
 * @version 1.0
 *
 */
public interface CustAccountService extends BaseService<CustAccountInf,Exception>{
	/**
	 * 余额变动 审核通过
	 * @param map
	 * @throws Exception
	 */
	public void blanceChange02(Map<String,Object> map) throws Exception;
	/**
	 * 余额变动 审核拒绝
	 * @param map
	 * @throws Exception
	 */
	public void blanceChange01(Map<String,Object> map) throws Exception;
	/**
	 * T0订单转余额
	 * @param map
	 * @throws Exception
	 */
	public void t0ToBlanceChange(Map<String, Object> map) throws Exception ;
	
	/**
	 * T1自动提现
	 * @param map
	 * @throws Exception
	 */
	public void t1yToBlanceChange(Map<String, Object> map) throws Exception ;
	
	/**
	 * AT1自动提现 跑批
	 * @param map
	 * @throws Exception
	 */
	public void at1ToBlanceChange(Map<String, Object> map) throws Exception ;
	
	/**
	 * 商户分润金额转余额
	 * @param map
	 * @throws Exception
	 */
	public void merProfitToBlanceChange(Map<String, Object> map) throws Exception ;
	/**
	 * 查询所有的T1余额
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryT1() throws Exception ;
	/**
	 * T1余额转T1Y
	 * @param map
	 * @throws Exception
	 */
	public void t1ToBlanceChange(Map<String, Object> map) throws Exception ;
	
	/**
	 * 加锁处理
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public CustAccountInf getCustAccountLock(CustAccountInf entity) throws Exception ;
	
	/**
	 * 获取所有的账户信息并加锁
	 * @return
	 * @throws Exception
	 */
	public List<CustAccountInf> getCustAccountAllLock() throws Exception ;

	
	/**
	 * T1余额转T1Y
	 * @param map
	 * @throws Exception
	 */
	public void t1ToT1YBlanceChange(Map<String, Object> map) throws Exception ;
	
	/***
	 * 获取商户账户汇总信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getCustAccountCount() throws Exception;
	
	/**
	 * 清算失败，回滚账户余额
	 * @param map
	 * @throws Exception
	 */
	public void settleFalse(Map<String,Object> map) throws Exception;
	
	/**
	 * 添加账户信息
	 * @param param 
	 * @return
	 * @throws TranException
	 */
	public int addCustAccount(Map<String , Object> param) throws Exception;
}
