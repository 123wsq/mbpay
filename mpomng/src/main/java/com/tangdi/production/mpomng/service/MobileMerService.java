package com.tangdi.production.mpomng.service;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpbase.service.BaseReportService;
import com.tangdi.production.mpomng.bean.CustRatesInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 
 * @author zhengqiang
 *
 */
public interface MobileMerService extends BaseService<MobileMerInf,Exception>,BaseReportService{
	
	/**
	 * 更新状态值
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int modifyMobileMerStatus(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询当前代理商下所有商户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MobileMerInf> queryMobileMerAgent(MobileMerInf mobileMerInf) throws Exception;
	/**
	 * 查询当前代理商下所有商户总数
	 * @param mobileMerInf
	 * @return
	 * @throws Exception
	 */
	public int getCountAgent(MobileMerInf mobileMerInf) throws Exception;
	/**
	 * 校验商户是否存在
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public int queryCustById(String custId) throws Exception;
	
	public boolean checkRateForT(Map<String,Object> param) throws Exception;

	public MobileMerInf getAgentEntity(MobileMerInf entity) throws Exception;
	
	
	/**
	 * 更新商户费率
	 * @param custRatesInf
	 * @return
	 * @throws Exception
	 */
	public int updateCustRate(CustRatesInf custRatesInf)throws Exception;

	/**
	 * 查看商户的费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustRatesInf queryCustRate(Map<String, Object> param) throws Exception;
	
	
	
	public void validateFee(CustRatesInf caCustRatesInf, Map<String, Object> agentFee) throws Exception;
	
}
