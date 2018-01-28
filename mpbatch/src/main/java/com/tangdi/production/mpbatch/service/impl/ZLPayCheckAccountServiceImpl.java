package com.tangdi.production.mpbatch.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbatch.constants.MsgCT;
import com.tangdi.production.mpbatch.dao.ZLCheckAccountDao;
import com.tangdi.production.mpbatch.service.ZLPayCheckAccountService;
import com.tangdi.production.mpbatch.util.Ftp;
import com.tangdi.production.mpbatch.util.ZLDZFileResolve;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 与中联支付对账实现<br>
 * 
 * @author limiao
 */
@Service
public class ZLPayCheckAccountServiceImpl implements ZLPayCheckAccountService {
	private static Logger log = LoggerFactory.getLogger(ZLPayCheckAccountServiceImpl.class);
	public static String dataFile = "zhl_cqyyyy-MM-dd.txt";
	public static String localDir = "/home/mobpay/app/file/download/data/zl_check_files";
	public static String objDir = "/D_duizhang_File/zlpay/upload/";
	public static String ftpIp = "172.20.100.12";
	public static String ftpUser = "ftpadm";
	public static String ftpPwd = "ftpadm";
	
	@Autowired
	private ZLCheckAccountDao checkAccountDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("ZLPay  ---- 对账   begin");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actdate", TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "d", "1"));
		map.put("bankcode", "ZLPay");
		map.put("chktype", MsgCT.CHECKTYPE_01); //消费
		
		//第一步：检查对账状态表，判断上一交易日是否对账
		if (checkAccountDao.checkStatus(map) > 0) {
			log.info("ZLPay  ---- 对账  " + map.get("actdate") + "已对账   退出");
			return;
		}
		log.info("ZLPay  ---- 对账  " + map.get("actdate") + " 记录");
		
		//第二步下载文件,检查文件是否存在2015-12-23
		String d = new SimpleDateFormat("yyyy-MM-dd").format(
				new SimpleDateFormat("yyyyMMdd").parse(
						map.get("actdate").toString()));
		String ftpFil = dataFile.replaceAll("yyyy-MM-dd", d);
		File file = getFileFromFtp(ftpIp, 21, ftpUser, ftpPwd, objDir+map.get("actdate"), ftpFil, localDir, "");
		String filename = file.getCanonicalPath().toString();
		log.info(filename+"-----------文件");
		//第三步：插入对账状态表，上一交易日对账记录
		checkAccountDao.insertStatus(map);

		//第四步：清空第三方对账表
		log.info("THD ---- 对账 清空第三方对账表");
		checkAccountDao.emptyThd(map);
		
		log.info("ZLPay  ---- 对账  清空 交易记录对账表");
		checkAccountDao.emptyPay(map);

		//第五步：插入前一个对账日的疑账数据
		log.info("ZLPay  ---- 对账  导入 疑帐表中银行记录");
		checkAccountDao.insetDoubtBank(map);

		log.info("ZLPay  ---- 对账  导入 疑帐表中系统记录");
		checkAccountDao.insetDoubtPay(map);

		 
		//第六步：插入当天对账文件中的数据，以及交易流水表中的数据
		log.info("ZLPay  ---- 对账  导入 银行对账文件");
		// 解析 对账文件
		List<Map<String,Object>> list = ZLDZFileResolve.resolve(filename);
		for(Map<String,Object> dataMap:list){
			dataMap.putAll(map);
			checkAccountDao.insetBank(dataMap);
		}

		log.info("ZLPay  ---- 对账  导入 交易流水");
		checkAccountDao.insetPay(map);//支付

		//数据准备完成后切换对账状态为对账中
		map.put("CHKPROSTS", MsgCT.CHKPROSTS_01);
		checkAccountDao.updateStatus(map);

		//第七步：开始对账
		log.info("ZLPay  ---- 对账  开始");
		checkAccountDao.checkAccount(map);

		//第八步：插入疑账表
		log.info("ZLPay  ---- 对账  插入疑帐表");
		checkAccountDao.insetBankDoubt(map);
		checkAccountDao.insetPayDoubt(map);
		
		//第九步：插入错账表
		log.info("ZLPay  ---- 对账  插入错账表");
		checkAccountDao.insetError(map);

		//对账结束切换对账状态为对账完成
		log.info("ZLPay  ---- 对账  结束");
		map.put("chkprosts", MsgCT.CHKPROSTS_02);
		checkAccountDao.updateStatus(map);

		log.info("ZLPay  ---- 对账   end");

	}
	
	private File getFileFromFtp(String ipAdr, int ipPort, String usrNam, String usrPwd, String objDir, String objFil, String lclDir, String lclFil) throws Exception{
		File file = null;
		File lclFileD = new File(lclDir);
		if(!lclFileD.exists()){
			lclFileD.mkdir();
		}
		int i = Ftp.ftpGet(ipAdr, ipPort, usrNam, usrPwd, objDir, objFil, lclDir, lclFil);
		if(i!=0){
			log.error("FTP下载文件失败");
			throw new Exception("FTP下载文件失败");
		}
		file = new File(lclDir+File.separator+objFil);
		if(!file.exists()){
			log.info("文件不存在");
			throw new Exception("FTP下载文件不存在");
		}
		
		return file;
	}
	
}
