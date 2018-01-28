package com.tangdi.production.mpomng.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.CasPrdDao;
import com.tangdi.production.mpomng.report.CasPrdTemplate;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

/**
 * 
 * @author zhengqiang 2015/09/18
 *
 */
@Service
public class CasPrdServiceImpl implements CasPrdService {
	private static final Logger log = LoggerFactory.getLogger(CasPrdServiceImpl.class);
	@Autowired
	private CasPrdDao casPrdDao;

	@Autowired
	private GetSeqNoService seqNoService;
	
	@Autowired
	private HessinClientBean remoteService;
	
	@Autowired
	private CustAccountService custAccountService;
	
	@Autowired
	private FileReportService<CasPrdTemplate> reportService;

	@Override
	public Integer getCasPrdCount(Map<String, Object> paramMap) {
		return casPrdDao.getCasPrdCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getCasPrdListPage(
			Map<String, Object> paramMap) {
		return casPrdDao.getCasPrdListPage(paramMap);
	}

	@Override
	public Map<String, Object> casCount(Map<String, Object> paramMap) {
		return casPrdDao.casCount(paramMap);
	}

	@Override
	public Map<String, Object> casDetails(Map<String, Object> paramMap) {
		return casPrdDao.casDetails(paramMap);
	}
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		List<CasPrdTemplate> datas = null;
		try {
			datas = casPrdDao.selectReport(con);
		} catch (Exception e) {
			log.error("生成清算文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		int size = reportService.report(datas, uid, "清算文件", "清算文件", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		log.debug("文件大小:[{}]",size);
		return size;

	}
	/*
	@Override
	public void auditCasPrd(String approved, String custId, String ordAmt, String casOrdNo) throws Exception {
		try {
			if("0".equals(approved)){
				// 1.审核通过
				// 1.1 更新账户信息表
				CustAccountInf custAccountInf_ = new CustAccountInf();
				custAccountInf_.setCustId(custId);
				CustAccountInf custAccountInf = custAccountDao.selectEntity(custAccountInf_);
				String acBal = String.valueOf(Double.valueOf(custAccountInf.getAcBal()).intValue() - Double.valueOf(ordAmt).intValue()*100);
				custAccountInf.setAcBal(acBal);
				String acT0 = String.valueOf(Double.valueOf(custAccountInf.getAcT0()).intValue() - Double.valueOf(ordAmt).intValue()*100);
				custAccountInf.setAcT0(acT0);
				custAccountDao.updateEntity(custAccountInf);
				// 1.2 更新提现订单表
				Map<String, String> param = new HashMap<String, String>();
				param.put("casOrdNo", casOrdNo);
				param.put("casAudit", "02");
				param.put("ordStatus", "01");
				casPrdDao.modifyCasPrdBystauts(param);
			}else{
				// 2.审核不通过
				// 1.1 更新账户信息表
				CustAccountInf custAccountInf_ = new CustAccountInf();
				custAccountInf_.setCustId(custId);
				CustAccountInf custAccountInf = custAccountDao.selectEntity(custAccountInf_);
				String acT0 = String.valueOf(Double.valueOf(custAccountInf.getAcT0()).intValue() - Double.valueOf(ordAmt).intValue()*100);
				custAccountInf.setAcT0(acT0);
				String acT1 = String.valueOf(Double.valueOf(custAccountInf.getAcT1()).intValue() + Double.valueOf(ordAmt).intValue()*100);
				custAccountInf.setAcT1(acT1);
				custAccountDao.updateEntity(custAccountInf);
				// 1.2 更新提现订单表
				Map<String, String> param = new HashMap<String, String>();
				param.put("casOrdNo", casOrdNo);
				param.put("casAudit", "01");
				param.put("ordStatus", "01");
				casPrdDao.modifyCasPrdBystauts(param);
			}
		} catch (Exception e) {
			throw new TranException(e.getCause());
		}
	}*/
	
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCas(Map<String, Object> param) throws Exception {
		log.info("提现订单创建开始：{}",param);
		String casId =null;
		int rt=0;
		try {
			casId= TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("CASID", "9", "0");
			param.put("casOrdNo", casId);
			param.put("casDate", TdExpBasicFunctions.GETDATETIME());
			param.put("ordstatus", "01");  
			param.put("casAudit",  "02"); 
			if(param.containsKey("SUC_DATE")){
				param.put("sucDate",  TdExpBasicFunctions.GETDATETIME()); 
				param.put("auditDesc",  "系统自动审核"); 
			}
			rt=casPrdDao.insertEntity(param);
		} catch (Exception e) {
			throw new Exception("提现订单生成异常.",e);
		} 
		if(rt==0){
			throw new Exception("提现订单生成出错了.");
		}
		return rt;
	}

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setSettlementStatusEnd() throws Exception {
		casPrdDao.updateCasPrdStatus();
	}

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void settleCasPrdOver(String casOrdNos) throws Exception {
		String[] nos = casOrdNos.split(",");
		for(String casOrdNo : nos){
			casPrdDao.updateCasPrdStatusById(casOrdNo);
		}
	}

	@Override
	public int getCasPrdFromMerCount(Map<String, Object> paramMap) {
		return getCasPrdCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getCasPrdListFromMerPage(
			Map<String, Object> paramMap) {
		return getCasPrdListPage(paramMap);
	}

	@Override
	public Map<String, Object> casPrdFromMerCount(Map<String, Object> paramMap) {
		return casCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getCasPrdT0(Map<String, String> param) throws Exception {
		return casPrdDao.selectCasPrdT0(param);
	}

	@Override 
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyCasPrdT0(Map<String, Object> param) throws Exception {
		return casPrdDao.updateCasPrdT0(param);
	}

	@Override
	public int modifyCasPrdStatusForDone(Map<String, Object> param)
			throws Exception {
		return casPrdDao.updateCasPrdStatusForDone(param);
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyCasPrdBystauts(Map<String, Object> paramMap) throws Exception{
		//审核
		String adus = paramMap.get("casAudit").toString();
		String casOrdNos_ = paramMap.get("casOrdNos").toString();
		String[] casOrdNos = casOrdNos_.split(",");
		for(String casOrdNo : casOrdNos){
			paramMap.put("casOrdNo", casOrdNo);
			Map<String, Object> map = null;
			try {
				//订单加锁
				map = casPrdDao.selectCustByCasNoForUpdate(casOrdNo);
			} catch (Exception e) {
				log.error("查询提现订单信息出错了.",e);
				throw new Exception("查询提现订单信息出错了.",e);
			}
			if(adus.equals("01")){  //审核拒绝
				try {
					custAccountService.blanceChange01(map);
				} catch (Exception e) {
					log.error("更新余额账户出错了.",e);
					throw new Exception("更新余额账户出错了.",e);
				}
			}else if(adus.equals("02")){ //审核通过
				try {
					//注： 提交订单时减帐户余额, 审核拒绝时退回帐户。 审核通过时不操作账户
					//custAccountService.blanceChange02(map);
				} catch (Exception e) {
					log.error("更新余额账户出错了.",e);
					throw new Exception("更新余额账户出错了.",e);
				}
			}
			try {
				
				casPrdDao.modifyCasPrdBystauts(paramMap);
			} catch (Exception e) {
				log.error("修改提现审核状态.",e);
				throw new Exception("更新余额账户出错了.",e);
			}
		}
		return 1;
	}

	@Override
	public int addAccount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		log.info("商户余额提现订单创建开始：{}",param);
		String casId =null;
		int rt=0;
		try {
			casId= TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("CASID", "9", "0");
			param.put("casOrdNo", casId);
			param.put("casDate",  TdExpBasicFunctions.GETDATETIME());
			param.put("ordstatus", "01");  
			param.put("casAudit",  "02"); 
			if(param.containsKey("SUC_DATE")){
				param.put("sucDate",  TdExpBasicFunctions.GETDATETIME()); 
				param.put("auditDesc",  "系统自动审核"); 
			}
			log.info("订单准备数据："+param.toString());
			rt = casPrdDao.insertEntity(param);
		} catch (Exception e) {
			throw new Exception("商户余额提现订单生成异常.",e);
		} 
		if(rt==0){
			throw new Exception("商户余额提现订单生成出错了.");
		}
		return rt;
	}
	
	@Override
	public int lmtAddAccount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		log.info("查询额度提现订单创建开始：{}",param);
		String casId =null;
		int rt=0;
		try {
			casId= TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("CASID", "9", "0");
			param.put("casOrdNo", casId);
			param.put("casDate",  TdExpBasicFunctions.GETDATETIME());
			param.put("ordstatus", "01");  
			param.put("casAudit",  "02"); 
			if(param.containsKey("SUC_DATE")){
				param.put("sucDate",  TdExpBasicFunctions.GETDATETIME()); 
				param.put("auditDesc",  "系统自动审核"); 
			}
			log.info("订单准备数据："+param.toString());
			rt = casPrdDao.insertEntity(param);
		} catch (Exception e) {
			throw new Exception("查询额度提现订单生成异常.",e);
		} 
		if(rt==0){
			throw new Exception("查询额度提现订单生成出错了.");
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void settleCasPrdFalse(String casOrdNos) throws Exception {
		String[] nos = casOrdNos.split(",");
		for(String casOrdNo : nos){
			log.info("当前处理体现订单号：{}", casOrdNo);
			log.info("1.设置清算状态为清算失败");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("casOrdNo", casOrdNo);
			param.put("ordStatus", "02");//手工清算失败，状态修改为02
			param.put("sucDate", TdExpBasicFunctions.GETDATETIME());
			casPrdDao.updateCasPrdStatusById(param);
			
			log.info("2.回滚账户余额");
			Map<String, Object> casOrdMap = casPrdDao.selectCustByCasNo(casOrdNo);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("custId", casOrdMap.get("custId"));
			paramMap.put("t0", casOrdMap.get("t0"));
			paramMap.put("t1", casOrdMap.get("t1"));
			paramMap.put("casType", casOrdMap.get("casType"));
			
			custAccountService.settleFalse(casOrdMap);
		}
	}
	
	/**
	 * 实时代付
	 * */
	@Override
	public Map<String,Object> casPay(String str, String str2) throws Exception{
		log.info("实时代付交易开始...");
		String[] casOrdNos = str.split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		for(String casOrdNo:casOrdNos){
			//获取订单信息
			Map<String, Object> pmap = new HashMap<String,Object>();
			Map<String, Object> pmap2 = new HashMap<String,Object>();
			try{
				pmap = casPrdDao.getCasPayInfo(str);
				pmap2 = casPrdDao.getCasPayType(str2);
			}catch(Exception e){
				throw new Exception("查询提现订单信息异常.",e);
			}
			pmap.put("cooporgMerNo", pmap2.get("merNo"));
			//远程调用，发送第三方通道
			Map<String, Object> rmap = remoteService.dotran(TranCode.TRAN_PAY, pmap);
			log.info("远程调用结果：{}",rmap);
			map.put("filed3", rmap.get("filed3"));
			//根据结果将提现订单置为清算中
			if("00".equals(rmap.get("TCPSCod"))){
				map.put("ordStatus", "07");//清算成功
				map.put("casOrdNo", str);//订单号
				map.put("sucDate", DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss"));
				try{
					casPrdDao.modifyCasPrdBystauts(map);
				}catch(Exception e){
					throw new Exception("更新订单信息异常.",e);
				}
			}else if("tt".equals(rmap.get("TCPSCod")) || "98".equals(rmap.get("TCPSCod"))){
				map.put("ordStatus", "06");//清算中
				map.put("casOrdNo", str);//订单号
				rmap.put("casOrdNo", str);
				try{
					casPrdDao.modifyCasPrdBystauts(map);
					//保存代付信息至代付确认表
					rmap.put("payTime", DateUtil.get(DateUtil.FULL_TIME_FORMAT_EN, 0));
					rmap.put("payStatus", "0");
					rmap.put("cooporgNo", str2);
					log.info("确认代付插入数据：{}",rmap.toString());
					casPrdDao.deleteConPay(rmap);
					casPrdDao.insertConPay(rmap);
				}catch(Exception e){
					throw new Exception("更新订单信息异常.",e);
				}
			}else{
				map.put("ordStatus", "08");//待清算（代付失败）
				map.put("casOrdNo", str);//订单号
				try{
					casPrdDao.modifyCasPrdBystauts(map);
				}catch(Exception e){
					throw new Exception("更新订单信息异常.",e);
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 额度查询
	 * */
	@Override
	public Map<String,Object> limitQuery(String str, String str2) throws Exception{
		log.info("额度查询开始...");
		Map<String,Object> map = new HashMap<String,Object>();
			//获取订单信息
			Map<String, Object> pmap = new HashMap<String,Object>();
			Map<String, Object> pmap2 = new HashMap<String,Object>();
			//华势商终
			try{
				pmap2 = casPrdDao.getLImitQuery(str2);
			}catch(Exception e){
				throw new Exception("查询限制额度信息异常.",e);
			}
			pmap.put("cooporgMerNo", pmap2.get("merNo"));
			pmap.put("terNo", pmap2.get("terNo"));
			pmap.put("agentId", pmap2.get("agentId"));
			pmap.put("firstAgentId", pmap2.get("firstAgentId"));
			pmap.put("orgNo", str2);
			
			//远程调用，发送第三方通道
			Map<String, Object> rmap = remoteService.dotran(TranCode.TRAN_LMTQRY, pmap);
			pmap2.putAll(rmap);
			log.info("远程调用结果：{}",rmap);
		return pmap2;
	}
}
