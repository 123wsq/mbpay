package com.tangdi.production.mpapp.service.impl;

import java.util.HashMap;
import java.util.List;
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
import com.tangdi.production.mpapp.dao.AgeCustDao;
import com.tangdi.production.mpapp.dao.CustRateDao;
import com.tangdi.production.mpapp.dao.MerchantDao;
import com.tangdi.production.mpapp.dao.MeridentifyDao;
import com.tangdi.production.mpapp.dao.RatesInfDao;
import com.tangdi.production.mpapp.service.AgentService;
import com.tangdi.production.mpapp.service.CustAccountService;
import com.tangdi.production.mpapp.service.CustBankInfService;
import com.tangdi.production.mpapp.service.MerchantService;
import com.tangdi.production.mpapp.service.MessageService;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpapp.service.TranOrderService;
import com.tangdi.production.mpapp.service.ValidateCodeService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

import jxl.biff.XCTRecord;

/**
 * 商户服务接口实现类
 * @author zhengqiang
 *
 */
@Service
public class MerchantServiceImpl implements MerchantService {

	private static Logger log = LoggerFactory
			.getLogger(MerchantServiceImpl.class);
	@Autowired
	private GetSeqNoService seqNoService;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private MeridentifyDao meridentifyDao;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private CustBankInfService custBankInfService;
	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private TranOrderService tranOrderService;
	@Autowired
	private ValidateCodeService volatileCode;
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private AgeCustDao ageCustDao;
	@Autowired
	private CustRateDao custRateDao;
	
	@Autowired
	private RatesInfDao ratesInfDao;
	
	
	
	@Value("#{mpappConfig}")
	private Properties prop;
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String register(Map<String, Object> param)
			throws TranException{
		
		ParamValidate.doing(param, "custMobile","custPwd");
		
		int rtMer = 0;
		int rtMdr=0;
		int rtAcc=0;
		//验证是否存在
		String login=(String) param.get("custMobile");
		Map<String, Object> validateMap=new HashMap<String, Object>();
		validateMap.put("custMobile", login);
		Map<String, Object> rst=null;

		try {
			rst=getMerchant(validateMap);
		} catch (Exception e) {
			log.error("商户查询验证存在失败："+e.getMessage(),e);
			throw new TranException(ExcepCode.EX000201,e);
		}
		if (rst!=null&&rst.size()>0) {
			throw new TranException(ExcepCode.EX000202);
		}
		String mid="";
		try {
			mid = TdExpBasicFunctions.GETDATE().substring(2)+seqNoService.getSeqNoNew("MMER_ID", "8", "1");
			param.put("custId", mid);
			param.put("custStatus", MsgST.AUTH_STATUS_NO); 
			param.put("custLogin", login);
			param.put("isLoginFlag", MsgST.IS_LOGIN_OK);
			param.put("usrMobile", login);
			param.put("custPwdNum", 0);
			param.put("policeIdentifystatus", MsgST.AUTH_STATUS_NO);
			param.put("custRegDatetime", TdExpBasicFunctions.GETDATETIME());
			param.put("merclass", MsgST.MER_CLASS_00);
			param.put("authStatus", "0");
			log.debug("注册信息:{}",param);
			for(int i = 1; i < 5; i++){
				Map<String, Object> accountParam=new HashMap<String, Object>();
				accountParam.put("custId", mid);
				accountParam.put("account", mid+"0" +i);
				accountParam.put("acType", "0" +i);
				accountParam.put("acT0", "0");
				accountParam.put("acT1", "0");
				accountParam.put("acT1Y", "0");
				accountParam.put("acBal", "0");
				accountParam.put("ccy", "CNY");
				
				rtAcc=custAccountService.addCustAccount(accountParam);
				
				//新增商户默认费率
				Map<String, Object> rateInfMap = new HashMap<String, Object>();
				rateInfMap.put("custId", mid);
				rateInfMap.put("insTim", TdExpBasicFunctions.GETDATETIME());
				rateInfMap.put("rateCode", MsgCT.RATECODE_AGE);
				rateInfMap.put("rateId", prop.get("SM_DEFAGENT")+"");
				rateInfMap.put("rateType", accountParam.get("acType"));
				
				ratesInfDao.insertDefMerRate(rateInfMap);
				
			}
			
			//将商户绑定到默认代理商下
			Map<String, String> ageCustMap = new HashMap<String, String>();
			ageCustMap.put("ageId", prop.get("SM_DEFAGENT")+"");
			log.debug("绑定商户与扫码默认代理商  CUST_ID {} AGE_ID{} ",mid, ageCustMap.get("ageId").toString());
			ageCustMap.put("custId", mid);  //新注册商户的Id
			ageCustMap.put("terminalId", "999999999");
			ageCustDao.insertAgeCustInfo(ageCustMap);
			
			rtMer = merchantDao.insertEntity(param);
			rtMdr=meridentifyDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000201,"商户注册异常",e);
		}
		if(rtMer<=0||rtMdr<=0||rtAcc<=0){
			throw new TranException(ExcepCode.EX000201,"商户注册失败");
		}
		
		
		return mid;
	}
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updatePwd(Map<String, Object> param)
			throws TranException{

		ParamValidate.doing(param, "pwdType","updateType","value","newPwd","custMobile");

		//验证原密码
		Map<String, Object> resultList = null;
		Map<String, Object> map=new HashMap<String, Object>();
		String updateType=(String) param.get("updateType");
		if (updateType.equals("1")) {
			map.put("custMobile", param.get("custMobile"));
			try {
				resultList = getMerchant(map);
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX000207,e);
			}
			if (resultList==null||resultList.size()<=0) {
				throw new TranException(ExcepCode.EX000206,"商户查询不存在");
			}
			String oldpwd  = resultList.get("custPwd").toString();
			String oldpwd_ = param.get("value").toString();
			log.info("数据库原密码：[{}],客户端原密码：[{}]",oldpwd,oldpwd_);
			if (!oldpwd.equalsIgnoreCase(oldpwd_)) {
				throw new TranException(ExcepCode.EX000216);
			}
		}
		//验证密码类型
		String pwdType=(String) param.get("pwdType");
		if (pwdType.equals("1")) {
			param.put("custPwd", param.get("newPwd"));
		}else if (pwdType.equals("2")) {
			param.put("payPwd", param.get("newPwd"));
		}else {
			throw new TranException(ExcepCode.EX000205);
		}
		
		int rtMer = 0;
		
		try {
			rtMer=merchantDao.updateEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000205,"商户更新密码异常",e);
		}
		
		if (rtMer==0) {
			throw new TranException(ExcepCode.EX000205,"商户更新密码失败");
		}
		return rtMer;
	}
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int certification(Map<String, Object> param)
			throws TranException{
		ParamValidate.doing(param, "cardHandheld","cardFront"
				,"cardBack","custId","custName","certificateNo");
		//,"userEmail","provinceId","cityId"
		int rtMer=0;
		int rtMob=0;
		param.put("custStatus", "1");
//		BASE64Decoder base=new BASE64Decoder();
		String cardFront="";
		String cardHandheld="";
		String cardBack="";
		try {
			//MultipartFile
			cardHandheld   =saveImg((MultipartFile) param.get("cardHandheld"));
			cardFront      =saveImg((MultipartFile) param.get("cardFront"));
			cardBack       =saveImg((MultipartFile) param.get("cardBack"));
			
			param.put("cardFrontPath", cardFront);
			param.put("cardHandheldPath", cardHandheld);
			param.put("cardBackPath", cardBack);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000205,"商户实名认证信息保存图片异常",e);
		}
		
		try {
			rtMer=meridentifyDao.updateEntity(param);
			rtMob=merchantDao.updateEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000205,"商户实名认证更新异常",e);
		}
		
		if(rtMer==0||rtMob==0){
			throw new TranException(ExcepCode.EX000205,"商户实名认证更新失败");
		}
		return 0;
	}

	@Override
	public Map<String, Object> getMerchant(Map<String, Object> param)
			throws TranException {
		
		ParamValidate.doing(param, "custMobile");

		Map<String, Object> resultList = null;
		try {
			resultList = merchantDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000207,"获取商户信息异常",e);
		}
		log.debug("商户信息:{}",resultList);
		return resultList;
	}
	
	@Override
	public Map<String, Object> getMerchantByID(Map<String, Object> param)
			throws TranException {
		
		ParamValidate.doing(param, "custId");

		Map<String, Object> resultList = null;
		try {
			resultList = merchantDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000207,"获取商户信息异常",e);
		}
		log.debug("商户信息:{}",resultList);
		return resultList;
	}
	
	@Override
	public int checkIsExist(Map<String , Object> param) throws TranException{
		ParamValidate.doing(param,"custMobile");
		Map<String , Object> result=null;
		result=getMerchant(param);
		log.info("查询商户信息返回：[{}]",result);
		if (result!=null&&result.size()>=0) {
			//throw new TranException(ExcepCode.EX000202,"商户已存在");
			String status = result.get("custStatus").toString();
			String isLoginFlag = result.get("isLoginFlag").toString();
			//商户被禁用
			if(MsgST.CUST_STATUS_FAL.equals(status)){
				return 2;
			}
			//登陆状态冻结
			if(MsgST.IS_LOGIN_NG.equals(isLoginFlag)){
				return 3;
			}
			return 1 ;
		}
		
		return 0 ;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int updateLoginInfo(Map<String, Object> map)
			throws TranException {
		int rt = 0;
		try {
			log.debug("更新商户登陆信息:{}",map);
			rt = merchantDao.updateEntity(map);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000205,e);
		}
		log.debug("更新结果:[{}]",rt);
		return rt;
	}
	/**
	 * 保持身份证图片
	 * @param img
	 * @return
	 * @throws TranException 
	 */
	private String saveImg(String img) throws TranException{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_CERT);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001001,"身份认证图片保存失败!",e);
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
	 * 保持身份证图片
	 * @param img MultipartFile
	 * @return
	 * @throws TranException 
	 */
	private String saveImg(MultipartFile img) throws TranException{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_CERT);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001001,"身份认证图片保存失败!",e);
		}
		if(rmap != null){
			fid = String.valueOf(rmap.get("fid"));
		}else{
			throw new TranException(ExcepCode.EX001001,"返回值对象rmap为空!");
		}
		log.info("文件ID=[{}]",fid);
		return fid;

	}

	@Override
	public Map<String, Object> getMerchantInfo(Map<String, Object> param)
			throws TranException {
		Map<String, Object> resultMap=new HashMap<String, Object>();

		Map<String, Object> info=getMerchant(param);
		if (info==null||info.size()<=0) {
			throw new TranException(ExcepCode.EX000206);
		}
		try {
			log.debug("查询商户信息..");
			String custId = String.valueOf(info.get("custId"));
			resultMap.put("custId", custId);
			resultMap.put("custName", info.get("custName"));
			resultMap.put("custLogin", info.get("custLogin"));
			param.put("custId", custId);
		
			log.debug("查询实名认证信息..");
			Map<String,Object> info_=meridentifyDao.selectEntity(param);
			String status = String.valueOf(info_.get("custStatus"));
			resultMap.put("custStatus", status);

			log.debug("查询银行卡信息..");
			int cardNum=custBankInfService.getCount(param);
			int cardTempNum=custBankInfService.getTempCount(param);
			//查询银行卡状态
			String cardState = custBankInfService.selectCardState(custId);
			log.debug("卡片状态...");
			if (cardNum==0&&cardTempNum==0) {
				resultMap.put("cardBundingStatus", MsgST.CARD_BUNDING_STATUS_0);
			}else if ("1".equals(cardState)) {
				resultMap.put("cardBundingStatus", MsgST.CARD_BUNDING_STATUS_1);
			}else if ("0".equals(cardState)) {
				resultMap.put("cardBundingStatus", MsgST.CARD_BUNDING_STATUS_2);
			}else if ("2".equals(cardState)) {
				resultMap.put("cardBundingStatus", "3");
			}
			
			resultMap.put("cardNum", cardNum);
			//终端信息
			int termNum=terminalService.getlist(param).size();
			resultMap.put("termNum",termNum);
			
			//查询银行卡认证错误信息
			String bankCardAuthError = "";
			if(cardNum <= 0){
				bankCardAuthError = custBankInfService.selectErrorReason(custId);
			}
			resultMap.put("bankCardAuthError",bankCardAuthError);
			//查询身份证认证错误信息
			String ideCardAuthError = "";
			if(MsgST.AUTH_STATUS_NG.equals(status)){
				ideCardAuthError = String.valueOf(info_.get("auditIdea"));
			}
			resultMap.put("ideCardAuthError",ideCardAuthError);	
			
			//查询刷卡头蓝牙地址
			String macAddress = terminalService.queryMacAddress(custId);
			resultMap.put("macAddress", macAddress);
			
			//查询红点提示
			String redDot = tranOrderService.redDot(param);
			resultMap.put("redDot", redDot);
			
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000207,e);
		}
		return resultMap;
	}

	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int registerByReference(Map<String, Object> param)
			throws TranException {
		
		ParamValidate.doing(param, "custMobile","custPwd");
		
		int rtMer = 0;
		int rtMdr=0;
		int rtAcc=0;
		
		//校验推荐人是否存在
		//getMerchantByReferrer(param);
		
		//验证是否存在
		String login=(String) param.get("custMobile");
		Map<String, Object> validateMap=new HashMap<String, Object>();
		validateMap.put("custMobile", login);
		Map<String, Object> rst=null;
		try {
			rst=getMerchant(validateMap);
		} catch (Exception e) {
			log.error("商户查询验证存在失败："+e.getMessage(),e);
			throw new TranException(ExcepCode.EX000201,e);
		}
		if (rst!=null&&rst.size()>0) {
			throw new TranException(ExcepCode.EX000202);
		}
		try {
			String mid = TdExpBasicFunctions.GETDATE().substring(2)+seqNoService.getSeqNoNew("MMER_ID", "8", "1");
			param.put("custId", mid);
			param.put("custStatus", MsgST.AUTH_STATUS_NO); 
			param.put("custLogin", login);
			param.put("isLoginFlag", MsgST.IS_LOGIN_OK);
			param.put("usrMobile", login);
			param.put("custPwdNum", 0);
			param.put("policeIdentifystatus", MsgST.AUTH_STATUS_NO);
			param.put("custRegDatetime", TdExpBasicFunctions.GETDATETIME());
			param.put("merclass", MsgST.MER_CLASS_00);
			log.debug("注册信息:{}",param);
			Map<String, Object> accountParam=new HashMap<String, Object>();
			accountParam.put("custId", mid);
			accountParam.put("account", mid+"01");
			accountParam.put("acType", "01");
			accountParam.put("acT0", "0");
			accountParam.put("acT1", "0");
			accountParam.put("acT1Y", "0");
			accountParam.put("acBal", "0");
			accountParam.put("ccy", "CNY");
			rtAcc=custAccountService.addCustAccount(accountParam);
			rtMer = merchantDao.insertEntity(param);
			rtMdr=meridentifyDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000201,"商户注册异常",e);
		}
		if(rtMer<=0||rtMdr<=0||rtAcc<=0){
			throw new TranException(ExcepCode.EX000201,"商户注册失败");
		}
		return 0;
	}

	@Override
	public Boolean getMerchantByReferrer(Map<String, Object> param)
			throws TranException {

		String status=null;
		try {
			status = merchantDao.selectReference(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000221,"没有找到商户信息.",e);
		}
		if(MsgST.CUST_STATUS_FAL.equals(status)){
			throw new TranException(ExcepCode.EX000221,"无效推荐人");
		}
		log.debug("推荐人状态:{}",status);
		return true;
	}
	
	@Override
	public Map<String,String> getRateT0(Map<String,Object> param) throws TranException{
		Map<String,String> custRateT0 = new HashMap<String,String>();
		try {
			if(MsgCT.PAYTYPE_SM.equals(param.get("rateType"))){
				custRateT0 = merchantDao.selectRateT0new(param);
			}else{
				custRateT0 = merchantDao.selectRateT0(param);
			}
		} catch (Exception e) {
			log.error("获取商户T0提现费率异常");
		}
		return custRateT0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReturnMsg webScanRegitser(Map<String, Object> param) throws TranException {
		// TODO Auto-generated method stub
		ReturnMsg msg = new ReturnMsg();
		
		ParamValidate.doing(param, "custMobile","custPwd", "msgCode");
		//验证  验证码
		if (!param.get("url").toString().startsWith("http://103.47.137.53:8098")) {
			volatileCode.validate(param);
		}else{
			log.debug("测试环境， 跳过验证码。。。");
		}
		//注册商户
		String custId = register(param);
		
		if (custId==null || custId.trim().equals("")) {
			throw new TranException("商户ID获取失败");
		}
		
		log.info("开始验证推荐人是否已绑定代理商。。。");
		String agentId ="";
		if (param.get("c_Id") != null && !param.get("c_Id").toString().equals("")) {
			param.put("custId", param.get("c_Id")); //推荐人的id
			agentId = agentService.checkWebAgent(param);
		}
		
		if (agentId== null || agentId.equals("")) {
				agentId = prop.get("SM_DEFAGENT")+"";
		}
		
		log.debug("将商户绑定到推荐人的代理商下");
		log.debug("商户ID {}, 代理商：{}",custId, agentId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("terminalNo", "999999999");
		map.put("custId", custId);  //新注册商户的Id
				//terminalNo
		//j建立表关系
		List<Map<String, Object>> listRef = ageCustDao.selectEntry(map);
		if (listRef != null && listRef.size() >0) {
			log.debug("表中不存在商户代理商关系，添加关系");
			
			ageCustDao.deleteAgeCustInfoDefault(map);
		}
		map.put("terminalId", "999999999");
		map.put("ageId", agentId);
		ageCustDao.insertAgeCustInfo(map);
		
		
		log.debug("查询代理商的分享费率");
		Map<String, String> agentRate = new HashMap<String, String>();
		agentRate.put("custId", agentId);
		agentRate.put("rate_code", "00");
		List<Map<String, Object>> listAgentRate = custRateDao.getAgentRate(agentRate);
		
		
		if (listAgentRate ==null || listAgentRate.size() == 0) {
			throw new TranException("代理商费率没有初始化");
		}
		log.debug("修改商户费率");
		for (int i = 0; i < listAgentRate.size(); i++) {
			log.debug("检查商户费率是否存在！");
			Map<String, String> rate = new HashMap<String, String>();
			Map<String, Object> map1 = listAgentRate.get(i);
			String rateType = map1.get("rateType").toString();
			rate.put("custId", custId);
			rate.put("rate_code", "01");
			rate.put("rateType", rateType);
			List<Map<String, Object>> list = custRateDao.getAgentRate(rate);
			if (list == null || list.size()==0) {
				log.debug("商户费率不存在，开始插入费率。。。");
					try {
						listAgentRate.get(i).put("custId", custId);
						if(rateType.equals("03") || rateType.equals("04")){
							map1.put("rateGeneralTop", map1.get("shareRate").toString());
						}
						custRateDao.insertCustRate(map1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new TranException("费率插入失败");
						
					}
				}else{
					log.debug("商户费率存在，跳过插入。。。");
				}
			}
		
		
		
		msg.setSuccess();
		
		return msg;
	}
	
	

}
