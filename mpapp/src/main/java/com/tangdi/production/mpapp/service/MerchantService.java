package com.tangdi.production.mpapp.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 商户服务接口
 * @author zhengqiang
 *
 */
public interface MerchantService {
	
	/**
	 * 商户注册
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public String register(Map<String,Object> param) throws TranException;
	
	/**
	 * 商户注册,通过推荐人
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int registerByReference(Map<String,Object> param) throws TranException;
	/**
	 * 修改密码
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int updatePwd(Map<String, Object> param) throws TranException;
	/**
	 * 实名认证
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int certification(Map<String, Object> param)	throws TranException;
	/**
	 * 通过手机号查询商户
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getMerchant(Map<String, Object> param) throws TranException;
	
	/**
	 * 通过推荐人查询商户
	 * @return
	 * @throws TranException
	 */
	public Boolean getMerchantByReferrer(Map<String, Object> param) throws TranException;
	/**
	 * 查询商户信息
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getMerchantInfo(Map<String, Object> param) throws TranException;
	/**
	 * 更新登陆信息
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int updateLoginInfo(Map<String, Object> param) throws TranException;
	
	/**
	 * 检查商户是否存在
	 * @param param
	 * @throws TranException
	 */
	public int checkIsExist(Map<String, Object> param) throws TranException;
	
	/**
	 * 获取商户T0提现费率
	 * */
	public Map<String,String> getRateT0(Map<String, Object> param) throws TranException;
	
	public ReturnMsg webScanRegitser(Map<String, Object> param) throws TranException;
	
	/**
	 * 通过商户号查询商户
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> getMerchantByID(Map<String, Object> param) throws TranException;
}
