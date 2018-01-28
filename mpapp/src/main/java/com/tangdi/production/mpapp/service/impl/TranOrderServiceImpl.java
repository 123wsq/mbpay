package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.TranOrderDao;
import com.tangdi.production.mpapp.service.TranOrderService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 获取交易记录接口
 * author zhengqiang 2015/08/24 update
 *
 */
@Service
public class TranOrderServiceImpl implements TranOrderService {
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	private TranOrderDao dao;
	
	/**
	 * 文件上传接口
	 */
	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	public  List<Map<String,Object>>  queryList(Map<String,Object> param) throws Exception {
		
		ParamValidate.doing(param, "start","pageSize","recordType");

		List<Map<String, Object>> resultList = null;
		
		int start_    = Integer.valueOf(param.get("start").toString());
		param.put("start", start_);
		int pageSize_ = Integer.valueOf(param.get("pageSize").toString()); 
		param.put("pageSize", pageSize_);
		log.info("请求页：[{}],每页显示记录[{}]",start_,pageSize_);
		try {
			//param.put("start", start_*pageSize_);
			log.info("start: [{}], pageSize: [{}]",param.get("start"),param.get("pageSize"));
			resultList = dao.selectList(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX080001,e);
		}
		log.debug("订单交易信息:{}",resultList);
		return resultList;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param)
			throws Exception {
		
		ParamValidate.doing(param, "busType","ordno");
		Map<String,Object> ordMap = new HashMap<String,Object>();
		try {
			String busType = param.get("busType").toString();
			log.info("订单类型:[{}]",busType);
			if(MsgST.PRDORDTYPE_CASH.equals(busType)){
				ordMap = dao.selectCasDetail(param);
			}else if(MsgST.PRDORDTYPE_GOODS.equals(busType) ||
					 MsgST.PRDORDTYPE_PAYMENTS.equals(busType)){
				ordMap =dao.selectPrdDetail(param);
				if(MsgCT.PAYTYPE_SM.equals(ordMap.get("payType"))){ //扫码无电子签名
					ordMap.put("fjpath", "");
				}else{
					try{
						ordMap.put("fjname", fileUploadService.getRemotePath(ordMap.get("picId").toString(),MsgST.PIC_TYPE_ESIGN));
						String fjpath = fileUploadService.getFilePath(ordMap.get("picId").toString(), MsgST.PIC_TYPE_OTHER);
						if(null != fjpath && !"".equals(fjpath) && "/".equals(fjpath.charAt(0)))
							fjpath = fjpath.substring(1);
						ordMap.put("fjpath", fjpath);
					} catch (Exception e) {
						throw new TranException(ExcepCode.EX001004,"未找到电子签名记录！",e);
					}
				}
			}
			log.info("订单详细信息:{}",ordMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX080001,e);
		}
		return ordMap;
	}

	@Override
	public String redDot(Map<String,Object> param) throws Exception {
		
		String redDot = "2";
		if(dao.selectCount(param)>0){
			redDot = "1";
		}
		return redDot;
		
	}
	
	@Override
	public Map<String, Object> querySMPayResult(Map<String, Object> param)
			throws Exception {
		
		ParamValidate.doing(param, "prdordNo");
		Map<String,Object> ordMap = new HashMap<String,Object>();
		try {
			ordMap = dao.selectPrdPayStatus(param);
			if(MsgCT.PAY_STATUS_S.equals(ordMap.get("paystatus")) ||
					MsgCT.PRD_STATUS_S.equals(ordMap.get("ordstatus"))){
				ordMap.put("payResult", MsgST.TXNSTATUS_S);
			}else if(MsgCT.PAY_STATUS_F.equals(ordMap.get("paystatus")) ||
					MsgCT.PRD_STATUS_F.equals(ordMap.get("ordstatus"))){
				ordMap.put("payResult", MsgST.TXNSTATUS_F);
			}else{
				ordMap.put("payResult", MsgST.TXNSTATUS_U);
			}
			ordMap.put("payTime", "" + ordMap.get("payDate") + ordMap.get("payTime"));
			log.info("扫码支付结果信息:{}",ordMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX080001,e);
		}
		return ordMap;
	}
	
}
