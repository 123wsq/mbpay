package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.CustAccountService;
import com.tangdi.production.mpapp.service.PayTranDoneService;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpapp.service.ProfitSharingService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpapp.service.TranSerialRecordService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.EsignService;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.DataFormatUtil;
import com.tangdi.production.mpbase.util.ImgFont;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mprcs.service.RcsTransactionService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 支付交易完成后业务处理
 * @author zhengqiang
 *
 */
@Service
public class PayTranDoneServiceImpl implements PayTranDoneService {
	private static Logger log = LoggerFactory.getLogger(PayTranDoneServiceImpl.class);
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
	 * 交易流水接口
	 */
	@Autowired
	private PaymentJournalService paymentJournalService;
	/**
	 * 交易信息查询
	 */
	@Autowired
	private TranSerialRecordService tranSerialRecordInfService;

	/**
	 * 分润计算业务接口
	 */
	@Autowired
	private ProfitSharingService profitSharingService;
	/**
	 * 账户查询业务层接口
	 */
	@Autowired
	private CustAccountService custAccountService;
	
	/**
	 * 商户限额业务接口
	 */
	@Autowired
	private RcsTransactionService rcsTransactionService;
	
	/**
	 * 前置交易接口
	 */
	@Autowired
	private TranFrontService tranFrontservice;
	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private EsignService esignService;
	
	/**
	 * mpapp 模块配置文件
	 * 
	 */
	@Value("#{mpappConfig}")
	private Properties prop;
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void handler(Map<String,Map<String,Object>> pmap) throws TranException {
		log.info("支付完成, 订单更新中... 请求参数=[{}]",pmap);
		Map<String,Object> param =  pmap.get("param");
		Map<String,Object> prdordMap = pmap.get("prdordMap");
		Map<String,Object> termMap =  pmap.get("termMap");
		Map<String,Object> udata = pmap.get("udata");
		boolean termstatus = (Boolean) udata.get("termstatus");
		String exp =  (String) udata.get("exp");
		Map<String, Object> prdmap =null;
		Map<String, Object> paymap =null;
		String busType  = prdordMap.get("prdordtype").toString();
		String payordno = udata.get("payNo").toString();
		String ctype = "00";  //收款类型，预留接口
		String payordtime = param.get("payordtime").toString();
		log.info("支付时间=[{}]",payordtime);
		try{
			log.info("1.更新交易流水.");		
			param.put("txnstatus", udata.get("txnstatus"));
			param.put("cause", udata.get("cause"));
			paymentJournalService.modifyPaymentJournal(param);
			if (MsgST.BANK_TRAN_OK.equals(udata.get("bankstatus").toString())) {
				
				//计算合作机构费
				String merRateType = "";
				String merRate = "";
				String merRateTop = "";
				int payAmt     = 0;
				int merSettleAmt = 0;
				try{
					payAmt     = Integer.valueOf(param.get("payAmt").toString());
					 merRateType = param.get("TMerRateType").toString();
					 merRate = param.get("TMerRate").toString();
					 merRateTop = param.get("TMerRateTop").toString();
					 merSettleAmt = getMerSettleAmt(merRateType,merRate,merRateTop,payAmt);
				}catch(Exception e){
					log.error("计算合作机构结算费出错了.");
				}
				
				String tranTime=TdExpBasicFunctions.GETDATETIME();
				Map<String , Object> tranMap=new HashMap<String, Object>();
				tranMap.put("busType",  busType);
				tranMap.put("subBus",  prdordMap.get("bizType"));
				tranMap.put("payWay",   param.get("payType"));
				tranMap.put("agentId",  param.get("agentId"));
				tranMap.put("custId",  param.get("custId"));
				tranMap.put("custName",  udata.get("custName"));
				tranMap.put("terNo",  termMap.get("termNo"));
				tranMap.put("bankCardNo",  udata.get("cardNo"));
				tranMap.put("cardType",  udata.get("dcflag"));
				tranMap.put("tranAmt", payAmt);
				tranMap.put("tranYear", tranTime.substring(0, 4));
				tranMap.put("tranMonth", tranTime.substring(4,6));
				tranMap.put("tranDay",   tranTime.substring(6,8));
				tranMap.put("tranTime", tranTime.substring(8));
				tranMap.put("tranCode", udata.get("payNo"));
				tranMap.put("tranState", "1");
				tranMap.put("cooporgNo", param.get("orgNo"));
				tranMap.put("mid",   param.get("mid"));
				tranMap.put("mtype", param.get("mtype"));
				tranMap.put("merNo", param.get("TMercId"));
				tranMap.put("mterNo", param.get("TTermId"));
				tranMap.put("merRateType", param.get("TMerRateType"));
				tranMap.put("merRate", param.get("TMerRate"));
				tranMap.put("merRateTop", param.get("TMerRateTop"));
				tranMap.put("tranRateType", param.get("rateType"));
				tranMap.put("tranRate", param.get("rate"));
//				tranMap.put("tranRateTop", param.get(""));
				tranMap.put("thirdTranDate", param.get("CTxnDt"));
				tranMap.put("thirdTranTime", param.get("CTxnTm"));
				tranMap.put("merSettleAmt", merSettleAmt);
				tranMap.put("tranFee", udata.get("fee"));
				tranMap.put("merFee", payAmt-merSettleAmt);
				tranMap.put("refno", param.get("SRefNo"));
				tranMap.put("prdno", param.get("prdordNo"));
				tranMap.put("tranSettleAmt", param.get("netrecamt"));
				log.info("写入交易记录流水:{}",tranMap);
				//登陆交易记录流水
				tranSerialRecordInfService.addTranSerialRecordInf(tranMap);
				
				Map<String , Object> map=new HashMap<String, Object>();
				map.put("payFee", udata.get("fee"));
				map.put("rate", param.get("rateType"));
				map.put("agentId", param.get("agentId"));
				map.put("payNo", udata.get("payNo"));
				map.put("termRate", udata.get("rate"));
				map.put("payAmt", param.get("payAmt"));
				map.put("payType", param.get("payType"));
				profitSharingService.profitSharing(map);
				log.info("2.限额登陆（订单信息、合作机构账户更新）.");
				rcsTransactionService.updateOrgTermTxnAmt(param);
			}
			
			
			log.info("3.更新商品订单.");
			prdmap = new HashMap<String, Object>();
			prdmap.put("ordstatus",udata.get("ordstatus"));
			prdmap.put("prdordNo", prdordMap.get("prdordno"));
			prdmap.put("custId", prdordMap.get("custId"));
			termPrdService.modifyPrd(prdmap);
			
			log.debug("udata=[{}]",udata);
			log.info("4.更新支付订单.");
		
			paymap = new HashMap<String, Object>();
			paymap.put("paystatus", udata.get("paystatus"));
			paymap.put("payordno",  payordno);
			paymap.put("prdordNo", prdordMap.get("prdordno"));
			paymap.put("ctype",  ctype);
			paymap.put("thirdOrdno", param.get("thirdOrdno"));
			paymap.put("payChannel", param.get("payChannel"));//补充POSP外发渠道ID
			//TODO 金额根据scanornot存入不同字段
			termPayService.modifyPay(paymap);
		
			//业务类型为收款时更新余额。
			if(!termstatus && exp.equals("000000") && busType.equals(MsgST.PRDORDTYPE_PAYMENTS)){
				//更新余额
				//udata.put("bankstatus", "00");//TODO 删除这一行
				if(MsgST.BANK_TRAN_OK.equals(udata.get("bankstatus").toString())){
					log.info("5.更新账户余额.");
					custAccountService.modifyCustAccountBalanceByPay(param);	
					
					log.info("6.合成电子签名{}.",param);
					//合成电子签名
					if(paymap.get("esign") != null){
						//获取图片ID
						String esign = paymap.get("esign").toString();
						
						LinkedList<ImgFont> list = new LinkedList<ImgFont>();
						
						String spath = null;
						
						try{
							String crdno = param.get("cardNo").toString();
							list.add(new ImgFont(param.get("TMercName"),120,180));    //商户名
							list.add(new ImgFont(param.get("TTermId"),180,270));  //终端号
							list.add(new ImgFont(param.get("TMercId"),180,310));  //商户编号
							list.add(new ImgFont(DataFormatUtil.getCardNo(crdno),100,380));  //卡号
							list.add(new ImgFont("消费(SALE)",180,475));  //交易类型
							list.add(new ImgFont("",380,475));  //有效期
							list.add(new ImgFont(param.get("batchNo"),180,520));  //批次号
							list.add(new ImgFont(param.get("SRefNo"),350,520));  //查询号
							list.add(new ImgFont(DataFormatUtil.getDateTime(payordtime),180,550));  //时间
							list.add(new ImgFont(param.get("TSeqNo"),120,580));   //序号
							list.add(new ImgFont(param.get("AutCod"),380,580));   //授权号
							list.add(new ImgFont(MoneyUtils.toStrYuan(param.get("payAmt")),120,720));   //
							
						   spath = fileUploadService.getFilePath(esign);
						}catch(Exception e){
							log.error("签名图片路径获取失败！");
						}
						if(spath != null){
							String fid = esignService.save(list,spath,ctype);
							termPayService.modifyESign(payordno, fid);
						}
					}
				}
			}else{
				log.info("更新账户信息失败");
			}
		}catch(TranException e){
			//数据更新失败,调用冲正接口
			if(param.get("redo").toString().equals("0")){
				param.put("RedoTCPSCod", "06");
				tranFrontservice.redo(param);
			}
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}
	}
	/**
	 * 计算合作机构结算金额
	 * @param merRateType 费率类型
	 * @param merRate     费率点
	 * @param merRateTop  费率封顶
	 * @param txamt       交易金额
	 * @return  结算金额
	 */
	private int getMerSettleAmt(String merRateType,String merRate,String merRateTop,int txamt) {
		int amt = 0;
		

		log.info("计算合作机构结算金额.");
		log.info("费率类型={}",merRateType);
		log.info("费率点={}",merRate);
		log.info("费率封顶={}",merRateTop);
		log.info("交易金额={} 分",txamt);
		try{
			Integer fee = 0;
			if (MsgST.RATE_LIVELIHOOD.equals(merRateType)) {
				log.info("计算手续费 类型为: 民生类"); 
				fee = MoneyUtils.calculate(merRate, txamt);
			} else if (MsgST.RATE_GENERAL.equals(merRateType)) {
				log.info("计算手续费 类型为: 一般类");
				fee = MoneyUtils.calculate(merRate, txamt);
			} else if (MsgST.RATE_ENTERTAIN.equals(merRateType)) {
				log.info("计算手续费 类型为: 餐娱类");
				fee = MoneyUtils.calculate(merRate, txamt);
			} else if (MsgST.RATE_GENERAL_TOP.equals(merRateType)) {
				log.info("计算手续费 类型为: 批发类");
				Integer maximun = NumberUtils.toInt(merRateTop);
				fee = MoneyUtils.calculate(merRate, txamt);
				if (fee > maximun) {
					fee = maximun;
				}
			} else if (MsgST.RATE_ENTERTAIN_TOP.equals(merRateType)) {
				log.info("计算手续费 类型为: 房产汽车类");
				Integer maximun = NumberUtils.toInt(merRateTop);
				fee = MoneyUtils.calculate(merRate, txamt);
				if (fee > maximun) {
					fee = maximun;
				}
			}
			amt = txamt - fee;
			log.info("费率类型为:" + merRateType + "  手续费为:" + MoneyUtils.toStrCent(fee) + " 元" 
						+ " 应结算金额:" +  MoneyUtils.toStrCent(amt) + " 元");
		}catch(Exception e){
			log.error("计算合作机构结算金额出错了！");
		}
		return amt ;
	}
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelledHandler(Map<String,Map<String,Object>> pmap) throws TranException {
		log.info("支付撤销完成, 订单更新中... 请求参数=[{}]",pmap);
		Map<String,Object> param =  pmap.get("param");
		Map<String,Object> prdordMap = pmap.get("prdordMap");
		Map<String,Object> termMap =  pmap.get("termMap");
		Map<String,Object> udata = pmap.get("udata");
		boolean termstatus = (Boolean) udata.get("termstatus");
		String exp =  (String) udata.get("exp");
		Map<String, Object> prdmap =null;
		Map<String, Object> paymap =null;
		String busType  = prdordMap.get("prdordtype").toString();
		String payordno = udata.get("payNo").toString();
		String ctype = "00";  //收款类型，预留接口
		
		try{
			log.info("1.更新交易流水.");		
			param.put("txnstatus", udata.get("txnstatus"));
			param.put("cause", udata.get("cause"));
			paymentJournalService.modifyPaymentJournal(param);
			
			if (MsgST.BANK_TRAN_OK.equals(udata.get("bankstatus").toString())) {
				log.info("2.更新原支付交易流水.");
				Map<String, Object> ornPayJnlMap = new HashMap<String, Object>();
				ornPayJnlMap.put("opayordno", payordno); //原交易流水号
				ornPayJnlMap.put("otxntype", "01"); //交易类型 01消费
				ornPayJnlMap = paymentJournalService.queryOrnPaymentJournal(ornPayJnlMap);
				ornPayJnlMap.put("txnstatus", udata.get("otxnstatus")); //已撤销
				paymentJournalService.modifyPaymentJournal(ornPayJnlMap);
				
				int payAmt     = Integer.valueOf(param.get("payAmt").toString());
				String tranTime=TdExpBasicFunctions.GETDATETIME();
				Map<String , Object> tranMap=new HashMap<String, Object>();
				tranMap.put("busType",  busType);
				tranMap.put("subBus",  prdordMap.get("bizType"));
				tranMap.put("payWay",   param.get("payType"));
				tranMap.put("agentId",  param.get("agentId"));
				tranMap.put("custId",  param.get("custId"));
				tranMap.put("custName",  udata.get("custName"));
				tranMap.put("terNo",  termMap.get("termNo"));
				tranMap.put("bankCardNo",  udata.get("cardNo"));
				tranMap.put("cardType",  udata.get("dcflag"));
				tranMap.put("tranAmt", payAmt);
				tranMap.put("tranYear", tranTime.substring(0, 4));
				tranMap.put("tranMonth", tranTime.substring(4,6));
				tranMap.put("tranDay",   tranTime.substring(6,8));
				tranMap.put("tranTime", tranTime.substring(8));
				tranMap.put("tranCode", udata.get("payNo"));
				tranMap.put("tranState", "1");
				tranMap.put("cooporgNo", param.get("orgNo"));
				tranMap.put("mid",   param.get("mid"));
				tranMap.put("mtype", param.get("mtype"));
				tranMap.put("merNo", param.get("TMercId"));
				tranMap.put("mterNo", param.get("TTermId"));
				tranMap.put("merRateType", param.get("TMerRateType"));
				tranMap.put("merRate", param.get("TMerRate"));
				tranMap.put("merRateTop", param.get("TMerRateTop"));
				tranMap.put("tranRateType", param.get("rateType"));
				tranMap.put("tranRate", param.get("rate"));
//				tranMap.put("tranRateTop", param.get(""));
				tranMap.put("thirdTranDate", param.get("CTxnDt"));
				tranMap.put("thirdTranTime", param.get("CTxnTm"));
				tranMap.put("merSettleAmt", "0");
				tranMap.put("tranFee", udata.get("fee"));
				tranMap.put("merFee", "0");
				tranMap.put("refno", param.get("SRefNo"));
				tranMap.put("prdno", param.get("prdordNo"));
				tranMap.put("tranSettleAmt", param.get("netrecamt"));
				log.info("写入交易记录流水:{}",tranMap);
				//登陆交易记录流水
				tranSerialRecordInfService.addTranSerialRecordInf(tranMap);
				
				//删除分润
				Map<String , Object> map=new HashMap<String, Object>();
				map.put("agentId", param.get("agentId"));
				map.put("payNo", udata.get("payNo"));
				profitSharingService.profitSharingCancel(map);
				
				log.info("3.恢复限额（订单信息、合作机构账户更新）.");
				rcsTransactionService.updateOrgTermTxnAmtRoll(param);
				
				log.info("4.更新商品订单.");
				prdmap = new HashMap<String, Object>();
				prdmap.put("ordstatus",udata.get("ordstatus"));
				prdmap.put("prdordNo", prdordMap.get("prdordno"));
				termPrdService.modifyPrd(prdmap);
				
				log.debug("udata=[{}]",udata);
				log.info("5.更新支付订单.");
				paymap = new HashMap<String, Object>();
				paymap.put("paystatus", udata.get("paystatus"));
				paymap.put("payordno",  payordno);
				paymap.put("prdordNo", prdordMap.get("prdordno"));
				paymap.put("ctype",  ctype);
				termPayService.modifyPay(paymap);
				
				log.info("6.扣除账户余额.");
				custAccountService.deductCustAccountBalanceByPay(param);
			}
		
		}catch(TranException e){
			//异常抛出
			log.error(e.getCode());
			throw new TranException(ExcepCode.EX000801);
		}
	}

}
