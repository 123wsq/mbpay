package com.tangdi.production.mpbase.dao;

import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 * 卡BIN
 * 
 * @author limiao
 * 
 */
public interface UnionfitDao extends BaseDao<UnionfitInf, Exception> {

	UnionfitInf getCardInf(String cardNo);

}
