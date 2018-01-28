package com.tangdi.production.mpamng.dao;

import java.util.List;

import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpamng.bean.TerminalKeyInf;
import com.tangdi.production.tdbase.dao.BaseDao;

public interface TerminalDao extends BaseDao<TerminalInf, Exception> {

	public abstract int insertTerminalKey(TerminalKeyInf entity) throws Exception;

	public abstract int unbindAgent(TerminalInf entity) throws Exception;

	public abstract int unbindMer(TerminalInf entity) throws Exception;

	public abstract Integer queryTermAllocateNum(TerminalInf entity) throws Exception;

	public abstract TerminalInf findTermAllocate(TerminalInf entity) throws Exception;

	public abstract List<TerminalInf> findTermAllocateList(TerminalInf entity) throws Exception;

	public abstract int termAllocate(TerminalInf entity) throws Exception;

	public abstract List<TerminalInf> getTermFeeListPage(TerminalInf entity) throws Exception;

	public abstract TerminalInf findTermFeeBind(TerminalInf entity) throws Exception;

	public abstract List<TerminalInf> findTermFeeBindList(TerminalInf entity) throws Exception;

	public abstract int termFeeBind(TerminalInf entity) throws Exception;

	public abstract int deleteTermFee(TerminalInf entity) throws Exception;

	public abstract int getTermFeeCount(TerminalInf entity) throws Exception;
	
	/***
	 * 获取该终端，当天有效的订单数
	 * 
	 * @param terminalNo
	 * @return
	 * @throws Exception
	 */
	public int getEffectivePayCount(String terminalNo) throws Exception;
	
	public abstract TerminalInf selectTerminalNo(TerminalInf entity);
	
	public abstract String getFjpath(String uploadFileId);

	public abstract void modifyCustFee(TerminalInf terminal);
}
