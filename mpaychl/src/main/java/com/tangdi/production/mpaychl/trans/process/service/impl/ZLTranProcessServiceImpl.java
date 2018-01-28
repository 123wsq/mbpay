package com.tangdi.production.mpaychl.trans.process.service.impl;

import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.tangdi.production.mpaychl.trans.process.service.TranProcessService;
import com.tangdi.production.mpaychl.utils.RSAUtil;
import com.tangdi.production.mpaychl.utils.ThreeDESUtil;
import com.tangdi.production.mpaychl.utils.ZLJsonParser;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.MD5;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpbase.util.TRANCODE;
import com.tangdi.production.mpbase.util.TranMap;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdbase.util.DesUtils;
import com.tangdi.production.tdcomm.channel.CallThrids;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.redoservice.TdRdoAtc;


/**
 * <b>中联支付通道</b>交易[外发]渠道公共处理流程接口实现
 * 
 * @author chenlibo 2015/12/10
 *
 */
@SuppressWarnings("unused")
@Service
public class ZLTranProcessServiceImpl extends TranProcessService {
	private static Logger log = LoggerFactory
			.getLogger(ZLTranProcessServiceImpl.class);
	
	private String twk = "";
	private String md5key = "";
	private String project_id = "";
	private String tmk = "";
	private String signSvr = ""; //签到服务
	private String signCod = ""; //签到交易码
	private Map<String, String> tokenMap = new HashMap<String, String>(); // key=custId, value=token
	private Map<String, String> wkMap = new HashMap<String, String>(); // key=custId, value=tw

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
		//定义处理结果Map
		//初始化
		initData();
		String token = this.getCustToken(param.get("TMercId").toString(), param.get("orgNo").toString());
		String wk = this.getCustWk(param.get("TMercId").toString(), param.get("orgNo").toString());
		//PIN转加密
		String pinblock =  "06" +  new String( hexStringToByteArray(decryptPIN(param))) + "FFFFFFFF";
		//获取批次号
		String batchNo = "";
		String seqNo = getPosSEQ();
		//机构号
		String orgNo = param.get("orgNo").toString();
		
		log.info("获取组包数据.");
		
		//1.创建订单
		Map<String, Object> orderMap =  
				(Map<String, Object>) createOrder(param).get(MsgSubST.ZL_RSP_NM);
		String orderId = (String) 
				((Map) orderMap.get("data")).get("orderId");;  //支付订单号
				
		param.put("orderId", orderId);
		param.put("thirdOrdno", orderId);
		
		//2.订单支付
		Map<String,Object> dataPack = new HashMap<String,Object>();
		Map<String,Object> paramPack = new HashMap<String,Object>();
		Map<String,Object> payBusPack = new HashMap<String,Object>();
		paramPack.put("merchantCode", param.get("TMercId")); //商户号
		paramPack.put("token", token); //令牌

		payBusPack.put("merchantCode", param.get("TMercId")); //商户号
		payBusPack.put("orderId", orderId); //支付订单号
		payBusPack.put("cardNo", param.get("cardNo")); //卡号
		payBusPack.put("isDevice", "1"); //设备类型
		payBusPack.put("inputMode", "021"); //输入模式
		
		String swk = ""; //wk
		try {
			byte[] wkb = ThreeDESUtil.decryptData(ThreeDESUtil.hex2byte( this.twk,"utf-8"), ThreeDESUtil.hex2byte( wk, "utf-8"));
			byte[] pblk =  ThreeDESUtil.encryptData(wkb, ThreeDESUtil.hex2byte(pinblock,"utf-8"));
			pinblock = ThreeDESUtil.byte2hex(pblk);
			swk = ThreeDESUtil.byte2hex(wkb);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100301);
		}
		
		payBusPack.put("pin", pinblock); //pin
		payBusPack.put("cardMedium", "0"); //0-磁条卡,1-IC 卡
		payBusPack.put("track2", ThreeDESUtil.encryptData(swk, param.get("track2").toString(), "utf-8")); //磁道2
		
		if(String.valueOf(param.get("mediaType")).equals(MsgSubST.CARD_TYPE_2)){
			payBusPack.put("cardMedium", "1"); //0-磁条卡,1-IC 卡
			payBusPack.put("inputMode", "051"); //输入模式
			payBusPack.put("icNumber", ThreeDESUtil.encryptData(swk, param.get("crdnum").toString(), "utf-8")); //主账号序列号
			payBusPack.put("expire", ThreeDESUtil.encryptData(swk, param.get("period").toString(), "utf-8")); //有效期
			payBusPack.put("dcData", ThreeDESUtil.encryptData(swk, param.get("icdata").toString(), "utf-8")); //ic卡借贷应用数据
		}
		
		//twk 3des 加密
		String packChiperJson = ZLJsonParser.convertMap2Json( payBusPack);
		log.info("订单支付获取业务数据:" + packChiperJson);
		String orderPackChiper = ThreeDESUtil.encryptData(this.twk, packChiperJson, "utf-8");; //订单支付密文
		
		paramPack.put("packChiper", orderPackChiper);
		dataPack.put("param", paramPack);  //请求参数
		dataPack.put("project_id",  this.project_id);  //project_id
		
		log.info("订单支付组包数据完成,调用Process渠道.dataPack={}",dataPack);
		dataPack.put("ZL_RTRCOD", "0002"); //路由转换
		Map<String,Object> rmap =  CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		
		//设置返回值
		param.putAll(tranMap);
		
		log.debug("第三方交易响应数据:{}",tranMap);
		String rspcod = tranMap.getValue("RETCODE");

		if(MsgSubST.RETCODE_OK.equals(rspcod)){
			//渠道错误码转换
			String TCPSCod = "99"; //默认失败
			if(tranMap.get(MsgSubST.ZL_RSP_NM) != null){
				TCPSCod = TRANCODE.GET(orgNo, ((Map )tranMap.get(MsgSubST.ZL_RSP_NM)).get("code").toString());
				param.put("TCPSCod", TCPSCod); //交易成功后计算合作机构结算费
			}
			
			param.put("cause", prop.get("CPSCODE_"+TCPSCod));
			if(MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
				log.info("订单支付完成[OK].");
			}else{
				if(TCPSCod.equals("68") || TCPSCod.equals("98")){  
					param.put("RedoTCPSCod", "98");
					//交易撤销
					tranCancel(param);
				}
				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+TCPSCod));
				if(EXMAP.containsKey(TCPSCod)){
					throw EXMAP.get(TCPSCod); 
				}else{
					throw new TranException(ExcepCode.EX100301);
				}
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			//交易撤销
			tranCancel(param);
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			//交易撤销
			tranCancel(param);
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
		return null;
	}

	/**
	 * 收单机构或银行签到
	 */
	@Override
	public Map<String, Object> banksign(Map<String, Object> param)
			throws TranException {
		log.info("进入第三方终端签到...");
		//初始化
		initData();
		
		Map<String,Object> result = null;
		String orgNo = param.get("orgNo").toString();
		Map<String,Object> dataPack = new HashMap<String,Object>();
		Map<String,Object> paramPack = new HashMap<String,Object>();
		paramPack.put("merchantCode", param.get("merno")); //商户号
		String stwk = "";
		// 对twk做RSA加密处理
		RSAPublicKey rsaKey;
		try {
			rsaKey = RSAUtil.createRSAPublicKey(this.tmk);
			byte[] b = RSAUtil.encrypt(rsaKey, ThreeDESUtil.hex2byte(this.twk, "utf-8"));
			stwk = ThreeDESUtil.byte2hex(b);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100301);
		}
		
		paramPack.put("twk", stwk);  //twk
		dataPack.put("param", paramPack);  //请求参数
		dataPack.put("project_id", this.project_id);  //project_id
		
		log.info("中联签到组包数据完成,调用Process渠道.dataPack={}",dataPack);
		dataPack.put("ZL_RTRCOD", "9999");
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		String rspcod = tranMap.getValue("rsp");
		if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				MsgSubST.BANK_TRAN_OK.equals(rspcod)){
			result = new HashMap<String,Object>();
			
			Map zlRspMap = (Map) tranMap.get(MsgSubST.ZL_RSP_NM);
			result.put("token", ((Map) zlRspMap.get("data")).get("token") );
			result.put("wk", ((Map) zlRspMap.get("data")).get("wk") );
			result.put("BATCHNO", "000001");  //汇竹收单通道:批次号固定[000001]
			
			tokenMap.put(param.get("merno").toString(), ((Map) zlRspMap.get("data")).get("token").toString());
			wkMap.put(param.get("merno").toString(), ((Map) zlRspMap.get("data")).get("wk").toString());
			
			result.put("PINKEY",  ((Map) zlRspMap.get("data")).get("wk").toString());
		}else if(tranMap.getValue("RETCODE").equals(MsgSubST.RETCODE_OK) && 
				!MsgSubST.BANK_TRAN_OK.equals(rspcod)){
			throw new TranException(ExcepCode.EX100301);
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

	@Override
	public Map<String, Object> reverse(Map<String, Object> param)
			throws TranException {
		return null;
	}
	
	@Override
	public void redo(Map<String, Object> map) throws TranException {
		log.error("交易超时冲正处理,数据：{}", map);
		return ;
	}

	@Override
	public Map<String, Object> casPay(Map<String, Object> param) throws TranException {
		//进入实时代付
		
		return null;
	}

	@Override
	public Map<String, Object> conCasPay(Map<String, Object> param) throws TranException {
		//进入确认实时代付
		
		return null;
	}
	
	/**
	 * 获取签名串
	 * @param map
	 * @param md5key
	 * @return
	 */
	private String getSignStr(Map<String, Object> map, String md5key){
		String res = "KEY=" + md5key;
		if(map == null || map.size() == 0){
			return res;
		}
		List<String> paraList = new ArrayList<String>();
		Iterator it =  (Iterator) map.keySet().iterator();
		while(it.hasNext()){
			String pname = (String) it.next(); //业务参数名称
			Object pvalue = map.get(pname); //业务参数值
			paraList.add(pname + "=" + (pvalue == null ? "":((String)pvalue).trim()) ); //空值使用 key=
		}
		
		Collections.sort(paraList); //排序
		
		StringBuffer sb = new StringBuffer();
		int size = paraList.size();
		for (int i = 0; i < size; i++) {
			sb.append(paraList.get(i));
			sb.append("&");
		}
		sb.append(res); //添加KEY=MD5key

		return sb.toString();
		
	}
	/**
	 * 撤销交易
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> tranCancel(Map<String, Object> param) throws TranException {
		//进入撤销交易
		log.info("1进入tranCancel请求参数:{}",param);
		//初始化
		initData();
		String orgNo = param.get("orgNo").toString();
		//定义处理结果Map
	    Map<String,Object> result = new HashMap<String,Object>(); 		

		log.info("获取组包数据.");
		Map<String,Object> dataPack = new HashMap<String,Object>();
		dataPack.put("merchantCode", param.get("TMercId")); //商户号
		dataPack.put("orderId", param.get("orderId"));  //原交易订单号
		dataPack.put("outOrderId", param.get("payNo"));  //商户订单号
		
		//md5签名
		String signStr = getSignStr(dataPack, this.md5key);
		String sign = MD5.encryption(signStr).toUpperCase();
		
		dataPack.put("sign", sign);  //签名
		
		log.info("消费撤销获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		dataPack.put("ZL_RTRCOD", "0003");
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
				String.valueOf(param.get("rtrsvr")), dataPack);
		//获取相应参数
		TranMap<String,Object> tranMap = new TranMap<String,Object>();
		tranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",rmap);
		
		String rspcod = tranMap.getValue("RETCODE");

		if(MsgSubST.RETCODE_OK.equals(rspcod)){
			//渠道错误码转换
			String TCPSCod = "99"; //默认失败
			if(tranMap.get(MsgSubST.ZL_RSP_NM) != null){
				TCPSCod = TRANCODE.GET(orgNo, ((Map )tranMap.get(MsgSubST.ZL_RSP_NM)).get("code").toString());
				param.put("TCPSCod", TCPSCod); //交易成功后计算合作机构结算费
			}
			
			param.put("cause", prop.get("CPSCODE_"+TCPSCod));
			if(MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
				log.info("订单支付撤销完成[OK].");
			}else{
				log.error("订单支付撤销交易失败:错误信息[{}]",prop.get("CPSCODE_"+TCPSCod));
				if(EXMAP.containsKey(TCPSCod)){
					throw EXMAP.get(TCPSCod); 
				}else{
					throw new TranException(ExcepCode.EX100301);
				}
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(rspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		return null;
	}
	/**
	 * 创建订单
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String, Object> createOrder(Map<String, Object> param) throws TranException{
		log.info("进入createOrder请求参数:{}",param);
		//初始化
		initData();
		String token = this.getCustToken(param.get("TMercId").toString(), param.get("orgNo").toString());
		String wk = this.getCustWk(param.get("TMercId").toString(), param.get("orgNo").toString());
		//机构号
		String orgNo = param.get("orgNo").toString();
				
		log.info("获取组包数据.");
		
		Map<String,Object> dataPack = new HashMap<String,Object>();
	    Map<String,Object> paramPack = new HashMap<String,Object>();
		Map<String,Object> busPack = new HashMap<String,Object>();
		paramPack.put("merchantCode", param.get("TMercId")); //商户号
		paramPack.put("token", token); //令牌
				
		busPack.put("merchantCode", param.get("TMercId")); //商户号
		busPack.put("nonceStr", "11111111111111111111111111111111");  //随机数
		busPack.put("outUserId", "");  //商户系统用户编号
		busPack.put("outOrderId", param.get("payNo"));  //商户订单号
		busPack.put("totalAmount", param.get("payAmt").toString().replace(".", ""));  //支付金额(Long 分)
		busPack.put("orderCreateTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));  //商户订单创建时间
		busPack.put("lastPayTime", "");  //最晚支付时间
		
		//md5签名
		String signStr = getSignStr(busPack, this.md5key);
		String sign = MD5.encryption(signStr).toUpperCase();
		
		busPack.put("sign", sign);  //签名
		
		//使用twk做3des加密业务数据
		String packChiperJson = ZLJsonParser.convertMap2Json( busPack); //创建订单JSON
		log.info("创建订单获取业务数据:" + packChiperJson);
		String packChiper = ThreeDESUtil.encryptData(this.twk, packChiperJson, "utf-8");; //创建订单密文
		
		paramPack.put("packChiper", packChiper);
		dataPack.put("param", paramPack);  //请求参数
		dataPack.put("project_id",  this.project_id);  //project_id
		
		log.info("创建订单获取组包数据完成,调用Process渠道.dataPack={}",dataPack);
		dataPack.put("ZL_RTRCOD", "0001");
		Map<String,Object> rmap = CallThrids.CallThirdOther(String.valueOf(param.get("rtrcod")), 
						String.valueOf(param.get("rtrsvr")), dataPack);
		//获取相应参数
		TranMap<String,Object> coTranMap = new TranMap<String,Object>();
		coTranMap.putAll(rmap);
		log.debug("第三方交易响应数据:{}",coTranMap);
		String coRspcod = coTranMap.getValue("RETCODE");
		
		if(MsgSubST.RETCODE_OK.equals(coRspcod)){
			//渠道错误码转换
			String TCPSCod = "99";  //默认失败
			if(coTranMap.get(MsgSubST.ZL_RSP_NM) != null){
				TCPSCod = TRANCODE.GET(orgNo, ((Map )coTranMap.get(MsgSubST.ZL_RSP_NM)).get("code").toString());
			}
			
			param.put("cause", prop.get("CPSCODE_"+TCPSCod));
			if(MsgSubST.BANK_TRAN_OK.equals(TCPSCod)){
				log.info("创建订单完成[OK].");
			}else{
				log.error("交易失败:错误信息[{}]",prop.get("CPSCODE_"+TCPSCod));
				if(EXMAP.containsKey(TCPSCod)){
					throw EXMAP.get(TCPSCod); 
				}else{
					throw new TranException(ExcepCode.EX100301);
				}
			}
		}else if(MsgSubST.RETCODE_TIMEOUT.equals(coRspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else if(MsgSubST.RETCODE_NG.equals(coRspcod)){
			param.put("RedoTCPSCod", "98");
			throw new TranException(ExcepCode.EX100302);
		}else{
			throw new TranException(ExcepCode.EX100301);
		}
		return coTranMap;
	}
	
	private byte[] hexStringToByteArray(String text) {
        if (text == null) {
            return null;
        }
        byte[] result = new byte[text.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            int x = Integer.parseInt(text.substring(i * 2, i * 2 + 2), 16);
            result[i] = x <= 127 ? (byte) x : (byte) (x - 256);
        }
        return result;
    }
	/**
	 * 使用{mpaychlConfig}初始化基础数据
	 * @throws TranException
	 */
	private void initData() throws TranException{
		if(this.md5key == null || "".equals(this.md5key.trim())){
			this.md5key = prop.getProperty("ZL_CHNL_P2");
		}
		if(this.tmk == null || "".equals(this.tmk.trim())){
			this.tmk = prop.getProperty("ZL_CHNL_P4");
		}
		if(this.project_id == null || "".equals(this.project_id.trim())){
			this.project_id = prop.getProperty("ZL_CHNL_P3");
		}
		if(this.twk == null || "".equals(this.twk.trim())){
			this.twk = prop.getProperty("ZL_CHNL_P1");
		}
		if(this.signSvr == null || "".equals(this.signSvr.trim())){
			this.signSvr = prop.getProperty("ZL_SIGN_SVR");
		}
		if(this.signCod == null || "".equals(this.signCod.trim())){
			this.signCod = prop.getProperty("ZL_SIGN_COD");
		}
		
		if(this.md5key == null || "".equals(this.md5key.trim()) ||
				this.tmk == null || "".equals(this.tmk.trim()) ||
				this.project_id == null || "".equals(this.project_id.trim()) ||
				this.twk == null || "".equals(this.twk.trim()) ||
				this.signSvr == null || "".equals(this.signSvr.trim()) ||
				this.signCod == null || "".equals(this.signCod.trim())){
			throw new TranException(ExcepCode.EX900003, "获取参数失败");
		}
	}
	
	private String getCustToken(String custId, String orgNo) throws TranException{
		String token = tokenMap.get(custId);
		if(token == null || "".equals(token.trim())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("merno", custId);
			param.put("orgNo", orgNo);
			param.put("rtrcod", this.signCod);
			param.put("rtrsvr", this.signSvr);
			try {
				this.banksign(param);
			} catch (TranException e) {
				throw e;
			}
		}
		return tokenMap.get(custId);
	}
	
	private String getCustWk(String custId, String orgNo) throws TranException{
		String wk = wkMap.get(custId);
		if(wk == null || "".equals(wk.trim())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("merno", custId);
			param.put("orgNo", orgNo);
			param.put("rtrcod", this.signCod);
			param.put("rtrsvr", this.signSvr);
			try {
				this.banksign(param);
			} catch (TranException e) {
				throw e;
			}
		}
		return wkMap.get(custId);
	}


	@Override
	public Map<String, Object> limitQuery(Map<String, Object> param)
			throws TranException {
		// TODO 自动生成的方法存根
		return null;
	}

}
