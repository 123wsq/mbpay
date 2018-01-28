package com.tangdi.production.mpaychl.dao;

import java.util.List;

import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author chenlibo
 * @version 1.0
 */
public interface CooporgMerInfDao extends BaseDao<CooporgMerInf, Exception> {

	public abstract List<CooporgMerInf> selectList(CooporgMerInf entity) throws Exception;

}
