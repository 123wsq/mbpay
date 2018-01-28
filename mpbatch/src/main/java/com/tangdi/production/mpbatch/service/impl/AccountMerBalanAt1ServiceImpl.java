package com.tangdi.production.mpbatch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.dao.HolidayRuleInfDao;
import com.tangdi.production.mpbatch.service.AccountMerBalanAt1Service;
import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.mpomng.dao.CasPrdDao;
import com.tangdi.production.mpomng.dao.CustAccountDao;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 根据商户余额 清算跑批 实现
 * 
 * @author caojf
 *
 */
@Service
public class AccountMerBalanAt1ServiceImpl implements AccountMerBalanAt1Service {
	private static Logger log = LoggerFactory.getLogger(AccountMerBalanAt1ServiceImpl.class);

	@Autowired
	private CustAccountDao CustAccountDao;

	@Autowired
	private CasPrdService casPrdService;
	@Autowired
	private CasPrdDao casPrdDao;
	
	@Autowired
	private HolidayRuleInfDao holidayRuleInfDao;

	@Autowired
	private CustAccountService custAccountService;
	
	/**
	 * 获取工作日
	 * 
	 * @param date
	 * @return false 不是工作日 true 是工作日
	 */
	private boolean isWorkDay(Map<String, Object> map) {
		boolean isHoliday = DateUtil.getWorkDay();
		map.put("date", DateUtil.convertDateToString(new Date(), "yyyyMMdd"));
		try {
			Map<String, Object> isHolidayMap = holidayRuleInfDao.selectIsHoliday(map);
			if (isHolidayMap != null && isHolidayMap.size() > 0) {
				if ("0".equals(isHolidayMap.get("t0Status"))) {
					// 0:不是节假日
					isHoliday = true;
				}
				if ("1".equals(isHolidayMap.get("t0Status"))) {
					// 1是节假日
					isHoliday = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isHoliday;
	}

	@Override
	public void processAccount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (!StringUtils.isEmpty(map.get("cp"))) {
			// 重跑 不备份 余额表 直接使用备份表数据 重新生成订单
			flag = false;
		}
		if (isWorkDay(map)) {// 判断今天是否是 工作日
			log.info("跑批  商户余额  清算     begin");

			Map<String, Object> paramp = new HashMap<String, Object>();
			if (flag) {
				// 备份之前清除 上一次 备份的数据
				CustAccountDao.clearCopyAccount(map);

				// 备份账户余额表到备份表 每天备份一次
				CustAccountDao.copyAccount(map);
			}
			// 查询备份表所有账户信息 获取所需数据
			List<Map<String, Object>> list = getCustCopyAccount(map);

			CustAccountInf cust = new CustAccountInf();
			log.info("共查询清算账户:[{}]", list.size());
			for (Map<String, Object> m : list) {
				String at1 = m.get("acT1Y").toString();
				paramp.put("casType", "03"); // aT1 自动 提现
				paramp.put("custId", m.get("custId"));
				paramp.put("custName", m.get("custName"));
				paramp.put("txamt", at1);
				paramp.put("casDate", TdExpBasicFunctions.GETDATETIME());
				paramp.put("sucDate", TdExpBasicFunctions.GETDATETIME());
				paramp.put("rate", "0");
				paramp.put("fee", "0");
				paramp.put("serviceFee", "0");
				paramp.put("netrecamt", m.get("acT1Y"));
				paramp.put("issno", m.get("issno"));
				paramp.put("issnam", m.get("issnam"));
				paramp.put("cardNo", m.get("cardNo"));
				paramp.put("provinceId", m.get("provinceId"));
				paramp.put("casDesc", "");
				paramp.put("auditDesc", "系统自动审核");
				paramp.put("t0Amt", 0);
				paramp.put("t1Amt", at1);
				paramp.put("acType", m.get("acType"));
				// 生成订单 at1 自动提现
				if(!"0".equals(at1)){
					log.info("准备订单数据:{}", paramp);
					casPrdService.addAccount(paramp);
					// 将根据CustId清零所有账户余额 l
					cust.setCustId(m.get("custId").toString());
					double acBal = Double.parseDouble(m.get("acBal").toString()) - Double.parseDouble(at1);
					cust.setAcBal(String.valueOf(acBal));
					cust.setAcType(m.get("acType").toString());
					
					CustAccountDao.emprtCustAccount(cust);
					// 更新余额信息记录变更表
					Map<String, Object> updateAccountMap = new HashMap<String, Object>();
					updateAccountMap.put("t0", "0");
					updateAccountMap.put("t1", "0");
					updateAccountMap.put("custId", m.get("custId"));
					updateAccountMap.put("acType", m.get("acType"));
					custAccountService.at1ToBlanceChange(updateAccountMap);
					log.info("商户[{}]清算完成.", m.get("custName"));
				}
				
			}
			log.info("跑批  商户余额  清算     end");
		} else {
			log.info("跑批  节假日  商户余额 不执行 清算  跳过！   ");
		}

	}

	@Override
	public List<Map<String, Object>> getCustCopyAccount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = CustAccountDao.selectCustCopyAccounts(map);
		return list;
	}

	@Override
	public int emptyAccountAt1(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub emptyAccountAt1
		return casPrdDao.emprtAccounts(map);
	}

	@Override
	public void processCas(Map<String, Object> map) throws Exception {
		boolean flag = true;
		if (!StringUtils.isEmpty(map.get("cp"))) {
			// 重跑 不备份 余额表 直接使用备份表数据 重新生成订单
			flag = false;
		}
		if (DateUtil.getWorkDay()) {// 判断今天是否是 工作日
			log.info("进入提现订单生成批处理：批处理方案二");
			// 生成at1提现订单
			// 跑批订单截止日期

			map.put("endTime", DateUtil.getYesterday() + "240000");
			// 判断是否周一，周一则开始日期为4天前，否则为一天前
			if (DateUtil.getFirstWorkDay()) {
				map.put("startTime", DateUtil.getFourdayAgo() + "240000");
			} else {
				map.put("startTime", DateUtil.getYesterday(DateUtil.getYesterday()) + "240000");
				// map.put("endTime", DateUtil.getDay()+"240000");
			}
			log.info("批处理参数：" + map);
			// 累计商户收款订单金额
			List<Map<String, Object>> payMapList = CustAccountDao.selectPayAmt(map);
			for (Map<String, Object> payMap : payMapList) {
				// 根据商户号累计已提现金额
				map.put("custId", payMap.get("custId"));
				Map<String, Object> casMap = CustAccountDao.selectCasAmt(map);
				// 提现订单数据拼装
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.putAll(payMap);
				// 金额
				Double payAmt = Double.parseDouble(String.valueOf(payMap.get("payAmt")));
				Double casAmt = Double.parseDouble(String.valueOf(casMap.get("casAmt")));
				tempMap.put("txamt", payAmt - casAmt);
				tempMap.put("netrecamt", payAmt - casAmt);
				tempMap.put("t0Amt", 0);
				tempMap.put("t1Amt", payAmt - casAmt);
				tempMap.put("casDate", TdExpBasicFunctions.GETDATETIME());
				tempMap.put("sucDate", TdExpBasicFunctions.GETDATETIME());
				tempMap.put("rate", "0");
				tempMap.put("fee", "0");
				tempMap.put("serviceFee", "0");
				tempMap.put("casDesc", "");
				tempMap.put("auditDesc", "系统自动审核");
				tempMap.put("casType", "03");
				// 插入提现订单
				casPrdService.addCas(tempMap);

				// 更改账户余额
				CustAccountDao.changeAccount(tempMap);

				// 变更余额记录
				// TODO 提取

			}
			// 2、账户余额减去批处理生成的t1提现订单 --- 账户余额-（累计收款-累计提现）

		} else {
			log.info("跑批  节假日  不执行清算  跳过！   ");
		}

	}

}
