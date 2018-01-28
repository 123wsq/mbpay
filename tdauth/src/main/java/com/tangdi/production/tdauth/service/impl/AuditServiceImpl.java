package com.tangdi.production.tdauth.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.tdauth.bean.AuditMenuRelInf;
import com.tangdi.production.tdauth.dao.AuditDao;
import com.tangdi.production.tdauth.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditDao auditDao;

	/**
	 * 根据审计id 更新审计管理表
	 * @param auditId
	 * @param checkValue
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyAuditInf(String auditId, String checkValue) {
		// TODO Auto-generated method stub
		int result=0;
		HashMap<String,String> map=new HashMap<String,String>();
		checkValue=menuGetParent(checkValue);
		map.put("menuId", checkValue);
		map.put("auditId", auditId);
		try {
			result=auditDao.deleteMenuRefByAudit(auditId );
			result=+auditDao.insertAuditMenuRef(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private String menuGetParent(String checkValue) {
		// TODO Auto-generated method stub
		HashSet<String> menuSet=new HashSet<String>();
		StringBuffer strBuffer=new StringBuffer();
		String[]str=checkValue.split(",");
		for (int i = 0; i < str.length; i++) {
			String st=str[i];
			putStrToSet(st, menuSet);
		}
		Iterator<String> it=menuSet.iterator();
		while(it.hasNext()){
			if(strBuffer.length()>0){
				strBuffer.append(',');
			}
			strBuffer.append("'"+it.next()+"'");
		}
		return strBuffer.toString();
	}
	private void putStrToSet(String st,HashSet<String> menuSet){
		if(st!=""){
			int len=st.length()/2;
			for (int j = 1; j <= len; j++) {
				menuSet.add(st.substring(0, j*2));
			}
		}
	}
	public AuditMenuRelInf getEntity(AuditMenuRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public List<AuditMenuRelInf> getList(AuditMenuRelInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public int addEntity(AuditMenuRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int modifyEntity(AuditMenuRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int removeEntity(AuditMenuRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getCount(AuditMenuRelInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<AuditMenuRelInf> getListPage(AuditMenuRelInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
