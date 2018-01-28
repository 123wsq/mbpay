package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpbase.exception.TranException;



/**
*
* 
*商户银行卡信息接口
* @author zhuji
* @version 1.0
*/
public interface CustBankInfDao{
	/**
	 * 绑定银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertEntity(Map<String , Object> param) throws Exception;
	/**
	 * 解绑银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteEntity(Map<String , Object> param) throws Exception;
	/**
	 * 修改银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateEntity (Map<String , Object> param) throws Exception;
	/**
	 * 查询银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectEntity(Map<String , Object> param) throws Exception;
	/**
	 * 查询银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(Map<String , Object> param) throws Exception;
	/**
	 * 查询未审核银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectTempEntity(Map<String , Object> param) throws Exception;
	/**
	 * 未审核银行卡列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>  selectTempList(Map<String , Object> param) throws Exception;
	/**
	 * 查询数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectCount(Map<String , Object> param) throws Exception;
	/**
	 * 查询审核数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectTempCount(Map<String , Object> param) throws Exception;
	/**
	 * 删除
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteTmpEntity(Map<String, Object> param) throws Exception;
	/**
	 * 查询银行卡绑定错误原因
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectErrorReason(@Param(value="custId") String custId) throws Exception;
	/**
	 * 查询最新绑定银行卡状态
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public String selectCardState(@Param(value="custId") String custId) throws TranException;
	
	public String selecCertificateNo(@Param(value="custId") String custId) throws Exception;
}
