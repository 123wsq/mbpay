package com.tangdi.production.mpapp.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.dao.CustAccountHisDao;
import com.tangdi.production.mpapp.service.CustAccountHisService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

@Service
public class CustAccountHisServiceImpl implements CustAccountHisService{
	private static final Logger log = LoggerFactory
			.getLogger(CustAccountHisServiceImpl.class);
	@Autowired
	private CustAccountHisDao custAccountHisDao;
	


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCustAccountHis(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "accountLogId");
		int rt=0;
		try {
			rt=custAccountHisDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000214,"账户变更历史添加异常",e);
		}
		if (rt==0) {
			throw new TranException(ExcepCode.EX000213,"账户变更历史添加失败");
		}
		return rt;
	}

	

	@Override
	public int modifyCustAccountAmt(Map<String, Object> param)
			throws TranException {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
