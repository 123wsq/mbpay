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
import com.tangdi.production.mpapp.dao.CustAccountDao;
import com.tangdi.production.mpapp.dao.CustAccountHisDao;
import com.tangdi.production.mpapp.service.CustAccountHisService;
import com.tangdi.production.mpapp.service.CustAccountService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;



/**
 * 
 * 商户账户业务实现
 * @author zhengqiang 2015/09/15	
 * @version 1.0 
 *
 */
@Service
public class CustAccountServiceImpl implements CustAccountService{
	private static final Logger log = LoggerFactory
			.getLogger(CustAccountServiceImpl.class);
	@Autowired
	private CustAccountDao custAccountDao;
	@Autowired
	private CustAccountHisService custAccountHisService;
	@Autowired
	private GetSeqNoService seqNoService;
	@Autowired
	private CustAccountHisDao custAccountHisDao;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyCustAccount(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId", "acType");
		log.info("商户余额更新参数：{}",param);
		int rt=0;
		try {
			param.put("accountLstTxDatetime", TdExpBasicFunctions.GETDATETIME());
			rt=custAccountDao.updateEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户更新异常",e);
		}
		if (rt==0) {
			throw new TranException(ExcepCode.EX000208,"商户账户更新失败");
		}
		return rt;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyCustAccountSync(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId", "acType", "oacBal","oacT0","oacT1","oacT1Y","oacT1AP","oacT1UNA");
		log.info("商户余额更新参数：{}",param);
		int rt=0;
		try {
			param.put("accountLstTxDatetime", TdExpBasicFunctions.GETDATETIME());
			rt=custAccountDao.updateEntitySync(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户更新异常",e);
		}
		return rt;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCustAccount(Map<String, Object> param) throws TranException {
		ParamValidate.doing(param, "custId");
		int rt=0;
		try {
			rt=custAccountDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户添加异常",e);
		}
		if (rt==0) {
			throw new TranException(ExcepCode.EX000208,"商户账户添加失败");
		}
		return rt;
	}
	@Override
	public Map<String, Object> queryCustAccount(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId", "acType");
		Map<String , Object> resultMap=null;
		try {
			resultMap=custAccountDao.selectEntity(param);
			log.debug("商户账户查询结果：{}",resultMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户查询异常",e);
		}
		if (resultMap==null||resultMap.size()<=0) {
			throw new TranException(ExcepCode.EX000208,"商户账户查询失败");
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> queryCustAccountNew(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId", "acType");
		Map<String , Object> resultMap=null;
		try {
			resultMap=custAccountDao.selectEntity(param);
			log.debug("商户账户查询结果：{}",resultMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户查询异常",e);
		}
		if (resultMap==null||resultMap.size()<=0) {
			
		}
		return resultMap;
	}
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> queryCustAccountLock(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId", "acType");
		Map<String , Object> resultMap=null;
		try {
			resultMap=custAccountDao.selectEntityForUpdate(param);
			log.debug("[加锁]商户账户查询结果：{}",resultMap);
			
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"[加锁]商户账户查询异常",e);
		}
		if (resultMap==null||resultMap.size()<=0) {
			throw new TranException(ExcepCode.EX000208,"[加锁]商户账户查询失败");
		}
		return resultMap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyCustAccountBalanceByPay(Map<String, Object> param)
			throws TranException {
		log.info("[支付完成后]修改余额业务参数：{}",param);
		ParamValidate.doing(param, "custId", "acType");
		Map<String , Object>  accountHisMap = new HashMap<String, Object>();
		int rt=0;
		Double payAmt=Double.valueOf(param.get("payAmt").toString());
		log.info("费率：{}",param.get("feeMap"));
		//查费率
		Double fee = Double.valueOf(((Map<String, Object>) param.get("feeMap")).get("fee").toString());
		log.info("交易金额：{}元",MoneyUtils.toStrYuan(payAmt));
		log.info("费率金额：{}元",  MoneyUtils.toStrYuan(fee));
		log.info("账户加值：{}元",  MoneyUtils.toStrYuan(payAmt-fee));
		param.put("payAtm", MoneyUtils.toStrYuan(payAmt-fee));
		try {
			//加锁查询原来的账户原信息
			Map<String , Object> resultMap=queryCustAccountLock(param);
			if(resultMap == null){
				throw new TranException(ExcepCode.EX000212);
			}
			
			Double  acBal=Double.valueOf(resultMap.get("acBal").toString());//总额
			Double  acT0= Double.valueOf(resultMap.get("acT0").toString()); //T0
			Double  acT1= Double.valueOf(resultMap.get("acT1").toString()); //T1			
			Double  acT1Y=Double.valueOf(resultMap.get("acT1Y").toString());//T1未提
			Double  acT1UNA = Double.valueOf(resultMap.get("acT1UNA").toString());//未审核
			Double  acT1AP  = Double.valueOf(resultMap.get("acT1AP").toString());//审核通过
			Double  acT1AUNP = Double.valueOf(resultMap.get("acT1AUNP").toString());//审核未通过
			if("1".equals(String.valueOf(param.get("scanornot")))){
				acT1AP = acT1AP + (payAmt-fee);
			}else{
				acT1UNA = acT1UNA + (payAmt-fee);
			}
			acT1  = acT1  + (payAmt-fee);//T1  账户加值
			acBal = acBal + (payAmt-fee);
			
			//检查系统支持最大操作金额
			if(acBal.doubleValue() > MsgST.AMT_HIG){
				throw new TranException(ExcepCode.EX000802);
			}

			//在历史表中添加记录
			accountHisMap.put("accountLogId", getHisSEQ());
			accountHisMap.put("custId", resultMap.get("custId"));
			accountHisMap.put("account", resultMap.get("account"));
			accountHisMap.put("acType", resultMap.get("acType"));
			accountHisMap.put("ccy", resultMap.get("ccy"));
			accountHisMap.put("oldAcBal", resultMap.get("acBal"));
			accountHisMap.put("oldAcT0", resultMap.get("acT0"));
			accountHisMap.put("oldAcT1", resultMap.get("acT1"));
			accountHisMap.put("oldAcT1Y", resultMap.get("acT1Y"));
			accountHisMap.put("acBal", acBal.intValue());
			accountHisMap.put("acT0",  acT0.intValue());
			accountHisMap.put("acT1",  acT1.intValue());
			accountHisMap.put("acT1Y", acT1Y.intValue());
			accountHisMap.put("acT1UNA", acT1UNA.intValue());
			accountHisMap.put("acT1AP", acT1AP.intValue());
			accountHisMap.put("acT1AUNP", 0);
			accountHisMap.put("oldFrozBalance", resultMap.get("frozBalance"));
			accountHisMap.put("lstTxDatetime", TdExpBasicFunctions.GETDATETIME());		
			accountHisMap.put("changeType", MsgCT.ACCOUT_CHANGE_TYPE_1001);			
			custAccountHisService.addCustAccountHis(accountHisMap);
			
			//更新账户
			param.put("acBal", acBal.intValue());
			param.put("acT0",  acT0.intValue());
			param.put("acT1",  acT1.intValue());
			param.put("acT1Y", acT1Y.intValue());
			param.put("acT1AP", acT1AP.intValue());
			param.put("acT1UNA", acT1UNA.intValue());
			param.put("acT1AUNP", acT1AUNP.intValue());
			
			if(MsgCT.AC_TYPE_SM.equals(param.get("acType"))){
				modifyCustAccountSync(param);
			}else{
				modifyCustAccount(param);
			}
			
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,e);
		}
		return rt;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deductCustAccountBalanceByPay(Map<String, Object> param)
			throws TranException {
		log.info("[支付撤销完成后]扣除余额业务参数：{}",param);
		ParamValidate.doing(param, "custId");
		Map<String , Object>  accountHisMap = new HashMap<String, Object>();
		int rt=0;
		Double payAmt=Double.valueOf(param.get("payAmt").toString());
		log.info("费率：{}",param.get("feeMap"));
		//查费率
		Double fee = Double.valueOf(((Map<String, Object>) param.get("feeMap")).get("fee").toString());
		log.info("交易金额：{}元",MoneyUtils.toStrYuan(payAmt));
		log.info("费率金额：{}元",  MoneyUtils.toStrYuan(fee));
		log.info("账户减值：{}元",  MoneyUtils.toStrYuan(payAmt-fee));
		param.put("payAtm", MoneyUtils.toStrYuan(payAmt-fee));
		try {
			//加锁查询原来的账户原信息
			Map<String , Object> resultMap=queryCustAccountLock(param);
			if(resultMap == null){
				throw new TranException(ExcepCode.EX000212);
			}
			
			Double  acBal=Double.valueOf(resultMap.get("acBal").toString());//总额
			Double  acT0= Double.valueOf(resultMap.get("acT0").toString()); //T0
			Double  acT1= Double.valueOf(resultMap.get("acT1").toString()); //T1			
			Double  acT1Y=Double.valueOf(resultMap.get("acT1Y").toString());//T1未提
			acT1  = acT1  - (payAmt-fee);//T1  账户加值
			acBal = acBal - (payAmt-fee);
			
			//检查系统支持最大操作金额
			if(acBal.doubleValue() > MsgST.AMT_HIG){
				throw new TranException(ExcepCode.EX000802);
			}

			//在历史表中添加记录
			accountHisMap.put("accountLogId", getHisSEQ());
			accountHisMap.put("custId", resultMap.get("custId"));
			accountHisMap.put("account", resultMap.get("account"));
			accountHisMap.put("acType", resultMap.get("acType"));
			accountHisMap.put("ccy", resultMap.get("ccy"));
			accountHisMap.put("oldAcBal", resultMap.get("acBal"));
			accountHisMap.put("oldAcT0", resultMap.get("acT0"));
			accountHisMap.put("oldAcT1", resultMap.get("acT1"));
			accountHisMap.put("oldAcT1Y", resultMap.get("acT1Y"));
			accountHisMap.put("acBal", acBal.intValue());
			accountHisMap.put("acT0",  acT0.intValue());
			accountHisMap.put("acT1",  acT1.intValue());
			accountHisMap.put("acT1Y", acT1Y.intValue());
			accountHisMap.put("oldFrozBalance", resultMap.get("frozBalance"));
			accountHisMap.put("lstTxDatetime", TdExpBasicFunctions.GETDATETIME());		
			accountHisMap.put("changeType", MsgCT.ACCOUT_CHANGE_TYPE_1001);			
			custAccountHisService.addCustAccountHis(accountHisMap);
			
			//更新账户
			param.put("acBal", acBal.intValue());
			param.put("acT0",  acT0.intValue());
			param.put("acT1",  acT1.intValue());
			param.put("acT1Y", acT1Y.intValue());
			modifyCustAccount(param);
			
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,e);
		}
		return rt;
	}
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyCustAccountBalanceByCash(Map<String, Object> param)
			throws TranException {
		log.info("[提现完成后]修改余额业务参数：{}",param);
		ParamValidate.doing(param, "custId");
		Map<String , Object>  accountHisMap = new HashMap<String, Object>();
		int rt=0;
		//提现金额
		Double txamt=NumberUtils.toDouble(String.valueOf(param.get("txamt")));
		Double fee = NumberUtils.toDouble(String.valueOf(param.get("fee")));
		Double serviceFee = NumberUtils.toDouble(String.valueOf(param.get("serviceFee")));

		log.info("交易金额：{}元",MoneyUtils.toStrYuan(txamt));
		log.info("手续费：{}元",  MoneyUtils.toStrYuan(fee));
		log.info("服务费：{}元",  MoneyUtils.toStrYuan(serviceFee));
		log.info("账户减值：{}元",MoneyUtils.toStrYuan(fee+serviceFee));
		try {
			//在历史表中添加记录
			accountHisMap.put("accountLogId", getHisSEQ());
			accountHisMap.put("custId", param.get("custId"));
			accountHisMap.put("account", param.get("account"));
			accountHisMap.put("acType", param.get("acType"));
			accountHisMap.put("ccy",    param.get("ccy"));
			accountHisMap.put("oldAcBal", param.get("oldAcBal"));
			accountHisMap.put("oldAcT0",  param.get("oldAcT0"));
			accountHisMap.put("oldAcT1",  param.get("oldAcT1"));
			accountHisMap.put("oldAcT1Y", param.get("oldAcT1Y"));
			accountHisMap.put("oldAcT1AP", param.get("oldAcT1AP"));
			accountHisMap.put("oldAcT1AUNP", param.get("oldAcT1AUNP"));
			accountHisMap.put("oldAcT1UNA", param.get("oldAcT1UNA"));
			accountHisMap.put("acBal", param.get("acBal"));
			accountHisMap.put("acT0",  param.get("acT0"));
			accountHisMap.put("acT1",  param.get("acT1"));
			accountHisMap.put("acT1Y", param.get("acT1Y"));
			accountHisMap.put("acT1AP", param.get("acT1AP"));
			accountHisMap.put("oldFrozBalance", param.get("frozBalance"));
			accountHisMap.put("lstTxDatetime", TdExpBasicFunctions.GETDATETIME());			
			accountHisMap.put("changeType", MsgCT.ACCOUT_CHANGE_TYPE_2001);			
			custAccountHisService.addCustAccountHis(accountHisMap);
			
			//更新账户
			modifyCustAccount(param);
			
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000212,"商户账户更新异常",e);
		}
		return rt;
	}
	
	/**
	 * 获取历史账户序号
	 * @return
	 */
	private synchronized String getHisSEQ(){
		try {
			String acLogId = TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("AC_HIS_ID", "9", "1");
			log.info("历史账户序号:[{}]",acLogId);
			return acLogId;
		} catch (Exception e) {
			log.error("获取历史账户序号失败.");
			return TdExpBasicFunctions.GETDATE() + "000000000";
		}
	}

	
	
	

	
}
