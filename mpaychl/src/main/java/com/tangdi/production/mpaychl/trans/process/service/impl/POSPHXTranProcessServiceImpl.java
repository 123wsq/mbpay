package com.tangdi.production.mpaychl.trans.process.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.base.service.EncryptorService;
import com.tangdi.production.mpaychl.base.service.TermService;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.constants.ThirdCode;
import com.tangdi.production.mpaychl.dao.AnotherPayDao;
import com.tangdi.production.mpaychl.trans.process.service.TranProcessService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpbase.util.TRANCODE;
import com.tangdi.production.mpbase.util.TranMap;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdbase.util.DesUtils;
import com.tangdi.production.tdcomm.channel.CallThrids;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.redoservice.TdRdoAtc;

/**
 * <b>POSP交易通道</b>交易[外发]渠道公共处理流程接口实现
 * 
 * @author chenlibo 2016/3/29
 *
 */
@SuppressWarnings("unused")
@Service
public class POSPHXTranProcessServiceImpl extends TranProcessService {
	private static Logger log = LoggerFactory
			.getLogger(POSPHXTranProcessServiceImpl.class);
	
	@Autowired
	private AnotherPayDao payDao;
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop;
	
	/**
	 * 支付
	 */
	@Override
	public Map<String, Object> pay(Map<String, Object> param)
			throws TranException {
		log.info("进入pay请求参数:{}",param);
		
		throw new TranException(ExcepCode.EX100301);
	}
	
	/**
	 * 微信扫码支付
	 */
	@Override
	public Map<String, Object> wxsmPay(Map<String, Object> param)
			throws TranException {
		log.info("进入wxsmPay请求参数:{}",param);
		
		throw new TranException(ExcepCode.EX100301);
	}
	
	/**
	 * 微信扫码查询
	 */
	@Override
	public Map<String, Object> wxsmQuery(Map<String, Object> param)
			throws TranException {
		log.info("进入wxsmQuery请求参数:{}",param);
		
		throw new TranException(ExcepCode.EX100301);
	}
	
	
	/**
	 * 银行卡余额查询
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param)
			throws TranException {
		log.info("进入query请求参数:{}",param);
		
		throw new TranException(ExcepCode.EX100301);
	}

	/**
	 * 收单机构或银行签到
	 */
	@Override
	public Map<String, Object> banksign(Map<String, Object> param)
			throws TranException {
		log.info("进入第三方终端签到...");
		
		throw new TranException(ExcepCode.EX100301);
	}

	/**
	 * 终端设备签到
	 */
	@Override
	public Map<String, Object> termsign(Map<String, Object> param)
			throws TranException {
		throw new TranException(ExcepCode.EX100301);
	}


	@Override
	public Map<String, Object> reverse(Map<String, Object> param)
			throws TranException {
		
		throw new TranException(ExcepCode.EX100301);
	}
	
	@Override
	public void redo(Map<String, Object> map) throws TranException {
		log.error("交易超时冲正处理,数据：{}", map);
		// 超时冲正处理
		Map<String, Object> data = dowithredomap(map);
		String txncod = TranCode.TRAN_RDO;   // 冲正交易代码
		String tlv = String.valueOf(5); // 5
		String payNo = map.get("payNo").toString();
		String maxTms= "5";
		// 写冲正数据
		log.info(
				"交易超时,支付订单[{}]写入冲正数据:[txncod={},maxTms={},logno={},tlv={},data={}]",
				payNo, txncod, maxTms, payNo, tlv,data.toString());
		TdRdoAtc.TranBeginWork(txncod, maxTms, payNo, tlv, data);
		log.info("冲正数据写入完成！");

	}
	/**
	 * 准备冲正数据
	 * 
	 * @param map
	 * @return
	 * @throws TranTimeoutException
	 */
	private Map<String, Object> dowithredomap(Map<String, Object> map)
			throws TranException {
		@SuppressWarnings("unchecked")
		Map<String,Object> dataPack = (Map<String, Object>) map.get("dataPack");
		// 冲正数据
		log.info("组冲正数据...");
		log.info("dataPack:{}",dataPack);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("txncd", "0400"); //消息类型
		data.put("AC_NO",  dataPack.get("AC_NO"));  // 主账号 2域
		data.put("TProCod", dataPack.get("TProCod")); // 交易代码 3域
		data.put("CTxnAt", dataPack.get("CTxnAt")); // 交易金额 4域
		data.put("TSeqNo", dataPack.get("TSeqNo")); // 受卡方系统跟踪号 11域
		data.put("InMod",  dataPack.get("InMod"));     // 服务点输入方式码 22域
		data.put("TPosCnd", dataPack.get("TPosCnd"));    // 服务点条件码 25域PosCnd
		data.put("AcqCod", dataPack.get("AcqCod"));    //32受理方标识码
		data.put("AutCod", dataPack.get("AutCod"));    // 授权标识应答码 38域AutCod
		data.put("TCPSCod",  map.get("RedoTCPSCod"));  //应答码 39域
		data.put("TTermId",  dataPack.get("TTermId")); // 41 受卡机终端标识码
		data.put("TMercId",  dataPack.get("TMercId")); // 42 受卡方标识码
		data.put("CcyCod", dataPack.get("CcyCod"));    // 49 TCcyCod
		data.put("TRsvDat", dataPack.get("TRsvDat"));  // 60 自定义域RsvDat
		
		if(String.valueOf(map.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			data.put("ExpDat", dataPack.get("ExpDat"));    //14域 有效期  | 同原交易
			data.put("CrdSqn", dataPack.get("CrdSqn"));     //23域 卡片序列号  | 同原交易
			data.put("ICDat",  dataPack.get("ICDat"));      //55域 ICDATA  | 同原交易
		}
		data.put("mediaType", map.get("mediaType")); //卡介质类型
		data.put("payNo",   map.get("payNo"));       //支付流水
		data.put("prdordNo", map.get("prdordNo"));   //商品订单流水
		data.put("agentId", map.get("agentId"));     //代理商编号
		data.put("termNo",  map.get("termNo"));      //终端编号
		data.put("orgNo",   map.get("orgNo"));       //合作机构
		//data.put("batchNo", map.get("batchNo"));  //批次号
		data.put("firstAgentId", map.get("firstAgentId"));//一级代理商
		data.put("rateType", map.get("rateType"));
		data.put("payAmt", map.get("payAmt"));
		log.info("组冲正数据完成.");
		return data;
	}


	@Override
	public Map<String, Object> casPay(Map<String, Object> param) throws TranException {
		//进入实时代付
		log.info("1进入casPay请求参数:{}",param);
		String orgNo = param.get("orgNo").toString();
		//定义处理结果Map
	    Map<String,Object> result = new HashMap<String,Object>(); 		

		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("RcvBankCode",  param.get("issno")); 
		dataPack.put("RcvBankCodeName",  param.get("issnam")); 
		dataPack.put("OrgNo","SHUA00000000001");  //无用必输项   
		dataPack.put("TxnAmt",  String.valueOf(param.get("netrecamt")));  //代付金额
		dataPack.put("TraceNo", getPosSEQ());
		dataPack.put("RcvAcct", 	param.get("cardNo")); //收款账号
		dataPack.put("RcvAcctName", param.get("custName"));  //收款户名
		dataPack.put("TTermId", param.get("TTermId")); 
		dataPack.put("TMercId", param.get("TMercId"));
		String dateTime = DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss");
		dataPack.put("TranDate",  dateTime.substring(0, 8)); 
		dataPack.put("TranTime",  dateTime.substring(8)); 
		dataPack.put("TranDateTime",  dateTime); 
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("ErrCode");
		
		//代付流水表所需数据
		Map<String,Object> susMap = new HashMap<String,Object>();
		susMap.put("tmercid", param.get("TMercId"));//第三方商户号
		susMap.put("ttermid", param.get("TTermId"));//第三方终端号
		susMap.put("txndate", dateTime.substring(0, 8));//交易日期
		susMap.put("txnTime", dateTime.substring(8));//交易日期
		susMap.put("tlogno", dataPack.get("TraceNo"));//交易流水号
		susMap.put("txnactno", param.get("cardNo"));//交易卡号
		susMap.put("txnamt", param.get("netrecamt"));//交易金额
		susMap.put("paymentId", param.get("anoPid"));//提现流水号
		susMap.put("payOrdNo", param.get("casOrdNo"));//提现订单号
		
		result.put("tseqNo", dataPack.get("TraceNo"));
		result.put("payTime", dataPack.get("TranDateTime"));
		result.put("filed3", tranMap.get("RouteId"));//POSP外发通道ID
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TCAS_OK.equals(TCPSCod)){
			//插入代付流水表
			susMap.put("txnstatus", "01");//代付状态--成功
			try {
				payDao.insertAnotherPay(susMap);
			} catch (Exception e) {
				log.info("插入代付流水表失败！");
				e.printStackTrace();
			}
			//交易成功后处理
			result.put("TCPSCod", "00"); 
			result.putAll(param);
			result.put("tseqNo",  dataPack.get("TraceNo"));
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(tranMap.getValue("RETCODE"))){
			//插入代付流水表
			susMap.put("txnstatus", "02");//代付状态--失败
			try {
				payDao.insertAnotherPay(susMap);
			} catch (Exception e) {
				log.info("插入代付流水表失败！");
				e.printStackTrace();
			}
			result.put("TCPSCod", "tt");//代付通道返回超时
		} else{
			if(TCPSCod == null || 
					"".equals(TCPSCod)||
					"ERR4008".equals(TCPSCod)||
					"ERRFFFF".equals(TCPSCod)||
					"ERREEEE".equals(TCPSCod)||
					"ERR0016".equals(TCPSCod)){
				//插入代付流水表
				susMap.put("txnstatus", "03");//代付状态--可疑
				try {
					payDao.insertAnotherPay(susMap);
				} catch (Exception e) {
					log.info("插入代付流水表失败！");
					e.printStackTrace();
				}
				result.put("TCPSCod", "98");//代付状态未明
			}else{
				result.put("TCPSCod", "f0");//代付失败后，状态需改为 待清算（代付失败）
			}
		}
		log.info("响应数据:{}",result);
		return result;
	}

	@Override
	public Map<String, Object> conCasPay(Map<String, Object> param) throws TranException {
		//进入确认实时代付
		log.info("进入conCasPay请求参数:{}",param);
		String orgNo = param.get("orgNo").toString();
		//定义处理结果Map
	    Map<String,Object> result = new HashMap<String,Object>();

		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("OrgNo","SHUA00000000001");  //无用必输项  
		log.info("原金额：{}",param.get("payAmt"));
		dataPack.put("TxnAmt", param.get("payAmt"));
		dataPack.put("TraceNo", getPosSEQ());     // 交易流水号 
		dataPack.put("TTermId", param.get("TTermId")); 
		dataPack.put("TMercId", param.get("TMercId"));
		String dateTime = DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss");
		dataPack.put("TranDate",  dateTime.substring(0, 8)); 
		dataPack.put("TranTime",  dateTime.substring(8)); 
		dataPack.put("TranDateTime",  dateTime); 
		dataPack.put("OrgTranDate",  param.get("txnDate")); 
		dataPack.put("OrgTraceNo",  param.get("TLogNo")); 
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("ErrCode");
		String OTCPSCod = tranMap.getValue("OrgErrorCode");
		Map<String,Object> map = new HashMap<String,Object>(); 		
		map.put("tlogno", getPosSEQ());
		map.put("payOrdNo", param.get("casOrdNo"));
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TCAS_OK.equals(TCPSCod) && 
				MsgSubST.BANK_TCAS_OK.equals(OTCPSCod)){
			//交易成功后处理
			//result.put("SRefNo",  tranMap.getValue("SRefNo"));
			result.put("TCPSCod", "00");  //39域 应答码
			//更新代付流水表的代付状态
			map.put("txnstatus", "01");
			try {
				payDao.updateAnotherPay(map);
			} catch (Exception e) {
				log.info("更新代付流水表代付状态失败！");
				e.printStackTrace();
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(tranMap.getValue("RETCODE")) ||
				TCPSCod == null || 
				OTCPSCod == null ||
				!MsgSubST.BANK_TCAS_OK.equals(TCPSCod) ||
				(
						MsgSubST.BANK_TCAS_OK.equals(TCPSCod) && 
						("ERRFFFF".equals(OTCPSCod) || 
								"ERREEEE".equals(OTCPSCod) ||
								"ERR4002".equals(OTCPSCod) ||
								"ERR4004".equals(OTCPSCod)
						)
				)){
			result.put("TCPSCod", "02");
		}else{
			//更新代付流水表的代付状态
			map.put("txnstatus", "02");
			try {
				payDao.updateAnotherPay(map);
			} catch (Exception e) {
				log.info("更新代付流水表代付状态失败！");
				e.printStackTrace();
			}
			result.put("TCPSCod", "01");
		}
		log.info("响应数据:{}",result);
		return result;
	}

	@Override
	public Map<String, Object> tranCancel(Map<String, Object> param)
			throws TranException {
		// TODO 自动生成的方法存根
		throw new TranException(ExcepCode.EX100301);
	}
	
	public Map<String, Object> limitQuery(Map<String, Object> param) throws TranException {
		//进入额度查询
		log.info("1进入limitQuery请求参数:{}",param);
		throw new TranException(ExcepCode.EX100301);
	}
	
	
}
