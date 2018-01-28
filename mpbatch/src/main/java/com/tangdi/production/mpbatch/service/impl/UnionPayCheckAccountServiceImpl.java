package com.tangdi.production.mpbatch.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.constants.MsgCT;
import com.tangdi.production.mpbatch.dao.CheckAccountDao;
import com.tangdi.production.mpbatch.service.UnionPayCheckAccountService;
import com.tangdi.production.mpbatch.util.Ftp;
import com.tangdi.production.mpbatch.util.ListResolve;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 与第三方或银行对账实现<br>
 * 
 * @author limiao
 */
@Service
public class UnionPayCheckAccountServiceImpl implements UnionPayCheckAccountService {
	private static Logger log = LoggerFactory.getLogger(UnionPayCheckAccountServiceImpl.class);

	@Autowired
	private CheckAccountDao checkAccountDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("华势  ---- 对账   begin");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actdate", TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "d", "1"));
		map.put("bankcode", "01");
		map.put("chktype", MsgCT.CHECKTYPE_01);
		
		//第一步：检查对账状态表，判断上一交易日是否对账
		if (checkAccountDao.checkStatus(map) > 0) {
			log.info("华势  ---- 对账  " + map.get("ACTDATE") + "已对账   退出");
			return;
		}
		log.info("华势  ---- 对账  " + map.get("ACTDATE") + " 记录");
		
		//第二步下载文件,检查文件是否存在
		String day = DateUtil.convertDateToString(new Date(), DateUtil.FORMAT_YYYYMMDD);//当天日期
		String nexDay = DateUtil.convertDateToString(getNextDay(new Date(),-1), DateUtil.FORMAT_YYYYMMDD);//当天日期的前一天
		String ftpFil = "STCHQ002"+ nexDay.substring(2, 8)+"01.list";
		//File file = getFileFromFtp("172.20.100.12 ", 21, "ftpadm", "ftpadm", "/D_duizhang_File/huashi/"+day+"/",ftpFil, "D:\\"+day,"");
		File file = getFileFromFtp("172.20.100.12 ", 21, "ftpadm", "ftpadm", "/D_duizhang_File/huashi/"+day+"/",ftpFil, "/home/mobpay/app/file/check/huashi/"+day,"");
		String filename = file.getCanonicalPath().toString();
		log.info(filename+"-----------文件");
		//第三步：插入对账状态表，上一交易日对账记录
		checkAccountDao.insertStatus(map);

		//第四步：清空第三方对账表
		log.info("THD ---- 对账 清空第三方对账表");
		checkAccountDao.emptyThd(map);
		
		log.info("华势  ---- 对账  清空 交易记录对账表");
		checkAccountDao.emptyPay(map);

		//第五步：插入前一个对账日的疑账数据
		log.info("华势  ---- 对账  导入 疑帐表中银行记录");
		checkAccountDao.insetDoubtBank(map);

		log.info("华势  ---- 对账  导入 疑帐表中系统记录");
		checkAccountDao.insetDoubtPay(map);

		 
		//第六步：插入当天对账文件中的数据，以及交易流水表中的数据
		log.info("华势  ---- 对账  导入 银行对账文件");
		// 解析 对账文件
		List<Map<String,Object>> list = ListResolve.resolve(filename);
		for(int i=0;i<list.size();i++){
			Map<String,Object> dataMap = list.get(i);
			dataMap.putAll(map);
			checkAccountDao.insetBank(dataMap);
		}
		/*for(Map<String,Object> dataMap:list){
			dataMap.putAll(map);
			checkAccountDao.insetBank(dataMap);
		}*/

		log.info("华势  ---- 对账  导入 交易流水");
		checkAccountDao.insetPay(map);//支付
		checkAccountDao.insetAnotherPay(map);//代付

		//数据准备完成后切换对账状态为对账中
		map.put("CHKPROSTS", MsgCT.CHKPROSTS_01);
		checkAccountDao.updateStatus(map);

		//第七步：开始对账
		log.info("华势  ---- 对账  开始");
		checkAccountDao.checkAccount(map);

		//第八步：插入疑账表
		log.info("华势  ---- 对账  插入疑帐表");
		checkAccountDao.insetBankDoubt(map);
		checkAccountDao.insetPayDoubt(map);
		
		//第九步：插入错账表
		log.info("华势  ---- 对账  插入错账表");
		checkAccountDao.insetError(map);

		//对账结束切换对账状态为对账完成
		log.info("华势  ---- 对账  结束");
		map.put("chkprosts", MsgCT.CHKPROSTS_02);
		checkAccountDao.updateStatus(map);

		log.info("华势  ---- 对账   end");

	}
	
	private File getFileFromFtp(String ipAdr, int ipPort, String usrNam, String usrPwd, String objDir, String objFil, String lclDir, String lclFil) throws Exception{
		
		int i = Ftp.ftpGet(ipAdr, ipPort, usrNam, usrPwd, objDir, objFil, lclDir, lclFil);
		if(i!=0){
			log.error("FTP下载文件失败");
		}
		File file = new File(lclDir+File.separator+objFil);
		if(!file.exists()){
			log.info("文件不存在");
		}
		return file;
	}
	
	/**
	 *获取当前日期的之前的日期
	 */
    public static Date getNextDay(Date date,int i) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, i);  
        date = calendar.getTime();  
        return date;  
    }  
}
