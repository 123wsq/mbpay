package com.tangdi.production.mpbatch.service.impl;

import java.io.File;
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

import com.tangdi.production.mpbase.lib.ReportExportLib;
import com.tangdi.production.mpbatch.dao.GenOrgSettleReportDao;
import com.tangdi.production.mpbatch.service.GenOrgSettleReportService;
import com.tangdi.production.mpbatch.util.Ftp;
import com.tangdi.production.mpomng.bean.OrgSettleCountInf;
import com.tangdi.production.mpomng.dao.AgentInfDao;
import com.tangdi.production.mpomng.report.OrgSettleReportTemplate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 生成机构对账文件实现<br>
 * 
 * @author chenlibo
 */
@Service
public class GenOrgSettleReportServiceImpl implements GenOrgSettleReportService {
	private static Logger log = LoggerFactory.getLogger(GenOrgSettleReportServiceImpl.class);
	public static String dataFile = "MST[AgentId][yyMMdd]01.txt";
	public static String localDir = "/home/mobpay/app/file/download/data/";
	public static String objDir = "/D_duizhang_File/rongbao/";
	public static String ftpIp = "172.20.100.12";
	public static String ftpUser = "ftpadm";
	public static String ftpPwd = "ftpadm";
	
	@Autowired
	private GenOrgSettleReportDao genOrgSettleReportDao;
	
	@Autowired
	private AgentInfDao agentInfDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("生成SettleReport   begin");
		Map<String, Object> con = new HashMap<String, Object>();
		List<OrgSettleReportTemplate> T = new ArrayList<OrgSettleReportTemplate>();
		List<OrgSettleCountInf> datas = null;
		String txnDate = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "d", "1");
		int num = 0;
		int sum = 0;
		String filed1 = "2"; //代理机构
		String wkdate = TdExpBasicFunctions.GETDATE();
		
		try {
			con.put("txnDate", txnDate);
			List<Map<String, Object>> agentList =  agentInfDao.selectListByFiled1(filed1);
			if(agentList == null || agentList.size() <= 0 ){
				log.error("没有对账的代理机构。size={}", 0);
				throw new Exception("没有对账的代理机构。");
			}
			
			for(Map<String, Object> agent: agentList){
				con.put("agentId", agent.get("agentId"));
				datas = genOrgSettleReportDao.selectOrgSettleCount(con);
				num = genOrgSettleReportDao.selectOrgSettleCountNum(con);
				sum = genOrgSettleReportDao.selectOrgSettleCountSum(con);
				
				if (datas.size() <= 0) {
					log.info("agentId={}没有要清算的订单信息。size={}", agent.get("agentId"), 0);
				}
				T.clear();
				for (OrgSettleCountInf data : datas) {
					log.debug("data=[{}]", data);
	
					OrgSettleReportTemplate settle = new OrgSettleReportTemplate();
					settle.setAgentId(data.getAgentId());
					settle.setCustId(data.getCustId());
					settle.setTerminalNo(data.getTerminalNo());
					settle.setPayCardno(data.getPayCardno());
					settle.setTxAmt(data.getTxAmt());
					settle.setNetrecamt(data.getNetrecamt());
					settle.setFee(data.getFee());
					settle.setPrdordNo(data.getPrdordNo());
					settle.setTxnDate(data.getTxnDate());
					settle.setTxnTime(data.getTxnTime());
					settle.setPaymentId(data.getPaymentId());
					settle.setTxnType(data.getTxnType());
	
					T.add(settle);
				}
				
				ReportExportLib<OrgSettleReportTemplate> report = new ReportExportLib<OrgSettleReportTemplate>();
				StringBuffer headSb = new StringBuffer();
				headSb.append(num).append("|").append(sum).append("|");
				String wkfile = dataFile.replace("[AgentId]", agent.get("agentId").toString())
						.replace("[yyMMdd]", wkdate.substring(2,8));
				long size = report.createFormatFile(T, headSb.toString(), "", localDir, 
						wkfile, "UTF-8", "|", true);
				log.debug(agent.get("agentId") + "对账文件大小:[{}]",size);
				
				if(new File(localDir + wkfile).exists()){
					log.info("生成SettleReport  ---- ftp上传文件 start: " + wkfile);
					putFile(ftpIp, 21, ftpUser, ftpPwd, 
							objDir+wkdate+"/"+agent.get("agentId"),
							wkfile, localDir, wkfile);
					log.info("生成SettleReport  ---- ftp上传文件 end");
				}else{
					log.error("生成机构对账文件不成功!");
				}
			}
		} catch (Exception e) {
			log.error("生成机构对账文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		
		log.info("生成SettleReport  ---- 对账   end");
	}
	
	public static boolean putFile(String ipAdr, int ipPort, String usrNam, String usrPwd, String objDir, String objFil, String lclDir, String lclFil) throws Exception{
		int i = Ftp.ftpPut(ipAdr, ipPort, usrNam, usrPwd, objDir, objFil, lclDir, lclFil);
		if(i!=0){
			log.error("上传机构对账文件失败");
			throw new Exception("上传机构对账文件失败");
		}
		
		return true;
	}
	public static void main(String[] args){
		try {
			putFile("172.20.100.12", 21, "ftpadm", "ftpadm", "////D_duizhang_File////rongbao/ 20160123 /8151102488///",
					"xxx.txt", "C:\\", "xxx.txt");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
