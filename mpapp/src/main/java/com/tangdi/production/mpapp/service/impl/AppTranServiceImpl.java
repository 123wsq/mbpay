package com.tangdi.production.mpapp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.AgentService;
import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpapp.service.CasService;
import com.tangdi.production.mpapp.service.CustAccountService;
import com.tangdi.production.mpapp.service.CustBankInfService;
import com.tangdi.production.mpapp.service.ESignService;
import com.tangdi.production.mpapp.service.HolidayRuleService;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.PayTranDoneService;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpapp.service.PlatformParameterService;
import com.tangdi.production.mpapp.service.ProfitSharingService;
import com.tangdi.production.mpapp.service.RatesInfService;
import com.tangdi.production.mpapp.service.RouteService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpapp.service.TranSerialRecordService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mprcs.service.RcsTransactionService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * <b>APP通道交易接口[银行签到、终端签到、余额查询、消费] 、 电子签名接口实现</b></br>
 * 
 * @author zhengqiang 2015/3/20
 *
 */
@Service
public class AppTranServiceImpl implements AppTranService {
	private static Logger log = LoggerFactory.getLogger(AppTranServiceImpl.class);

	@Value("#{mpappConfig}")
	private Properties prop;
	
	@Autowired
	private GetSeqNoService seqNoService;
	
	/**
	 * 前置交易接口
	 */
	@Autowired
	private TranFrontService tranFrontservice;
	/**
	 * 支付订单接口
	 */
	@Autowired
	private TermPayService termPayService;
	/**
	 * 终端服务接口
	 */
	@Autowired
	private TerminalService terminalService;
	/**
	 * 商品订单接口
	 */
	@Autowired
	private TermPrdService termPrdService;
	/**
	 * 银行卡交易接口
	 */
	@Autowired
	private CustAccountService custAccountService;
	/**
	 * 银行卡交易接口
	 */
	@Autowired
	private CustBankInfService custBankInfService;
	/**
	 * 代理商接口
	 */
	@Autowired
	private AgentService agentService;
	/**
	 * 支付流水接口
	 */
	@Autowired
	private PaymentJournalService paymentJournalService;
	/**
	 * 交易信息查询
	 */
	@Autowired
	private TranSerialRecordService tranSerialRecordService;
	/**
	 * 	商户接口
	 */
	@Autowired
	private MerchantService merchantService;
	/**
	 * 提现接口
	 */
	@Autowired
	private CasService casService;
	/**
	 * 提现费率
	 */
	@Autowired
	private PlatformParameterService platformParameterService;
	/**
	 * 电子签名接口
	 */
	@Autowired
	private ESignService esignService;
	/**
	 * 卡BIN接口
	 */
	@Autowired
	private UnionfitService unionfitService;
	/**
	 * 注入路由接口
	 */
	@Autowired
	private RouteService routeService;
	/**
	 * 分润计算业务接口
	 */
	@Autowired
	private ProfitSharingService profitSharingService;
	
	/**
	 * 商户限额业务接口
	 */
	@Autowired
	private RcsTransactionService rcsTransactionService;
	
	/**
	 * 支付完成处理接口
	 */
	@Autowired
	private PayTranDoneService payTranDoneService;
	
	
	/**
	 * 判断是否为节假日接口
	 */
	@Autowired
	private HolidayRuleService holidayRuleService;
	
	/**
	 * 保存图片接口
	 * */
	@Autowired
	private FileUploadService fileUploadService;
	
	/**
	 * 费率查询接口
	 * */
	@Autowired
	private RatesInfService ratesInfService;
	
	/**
	 * 支付
	 */
	@Override 
	public Map<String, Object> payment(Map<String, Object> param) throws TranException {
		log.info("进入支付接口.参数:[{}]", param);
		
		Double payAmt_ = null;
		try{
			payAmt_ = Double.valueOf(param.get("payAmt").toString());
		}catch(Exception e){
			throw new TranException(ExcepCode.EX000800);
		}
		//检查系统支持最大操作金额
		if(payAmt_.doubleValue() > MsgST.AMT_HIG){
			throw new TranException(ExcepCode.EX000802);
		}
		
		//消费时间段检查
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("paraCode", "PAY_START_TIME");
		Map<String,Object> startTimeMap = platformParameterService.getCasTime(queryMap);
		int startTime = Integer.valueOf(startTimeMap.get("paraVal").toString());
		log.info("起始消费时间=[{}]",startTime);
		queryMap.put("paraCode", "PAY_END_TIME");
		Map<String,Object> endTimeMap = platformParameterService.getCasTime(queryMap);
		int endTime = Integer.valueOf(endTimeMap.get("paraVal").toString());
		log.info("截至消费时间=[{}]",endTime);
		
		int ctime = Integer.valueOf(TdExpBasicFunctions.GETDATETIME("HHMISS").substring(0, 2));
		log.info("当前时间=[{}]",ctime);
		if(startTime > ctime || endTime <= ctime){
			log.info("消费时间段检查不通过。");
			throw new TranException(ExcepCode.EX000232,"消费时间段检查不通过。",
					String.valueOf(startTime),String.valueOf(endTime));
		}else{
			log.info("正常消费时间段。");
		}
		
		log.info("1.参数检查（APP）.");
		/**
		 * 必填参数效验 custMobile 手机号 termNo 终端号 pinblk 密码密文 track 磁道信息 mediaType
		 * 介质类型 rateType 费率类型
		 */
		ParamValidate.doing(param, "prdordNo","custMobile", "termNo", "termType", "pinblk", "track", "mediaType", "rateType");
		if (MsgST.MEDIATYPE_IC.equals(String.valueOf(param.get("mediaType")))) {
			// IC卡试效验IC卡数据
			/**
			 * period 有效期 icdata IC卡数据域 seqnum 序列号
			 * 
			 */
			ParamValidate.doing(param, "period", "icdata", "crdnum");
		}
		
		param.put("rate", param.get("rateType"));
		
		log.info("x.查询收单接入开关");
		Map<String, Object> tunnel = new HashMap<String, Object>();
		tunnel.put("paraCode", "PAY_STATUS_ TUNNEL");
		Map<String,Object> tunnelMap = platformParameterService.getTunnel(tunnel);
		String payTunnelVal = String.valueOf(tunnelMap.get("paraVal"));
		log.info("查询通道选择参数结束（1、收单 2、华势）:"+tunnelMap.get("paraVal"));


		log.info("2.终端检查（带手续费、密钥[刷卡头|APP]）.");
		Map<String, Object> termMap = terminalService.check(param);
		boolean termstatus = false;
		param.putAll(termMap);
		
		log.info("3.商户检查[检查商户状态].");
		Map<String , Object> cust=merchantService.getMerchant(param);
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		
		log.info("4.代理商检查.");
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);

		log.info("5.订单检查（商品）.");
		Map<String, Object> prdordMap = termPrdService.check(param);
		param.put("prdordMap", prdordMap);
		
		log.info("6.解密磁道信息（终端实现类）.");
		String[] track = tranFrontservice.decryptTrack(param);
	
		param.put("track2", track[0]);
		param.put("track3", track[1]);
		param.put("icdata", track[2]);
		param.put("cardNo", track[3]);

		log .info("7.卡BIN信息查询.");
		UnionfitInf bin =null;
		String cardNo=track[3];
		try {
			bin = unionfitService.getCardInf(cardNo);
			log.info("卡BIN信息：{}",bin.debug());
			param.putAll(bin.toMap());
		} catch (Exception e) {
			log.warn("卡BIN信息获取失败!",e.getMessage());
			throw new TranException(ExcepCode.EX001002);
		}
		log.info("7.1 检查磁道卡号与扫描卡号是否一致");
		if(!param.get("scancardnum").equals(cardNo)){
			throw new TranException(ExcepCode.EX010001);
		}
		log.info("8.限额检查（商户）");
		Map<String, Object> rcsMap = new HashMap<String, Object>();
		rcsMap.put("custId", cust.get("custId"));
		rcsMap.put("merclass", cust.get("merclass"));
		rcsMap.put("agentId", agentMap.get("agentId"));
		rcsMap.put("termNo", termMap.get("termNo"));
		rcsMap.put("bizType",prdordMap.get("prdordtype"));
		rcsMap.put("payType", param.get("payType"));
		rcsMap.put("cardNo", cardNo);
		rcsMap.put("payAmt", param.get("payAmt"));
		rcsTransactionService.checkTxnLimit(rcsMap);
		
		log.info("9.计算手续费 （费率计算）.");
		Map<String, Object> feeMap = terminalService.calcRateFee(param, termMap);
		param.put("feeMap", feeMap);
		
		if("2".equals(payTunnelVal)){
			log.info("10.查询路由（合作机构、密钥信息）.");
			param.put("agentId", agentMap.get("agentId"));
			param.put("rtrType", MsgST.RTR_TYPE_CONSUME); 
			routeService.route(param);
		}else{
			log.info("10.走收单无需查询路由。");
		}
		
		log.info("11.创建支付订单.");
		param.put("payCardno", cardNo);
		param.put("issnam", bin.getIssnam());
		param.put("crdnam", bin.getCrdnam());
		param.put("payChannel", param.get("rtrsvr"));
		String payNo = termPayService.createPay(param);
		
		log.info("12.更新商品订单.");
		param.put("payNo", payNo); //加入支付订单号
		param.put("ordstatus", MsgST.ORDSTATUS_PROCESSING);
		termPrdService.modifyPrd(param);
		
		log.info("13.创建交易流水.");
		String paymentId=paymentJournalService.addPaymentJournal(param);
		param.put("paymentId", paymentId);//交易流水
		param.put("txnType", "01");       //交易类型 01消费  02 撤销
		
		log.info("14.外发 >>> 调用渠道模块支付接口.");
		if("1".equals(payTunnelVal)){
			//不确定11 12 13步是否需要orgNo
			param.put("rtrsvr", "STHDSD");
			param.put("rtrcod", "9992");
			param.put("orgNo","10000");
		}
		String ordstatus =  MsgST.ORDSTATUS_SUCCESS;  //订单状态 初始状态为交易成功
		String paystatus =  MsgST.PAYSTATUS_SUCCESS;  //支付状态
		String txnstatus =  MsgST.TXNSTATUS_S;
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		try{
			param.put("cardNo", cardNo);
			
			//heguo add 20160503 根据代理商编号 查询出三级代理商（一级代理商时 另外两个为15个F；二级代理商时，最后一个为15个F）
			log.info("New >>> 查询商户所属的三层代理商开始.");
			String agentId_three= agentService.getThreeAgentId(param);
			param.put("agentId_three", agentId_three);
			log.info("New >>> 查询商户所属的三层代理商结束.agentId_three："+agentId_three);
			
			tranFrontservice.pay(param);
			//设置银行交易状态
			bankstatus = String.valueOf(param.get("TCPSCod"));
		}catch(TranException e){
			exp = e.getCode();
			if(exp.equals(ExcepCode.EX100302)          //接收第三方系统数据超时 
					|| exp.equals(ExcepCode.EX200359)  //第三方系统发送超时
					|| exp.equals(ExcepCode.EX200367)){//发卡方超时
				ordstatus = MsgST.ORDSTATUS_TIMEOUT;
				paystatus = MsgST.PAYSTATUS_TIMEOUT;
				txnstatus = MsgST.TXNSTATUS_T;
			}else{
				ordstatus = MsgST.ORDSTATUS_FAIL;
				paystatus = MsgST.PAYSTATUS_FAIL;
				txnstatus = MsgST.TXNSTATUS_F;
			}
			bankstatus = param.get("TCPSCod") == null?"":String.valueOf(param.get("TCPSCod"));
			log.info("异常处理. exp=[{}] bankstatus=[{}] ordstatus=[{}] paystatus=[{}] txnstatus=[{}]",
					exp,bankstatus,ordstatus,paystatus,txnstatus);
		}
		
		log.info("支付完成.后续处理中...");
		Map<String,Object> udata = new HashMap<String,Object>();
		udata.put("paystatus", paystatus);
		udata.put("bankstatus", bankstatus);
		udata.put("cardNo", cardNo);
		udata.put("dcflag", bin.getDcflag());
		udata.put("payNo", payNo);
		udata.put("fee", feeMap.get("fee"));
		udata.put("rate", feeMap.get("rate"));
		udata.put("ordstatus", ordstatus);
		udata.put("paystatus", paystatus);
		udata.put("termstatus", termstatus);
		udata.put("exp", exp);
		udata.put("ctype", termMap.get("ctype"));
		udata.put("cause", param.get("cause"));
		udata.put("txnstatus", txnstatus);
		udata.put("custName", cust.get("custName"));
		
		Map<String,Map<String,Object>> resultMap = new HashMap<String,Map<String,Object>>();
		resultMap.put("param", param);
		resultMap.put("prdordMap", prdordMap);
		resultMap.put("termMap", termMap);
		resultMap.put("udata", udata);
		try{
			payTranDoneService.handler(resultMap);
		}catch(TranException e){
			//数据更新失败,调用冲正接口
			if(param.get("redo").toString().equals("0")){
				param.put("RedoTCPSCod", "98");
				tranFrontservice.redo(param);
			}
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}

		if(!exp.equals("000000")){
			throw new TranException(exp);
		}
		log.info("支付完成.后续处理完成...");
		return null;
	}
	

	@Override
	public Map<String, Object> banksign(Map<String, Object> param) throws TranException {
		log.info("进入签到接口.参数:[{}]", param);
		/**
		 * 效验参数
		 */
		ParamValidate.doing(param, "termType");

		log.info("调用渠道模块签到接口...");
		return tranFrontservice.banksign(param);
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param) throws TranException {
		log.info("进入余额查询接口.参数:[{}]", param);
		log.info("1.必须参数校验.");
		/**
		 * 必填参数效验 custMobile 手机号 termNo 终端号 pinblk 密码密文 track 磁道信息 mediaType
		 * 介质类型 rateType 费率类型
		 */
		ParamValidate.doing(param, "custMobile", "termNo", "termType", "pinblk", "track", "mediaType");
		if (MsgST.MEDIATYPE_IC.equals(String.valueOf(param.get("mediaType")))) {
			// IC卡试效验IC卡数据
			/**
			 * period 有效期 icdata IC卡数据域 seqnum 序列号
			 * 
			 */
			ParamValidate.doing(param, "period", "icdata", "crdnum");
		}
		param.put("rateType", MsgST.RATE_TYPE_CH00);

		log.info("2.商户检查[检查商户状态].");
		Map<String , Object> cust=merchantService.getMerchant(param);
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		log.info("3.终端检查（带手续费、密钥[刷卡头|APP]）.");
		Map<String, Object> termMap = terminalService.check(param);
		param.putAll(termMap);
		
		log.info("4.代理商检查.");
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);
		
		log.info("5.解密磁道信息（终端实现类）.");
		String[] track = tranFrontservice.decryptTrack(param);
		param.put("track2", track[0]);
		param.put("track3", track[1]);
		param.put("icdata", track[2]);
		param.put("cardNo", track[3]);
		
		
		log.info("6.查询路由（合作机构、密钥信息）.");
		//余额查询
		param.put("rtrType", MsgST.RTR_TYPE_QUERY); 
		routeService.route(param);

		log.info("7.外发 >>>  调用渠道模块余额查询接口...");
		return tranFrontservice.query(param);
	}

	@Override
	public Map<String, Object> termsign(Map<String, Object> param) throws TranException {
		log.info("进入设备签到接口.参数:[{}]", param);

		/**
		 * 效验参数
		 */
		ParamValidate.doing(param, "termNo", "termType");
		
		//48864EA979EE9337DA05B7A979CBD9A1
		//效验终端并获取主密钥信息
		Map<String,Object> termInfo = terminalService.check(param);
		String mk = String.valueOf(termInfo.get("terminalLMK"));
		log.info("主密钥:[{}]",mk);
		
		param.put("terminalLMK", mk);
		param.put("terminalCom", termInfo.get("terminalCom"));
		
		//调用通信渠道模块接口
		Map<String, Object> keymap = tranFrontservice.termsign(param);

		// 保存签到密钥
		Map<String, Object> keydata = new HashMap<String,Object>();
		keydata.put("terminalId",    termInfo.get("terminalId"));
		keydata.put("terminalPIK",   keymap.get("LPINKEY"));
		keydata.put("terminalPIKCV", keymap.get("ZPINVALUE"));
		keydata.put("terminalMAK",   keymap.get("LMAKKEY"));
		keydata.put("terminalMAKCV", keymap.get("ZMAKVALUE"));
		
		terminalService.modiftyKey(keydata);
		//组返回值
		Map<String,Object> termKey = new HashMap<String,Object>();
		termKey.put("zpinkey", keymap.get("ZPINKEY").toString().replace("X", ""));
		termKey.put("zpincv",  keymap.get("ZPINVALUE"));
		termKey.put("zmakkey", keymap.get("ZMAKKEY"));
		termKey.put("zmakcv",  keymap.get("ZMAKVALUE"));
		return termKey;
	}

	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void txTran(Map<String, Object> param) throws TranException {
		log.info("进入提现交易接口.参数:[{}]", param);
		ParamValidate.doing(param, "txamt","casType");
		//处理前台带小数点金额。
		
		Double txamt = null;
		try{
			txamt = Double.valueOf(param.get("txamt").toString());
		}catch(Exception e){
			throw new TranException(ExcepCode.EX000800);
		}
		//检查系统支持最大操作金额
		if(txamt.doubleValue() > MsgST.AMT_HIG){
			throw new TranException(ExcepCode.EX000802);
		}
		
		param.put("txamt", txamt.intValue());
		//是否非工作日
		//TODO
		
		
		//提现时间段检查
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("paraCode", "T0_START_TIME");
		Map<String,Object> startTimeMap = platformParameterService.getCasTime(queryMap);
		int startTime = Integer.valueOf(startTimeMap.get("paraVal").toString());
		log.info("起始提现时间=[{}]",startTime);
		queryMap.put("paraCode", "T0_END_TIME");
		Map<String,Object> endTimeMap = platformParameterService.getCasTime(queryMap);
		int endTime = Integer.valueOf(endTimeMap.get("paraVal").toString());
		log.info("截至提现时间=[{}]",endTime);
		
		int ctime = Integer.valueOf(TdExpBasicFunctions.GETDATETIME("HHMISS").substring(0, 2));
		log.info("当前时间=[{}]",ctime);
		if(startTime > ctime || endTime <= ctime){
			log.info("提现时间段检查不通过。");
			throw new TranException(ExcepCode.EX000231,"提现时间段检查不通过。",
					String.valueOf(startTime),String.valueOf(endTime));
		}else{
			log.info("正常提现时间段。");
		}
		
		Map<String , Object> cust=merchantService.getMerchant(param);
		//检查商户状态 custStatus
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		String cpwd = param.get("payPwd").toString();
		String spwd = cust.get("payPwd").toString();
		//检查支付密码
		log.debug("校验支付密码[cpwd={},spwd={}]",cpwd,spwd);
		if(!cpwd.equalsIgnoreCase(spwd)){
			throw new TranException(ExcepCode.EX000105);
		}
		
		param.putAll(cust);
		//检查银行卡
		param.put("cardType", MsgST.CARD_TYPE_01);
		param.put("cardState", MsgST.CARD_STATUS_1);

		//只能绑定一张银行卡
		Map<String , Object> bankMap=custBankInfService.getCustBankInfo(param);
		if (bankMap==null||bankMap.size()<=0) {
			throw new TranException(ExcepCode.EX000217);
		}
		param.putAll(bankMap);
		
		if(param.get("acctType") == null || "".equals(param.get("acctType"))){
			param.put("acType", MsgCT.AC_TYPE_PT);
		}else{
			param.put("acType", param.get("acctType"));
		}
		
		//加锁查询账户余额
		Map<String , Object> accountMap=custAccountService.queryCustAccountLock(param);
		param.putAll(accountMap);
		///20160517 防止为负数
		Double acT1una=Double.parseDouble(accountMap.get("acT1UNA").toString());
		Double acT1aunp=Double.parseDouble(accountMap.get("acT1AUNP").toString());
		if(acT1una<0.0 || acT1aunp<0.0){
			log.debug("提现金额为负数！！！！");
			throw new TranException(ExcepCode.EX000217);
		};
		//检查代理商状态 
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);
		
		//计算手续费
		try {
			param=countFeeT1(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX060000,e,"手续费计算异常");
		}
		
		//hg add 20160412  检查扣除手续费（单笔收费）以后的金额是否大于0
		if(param.get("netrecamt")==null||param.get("netrecamt").equals("")||Integer.parseInt(param.get("netrecamt").toString()) <= 0){
			throw new TranException(ExcepCode.EX000800);
		}
		
		//校验限额：
		String casType = String.valueOf(param.get("casType"));
		if(MsgST.CAS_TYPE_T0.equals(casType) || MsgST.CAS_TYPE_ALL.equals(casType)){
			log.info("T+0提现限额检查.");
			Map<String,Object> t0 = casService.totalT0Amt(param.get("custId").toString());
			Double totalAmt_ = Double.valueOf(t0.get("totalTxnAmt").toString()) + txamt;
			log.debug("商户当天累计提现额=[{}]元",MoneyUtils.toStrYuan(totalAmt_));
			
			Map<String,Object> rcsmap = new HashMap<String,Object>();
			rcsmap.put("custId", param.get("custId"));
			rcsmap.put("txnAmt", txamt);
			rcsmap.put("totalTxnAmt", totalAmt_);
			rcsmap.put("totalTxnNum", t0.get("totalTxnNum"));
			rcsTransactionService.checkCASLimit(rcsmap);
			
			//代理商限额
			log.info("T+0商户的代理商提现限额检查.");
			t0 = casService.totalAgeT0Amt(param.get("custId").toString());
			totalAmt_ = Double.valueOf(t0.get("totalTxnAmt").toString()) + txamt;
			log.debug("商户的代理商当天累计提现额=[{}]元",MoneyUtils.toStrYuan(totalAmt_));
			
			Map<String,Object> rcsmap2 = new HashMap<String,Object>();
			rcsmap2.put("custId", param.get("custId"));
			rcsmap2.put("agentId", t0.get("agentId"));
			rcsmap2.put("txnAmt", txamt);
			rcsmap2.put("totalTxnAmt", totalAmt_);
			rcsmap2.put("totalTxnNum", t0.get("totalTxnNum"));
			rcsTransactionService.checkAgeCASLimit(rcsmap2);
		}
		
		//创建提现订单
		casService.addCasInf(param);
		//更新帐户
		custAccountService.modifyCustAccountBalanceByCash(param);
		
		log.info("查询秒付开关");
		Map<String, Object> tunnel = new HashMap<String, Object>();
		tunnel.put("paraCode", "DF_QUICK_FLAG");
		Map<String,Object> tunnelMap = platformParameterService.getTunnel(tunnel);
		String qdfSwitch = String.valueOf(tunnelMap.get("paraVal"));
		log.info("秒付开关:[{}]",qdfSwitch);
		if(qdfSwitch.equals("1")){
			//T1提现 自动审核
			param.put("ordstatus", MsgST.CAS_ORD_STATUS_01);  //成功
			param.put("casAudit", MsgST.CAS_ADU_STATUS_PASS);//通过
			
			//代付路由查询
			log.info("代付路由查询..");
			Map<String,Object> routMap = new HashMap<String, Object>();
			routMap.put("daifu", "y");
			routMap.put("rtrType", MsgST.RTR_TYPE_CASPAY);
			routMap.put("rateType", "4");
			routMap.put("payAmt", param.get("netrecamt"));
			routMap.put("agentId", param.get("agentId"));
			routMap.put("firstAgentId", param.get("firstAgentId"));
			routMap.put("cooporgNo", prop.get("QDFOrg"));
			param.putAll(routeService.route(routMap));
			
			String anoPid = "";
			try {
				anoPid = TdExpBasicFunctions.GETDATE()+ seqNoService.getSeqNoNew("ANOPID", "9", "1");
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX060000,e,"获取提现流水号异常");
			}
			param.put("anoPid", anoPid);
			//调用秒付接口
			log.info("外发秒付接口..");
			Map<String, Object> rmap = tranFrontservice.casPay(param);
			rmap.putAll(routMap);
			rmap.putAll(param);
			log.info("调用秒付接口返回={}",rmap);
			param.put("filed3", rmap.get("filed3")); //POSP外发通道ID
			if("00".equals(rmap.get("TCPSCod"))){
				param.put("ordStatus", "07");//清算成功
				param.put("sucDate", DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss"));
				casService.updateCasInfStatus(param);
			}else if("tt".equals(rmap.get("TCPSCod")) || "98".equals(rmap.get("TCPSCod"))){
				param.put("ordStatus", "06");//清算中
				rmap.put("casOrdNo", param.get("casOrdNo"));
				casService.updateCasInfStatus(param);
				//保存代付信息至代付确认表
				rmap.put("payStatus", "0");
				log.info("确认代付插入数据：{}",rmap.toString());
				casService.deleteConPayByCasId(param.get("casOrdNo").toString());
				casService.addConCasInf(rmap);
			}else{
				param.put("ordStatus", "08");//待清算（代付失败）
				casService.updateCasInfStatus(param);
			}
		}
		
		log.info("提现交易完成");
	}
	
	@Override
	public Map<String , Object> getPoundage(Map<String , Object> param) throws TranException{
		ParamValidate.doing(param, "txamt","casType");
		Map<String , Object> result=new HashMap<String, Object>();
		//查询账户余额
		param.putAll(getAcBalance(param));
		//计算手续费
		Map<String , Object> map=countFeeT1(param);
		if(map.get("serviceFee")==null||"".equals(map.get("serviceFee"))){
			result.put("serviceFee", 0);
		}else{
			result.put("serviceFee", map.get("serviceFee"));
		}
		result.put("fee", map.get("fee"));
		
		return result;
	}
	
	@Override
	public void esign(Map<String, Object> param) throws TranException {
		log.info("进入电子签名接口.参数:[{}]", param);
		/**
		 * 效验参数
		 */
		ParamValidate.doing(param, "prdordNo", "content");
		esignService.save(param);
		log.info("电子签名交易完成");
		
	}
	
	@Override
	public Map<String , Object> getAcBalance(Map<String , Object> param) throws TranException{
		log.info("获取可用余额交易参数：{}",param);
		ParamValidate.doing(param, "custId");
		Map<String , Object> accountMap=null;
		try {
			if(param.get("acctType") == null || "".equals(param.get("acctType"))){
				param.put("acType", MsgCT.AC_TYPE_PT);
			}else{
				param.put("acType", param.get("acctType"));
			}
			accountMap=custAccountService.queryCustAccount(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000816,"获取账户余额异常",e);
		}
		if (accountMap==null||accountMap.size()<=0) {
			throw new TranException(ExcepCode.EX000817,"获取账户余额失败");
		}
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("acT1", accountMap.get("acT1"));
		map.put("acT1Y", accountMap.get("acT1Y"));
		map.put("acT0", accountMap.get("acT0"));
		map.put("acBal", accountMap.get("acBal"));
		map.put("acT1UNA", accountMap.get("acT1UNA"));
		map.put("acT1AP", accountMap.get("acT1AP"));
		map.put("acT1AUNP", accountMap.get("acT1AUNP"));
		map.put("acType", accountMap.get("acType"));
		log.info("账户信息:[{}]",map);
		return map;
	}
	
	@Override
	public Map<String , Object> getAcBalance2(Map<String , Object> param) throws TranException{
		log.info("获取可用余额交易参数：{}",param);
		ParamValidate.doing(param, "custId");
		Map<String , Object> accountMap=null;
		try {
			if(param.get("acctType") == null || "".equals(param.get("acctType"))){
				param.put("acType", MsgCT.AC_TYPE_PT);
			}else{
				param.put("acType", param.get("acctType"));
			}
			accountMap=custAccountService.queryCustAccountNew(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000816,"获取账户余额异常",e);
		}
		if (accountMap==null||accountMap.size()<=0) {
			log.info("账户信息:[{}]",accountMap);
			return null;
		}
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("acT1", accountMap.get("acT1"));
		map.put("acT1Y", accountMap.get("acT1Y"));
		map.put("acT0", accountMap.get("acT0"));
		map.put("acBal", accountMap.get("acBal"));
		map.put("acT1UNA", accountMap.get("acT1UNA"));
		map.put("acT1AP", accountMap.get("acT1AP"));
		map.put("acT1AUNP", accountMap.get("acT1AUNP"));
		map.put("acType", accountMap.get("acType"));
		log.info("账户信息:[{}]",map);
		return map;
	}
	
	
	/**
	 * 计算手续费（T1和T1Y账户中提款）
	 * @param param
	 * @return
	 * @throws TranException
	 */
	private Map<String , Object> countFeeT1(Map<String , Object> param) throws TranException{

		Double acBal=Double.valueOf(param.get("acBal").toString());//账户余额
		Double txAmtApp=Double.valueOf(param.get("txamt").toString());//提现目标金额
		Double txAmtT1Y=Double.valueOf(param.get("acT1Y").toString());//t1未提余额
		Double txAmtT1=Double.valueOf(param.get("acT1").toString());//t1余额
		Double txAmtT0=Double.valueOf(param.get("acT0").toString());//t0余额
		Double txAmtT1AP=Double.valueOf(param.get("acT1AP").toString());//已审核
		
		//备份原始帐户记录
		param.put("oldAcBal", param.get("acBal"));
		param.put("oldAcT1Y", param.get("acT1Y"));
		param.put("oldAcT1",  param.get("acT1"));
		param.put("oldAcT0",  param.get("acT0"));
		param.put("oldacT1AP", param.get("acT1AP"));
		param.put("oldacT1AUNP", param.get("acT1AUNP"));
		param.put("oldacT1UNA", param.get("acT1UNA"));
		param.put("oldacType", param.get("acType"));
		
		boolean isWorkday = DateUtil.getWorkDay();//判断是否是工作日
		try {
			int isHoli = holidayRuleService.queryHoliday();
			if (isHoli == 1) {
				isWorkday = false;//为节假日，非工作日
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		

		//获取系统费率、服务费参数
		param.put("paraCode", MsgST.T0_TX_RATE_CODE);
		Map<String, Object> rateT0=platformParameterService.getRate(param);
		log.info("T0手续费:[{}]",rateT0);
		param.put("paraCode", MsgST.T1_TX_RATE_CODE);
		Map<String, Object> rateT1=platformParameterService.getRate(param);
		log.info("T1手续费:[{}]",rateT1);
		
		//获取商户T0提现费率
//		String custRateT0 = merchantService.getRateT0(param);
		param.put("rateType", param.get("acType"));
		Map<String,String> custRateT0Info = merchantService.getRateT0(param);
		String custRateT0 = custRateT0Info.get("rateTCas");//商户T0提现费率
		String custRateT0max = custRateT0Info.get("maxTCas");//商户T0提现单笔收费
		if(custRateT0!=null&&!"".equals(custRateT0)){
			rateT0.put("paraValType", "1");
			rateT0.put("paraVal", custRateT0);
		}
		if(custRateT0max ==null||"".equals(custRateT0max)){
			custRateT0max = "0";
		}
		log.info("T0 提现费率rateTCas ,及 单笔收费maxTCas:[{},{}]",custRateT0,custRateT0max);
		Double maxTCas = Double.parseDouble(custRateT0max);
		
		//节假日手续费
		Map<String, Object> rateHoli = null;
		if(isWorkday == false){
			param.put("paraCode", MsgST.HOLIDAY_TX_RATE_CODE);
			rateHoli = platformParameterService.getRate(param);
			log.info("节假日手续费：[{}]",rateHoli);
		}
		
		log.info("账户:T1Y=[{}] T1AP=[{}],提款金额=[{}]",
				txAmtT1Y.intValue(),txAmtT1AP.intValue(),txAmtApp.intValue());
		//余额检查 
		if (txAmtApp.intValue()>(txAmtT1Y.intValue()+txAmtT1AP.intValue())) {
			throw new TranException(ExcepCode.EX000218,"账户总余额不足(T1Y+T1AP)");
		}
		/*if(txAmtApp.intValue()>txAmtT1Y.intValue() && txAmtApp.intValue()>txAmtT1.intValue()){
			throw new TranException(ExcepCode.EX000218,"账户余额不足(T1Y、T1)");
		}*/
		
		//选检查T1Y余额账户
		if (txAmtT1Y.intValue()>=txAmtApp.intValue()) {
			//T1手续费
			log.info("单纯计算T1手续费[T1Y余额充足 从T1Y账户中提款]");
			param.put("acT1Y", txAmtT1Y.intValue()-txAmtApp.intValue());
			//Double serviceFee = getCountValue(rateT1, txAmtApp);
//			if(rateHoli != null){
//				//serviceFee = serviceFee + getCountValue(rateHoli, txAmtApp);
//			}
			param.put("rate", 0);
			param.put("fee", 0);
			param.put("serviceFee", 0);
			param.put("netrecamt", txAmtApp.intValue());
			param.put("t0Amt", 0);
			param.put("t1Amt", txAmtApp.intValue());
			
			//T1提现 自动审核
			param.put("ordstatus", MsgST.CAS_ORD_STATUS_01);  //成功
			param.put("casAudit", MsgST.CAS_ADU_STATUS_PASS);//通过
			log.info("提现方式[T1]");
			param.put("casType", MsgST.CAS_TYPE_T1);
		}else{
			//手续费
			//Double serviceFee;
			//T+0部分
			Double needPoundageT0 = txAmtApp - txAmtT1Y;
			//T0手续费
			Double poundage = getCountValue(rateT0, needPoundageT0);
			//节假日手续费
			if(rateHoli != null){
				poundage = poundage + getCountValue(rateHoli, needPoundageT0);
			}
			//T0 fee 20160411 hg
			poundage = poundage + maxTCas*100;//poundage以分为单位
			
			if(txAmtT1Y == 0){ //T0提现
				log.info("T0计算手续费[T1AP账户提{}元]",MoneyUtils.toStrYuan(needPoundageT0));
				log.info("提现方式[T0]");
				param.put("casType", MsgST.CAS_TYPE_T0);
				log.info("T0手续费=[{}]",poundage);
				log.info("T0单笔收费费=[{}]",maxTCas);
//				poundage = poundage + maxTCas;
				log.info("T0实际扣除手续费=[{}]",poundage);
				//实际到账资金
				param.put("netrecamt", txAmtApp.intValue()-poundage.intValue());
			}
			else{             //T0+T1 混合提现
				log.info("混合计算手续费[T1Y账户提取{}元,T1AP账户提取{}元]",
						MoneyUtils.toStrYuan(txAmtT1Y),MoneyUtils.toStrYuan(needPoundageT0));
				log.info("提现方式[T0&T1]");
				param.put("casType", MsgST.CAS_TYPE_ALL);
				//T1手续费
			   // serviceFee=getCountValue(rateT1, txAmtT1Y);
			    log.info("T0手续费=[{}] T1手续费=[{}]",poundage,0);
			    //param.put("serviceFee", serviceFee.intValue());//服务费(只有T1才有)
			    param.put("serviceFee", 0);
			    //实际到账资金
				//param.put("netrecamt", txAmtApp.intValue()-serviceFee.intValue()-poundage.intValue());
			    param.put("netrecamt", txAmtApp.intValue()-poundage.intValue());
			}
			log.info("netrecamt 实际到账资金:[{}]",param.get("netrecamt"));
			param.put("fee", poundage.intValue());         //手续费(只有T0才有)
			
			param.put("rate", rateT0.get("paraVal"));
			param.put("acT1Y", 0);   //T1Y账户置0
			param.put("acT0", 0);
			param.put("acT1AP", txAmtT1AP.intValue()-needPoundageT0.intValue());
			
			param.put("t0Amt", needPoundageT0.intValue());
			param.put("t1Amt", txAmtT1Y.intValue());
			
			param.put("ordstatus", MsgST.CAS_ORD_STATUS_04); //审核中
			param.put("casAudit", MsgST.CAS_ADU_STATUS_ING); //待审核
		}
		param.put("acBal", acBal.intValue()-txAmtApp.intValue());
		log.info("计算结果:{}",param);
		return param;
	}
	
	/**
	 * 按照百分比或定额计算具体的手续费
	 * @param rate
	 * @param needPoundage
	 * @return
	 */
	private double getCountValue(Map<String, Object> rate,Double needPoundage) {
		double poundage=0;
		String valType = String.valueOf(rate.get("paraValType"));
		if (valType.equals(MsgST.PARA_VAL_TYPE_1)) {
			String rateVal=String.valueOf(rate.get("paraVal"));
			log.info("最低手续费："+MsgCT.FEE_LOW);
			log.info("最终提现费率："+rateVal);
			poundage = MoneyUtils.calculate(rateVal, needPoundage.toString(), MsgST.FEE_LOW);

		}else if (valType.equals(MsgST.PARA_VAL_TYPE_2)) {
			poundage=Double.valueOf(rate.get("paraVal").toString()).doubleValue();
		}
		return poundage;
	}

	@Override
	public Map<String, Object> reverse(Map<String, Object> param)
			throws TranException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void upBankCard(Map<String,Object> param) throws TranException{
		String bankcard = "";
		try {
			bankcard = saveImg((MultipartFile)param.get("bankCardFile"));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000229, "上传扫描银行卡异常",e);
		}
		param.put("bankcardid", bankcard);
		//更新订单
		termPrdService.modifyPrd(param);
	}
	
	private String saveImg(MultipartFile img) throws Exception{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_CARD);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001001,"银行卡图片保存失败!",e);
		}
		if(rmap != null){
			fid = String.valueOf(rmap.get("fid"));
		}else{
			throw new TranException(ExcepCode.EX001001,"返回值对象rmap为空!");
		}
		log.info("文件ID=[{}]",fid);
		return fid;
	}
	
	/**
	 * 微信扫码支付
	 */
	@Override 
	public Map<String, Object> wxsmPayment(Map<String, Object> param) throws TranException {
		log.info("进入微信扫码支付接口.参数:[{}]", param);
		
		Double payAmt_ = null;
		try{
			payAmt_ = Double.valueOf(param.get("payAmt").toString());
		}catch(Exception e){
			throw new TranException(ExcepCode.EX000800);
		}
		//检查系统支持最大操作金额
		if(payAmt_.doubleValue() > MsgST.AMT_HIG){
			throw new TranException(ExcepCode.EX000802);
		}
		
		//消费时间段检查
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("paraCode", "PAY_START_TIME");
		Map<String,Object> startTimeMap = platformParameterService.getCasTime(queryMap);
		int startTime = Integer.valueOf(startTimeMap.get("paraVal").toString());
		log.info("起始消费时间=[{}]",startTime);
		queryMap.put("paraCode", "PAY_END_TIME");
		Map<String,Object> endTimeMap = platformParameterService.getCasTime(queryMap);
		int endTime = Integer.valueOf(endTimeMap.get("paraVal").toString());
		log.info("截至消费时间=[{}]",endTime);
		
		int ctime = Integer.valueOf(TdExpBasicFunctions.GETDATETIME("HHMISS").substring(0, 2));
		log.info("当前时间=[{}]",ctime);
		if(startTime > ctime || endTime <= ctime){
			log.info("消费时间段检查不通过。");
			throw new TranException(ExcepCode.EX000232,"消费时间段检查不通过。",
					String.valueOf(startTime),String.valueOf(endTime));
		}else{
			log.info("正常消费时间段。");
		}
		
		log.info("1.参数检查（APP）.");
		/**
		 * 必填参数效验 custMobile 手机号 termNo
		 */
		ParamValidate.doing(param, "prdordNo","custMobile", "termNo");
		
		log.info("x.查询收单接入开关");
		Map<String, Object> tunnel = new HashMap<String, Object>();
		tunnel.put("paraCode", "PAY_STATUS_ TUNNEL");
		Map<String,Object> tunnelMap = platformParameterService.getTunnel(tunnel);
		String payTunnelVal = String.valueOf(tunnelMap.get("paraVal"));
		log.info("查询通道选择参数结束（1、收单 2、华势）:"+tunnelMap.get("paraVal"));


		log.info("2.终端检查（带手续费、密钥[刷卡头|APP]）.");
		Map<String, Object> termMap = terminalService.check(param);
		boolean termstatus = false;
		param.putAll(termMap);
		
		log.info("3.商户检查[检查商户状态].");
		Map<String , Object> cust=merchantService.getMerchant(param);
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		param.put("custName", cust.get("custName"));
		param.put("certNo", cust.get("certNo"));
		
		log.info("4.代理商检查.");
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);

		log.info("5.订单检查（商品）.");
		Map<String, Object> prdordMap = termPrdService.check(param);
		param.put("prdordMap", prdordMap);
		
		log.info("6.限额检查（商户）");
		Map<String, Object> rcsMap = new HashMap<String, Object>();
		rcsMap.put("custId", cust.get("custId"));
		rcsMap.put("merclass", cust.get("merclass"));
		rcsMap.put("agentId", agentMap.get("agentId"));
		rcsMap.put("termNo", termMap.get("termNo"));
		rcsMap.put("bizType",prdordMap.get("prdordtype"));
		rcsMap.put("payType", param.get("payType"));
//		rcsMap.put("cardNo", cardNo);
		rcsMap.put("payAmt", param.get("payAmt"));
		rcsTransactionService.checkTxnLimit(rcsMap);
		
		log.info("7.查询费率（费率计算）.");
		param.put("rateCode", "01"); //商户
		param.put("rateId", param.get("custId"));
		param.put("rateType", param.get("payType"));
		Map<String, Object> rateMap = ratesInfService.getRate(param);
		param.put("rateMap", rateMap);
		
		log.info("8.计算手续费 （费率计算）.");
		param.put("rate", MsgST.RATE_GENERAL_TOP);
		Map<String, Object> feeMap = terminalService.calcRateFee(param, rateMap);
		param.put("feeMap", feeMap);
		
		if("2".equals(payTunnelVal)){
			log.info("9.查询路由（合作机构、密钥信息）.");
			//param.put("agentId", agentMap.get("agentId"));
			//param.put("rtrType", MsgST.RTR_TYPE_CONSUME); 
			//routeService.route(param);
			throw new TranException(ExcepCode.EX000801);
		}else{
			log.info("9.走收单无需查询路由。");
		}
		
		log.info("10.创建支付订单.");
		param.put("payCardno", "");
		param.put("issnam", "");
		param.put("crdnam", "");
		param.put("payChannel", param.get("rtrsvr"));
		param.put("scancardnum", param.get("prdordNo"));
		param.put("scanornot", "1");//已审核
		String payNo = termPayService.createPay(param);
		
		log.info("11.更新商品订单.");
		param.put("payNo", payNo); //加入支付订单号
		param.put("ordstatus", MsgST.ORDSTATUS_PROCESSING);
		termPrdService.modifyPrd(param);
		
		log.info("12.创建交易流水.");
		String paymentId=paymentJournalService.addPaymentJournal(param);
		param.put("paymentId", paymentId);//交易流水
		param.put("txnType", "01");       //交易类型 01消费  02 撤销
		
		log.info("13.外发 >>> 调用渠道模块扫码支付接口.");
		if("1".equals(payTunnelVal)){
			//不确定11 12 13步是否需要orgNo
			param.put("rtrsvr", "STHDSD");
			param.put("rtrcod", "9993");
			param.put("orgNo","10000");
		}
		String ordstatus =  MsgST.ORDSTATUS_PROCESSING;  //订单状态 初始状态为支付中
		String paystatus =  MsgST.PAYSTATUS_PAY_ING;  //支付状态 处理中
		String txnstatus =  MsgST.TXNSTATUS_U;
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		try{
			param.put("cardNo", "");
			
			//heguo add 20160503 根据代理商编号 查询出三级代理商（一级代理商时 另外两个为15个F；二级代理商时，最后一个为15个F）
			log.info("New >>> 查询商户所属的三层代理商开始.");
			String agentId_three= agentService.getThreeAgentId(param);
			param.put("agentId_three", agentId_three);
			log.info("New >>> 查询商户所属的三层代理商结束.agentId_three："+agentId_three);
			
			tranFrontservice.wxsmPay(param);
			//设置银行交易状态
			bankstatus = String.valueOf(param.get("TCPSCod"));
		}catch(TranException e){
			exp = e.getCode();
			if(exp.equals(ExcepCode.EX100302)          //接收第三方系统数据超时 
					|| exp.equals(ExcepCode.EX200359)  //第三方系统发送超时
					|| exp.equals(ExcepCode.EX200367)){//发卡方超时
				ordstatus = MsgST.ORDSTATUS_TIMEOUT;
				paystatus = MsgST.PAYSTATUS_TIMEOUT;
				txnstatus = MsgST.TXNSTATUS_T;
			}else{
				ordstatus = MsgST.ORDSTATUS_FAIL;
				paystatus = MsgST.PAYSTATUS_FAIL;
				txnstatus = MsgST.TXNSTATUS_F;
			}
			bankstatus = param.get("TCPSCod") == null?"":String.valueOf(param.get("TCPSCod"));
			log.info("异常处理. exp=[{}] bankstatus=[{}] ordstatus=[{}] paystatus=[{}] txnstatus=[{}]",
					exp,bankstatus,ordstatus,paystatus,txnstatus);
		}
		
		log.info("扫码支付完成.后续处理中...");
		Map<String,Object> udata = new HashMap<String,Object>();
		udata.put("paystatus", paystatus);
		udata.put("bankstatus", bankstatus);
		udata.put("cardNo", "");
		udata.put("dcflag", "");
		udata.put("payNo", payNo);
		udata.put("fee", feeMap.get("fee"));
		udata.put("rate", feeMap.get("rate"));
		udata.put("ordstatus", ordstatus);
		udata.put("paystatus", paystatus);
		udata.put("termstatus", termstatus);
		udata.put("exp", exp);
		udata.put("ctype", termMap.get("ctype"));
		udata.put("cause", param.get("cause"));
		udata.put("txnstatus", txnstatus);
		udata.put("custName", cust.get("custName"));
		udata.put("custId", cust.get("custId"));
		udata.put("prdordNo", param.get("prdordNo"));
		udata.put("payordno", payNo);
		udata.put("paymentId", paymentId);
		
		try{
			paymentJournalService.modifyPaymentJournal(udata);
			
			termPrdService.modifyPrd(udata);
			
			termPayService.modifyPay(udata);
		}catch(TranException e){
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}

		if(!exp.equals("000000")){
			throw new TranException(exp);
		}
		log.info("扫码支付完成.后续处理完成...");
		return null;
	}
	
	/**
	 * 微信扫码支付结果查询
	 */
	@Override 
	public Map<String, Object> wxsmQuery(Map<String, Object> param) throws TranException {
		log.info("进入微信扫码查询接口.参数:[{}]", param);
		
		log.info("1.参数检查（APP）.");
		/**
		 * 必填参数效验 订单号 prdordNo
		 */
		ParamValidate.doing(param, "prdordNo");
		
		log.info("x.查询收单接入开关");
		Map<String, Object> tunnel = new HashMap<String, Object>();
		tunnel.put("paraCode", "PAY_STATUS_ TUNNEL");
		Map<String,Object> tunnelMap = platformParameterService.getTunnel(tunnel);
		String payTunnelVal = String.valueOf(tunnelMap.get("paraVal"));
		log.info("查询通道选择参数结束（1、收单 2、华势）:"+tunnelMap.get("paraVal"));
		
		log.info("2.商户检查[检查商户状态].");
		Map<String , Object> cust=merchantService.getMerchant(param);
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		
		log.info("3.代理商检查.");
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);
		
		if("2".equals(payTunnelVal)){
			log.info("4.查询路由（合作机构、密钥信息）.");
			//param.put("agentId", agentMap.get("agentId"));
			//param.put("rtrType", MsgST.RTR_TYPE_CONSUME); 
			//routeService.route(param);
			throw new TranException(ExcepCode.EX000801);
		}else{
			log.info("4.走收单无需查询路由。");
		}
		
		log.info("5.查询订单.");
		Map<String,Object> prdordMap = termPrdService.queryPrdByOrdNo(param);
		param.put("payAmt", prdordMap.get("ordamt"));
		param.put("payNo", prdordMap.get("payordno"));
		param.putAll(termPayService.queryPayInfWithAccById(prdordMap));
		param.put("opayordno", prdordMap.get("payordno"));
		param.putAll(paymentJournalService.queryOrnPaymentJournal(param));
		param.put("oacBal",param.get("acBal"));
		param.put("oacT0",param.get("acT0"));
		param.put("oacT1",param.get("acT1"));
		param.put("oacT1UNA",param.get("acT1UNA"));
		param.put("oacT1AP",param.get("acT1AP"));
		param.put("oacT1AUNP",param.get("acT1AUNP"));
		param.put("oacT1Y",param.get("acT1Y"));
		param.put("acType", prdordMap.get("biztype"));
		param.put("scanornot", "1");//已审核 累积金额
		param.put("payType", param.get("paytype"));
		if(MsgST.ORDSTATUS_SUCCESS.equals(prdordMap.get("ordstatus")) &&
				MsgST.PAYSTATUS_SUCCESS.equals(param.get("paystatus")) &&
				MsgST.TXNSTATUS_S.equals(param.get("txnStatus"))){
			param.put("TCPSCod", MsgST.BANK_TRAN_OK);
			log.info("扫码支付原订单已成功...");
			return null;
		}
		
		log.info("6.终端检查（带手续费、密钥[刷卡头|APP]）.");
		param.put("termNo", param.get("terNo"));
		Map<String, Object> termMap = terminalService.check(param);
		boolean termstatus = false;
		param.putAll(termMap);
		
		log.info("7.查询费率（费率计算）.");
		Map<String, Object> rateMap = new HashMap<String, Object>();
		rateMap.put("rateCode", "01"); //商户
		rateMap.put("rateId", param.get("custId"));
		rateMap.put("rateType", prdordMap.get("biztype"));
		rateMap.putAll(ratesInfService.getRate(rateMap));
		param.put("rateMap", rateMap);
		
		log.info("8.计算手续费 （费率计算）.");
		param.put("rate", MsgST.RATE_GENERAL_TOP);
		Map<String, Object> feeMap = terminalService.calcRateFee(param, rateMap);
		param.put("feeMap", feeMap);
		
		log.info("9.外发 >>> 调用渠道模块支付接口.");
		if("1".equals(payTunnelVal)){
			//不确定11 12 13步是否需要orgNo
			param.put("rtrsvr", "STHDSD");
			param.put("rtrcod", "9994");
			param.put("orgNo","10000");
		}
		String ordstatus =  MsgST.ORDSTATUS_SUCCESS;  //订单状态 初始状态为交易成功
		String paystatus =  MsgST.PAYSTATUS_SUCCESS;  //支付状态
		String txnstatus =  MsgST.TXNSTATUS_S;
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		try{
			param.put("cardNo", "");
			param.put("txnType", "01");       //交易类型 01消费  02 撤销
			
			//heguo add 20160503 根据代理商编号 查询出三级代理商（一级代理商时 另外两个为15个F；二级代理商时，最后一个为15个F）
			log.info("New >>> 查询商户所属的三层代理商开始.");
			String agentId_three= agentService.getThreeAgentId(param);
			param.put("agentId_three", agentId_three);
			log.info("New >>> 查询商户所属的三层代理商结束.agentId_three："+agentId_three);
			
			tranFrontservice.wxsmQuery(param);
			//设置银行交易状态
			bankstatus = String.valueOf(param.get("TCPSCod"));
			
			if(MsgST.BANK_TRAN_OK.equals(bankstatus)){
				ordstatus =  MsgST.ORDSTATUS_SUCCESS;
				paystatus =  MsgST.PAYSTATUS_SUCCESS;
				txnstatus =  MsgST.TXNSTATUS_S;
			}else if (MsgST.BANK_TRAN_PROCESSING.equals(bankstatus)){
				ordstatus = MsgST.ORDSTATUS_PROCESSING;
				paystatus = MsgST.PAYSTATUS_PAY_ING;
				txnstatus = MsgST.TXNSTATUS_U;
			}else{
				ordstatus = MsgST.ORDSTATUS_FAIL;
				paystatus = MsgST.PAYSTATUS_FAIL;
				txnstatus = MsgST.TXNSTATUS_F;
			}
		}catch(TranException e){ //本地异常，三方失败不抛异常
			exp = e.getCode();
			ordstatus = MsgST.ORDSTATUS_PROCESSING;
			paystatus = MsgST.PAYSTATUS_PAY_ING;
			txnstatus = MsgST.TXNSTATUS_U;
			bankstatus = param.get("TCPSCod") == null?"":String.valueOf(param.get("TCPSCod"));
			log.info("异常处理. exp=[{}] bankstatus=[{}] ordstatus=[{}] paystatus=[{}] txnstatus=[{}]",
					exp,bankstatus,ordstatus,paystatus,txnstatus);
		}
		
		log.info("扫码结果查询完成.后续处理中...");
		
		Map<String,Object> udata = new HashMap<String,Object>();
		udata.put("paystatus", paystatus);
		udata.put("bankstatus", bankstatus);
		udata.put("cardNo", "");
		udata.put("dcflag", "");
		udata.put("payNo", prdordMap.get("payordno"));
		udata.put("fee", feeMap.get("fee"));
		udata.put("rate", feeMap.get("rate"));
		udata.put("ordstatus", ordstatus);
		udata.put("termstatus", termstatus);
		udata.put("exp", exp);
		udata.put("ctype", termMap.get("ctype"));
		udata.put("cause", param.get("cause"));
		udata.put("txnstatus", txnstatus);
		udata.put("custName", cust.get("custName"));
		
		Map<String,Map<String,Object>> resultMap = new HashMap<String,Map<String,Object>>();
		resultMap.put("param", param);
		resultMap.put("prdordMap", prdordMap);
		resultMap.put("termMap", termMap);
		resultMap.put("udata", udata);
		try{
			payTranDoneService.handler(resultMap);
		}catch(TranException e){
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}

		if(!exp.equals("000000")){
			throw new TranException(exp);
		}
		log.info("扫码结果查询完成.后续处理完成...");
		return null;
	}
	
	/**
	 * 微信扫码支付结果处理
	 */
	@Override 
	public Map<String, Object> wxsmResultHandler(Map<String, Object> param) throws TranException {
		log.info("进入微信扫码结果处理.参数:[{}]", param);
		
		log.info("1.参数检查（APP）.");
		/**
		 * 必填参数效验 订单号 prdordNo 交易结果 tradeStatus
		 */
		ParamValidate.doing(param, "prdordNo", "tradeStatus");
		
		log.info("2.查询订单.");
		Map<String,Object> prdordMap = termPrdService.queryPrdByOrdNo(param);
		param.put("payAmt", prdordMap.get("ordamt"));
		param.put("payNo", prdordMap.get("payordno"));
		param.putAll(termPayService.queryPayInfWithAccById(prdordMap));
		param.put("opayordno", prdordMap.get("payordno"));
		param.putAll(paymentJournalService.queryOrnPaymentJournal(param));
		param.put("oacBal",param.get("acBal"));
		param.put("oacT0",param.get("acT0"));
		param.put("oacT1",param.get("acT1"));
		param.put("oacT1UNA",param.get("acT1UNA"));
		param.put("oacT1AP",param.get("acT1AP"));
		param.put("oacT1AUNP",param.get("acT1AUNP"));
		param.put("oacT1Y",param.get("acT1Y"));
		param.put("acType", prdordMap.get("biztype"));
		param.put("scanornot", "1");//已审核 累积金额
		param.put("payType", param.get("paytype"));
		if(MsgST.ORDSTATUS_SUCCESS.equals(prdordMap.get("ordstatus")) &&
				MsgST.PAYSTATUS_SUCCESS.equals(param.get("paystatus")) &&
				MsgST.TXNSTATUS_S.equals(param.get("txnStatus"))){
			param.put("TCPSCod", MsgST.BANK_TRAN_OK);
			log.info("扫码支付原订单已成功...");
			return null;
		}
		
		log.info("3.终端检查（带手续费、密钥[刷卡头|APP]）.");
		param.put("termNo", param.get("terNo"));
		Map<String, Object> termMap = terminalService.check(param);
		boolean termstatus = false;
		param.putAll(termMap);
		
		log.info("4.商户检查[检查商户状态].");
		Map<String , Object> cust=merchantService.getMerchantByID(param);
		if (cust.get("custStatus").equals(MsgST.CUST_STATUS_FAL)) {
			throw new TranException(ExcepCode.EX000215);
		}
		
		log.info("5.代理商检查.");
		Map<String, Object> agentMap = agentService.checkAgent(param);
		param.putAll(agentMap);
		
		log.info("6.查询费率（费率计算）.");
		Map<String, Object> rateMap = new HashMap<String, Object>();
		rateMap.put("rateCode", "01"); //商户
		rateMap.put("rateId", param.get("custId"));
		rateMap.put("rateType", prdordMap.get("biztype"));
		rateMap.putAll(ratesInfService.getRate(rateMap));
		param.put("rateMap", rateMap);
		
		log.info("7.计算手续费 （费率计算）.");
		param.put("rate", MsgST.RATE_GENERAL_TOP);
		Map<String, Object> feeMap = terminalService.calcRateFee(param, rateMap);
		param.put("feeMap", feeMap);
		
		String ordstatus =  MsgST.ORDSTATUS_SUCCESS;  //订单状态 初始状态为交易成功
		String paystatus =  MsgST.PAYSTATUS_SUCCESS;  //支付状态
		String txnstatus =  MsgST.TXNSTATUS_S;
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		
		if(MsgST.TXNSTATUS_S.equals(param.get("tradeStatus"))){
			ordstatus = MsgST.ORDSTATUS_SUCCESS;
			paystatus = MsgST.PAYSTATUS_SUCCESS;
			txnstatus = MsgST.TXNSTATUS_S;
			bankstatus = MsgST.BANK_TRAN_OK;
			exp = "000000";
			param.put("TCPSCod", MsgST.BANK_TRAN_OK);
		}else if(MsgST.TXNSTATUS_F.equals(param.get("tradeStatus"))){
			ordstatus = MsgST.ORDSTATUS_FAIL;
			paystatus = MsgST.PAYSTATUS_FAIL;
			txnstatus = MsgST.TXNSTATUS_F;
			bankstatus = MsgST.BANK_TRAN_FAIL;
			exp = ExcepCode.EX000801;
			param.put("TCPSCod", MsgST.BANK_TRAN_FAIL);
		}else{
			ordstatus = MsgST.ORDSTATUS_PROCESSING;
			paystatus = MsgST.PAYSTATUS_PAY_ING;
			txnstatus = MsgST.TXNSTATUS_U;
			bankstatus = MsgST.BANK_TRAN_OK;
			exp = "000000";
			param.put("TCPSCod", MsgST.BANK_TRAN_PROCESSING);
		}
		
		param.put("cardNo", "");
		param.put("txnType", "01");       //交易类型 01消费  02 撤销
		
		log.info("=====>扫码通知后续处理中...");
		
		Map<String,Object> udata = new HashMap<String,Object>();
		udata.put("paystatus", paystatus);
		udata.put("bankstatus", bankstatus);
		udata.put("cardNo", "");
		udata.put("dcflag", "");
		udata.put("payNo", prdordMap.get("payordno"));
		udata.put("fee", feeMap.get("fee"));
		udata.put("rate", feeMap.get("rate"));
		udata.put("ordstatus", ordstatus);
		udata.put("termstatus", termstatus);//
		udata.put("exp", exp);
		udata.put("ctype", termMap.get("ctype"));
		udata.put("cause", param.get("cause"));
		udata.put("txnstatus", txnstatus);
		udata.put("custName", cust.get("custName"));
		
		Map<String,Map<String,Object>> resultMap = new HashMap<String,Map<String,Object>>();
		resultMap.put("param", param);
		resultMap.put("prdordMap", prdordMap);
		resultMap.put("termMap", termMap);
		resultMap.put("udata", udata);
		try{
			payTranDoneService.handler(resultMap);
		}catch(TranException e){
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}

		log.info("扫码通知后续处理完成...");
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(Integer.valueOf(TdExpBasicFunctions.GETDATETIME("HHMISS").substring(0, 2)));
	}
	
	
}
