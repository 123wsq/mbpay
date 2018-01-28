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

import com.tangdi.production.mpbatch.dao.AgentProfitDayLogDao;
import com.tangdi.production.mpbatch.dao.AgentProfitMonthLogDao;
import com.tangdi.production.mpbatch.dao.ProfitsharingDao;
import com.tangdi.production.mpbatch.service.AgentMonthShareAmtService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author limiao
 */
@Service
public class AgentMonthShareAmtServiceImpl implements AgentMonthShareAmtService {
	private static Logger log = LoggerFactory.getLogger(AgentMonthShareAmtServiceImpl.class);
	@Autowired
	private ProfitsharingDao profitsharingDao;
	@Autowired
	private AgentProfitDayLogDao profitDayDao;

	@Autowired
	private AgentProfitMonthLogDao profitMonthDao;

	@Value("#{mpbatchConfig}")
	private Properties prop;

	/**
	 * 分润跑批处理 每月1日进行 1.查询代理商当月可分润金额总额，根据id分组 2.根据id查询代理商总额处于何种分润比区间 3.插入代理商分润记录表
	 * 
	 * @throws Exception
	 */
	public void process(Map<String, Object> param) throws Exception {
		log.info("查询代理商当月可分润金额总额{}", param);
		log.info("开始查询代理商。。。。");
		List<Map<String, Object>> results = profitDayDao.selectDayLogList(param);

		for (Map<String, Object> map : results) {
			log.info("代理商汇总信息：{}", map);
			String date =(String) param.get("date");
			if(StringUtils.isEmpty(date)){
				date =TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(),"-","M","1").substring(0, 6);
			}
			map.put("sharDate",date);// 统计分润年月

			Double payamt_ = Double.parseDouble(String.valueOf(map.get("payAmt")))/1000000;
			Double payFee = Double.parseDouble(String.valueOf(map.get("payFee")));
			Double agentFee = Double.parseDouble(String.valueOf(map.get("agentFee")));

			Integer payAmt= payamt_.intValue();
			log.info("月交易总额：{}万元=====查询分润阶梯使用", payAmt);
			map.put("payAmt", payAmt);
			map.put("rateType", map.get("payType"));
			// 查询当前代理所属分润阶梯
			Map<String, Object> stage = profitsharingDao.selectStage(map);
			Double payamt=payamt_*1000000;
			log.info("月交易总额：{}分========存储到月分润表", payamt);
			Double rate = Double.parseDouble(String.valueOf(stage.get("rates")));
			log.info("分润比：{}", rate);
			Double sharamt = Double.parseDouble(String.valueOf(map.get("sharAmt")));
			log.info("月分润总金额：{}分", sharamt);
			Integer sumSharamt = sharamt.intValue() * rate.intValue() / 10;
			log.info("计算后月分润总金额：{}分", sumSharamt);

			map.put("sharAmt", sharamt.intValue());
			map.put("payAmt", payamt.intValue());
			map.put("payFee", payFee.intValue());
			map.put("agentFee", agentFee.intValue());
			map.put("sharOutAmt", sumSharamt);
			map.put("sharProfitRatio", rate.intValue());
			map.put("auditUser", "");
			map.put("updateTime", "");

			map.put("status", 0);
			log.info("插入月份润表数据{}", map);
			profitsharingDao.insertMonthLog(map);

		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int emptyMonthSharingLog(Map<String, Object> param) throws Exception {
		return profitsharingDao.deleteProfitMonthLog(param);
	}

}
