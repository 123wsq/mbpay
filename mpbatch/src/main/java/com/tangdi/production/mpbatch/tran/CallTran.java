package com.tangdi.production.mpbatch.tran;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbatch.service.AccountMerBalanAt1Service;
import com.tangdi.production.mpbatch.service.AgentDayShareAmtService;
import com.tangdi.production.mpbatch.service.AgentMonthShareAmtService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;

/**
 * 调用分润、清算
 * 
 * 
 * @author limiao
 */
@Component
@IProcess
public class CallTran {
	private static Logger log = LoggerFactory.getLogger(CallTran.class);

	@Autowired
	AgentDayShareAmtService agentDayShareAmtService;

	@Autowired
	AgentMonthShareAmtService agentMonthShareAmtService;

	@Autowired
	AccountMerBalanAt1Service accountMerBalanAt1Service;

	@ICode(TranCode.TRAN_CALL_DAYSHARE)
	public void trans_080001(Map<String, Object> param) throws Exception {
		log.info("param 参数{}", param);
		// 清除日分润表中的数据
		agentDayShareAmtService.emptySharingLogDay(param);
		// 执行日分润跑批 处理
		agentDayShareAmtService.process(param);
		log.info("日分润重跑完成！");
	}

	@ICode(TranCode.TRAN_CALL_MONTHSHARE)
	public void trans_080002(Map<String, Object> param) throws Exception {
		log.info("param 参数{}", param);
		// 清除月分润表中的数据
		agentMonthShareAmtService.emptyMonthSharingLog(param);
		String lastDate = param.get("date") + "31";
		String firstDate = param.get("date") + "01";
		param.put("lastDate", lastDate);// 保存月最后一天
		param.put("firstDate", firstDate);// 保存月第一天
		// 执行月分润跑批 处理
		agentMonthShareAmtService.process(param);
		log.info("月分润重跑完成！");
	}

	@ICode(TranCode.TRAN_CALL_SETTLE)
	public void trans_080003(Map<String, Object> param) throws Exception {
		log.info("param 参数{}", param);
		// String date=param.get("date").toString();
		param.put("cp", "1");// 重跑标识
		// if(StringUtils.isEmpty(date)){//如果为空跑当前日期
		String date = TdExpBasicFunctions.CALCTIME(TdExpBasicFunctions.GETDATETIME(), "-", "M", "0").substring(0, 8);
		// }
		param.put("casDate", date);// 重跑日期
		// 清除提现订单中的数据
		accountMerBalanAt1Service.emptyAccountAt1(param);
		// 执行商户余额清算 跑批处理
		accountMerBalanAt1Service.processAccount(param);
		log.info("商户余额清算重跑完成！");
	}

}
