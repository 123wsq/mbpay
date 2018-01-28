package com.tangdi.production.mpapp.controller;




import java.util.HashMap;
import java.util.List;
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

import com.tangdi.production.mpapp.service.CustBankInfService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.message.Msg;
import com.tangdi.production.mpbase.message.ReqMsg;
import com.tangdi.production.mpbase.message.RspMsg;
import com.tangdi.production.mpbase.util.MSGCODE;

/**
 * 银行卡操作
 * @author zhuji
 * @version 1.0
 *
 */

@Controller
public class CustBankController {
	private static final Logger log = LoggerFactory
			.getLogger(CustBankController.class);

	@Autowired
	private CustBankInfService custBankInfService;
	
	@RequestMapping(value = "SY0008")
	@ResponseBody
	public String upBankCard(HttpServletRequest request,
			@RequestParam(value = "cardFront", required = false) MultipartFile cardFrontFile,
			@RequestParam(value = "cardBack",    required = false) MultipartFile cardBackFile) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		//String certificateNo=request.getParameter("certificateNo");
		//String custId=request.getParameter("custId");
		log.info("银行卡绑定，修改，解绑交易开始..." );
		log.info("请求数据为：{}" ,msg);
		//定义数据类型
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		boolean error = false;
		try{
			if(cardFrontFile == null || cardFrontFile.getSize() <= 0){
				log.error("银行卡正面图片接收错误...");
				error = true;
			}
			if(cardBackFile == null || cardBackFile.getSize() <= 0){
				log.error("银行卡反面图片接收错误...");
				error = true;
			}
			log.info("银行卡正面图片：[{}],大小：[{}b]",cardFrontFile.getOriginalFilename(),cardFrontFile.getSize());
			log.info("银行卡背面图片：[{}],大小：[{}b]",cardBackFile.getOriginalFilename(),cardBackFile.getSize());
			
		}catch(Exception e){
			log.error("银行卡图片接收错误...");
			error = true;
		}finally{
			if(error){
				String ecode = ExcepCode.EX000226;
				rspmessage.setDataV("RSPCOD",ecode); //获取异常代码
				rspmessage.setDataV("RSPMSG",MSGCODE.GET(ecode));  //获取异常信息
			}
		}

		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			pmap.put("cardFront", cardFrontFile);
			pmap.put("cardBack", cardBackFile);
			//pmap.put("certificateNo",certificateNo);
			//pmap.put("custId",custId);
			custBankInfService.distribution(pmap);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "操作成功!");
		}catch(TranException e){
			log.error(e.getMessage());
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("银行卡绑定，修改，解绑交易完成." );
	    return  Msg.getRspJson(rspmessage);
	}
	
	@RequestMapping(value = "SY0015")
	@ResponseBody
	public String getBankCardInf(HttpServletRequest request) {
		String msg=request.getParameter(MessageConstants.REQ_MESSAGE);
		log.info("银行卡列表查询交易开始..." );
		log.info("请求数据为：{}" ,msg);
		Map<String, Object> rspMap=new HashMap<String, Object>();
	    ReqMsg reqmessage=null;
		RspMsg rspmessage=new RspMsg();
		try {
			reqmessage=Msg.getReqMessage(msg);
			Map<String,Object> pmap = reqmessage.getBody();
			List<Map<String , Object>> resultList=custBankInfService.getCustBankInfoList(pmap);
			List<Map<String , Object>> resultTempList=custBankInfService.getCustBankInfoTempList(pmap);
			rspMap.put("bankCardList", resultList);
			rspMap.put("bankCardUnAuditList", resultTempList);
			rspmessage.setDataV("RSPCOD", "000000");
			rspmessage.setDataV("RSPMSG", "操作成功!");
			rspmessage.addDataAll(rspMap);
		}catch(TranException e){
			log.error(e.getMessage());
			rspmessage.setDataV("RSPCOD",e.getCode()); //获取异常代码
			rspmessage.setDataV("RSPMSG",e.getMsg());  //获取异常信息
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		log.info("银行卡列表查询交易完成.." );
	    return  Msg.getRspJson(rspmessage);
	}
	
}
