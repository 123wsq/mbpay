package com.tangdi.production.mpaychl.schedule.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.tangdi.production.mpaychl.dao.HolidayRuleInfDao;
import com.tangdi.production.mpaychl.dao.MessageSendDao;
import com.tangdi.production.mpaychl.front.service.SmsFrontService;
import com.tangdi.production.mpaychl.front.service.impl.SmsFrontServiceImpl;
import com.tangdi.production.mpaychl.schedule.service.MessageSendService;
import com.tangdi.production.mpbase.util.DateUtil;

@Service
public class MessageSendServiceImpl implements MessageSendService{

	@Autowired
	private HolidayRuleInfDao holidayRuleInfDao;
	@Autowired
	private MessageSendDao messageSendDao;
	
	/**
	 * 短信前置接口
	 */
	@Autowired
	private SmsFrontService smsFrontService;
	
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
	public void process(Map<String, Object> map) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> param1 = new HashMap<String, Object>();
		
		if(!isWorkDay(map)){
			//如果是节假日，查询审核中的订单数量
			Integer num1 = messageSendDao.selectAccount(map);
			//节假日，查询未审核的商户银行卡数量
			Integer num2 = messageSendDao.selectBankCard(map);
			//查询订单审核短信接收人的手机号
			List<String> tel = messageSendDao.selectAuditor();
			for(int i=0;i<tel.size();i++){
				param.put("custMobile", tel.get(i));
				
				//编写短信的内容
				String message = "当前系统中有 "+num1+" 笔未审核订单，请您及时处理";
				param.put("smsdata", message);
				String messa = "当前系统中有"+num2+"张未审核的商户银行卡，请您及时处理";
				
				
				param.put("seq", "");
				if(num1>0 && tel.size()>0){
					try{
						smsFrontService.sendsms(param);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			//查询商户审核短信接收人的手机号
			List<String> tele = messageSendDao.selectCustAuditor();
			for(int i=0;i<tele.size();i++){
				param1.put("custMobile", tele.get(i));
				
				//编写短信的内容
				String messa = "当前系统中有"+num2+"张未审核的商户银行卡，请您及时处理";
				param1.put("smsdata", messa);
				
				param1.put("seq", "");
				if(num2>0 && tele.size()>0){
					try{
						smsFrontService.sendsms(param1);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	

}
