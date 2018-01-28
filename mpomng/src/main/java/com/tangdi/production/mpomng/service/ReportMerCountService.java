package com.tangdi.production.mpomng.service;

import java.util.Map;

import com.tangdi.production.mpomng.bean.NoticeInf;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface ReportMerCountService {
	/**
	 * 商户注册量
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryReportMerCount() throws Exception;

	/**
	 * 商户活跃度(收款交易)统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryReportMerLiveCount() throws Exception;


	public NoticeInf queryNotice(NoticeInf inf);
	
	public int saveType(NoticeInf entity) throws Exception;

}
