package com.tangdi.production.mpomng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.SettleCountInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.SettlementDao;
import com.tangdi.production.mpomng.report.SettlementPayTemplate;
import com.tangdi.production.mpomng.service.CasInfReportService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

@Service
public class CasInfReportServiceImpl implements CasInfReportService {
	private static final Logger log = LoggerFactory.getLogger(CasInfReportServiceImpl.class);
	@Autowired
	SettlementDao settlementDao;

	@Autowired
	private FileReportService<SettlementPayTemplate> reportService;

	@Value("#{mpomngConfig}")
	private Properties prop;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		List<SettlementPayTemplate> T = new ArrayList<SettlementPayTemplate>();
		List<SettleCountInf> datas = null;
		String casOrdNo = con.get("casOrdNo").toString();
		String custId = con.get("custId").toString();
		String custName = con.get("custName").toString();
		String cardNo = con.get("cardNo").toString();
		String casType = con.get("casType").toString();
		String casStatus = con.get("casStatus").toString();
		//String startTime = con.get("startTime").toString();
		//String endTime = con.get("endTime").toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		try {
			paramMap.put("casOrdNo", casOrdNo);
			paramMap.put("custId", custId);
			paramMap.put("custName", custName);
			paramMap.put("cardNo", cardNo);
			paramMap.put("casType", casType);
			if (StringUtils.isEmpty(con.get("acType").toString())) {
				paramMap.put("acType", con.get("acType"));
			}
			if(StringUtils.isNotEmpty(casStatus)){
				paramMap.put("casStatus", casStatus);
			}
			Object sdate = con.get("startTime");
			Object edate = con.get("endTime");
			
			String yday = null,cday= null;
			if(sdate != null && !sdate.toString().equals("")){
				yday = DateUtil.getYesterday(sdate.toString());
				paramMap.put("startTime", (yday+MsgCT.DAY_TIME_START));
			}
			if(edate != null && !edate.toString().equals("")){
				cday = edate.toString();
				paramMap.put("endTime", cday+MsgCT.DAY_TIME_END);
			}
			
			/*	
			if (StringUtils.isNotEmpty(startTime)) {
				//paramMap.put("startTime", DateUtil.convertDateToString((Date) DateUtil.convertStringToDate(startTime, "yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD) + "000000");
			}
			if (StringUtils.isNotEmpty(endTime)) {
				//paramMap.put("endTime", DateUtil.convertDateToString((Date) DateUtil.convertStringToDate(endTime, "yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD) + "235959");
			}*/
			datas = settlementDao.selectCasPrd(paramMap);

			if (datas.size() <= 0) {
				log.error("没有要清算的订单信息。size={}", 0);
				throw new Exception("没有要清算的订单信息。");
			}
			for (SettleCountInf data : datas) {
				log.debug("data=[{}]", data);
				double netrecamt = Double.valueOf(data.getNetrecamt()) / 100.00; //
				// 应结算金额
				SettlementPayTemplate settle = new SettlementPayTemplate();
				settle.setAmt(String.valueOf(netrecamt));
				settle.setBankName(data.getSubBranch());
				settle.setCardName(data.getCardName());
				settle.setCardNo(data.getCardNo());
				settle.setCity(data.getCityName());
				settle.setCnapsCode(data.getCnapsCode());
				settle.setCustId(data.getCustId());
				settle.setCustName(data.getCustName());
				settle.setProvince(data.getProvinceName());
				settle.setType("对私");
				settle.setSucDate(data.getSucDate());

				T.add(settle);
			}
		} catch (Exception e) {
			log.error("生成提现订单文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		int result = reportService.report(T, uid, "提现订单数据文件", null, CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL, null);
		return result;

	}

	/**
	 * 更新状态为清算中
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyOrderStatus2(List<String> list, String casType) throws Exception {
		int rt = 0;
		for (String custId : list) {
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("custId", custId);
			String casDate = DateUtil.getYesterday().toString() + "23";
			con.put("casDate", casDate);
			con.put("casType", casType);
			rt = rt + settlementDao.updateStatusToProcessing(con);
		}
		return rt;
	}
}
