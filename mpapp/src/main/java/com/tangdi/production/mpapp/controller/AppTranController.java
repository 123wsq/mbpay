package com.tangdi.production.mpapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.AppTranService;
import com.tangdi.production.mpapp.service.TranOrderService;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.MSGCODE;


/**
 * 
 * <b>终端业务处理</b> 终端支付、余额查询、终端签到（刷卡器设备）
 * @author zhengqiang
 *
 */
@Controller
public class AppTranController {
	private static Logger log = LoggerFactory.getLogger(AppTranController.class);
	
	@Autowired
	private AppTranService service;
	
	@Autowired
	private TranOrderService tranOrderService;
	
	
	
	/**
	 * <b>刷卡器签到交易</b> 交易代码：SG0002
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SG0002")
	@ResponseBody
	public String sign(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("设备签到交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			rmap = service.termsign(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "设备签到成功");
	
			rspmessage.addDataAll(rmap);//
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("设备签到交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}

	
	/**
	 * <b>支付交易</b> 交易代码：PY0001
	 * 
	 * custMobile  手机号
	 * termNo   终端号
	 * pinblk  密码密文   
	 * track   磁道信息
	 * mediaType  介质类型
	 * rateType 费率类型
	 * period 有效期
	 * icdata IC卡数据域
	 * seqnum 序列号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "PY0001")
	@ResponseBody
	public String payment(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("支付交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			if(MsgCT.PAYTYPE_TM.equals(pmap.get("payType"))){
				pmap.put("acType", MsgCT.AC_TYPE_PT);
				service.payment(pmap);
			}else if(MsgCT.PAYTYPE_SM.equals(pmap.get("payType"))){
				String recvResultPath = 
						request.getScheme() +
						"://127.0.0.1" + 
						":" +request.getServerPort() +
						request.getContextPath() + "/TZ0001.json";
				pmap.put("sMJGPath", recvResultPath);
				service.wxsmPayment(pmap);
				rspmessage.setDataV("payData", pmap.get("payData"));
			}else if(MsgCT.PAYTYPE_DEF.equals(pmap.get("payType"))){
				service.payment(pmap);
			}else if(MsgCT.PAYTYPE_KJ.equals(pmap.get("payType"))){
				service.payment(pmap);
			}else{
				throw new TranException(ExcepCode.EX200401,"不支持的支付方式。",
						String.valueOf(pmap.get("payType")));
			}
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "支付成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("支付交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}

	/**
	 * <b>余额查询交易</b> 交易代码：PY0003
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "PY0003")
	@ResponseBody
	public String bankCardBalance(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("银行卡余额交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    Map<String,Object> rmap = new HashMap<String,Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			rmap = service.query(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "查询成功");
	
			rspmessage.addDataAll(rmap);//
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("银行卡余额交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * <b>提现交易</b> 交易代码：PY0004
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "PY0004")
	@ResponseBody
	public String txTran(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("提现交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			service.txTran(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "提现成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("提现交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * <b>上传电子签名交易</b> 交易代码：UP0001
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "UP0001")
	@ResponseBody
	public String esign(HttpServletRequest request,
			@RequestParam(value = "content",     required = false) MultipartFile content) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("电子签名交易开始..." );
		log.info("请求数据为：{}" ,msg);
		
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		
		boolean error = false;
		try{
			if(content == null || content.getSize() <= 0){
				log.error("电子签名接收错误...");
				error = true;
			}
			
			log.info("电子签名：[{}],大小：[{}b]",content.getOriginalFilename(),content.getSize());
			
		}catch(Exception e){
			log.error("电子签名接收错误...");
			error = true;
		}finally{
			if(error){
				String ecode = ExcepCode.EX000228;
				rspmessage.setDataV("RSPCOD",ecode); //获取异常代码
				rspmessage.setDataV("RSPMSG",MSGCODE.GET(ecode));  //获取异常信息
			}
		}
		
		
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			pmap.put("content", content);
			service.esign(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "签名成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("电子签名交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * <b>上传扫描银行卡照片</b> 交易代码：UP0002
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "UP0002")
	@ResponseBody
	public String upBankCard(HttpServletRequest request,
			@RequestParam(value = "bankcard",     required = false) MultipartFile bankcard) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("上传扫描银行卡照片交易开始..." );
		log.info("请求数据为：{}" ,msg);
		
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		
		boolean error = false;
		try{
			if(bankcard == null || bankcard.getSize() <= 0){
				log.error("银行卡照片接收错误...");
				error = true;
			}
			
			log.info("银行卡照片：[{}],大小：[{}b]",bankcard.getOriginalFilename(),bankcard.getSize());
			
		}catch(Exception e){
			log.error("银行卡照片接收错误...");
			error = true;
		}finally{
			if(error){
				String ecode = ExcepCode.EX000229;
				rspmessage.setDataV("RSPCOD",ecode); //获取异常代码
				rspmessage.setDataV("RSPMSG",MSGCODE.GET(ecode));  //获取异常信息
			}
		}
		
		
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			pmap.put("bankCardFile", bankcard);
			service.upBankCard(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "上传银行卡照片成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("上传银行卡照片交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * <b>获取账户余额</b> 交易代码：GB0001
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "GB0001")
	@ResponseBody
	public String getBalance(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("获取账户余额交易开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			pmap.put("acctType", MsgCT.AC_TYPE_PT);
			Map<String,Object> result=service.getAcBalance(pmap);
			rspmessage.addDataAll(result);
			
			pmap.put("acctType", MsgCT.AC_TYPE_KJ);
			result=service.getAcBalance2(pmap);
			if(result != null){
				rspmessage.setDataV("acT1AP_ACT03", result.get("acT1AP"));
				rspmessage.setDataV("acT1Y_ACT03", result.get("acT1Y"));
			}
			
			pmap.put("acctType", MsgCT.AC_TYPE_SM);
			result=service.getAcBalance2(pmap);
			if(result != null){
				rspmessage.setDataV("acT1AP_ACT04", result.get("acT1AP"));
				rspmessage.setDataV("acT1Y_ACT04", result.get("acT1Y"));
			}
			
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "获取账户余额信息成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("获取账户余额信息交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * <b>获取手续费</b> 交易代码：GB0002
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "GB0002")
	@ResponseBody
	public String getFee(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("计算手续费开始..." );
		log.info("请求数据为：{}" ,msg);
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			Map<String,Object> result=service.getPoundage(pmap);
			rspmessage.addDataAll(result);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "计算手续费成功");
		}  catch (TranException e) {
			rspmessage.setDataV("RSPCOD",e.getCode());
			rspmessage.setDataV("RSPMSG",e.getMsg());
			log.error(e.getMessage(),e);
		}
		log.info("计算手续费完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	/**
	 * 查询扫码支付结果
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "SM0001")
	@ResponseBody
	public String smPayResultQuery(HttpServletRequest request) {
		String msg = request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("交易详细查询开始...");
		log.info("请求数据为：{}", msg);
		Map<String,Object> tranDetail = new HashMap<String,Object>();
		ReqMsg reqmessage=null;
		RspMsg rspmessage = new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			tranDetail = tranOrderService.querySMPayResult(pmap);
			if(MsgST.TXNSTATUS_U.equals(tranDetail.get("payResult"))){
				service.wxsmQuery(pmap);
				if(MsgSubST.BANK_TRAN_OK.equals(pmap.get("TCPSCod"))){
					tranDetail.put("payResult", MsgST.TXNSTATUS_S);
				}else if(MsgSubST.BANK_TRAN_PROCESSING.equals(pmap.get("TCPSCod"))){
					tranDetail.put("payResult", MsgST.TXNSTATUS_U);
				}else{
					tranDetail.put("payResult", MsgST.TXNSTATUS_F);
				}
			}
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "交易详细查询成功!");
			rspmessage.addDataAll(tranDetail);
		} catch (TranException e) {
			rspmessage.setDataV("RSPCOD", e.getCode()); // 获取异常代码
			rspmessage.setDataV("RSPMSG", e.getMsg()); // 获取异常信息
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("扫码支付结果查询完成.");
		return Msg.getRspJson(rspmessage);
	}
}
