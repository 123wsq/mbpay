package com.tangdi.production.mpbatch.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.OrgSettleCountInf;

/**
 * 生成机构对账文件
 * 
 * @author chenlibo
 * 
 * @version 1.0
 */
public interface GenOrgSettleReportDao {

	/**
	 * 统计机构对账流水
	 * @return
	 * @throws Exception
	 */
	public List<OrgSettleCountInf> selectOrgSettleCount(Map<String, Object> paramMap)  throws Exception;
	
	/**
	 * 统计机构对账流水总金额
	 * @return
	 * @throws Exception
	 */
	public int selectOrgSettleCountSum(Map<String, Object> paramMap)  throws Exception;
	
	/**
	 * 统计机构对账流水总笔数
	 * @return
	 * @throws Exception
	 */
	public int selectOrgSettleCountNum(Map<String, Object> paramMap)  throws Exception;

}
