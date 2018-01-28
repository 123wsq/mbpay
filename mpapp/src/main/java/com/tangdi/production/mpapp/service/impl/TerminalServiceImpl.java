package com.tangdi.production.mpapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.dao.AgeCustDao;
import com.tangdi.production.mpapp.dao.AppFLDescDao;
import com.tangdi.production.mpapp.dao.MerchantDao;
import com.tangdi.production.mpapp.dao.TerminalDao;
import com.tangdi.production.mpapp.service.TerminalService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.TermStepDesc;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.TermStepService;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.context.SpringContext;

/**
 * 终端服务接口实现类
 * 
 * @author shanbeiyi
 *
 */
@Service
public class TerminalServiceImpl implements TerminalService {
	private static Logger log = LoggerFactory.getLogger(TerminalServiceImpl.class);

	@Autowired
	private TerminalDao terminalDao;
	
	@Autowired
	private AgeCustDao ageCustDao;
	
	@Autowired
	private MerchantDao merchantDao;
	
	@Autowired
	private TermStepService termStepService;
	
	@Autowired
	private AppFLDescDao appFlDescDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void binding(Map<String, Object> pmap) throws TranException {
		ParamValidate.doing(pmap, "termNo", "custId");
		// 验证终端是否存在
		String terNo = (String) pmap.get("termNo");
		Map<String, Object> validateMap = new HashMap<String, Object>();
		validateMap.putAll(pmap);
		validateMap.put("terminalNo", terNo);
		Map<String, Object> rst = null;
		try {
			rst = terminalDao.selectEntity(validateMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX030001, e);
		}
		if (rst == null || rst.get("terminalNo") == null) {
			throw new TranException(ExcepCode.EX030001);
		}
		String tstatus = rst.get("terminalStatus").toString();
		log.info("终端号:[{}], 状态:[{}]",terNo,tstatus);
		
		log.info("验证终端的状态.");
		if (MsgST.TERMINALSTATUS.equals(tstatus)){
			throw new TranException(ExcepCode.EX030004);
		}
		if(MsgST.TERMINALSTATUS__BIND.equals(rst.get("terminalStatus"))) {
			throw new TranException(ExcepCode.EX030003);
		}
		// 查看商户是否已绑定终端
		String custId = (String) pmap.get("custId");
		Map<String, Object> Map = new HashMap<String, Object>();
		Map.put("custId", custId);
		List<Map<String, Object>> ter = null;
		try {
			ter = terminalDao.selecttrlEntity(Map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX030001, e);
		}
		log.info("是否跨代理商绑定校验.");
		if (ter.size() > 0 && rst != null) {
			// 商户已绑定过终端
			Map<String, Object> terMap = ter.get(0);
			
			String terAgentId = (String) terMap.get("agentId");
			String nagentid = (String) rst.get("agentId");
			validateMap.put("agentId", nagentid);

			if (!terAgentId.equals(nagentid)) {
				//不能跨代理商绑定终端
				throw new TranException(ExcepCode.EX030002);
			}
		}
		log.info("更新代理商、商户、终端关系.");
		validateMap.put("custId", custId);
		
		try {
			// 1.更新代理商、商户、终端关系
			Map<String, String> catMap = new HashMap<String, String>();
			catMap.put("custId", custId);
			catMap.put("terminalNo", "999999999");
			
			log.debug("查看该商户是否是否存在于  代理商-商户-终端表中");
			ageCustDao.deleteAgeCustInfoDefault(catMap);
			
			catMap.put("terminalNo", terNo);
			ageCustDao.updateAgeCustInfo(catMap);
			// 2.更新终端表
			terminalDao.updateEntity(validateMap);
			
			log.info("记录终端 日志-------------");
			termStepService.saveTermStepDesc(terNo, TermStepDesc.STEP_2, custId);
			
			// 3.更新商户T0提现费率
			merchantDao.udpateTCas(catMap);
			
			//4.更新同一商户下的其他终端费率信息  hg add 20160408
			if(null != custId && !"".equals(custId)){
				rst.put("custId", custId);
				terminalDao.updateOtherEntity(rst);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX030001);
		}
	}

	/**
	 * 查询商户绑定终端列表
	 */
	@Override
	public List<Map<String, Object>> getlist(Map<String, Object> pmap) throws TranException {
		String custId = (String) pmap.get("custId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custId", custId);
		List<Map<String, Object>> terMap = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			terMap = terminalDao.selecttrlEntity(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX030001, e);
		}
		
		if (terMap != null && terMap.size() > 0) {
			log.info("共查询终端：[{}]个",terMap.size());
			// 商户已绑定过终端
				for (Map<String, Object> map2 : terMap) {
					map = new HashMap<String, Object>();
					String comname  =  map2.get("termComName")  == null?"":map2.get("termComName").toString();
					String typename =  map2.get("termTypeName") == null?"":map2.get("termTypeName").toString();
					map.put("agentId", map2.get("agentId"));
					map.put("termNo", map2.get("terminalNo"));
					map.put("termTypeName", comname + " " + typename);
					resultList.add(map);
				}
		}else{
			log.info("没有查询到绑定的终端信息.");
		}
		return resultList;
	}

	@Override
	public Map<String, Object> check(Map<String, Object> pmap) throws TranException {
		Map<String, Object> map = null;
		try {
			map = terminalDao.check(pmap);
			if (map == null) {
				throw new TranException(ExcepCode.EX030001, "终端信息不存在");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TranException(ExcepCode.EX030001, e);
		}
	
		log.debug("查询终端信息:{}", map);
		return map;
	}

	@Override
	public Map<String, Object> calcRateFee(Map<String, Object> param, Map<String, Object> terminalMap) {
		Map<String, Object> rateMap = new HashMap<String, Object>();
		Double prdordAmt_ = Double.valueOf(param.get("payAmt").toString());
		String prdordAmt  = String.valueOf(prdordAmt_.intValue());
		
		log.info("需要计算 手续的金额为:" + prdordAmt_/100 + " 元");
		Integer fee = 0;
		String rate = "";
		if (MsgST.RATE_LIVELIHOOD.equals(param.get("rate"))) {
			log.info("计算手续费 类型为: 民生类");
			rate = terminalMap.get("rateLivelihood").toString();
			fee = MoneyUtils.calculate(rate, prdordAmt,MsgST.FEE_LOW);
			rateMap.put("fee", fee);
			rateMap.put("rate", terminalMap.get("rateLivelihood"));
		} else if (MsgST.RATE_GENERAL.equals(param.get("rate"))) {
			log.info("计算手续费 类型为: 一般类");
			rate = terminalMap.get("rateGeneral").toString();
			fee = MoneyUtils.calculate(rate, prdordAmt,MsgST.FEE_LOW);
			rateMap.put("fee", fee);
			rateMap.put("rate", terminalMap.get("rateGeneral"));
		} else if (MsgST.RATE_ENTERTAIN.equals(param.get("rate"))) {
			log.info("计算手续费 类型为: 餐娱类");
			rate = terminalMap.get("rateEntertain").toString();
			fee = MoneyUtils.calculate(rate, prdordAmt,MsgST.FEE_LOW);
			rateMap.put("fee", fee);
			rateMap.put("rate", terminalMap.get("rateEntertain"));
		} else if (MsgST.RATE_GENERAL_TOP.equals(param.get("rate"))) {
			log.info("计算手续费 类型为: 批发类");
			rate = terminalMap.get("rateGeneralTop").toString().trim();
			Integer maximun = NumberUtils.toInt((String) terminalMap.get("rateGeneralMaximun"));
			fee = MoneyUtils.calculate(rate, prdordAmt,MsgST.FEE_LOW);
			if (fee > maximun) {
				fee = maximun;
			}
			rateMap.put("rate", terminalMap.get("rateGeneralMaximun"));
			rateMap.put("fee", fee);
			
		} else if (MsgST.RATE_ENTERTAIN_TOP.equals(param.get("rate"))) {
			log.info("计算手续费 类型为: 房产汽车类");
			rate = terminalMap.get("rateEntertainTop").toString();
			Integer maximun = NumberUtils.toInt((String) terminalMap.get("rateEntertainMaximun"));
			fee = MoneyUtils.calculate(rate, prdordAmt,MsgST.FEE_LOW);
			if (fee > maximun) {
				fee = maximun;
			}
			rateMap.put("fee", fee);
			rateMap.put("rate", terminalMap.get("rateEntertainTop"));
		}
		log.info("费率类型为:" + rateMap.get("rate") + "  手续费为:" + rateMap.get("fee") + "分");
		return rateMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modiftyKey(Map<String, Object> pmap) throws TranException {
		int rt = 0;
		try {
			log.info("更新密钥数据:{}", pmap);
			rt = terminalDao.updateKey(pmap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000304, e);
		}
		log.info("更新结果:[{}]", rt);
		return rt;
	}

	@Override
	public Map<String, Object> getTermRate(Map<String, Object> pmap)
			throws TranException {
		
		List<Map<String, Object>> rateList = new ArrayList<Map<String, Object>>();
		Map<String, Object> rateMap = null;
		Map<String, Object> rmlist = new HashMap<String, Object> ();
		
		String termNo = pmap.get("termNo").toString();
		//查询刷卡头费率
		List<Map<String, Object>> rateTypeList = queryChlRate(termNo);
		Map<String, Object> rateTypeMap = rateTypeList.get(0);
	
		//民生类
		if(null != rateTypeMap.get("rateLivelihood") && !"".equals(rateTypeMap.get("rateLivelihood"))){
			rateMap = new HashMap<String, Object>();
			rateMap.put("rateNo", MsgST.RATE_LIVELIHOOD);
			String appFlDesc = getAppFlDesc(MsgST.RATE_LIVELIHOOD); 
			rateMap.put("rateDesc", appFlDesc);
			log.debug("设置民生类费率:[{}]",rateMap);
			rateList.add(rateMap);
		}
		//一般类
		if(null != rateTypeMap.get("rateGeneral") && !"".equals(rateTypeMap.get("rateGeneral"))){
			rateMap = new HashMap<String, Object>();
			rateMap.put("rateNo", MsgST.RATE_GENERAL);
			String appFlDesc = getAppFlDesc(MsgST.RATE_GENERAL); 
			rateMap.put("rateDesc", appFlDesc);
			log.debug("设置一般类费率:[{}]",rateMap);
			rateList.add(rateMap);
		}
		//餐娱类
		if(null != rateTypeMap.get("rateEntertain") && !"".equals(rateTypeMap.get("rateEntertain"))){
			rateMap = new HashMap<String, Object>();
			rateMap.put("rateNo", MsgST.RATE_ENTERTAIN);
			String appFlDesc = getAppFlDesc(MsgST.RATE_ENTERTAIN); 
			rateMap.put("rateDesc", appFlDesc);
			log.debug("设置餐娱类费率:[{}]",rateMap);
			rateList.add(rateMap);
		}
		//批发类
		if(null != rateTypeMap.get("rateGeneralTop") && !"".equals(rateTypeMap.get("rateGeneralTop"))){
			rateMap = new HashMap<String, Object>();
			rateMap.put("rateNo", MsgST.RATE_GENERAL_TOP);
			String appFlDesc = getAppFlDesc(MsgST.RATE_GENERAL_TOP); 
			rateMap.put("rateDesc", appFlDesc);
			log.debug("设置批发类费率:[{}]",rateMap);
			rateList.add(rateMap);
		}
		//房产类
		if(null != rateTypeMap.get("rateEntertainTop") && !"".equals(rateTypeMap.get("rateEntertainTop"))){
			rateMap = new HashMap<String, Object>();
			rateMap.put("rateNo", MsgST.RATE_ENTERTAIN_TOP);
			String appFlDesc = getAppFlDesc(MsgST.RATE_ENTERTAIN_TOP); 
			rateMap.put("rateDesc", appFlDesc);
			log.debug("设置房产类费率:[{}]",rateMap);
			rateList.add(rateMap);
		}
		
//		// 0.38 民生类
//		if(rateTypeList.contains(MsgST.RATE_LIVELIHOOD)){
//			rateMap = new HashMap<String, Object>();
//			rateMap.put("rateNo", MsgST.RATE_LIVELIHOOD);
//			rateMap.put("rateDesc", "民生类");
//			log.debug("设置民生类费率:[{}]",rateMap);
//			rateList.add(rateMap);
//		}
//		// 0.78
//		if(rateTypeList.contains(MsgST.RATE_GENERAL)){
//			rateMap = new HashMap<String, Object>();
//			rateMap.put("rateNo", MsgST.RATE_GENERAL);
//			rateMap.put("rateDesc", "一般类");
//			log.debug("设置一般类费率:[{}]",rateMap);
//			rateList.add(rateMap);
//		}
//		// 1.25
//		if(rateTypeList.contains(MsgST.RATE_ENTERTAIN)){
//			rateMap = new HashMap<String, Object>();
//			rateMap.put("rateNo", MsgST.RATE_ENTERTAIN);
//			rateMap.put("rateDesc", "餐娱类");
//			log.debug("设置餐娱类费率:[{}]",rateMap);
//			rateList.add(rateMap);
//		}
//		// 0.78封顶
//		if(rateTypeList.contains(MsgST.RATE_GENERAL_TOP)){
//			rateMap = new HashMap<String, Object>();
//			rateMap.put("rateNo", MsgST.RATE_GENERAL_TOP);
//			rateMap.put("rateDesc", "批发类 封顶");
//			log.debug("设置批发类费率:[{}]",rateMap);
//			rateList.add(rateMap);
//			
//		}
//		// 1.25封顶
//		if(rateTypeList.contains(MsgST.RATE_ENTERTAIN_TOP)){
//			rateMap = new HashMap<String, Object>();
//			rateMap.put("rateNo", MsgST.RATE_ENTERTAIN_TOP);
//			rateMap.put("rateDesc", "房产类");
//			log.debug("设置房产类费率:[{}]",rateMap);
//			rateList.add(rateMap);
//		}
		
		
		rmlist.put("rateList", rateList);
		return rmlist;
	}

	@Override
	public List<Map<String, Object>> queryChlRate(String termNo) throws TranException {
		log.info("查询刷卡头的费率类型...");
		List<Map<String, Object>> rateList = new ArrayList<Map<String, Object>>();
		try {
			rateList = terminalDao.selectTermRate(termNo);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX030007,"终端未查询到相关费率信息。",e);
		}
		if(rateList == null || rateList.size() <=0){
			throw new TranException(ExcepCode.EX030007,"该设备未设置费率信息。");
		}
		Map<String, Object> isnull = rateList.get(0);
		if(isnull == null){
			throw new TranException(ExcepCode.EX030007,"该设备未设置费率信息。");
		}
		log.info("查询完成[{}]",rateList);
		return rateList;
	}
	
	@Override
	public String queryMacAddress(String custId) throws TranException {
		log.info("查询刷卡头蓝牙地址");
		String macAddress = "";
		try {
			macAddress = terminalDao.selectMacAddress(custId);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000305, e);
		}
		log.info("查询完成[{}]",macAddress);
		return macAddress;
	}
	
	private String getAppFlDesc(String flType) throws TranException{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("flType", flType);
		String appFlDesc = ""; 
		try {
			appFlDesc = appFlDescDao.selectAppFlDesc(param).get("flAppDesc").toString();
		} catch (Exception e) {
			throw new TranException(e);
		}
		return appFlDesc;
	}
}
