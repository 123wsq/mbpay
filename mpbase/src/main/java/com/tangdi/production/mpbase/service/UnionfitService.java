package com.tangdi.production.mpbase.service;

import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 卡BIN
 * 
 * @author limiao
 *
 */
public interface UnionfitService extends BaseService<UnionfitInf, Exception> {
	
	/**
	 * 根据track2获取卡bin信息
	 * @param track2
	 * @return
	 * @throws Exception
	 */
	UnionfitInf getCardInf(String track2) throws Exception;

}
