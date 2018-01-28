package com.tangdi.production.mpomng.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.report.MobileMerTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 手机商户信息DAO
 * @author zhengqiang
 *
 */
public interface MobileMerDao extends BaseDao<MobileMerInf,Exception>{
	
	/**
	 * 更新状态值
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateMobileMerStatus(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询数据报表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MobileMerTemplate> selectReport(HashMap<String, Object> map)  throws Exception;
	/**
	 * 代理商平台商户查询
	 * @param mobileMerInf agentName agentId custName custType custLogin custStatus 【agentId必传】
	 * @return
	 * @throws Exception
	 */
	public List<MobileMerInf> selectEntityByAgent(MobileMerInf mobileMerInf) throws Exception;
	/**
	 * 代理商平台商户总数查询
	 * @param mobileMerInf agentName agentId custName custType custLogin custStatus 【agentId必传】
	 * @return
	 * @throws Exception
	 */
	public int selectCountByAgent(MobileMerInf mobileMerInf) throws Exception;
	
	/**
	 * 校验商户是否存在
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public int validate(@Param(value="custId")String custId) throws Exception;
}

