package com.tangdi.production.mpbatch.service;

/**
 * 每天修改合作机构限额 的 DAY MONTH 改成当日
 * 
 * @author limiao
 *
 */
public interface RcsOrgLimitService {
	public void process() throws Exception;
}
