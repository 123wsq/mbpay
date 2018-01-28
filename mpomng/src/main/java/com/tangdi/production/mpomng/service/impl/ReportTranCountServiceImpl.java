package com.tangdi.production.mpomng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpomng.dao.ReportTranCountDao;
import com.tangdi.production.mpomng.service.ReportTranCountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class ReportTranCountServiceImpl implements ReportTranCountService {
	private static final Logger log = LoggerFactory.getLogger(ReportTranCountServiceImpl.class);
	@Autowired
	private ReportTranCountDao reportTranCountDao;

	@Override
	public Map<String, Object> queryReportTranCount() throws Exception {
		log.info(" 交易额/交易比数 统计(近七天)  begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("date", TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "7"));

		String DAY_01 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "7");
		String DAY_02 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "6");
		String DAY_03 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "5");
		String DAY_04 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "4");
		String DAY_05 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "3");
		String DAY_06 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "2");
		String DAY_07 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "1");

		List<Map<String, Object>> countList = new ArrayList<Map<String, Object>>();

		countList = reportTranCountDao.selectReportTranCount(paramMap);

		String count01 = "0";
		String count02 = "0";
		String count03 = "0";
		String count04 = "0";
		String count05 = "0";
		String count06 = "0";
		String count07 = "0";

		String amtcount01 = "0";
		String amtcount02 = "0";
		String amtcount03 = "0";
		String amtcount04 = "0";
		String amtcount05 = "0";
		String amtcount06 = "0";
		String amtcount07 = "0";

		List<Object> dayList = new ArrayList<Object>();
		List<Object> paycountList = new ArrayList<Object>();
		List<Object> amtList = new ArrayList<Object>();

		for (Map<String, Object> map : countList) {
			if (map.containsValue(DAY_01)) {
				count01 = String.valueOf(map.get("paycount"));
				amtcount01 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_02)) {
				count02 = String.valueOf(map.get("paycount"));
				amtcount02 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_03)) {
				count03 = String.valueOf(map.get("paycount"));
				amtcount03 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_04)) {
				count04 = String.valueOf(map.get("paycount"));
				amtcount04 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_05)) {
				count05 = String.valueOf(map.get("paycount"));
				amtcount05 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_06)) {
				count06 = String.valueOf(map.get("paycount"));
				amtcount06 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_07)) {
				count07 = String.valueOf(map.get("paycount"));
				amtcount07 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}
		}
		dayList.add(DateUtil.convertMonthDay(DAY_01));
		paycountList.add(count01);
		amtList.add(amtcount01);

		dayList.add(DateUtil.convertMonthDay(DAY_02));
		paycountList.add(count02);
		amtList.add(amtcount02);

		dayList.add(DateUtil.convertMonthDay(DAY_03));
		paycountList.add(count03);
		amtList.add(amtcount03);

		dayList.add(DateUtil.convertMonthDay(DAY_04));
		paycountList.add(count04);
		amtList.add(amtcount04);

		dayList.add(DateUtil.convertMonthDay(DAY_05));
		paycountList.add(count05);
		amtList.add(amtcount05);

		dayList.add(DateUtil.convertMonthDay(DAY_06));
		paycountList.add(count06);
		amtList.add(amtcount06);

		dayList.add(DateUtil.convertMonthDay(DAY_07));
		paycountList.add(count07);
		amtList.add(amtcount07);

		resultMap.put("dayList", dayList);
		resultMap.put("paycountList", paycountList);
		resultMap.put("amtList", amtList);

		return resultMap;

	}

	@Override
	public Map<String, Object> queryT0ReportTranCount() throws Exception {
		log.info(" T0提现 统计(近七天)  begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("date", TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "7"));

		String DAY_01 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "7");
		String DAY_02 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "6");
		String DAY_03 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "5");
		String DAY_04 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "4");
		String DAY_05 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "3");
		String DAY_06 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "2");
		String DAY_07 = TdExpBasicFunctions.CALCDATE(TdExpBasicFunctions.GETDATE(), "-", "D", "1");

		List<Map<String, Object>> countList = new ArrayList<Map<String, Object>>();

		List<Object> dayList = new ArrayList<Object>();
		List<Object> paycountList = new ArrayList<Object>();
		List<Object> amtList = new ArrayList<Object>();

		countList = reportTranCountDao.selectT0ReportTranCount(paramMap);

		String count01 = "0";
		String count02 = "0";
		String count03 = "0";
		String count04 = "0";
		String count05 = "0";
		String count06 = "0";
		String count07 = "0";

		String amtcount01 = "0";
		String amtcount02 = "0";
		String amtcount03 = "0";
		String amtcount04 = "0";
		String amtcount05 = "0";
		String amtcount06 = "0";
		String amtcount07 = "0";

		for (Map<String, Object> map : countList) {
			if (map.containsValue(DAY_01)) {
				count01 = String.valueOf(map.get("paycount"));
				amtcount01 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_02)) {
				count02 = String.valueOf(map.get("paycount"));
				amtcount02 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_03)) {
				count03 = String.valueOf(map.get("paycount"));
				amtcount03 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_04)) {
				count04 = String.valueOf(map.get("paycount"));
				amtcount04 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_05)) {
				count05 = String.valueOf(map.get("paycount"));
				amtcount05 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_06)) {
				count06 = String.valueOf(map.get("paycount"));
				amtcount06 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}

			if (map.containsValue(DAY_07)) {
				count07 = String.valueOf(map.get("paycount"));
				amtcount07 = String.valueOf(MoneyUtils.toStrWanYuan(map.get("amt")));
			}
		}
		dayList.add(DateUtil.convertMonthDay(DAY_01));
		paycountList.add(count01);
		amtList.add(amtcount01);

		dayList.add(DateUtil.convertMonthDay(DAY_02));
		paycountList.add(count02);
		amtList.add(amtcount02);

		dayList.add(DateUtil.convertMonthDay(DAY_03));
		paycountList.add(count03);
		amtList.add(amtcount03);

		dayList.add(DateUtil.convertMonthDay(DAY_04));
		paycountList.add(count04);
		amtList.add(amtcount04);

		dayList.add(DateUtil.convertMonthDay(DAY_05));
		paycountList.add(count05);
		amtList.add(amtcount05);

		dayList.add(DateUtil.convertMonthDay(DAY_06));
		paycountList.add(count06);
		amtList.add(amtcount06);

		dayList.add(DateUtil.convertMonthDay(DAY_07));
		paycountList.add(count07);
		amtList.add(amtcount07);

		resultMap.put("dayList", dayList);
		resultMap.put("paycountList", paycountList);
		resultMap.put("amtList", amtList);

		return resultMap;
	}

}
