package com.tangdi.production.mpomng.service.impl;

import java.util.ArrayList;
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

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.mpomng.bean.PayInf;
import com.tangdi.production.mpomng.bean.PrdInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.CustAccountDao;
import com.tangdi.production.mpomng.dao.PrdInfDao;
import com.tangdi.production.mpomng.report.PrdTemplate;
import com.tangdi.production.mpomng.report.RechargePrdTemplate;
import com.tangdi.production.mpomng.service.PrdInfService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class PrdInfServiceImpl implements PrdInfService {
	private static final Logger log = LoggerFactory.getLogger(PrdInfServiceImpl.class);
	@Autowired
	private PrdInfDao dao;
	
	@Autowired
	private CustAccountDao custAccountDao;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Autowired
	private FileReportService<Object> reportService;
	
	@Autowired
	private FileReportService<PrdTemplate> reportService01;
	@Autowired
	private FileReportService<RechargePrdTemplate> reportService02;

	@Override
	public List<PrdInf> getListPage(PrdInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(PrdInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public PrdInf getEntity(PrdInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		PrdInf prdInf = null;
		try {
			prdInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", prdInf.debug());
		return prdInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(PrdInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String prdordno = seqNoService.getSeqNoNew("PRDORDNO", "9", "0");
			entity.setPrdordno(prdordno);
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(PrdInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("更新数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(PrdInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.deleteEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("删除数据异常!" + e.getMessage(), e);
		}
		return rt;
	}
	@Override
	public List<PrdInf> getCountList(PrdInf entity) throws Exception{
		List<PrdInf> resultList=null;
		try{
			resultList=dao.getCountList(entity);
		}catch(Exception e){
			log.error("收款订单查询失败！{}",e.getMessage());
			throw new Exception(e);
		}
		return resultList;
	}

	@Override
	public int getRechargeCount(Map<String, Object> paramMap) {
		formatTimes(paramMap);
		return dao.getRechargeCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getRechargeListPage(
			Map<String, Object> paramMap) {
		return dao.getRechargeListPage(paramMap);
	}

	public void formatTimes(Map<String, Object> paramMap){
		String startTime=(String) paramMap.get("startTime");
		String endTime=(String) paramMap.get("endTime");
		String stimes="";
		String etimes="";
		if(startTime != null && !startTime.equals("") &&startTime.length() <=10){
			stimes = startTime.substring(0,4);
			stimes=stimes+startTime.substring(5,7);
			stimes=stimes+startTime.substring(8,10);
		}else{
			stimes = startTime;
		}
		paramMap.put("startTime", stimes);
		
		if(endTime != null && !endTime.equals("") &&endTime.length() <=10){
			etimes = endTime.substring(0,4);
			etimes=etimes+endTime.substring(5,7);
			etimes=etimes+endTime.substring(8,10);
		}else{
			etimes = endTime;
		}
		paramMap.put("endTime", etimes);
	}
	
	@Override
	public Map<String, Object> prdDetails(Map<String, Object> paramMap)
			throws Exception {
		return dao.selectPrdDetails(paramMap);
	}

	@Override
	public Map<String, Object> rechargeCount(Map<String, Object> paramMap) {
		return dao.rechargeCount(paramMap);
	}

	@Override
	public Map<String, Object> rechargeDetails(Map<String, Object> paramMap) {
		return dao.rechargeDetails(paramMap);
	}

	@Override
	public int getAgentPrdCount(Map<String, Object> paramMap) {
		return dao.getAgentPrdCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getAgentPrdList(
			Map<String, Object> paramMap) {
		return dao.getAgentPrdList(paramMap);
	}

	@Override
	public Map<String, Object> prmPrdCount(Map<String, Object> paramMap) {
		return dao.prmPrdCount(paramMap);
	}

	@Override
	public Map<String, Object> prmPrdDetails(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return dao.prmPrdDetails(paramMap);
	}
	@Override
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		if("01".equals(con.get("prdType").toString())){
			List<PrdTemplate> data = null;
			try {
				data = dao.selectPrdReport(con);
			} catch (Exception e) {
				log.error("生成商品订单文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService01.report(data, uid, "商品订单文件", "商品订单报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}else if("02".equals(con.get("prdType").toString())) {
			List<RechargePrdTemplate> data = null;
			try {
				data = dao.selectRechargePrdReport(con);
				
				log.debug("PrdInfServiceImpl.report收款订单数据："+data.size());
			} catch (Exception e) {
				log.error("生成收款订单文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService02.report(data, uid, "收款订单文件", "收款订单报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyAuditStatus(PayInf payInf) throws Exception {
		int rt=0;
		
		String custId = payInf.getCustId();
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		CustAccountInf entity = new CustAccountInf();
		entity.setCustId(payInf.getCustId());
		entity.setAcType(payInf.getPaytype());
		entity=custAccountDao.selectEntity(entity);
		
		Double acT1UNA = Double.parseDouble(entity.getAcT1UNA());//未审核
		Double acT1AP = Double.parseDouble(entity.getAcT1AP());//审核通过
		Double acT1AUNP = Double.parseDouble(entity.getAcT1AUNP());//审核不通过
		Double acT1Y = Double.parseDouble(entity.getAcT1Y());//T1未提
		Double netrecamt = Double.parseDouble(payInf.getNetrecamt());
		
		//判断运营人员审核或风控人员审核 ：1 运营 2风控
		if(payInf.getCheckAudit().equals("2")){
			//查询风险备用金账户
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap=custAccountDao.selectRiskAmt();
			Double Amt = Double.parseDouble(paramMap.get("Amt").toString());
			
			//1：通过，金额从审核不通过转至审核通过
			//2：不通过，金额从审核不通过转至备用金
			if(payInf.getAuditStatus().toString().equals("1")){
				acT1AUNP = acT1AUNP - netrecamt;
				if(TdExpBasicFunctions.GETDATE().equals(payInf.getPayDate())){
					acT1AP = acT1AP + netrecamt;
				}else{
					acT1Y = acT1Y + netrecamt;
				}
				//订单状态： 1 审核通过
				payInf.setAuditStatus("1");
			}else if(payInf.getAuditStatus().toString().equals("2")){
				acT1AUNP = acT1AUNP - netrecamt;
				Amt = Amt + netrecamt;
				custAccountDao.changeRiskAmt(Amt.toString());
				//订单状态： 3 最终审核不通过
				payInf.setAuditStatus("3");
			}
			if(acT1AUNP<0){
				log.debug("金额小于的时");
				throw new TranException(ExcepCode.EX000217);
				
			}
			map.put("txamt", 0); //总额不变
			map.put("custId", custId);
			map.put("acT1AP", acT1AP);
			map.put("acT1AUNP",acT1AUNP);
			map.put("acT1UNA", acT1UNA);
			map.put("acT1Y",acT1Y);
			map.put("acType",entity.getAcType());
			map.put("oldversion", entity.getVersion());
			log.debug("date操作时间==================="+new Date());
			log.debug("auth操作人==================="+payInf.getUserName());
			
			log.debug("old==================="+entity.getVersion());
			
			map.put("version", entity.getVersion()+1);
			log.debug("new==================="+entity.getVersion()+1);
			try{
				//查询状态
				Map<String,Object> paramMapStatus = new HashMap<String,Object>();
				paramMapStatus=dao.selectStatus(payInf);
				String selectState=paramMapStatus.get("auditStatus").toString();
				log.debug("auditStatus==================="+selectState);
				if(!selectState.equals("2")){
					throw new TranException(ExcepCode.EX000217);
				}else{
				      custAccountDao.changeAccountByAudit(map); //更改余额
				}
			}catch(Exception e){
				throw new TranException("请重新读取后审核！");
			}
		}else if (payInf.getCheckAudit().equals("1")){
			//1：通过，金额从未审核转至审核通过
			//2：不通过，金额从未审核转至审核不通过
			if(payInf.getAuditStatus().toString().equals("1")){
				acT1UNA = acT1UNA - netrecamt;
				if(TdExpBasicFunctions.GETDATE().equals(payInf.getPayDate())){
					acT1AP = acT1AP + netrecamt;
				}else{
					acT1Y = acT1Y + netrecamt;
				}
				//订单状态：1 审核通过
				payInf.setAuditStatus("1");
			}else if(payInf.getAuditStatus().toString().equals("2")){
				acT1UNA = acT1UNA - netrecamt;
				acT1AUNP = acT1AUNP + netrecamt;
				//订单状态： 2 审核不通过
				payInf.setAuditStatus("2");
			}
			if(acT1AUNP<0){
				log.debug("金额小于的时");
				throw new TranException(ExcepCode.EX000217);
			}
			map.put("txamt", 0); //总额不变
			map.put("custId", custId);
			map.put("acT1UNA",acT1UNA);
			map.put("acT1AP", acT1AP);
			map.put("acT1AUNP",acT1AUNP);
			map.put("acT1Y",acT1Y);
			map.put("acType",entity.getAcType());
			map.put("oldversion", entity.getVersion());
			map.put("version", entity.getVersion()+1);
			log.debug("date操作时间==================="+new Date());
			log.debug("auth操作人==================="+payInf.getUserName());
			
			log.debug("old==================="+entity.getVersion());
			log.debug("new==================="+entity.getVersion()+1);
			
			try{
				//查询状态
				Map<String,Object> paramMapStatus = new HashMap<String,Object>();
				paramMapStatus=dao.selectStatus(payInf);
				String selectState=paramMapStatus.get("auditStatus").toString();
				log.debug("selectState==================="+selectState);
				if(!selectState.equals("0")){
					throw new TranException(ExcepCode.EX000217);
				}else{
				   custAccountDao.changeAccountByAudit(map); //更改余额
				}
			}catch(Exception e){
				throw new TranException("请重新读取后审核！");
			}
			
		}
		payInf.setAuditTime(TdExpBasicFunctions.GETDATETIME());
		rt = dao.updateStatus(payInf);
		return rt;
	}
		
	}
