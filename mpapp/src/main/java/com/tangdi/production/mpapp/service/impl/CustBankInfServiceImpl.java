package com.tangdi.production.mpapp.service.impl;


import java.util.ArrayList;
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
import com.tangdi.production.mpapp.dao.CustBankInfDao;
import com.tangdi.production.mpapp.service.CustBankInfService;
import com.tangdi.production.mpbase.bean.UnionfitInf;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.service.UnionfitService;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;


/**
 * 用户银行卡绑定实现
 * @author zhuji
 * @version 1.0
 *
 */
@Service
public class CustBankInfServiceImpl implements CustBankInfService{
	private static final Logger log = LoggerFactory
			.getLogger(CustBankInfServiceImpl.class);
	
	@Autowired
	private CustBankInfDao custBankInfDao;
	@Autowired
	private GetSeqNoService seqNoService;
	@Value("#{mpappConfig}")
	private Properties prop;	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private UnionfitService unionfitService;
	
	@Override
	public void distribution(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "operType","cardNo","custId");
		if (param.get("operType").equals(MsgST.OPER_TYPE_ADD)) {
			log.debug("银行卡绑定参数数据：{}",param);
			addCustBankInfo(param);
		}else if (param.get("operType").equals(MsgST.OPER_TYPE_EDIT)) {
			log.debug("银行卡修改参数数据：{}",param);
			modifyCustBankInfo(param);
		}else if(param.get("operType").equals(MsgST.OPER_TYPE_REMOVE)){
			log.debug("银行卡解绑参数数据：{}",param);
			removeCustBankInfo(param);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCustBankInfo(Map<String, Object> param)
			throws TranException {
		
		int rt=0;
		try {
			custBankInfDao.deleteTmpEntity(param);
		} catch (Exception e) {
			log.error("清楚上次提交绑定银行卡信息失败");
			throw new TranException(ExcepCode.EX000209, "绑定银行卡异常",e);
		}
		
		Map<String, Object> checkBank=null;
		Map<String , Object> checkTemp=null;
		checkTemp=getCustBankTempInfo(param);
		checkBank=getCustBankInfo(param);
		if (checkBank!=null||checkTemp!=null) {
			throw new TranException(ExcepCode.EX000222, "该商户已绑定该卡");
		}
		UnionfitInf bin=null;
		try {
			bin = unionfitService.getCardInf(param.get("cardNo").toString());
		} catch (Exception e1) {
			throw new TranException(ExcepCode.EX000211, "卡bin查询异常",e1);
		}
		if (bin==null) {
			throw new TranException(ExcepCode.EX000220, "卡号在卡bin不存在");
		}
		if (!bin.getDcflag().equals(MsgST.CARD_TYPE_01)) {
			throw new TranException(ExcepCode.EX000223, "卡类型限定为借记卡");
		}
		try {
			param.put("cardType", bin.getDcflag());
			param.put("issno", bin.getIssno());
			param.put("issnam", bin.getIssnam());
			//标准版中cnapsCode字段为联行号, 改为支行名称。 保存在支行名称表。
			//param.put("subBranch", param.get("cnapsCode"));
			//param.remove("cnapsCode");
			param.put("bindingTime", TdExpBasicFunctions.GETDATETIME());
			
			String bankCardId=TdExpBasicFunctions.GETDATETIME().substring(2)+
					seqNoService.getSeqNoNew("CBANK_ID", "8", "1");
			param.put("bankCardId", bankCardId);
			if(param.get("cardFront")!=null&&!param.get("cardFront").equals("")){
				String cardFront=saveImg((MultipartFile)param.get("cardFront"));
				param.put("bankcardFront", cardFront); 
			}
			if(param.get("cardBack")!=null&&!param.get("cardBack").equals("")){
				String cardBack=saveImg((MultipartFile)param.get("cardBack"));
				param.put("bankcardBack", cardBack);
			}
			param.put("cardState", MsgST.CARD_STATUS_1);
			rt=custBankInfDao.insertEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209, "绑定银行卡异常",e);
		};
		if (rt==0) {
			throw new TranException(ExcepCode.EX000209,"绑定银行卡失败");
		}
		return rt;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyCustBankInfo(Map<String, Object> param)
			throws TranException {
		
		Map<String , Object> selectMap=new HashMap<String, Object>();
		Map<String , Object> isEdited=null;
		selectMap.put("custId", param.get("custId"));
		
		//卡bin查询
		UnionfitInf bin=null;
		try {
			bin = unionfitService.getCardInf(param.get("cardNo").toString());
		} catch (Exception e1) {
			throw new TranException(ExcepCode.EX000211, "卡bin查询异常",e1);
		}
		if (bin==null) {
			throw new TranException(ExcepCode.EX000220, "卡号在卡bin不存在");
		}
		if (!bin.getDcflag().equals(MsgST.CARD_TYPE_01)) {
			throw new TranException(ExcepCode.EX000223, "卡类型限定为借记卡");
		}
		
		param.put("cardType", bin.getDcflag());
		param.put("issno", bin.getIssno());
		param.put("issnam", bin.getIssnam());
		param.put("bindingTime", TdExpBasicFunctions.GETDATETIME());
		
		//图片保存
		try {
			String bankCardId=TdExpBasicFunctions.GETDATETIME().substring(2)+
					seqNoService.getSeqNoNew("CBANK_ID", "8", "1");
			param.put("bankCardId", bankCardId);
			if(param.get("cardFront")!=null&&!param.get("cardFront").equals("")){
				String cardFront=saveImg((MultipartFile)param.get("cardFront"));
				param.put("bankcardFront", cardFront); 
			}
			if(param.get("cardBack")!=null&&!param.get("cardBack").equals("")){
				String cardBack=saveImg((MultipartFile)param.get("cardBack"));
				param.put("bankcardBack", cardBack);
			}
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209, "绑定银行卡异常",e);
		};
		
		param.put("cardState", MsgST.CARD_STATUS_1);
		String custId=param.get("custId").toString();
		String certificateNo=param.get("certificateNo").toString();
		int rt=0;
		String dbCertificateNo = "";
		try {
			 dbCertificateNo = custBankInfDao.selecCertificateNo(custId);
		} catch (Exception e) {
			
		}
		if(dbCertificateNo.equals(certificateNo)){
			//清除商户原先绑定的银行卡：审核通过表或未审核表
			try{
				custBankInfDao.deleteEntity(param);
				custBankInfDao.deleteTmpEntity(param);
				rt=custBankInfDao.insertEntity(param);
				if (rt==0) {
					throw new TranException(ExcepCode.EX000209,"绑定银行卡失败");
				}
			}catch(Exception e){
				log.error("清楚原有绑定银行卡异常");
				throw new TranException(ExcepCode.EX000209, "绑定银行卡异常",e);
			}
		}else{
			log.error("身份证核对失败");
			throw new TranException(ExcepCode.EX010002,"身份证核对失败");
		}
		return rt;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeCustBankInfo(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "operType","cardNo","custId");
		
		int rt=0;
		try {
			custBankInfDao.deleteEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"解绑银行卡异常",e);
		}
		return rt;
	}
	
	@Override
	public Map<String, Object> getCustBankInfo(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId");
		Map<String, Object> resultMap=null;
		try {
			resultMap=custBankInfDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"查询银行卡异常",e);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getCustBankTempInfo(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> newMap=new HashMap<String, Object>();
		newMap.put("custId", param.get("custId"));
		try {
			
			resultMap=custBankInfDao.selectTempEntity(newMap);
			if(null == resultMap){
				return null;
			}
			if(null != resultMap.get("cardState") && resultMap.get("cardState").equals("2")){
				custBankInfDao.deleteTmpEntity(newMap);
				return null;
			}
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"查询银行卡审核表异常",e);
		}
		return resultMap;
	}

	/**
	 * 保持银行卡图片
	 * @param img
	 * @return
	 */
	private String saveImg(String img) throws Exception{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_CARD);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001001,"银行卡图片保存失败!",e);
		}
		if(rmap != null){
			fid = String.valueOf(rmap.get("fid"));
		}else{
			throw new TranException(ExcepCode.EX001001,"返回值对象rmap为空!");
		}
		log.info("文件ID=[{}]",fid);
		return fid;
	}
	private String saveImg(MultipartFile img) throws Exception{
		Map<String,Object> rmap = null;
		String fid = "";
		try{
			rmap = fileUploadService.upload(img,MsgST.PIC_TYPE_CARD);
		}catch(Exception e){
			throw new TranException(ExcepCode.EX001001,"银行卡图片保存失败!",e);
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
	public int getCount(Map<String, Object> param) throws TranException {
		int rt=0;
		try {
			rt=custBankInfDao.selectCount(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"银行卡数量查询异常",e);
		}
		return rt;
	}

	@Override
	public List<Map<String, Object>> getCustBankInfoList(
			Map<String, Object> param) throws TranException {
		List<Map<String , Object>> resultList=null;
		try {
			resultList=custBankInfDao.selectList(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"银行卡查询异常",e);
		}
		return resultList;
	}
	

	@Override
	public List<Map<String, Object>> getCustBankInfoTempList(
			Map<String, Object> param) throws TranException {
		List<Map<String , Object>> resultList=null;
		try {
			resultList=custBankInfDao.selectTempList(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"银行卡审核查询异常",e);
		}
		return resultList;
	}

	@Override
	public int getTempCount(Map<String, Object> param) throws TranException {
		int rt=0;
		try {
			rt=custBankInfDao.selectTempCount(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000209,"银行卡数量查询异常",e);
		}
		return rt;
	}

	@Override
	public String selectErrorReason(String custId)
			throws TranException {
		List<Map<String, Object>> reasonList = new ArrayList<Map<String,Object>>();
		String reason = "";
		try {
			String reasonDes = "";
			reasonList = custBankInfDao.selectErrorReason(custId);
			if(reasonList.size()>0){
				reason = ((Map<String, Object>)reasonList.get(0)).get("defeatReason") + "";
				reasonDes = ((Map<String, Object>)reasonList.get(0)).get("defeatReasonDes") + "";
				if(!"".equals(reason)){
					if("1".equals(reason))
						reason = "图片有误";
					else if("2".equals(reason))
						reason = "支行信息有误";
					else if("3".equals(reason))
						reason = "其他";
					if(!"".equals(reasonDes))
						reason = reason + ":" +reasonDes;
				}
				else if(!"".equals(reasonDes)){
					reason = reasonDes;
				}
			}
		} catch (Exception e) {
			log.error("查询银行卡绑定错误原因出错啦。");
		}
		log.info("银行卡绑定审核拒绝原因:[{}]",reason);
		return reason;
	}

	@Override
	public String selectCardState(String custId) throws TranException{
		String cardStatus = "";
		try {
			cardStatus = custBankInfDao.selectCardState(custId);
		} catch (Exception e) {
			log.error("查询银行卡绑定状态出错啦。");
		}
		log.info("银行卡绑定状态:[{}]",cardStatus);
		return cardStatus;
	}
}
