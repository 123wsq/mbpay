package com.tangdi.production.mpomng.dao;

import java.util.List;

import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author huchunyuan
 * @version 1.0
 */
public interface PayInfDao extends BaseDao<PayInf, Exception> {
	
	public List<PayInf> getCountList(PayInf entity) throws Exception;
}
