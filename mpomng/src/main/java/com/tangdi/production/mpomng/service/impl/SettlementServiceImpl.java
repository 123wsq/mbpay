package com.tangdi.production.mpomng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.SettleCountInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.SettlementDao;
import com.tangdi.production.mpomng.report.SettlementPayTemplate;
import com.tangdi.production.mpomng.service.SettlementService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 提现订单
 * 
 * @author zhengqiang
 *
 */

@Service
public class SettlementServiceImpl implements SettlementService {
	private static final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);
	@Autowired
	SettlementDao dao;

	@Autowired
	private FileReportService<SettlementPayTemplate> reportService;

	@Value("#{mpomngConfig}")
	private Properties prop;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {

		List<SettlementPayTemplate> T = new ArrayList<SettlementPayTemplate>();
		List<SettlementPayTemplate> T0 = new ArrayList<SettlementPayTemplate>();
		List<SettlementPayTemplate> T1 = new ArrayList<SettlementPayTemplate>();
		Map<String, List<SettlementPayTemplate>> map = new HashMap<String, List<SettlementPayTemplate>>();
		List<SettleCountInf> datas = null;
		List<SettleCountInf> t0Datas = null;
		List<SettleCountInf> t1Datas = null;
		List<String> custIds = new ArrayList<String>();
		String sucDate = DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss");
		try {
			if(null != con.get("startTime") && !"".equals(con.get("startTime"))){
				con.put("startTime", con.get("startTime").toString().replace("-", "")+"000000");
			}
			if(null != con.get("endTime") && !"".equals(con.get("endTime"))){
				con.put("endTime", con.get("endTime").toString().replace("-", "")+"235959");
			}
			datas = dao.selectSettleCount(con);
			// T+0提现文件
			con.put("casType", "00");
			t0Datas = dao.selectT0T1Count(con);
			// T+1提现文件
			con.put("casType", "01");
			t1Datas = dao.selectT0T1Count(con);
			//String casDate = DateUtil.getYesterday().toString() + "23";
			// 总清算文件
			//con.put("casDate", casDate);
			//dao.modifyBySettle(con);
			if (datas.size() <= 0) {
				log.error("没有要清算的订单信息。size={}", 0);
				throw new Exception("没有要清算的订单信息。");
			}
			// 生成总清算文件
			for (SettleCountInf data : datas) {
				log.debug("data=[{}]", data);

				double netrecamt = Double.valueOf(data.getNetrecamt()) / 100.00; // 应结算金额
				SettlementPayTemplate settle = new SettlementPayTemplate();
				settle.setAmt(String.valueOf(netrecamt));
				settle.setBankName(data.getIssnam());
				settle.setSubBranch(data.getSubBranch());
				settle.setCardName(data.getCardName());
				settle.setCardNo(data.getCardNo());
				settle.setCity(data.getCityName());
				settle.setCnapsCode(data.getCnapsCode());
				settle.setCustId(data.getCustId());
				settle.setCustName(data.getCustName());
				settle.setProvince(data.getProvinceName());
				settle.setType("对私");
				settle.setAgentId(data.getAgentId());
				settle.setAgentName(data.getAgentName());
				settle.setSucDate(sucDate);

				T.add(settle);

				custIds.add(data.getCustId());
			}

			// 生成T0清算文件
			for (SettleCountInf data : t0Datas) {
				log.debug("data=[{}]", data);

				double netrecamt = Double.valueOf(data.getNetrecamt()) / 100.00; // 应结算金额
				SettlementPayTemplate settle = new SettlementPayTemplate();
				settle.setAmt(String.valueOf(netrecamt));
				settle.setBankName(data.getIssnam());
				settle.setSubBranch(data.getSubBranch());
				settle.setCardName(data.getCardName());
				settle.setCardNo(data.getCardNo());
				settle.setCity(data.getCityName());
				settle.setCnapsCode(data.getCnapsCode());
				settle.setCustId(data.getCustId());
				settle.setCustName(data.getCustName());
				settle.setProvince(data.getProvinceName());
				settle.setType("对私");
				settle.setAgentId(data.getAgentId());
				settle.setAgentName(data.getAgentName());
				settle.setSucDate(sucDate);

				T0.add(settle);
			}

			// 生成T1清算文件
			for (SettleCountInf data : t1Datas) {
				log.debug("data=[{}]", data);

				double netrecamt = Double.valueOf(data.getNetrecamt()) / 100.00; // 应结算金额
				SettlementPayTemplate settle = new SettlementPayTemplate();
				settle.setAmt(String.valueOf(netrecamt));
				settle.setBankName(data.getIssnam());
				settle.setSubBranch(data.getSubBranch());
				settle.setCardName(data.getCardName());
				settle.setCardNo(data.getCardNo());
				settle.setCity(data.getCityName());
				settle.setCnapsCode(data.getCnapsCode());
				settle.setCustId(data.getCustId());
				settle.setCustName(data.getCustName());
				settle.setProvince(data.getProvinceName());
				settle.setType("对私");
				settle.setAgentId(data.getAgentId());
				settle.setAgentName(data.getAgentName());
				settle.setSucDate(sucDate);

				T1.add(settle);
			}

			// 更新 成 清算完成
			modifyOrderStatus2(custIds, con);
		} catch (Exception e) {
			log.error("生成提现订单文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		map.put("SttlementAccount", T);
		map.put("T+0Account", T0);
		map.put("T+1Account", T1);
//		int result = reportService.report(T, uid, null, null, CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL, null);
		int size = reportService.reportZIP(uid, "清算统计文件", "清算统计文件", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null,map);
		
		log.debug("文件大小:[{}]",size);
		
		return size;
	}

	/**
	 * 更新状态为清算完成
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyOrderStatus2(List<String> list, Map<String, Object> map) throws Exception {
		int rt = 0;
		for (String custId : list) {
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("custId", custId);
//			String casDate = DateUtil.getYesterday().toString() + "23";
//			con.put("casDate", casDate);
			String sucDate = DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss");
			con.put("sucDate", sucDate);
			if(null != map.get("startTime") && !"".equals(map.get("startTime"))){
				con.put("startTime", map.get("startTime").toString().replace("-", "")+"000000");
			}
			if(null != map.get("endTime") && !"".equals(map.get("endTime"))){
				con.put("endTime", map.get("endTime").toString().replace("-", "")+"235959");
			}
			rt = rt + dao.updateStatusToProcessing(con);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyOrderStatus(Map<String, Object> paramMap) throws Exception {
		return dao.modifyOrderStatus(paramMap);
	}

	@Override
	public int modifyByCasOrdNo(Map<String, Object> paramMap) throws Exception {
		return dao.modifyByCasOrdNo(paramMap);
	}

}
