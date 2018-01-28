package com.tangdi.production.mpamng.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpbase.service.BaseReportService;
import com.tangdi.production.tdbase.service.BaseService;

public interface TerminalService extends BaseService<TerminalInf, Exception>, BaseReportService {

	/**
	 * 解绑代理商
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public abstract int unbindAgent(TerminalInf entity) throws Exception;

	/**
	 * 解绑用户
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public abstract int unbindMer(TerminalInf entity) throws Exception;

	/**
	 * 查询 终端类型 总条数
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public abstract Integer queryTermAllocateNum(TerminalInf entity) throws Exception;

	/**
	 * 终端物理号 查询出 终端信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public abstract TerminalInf findTermAllocate(TerminalInf entity) throws Exception;

	/**
	 * 终端下拨
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public abstract int termAllocate(TerminalInf entity) throws Exception;
	
	
	
	/**
	 * 获取总条数
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer getTermFeeCount(TerminalInf entity) throws Exception;

	/**
	 * 终端 分页
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<TerminalInf> getTermFeeListPage(TerminalInf entity) throws Exception;
	
	/***
	 * 检查此终端，当天是否存在有效交易
	 * @param terminalNo
	 * @return
	 */
	public boolean isExistEffectivePay(String terminalNo) throws Exception;

	/**
	 * 手工终端入库
	 * @return 
	 */
	public TerminalInf getEidtManual(String fileName,TerminalInf entity) throws Exception;
	
	public String getFjpath(String uploadFileId);
	/**
	 * 检查终端T1及商户T0费率是否大于代理商的费率
	 * @param terminal
	 * @return
	 */
	public abstract boolean checkAgtFee(TerminalInf terminal);
	/**
	 * 每次修改完终端的T0费率，同步到商户身上。
	 * @param terminal
	 */
	public abstract void modifyCustFee(TerminalInf terminal);
}
