package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 终端信息管理
 * 
 * @author shanbeiyi
 * 
 * @version 1.0
 */
public interface TerminalDao {

	/**
	 * 查询终端信息
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String, Object> map) throws Exception;

	/**
	 * 查看商户是否已绑定终端
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public List<java.util.Map<String, Object>> selecttrlEntity(Map<String, Object> map) throws Exception;

	/**
	 * 终端绑定商户
	 * 
	 * @param Map
	 * @throws Exception
	 */
	public int updateEntity(Map<String, Object> map) throws Exception;

	public Map<String, Object> check(Map<String, Object> map) throws Exception;
	/**
	 * 更新终端工作密钥
	 * @param pmap
	 * @return
	 * @throws TranException
	 */
	public int updateKey(Map<String, Object> pmap) throws Exception;

	
	/**
	 * 查询支付通道支持的费率类型
	 * @param termNo
	 * @return
	 * @throws TranException
	 */
	public List<String> selectChlRate(@Param(value="termNo") String termNo) throws Exception;
	
	/**
	 * 查询刷卡头费率类型
	 * @param termNo
	 * @return
	 * @throws TranException
	 */
	public List<java.util.Map<String, Object>> selectTermRate(@Param(value="termNo") String termNo) throws Exception;
	
	/**
	 * 查询刷卡头蓝牙地址
	 * @param termNo
	 * @return
	 * @throws TranException
	 */
	public String selectMacAddress(@Param(value="custId") String custId) throws Exception;

	public void updateOtherEntity(Map<String, Object> validateMap);

}
