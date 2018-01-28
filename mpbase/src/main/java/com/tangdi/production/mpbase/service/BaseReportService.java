package com.tangdi.production.mpbase.service;

import java.util.HashMap;

/**
 * 业务报表导出基类接口
 * @author zhengqiang
 *
 */
public interface BaseReportService {
	/**
	 * 生成业务报表
	 * @param con 查询条件
	 * @param uid 用户编号
	 * @return 0 成功 1失败
	 * @throws Exception
	 */
	public int report(HashMap<String, Object> con,String uid) throws Exception;

}
