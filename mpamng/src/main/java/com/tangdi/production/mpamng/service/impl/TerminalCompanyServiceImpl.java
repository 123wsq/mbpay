package com.tangdi.production.mpamng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.bean.TerminalCompanyInf;
import com.tangdi.production.mpamng.dao.TerminalCompanyDao;
import com.tangdi.production.mpamng.service.TerminalCompanyService;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

@Service
public class TerminalCompanyServiceImpl implements TerminalCompanyService {

	@Autowired
	private TerminalCompanyDao terminalCompanyDao;

	@Autowired
	private GetSeqNoService seqNoService;

	/**
	 * 新增 终端厂商/终端类型
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(TerminalCompanyInf entity) throws Exception {
		entity.setTermId(seqNoService.getSeqNoNew("SEQ_MPAMNG_TERMINALCOMPANY", "9", "0"));
		return terminalCompanyDao.insertEntity(entity);
	}

	/**
	 * 修改 终端厂商/终端类型
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(TerminalCompanyInf entity) throws Exception {
		return terminalCompanyDao.updateEntity(entity);
	}

	/**
	 * 删除 终端厂商/终端类型
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(TerminalCompanyInf entity) throws Exception {
		return terminalCompanyDao.deleteEntity(entity);
	}

	/**
	 * 根据ID查询
	 * 
	 */
	public TerminalCompanyInf getEntity(TerminalCompanyInf entity) throws Exception {
		return terminalCompanyDao.selectEntity(entity);
	}

	/**
	 * 获取总条数
	 * 
	 */
	public Integer getCount(TerminalCompanyInf entity) throws Exception {
		return terminalCompanyDao.countEntity(entity);
	}

	/**
	 * 终端厂商/终端类型 分页
	 * 
	 * @throws Exception
	 */
	@Override
	public List<TerminalCompanyInf> getListPage(TerminalCompanyInf entity) throws Exception {
		return terminalCompanyDao.selectList(entity);
	}

	/**
	 * 获取终端类型
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public String querySelectOptionTermType() throws Exception {
		List<TerminalCompanyInf> terminalCompanyInfList = terminalCompanyDao.getTermTypeList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> nmap = new HashMap<String, Object>();
		for (int i = 0; i < terminalCompanyInfList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			TerminalCompanyInf terminalCompanyInf = terminalCompanyInfList.get(i);
			map.put("text", terminalCompanyInf.getTermTypeName());
			map.put("value", terminalCompanyInf.getTermType());
			list.add(map);
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap);
	}

	/**
	 * 获取终端厂商
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public String querySelectOptionTermCom() throws Exception {
		List<TerminalCompanyInf> terminalCompanyInfList = terminalCompanyDao.getTermComList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> nmap = new HashMap<String, Object>();
		for (int i = 0; i < terminalCompanyInfList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			TerminalCompanyInf terminalCompanyInf = terminalCompanyInfList.get(i);
			map.put("text", terminalCompanyInf.getTermcomName());
			map.put("value", terminalCompanyInf.getTermcomCode());
			list.add(map);
		}
		nmap.put("options", list);
		return JUtil.toJsonString(nmap);
	}
}
