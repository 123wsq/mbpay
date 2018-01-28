package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.PayDao;
import com.tangdi.production.mpapp.service.ESignService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 终端[手机发起的]支付订单接口实现类
 * 
 * @author limiao
 *
 */
@Service
public class TermPayServiceImpl implements TermPayService {
	private static Logger log = LoggerFactory.getLogger(TermPayServiceImpl.class);
	@Autowired
	private PayDao payDao;
	@Autowired
	private GetSeqNoService seqNoService;
	/**
	 * 电子签名接口
	 */
	@Autowired
	private ESignService esignService;

	@Override @SuppressWarnings("unchecked") 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createPay(Map<String, Object> param) throws TranException {
		log.info("支付订单创建中...");
		try {
			ParamValidate.doing(param, "prdordNo", "payType", "termNo", "rate","scancardnum","scanornot");
		} catch (TranException e) {
			throw new TranException(ExcepCode.EX900000, e);
		}
		param.put("rateType", param.get("rate"));
		Map<String, Object> prdordMap = (Map<String, Object>) param.get("prdordMap");
		Map<String, Object> feeMap = (Map<String, Object>) param.get("feeMap");

		Double payAmt = NumberUtils.toDouble((String) param.get("payAmt"));
		param.put("payAmt", payAmt.intValue());
		
		if (!(prdordMap.get("ordamt").toString().equals(param.get("payAmt").toString()))) {
			log.warn("商品订单与支付订单 金额不符 数据库查询到的："+prdordMap.get("ordamt")+";参数所传："+param.get("payAmt"));
			throw new TranException(ExcepCode.EX080002, "商品订单与支付订单 金额不符");
		}
		String payNo = "";
		try {
			payNo = TdExpBasicFunctions.GETDATE() + seqNoService.getSeqNoNew("PAY_NO", "9", "1");
			param.put("payordno", payNo);
			param.put("paystatus", MsgST.PAYSTATUS_NO_PAY);
			param.put("payDate", TdExpBasicFunctions.GETDATE());
			param.put("payTime", TdExpBasicFunctions.GETDATETIME("HHMISS"));
			param.put("payordtime", TdExpBasicFunctions.GETDATETIME());
			param.put("rate", feeMap.get("rate"));
			param.put("fee", feeMap.get("fee"));
			param.put("txamt", param.get("payAmt"));
			param.put("paytype", param.get("payType"));
			param.put("scancardnum", param.get("scancardnum"));
			Double netrecamt = Double.valueOf(param.get("payAmt").toString()) - Double.valueOf(feeMap.get("fee").toString());
			param.put("netrecamt", netrecamt.intValue());
			param.put("cardSignPic", prdordMap.get("cardPhotoId"));

			log.debug("支付订单 信息:{}", param);
			String scanornot = param.get("scanornot").toString();
			if("1".equals(scanornot)){
				param.put("auditStatus", "1");
			}else{
				param.put("auditStatus", "0");
			} 
				
			if (payDao.insertEntity(param) <= 0) {
				throw new TranException(ExcepCode.EX080003, "创建支付订单失败订单");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080003, e);
		}
		log.info("支付订单创建完成,订单号:[{}]",payNo);
		return payNo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyPay(Map<String, Object> map) throws TranException {
		Integer result = 0;
		String esign = null;
		try {
			log.info("支付订单更新中...");
			 esign = esignService.query(map.get("prdordNo").toString());
			 log.debug("电子签名ID=[{}]",esign);
			 
			 map.put("modifyTime", TdExpBasicFunctions.GETDATETIME());
			 result = payDao.updateEntity(map);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		map.put("esign", esign);
		log.info("支付订单更新完成.处理结果:[{}]",result);
		return result;
	}

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyPayByRedo(Map<String, Object> map) throws TranException {
		Integer result = 0;
		try {
			log.info("支付订单更新中.参数：{}",map);
			map.put("modifyTime", TdExpBasicFunctions.GETDATETIME());
			result = payDao.updateEntity(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		log.info("支付订单更新完成.处理结果:[{}]",result);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyESign(String payordno,String fid) throws TranException {
		Integer result = 0;
		try {
			log.info("支付订单电子签名更新中...");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("paySignPic", fid);
			map.put("payordno", payordno);
			result = payDao.updateESign(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080011, e);
		}
		log.info("支付订单电子签名更新完成.处理结果:[{}]",result);
		return result;
	}

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyPaying(Map<String, Object> map) throws TranException {
		Integer result = 0;
		try {
			 log.info("支付订单处理中...");
			
			 map.put("modifyTime", TdExpBasicFunctions.GETDATETIME());
			 result = payDao.updateEntity(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080001, e);
		}
		log.info("支付订单状态更新完成.处理结果:[{}]",result);
		return result;
	}

	@Override
	public Map<String, Object> queryPayOrdById(Map<String, Object> map)
			throws TranException {
		Map<String, Object> rmap = null;
		try {
			rmap = payDao.selectEntity(map);
			if (rmap == null) {
				throw new TranException(ExcepCode.EX080008, "支付订单不存在");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080008, e);
		}
		return rmap;
	}
	
	@Override
	public Map<String, Object> queryPayInfWithAccById(Map<String, Object> map)
			throws TranException {
		Map<String, Object> rmap = null;
		try {
			rmap = payDao.selectPayInfAcc(map);
			if (rmap == null) {
				throw new TranException(ExcepCode.EX080008, "支付订单不存在");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX080008, e);
		}
		return rmap;
	}

}
