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
 * <b>安汇捷收单通道</b>交易[外发]渠道公共处理流程接口实现
 * 
 * @author zhengqiang 2015/8/04
 *
 */
@SuppressWarnings("unused")
@Service
public class HXTranProcessServiceImpl extends TranProcessService {
	private static Logger log = LoggerFactory
			.getLogger(HXTranProcessServiceImpl.class);
	
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

		//2. PIN转加密
		String pinblock =  convertPIN(param);
		//获取批次号
		String batchNo = param.get("batchNo").toString();
		String seqNo = getPosSEQ();
		//机构号
		String orgNo = param.get("orgNo").toString();
		//获取支付订单号收单需要
		String Payordno = param.get("payNo").toString();
		
		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
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
		dataPack.put("TTermId", param.get("TTermId"));       //41域 终端代码
		dataPack.put("TMercId", param.get("TMercId"));       //42域 商户代码
		dataPack.put("CcyCod", "156");                       //49域 货币代码
		dataPack.put("TPinBlk", pinblock);                    //52域 密码
		dataPack.put("SecInf", "2600000000000000");        //53域 
		dataPack.put("TRsvDat", "22"+batchNo+"000500");    //60域 自定义域
		
		String cityId = null;
		try {
			String address = param.get("address").toString(); 
			cityId = payDao.pmctycodCitId(address);
			if(cityId.length()==3)
				cityId = cityId+"0";
		} catch (Exception e1) {
			log.debug("地域代码",e1);
		}
		
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			dataPack.put("InMod", "051");                                   //22域 服务点输入方式码
			dataPack.put("ExpDat", param.get("period"));             //14域 有效期
			dataPack.put("CrdSqn", param.get("crdnum"));           //23域 卡片序列号 param.get("seqnum")
			dataPack.put("ICDat",  param.get("icdata"));                //55域 ICDATA
			dataPack.put("TRsvDat", "22"+batchNo+"00050");    //60域 自定义域
		}
		
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		//MAC密钥
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);	
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",tranMap);
		
		//设置返回值
		param.put("batchNo", batchNo);//设置冲正需要的batchNo
		param.put("posSeqNo", seqNo);
		param.put("dataPack", dataPack);
		param.put("redo", "0");  //冲正状态
		param.putAll(tranMap);
		
		String rspcod = tranMap.getValue("RETCODE");
		if (MsgSubST.RETCODE_OK.equals(rspcod)) {
			// 渠道错误码转换
			String TCPSCod = TRANCODE.GET(orgNo, tranMap.getValue("TCPSCod"));
			param.put("cause", prop.get("CPSCODE_" + TCPSCod));
			if (MsgSubST.BANK_TRAN_OK.equals(TCPSCod)) {
				// 交易成功后处理 校验MAC
				String remoteMac = tranMap.getValue("MAC");
				String allMessage = tranMap.getValue("allMessage").replace(
						remoteMac, "");
				Map<String, Object> macMap = new HashMap<String, Object>();
				macMap.put("MesDat", allMessage);
				macMap.put("MACKEY", param.get("MACKEY"));
				macMap.put("MACMODE", tranMap.get("MACMODE"));
				String localMac = getEncryptorService().getMac(macMap);
				localMac = DesUtils.toHexString(localMac.substring(0, 8));
				log.info("截取前8位转16进制,取得MAC=[{}]。", localMac);
				param.remove("MesDat"); // 删除节点
				param.remove("allMessage");// 删除节点
				log.info("通道MAC={},本地MAC={}", remoteMac, remoteMac);
				if (!remoteMac.equals(localMac)) {
					// 发送冲正
					param.put("RedoTCPSCod", "A0");
					redo(param); // 冲正处理 MAC校验失败
					param.put("redo", "1");
					throw new TranException(ExcepCode.EX100301,
							"MAC校验[NG]!");
				}
				log.info("MAC校验[OK].");
			} else {
				if (TCPSCod.equals("68") || TCPSCod.equals("98")) {
					param.put("RedoTCPSCod", "98");
					redo(param); // 冲正处理 第三方系统发送超时 或 发卡方超时
					param.put("redo", "1");
				}
				log.error("交易失败:错误信息[{}]", prop.get("CPSCODE_" + TCPSCod));
				if (EXMAP.containsKey(TCPSCod)) {
					throw EXMAP.get(TCPSCod);
				} else {
					throw new TranException(ExcepCode.EX100301);
				}
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			redo(param); //冲正处理  接收第三方系统数据超时
			param.put("redo", "1");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			redo(param); //冲正处理  读超时
			param.put("redo", "1");
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
		//定义处理结果Map
		Map<String,Object> result = new HashMap<String,Object>(); 		
		
		String pinblock =  convertPIN(param);
		
		 //获取合作机构
		String orgNo = param.get("orgNo").toString();
		//获取批次号
		String batchNo = param.get("batchNo").toString();
		 
		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("MsgId", param.get("txncd"));              //       消息类型
		dataPack.put("AC_NO", param.get("cardNo"));         // 2域 主账号
		
		dataPack.put("TProCod", "310000");                       //  3域  310000 余额查询固定
		dataPack.put("TSeqNo",getPosSEQ());                              //11域 POS终端交易流水号
		dataPack.put("InMod", "021");                                //22域 服务点输入方式码
		dataPack.put("TPosCnd", "00");                               //25域 服务点条件码
		dataPack.put("PosPin", "12");                                  //26域 服务点PIN获取码
		dataPack.put("AcqCod", orgNo);                         //32受理方标识码
		dataPack.put("Track2",  param.get("track2"));          //35域 2磁道
		dataPack.put("TTermId", param.get("TTermId"));      //41域 终端代码
		dataPack.put("TMercId", param.get("TMercId"));      //42域 商户代码
		dataPack.put("CcyCod", "156");                               //49域 货币代码
		dataPack.put("TPinBlk", pinblock);                            //52域 密码
		dataPack.put("SecInf", "2600000000000000");       //53域 
		
		dataPack.put("BatchNo", batchNo);                              //56域 批次号
		dataPack.put("TRsvDat", "01"+batchNo+"000500");    //60域 自定义域
		
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			dataPack.put("InMod", "051");                                    //22域 服务点输入方式码
			dataPack.put("ExpDat", param.get("period"));              //14域 有效期
			dataPack.put("CrdSqn", param.get("crdnum"));           //23域 卡片序列号 
			dataPack.put("ICDat",  param.get("icdata"));                //55域 ICDATA
			dataPack.put("TRsvDat", "01"+batchNo+"000500");     //60域 自定义域
		}
		
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		param.putAll(tranMap);
		log.debug("第三方交易响应数据:{}",rmap);
		String rspcod = tranMap.getValue("RETCODE");
		if(MsgSubST.RETCODE_OK.equals(rspcod)){
			String TCPSCod = tranMap.getValue("TCPSCod");
			if(MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
				//交易成功后处理
				//设置处理结果到result中
				String remoteMac  = tranMap.getValue("MAC");
				String allMessage = tranMap.getValue("allMessage").replace(remoteMac, "");
				Map<String,Object> macMap = new HashMap<String,Object>();
				macMap.put("MesDat", allMessage);
				macMap.put("MACKEY", param.get("MACKEY"));
				macMap.put("MACMODE", tranMap.get("MACMODE"));
				String localMac = getEncryptorService().getMac(macMap);
				localMac = DesUtils.toHexString(localMac.substring(0, 8));
				log.info("截取前8位转16进制,取得MAC=[{}]。",localMac);
				param.remove("MesDat");    //删除节点
				param.remove("allMessage");//删除节点
				log.info("通道MAC={},本地MAC={}",remoteMac,localMac);
				if(!remoteMac.equals(localMac)){
					throw new TranException(ExcepCode.EX100301,"MAC校验[NG]!");
				}
				log.info("MAC校验[OK].");
				result.put("balance",  tranMap.getValue("BalInf").substring(8));   //54域 受卡方所在地时间
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

	/**
	 * 收单机构或银行签到
	 */
	@Override
	public Map<String, Object> banksign(Map<String, Object> param)
			throws TranException {
		log.info("进入第三方终端签到...");
		Map<String,Object> result = null;
		String orgNo = param.get("orgNo").toString();
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("MsgId",  "0800");               //签到消息类型
		dataPack.put("TSeqNo",getPosSEQ());           //11域 POS终端交易流水号
	  //dataPack.put("AcqCod", orgNo);           //32受理方标识码
		dataPack.put("TTermId", param.get("terno"));  //41域 终端代码
		dataPack.put("TMercId", param.get("merno"));  //42域 商户代码
		dataPack.put("TRsvDat", "00"+"000006"+"003");       //60 自定义域
		dataPack.put("NetDat",  "010");               //63 自定义域
		//57固定域
		//dataPack.put("AddDat", "00V2014061001         0000000000F42BCF8ED7F1130EF42BCF8ED7F1130E"); 
		
		dataPack.put(MessageConstants.GETMACFLAG,"1");
		
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String rspcod = tranMap.getValue("TCPSCod");
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(rspcod)){
			result = new HashMap<String,Object>();
			result.put("BATCHNO", "000001");  //汇竹收单通道:批次号固定[000001]
			 
			String key = tranMap.getValue("TSwtDat").toString();
			if(key.length() == 80){
				log.info("签到获取KEY=[{}]",key);
				
				
				Map<String,Object> paramMap = null;
				Map<String,Object> encryMap = null;  //加密机返回结果
				String  pinkeyCheckValue = null; //通道下方的pinkey效验值
				String lpinkeyCheckValue = null; //转本地后生成的pinkey校验值
				String  mackeyCheckValue = null; //通道下方的mackey效验值
				String lmackeyCheckValue = null; //转本地后生成的mackey校验值
	
				log.debug("PINKEY转本地密钥开始...");
				paramMap = new HashMap<String,Object>();
				/*测试*/
				//加密第三方明文密钥生成通信主密钥
//				Map<String,Object> lmkMap = getEncryptorService().encryZMK(paramMap);
				/*生产*/
				//转换通信主密钥
				Map<String,Object> lmkMap = getEncryptorService().getLocalTermKey(param);
				paramMap.put("ZMK", lmkMap.get("LTERMKEY"));		//本地加密后的通信主密钥
				paramMap.put("PINKEY",key.substring(0, 32));	//第三方工作密钥
				pinkeyCheckValue = key.substring(32, 40);
				
				//PINKEY本地转换
				encryMap = getEncryptorService().getLocalPinKey(paramMap);
				log.debug("PINKEY转本地密钥完成.");
				if(encryMap != null){
					lpinkeyCheckValue = String.valueOf(encryMap.get("LPINKEYCV"));
					result.put("PINKEY",  String.valueOf(encryMap.get("LPINKEY")));
					result.put("PINCHECKVALUE", lpinkeyCheckValue);
				}
				log.info("PIN校验值对比 pinkeyCheckValue=[{}],lpinkeyCheckValue=[{}]",pinkeyCheckValue,lpinkeyCheckValue);
				if(lpinkeyCheckValue==null || !pinkeyCheckValue.equals(lpinkeyCheckValue.substring(0,8))){
					throw new TranException(ExcepCode.EX100205,"PIN校验值比对错误.");
				}
				
				//MACKEY本地转换
				encryMap = null;
				log.debug("MACKEY转本地密钥开始...");
				paramMap = new HashMap<String,Object>();
				paramMap.put("ZMK", lmkMap.get("LTERMKEY"));				//本地加密后的通信主密钥
				paramMap.put("MACKEY", key.substring(40, 56));			//第三方工作密钥
				mackeyCheckValue = key.substring(72,80);
				encryMap = getEncryptorService().getLocalMacKey(paramMap);
	
				log.debug("MACKEY转本地密钥完成.");
				if(encryMap != null){
					lmackeyCheckValue = String.valueOf(encryMap.get("LMACKEYCV"));
					result.put("MACKEY",  String.valueOf(encryMap.get("LMACKEY")));
					result.put("MACCHECKVALUE", lmackeyCheckValue);
				}
				log.info("MAC校验值对比 mackeyCheckValue=[{}],lmackeyCheckValue=[{}]",mackeyCheckValue,lmackeyCheckValue);
				if(lmackeyCheckValue==null || !mackeyCheckValue.equals(lmackeyCheckValue.substring(0,8))){
					throw new TranException(ExcepCode.EX100205,"MAC校验值对比错误.");
				}
			}else{
				throw new TranException(ExcepCode.EX100205,"KEY长度不正确.");
			}
		}else if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_TIMEOUT)){
			throw new TranException(ExcepCode.EX100302);
		}else{
		//	String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
		//	throw EXMAP.get(tranErrorCode);
		}
		log.info("响应数据:{}",result);
		return result;
	}

	/**
	 * 终端设备签到
	 */
	@Override
	public Map<String, Object> termsign(Map<String, Object> param)
			throws TranException {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tangdi.production.mpaychl.base.service.TranService#reverse(java.util.Map)
	 */
	@Override
	public Map<String, Object> reverse(Map<String, Object> param)
			throws TranException {
		
		//冲正业务代码
		log.info("1进入reverse请求参数:{}",param);
		String orgNo = param.get("orgNo").toString();
		//冲正交易MAP
		//定义处理结果Map
	    Map<String,Object> result = new HashMap<String,Object>(); 		
	     
		//获取批次号
		String batchNo = param.get("batchNo").toString();
		
		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("MsgId",  param.get("txncd"));            //    消息类型
		dataPack.put("AC_NO",  param.get("AC_NO"));      // 2域 主账号
		dataPack.put("TProCod",param.get("TProCod"));    // 3域 交易处理码     | 同原交易
		dataPack.put("CTxnAt", param.get("CTxnAt"));     // 4域 交易金额     | 同原交易
		dataPack.put("TSeqNo", param.get("TSeqNo"));     //11域 POS终端交易流水号 | 同原交易
		dataPack.put("InMod",  param.get("InMod"));      //22域 服务点输入方式码   | 同原交易
		dataPack.put("TPosCnd",param.get("TPosCnd"));    //25域 服务点条件码     | 同原交易
		dataPack.put("AcqCod", orgNo);                   //32受理方标识码
		if(param.containsKey("AutCod")){
			dataPack.put("AutCod", param.get("AutCod")); //38域 授权标识应答码   | 同原交易
		}
		dataPack.put("TCPSCod", param.get("TCPSCod"));   //39域 应答码    |同原交易
		dataPack.put("TTermId", param.get("TTermId"));   //41域 终端代码 |同原交易
		dataPack.put("TMercId", param.get("TMercId"));   //42域 商户代码 |同原交易
		dataPack.put("CcyCod",  param.get("CcyCod"));    //49域 货币代码 |同原交易

		dataPack.put("BatchNo", param.get("batchNo"));   //56域 批次号
		dataPack.put("TRsvDat", param.get("TRsvDat"));   //60域 自定义域
		dataPack.put("IDInf",   param.get("batchNo").toString()
				+ param.get("TSeqNo").toString());       //61域 自定义域 |原批次号+原交易流水号
		
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			dataPack.put("ExpDat", param.get("ExpDat"));      //14域 有效期  | 同原交易
			dataPack.put("CrdSqn", param.get("CrdSqn"));      //23域 卡片序列号  | 同原交易
			dataPack.put("ICDat",  param.get("ICDat"));      //55域 ICDATA  | 同原交易
		}
		
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("TCPSCod");
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
			//交易成功后处理
			String remoteMac  = tranMap.getValue("MAC");
			String allMessage = tranMap.getValue("allMessage").replace(remoteMac, "");
			Map<String,Object> macMap = new HashMap<String,Object>();
			macMap.put("MesDat", allMessage);
			macMap.put("MACKEY", param.get("MACKEY"));
			macMap.put("MACMODE", tranMap.get("MACMODE"));
			String localMac = getEncryptorService().getMac(macMap);
			localMac = DesUtils.toHexString(localMac.substring(0, 8));
			log.info("截取前8位转16进制,取得MAC=[{}]。",localMac);
			param.remove("MesDat");    //删除节点
			param.remove("allMessage");//删除节点
			log.info("通道MAC={},本地MAC={}",remoteMac,localMac);
			if(!remoteMac.equals(localMac)){
				throw new TranException(ExcepCode.EX100301,"MAC校验[NG]!");
			}
			log.info("MAC校验[OK].");
			//设置处理结果到result中
			result.put("CTxnTm",  tranMap.getValue("CTxnTm"));   //12域 受卡方所在地时间
			result.put("CTxnDt",  tranMap.getValue("CTxnDt"));   //13域 受卡方所在地日期
			result.put("AcqCod",  tranMap.getValue("AcqCod"));   //32域 受理方标识码
			result.put("SRefNo",  tranMap.getValue("SRefNo"));   //37域 检索参考号
			result.put("TCPSCod", tranMap.getValue("TCPSCod"));  //39域 应答码
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(tranMap.getValue("RETCODE"))){
			throw new TranException(ExcepCode.EX100302);
		}else{
			if(TCPSCod != null){
				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+tranErrorCode));
				throw EXMAP.get(tranErrorCode); 
			}else{
				throw new TranException(ExcepCode.EX100301);
			}
		}
		log.info("响应数据:{}",result);
		return result;
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
		dataPack.put("MsgId",  "0200");            //    消息类型
		//dataPack.put("AC_NO",  param.get("AC_NO"));      // 2域 主账号
		dataPack.put("TProCod","290000");    			 	// 3域 交易处理码     
		dataPack.put("CTxnAt", param.get("netrecAmt"));     		// 4域 交易金额    
		dataPack.put("TSeqNo", getPosSEQ());     	//11域 POS终端交易流水号 
		dataPack.put("F20", 	param.get("cardNo"));			//20域 转入账号
		//dataPack.put("F20", "6228480038395977176");
		dataPack.put("InMod",  "021");      				//22域 服务点输入方式码
		dataPack.put("TPosCnd","00");    					//25域 服务点条件码   
		dataPack.put("TTermId", param.get("TTermId"));  	//41域 终端代码 
		//dataPack.put("TMercId", param.get("TMercId"));   	//42域 商户代码 
		dataPack.put("TMercId", prop.getProperty("TRAN_DAIFU"));
		dataPack.put("NewPin", 	param.get("custName").toString());  	//48域 转入账户名
		//dataPack.put("NewPin", 	"中宏测试");  	//48域 转入账户名
		dataPack.put("CcyCod",  "156");   					//49域 货币代码
		dataPack.put("TRsvDat", "22000001");   			//60域 自定义域
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("TCPSCod");
		//代付流水表所需数据
		Map<String,Object> susMap = new HashMap<String,Object>();
		susMap.put("tmercid", param.get("TMercId"));//第三方商户号
		susMap.put("ttermid", param.get("TTermId"));//第三方终端号
		susMap.put("txndate", DateUtil.convertDateToString(new Date(), DateUtil.FORMAT_YYYYMMDD));//交易日期
		susMap.put("tlogno", getPosSEQ());//交易流水号
		susMap.put("txnactno", param.get("cardNo"));//交易卡号
		susMap.put("txnamt", param.get("netrecAmt"));//交易金额
		
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
			//插入代付流水表
			susMap.put("txnstatus", "01");//代付状态--成功
			try {
				payDao.insertAnotherPay(susMap);
			} catch (Exception e) {
				log.info("插入代付流水表失败！");
				e.printStackTrace();
			}
			//交易成功后处理
			String remoteMac  = tranMap.getValue("MAC");
			String allMessage = tranMap.getValue("allMessage").replace(remoteMac, "");
			Map<String,Object> macMap = new HashMap<String,Object>();
			macMap.put("MesDat", allMessage);
			macMap.put("MACKEY", param.get("MACKEY"));
			macMap.put("MACMODE", tranMap.get("MACMODE"));
			String localMac = getEncryptorService().getMac(macMap);
			localMac = DesUtils.toHexString(localMac.substring(0, 8));
			log.info("截取前8位转16进制,取得MAC=[{}]。",localMac);
			param.remove("MesDat");    //删除节点
			param.remove("allMessage");//删除节点
			log.info("通道MAC={},本地MAC={}",remoteMac,localMac);
			if(!remoteMac.equals(localMac)){
				throw new TranException(ExcepCode.EX100301,"MAC校验[NG]!");
			}
			log.info("MAC校验[OK].");
			//设置处理结果到result中
//			result.put("CTxnTm",  tranMap.getValue("CTxnTm"));   //12域 受卡方所在地时间
//			result.put("CTxnDt",  tranMap.getValue("CTxnDt"));   //13域 受卡方所在地日期
//			result.put("AcqCod",  tranMap.getValue("AcqCod"));   //32域 受理方标识码
//			result.put("SRefNo",  tranMap.getValue("SRefNo"));   //37域 检索参考号
			result.put("TCPSCod", tranMap.getValue("TCPSCod"));  //39域 应答码
			result.putAll(param);
			result.put("tseqNo",  dataPack.get("TSeqNo"));
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
		}
		else{
			if(TCPSCod != null && "98".equals(TCPSCod)){
				//插入代付流水表
				susMap.put("txnstatus", "03");//代付状态--可疑
				try {
					payDao.insertAnotherPay(susMap);
				} catch (Exception e) {
					log.info("插入代付流水表失败！");
					e.printStackTrace();
				}
//				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
//				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+tranErrorCode));
//				throw EXMAP.get(tranErrorCode); 
			}else{
				result.put("TCPSCod", "f0");//代付失败后，状态需改为 待清算（代付失败）
				//throw new TranException(ExcepCode.EX100301);
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
		dataPack.put("MsgId",  "0220");            		  	//消息类型
		dataPack.put("AC_NO",  param.get("cardNo"));      	// 2域 主账号
		//dataPack.put("AC_NO", "6228480038395977176");
		dataPack.put("TProCod","290000");    			 	// 3域 交易处理码    
		log.info("原金额：{}",param.get("payAmt"));
		dataPack.put("CTxnAt", param.get("payAmt"));     // 4域 交易金额    
		dataPack.put("TSeqNo", getPosSEQ());     			// 11域 POS终端交易流水号 
		dataPack.put("F20", param.get("tseqNo"));			// 20域 转入账号
		dataPack.put("InMod",  "021");      				//22域 服务点输入方式码
		dataPack.put("TPosCnd","68");    					//25域 服务点条件码   
		dataPack.put("TTermId", param.get("TTermId"));  	//41域 终端代码 
		//dataPack.put("TMercId", param.get("TMercId"));   	//42域 商户代码 
		dataPack.put("TMercId", param.get("cooporgMerNo"));
		dataPack.put("CcyCod",  "156");   					//49域 货币代码
		dataPack.put("TRsvDat", "22000001");   				//60域 自定义域
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("TCPSCod");
		Map<String,Object> map = new HashMap<String,Object>(); 		
		map.put("tlogno", getPosSEQ());
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
			//交易成功后处理
			String remoteMac  = tranMap.getValue("MAC");
			String allMessage = tranMap.getValue("allMessage").replace(remoteMac, "");
			Map<String,Object> macMap = new HashMap<String,Object>();
			macMap.put("MesDat", allMessage);
			macMap.put("MACKEY", param.get("MACKEY"));
			macMap.put("MACMODE", tranMap.get("MACMODE"));
			String localMac = getEncryptorService().getMac(macMap);
			localMac = DesUtils.toHexString(localMac.substring(0, 8));
			log.info("截取前8位转16进制,取得MAC=[{}]。",localMac);
			param.remove("MesDat");    //删除节点
			param.remove("allMessage");//删除节点
			log.info("通道MAC={},本地MAC={}",remoteMac,localMac);
			if(!remoteMac.equals(localMac)){
				throw new TranException(ExcepCode.EX100301,"MAC校验[NG]!");
			}
			log.info("MAC校验[OK].");
			//设置处理结果到result中
			result.put("CTxnTm",  tranMap.getValue("CTxnTm"));   //12域 受卡方所在地时间
			result.put("CTxnDt",  tranMap.getValue("CTxnDt"));   //13域 受卡方所在地日期
			result.put("AcqCod",  tranMap.getValue("AcqCod"));   //32域 受理方标识码
			result.put("SRefNo",  tranMap.getValue("SRefNo"));   //37域 检索参考号
			result.put("TCPSCod", tranMap.getValue("TCPSCod"));  //39域 应答码
			//更新代付流水表的代付状态
			map.put("txnstatus", "01");
			try {
				payDao.updateAnotherPay(map);
			} catch (Exception e) {
				log.info("更新代付流水表代付状态失败！");
				e.printStackTrace();
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(tranMap.getValue("RETCODE"))){
			throw new TranException(ExcepCode.EX100302);
		}else{
			//更新代付流水表的代付状态
			map.put("txnstatus", "01");
			try {
				payDao.updateAnotherPay(map);
			} catch (Exception e) {
				log.info("更新代付流水表代付状态失败！");
				e.printStackTrace();
			}
			if(TCPSCod != null){
				String tranErrorCode = TRANCODE.GET(orgNo,TCPSCod);
				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+tranErrorCode));
				//throw EXMAP.get(tranErrorCode); 
			}else{
				//throw new TranException(ExcepCode.EX100301);
				log.error("交易失败");
				result.put("TCPSCod", "01");
			}
		}
		log.info("响应数据:{}",result);
		return result;
	}

	@Override
	public Map<String, Object> tranCancel(Map<String, Object> param)
			throws TranException {
		// TODO 自动生成的方法存根
		return null;
	}
	
	public Map<String, Object> limitQuery(Map<String, Object> param) throws TranException {
		//进入额度查询
		log.info("1进入limitQuery请求参数:{}",param);
		String orgNo = param.get("orgNo").toString();
		//定义处理结果Map
	    Map<String,Object> result = new HashMap<String,Object>(); 		

		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("MsgId",  "0100");            //    消息类型
		//dataPack.put("AC_NO",  param.get("AC_NO"));      // 2域 主账号
		dataPack.put("TProCod","9F0004");    			 	// 3域 交易处理码     
		//dataPack.put("CTxnAt", param.get("netrecAmt"));     		// 4域 交易金额    
		dataPack.put("TSeqNo", getPosSEQ());     	//11域 POS终端交易流水号 
		//dataPack.put("F20", 	param.get("cardNo"));			//20域 转入账号
		//dataPack.put("F20", "6228480038395977176");
		//dataPack.put("InMod",  "021");      				//22域 服务点输入方式码
		dataPack.put("TPosCnd","00");    					//25域 服务点条件码   
		dataPack.put("TTermId", param.get("terNo"));  	//41域 终端代码 
		//dataPack.put("TMercId", param.get("TMercId"));   	//42域 商户代码 
		dataPack.put("TMercId", param.get("cooporgMerNo"));
		//dataPack.put("NewPin", 	param.get("custName").toString());  	//48域 转入账户名
		//dataPack.put("NewPin", 	"中宏测试");  	//48域 转入账户名
		dataPack.put("CcyCod",  "156");   					//49域 货币代码
		//dataPack.put("TRsvDat", "22000001");   			//60域 自定义域
		//设置是否产生MAC[0 生成 MAC 1不生成MAC]
		dataPack.put(MessageConstants.GETMACFLAG,"0");
		//设置 默认MAC值   [3030303030303030]
		dataPack.put("MAC", MessageConstants.DEFAULTMAC);
		dataPack.put("MACKEY", param.get("MACKEY"));
		//设置加密机实现类
		dataPack.put("encryServiceName", getEncryptorServiceName());
		
		log.info("获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		Map<String,Object> rmap = CallThrids.CallThirdOther("9997", 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String TCPSCod = tranMap.getValue("TCPSCod");
		//提现流水表所需数据
		Map<String,Object> susMap = new HashMap<String,Object>();
		
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
			//插入提现表
			
			//交易成功后处理
			String remoteMac  = tranMap.getValue("MAC");
			String allMessage = tranMap.getValue("allMessage").replace(remoteMac, "");
			Map<String,Object> macMap = new HashMap<String,Object>();
			macMap.put("MesDat", allMessage);
			macMap.put("MACKEY", param.get("MACKEY"));
			macMap.put("MACMODE", tranMap.get("MACMODE"));
			String localMac = getEncryptorService().getMac(macMap);
			localMac = DesUtils.toHexString(localMac.substring(0, 8));
			log.info("截取前8位转16进制,取得MAC=[{}]。",localMac);
			param.remove("MesDat");    //删除节点
			param.remove("allMessage");//删除节点
			log.info("通道MAC={},本地MAC={}",remoteMac,localMac);
			if(!remoteMac.equals(localMac)){
				throw new TranException(ExcepCode.EX100301,"MAC校验[NG]!");
			}
			log.info("MAC校验[OK].");
			//设置处理结果到result中
//			result.put("CTxnTm",  tranMap.getValue("CTxnTm"));   //12域 受卡方所在地时间
//			result.put("CTxnDt",  tranMap.getValue("CTxnDt"));   //13域 受卡方所在地日期
//			result.put("AcqCod",  tranMap.getValue("AcqCod"));   //32域 受理方标识码
//			result.put("SRefNo",  tranMap.getValue("SRefNo"));   //37域 检索参考号
			result.put("BalInf", tranMap.getValue("BalInf"));
			result.put("TCPSCod", tranMap.getValue("TCPSCod"));  //39域 应答码
			result.putAll(param);
			result.put("tseqNo",  dataPack.get("TSeqNo"));
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(tranMap.getValue("RETCODE"))){
			
			result.put("TCPSCod", "tt");//代付通道返回超时
		}
		else{
			if(TCPSCod != null && "98".equals(TCPSCod)){
				
			}else{
				result.put("TCPSCod", "f0");//代付失败后，状态需改为 待清算（代付失败）
				//throw new TranException(ExcepCode.EX100301);
			}
		}
		log.info("响应数据:{}",result);
		return result;
	}
	
	
}
