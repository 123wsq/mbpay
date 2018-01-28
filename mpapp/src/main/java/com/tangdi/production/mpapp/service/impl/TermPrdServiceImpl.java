package com.tangdi.production.mpapp.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.PayDao;
import com.tangdi.production.mpapp.dao.PrdDao;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 商品订单接口实现类
 * 
 * @author zhengqiang
 *
 */
@Service
public class TermPrdServiceImpl implements TermPrdService {
	private static Logger log = LoggerFactory.getLogger(TermPrdServiceImpl.class);

	@Autowired
	private PrdDao prdDao;
	@Autowired
	private GetSeqNoService seqNoService;
	@Autowired
	private PayDao payDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createPrd(Map<String, Object> param) throws TranException {
		try {
			ParamValidate.doing(param, "prdordType", "prdordAmt");
		} catch (TranException e) {
			throw new TranException("参数验证不通过", e);
		}
		String custId = (String) param.get("custId");
		String prdordNo = "";
		try {
			prdordNo = TdExpBasicFunctions.GETDATE() + seqNoService.getSeqNoNew("PRD_NO", "9", "1");
			String prdordType = (String) param.get("prdordType");
			param.put("prdordNo", prdordNo);
			param.put("prdordType", prdordType);

			if (MsgST.PRDORDTYPE_PAYMENTS.equals(prdordType)) {
				param.put("bizType", param.get("bizType"));
			}
			param.put("custId", custId);
			param.put("ordStatus", MsgST.ORDSTATUS_NO_TRANSACTION);
			param.put("prdDate", TdExpBasicFunctions.GETDATE());
			param.put("prdTime", TdExpBasicFunctions.GETDATETIME("HHMISS"));
			param.put("ordTime", TdExpBasicFunctions.GETDATETIME());
			Double prdordAmt = Double.valueOf(param.get("prdordAmt").toString());
			param.put("ordAmt", prdordAmt.intValue());

			log.debug("商品订单 信息:{}", param);

			if (prdDao.insertEntity(param) <= 0) {
				throw new TranException("创建商品订单失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException("创建商品订单异常!", e);
		}
		return prdordNo;
	}

	@Override
	public Integer modifyPrd(Map<String, Object> param) throws TranException {
		Integer result = 0;
		Map<String, Object> map = null;
		try {
			param.put("modifyTime", TdExpBasicFunctions.GETDATETIME());
			result = prdDao.updateEntity(param);
			
			map = prdDao.check(param);
			if( map.get("payordno") != null && !map.get("payordno").toString().equals("")){
				param.put("payordno", map.get("payordno"));
				result = payDao.updateEntity(param);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> check(Map<String, Object> pmap) throws TranException {
		Map<String, Object> map = null;
		try {
			map = prdDao.check(pmap);
			if (map == null) {
				throw new TranException(ExcepCode.EX080001, "商品订单不存在");
			}
			if( map.get("payordno") != null && !map.get("payordno").toString().equals("")){
				throw new TranException(ExcepCode.EX080004, "商品订单已支付");
			}
			if (map.get("ordstatus")==MsgST.ORDSTATUS_PROCESSING) {
				throw new TranException(ExcepCode.EX080006, "商品订单处理中");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		return map;
	}

	@Override
	public Map<String, Object> check(Map<String, Object> pmap, int flag)
			throws TranException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> queryPrdByOrdNo(Map<String, Object> pmap) throws TranException {
		Map<String, Object> map = null;
		try {
			map = prdDao.queryPrdInfByprdNo(pmap);
			if (map == null) {
				throw new TranException(ExcepCode.EX080001, "商品订单不存在");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		return map;
	}

}
