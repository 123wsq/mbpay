package com.tangdi.production.mpamng.dao;

import java.util.List;

import com.tangdi.production.mpamng.bean.TerminalCompanyInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface TerminalCompanyDao extends BaseDao<TerminalCompanyInf, Exception> {
	public List<TerminalCompanyInf> getTermTypeList() throws Exception;

	public List<TerminalCompanyInf> getTermComList() throws Exception;

}
