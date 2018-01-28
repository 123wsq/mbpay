package com.tangdi.production.mpomng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.bean.CustShareInf;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.AgentInfDao;
import com.tangdi.production.mpomng.dao.ProfitShareDao;
import com.tangdi.production.mpomng.report.ProfitSharingTemlpate;
import com.tangdi.production.mpomng.service.profitShareReportService;

@Service
public class profitShareReportServiceImpl implements profitShareReportService {
	private static final Logger log = LoggerFactory.getLogger(profitShareReportServiceImpl.class);
	@Autowired
	ProfitShareDao profitShareDao;
	@Autowired
	private FileReportService<ProfitSharingTemlpate> reportService;
	@Autowired
	private AgentInfDao agentInfDao;

	@SuppressWarnings("null")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		log.info("shar是什么：" + con.get("shar"));
		String shar=(String) con.get("shar");
		String shar1 = "";
		List<Map<String, Object>> agentIds = null;
		if(null == con.get("agentId") || "".equals(con.get("agentId"))){
			agentIds = profitShareDao.querySharingList();
		}
		else{
			Map<String, Object> agentId = new HashMap<String, Object>();
			agentId.put("agentId", con.get("agentId"));
			log.info("输入代理编号：" + con.get("agentId"));
			agentIds.add(agentId);
		}
			for(Map<String, Object> agentIdMap : agentIds){
				String agentId = agentIdMap.get("agentId") + "";
				log.info("AAA agentId:" + agentId);
				List<ProfitSharingTemlpate> T = new ArrayList<ProfitSharingTemlpate>();
				List<CustShareInf> apls = null;
				try {
					//		String date = TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(), "-", "M", "0").substring(0, 8);
					// 1.组装查询参数
					Map<String, Object> param = new HashMap<String, Object>();
					//		param.put("date", date);
					param.put("agentId", agentId);
					if("d".equals(shar)){
						shar1 = "日分润文件统计";
						apls = profitShareDao.selectCustShareList(param);
					}else{
						shar="月分润文件统计";
						apls = profitShareDao.selectCustMonthShareList(param);
					}
					for (CustShareInf apl : apls) {
						ProfitSharingTemlpate pst = new ProfitSharingTemlpate();
						pst.setCustId(apl.getCustId());
						pst.setCustName(apl.getCustName());
						pst.setPayAmt(apl.getPayAmt());
						pst.setPayFee(apl.getPayFee());
						pst.setRate(apl.getRate());
						pst.setSharAmt(apl.getSharAmt());
						pst.setSharDate(apl.getSharDate());
						pst.setAgentId(apl.getAgentId());
						pst.setAgentName(apl.getAgentName());
						pst.setAgentDgr(apl.getAgentDgr());
						pst.setFirstAgentId(apl.getFirstAgentId());
						pst.setFatherAgentId(apl.getFatherAgentId());
						pst.setPayType(apl.getPayType());
						T.add(pst);
					}
				}
				catch (Exception e) {
					log.error("生成提现订单文件出错！{}", e.getMessage());
					throw new Exception(e);
				}
				reportService.report(T, uid, "代理商" + shar1, null, CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL, null);
			}
		return 0;
		
//		String[] agentIds =  con.get("agentIds").toString().split(",");
//		log.info("agentIds:" + agentIds);
//		String shar=(String) con.get("shar");
//		for(String agentId : agentIds){
//			//	Map<String, Object> agentInf = agentInfDao.selectEntityByAgentId(agentId);
//			List<ProfitSharingTemlpate> T = new ArrayList<ProfitSharingTemlpate>();
//			List<CustShareInf> apls = null;
//			try {
//				String date = TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(), "-", "M", "0").substring(0, 8);
//				// 1.组装查询参数
//				Map<String, Object> param = new HashMap<String, Object>();
//				param.put("date", date);
//				param.put("agentId", agentId);
//				// 2.查询代理商日分润数据 或者 月分润数据
//				if(shar.equals("d")){
//					shar="日分润文件统计";
//					apls = profitShareDao.selectCustShareList(param);
//				}else{
//					shar="月分润文件统计";
//					apls = profitShareDao.selectCustMonthShareList(param);
//				}
//				for (CustShareInf apl : apls) {
//					ProfitSharingTemlpate pst = new ProfitSharingTemlpate();
//					pst.setCustId(apl.getCustId());
//					pst.setCustName(apl.getCustName());
//					pst.setPayAmt(apl.getPayAmt());
//					pst.setPayFee(apl.getPayFee());
//					pst.setRate(apl.getRate());
//					pst.setSharAmt(apl.getSharAmt());
//					pst.setSharDate(apl.getSharDate());
//					pst.setAgentId(apl.getAgentId());
//					pst.setAgentName(apl.getAgentName());
//					pst.setAgentDgr(apl.getAgentDgr());
//					pst.setFirstAgentId(apl.getFirstAgentId());
//					pst.setFatherAgentId(apl.getFatherAgentId());
//					T.add(pst);
//				}
//			} catch (Exception e) {
//				log.error("生成提现订单文件出错！{}", e.getMessage());
//				throw new Exception(e);
//			}
//			reportService.report(T, uid, "代理商" +shar, null, CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL, null);
//		}
	}
}
