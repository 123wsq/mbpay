package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.MessageDao;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.MessageService;
import com.tangdi.production.mpapp.service.ValidateCodeService;
import com.tangdi.production.mpaychl.front.service.SmsFrontService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpbase.util.TemplateUtil;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 短信业务接口实现
 * @author zhengqiang 2015/08/19
 *
 */
@Service
public class MessageServiceImpl implements MessageService {
	private static Logger log = LoggerFactory
			.getLogger(MessageServiceImpl.class);
	/**
	 * 短信DAO
	 */
	@Autowired
	private MessageDao dao;
	/**
	 * 获取验证码接口
	 */
	@Autowired
	private ValidateCodeService vcservice;
	/**
	 * 获取序号接口
	 */
	@Autowired
	private GetSeqNoService seqNoService;
	/**
	 * 短信前置接口
	 */
	@Autowired
	private SmsFrontService smsFrontService;
	
	/**
	 * 商户接口
	 */
	@Autowired
	private MerchantService merchantService;
	/**
	 * 添加发送短信记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int addSendMssage(Map<String, Object> param) throws TranException {
		log.debug("保存发送记录开始. 参数：{}",param);
		
		int rt = 0;
		param.put("smsId",   param.get("seq"));
		param.put("smsType", param.get("smsType"));
		param.put("smsMoblie", param.get("custMobile"));
		param.put("smsBody", param.get("smsdata"));
		param.put("smsDate", TdExpBasicFunctions.GETDATETIME());
		param.put("smsStatus", MsgST.SMS_SEND_INIT);
		try {
			rt = dao.insertSendMssage(param);
			log.debug("保存发送记录完成. 状态值：{}",rt);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001106,e);
		}
		return rt;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int updateMssage(Map<String, Object> param) throws TranException {
		log.debug("更新短信状态... 参数：{}",param);
		
		int rt = 0;
		param.put("smsId",   param.get("seq"));
		param.put("smsStatus", param.get("smsStatus"));
		try {
			rt = dao.updateMessage(param);
			log.debug("更新短信状态完成. 状态值：{}",rt);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001107,e);
		}
		return rt;
	}
	
	
	/**
	 * 通过短信类型,查询短信模版
	 * @param param
	 * @return
	 * @throws TranException
	 */
	
	private Map<String, Object> queryTemplate(Map<String, Object> param)
			throws TranException {
		log.debug("查询短信模版,短信类型:{}",param);
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap = dao.selectTemplate(param);
		log.debug("查询结果:{}",rmap);
		return rmap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String sendsms(Map<String, Object> param) throws TranException {
		/**
		 * 1.效验参数
		 */
		ParamValidate.doing(param, "custMobile","codeType");
		String seq = "";
		String validateCode = "000000";

		/**
		 *  短信类别
		 *  01:注册验证码；
		 *	02:登录密码修改或找回;
		 *  03:支付密码修改或找回；
		 */
		String codeType = param.get("codeType").toString();
		log.info("短信类别[{}]",codeType);
		param.put("smsType", codeType);
		/**
		 * 1.验证手机号是否存在
		 */
		int isext = merchantService.checkIsExist(param);
		log.info("商户操作状态码：[{}]",isext);
		if(MsgST.SMS_TYPE_REGIST.equals(codeType)){
			if(isext != 0){
				throw new TranException(ExcepCode.EX000202);
			}
		}else if(MsgST.SMS_TYPE_PWD.equals(codeType)){
			if(isext == 0){
				throw new TranException(ExcepCode.EX000250);
			}
			if(isext == 2){
				throw new TranException(ExcepCode.EX000251);
			}
			if(isext == 3){
				throw new TranException(ExcepCode.EX000252);
			}
		}else if( MsgST.SMS_TYPE_PAY_PWD.equals(codeType)){
			if(isext == 0){
				throw new TranException(ExcepCode.EX000250);
			}
		}
		
		try{
		//生产环境把此处打开	
		    validateCode = TdExpBasicFunctions.RANDOM(6, "2");
			log.info("生成的验证码:[{}]",validateCode);
			param.put("validateCode", validateCode);
			vcservice.addCode(param);
			

			log.info("通过短信类型,查询短信模版.");
			Map<String,Object> template = queryTemplate(param);
			
			String smsdata = TemplateUtil.convert(template.get("smsContent"), validateCode);
			param.put("smsdata", smsdata);
			
			try {
				 seq = "1"+TdExpBasicFunctions.GETDATE().substring(2)+seqNoService.getSeqNoNew("SMS_ID1" , "8", "1");
				 param.put("seq", seq);
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001101,"短信序列号获取失败!",e);
			}
			
			log.info("保存发送短信记录...");
			addSendMssage(param);
		}catch(TranException e){
			//捕获异常后, 把EX001101异常消息返回给APP
			throw new TranException(ExcepCode.EX001101,e);
		}

		try{
			log.info("调用渠道发送短信");
			smsFrontService.sendsms(param);
			param.put("smsStatus", MsgST.SMS_SEND_OK);
			updateMssage(param);
		}catch(TranException e){
			param.put("smsStatus", MsgST.SMS_SEND_NG);
			updateMssage(param);
			//捕获异常后, 把EX001101异常消息返回给APP
			throw new TranException(ExcepCode.EX001101,e);
		}
		return validateCode;
		
	}

}
