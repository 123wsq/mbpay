package com.tangdi.production.mpbatch.service.impl;

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
import org.springframework.util.StringUtils;

import com.tangdi.production.mpbatch.dao.ProfitsharingDao;
import com.tangdi.production.mpbatch.service.AgentDayShareAmtService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author youdd
 */
@Service
public class AgentDayShareAmtServiceImpl implements AgentDayShareAmtService{
	private static Logger log = LoggerFactory.getLogger(AgentDayShareAmtServiceImpl.class);
	@Autowired
	private ProfitsharingDao profitsharingDao;
	
	@Value("#{mpbatchConfig}")
	private Properties prop;
	/**
	 * 日分润跑批处理  每日进行
	 * 1.插入代理商日分润表
	 * @throws Exception
	 */
	public void process(Map<String , Object > param) throws Exception{
		log.info("查询代理商当日可分润金额总额{}", param);
		String date =(String) param.get("date");
		if(StringUtils.isEmpty(date)){
			date =TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(),"-","D","1").substring(0, 8);
		}
		param.put("date", date);
		log.info("查询昨天的日期"+date);
		List<Map<String, Object>> results = profitsharingDao.selectDayLog(param);
		
		for (Map<String, Object> map : results) {
			map.put("date", date);
		    //查询金额
			Double payamt_ = Double.parseDouble(String.valueOf(map.get("payamt")))/1000000;
			Integer payamt= payamt_.intValue();
			log.info("日交易总额：{}万元", payamt);
			map.put("payAmt", payamt);
			map.put("rateType", map.get("payType"));
			// 查询当前代理所属分润阶梯
			Map<String, Object> stage = profitsharingDao.selectStage(map);
			map.put("payAmt", payamt_*1000000);
			//分润比
			Double rate = Double.parseDouble(String.valueOf(stage.get("rates")));
			log.info("分润比：{}", rate);
			map.put("rate", rate);
			Double sharamt = Double.parseDouble(String.valueOf(map.get("sharamt")));
			log.info("日分润总金额：{}分", sharamt);
			map.put("sharAmt", sharamt);
			Integer sumSharamt = sharamt.intValue() * rate.intValue() / 10;
			
			log.info("计算后日分润总金额：{}分", sumSharamt);
			map.put("sharAmtReal", sumSharamt);
			log.info("计算代理商分润开始，参数："+map);
			profitsharingDao.insertDayLog(map);
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int emptySharingLogDay(Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		//String date =TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(),"-","M","0").substring(0, 8);
		//map.put("date", date);
		return profitsharingDao.deleteDayLog(map);
	}


	@Override
	public int emptySharingLog() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
