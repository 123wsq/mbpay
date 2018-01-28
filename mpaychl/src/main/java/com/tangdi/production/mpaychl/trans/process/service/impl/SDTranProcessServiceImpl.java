package com.tangdi.production.mpaychl.trans.process.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpbase.util.TRANCODE;
import com.tangdi.production.mpbase.util.TranMap;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdbase.util.DesUtils;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.channel.CallThrids;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.redoservice.TdRdoAtc;

/**
 * 手刷接收单实现类
 * 
 * @author youdd 2016/03/30
 *
 */
@SuppressWarnings("unused")
@Service
public class SDTranProcessServiceImpl extends TranProcessService {
	private static Logger log = LoggerFactory
			.getLogger(SDTranProcessServiceImpl.class);
	
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
		log.info("进入收单pay请求参数:{}",param);
		Map<String,Object> dataPack = new HashMap<String,Object>();
		//城市编码
		String cityId = null;
		try {
			String address = param.get("address").toString(); 
			cityId = payDao.pmctycodCitId(address);
		} catch (Exception e1) {
			log.debug("地域代码",e1);
		}
		//String batchNo = param.get("batchNo").toString();    //获取批次号
		String batchNo = "000001";    //获取批次号
		String seqNo = getPosSEQ();							 //流水号
		String orgNo = param.get("orgNo").toString();        //机构号
		String Payordno = param.get("payNo").toString();     //获取支付订单号收单需要
		
		dataPack.put("MsgId", param.get("txncd"));           //    消息类型
		dataPack.put("AC_NO", param.get("cardNo"));          // 2域 主账号
		dataPack.put("TProCod", "000000");                   // 3域  000000 消费固定
		dataPack.put("CTxnAt", param.get("payAmt"));         // 4域  交易金额
		dataPack.put("TSeqNo",seqNo);                        //11域 POS终端交易流水号
		dataPack.put("InMod", "021");                        //22域 服务点输入方式码
		dataPack.put("TPosCnd", "00");                       //25域 服务点条件码
		dataPack.put("PosPin", "06");                        //26域 服务点PIN获取码 12
		dataPack.put("AcqCod", orgNo);                       //32受理方标识码
		dataPack.put("Track2",  param.get("track2"));        //35域 2磁道
		dataPack.put("TTermId", param.get("terminalId"));    //41域 终端代码
		dataPack.put("TMercId", param.get("custId"));        //42域 商户代码
		dataPack.put("CcyCod", "156");                       //49域 货币代码
		dataPack.put("TPinBlk", param.get("pinblk"));        //52域 密码
		dataPack.put("SecInf", "2600000000000000");          //53域 
		dataPack.put("TRsvDat", "22"+batchNo+"000500");      //60域 自定义域
		dataPack.put("TSwtDat", 
				"99"+StringUtils.rightPad(cityId, 4, "F")
				//+StringUtils.rightPad(String.valueOf(param.get("agentId")), 15, "F") 
				+String.valueOf(param.get("agentId_three")) //heguo modify 20160503
				+StringUtils.leftPad(String.valueOf(param.get("fee")), 12, "0"));   //62域 99+地区码 收单需要
		
		//IC卡
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			dataPack.put("InMod", "051");                            //22域 服务点输入方式码
			dataPack.put("ExpDat", param.get("period"));             //14域 有效期
			dataPack.put("CrdSqn", param.get("crdnum"));           	 //23域 卡片序列号 param.get("seqnum")
			dataPack.put("ICDat",  param.get("icdata"));             //55域 ICDATA
			dataPack.put("TRsvDat", "22"+batchNo+"00050");    	     //60域 自定义域
		}
		
		//param.put("rtrsvr", "STHDSD");
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		log.info("渠道码："+param.get("rtrsvr"));
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",tranMap);
		/**更新商品订单*/
		Map<String, Object> prdMap = new HashMap<String, Object>();
		prdMap.put("Payordno", Payordno);//支付订单号
		prdMap.put("TMercId", rmap.get("TMercId"));//第三方商户号
		prdMap.put("TTermId", rmap.get("TTermId"));//第三方终端号
		prdMap.put("AcqCod", rmap.get("AcqCod"));//授权码
		prdMap.put("TSeqNo", rmap.get("TSeqNo"));//交易流水
		
		if(rmap.get("TCPSCod")!=null && "00".equals(rmap.get("TCPSCod"))){
			prdMap.put("TxnRateType", rmap.get("TSwtDat").toString().substring(0, 1));//费率类型
			prdMap.put("TxnRate", Double.parseDouble(rmap.get("TSwtDat").toString().substring(1, 4))/1000+"");//费率
			prdMap.put("TxnFee", Integer.parseInt(rmap.get("TSwtDat").toString().substring(4, 19))+"");//手续费
			prdMap.put("TMercName", toStringHex(rmap.get("TSwtDat").toString().substring(19)));//大商户名称
		}
		tranMap.put("TMercName",prdMap.get("TMercName"));
		tranMap.put("batchNo",batchNo);
		log.debug("电子小票应有的数据:{}",tranMap);
		param.putAll(tranMap);
		try {
			payDao.updatePrdInf(prdMap);
		} catch (Exception e) {
			log.debug("更新商品订单数据失败",e);
		}
		String rspcod = tranMap.getValue("RETCODE");
		param.put("TCPSCod", tranMap.getValue("TCPSCod"));
		param.put("payChannel", "P"+tranMap.get("F30"));
		if (MsgSubST.RETCODE_OK.equals(rspcod)) {
			// 返回码
			String TCPSCod = tranMap.getValue("TCPSCod");
			if (MsgSubST.BANK_TRAN_OK.equals(TCPSCod)) {
				
				log.info("交易成功");
			} else {
				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
				log.error("交易失败:错误信息[{}]", prop.get("CPSCODE_" + tranErrorCode));
				if(EXMAP.containsKey(tranErrorCode)){
					throw EXMAP.get(tranErrorCode);
				}else{
					throw new TranException(ExcepCode.EX100301);
				} 
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		return null;
	}
	
	/**
	 * 微信扫码支付
	 */
	@Override
	public Map<String, Object> wxsmPay(Map<String, Object> param)
			throws TranException {
		log.info("进入收单wxsmPay请求参数:{}",param);
		Map<String,Object> dataPack = new HashMap<String,Object>();
		//城市编码
		String cityId = null;
		try {
			String address = param.get("address").toString(); 
			cityId = payDao.pmctycodCitId(address);
		} catch (Exception e1) {
			log.debug("地域代码",e1);
		}
		
		String batchNo = "000001";    //获取批次号
		String seqNo = getPosSEQ();							 //流水号
		String orgNo = param.get("orgNo").toString();        //机构号
		String Payordno = param.get("payNo").toString();     //获取支付订单号收单需要
		String prdordno = param.get("prdordNo").toString();  //获取支付订单号收单需要
		
		dataPack.put("MsgId", "0700");                       // 消息类型
		dataPack.put("TProCod", "180000");                   // 3域  180000
		dataPack.put("CTxnAt", param.get("payAmt"));         // 4域  交易金额
		dataPack.put("TSeqNo",seqNo);                        //11域 POS终端交易流水号
		dataPack.put("TPosCnd", "00");                       //25域 服务点条件码
		dataPack.put("AcqCod", orgNo);                       //32受理方标识码
		dataPack.put("F34", prdordno);                       //34订单号
		dataPack.put("TTermId", param.get("terminalId"));    //41域 终端代码
		dataPack.put("TMercId", param.get("custId"));        //42域 商户代码
		dataPack.put("CcyCod", "156");                       //49域 货币代码
		dataPack.put("TSwtDat", 
				"99"+StringUtils.rightPad(cityId, 4, "F")
				//+StringUtils.rightPad(String.valueOf(param.get("agentId")), 15, "F") 
				+String.valueOf(param.get("agentId_three")) //heguo modify 20160503
				+StringUtils.leftPad(String.valueOf(param.get("fee")), 12, "0"));   //62域 99+地区码 收单需要
		
		Map<String,Object> busMap = new HashMap<String, Object>();
		busMap.put("trade_type", "10000");  //交易类型标识
		busMap.put("randomKey", TdExpBasicFunctions.GETDATETIME());  //随机数
		busMap.put("notifyUrl", param.get("sMJGPath").toString());  //通知地址
		busMap.put("userName", param.get("custName").toString());  //充值用户
		busMap.put("userPhone", param.get("custMobile").toString());  //充值手机号
		busMap.put("creat_ip", param.get("sMJGPath").toString().substring(7, 
				param.get("sMJGPath").toString().lastIndexOf(":")));  //客户端连接IP
		busMap.put("identityType", "00"); //身份证 
		busMap.put("identityNo", param.get("certNo"));
		dataPack.put("DetInq", JUtil.toJsonString(busMap));  //59域 业务信息  
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		log.info("渠道码："+param.get("rtrsvr"));
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",tranMap);
		/**更新商品订单*/
		Map<String, Object> prdMap = new HashMap<String, Object>();
		prdMap.put("Payordno", Payordno);//支付订单号
		prdMap.put("TMercId", rmap.get("TMercId"));//第三方商户号
		prdMap.put("TTermId", rmap.get("TTermId"));//第三方终端号
		prdMap.put("TSeqNo", rmap.get("TSeqNo"));//交易流水
		
		String returnCode = "";
		String returnMsg = "";
		String payUrl = "";
		Map<String,Object> detMap = null;
		
		if(rmap.get("TCPSCod")!=null && "00".equals(rmap.get("TCPSCod")) && rmap.get("DetInq") != null){
			try {
				detMap = JUtil.toMap(rmap.get("DetInq").toString());
				System.out.println(rmap.get("DetInq").toString());
			} catch (Exception e) {
				log.debug("DetInq Parse ERROR{}",rmap.get("DetInq").toString());
			}
			if(detMap.get("returnCode") != null && "0000".equals(detMap.get("returnCode"))){
				returnCode = detMap.get("returnCode").toString();
				//returnMsg = detMap.get("returnMsg").toString();
				payUrl = detMap.get("payUrl").toString();
			}
		}
		tranMap.put("batchNo",batchNo);
		log.debug("电子小票应有的数据:{}",tranMap);
		param.put("payData", payUrl); //扫码支付二维码数据
		param.putAll(tranMap);
		try {
			payDao.updatePrdInf(prdMap);
		} catch (Exception e) {
			log.debug("更新商品订单数据失败",e);
		}
		String rspcod = tranMap.getValue("RETCODE");
		param.put("TCPSCod", tranMap.getValue("TCPSCod"));
		if (MsgSubST.RETCODE_OK.equals(rspcod)) {
			// 返回码
			String TCPSCod = tranMap.getValue("TCPSCod");
			if (MsgSubST.BANK_TRAN_OK.equals(TCPSCod) && "0000".equals(returnCode)) {
				
				log.info("交易成功");
			} else {
				if(!MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
					String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
					log.error("交易失败:错误信息[{}]", prop.get("CPSCODE_" + tranErrorCode));
					if(EXMAP.containsKey(tranErrorCode)){
						throw EXMAP.get(tranErrorCode);
					}else{
						throw new TranException(ExcepCode.EX100301);
					}
				}else{
					log.error("交易失败:错误信息[{}{}]", returnCode, returnMsg);
					throw new TranException(ExcepCode.EX100301);
				}
				 
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		return null;
	}
	
	/**
	 * 微信扫码查询
	 */
	@Override
	public Map<String, Object> wxsmQuery(Map<String, Object> param)
			throws TranException {
		log.info("进入收单wxsmQuery请求参数:{}",param);
		Map<String,Object> dataPack = new HashMap<String,Object>();
		//城市编码
		String cityId = null;
		try {
			String address = "上海市";//param.get("address").toString(); 
			cityId = payDao.pmctycodCitId(address);
		} catch (Exception e1) {
			log.debug("地域代码",e1);
		}
		
		String batchNo = "000001";    //获取批次号
		String seqNo = getPosSEQ();							 //流水号
		String orgNo = param.get("orgNo").toString();        //机构号
		String Payordno = param.get("payNo").toString();     //获取支付订单号收单需要
		String prdordno = param.get("prdordNo").toString();  //获取支付订单号收单需要
		
		dataPack.put("MsgId", "0700");                       // 消息类型
		dataPack.put("TProCod", "180010");                   // 3域  180010
		dataPack.put("CTxnAt", param.get("payAmt"));         // 4域  交易金额
		dataPack.put("TSeqNo",seqNo);                        //11域 POS终端交易流水号
		dataPack.put("TPosCnd", "00");                       //25域 服务点条件码
		dataPack.put("AcqCod", orgNo);                       //32受理方标识码
		dataPack.put("F34", prdordno);                       //34订单号
		dataPack.put("TTermId", param.get("terminalId"));         //41域 终端代码
		dataPack.put("TMercId", param.get("custId"));        //42域 商户代码
		dataPack.put("CcyCod", "156");                       //49域 货币代码
		dataPack.put("TSwtDat", 
				"99"+StringUtils.rightPad(cityId, 4, "F")
				//+StringUtils.rightPad(String.valueOf(param.get("agentId")), 15, "F") 
				+String.valueOf(param.get("agentId_three")) //heguo modify 20160503
				+StringUtils.leftPad(String.valueOf(param.get("fee")), 12, "0"));   //62域 99+地区码 收单需要
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		log.info("渠道码："+param.get("rtrsvr"));
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",tranMap);
		/**更新商品订单*/
		Map<String, Object> prdMap = new HashMap<String, Object>();
		prdMap.put("Payordno", Payordno);//支付订单号
		prdMap.put("TMercId", rmap.get("TMercId"));//第三方商户号
		prdMap.put("TTermId", rmap.get("TTermId"));//第三方终端号
		prdMap.put("TSeqNo", rmap.get("TSeqNo"));//交易流水
		
		if(rmap.get("TCPSCod")!=null && "00".equals(rmap.get("TCPSCod"))){
			
		}
		tranMap.put("batchNo",batchNo);
		log.debug("电子小票应有的数据:{}",tranMap);
		param.putAll(tranMap);
		try {
			payDao.updatePrdInf(prdMap);
		} catch (Exception e) {
			log.debug("更新商品订单数据失败",e);
		}
		String rspcod = tranMap.getValue("RETCODE");
		param.put("TCPSCod", tranMap.getValue("TCPSCod"));
		if (MsgSubST.RETCODE_OK.equals(rspcod)) {
			// 返回码
			String TCPSCod = tranMap.getValue("TCPSCod");
			if (MsgSubST.BANK_TRAN_OK.equals(TCPSCod)) {
				
				log.info("交易成功");
			} else if(MsgSubST.BANK_TRAN_PROCESSING.equals(TCPSCod)){
				log.info("交易处理中");
			} else {
				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
				log.error("交易失败:错误信息[{}]", prop.get("CPSCODE_" + tranErrorCode));
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		return null;
	}
	
	/**
	 * 银行卡余额查询
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param)
			throws TranException {
		log.info("进入收单query请求参数:{}",param);
		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		//定义处理结果Map
		Map<String,Object> result = new HashMap<String,Object>(); 		
		
		//城市编码
				String cityId = null;
				try {
					String address = param.get("address").toString(); 
					cityId = payDao.pmctycodCitId(address);
					if(cityId.length()==3)
						cityId = cityId+"0";
				} catch (Exception e1) {
					log.debug("地域代码",e1);
				}
		String orgNo = param.get("orgNo").toString();           //获取合作机构
		String batchNo = param.get("batchNo").toString();       //获取批次号
		 
		dataPack.put("MsgId", param.get("txncd"));              // 消息类型
		dataPack.put("AC_NO", param.get("cardNo"));         	// 2域 主账号
		dataPack.put("TProCod", "310000");                      // 3域  310000 余额查询固定
		dataPack.put("TSeqNo",getPosSEQ());                     //11域 POS终端交易流水号
		dataPack.put("InMod", "021");                           //22域 服务点输入方式码
		dataPack.put("TPosCnd", "00");                          //25域 服务点条件码
		dataPack.put("PosPin", "12");                           //26域 服务点PIN获取码
		dataPack.put("AcqCod", orgNo);                          //32受理方标识码
		dataPack.put("Track2",  param.get("track2"));           //35域 2磁道
		dataPack.put("TTermId", param.get("terminalId"));       //41域 终端代码
		dataPack.put("TMercId", param.get("custId"));           //42域 商户代码
		dataPack.put("CcyCod", "156");                          //49域 货币代码
		dataPack.put("TPinBlk", param.get("pinblk"));           //52域 密码
		dataPack.put("SecInf", "2600000000000000");             //53域 
		dataPack.put("BatchNo", batchNo);                       //56域 批次号
		dataPack.put("TRsvDat", "01"+batchNo+"000500");         //60域 自定义域
		dataPack.put("TSwtDat", "99"+cityId+param.get("agentId"));   //62域 99+地区码 收单需要
		
		//IC卡
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			dataPack.put("InMod", "051");                             //22域 服务点输入方式码
			dataPack.put("ExpDat", param.get("period"));              //14域 有效期
			dataPack.put("CrdSqn", param.get("crdnum"));              //23域 卡片序列号 
			dataPack.put("ICDat",  param.get("icdata"));              //55域 ICDATA
			dataPack.put("TRsvDat", "01"+batchNo+"000500");           //60域 自定义域
		}
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		
		param.putAll(tranMap);
		
		String rspcod = tranMap.getValue("RETCODE");
		if(MsgSubST.RETCODE_OK.equals(rspcod)){
			String TCPSCod = tranMap.getValue("TCPSCod");
			if(MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
				log.info("收单查询余额成功！");
				result.put("balance",  tranMap.getValue("BalInf").substring(8)); // 返回金额
			}else{
				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+tranErrorCode));
				if(EXMAP.containsKey(tranErrorCode)){
					throw EXMAP.get(tranErrorCode);
				}else{
					throw new TranException(ExcepCode.EX100301);
				} 
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		log.info("响应数据:{}",result);
		param.putAll(result);
		String balance = String.valueOf(result.get("balance"));
		balance = balance.substring(0,12); 
		balance = balance.replaceFirst("^0*", "");
		result.put("balance", balance);
		return result;
	}


	@Override
	public Map<String, Object> reverse(Map<String, Object> param) throws TranException {
		return null;
	}


	@Override
	public void redo(Map<String, Object> param) throws TranException {
		
	}


	@Override
	public Map<String, Object> casPay(Map<String, Object> param) throws TranException {
		return null;
	}


	@Override
	public Map<String, Object> conCasPay(Map<String, Object> param) throws TranException {
		return null;
	}


	@Override
	public Map<String, Object> limitQuery(Map<String, Object> param) throws TranException {
		return null;
	}


	@Override
	public Map<String, Object> banksign(Map<String, Object> param) throws TranException {
		return null;
	}


	@Override
	public Map<String, Object> termsign(Map<String, Object> param) throws TranException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, Object> tranCancel(Map<String, Object> param) throws TranException {
		// TODO Auto-generated method stub
		return null;
	}
    //转化十六进制编码为字符串方法
	 public static String toStringHex(String s)
	    {
	     byte[] baKeyword = new byte[s.length()/2];
	      for(int i = 0; i < baKeyword.length; i++)
	      {
	       try
	       {
	        baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
	       }
	       catch(Exception e)
	       {
	        e.printStackTrace();
	       }
	      }
	      
	     try 
	     {
	      s = new String(baKeyword, "utf-8");//UTF-16le:Not
	     } 
	     catch (Exception e1) 
	     {
	      e1.printStackTrace();
	     } 
	     return s;
	    } 
	
}
