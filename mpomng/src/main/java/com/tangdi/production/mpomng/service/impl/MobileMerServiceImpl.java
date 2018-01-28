package com.tangdi.production.mpomng.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.CustRatesInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.AgentInfDao;
import com.tangdi.production.mpomng.dao.CustRateDao;
import com.tangdi.production.mpomng.dao.MobileMerDao;
import com.tangdi.production.mpomng.report.MobileMerTemplate;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;


/**
 * 手机商户接口实现类
 * @author zhengqiang
 *
 */
@Service
public class MobileMerServiceImpl implements MobileMerService{
	private static final Logger log = LoggerFactory
			.getLogger(MobileMerServiceImpl.class);
	@Autowired
	private MobileMerDao dao;
	
	@Autowired
	private AgentInfDao agentInfDao;
	
	@Autowired
	private FileReportService<MobileMerTemplate> reportService;
	
	
	@Autowired
	private CustRateDao custRateDao;

	@Override
	public List<MobileMerInf> getListPage(MobileMerInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(MobileMerInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public MobileMerInf getEntity(MobileMerInf entity) throws Exception {
		log.debug("参数："+entity.debug());
		MobileMerInf mer = null;
		try{
			mer = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询手机商户信息异常!"+e.getMessage(),e);
		}
		log.debug(mer.debug());
		return mer;
	}

	@Override
	public MobileMerInf getAgentEntity(MobileMerInf entity) throws Exception {
		log.debug("参数："+entity.debug());
		MobileMerInf mer = null;
		try{
			mer = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询代理商信息异常!"+e.getMessage(),e);
		}
		log.debug(mer.debug());
		return mer;
	}
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(MobileMerInf entity) throws Exception {
		return dao.insertEntity(entity);
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(MobileMerInf entity) throws Exception {
		return dao.updateEntity(entity);
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(MobileMerInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyMobileMerStatus(Map<String, Object> map)
			throws Exception {
		log.info("更新商户状态值:[{}]",map);
		return dao.updateMobileMerStatus(map);
	}

	@Override
	public int report(HashMap<String, Object> con,String uid) throws Exception {
		List<MobileMerTemplate> data = null;
		
		try{	
			if(con.get("startTime")!=null&&!"".equals(con.get("startTime"))){
				con.put("startTime",DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(con.get("startTime").toString(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			con.put("startTime",null);
		}
		try{	
			if(con.get("endTime")!=null&&!"".equals(con.get("endTime"))){
				con.put("endTime",DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(con.get("endTime").toString(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD));
			}
		}catch(Exception e){
			con.put("endTime",null);
		}
		try {
			data = dao.selectReport(con);
			
			
		} catch (Exception e) {
			log.error("生成手机商户信息报表出错！{}", e.getMessage());
			throw new Exception(e);
		}
		return reportService.report(data, uid, "手机商户信息报表", "手机商户信息报表", CT.REPORT_TYPE_2, CT.FILE_TYPE_EXCEL,null);
		
		/*List<MobileMerInf> data = null;
		try{
			data = dao.selectReport(con);
		}catch(Exception e){
			log.error("查询报表数据出错！{}",e.getMessage());
			throw new Exception(e);
		}
		return reportService.report(data,uid,"手机商户信息报表",null, MsgCT.REPORT_TYPE_2 ,MsgCT.FILE_TYPE_CSV,null);*/
	}

	@Override
	public List<MobileMerInf> queryMobileMerAgent(MobileMerInf mobileMerInf)
			throws Exception {
		List<MobileMerInf> resultList=null;
		try{
			resultList=dao.selectEntityByAgent(mobileMerInf);
		}catch(Exception e){
			log.error("代理商商户查询出错！{}",e.getMessage());
			throw new Exception(e);
		}
		return resultList;
	}
	@Override
	public int getCountAgent(MobileMerInf mobileMerInf)
			throws Exception {
		int total=0;
		try{
			total=dao.selectCountByAgent(mobileMerInf);
		}catch(Exception e){
			log.error("代理商商户查询总数出错！{}",e.getMessage());
			throw new Exception(e);
		}
		return total;
	}



//	@Override
	public int queryCustById(String custId) throws Exception {
		int total=0;
		try{
			total=dao.validate(custId);
		}catch(Exception e){
			log.error("校验商户信息出错了{}",e.getMessage());
			throw new Exception(e);
		}
		return total;
	}

	/**
	 * 校验费率
	 * */
	@Override
	public boolean checkRateForT(Map<String,Object> param) throws Exception {
		
		Map<String,Object> map = agentInfDao.selectAgentRate(param);
		
		String rateTCas = map.get("rateTCas")==null?"":map.get("rateTCas").toString();
		String maxTCas = map.get("maxTCas")==null?"":map.get("maxTCas").toString();
		
		Double agentRate = Double.parseDouble(rateTCas);
		Double custRate = Double.parseDouble(String.valueOf(param.get("custRate")));
		if(agentRate>custRate){
			return false;
		}
		
		Double agentMax = Double.parseDouble(maxTCas);
		Double custMax = Double.parseDouble(String.valueOf(param.get("maxTCas")));
		
		if(custMax>agentMax){
			return false;
		}
		
		return true;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int updateCustRate(CustRatesInf custRatesInf) throws Exception {
		// TODO Auto-generated method stub
		
		log.debug("获取上级代理商的费率");
		Map<String, Object> param = new HashMap<String, Object>();
	
		param.put("custId", custRatesInf.getCustId());
		param.put("rateCode", "00");
		param.put("rateType", custRatesInf.getRateType());
		
		//查看上级代理商的费率
		Map<String, Object> agentRate = custRateDao.selectCustUpRate(param);
		log.debug("查询的上级代理商费率：   {}", agentRate);
		
		if (agentRate ==null || agentRate.size()==0) {
			throw new TranException("代理商费率没有初始化");
		}
		
		log.debug("验证代理商费率");
		validateFee(custRatesInf, agentRate);
		
		log.debug("费率验证通过， 开始修改商户费率");
		custRatesInf.setUpdateTime(TdExpBasicFunctions.GETDATETIME());
		custRatesInf.setInsertTime(TdExpBasicFunctions.GETDATETIME());
		log.debug("查看在商户费率是否存在");
		param.put("rateCode", custRatesInf.getRateCode());
		CustRatesInf rates = custRateDao.selectEntry(param);
		
		if (rates == null || rates.getCustId().equals("")) {
			log.debug("费率表中没有商户费率数据，添加费率");
			custRateDao.insertEntry(custRatesInf);
		}else{
			custRateDao.updateRate(custRatesInf);
		}
		return 0;
	}

	@Override
	public CustRatesInf queryCustRate(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		CustRatesInf rates = custRateDao.selectEntry(param);
		return rates;
	}

	@Override
	public void validateFee(CustRatesInf caCustRatesInf, Map<String, Object> agentFee) throws Exception {
		// TODO Auto-generated method stub
		
		double rateTCas = Double.parseDouble(caCustRatesInf.getRateTCas()==null ||caCustRatesInf.getRateTCas().equals("") ? "0" :caCustRatesInf.getRateTCas());
		double rateLivelihood = Double.parseDouble(caCustRatesInf.getRateLivelihood()==null ||caCustRatesInf.getRateLivelihood().equals("") ? "0" :caCustRatesInf.getRateLivelihood());
		double rateGeneral = Double.parseDouble(caCustRatesInf.getRateGeneral()==null ||caCustRatesInf.getRateGeneral().equals("") ? "0" :caCustRatesInf.getRateGeneral());
		double rateEntertain = Double.parseDouble(caCustRatesInf.getRateEntertain()==null ||caCustRatesInf.getRateEntertain().equals("") ? "0" :caCustRatesInf.getRateEntertain());
		double rateGeneralTop = Double.parseDouble(caCustRatesInf.getRateGeneralTop()==null ||caCustRatesInf.getRateGeneralTop().equals("") ? "0" :caCustRatesInf.getRateGeneralTop());
		double rateEntertainTop = Double.parseDouble(caCustRatesInf.getRateEntertainTop()==null ||caCustRatesInf.getRateEntertainTop().equals("") ? "0" :caCustRatesInf.getRateEntertainTop());
		
		double feeSum = rateTCas + rateLivelihood + rateGeneral+ rateEntertain +rateGeneralTop + rateEntertainTop;
		if (Double.parseDouble(agentFee.get("fee").toString()) > feeSum) {
			throw new Exception("商户费率不能低于代理商费率");
		}
		
		log.debug("验证封顶金额以及T0单笔提现费率");
		double maxTCas = Double.parseDouble(caCustRatesInf.getMaxTCas()==null ||caCustRatesInf.getMaxTCas().equals("") ? "0" :caCustRatesInf.getMaxTCas());
		if (Double.parseDouble(agentFee.get("maxTCas").toString()) > maxTCas) {
			throw new Exception("商户T0提现费率不能低于代理商费率");
		}
		double rateGeneralMaximun = Double.parseDouble(caCustRatesInf.getRateGeneralMaximun()==null ||caCustRatesInf.getRateGeneralMaximun().equals("") ? "0" :caCustRatesInf.getRateGeneralMaximun());
		if (Double.parseDouble(agentFee.get("rateGeneralMaximun").toString()) > rateGeneralMaximun) {
			throw new Exception("商户批发类金额不能低于代理商费率");
		}
		
		double rateEntertainMaximun = Double.parseDouble(caCustRatesInf.getRateEntertainMaximun()==null ||caCustRatesInf.getRateTCas().equals("") ? "0" :caCustRatesInf.getRateEntertainMaximun());
		if (Double.parseDouble(agentFee.get("rateEntertainMaximun").toString()) > rateEntertainMaximun ) {
			throw new Exception("房产类封顶金额不能低于代理商费率");
		}
	}

}
