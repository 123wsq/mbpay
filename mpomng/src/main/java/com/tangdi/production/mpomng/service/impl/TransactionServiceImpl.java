package com.tangdi.production.mpomng.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.TransactionDao;
import com.tangdi.production.mpomng.report.TransTemplate;
import com.tangdi.production.mpomng.report.TransactionTemplate;
import com.tangdi.production.mpomng.report.WithdrawTemplate;
import com.tangdi.production.mpomng.service.TransationService;
import com.tangdi.production.mprcs.bean.TranSerialRecordInf;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class TransactionServiceImpl implements TransationService {
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
	@Autowired
	private TransactionDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	@Autowired
	private FileReportService<TransactionTemplate> reportService;

	@Autowired
	private FileReportService<TransTemplate> reportService01;
	
	@Autowired
	private FileReportService<WithdrawTemplate> reportService02;
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		if("01".equals(con.get("state").toString())){
			List<TransactionTemplate> data = null;
			String yday = DateUtil.getYesterday();
			String cday = DateUtil.getDay();
			TranSerialRecordInf entity=new TranSerialRecordInf();
			entity.setStartTime(yday+MsgCT.DAY_TIME_START);
			entity.setEndTime(cday+MsgCT.DAY_TIME_END);
			con.put("stime", entity.getStartTime());
			con.put("etime", entity.getEndTime());
			try {
				data = dao.selectReport(con);
			} catch (Exception e) {
				log.error("生成当天交易明细数据文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService.report(data, uid, "当天交易明细文件", "当天交易明细报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}else if("02".equals(con.get("state").toString())) {
			List<TransactionTemplate> data = null;
			Object sdate = con.get("startTime");
			Object edate = con.get("endTime");
			
			String yday = null,cday= null;
			if(sdate != null && !sdate.toString().equals("")){
				yday = DateUtil.getYesterday(sdate.toString());
				con.put("stime", (yday+MsgCT.DAY_TIME_START));
			}
			if(edate != null && !edate.toString().equals("")){
				cday = edate.toString();
				String nday = TdExpBasicFunctions.GETDATE();
				if(Long.valueOf(nday).longValue() <= Long.valueOf(cday).longValue()){
					cday = nday;
				}
			}else{
				cday = DateUtil.getYesterday();
			}
			con.put("etime", cday+MsgCT.DAY_TIME_END);
			try {
				data = dao.selectHistoryReport(con);
			} catch (Exception e) {
				log.error("生成历史交易明细数据文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService.report(data, uid, "历史交易明细文件", "历史交易明细报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}else if("03".equals(con.get("state").toString())){
			List<TransTemplate> data = null;
			Object sdate = con.get("startTime");
			Object edate = con.get("endTime");
			Object sdate1 = sdate.toString().replaceAll("-", "");
			Object edate1 = edate.toString().replaceAll("-", "");
			Object bizType = con.get("bizType");
			String yday = null,cday= null;
			if(sdate1 != null && !sdate1.toString().equals("")){
				yday = DateUtil.getYesterday(sdate1.toString());
				con.put("stime", (yday+MsgCT.DAY_TIME_START));
			}
			
			if (bizType !=null && !bizType.toString().equals("")) {
				
				if (bizType.toString().equals("01")) {
					con.put("bizType", "05");
				}
			}
			if(edate1 != null && !edate1.toString().equals("")){
				cday = edate1.toString();
				String nday = TdExpBasicFunctions.GETDATE();
				if(Long.valueOf(nday).longValue() <= Long.valueOf(cday).longValue()){
					cday = nday;
				}
			}else{
				cday = DateUtil.getYesterday();
			}
			con.put("etime", cday+MsgCT.DAY_TIME_END);
			try {
				data = dao.selectTransReport(con);
			} catch (Exception e) {
				log.error("生成报表下载交易明细数据文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService01.report(data, uid, "报表下载交易明细文件", "报表下载交易明细报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}else if("04".equals(con.get("state").toString())){
			List<WithdrawTemplate> data = null;
			Object sdate = con.get("startTime");
			Object edate = con.get("endTime");
			Object sdate1 = sdate.toString().replaceAll("-", "");
			Object edate1 = edate.toString().replaceAll("-", "");
			
			String yday = null,cday= null;
			if(sdate1 != null && !sdate1.toString().equals("")){
				yday = DateUtil.getYesterday(sdate1.toString());
				con.put("stime", (yday+MsgCT.DAY_TIME_START));
			}
			if(edate1 != null && !edate1.toString().equals("")){
				cday = edate1.toString();
				String nday = TdExpBasicFunctions.GETDATE();
				if(Long.valueOf(nday).longValue() <= Long.valueOf(cday).longValue()){
					cday = nday;
				}
			}else{
				cday = DateUtil.getYesterday();
			}
			con.put("etime", cday+MsgCT.DAY_TIME_END);
			try {
				data = dao.selectWithdrawReport(con);
			} catch (Exception e) {
				log.error("生成报表下载提现明细数据文件出错！{}", e.getMessage());
				throw new Exception(e);
			}
			return reportService02.report(data, uid, "报表下载提现明细文件", "报表下载提现明细报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		}
		return 0;

	}


	@Override
	public List<TranSerialRecordInf> getListPage(TranSerialRecordInf entity)
			throws Exception {
		String yday = DateUtil.getYesterday();
		String cday = DateUtil.getDay();
		entity.setStime(yday+MsgCT.DAY_TIME_START);
		entity.setEtime(cday+MsgCT.DAY_TIME_END);
		return dao.selectList(entity);
	}



	@Override
	public Integer getCount(TranSerialRecordInf entity) throws Exception {
		String yday = DateUtil.getYesterday();
		String cday = DateUtil.getDay();
		entity.setStime(yday+MsgCT.DAY_TIME_START);
		entity.setEtime(cday+MsgCT.DAY_TIME_END);
		return dao.countEntity(entity);
	}



	@Override
	public TranSerialRecordInf getEntity(TranSerialRecordInf entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int addEntity(TranSerialRecordInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int modifyEntity(TranSerialRecordInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int removeEntity(TranSerialRecordInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public Map<String, Object> transactionCount(Map<String, Object> paramMap) {
		String yday = DateUtil.getYesterday();
		String cday = DateUtil.getDay();
		TranSerialRecordInf entity=new TranSerialRecordInf();
		entity.setStartTime(yday+MsgCT.DAY_TIME_START);
		entity.setEndTime(cday+MsgCT.DAY_TIME_END);
		paramMap.put("stime", entity.getStartTime());
		paramMap.put("etime", entity.getEndTime());
		return dao.transactionCount(paramMap);
	}
	
	@Override
	public List<TranSerialRecordInf> getHistorytranListPage(TranSerialRecordInf entity) throws Exception{
		log.info("查询参数:{}",entity.toString());
		/*
		String yday = null,cday= null;
		if(entity.getStime() != null){
			yday = DateUtil.getYesterday(entity.getStime());
			entity.setStime(yday+MsgCT.DAY_TIME_START);
		}
		if(entity.getEtime() != null){
			cday = entity.getEtime();
			String nday = TdExpBasicFunctions.GETDATE();
			if(Long.valueOf(nday).longValue() >= Long.valueOf(cday).longValue()){
				cday = nday;
			}

		}else{
			cday = DateUtil.getYesterday();
		}
		
		
	
		entity.setEtime(cday+MsgCT.DAY_TIME_END);
		*/
		
		return dao.getHistorytranListPage(entity);
		
	}

	@Override
	public Integer getHistorytranCount(TranSerialRecordInf entity) throws Exception{
		TranSerialRecordInf entity_tmp = entity;
		log.info("查询参数:{}",entity.toString());
		log.info("统计查询....{}",entity.toString());
		/*
		String yday = null,cday= null;
		if(entity_tmp.getStime() != null && !entity_tmp.getStime().equals("")){
			yday = DateUtil.getYesterday(entity_tmp.getStime());
			entity_tmp.setStime(yday+MsgCT.DAY_TIME_START);
		}
		if(entity_tmp.getEtime() != null && !entity_tmp.getEtime().equals("")){
			cday = entity_tmp.getEtime();
			String nday = TdExpBasicFunctions.GETDATE();
			if(Long.valueOf(nday).longValue() <= Long.valueOf(cday).longValue()){
				cday = nday;
			}
		}else{
			cday = DateUtil.getYesterday();
		}
		entity_tmp.setEtime(cday+MsgCT.DAY_TIME_END);
		*/
		
		try{	
			if(StringUtils.isNotEmpty(entity.getStime())){
				entity.setStime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getStime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setStartTime(null);
			
		}
		try{
			if(StringUtils.isNotEmpty(entity.getEtime())){
				entity.setEtime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getEtime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setEtime(null);
		}
		
		return dao.getHistorytranCount(entity_tmp);
	}
	@Override
	public Map<String, Object> historyTransactionCount(Map<String, Object> paramMap){
		String yday = DateUtil.getYesterday();
		TranSerialRecordInf entity=new TranSerialRecordInf();
		entity.setEndTime(yday+MsgCT.DAY_TIME_START);
		paramMap.put("endTime", entity.getEndTime());
		return dao.historyTransactionCount(paramMap);
	}


	@Override
	public List<TranSerialRecordInf> gettransListPage(TranSerialRecordInf entity)
			throws Exception {
		log.info("查询参数:{}",entity.toString());
		/*
		String yday = null,cday= null;
		if(entity.getStime() != null){
			yday = DateUtil.getYesterday(entity.getStime());
			entity.setStime(yday+MsgCT.DAY_TIME_START);
		}
		if(entity.getEtime() != null){
			cday = entity.getEtime();
			String nday = TdExpBasicFunctions.GETDATE();
			if(Long.valueOf(nday).longValue() >= Long.valueOf(cday).longValue()){
				cday = nday;
			}

		}else{
			cday = DateUtil.getYesterday();
		}
		
		
	
		entity.setEtime(cday+MsgCT.DAY_TIME_END);
		*/
		
		return dao.gettransListPage(entity);
	}


	@Override
	public int gettransCount(TranSerialRecordInf entity) throws Exception {
		TranSerialRecordInf entity_tmp = entity;
		log.info("查询参数:{}",entity.toString());
		log.info("统计查询....{}",entity.toString());
		/*
		String yday = null,cday= null;
		if(entity_tmp.getStime() != null && !entity_tmp.getStime().equals("")){
			yday = DateUtil.getYesterday(entity_tmp.getStime());
			entity_tmp.setStime(yday+MsgCT.DAY_TIME_START);
		}
		if(entity_tmp.getEtime() != null && !entity_tmp.getEtime().equals("")){
			cday = entity_tmp.getEtime();
			String nday = TdExpBasicFunctions.GETDATE();
			if(Long.valueOf(nday).longValue() <= Long.valueOf(cday).longValue()){
				cday = nday;
			}
		}else{
			cday = DateUtil.getYesterday();
		}
		entity_tmp.setEtime(cday+MsgCT.DAY_TIME_END);
		*/
		
		try{	
			if(StringUtils.isNotEmpty(entity.getStime())){
				entity.setStime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getStime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setStartTime(null);
			
		}
		try{
			if(StringUtils.isNotEmpty(entity.getEtime())){
				entity.setEtime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getEtime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setEtime(null);
		}
		
		return dao.gettransCount(entity_tmp);
	}


	@Override
	public Map<String, Object> TransCount(Map<String, Object> paramMap) {
		String yday = DateUtil.getYesterday();
		TranSerialRecordInf entity=new TranSerialRecordInf();
		entity.setEndTime(yday+MsgCT.DAY_TIME_START);
		paramMap.put("endTime", entity.getEndTime());
		return dao.TransCount(paramMap);
	}


	@Override
	public List<TranSerialRecordInf> getWithdrawListPage(
			TranSerialRecordInf entity) throws Exception {
		log.info("查询参数:{}",entity.toString());
		return dao.getWithdrawListPage(entity);
	}


	@Override
	public Integer getWithdrawCount(TranSerialRecordInf entity) throws Exception {
		TranSerialRecordInf entity_tmp = entity;
		log.info("查询参数:{}",entity.toString());
		log.info("统计查询....{}",entity.toString());
		try{	
			if(StringUtils.isNotEmpty(entity.getStime())){
				entity.setStime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getStime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setStartTime(null);
			
		}
		try{
			if(StringUtils.isNotEmpty(entity.getEtime())){
				entity.setEtime(DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(entity.getEtime(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			entity.setEtime(null);
		}
		
		return dao.getWithdrawCount(entity_tmp);
	}


	@Override
	public Map<String, Object> withdrawCount(Map<String, Object> paramMap) {
		String yday = DateUtil.getYesterday();
		TranSerialRecordInf entity=new TranSerialRecordInf();
		entity.setEndTime(yday+MsgCT.DAY_TIME_START);
		paramMap.put("endTime", entity.getEndTime());
		return dao.withdrawCount(paramMap);
	}


	}
