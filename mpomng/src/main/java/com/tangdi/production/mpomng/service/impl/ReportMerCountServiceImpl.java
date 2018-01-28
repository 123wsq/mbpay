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
import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.mpomng.bean.ReportMerCountInf;
import com.tangdi.production.mpomng.dao.NoticeDao;
import com.tangdi.production.mpomng.dao.ReportMerCountDao;
import com.tangdi.production.mpomng.service.ReportMerCountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class ReportMerCountServiceImpl implements ReportMerCountService {
	private static final Logger log = LoggerFactory.getLogger(ReportMerCountServiceImpl.class);
	@Autowired
	private ReportMerCountDao reportMerCountdao;
	
	@Autowired
	private NoticeDao noticeDao;

	private final String MONTH_01 = "01";
	private final String MONTH_02 = "02";
	private final String MONTH_03 = "03";
	private final String MONTH_04 = "04";
	private final String MONTH_05 = "05";
	private final String MONTH_06 = "06";
	private final String MONTH_07 = "07";
	private final String MONTH_08 = "08";
	private final String MONTH_09 = "09";
	private final String MONTH_10 = "10";
	private final String MONTH_11 = "11";
	private final String MONTH_12 = "12";

	@Override
	public Map<String, Object> queryReportMerCount() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String date = TdExpBasicFunctions.GETDATE();
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String month1 = "01";
		String month2 = "06";
		boolean cm = true;
		if (Integer.valueOf(month).intValue() > 6) {
			month1 = "07";
			month2 = "12";
			cm = false;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("year", year);
		paramMap.put("month1", month1);
		paramMap.put("month2", month2);
		log.debug("Map={}", paramMap);
		List<ReportMerCountInf> countList = new ArrayList<ReportMerCountInf>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			countList = reportMerCountdao.selectReportMerCount(paramMap);
			for (ReportMerCountInf reportMerCountInf : countList) {
				String cmonth = reportMerCountInf.getCmonth();
				String cnum = reportMerCountInf.getCnum();
				map.put(cmonth, cnum);
			}
			log.debug("商户注册统计查询数据集Map=[{}]", map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		double count01 = 0;
		double count02 = 0;
		double count03 = 0;
		double count04 = 0;
		double count05 = 0;
		double count06 = 0;
		double count07 = 0;
		double count08 = 0;
		double count09 = 0;
		double count10 = 0;
		double count11 = 0;
		double count12 = 0;

		List<Object> monthList = new ArrayList<Object>();
		List<Object> valueList = new ArrayList<Object>();
		if (cm) {
			if (map.containsKey(MONTH_01)) {
				count01 = Double.parseDouble((String) map.get(MONTH_01));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_01));
			valueList.add(count01);

			if (map.containsKey(MONTH_02)) {
				count02 = Double.parseDouble((String) map.get(MONTH_02));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_02));
			valueList.add(count02);

			if (map.containsKey(MONTH_03)) {
				count03 = Double.parseDouble((String) map.get(MONTH_03));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_03));
			valueList.add(count03);

			if (map.containsKey(MONTH_04)) {
				count04 = Double.parseDouble((String) map.get(MONTH_04));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_04));
			valueList.add(count04);

			if (map.containsKey(MONTH_05)) {
				count05 = Double.parseDouble((String) map.get(MONTH_05));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_05));
			valueList.add(count05);

			if (map.containsKey(MONTH_06)) {
				count06 = Double.parseDouble((String) map.get(MONTH_06));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_06));
			valueList.add(count06);
		} else {
			if (map.containsKey(MONTH_07)) {
				count07 = Double.parseDouble((String) map.get(MONTH_07));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_07));
			valueList.add(count07);

			if (map.containsKey(MONTH_08)) {
				count08 = Double.parseDouble((String) map.get(MONTH_08));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_08));
			valueList.add(count08);

			if (map.containsKey(MONTH_09)) {
				count09 = Double.parseDouble((String) map.get(MONTH_09));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_09));
			valueList.add(count09);

			if (map.containsKey(MONTH_10)) {
				count10 = Double.parseDouble((String) map.get(MONTH_10));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_10));
			valueList.add(count10);

			if (map.containsKey(MONTH_11)) {
				count11 = Double.parseDouble((String) map.get(MONTH_11));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_11));
			valueList.add(count11);

			if (map.containsKey(MONTH_12)) {
				count12 = Double.parseDouble((String) map.get(MONTH_12));
			}
			monthList.add(DateUtil.convertMonthDay(MONTH_12));
			valueList.add(count12);
		}
		resultMap.put("monthList", monthList);
		resultMap.put("valueList", valueList);

		return resultMap;
	}

	@Override
	public Map<String, Object> queryReportMerLiveCount() throws Exception {
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

		countList = reportMerCountdao.selectReportMerLiveCount(paramMap);

		String count01 = "0";
		String count02 = "0";
		String count03 = "0";
		String count04 = "0";
		String count05 = "0";
		String count06 = "0";
		String count07 = "0";

		List<Object> monthList = new ArrayList<Object>();
		List<Object> valueList = new ArrayList<Object>();

		for (Map<String, Object> map : countList) {
			if (map.containsValue(DAY_01)) {
				count01 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_02)) {
				count02 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_03)) {
				count03 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_04)) {
				count04 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_05)) {
				count05 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_06)) {
				count06 = String.valueOf(map.get("prdCount"));
			}

			if (map.containsValue(DAY_07)) {
				count07 = String.valueOf(map.get("prdCount"));
			}
		}
		monthList.add(DateUtil.convertMonthDay(DAY_01));
		valueList.add(count01);

		monthList.add(DateUtil.convertMonthDay(DAY_02));
		valueList.add(count02);

		monthList.add(DateUtil.convertMonthDay(DAY_03));
		valueList.add(count03);

		monthList.add(DateUtil.convertMonthDay(DAY_04));
		valueList.add(count04);

		monthList.add(DateUtil.convertMonthDay(DAY_05));
		valueList.add(count05);

		monthList.add(DateUtil.convertMonthDay(DAY_06));
		valueList.add(count06);

		monthList.add(DateUtil.convertMonthDay(DAY_07));
		valueList.add(count07);

		resultMap.put("monthList", monthList);
		resultMap.put("valueList", valueList);

		return resultMap;

	}
	@Override
	public NoticeInf queryNotice(NoticeInf noticeInf) {
		NoticeInf noticeType = new NoticeInf();
		//查询公告紧急记录
		NoticeInf notice = null;
		try {
			notice = noticeDao.selectInf();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查询商户是否已看过公告
		if(notice !=null){
			noticeInf.setNoticeId(notice.getNoticeId());
		}
		NoticeInf inf = noticeDao.selectType(noticeInf);
		if(inf == null){
			noticeType = notice;
		}
		return noticeType;
	}

	@Override
	public int saveType(NoticeInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = noticeDao.saveType(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}
}
