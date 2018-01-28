package com.tangdi.production.mpapp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.AgentService;
import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpapp.service.CasService;
import com.tangdi.production.mpapp.service.CustAccountService;
import com.tangdi.production.mpapp.service.CustBankInfService;
import com.tangdi.production.mpapp.service.ESignService;
import com.tangdi.production.mpapp.service.HolidayRuleService;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.MisTranService;
import com.tangdi.production.mpapp.service.PayTranDoneService;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpapp.service.PlatformParameterService;
import com.tangdi.production.mpapp.service.ProfitSharingService;
import com.tangdi.production.mpapp.service.RouteService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpapp.service.TranSerialRecordService;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpomng.bean.HolidayRuleInf;
import com.tangdi.production.mprcs.service.RcsTransactionService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * <b>机构通道交易接口[终端签到、订单消费、订单消费撤销] 接口实现</b></br>
 * 
 * @author chenlibo 2015/3/20
 *
 */
@Service
public class MisTranServiceImpl implements MisTranService {
	private static Logger log = LoggerFactory.getLogger(MisTranServiceImpl.class);


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
		 * 介质类型
		 */
		ParamValidate.doing(param, "prdordNo","custMobile", "termNo", "termType", "pinblk", "track", "mediaType");
		if (MsgST.MEDIATYPE_IC.equals(String.valueOf(param.get("mediaType")))) {
			// IC卡试效验IC卡数据
			/**
			 * period 有效期 icdata IC卡数据域 seqnum 序列号
			 * 
			 */
			ParamValidate.doing(param, "period", "icdata", "crdnum");
		}
		List<Map<String, Object>> rateList = 
				(List<Map<String, Object>>) terminalService.getTermRate(param)
				.get("rateList");
		param.put("rateType", rateList.get(0).get("rateNo"));
		param.put("rate", rateList.get(0).get("rateNo"));

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
		tranFrontservice.selectTermService(param);
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_1)){
			ParamValidate.doing(param, "track");
		}else{
			ParamValidate.doing(param, "icdata","track");
			param.put("icdata", param.get("icdata"));
		}
		String[] track = getTrack(param.get("track").toString());
		String cardNo = String.valueOf(track[0]).replace("=", "D").replace("d", "D");
		track[0] = cardNo;
		cardNo=cardNo.substring(0, cardNo.indexOf("D"));
		param.put("track2", track[0]);
		param.put("track3", "");
		param.put("cardNo", cardNo);

		log .info("7.卡BIN信息查询.");
		UnionfitInf bin =null;
		try {
			bin = unionfitService.getCardInf(cardNo);
			log.info("卡BIN信息：{}",bin.debug());
			param.putAll(bin.toMap());
		} catch (Exception e) {
			log.warn("卡BIN信息获取失败!",e.getMessage());
			throw new TranException(ExcepCode.EX001002);
		}
		
		param.put("scancardnum", cardNo);
		param.put("scanornot", "1");//已审核
		
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
		
		log.info("11.创建付订单.");
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
			param.put("address", "上海市");
		}
		String ordstatus =  MsgST.ORDSTATUS_SUCCESS;  //订单状态 初始状态为交易成功
		String paystatus =  MsgST.PAYSTATUS_SUCCESS;  //支付状态
		String txnstatus =  MsgST.TXNSTATUS_S;
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		try{
			param.put("cardNo", cardNo);
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

	@Override
	public Map<String, Object> dispayment(Map<String, Object> param)
			throws TranException {
		log.info("进入订单撤销接口.参数:[{}]", param);
		
		log.info("1.参数检查（APP）.");
		/**
		 * 必填参数效验 custMobile 手机号 termNo 终端号 pinblk 密码密文 track 磁道信息 mediaType
		 * 介质类型
		 */
		ParamValidate.doing(param, "prdordNo","custMobile", "termNo", "termType", "pinblk", "track", "mediaType");
		if (MsgST.MEDIATYPE_IC.equals(String.valueOf(param.get("mediaType")))) {
			// IC卡试效验IC卡数据
			/**
			 * period 有效期 icdata IC卡数据域 seqnum 序列号
			 * 
			 */
			ParamValidate.doing(param, "period", "icdata", "crdnum");
		}
		List<Map<String, Object>> rateList = 
				(List<Map<String, Object>>) terminalService.getTermRate(param)
				.get("rateList");
		param.put("rateType", rateList.get(0).get("rateNo"));
		param.put("rate", rateList.get(0).get("rateNo"));


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
		Map<String, Object> prdordMap = termPrdService.queryPrdByOrdNo(param);
		param.put("prdordMap", prdordMap);
		//只对成功的订单撤销
		if(!prdordMap.get("ordstatus").equals(MsgST.ORDSTATUS_SUCCESS) ||
				(prdordMap.get("payordno") != null && 
				"".equals(prdordMap.get("payordno").toString().trim())) ){
			throw new TranException(ExcepCode.EX080007);
		}
		String payNo = prdordMap.get("payordno").toString(); //支付订单号
		
		log.info("6.解密磁道信息（终端实现类）.");
		tranFrontservice.selectTermService(param);
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_1)){
			ParamValidate.doing(param, "track");
		}else{
			ParamValidate.doing(param, "icdata","track");
			param.put("icdata", param.get("icdata"));
		}
		String[] track = getTrack(param.get("track").toString());
		String cardNo = String.valueOf(track[0]).replace("=", "D").replace("d", "D");
		track[0] = cardNo;
		cardNo=cardNo.substring(0, cardNo.indexOf("D"));
		param.put("track2", track[0]);
		param.put("track3", "");
		param.put("cardNo", cardNo);

		log .info("7.卡BIN信息查询.");
		UnionfitInf bin =null;
		try {
			bin = unionfitService.getCardInf(cardNo);
			log.info("卡BIN信息：{}",bin.debug());
			param.putAll(bin.toMap());
		} catch (Exception e) {
			log.warn("卡BIN信息获取失败!",e.getMessage());
			throw new TranException(ExcepCode.EX001002);
		}
		
		log.info("8.支付订单查询."); 
		Map<String, Object> pOrdMap = new HashMap<String, Object>();
		pOrdMap.put("payordno", payNo);
		pOrdMap = termPayService.queryPayOrdById(pOrdMap);
		if(!MsgST.PAYSTATUS_SUCCESS.equals(pOrdMap.get("paystatus"))){
			throw new TranException(ExcepCode.EX080009);
		}
		if(!new SimpleDateFormat("yyyyMMdd").format(new Date())
				.equals(pOrdMap.get("payDate"))){
			throw new TranException(ExcepCode.EX080012);
		}
		Double payAmt = NumberUtils.toDouble((String) param.get("payAmt"));
		param.put("payAmt", payAmt.intValue());
		if(!param.get("payAmt").toString().equals(pOrdMap.get("txamt").toString())){
			log.warn("撤销金额与支付订单 金额不符 数据库查询到的："+pOrdMap.get("txamt")+";参数所传："+param.get("payAmt"));
			throw new TranException(ExcepCode.EX080013, "撤销金额与支付订单 金额不符");
		}
		param.put("orderId", pOrdMap.get("thirdOrdno"));
		param.put("netrecamt", pOrdMap.get("netrecamt"));
		param.put("fee", pOrdMap.get("fee"));
		param.put("txamt", pOrdMap.get("txamt"));
		
		
		log.info("9.限额检查（商户）"); //撤销成功恢复限额 ？
		Map<String, Object> rcsMap = new HashMap<String, Object>();
		
		log.info("10.计算手续费 （费率计算）."); //是否要恢复手续费？
		Map<String, Object> feeMap = new HashMap<String, Object>();
		feeMap.put("fee", param.get("fee"));
		feeMap.put("rate", param.get("rate"));
		param.put("feeMap", feeMap);
		
		log.info("11.查询路由（合作机构、密钥信息）.");

		param.put("agentId", agentMap.get("agentId"));
		param.put("rtrType", MsgST.RTR_TYPE_CONSUME); 
		
		routeService.route(param);
		
		log.info("12.创建交易流水.");
		param.put("payNo", payNo);
		param.put("txnType", "02");       //交易类型 01消费  02 撤销
		String paymentId=paymentJournalService.addPaymentJournal(param);
		
		param.put("paymentId", paymentId);//交易流水
		
		log.info("13.外发 >>> 调用渠道模块支付撤销接口.");
		String ordstatus =  MsgST.ORDSTATUS_CANCELED;  //订单状态 初始状态为取消
		String paystatus =  MsgST.PAYSTATUS_CANCELED;  //支付状态 撤销成功
		String txnstatus =  MsgST.TXNSTATUS_S; // 更新流水状态   S 成功
		String otxnstatus = MsgST.TXNSTATUS_R; // 更新原流水状态   R 撤销
		String exp = "000000";
		String bankstatus=  MsgST.BANK_TRAN_OK;       //银行交易成功状态
		try{
			param.put("cardNo", cardNo);
			tranFrontservice.tranCancel(param);
			//设置银行交易状态
			bankstatus = String.valueOf(param.get("TCPSCod"));
		}catch(TranException e){
			exp = e.getCode();
			if(exp.equals(ExcepCode.EX100302)          //接收第三方系统数据超时 
					|| exp.equals(ExcepCode.EX200359)  //第三方系统发送超时
					|| exp.equals(ExcepCode.EX200367)){//发卡方超时
				txnstatus = MsgST.TXNSTATUS_T;
			}else{
				txnstatus = MsgST.TXNSTATUS_F;
			}
			bankstatus = param.get("TCPSCod") == null?"":String.valueOf(param.get("TCPSCod"));
			log.info("异常处理. exp=[{}] bankstatus=[{}] txnstatus=[{}]",
					exp,bankstatus,txnstatus);
		}
		
		log.info("支付撤销完成.后续处理中...");
		Map<String,Object> udata = new HashMap<String,Object>();
		udata.put("paystatus", paystatus);
		udata.put("bankstatus", bankstatus);
		udata.put("cardNo", cardNo);
		udata.put("dcflag", bin.getDcflag());
		udata.put("payNo", payNo);
		udata.put("fee", feeMap.get("fee"));
		udata.put("rate", feeMap.get("rate"));
		udata.put("ordstatus", ordstatus);
		udata.put("termstatus", termstatus);
		udata.put("exp", exp);
		udata.put("ctype", termMap.get("ctype"));
		udata.put("cause", param.get("cause"));
		udata.put("txnstatus", txnstatus);
		udata.put("otxnstatus", otxnstatus);
		udata.put("custName", cust.get("custName"));
		
		Map<String,Map<String,Object>> resultMap = new HashMap<String,Map<String,Object>>();
		resultMap.put("param", param);
		resultMap.put("prdordMap", prdordMap);
		resultMap.put("termMap", termMap);
		resultMap.put("udata", udata);
		try{
			payTranDoneService.cancelledHandler(resultMap);
		}catch(TranException e){
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}

		if(!exp.equals("000000")){
			throw new TranException(exp);
		}
		log.info("支付撤销完成.后续处理完成...");
		return null;
	}
	
	/**
	 * 获取磁道信息
	 * @param track
	 * @return [0] 2磁道 [1] 3磁道
	 */
	private static String[] getTrack(String track) throws TranException{
		log.info("磁道信息:{}",track);

		String[] tracks = new String[]{"",""};
		if(!track.contains("|")){
			throw new TranException(ExcepCode.EX100101,"磁道数据格式有误,缺少分隔符\"|\"");
		}
		tracks = track.split("\\|");
		log.info("磁道信息解析[2磁道={},3磁道={}]",tracks[0],"");
		return tracks;
	}

}
