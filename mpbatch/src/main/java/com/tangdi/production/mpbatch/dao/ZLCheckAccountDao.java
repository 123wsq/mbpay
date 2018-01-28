package com.tangdi.production.mpbatch.dao;

import java.util.List;
import java.util.Map;

/**
 * 对账
 * 
 * @author limiao
 * 
 * @version 1.0
 */
public interface ZLCheckAccountDao {
	/**
	 * 查询 今天是否已经对账
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int checkStatus(Map<String, Object> map) throws Exception;
	
	/**
	 * 间隔时间取文件，保持数据库连接
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String holdConnStatus(Map<String, Object> map) throws Exception;

	/**
	 * 添加 当日对账记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertStatus(Map<String, Object> map) throws Exception;

	/**
	 * 修改 当日对账记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(Map<String, Object> map) throws Exception;
	
	/**
	 * 清空 第三方对账表
	 * @param map
	 * @return
	 * @throws Exception
	 * */
	public int emptyThd(Map<String, Object> map) throws Exception;

	/**
	 * 清空 银行对账表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int emptyBank(Map<String, Object> map) throws Exception;

	/**
	 * 清空 交易记录对账表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int emptyPay(Map<String, Object> map) throws Exception;

	/**
	 * 导入 银行对账文件
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetBank(Map<String, Object> map) throws Exception;

	/**
	 * 支付    导入 交易流水
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetPay(Map<String, Object> map) throws Exception;
	
	/**
	 * 代付   	导入 交易流水
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetAnotherPay(Map<String, Object> map) throws Exception;

	/**
	 * 导入 疑帐表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetDoubtBank(Map<String, Object> map) throws Exception;

	/**
	 * 导入 疑帐表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetDoubtPay(Map<String, Object> map) throws Exception;

	/**
	 * 对账
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int checkAccount(Map<String, Object> map) throws Exception;

	/**
	 * 插入 疑帐表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetDoubt(Map<String, Object> map) throws Exception;

	/**
	 * 插入 错账表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insetError(Map<String, Object> map) throws Exception;

	public int insetBankDoubt(Map<String, Object> map) throws Exception;

	public int insetPayDoubt(Map<String, Object> map) throws Exception;

	

}
