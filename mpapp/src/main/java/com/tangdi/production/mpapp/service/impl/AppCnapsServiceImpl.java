package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.AppCnapsDao;
import com.tangdi.production.mpapp.service.AppCnapsService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 联行号信息获取业务实现
 * author zhuji
 *
 */
@Service
public class AppCnapsServiceImpl implements AppCnapsService {
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	@Autowired
	private AppCnapsDao cnapsDao;
	@Override
	public List<String> getBankName(Map<String, Object> param)
			throws TranException {
		log.info("获取银行名称业务参数：{}",param);
		ParamValidate.doing(param, "bankProId","bankCityId");
		List<String> bankNames=null;
		try {
			bankNames=cnapsDao.selectBankName(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000224,"银行名称查询异常",e);
		}
		if (bankNames==null||bankNames.size()<=0) {
			throw new TranException(ExcepCode.EX000224,"银行列表查询失败");
		}
		return bankNames;
	}

	@Override
	public List<Map<String, Object>> getBankList(Map<String, Object> param)
			throws TranException {
		log.info("获取银行名称业务参数：{}",param);
		ParamValidate.doing(param, "bankProId","bankCityId","bankName");
		List<Map<String, Object>> banks=null;
		try {
			banks=cnapsDao.selectList(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000224,"联行号列表查询异常",e);
		}
		if (banks==null||banks.size()<=0) {
			throw new TranException(ExcepCode.EX000224,"联行号列表查询异常");
		}
		return banks;
	}
	
	
}
