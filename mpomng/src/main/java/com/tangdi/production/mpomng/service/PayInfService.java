package com.tangdi.production.mpomng.service;

import java.util.List;

import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
public interface PayInfService extends BaseService<PayInf, Exception> {
	public List<PayInf> getCountList(PayInf entity) throws Exception;
	
}
