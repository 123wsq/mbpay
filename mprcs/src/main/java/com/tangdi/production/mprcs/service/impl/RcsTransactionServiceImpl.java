package com.tangdi.production.mprcs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mprcs.bean.BankCardBlacklistInf;
import com.tangdi.production.mprcs.bean.CustLimitInf;
import com.tangdi.production.mprcs.bean.DefaultLimitInf;
import com.tangdi.production.mprcs.constants.RcsConstants;
import com.tangdi.production.mprcs.dao.RcsTransactionDao;
import com.tangdi.production.mprcs.service.BankCardBlacklistService;
import com.tangdi.production.mprcs.service.CustLimitService;
import com.tangdi.production.mprcs.service.DefaultLimitService;
import com.tangdi.production.mprcs.service.OrgTermLimitService;
import com.tangdi.production.mprcs.service.RcsTransactionService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 交易风控控制
 * 
 * @author zhengqiang 2015/09/12
 *
 */
@Service
public class RcsTransactionServiceImpl implements RcsTransactionService {
	private static final Logger log = LoggerFactory.getLogger(RcsTransactionServiceImpl.class);

	@Autowired
	private RcsTransactionDao rcsTransactionDao;

	@Autowired
	private BankCardBlacklistService bankCardBlacklistService;

	@Autowired
	private CustLimitService custLimitService;

	@Autowired
	private DefaultLimitService defaultLimitService;

	@Autowired
	private OrgTermLimitService orgTermLimitService;

	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public boolean checkTxnLimit(final Map<String, Object> paramMap) throws TranException {
		log.info("【风控】进入交易限额检查...");
		log.info("【风控】开始限额检查必要参数验证，参数列表：" + paramMap.toString());
		// 限额所需参数检查
		//ParamValidate.doing(paramMap, "custId", "cardNo", "bizType", "termNo", "payType", "payAmt");
		ParamValidate.doing(paramMap, "custId", "bizType", "termNo", "payType", "payAmt");

		doCheck(paramMap);
		return true;
	}

	@Override
	public boolean checkCASLimit(Map<String, Object> param) throws TranException {
		log.info("【风控】进入提现限额检查...");
		log.info("【风控】开始限额检查必要参数验证，参数列表：" + param.toString());

		// 限额所需参数检查
		ParamValidate.doing(param, "custId", "txnAmt");

		log.info("【风控】：开始检查限额.");
		String custId = param.get("custId").toString(); // 商户号
		String txamt = param.get("txnAmt").toString(); // 提现金额
		String casAmtTotal = param.get("totalTxnAmt").toString(); // 日总提现金额
		String casNumTotal = param.get("totalTxnNum").toString(); // 日总提现次数
		String rcsType = RcsConstants.LIMIT_TYPE_CUST;
		String bizType = MsgCT.PRDORDTYPE_CASH;
		String payType = "02"; // 终端提现方式
		String agentId="";

		log.info("【风控】：校验[商户限额].");

		CustLimitInf limit = getLimit(rcsType, bizType, payType, custId, agentId);
		if (limit != null) {
			oneCas(txamt, limit.getLimitMinAmt(), limit.getLimitMaxAmt());

			// 日提现金额
			dayCasAmt(casAmtTotal, limit.getLimitDayAmt());
			// 日提现次数
			dayCasNum(casNumTotal, limit.getLimitDayTimes());
			// 如果存在商户提现限额、且通过时返回, 将不再校验默认提现限额。
			return true;
		}

		log.info("【风控】：校验[默认限额].");
		DefaultLimitInf limit_ = getLimitDefault();
		if (limit_ != null) {
			// 日提现限额
			oneTxn(txamt, limit_.getLimitMinAmtTp(), limit_.getLimitMaxAmtTp());

			// 日提现金额
			dayCasAmt(casAmtTotal, limit_.getLimitDayAmtTp());
			// 日提现次数
			dayCasNum(casNumTotal, limit_.getLimitDayTimesTp());
		}
		return true;
	}
	
	@Override
	public boolean checkAgeCASLimit(Map<String, Object> param) throws TranException {
		log.info("【风控】进入提现代理商限额检查...");
		log.info("【风控】开始限额检查必要参数验证，参数列表：" + param.toString());

		// 限额所需参数检查
		ParamValidate.doing(param, "custId", "txnAmt", "agentId");

		log.info("【风控】：开始检查限额.");
		String custId = param.get("custId").toString(); // 商户号
		String txamt = param.get("txnAmt").toString(); // 提现金额
		String casAmtTotal = param.get("totalTxnAmt").toString(); // 日总提现金额
		String casNumTotal = param.get("totalTxnNum").toString(); // 日总提现次数
		String rcsType = RcsConstants.LIMIT_TYPE_AGENT;
		String bizType = MsgCT.PRDORDTYPE_CASH;
		String payType = "02"; // 终端提现方式
		String agentId=param.get("agentId").toString(); // 代理商

		log.info("【风控】：校验[代理商限额].");

		CustLimitInf limit = getLimit(rcsType, bizType, payType, custId, agentId);
		if (limit != null) {
			oneCas(txamt, limit.getLimitMinAmt(), limit.getLimitMaxAmt());

			// 日提现金额
			dayCasAmt(casAmtTotal, limit.getLimitDayAmt());
			// 日提现次数
			dayCasNum(casNumTotal, limit.getLimitDayTimes());
		}
		
		return true;
	}

	private boolean doCheck(Map<String, Object> param) throws TranException {
		log.info("【风控】：开始检查限额.");

		String bizType = param.get("bizType").toString(); // 业务类型
		String payType = param.get("payType").toString(); // 支付类型
		String custId = param.get("custId").toString(); // 商户号
		String agentId = param.get("agentId").toString();//代理商编号
		String cardno = null; // 银行卡
		String txnAmt = param.get("payAmt") == null ? "0" : param.get("payAmt").toString(); // 金额
		boolean bool = true;
		/**
		 * [商户限额]优先级大于[代理商限额], 如果[商户限额]通过,将跳过[代理商限额]
		 * [代理商限额]优先级大于[默认限额], 如果[代理商限额]通过,将跳过[默认限额]
		 * 
		 */

		if(param.get("cardNo") != null && !"".equals(param.get("cardNo"))){
			cardno = param.get("cardNo").toString().trim();
			step01CheckBankCardBlacklist(cardno);
		}

		bool = step02CheckMerLimit(agentId,custId, bizType, payType, txnAmt);

		// 暂时不做商户等级限额和银行卡限额
		// step03CheckMerClassLimit(custId,bizType,payType,txnAmt);
		// step04CheckBankCardLimit(cardno,bizType,payType,txnAmt);
		
		// 如果存在商户限额,且限额通过将不再校验默认限额
		if (bool) {
			step05CheckDefaultLimit(cardno, bizType, payType, txnAmt);
		}
		log.info("【风控】：限额检查完成.");
		return true;
	}

	/**
	 * 第一步：检查银行卡黑名单
	 * 
	 * @param cardno
	 *            银行卡号
	 * @return
	 */
	private void step01CheckBankCardBlacklist(String cardno) throws TranException {
		log.info("【风控】：开始检查银行卡黑名单.");
		BankCardBlacklistInf entity = new BankCardBlacklistInf();
		entity.setCardno(cardno);
		try {
			entity = bankCardBlacklistService.getEntity(entity);
		} catch (Exception e) {
			log.info("【风控】：检查银行卡黑名单异常 {}.", e);
		}
		if (entity != null && entity.getCardno() != null) {
			throw new TranException(ExcepCode.EX000898, "【" + cardno + "】为黑名单卡");
		}
	}

	/**
	 * 第三步：检查商户等级限额
	 * 
	 * @param custId
	 *            商户号 </br>
	 * @param bizType
	 *            业务类型 </br>
	 * @param payType
	 *            支付类型 </br>
	 * @param txnAmt
	 *            交易金额 </br>
	 * @return true 通过 false 不通过
	 */
	@SuppressWarnings("unused")
	private boolean step03CheckMerClassLimit(String custId, String bizType, String payType, String txnAmt) throws TranException {
		log.info("【风控】：检查商户等级限额.");
		return true;
	}

	/**
	 * 第二步：检查商户限额
	 * 
	 * @param custId
	 *            商户号 </br>
	 * @param bizType
	 *            业务类型 </br>
	 * @param payType
	 *            支付类型 </br>
	 * @param txnAmt
	 *            交易金额 </br>
	 * @return true 通过 false 不通过
	 */
	private boolean step02CheckMerLimit(String agentId,String custId, String bizType, String payType, String txnAmt) throws TranException {
		log.info("【风控】：检查商户限额.");
		CustLimitInf limitCust = getLimit(RcsConstants.LIMIT_TYPE_CUST, bizType, payType, custId, agentId);
		CustLimitInf limitAgent = getLimit(RcsConstants.LIMIT_TYPE_AGENT, bizType, payType, custId, agentId);
		if (limitCust != null) {
			return limitInf(custId, bizType, payType, txnAmt, limitCust);
		}else if(limitCust == null && limitAgent !=null){
			return limitInf(custId, bizType, payType, txnAmt, limitAgent);
		}
		return true;
	}
	//检查代理商限额和商户限额，商户限额优先级大于代理商限额
	private boolean limitInf(String custId, String bizType, String payType,
			String txnAmt, CustLimitInf limit) throws TranException {
		// 单笔最小和最大检查
		oneTxn(txnAmt, limit.getLimitMinAmt(), limit.getLimitMaxAmt());

		// 定义日交易统计MAP存放交易金额和次数汇总。
		Map<String, Object> txnDayTotal = null;

		// 定义月交易统计MAP存放交易金额和次数汇总。
		Map<String, Object> txnMonthTotal = null;

		// 日查询交易记录
		if (MsgCT.PRDORDTYPE_PAYMENTS.equals(bizType)) {
			if (MsgCT.PAYTYPE_TE.equals(payType) || MsgCT.PAYTYPE_SM.equals(payType)) {
				// 收款业务 终端和快捷限额检查

				// 日交易
				txnDayTotal = getTxnTotal(bizType, payType, null, custId, null, null, null, 1);

				if (txnDayTotal != null) {
					String txnAmtTotal = txnDayTotal.get("cntTranAmt").toString();
					String txnNumTotal = txnDayTotal.get("cntTranNum").toString();
					// 日交易金额
					dayTxnAmt(txnAmtTotal, limit.getLimitMaxAmt());
					// 日交易次数
					dayTxnNum(txnNumTotal, limit.getLimitDayTimes());
				}

				// 月交易
				txnMonthTotal = getTxnTotal(bizType, payType, null, custId, null, null, null, 2);

				if (txnMonthTotal != null) {
					// 日限额
					String txnAmtTotal = txnMonthTotal.get("cntTranAmt").toString();
					String txnNumTotal = txnMonthTotal.get("cntTranNum").toString();
					// 日交易金额
					monthTxnAmt(txnAmtTotal, limit.getLimitMonthAmt());
					// 日交易次数  
					monthTxnNum(txnNumTotal, limit.getLimitMonthTimes());
				}
			} else if (MsgCT.PAYTYPE_LI.equals(payType)) {
				// 快捷支付的使用checkNO[商户号作为用户限额标志]
				// TODO
			}
		}
		return false;
	}

	/**
	 * 第四步：检查银行卡限额
	 * 
	 * @param cardno
	 *            银行卡号 </br>
	 * @param bizType
	 *            业务类型 </br>
	 * @param payType
	 *            支付类型 </br>
	 * @param txnAmt
	 *            交易金额 </br>
	 * @return true 通过 false 不通过
	 */
	@SuppressWarnings("unused")
	private boolean step04CheckBankCardLimit(String cardno, String bizType, String payType, String txnAmt) throws TranException {
		log.info("【风控】：检查银行卡限额.");
		return true;
	}

	/**
	 * 第五步：检查默认限额
	 * 
	 * @param checkNO
	 *            限额号 </br>
	 * @param bizType
	 *            业务类型 </br>
	 * @param payType
	 *            支付类型 </br>
	 * @param txnAmt
	 *            交易金额 </br>
	 * @return true 通过 false 不通过
	 */
	private boolean step05CheckDefaultLimit(String checkNO, String bizType, String payType, String txnAmt) throws TranException {
		log.info("【风控】：检查默认限额.");

		DefaultLimitInf limit = getLimitDefault();
		if (limit != null) {

			// 定义日交易统计MAP存放交易金额和次数汇总。
			Map<String, Object> txnDayTotal = null;

			// 定义月交易统计MAP存放交易金额和次数汇总。
			Map<String, Object> txnMonthTotal = null;

			// 日查询交易记录
			if (MsgCT.PRDORDTYPE_PAYMENTS.equals(bizType)) {
				// 收款业务 终端
				if (MsgCT.PAYTYPE_TE.equals(payType)) {

					// 单笔最小和最大检查
					oneTxn(txnAmt, limit.getLimitMinAmtTp(), limit.getLimitMaxAmtTp());

					// 日交易、日次数
					txnDayTotal = getTxnTotal(bizType, payType, checkNO, null, null, null, null, 1);

					if (txnDayTotal != null) {

						String txnAmtTotal = txnDayTotal.get("cntTranAmt").toString();
						String txnNumTotal = txnDayTotal.get("cntTranNum").toString();

						// 日交易金额
						dayTxnAmt(txnAmtTotal, limit.getLimitDayAmtTp());
						// 日交易次数
						dayTxnNum(txnNumTotal, limit.getLimitDayTimesTp());
					}

					// 月交易、月次数
					txnMonthTotal = getTxnTotal(bizType, payType, checkNO, null, null, null, null, 1);

					if (txnMonthTotal != null) {
						log.info("限额信息：{}", txnMonthTotal);
						// 日限额
						String txnAmtTotal = txnMonthTotal.get("cntTranAmt").toString();
						String txnNumTotal = txnMonthTotal.get("cntTranNum").toString();

						// 日交易金额
						monthTxnAmt(txnAmtTotal, limit.getLimitMonthAmtTp());
						// 日交易次数
						monthTxnNum(txnNumTotal, limit.getLimitMonthTimesTp());
					}
				} else if (MsgCT.PAYTYPE_LI.equals(payType)) {
					// 快捷支付的使用checkNO[商户号作为用户限额标志]
					// TODO
				} else if (MsgCT.PAYTYPE_SM.equals(payType)){
					// 单笔最小和最大检查
					oneTxn(txnAmt, limit.getLimitMinAmtSp(), limit.getLimitMaxAmtSp());

					// 日交易、日次数
					txnDayTotal = getTxnTotal(bizType, payType, null, null, checkNO, null, null, 1);

					if (txnDayTotal != null) {

						String txnAmtTotal = txnDayTotal.get("cntTranAmt").toString();
						String txnNumTotal = txnDayTotal.get("cntTranNum").toString();

						// 日交易金额
						dayTxnAmt(txnAmtTotal, limit.getLimitDayAmtSp());
						// 日交易次数
						dayTxnNum(txnNumTotal, limit.getLimitDayTimesSp());
					}

					// 月交易、月次数
					txnMonthTotal = getTxnTotal(bizType, payType, null, null, checkNO, null, null, 1);

					if (txnMonthTotal != null) {
						log.info("限额信息：{}", txnMonthTotal);
						// 日限额
						String txnAmtTotal = txnMonthTotal.get("cntTranAmt").toString();
						String txnNumTotal = txnMonthTotal.get("cntTranNum").toString();

						// 日交易金额
						monthTxnAmt(txnAmtTotal, limit.getLimitMonthAmtSp());
						// 日交易次数
						monthTxnNum(txnNumTotal, limit.getLimitMonthTimesSp());
					}
				}
			}
		}
		return true;
	}

	private boolean oneTxn(String txnAmt, String minAmt, String maxAmt) throws TranException {
		log.info("【风控】：检查单笔交易限额.");
		log.info("【风控】：交易金额:{}元, 最小交易金额:{}元 ,最大交易金额:{}元", MoneyUtils.toStrYuan(txnAmt), MoneyUtils.toStrYuan(minAmt), MoneyUtils.toStrYuan(maxAmt));
		if (txnAmt == null || txnAmt.trim().equals("")) {
			txnAmt = "0";
		}
		if (minAmt == null || minAmt.trim().equals("")) {
			minAmt = "0";
		}
		if (maxAmt == null || maxAmt.trim().equals("")) {
			maxAmt = "0";
		}
		double txnAmt_ = Double.valueOf(txnAmt);
		double minAmt_ = Double.valueOf(minAmt);
		double maxAmt_ = Double.valueOf(maxAmt);
		if (minAmt_ > 0 && minAmt_ > txnAmt_) {
			throw new TranException(ExcepCode.EX000851, "交易限额不通过。", MoneyUtils.toStrYuan(minAmt));
		}
		if (maxAmt_ > 0 && maxAmt_ < txnAmt_) {
			throw new TranException(ExcepCode.EX000852, "交易限额不通过。", MoneyUtils.toStrYuan(maxAmt));
		}
		log.info("【风控】：检查单笔交易限额通过.");
		return true;
	}

	private boolean dayTxnAmt(String txnAmt, String maxAmt) throws TranException {
		log.info("【风控】：检查日交易金额.");
		log.info("【风控】：交易汇总金额:{}元, 最大交易金额:{}元", MoneyUtils.toStrYuan(txnAmt), MoneyUtils.toStrYuan(maxAmt));
		if (txnAmt == null || txnAmt.trim().equals("")) {
			txnAmt = "0";
		}
		if (maxAmt == null || maxAmt.trim().equals("")) {
			maxAmt = "0";
		}
		double txnAmt_ = Double.valueOf(txnAmt);
		double maxAmt_ = Double.valueOf(maxAmt);
		if (maxAmt_ > 0 && maxAmt_ < txnAmt_) {
			throw new TranException(ExcepCode.EX000853, "交易限额不通过。", MoneyUtils.toStrYuan(maxAmt));
		}
		log.info("【风控】：检查日交易金额通过.");
		return true;
	}

	private boolean dayTxnNum(String txnNum, String maxNum) throws TranException {
		log.info("【风控】：检查日交易次数.");
		log.info("【风控】：交易汇总次数:{}, 最大交易次数:{}", txnNum, maxNum);
		if (txnNum == null || txnNum.trim().equals("")) {
			txnNum = "0";
		}
		if (maxNum == null || maxNum.trim().equals("")) {
			maxNum = "0";
		}
		int txnNum_ = Integer.valueOf(txnNum);
		int maxNum_ = Integer.valueOf(maxNum);
		if (maxNum_ > 0 && maxNum_ < txnNum_) {
			throw new TranException(ExcepCode.EX000854, "交易限额不通过。", maxNum);
		}
		log.info("【风控】：检查日交易次数通过.");
		return true;
	}

	private boolean monthTxnAmt(String txnAmt, String maxAmt) throws TranException {
		log.info("【风控】：检查月交易金额.");
		log.info("【风控】：交易汇总金额:{}元, 最大交易金额:{}元", MoneyUtils.toStrYuan(txnAmt), MoneyUtils.toStrYuan(maxAmt));
		if (txnAmt == null || txnAmt.trim().equals("")) {
			txnAmt = "0";
		}
		if (maxAmt == null || maxAmt.trim().equals("")) {
			maxAmt = "0";
		}
		double txnAmt_ = Double.valueOf(txnAmt);
		double maxAmt_ = Double.valueOf(maxAmt);
		if (maxAmt_ > 0 && maxAmt_ < txnAmt_) {
			throw new TranException(ExcepCode.EX000855, "交易限额不通过。", MoneyUtils.toStrYuan(maxAmt_));
		}
		log.info("【风控】：检查月交易金额通过.");
		return true;
	}

	private boolean monthTxnNum(String txnNum, String maxNum) throws TranException {
		log.info("【风控】：检查月交易次数.");
		log.info("【风控】：交易汇总次数:{}, 最大交易次数:{}", txnNum, maxNum);
		if (txnNum == null || txnNum.trim().equals("")) {
			txnNum = "0";
		}
		if (maxNum == null || maxNum.trim().equals("")) {
			maxNum = "0";
		}
		int txnNum_ = Integer.valueOf(txnNum);
		int maxNum_ = Integer.valueOf(maxNum);
		if (maxNum_ > 0 && maxNum_ < txnNum_) {
			throw new TranException(ExcepCode.EX000856, "交易限额不通过。", maxNum);
		}
		log.info("【风控】：检查月交易次数通过.");
		return true;
	}

	private boolean oneCas(String casAmt, String minAmt, String maxAmt) throws TranException {
		log.info("【风控】：检查单笔提现限额.");
		log.info("【风控】：提现汇总金额:{}元, 最小提现金额:{}元,最大提现金额:{}元", MoneyUtils.toStrYuan(casAmt), MoneyUtils.toStrYuan(maxAmt), MoneyUtils.toStrYuan(maxAmt));
		if (casAmt == null || casAmt.trim().equals("")) {
			casAmt = "0";
		}
		if (minAmt == null || minAmt.trim().equals("")) {
			minAmt = "0";
		}
		if (maxAmt == null || maxAmt.trim().equals("")) {
			maxAmt = "0";
		}
		double casAmt_ = Double.valueOf(casAmt);
		double minAmt_ = Double.valueOf(minAmt);
		double maxAmt_ = Double.valueOf(maxAmt);
		if (minAmt_ > 0 && minAmt_ > casAmt_) {
			throw new TranException(ExcepCode.EX000857, "提现限额不通过。", MoneyUtils.toStrYuan(minAmt_));
		}
		if (maxAmt_ > 0 && maxAmt_ < casAmt_) {
			throw new TranException(ExcepCode.EX000858, "提现限额不通过。", MoneyUtils.toStrYuan(maxAmt_));
		}
		log.info("【风控】：检查单笔提现限额通过.");
		return true;
	}

	private boolean dayCasAmt(String casAmt, String maxAmt) throws TranException {
		log.info("【风控】：检查日提现金额.");
		log.info("【风控】：提现汇总金额:{}元,最大提现金额:{}元", MoneyUtils.toStrYuan(casAmt), MoneyUtils.toStrYuan(maxAmt));

		if (casAmt == null || casAmt.trim().equals("")) {
			casAmt = "0";
		}
		if (maxAmt == null || maxAmt.trim().equals("")) {
			maxAmt = "0";
		}
		double casAmt_ = Double.valueOf(casAmt);
		double maxAmt_ = Double.valueOf(maxAmt);
		if (maxAmt_ > 0 && maxAmt_ < casAmt_) {
			throw new TranException(ExcepCode.EX000859, "提现限额不通过。", MoneyUtils.toStrYuan(maxAmt_));
		}
		log.info("【风控】：检查日提现金额通过.");
		return true;
	}

	private boolean dayCasNum(String casNum, String maxNum) throws TranException {
		log.info("【风控】：检查日提现次数.");
		log.info("【风控】：提现汇总次数:{}, 最大交易次数:{}", casNum, maxNum);
		if (casNum == null || casNum.trim().equals("")) {
			casNum = "0";
		}
		if (maxNum == null || maxNum.trim().equals("")) {
			maxNum = "0";
		}
		int casNum_ = Integer.valueOf(casNum);
		int maxNum_ = Integer.valueOf(maxNum);
		if (maxNum_ > 0 && maxNum_ < casNum_) {
			throw new TranException(ExcepCode.EX000854, "提现限额不通过。", maxNum);
		}
		log.info("【风控】：检查日提现次数通过.");
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addTxnLimit(Map<String, Object> paramMap) throws TranException {
		try {
			/*
			 * String seq = TdExpBasicFunctions.GETDATE()+
			 * seqNoService.getSeqNoNew("ORG_TERM_LIMIT_NO", "9", "1");
			 * Map<String,Object> rcsMap = new HashMap<String,Object>();
			 * rcsMap.put("limitId", seq); rcsMap.put("limitOrgNo",
			 * paramMap.get("orgNo")); rcsMap.put("limitMerNo",
			 * paramMap.get("TMercId")); rcsMap.put("limitTermNo",
			 * paramMap.get("TTermId")); rcsMap.put("limitPayAmt",
			 * paramMap.get("payAmt")); rcsMap.put("txnDate",
			 * TdExpBasicFunctions.GETDATE());
			 */
			return 1;
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000826, e);
		}
	}

	/**
	 * 查询商户限额记录
	 * 
	 * @param rcsType
	 *            风控类型
	 * @param bizType
	 *            业务类型
	 * @param payType
	 *            支付类型
	 * @param custId
	 *            商户号
	 * @return
	 * @throws TranException
	 */
	private CustLimitInf getLimit(String rcsType, String bizType, String payType, String custId, String agentId) throws TranException {
		try {
			CustLimitInf param = new CustLimitInf(rcsType, bizType, RcsConstants.LIMIT_SUB_BIZTYPE, payType, RcsConstants.LIMIT_IS_USE_NO);
			if ("20".equals(rcsType) && custId != null) {
				param.setLimitCustId(custId);
			}
			if ("40".equals(rcsType) && agentId != null) {
				param.setLimitAgentId(agentId);
			}
			CustLimitInf limit = custLimitService.getEntity(param);
			if (limit != null)
				log.info("商户限额记录,{}", limit.toString());
			else
				log.info("无商户限额记录.");
			return limit;
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000899, "查询商户限额记录出错了。");
		}
	}

	/**
	 * 查询默认限额记录
	 * 
	 * @return
	 * @throws TranException
	 */
	private DefaultLimitInf getLimitDefault() throws TranException {
		try {

			DefaultLimitInf limit = defaultLimitService.queryLimit();
			if (RcsConstants.LIMIT_IS_USE_OFF.equals(limit.getIsUse())) {
				limit = null;
			}
			if (limit != null)
				log.info("默认限额记录,{}", limit.toString());
			else
				log.info("无默认限额记录.");
			return limit;
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000899, "查询默认限额记录出错了。");
		}
	}

	/**
	 * 查询交易汇总
	 * 
	 * @param bizType
	 *            业务类型
	 * @param payType
	 *            支付类型
	 * @param termno
	 *            系统刷卡器终端号
	 * @param custId
	 *            商户号
	 * @param cardno
	 *            银行卡号
	 * @param tmerno
	 *            合作机构商户号(第三方)
	 * @param ttermno
	 *            合作机构终端号(第三方)
	 * @param type
	 *            限额类型 1 日交易限额 2 月交易限额
	 * @return 汇总金额
	 * @throws TranException
	 */
	private Map<String, Object> getTxnTotal(String bizType, String payType, String termno, String custId, String cardno, String tmerno, String ttermno, int type) throws TranException {
		Map<String, Object> txn = null;
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			String date = TdExpBasicFunctions.GETDATE();
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6);
			param.put("year", year);
			param.put("month", month);
			if (1 == type) {
				param.put("day", day);
			}
			param.put("busType", bizType);
			// 所有子业务
			// param.put("subBus", RcsConstants.LIMIT_SUB_BIZTYPE);
			param.put("payWay", payType);
			param.put("termno", termno);
			param.put("custId", custId);
			param.put("cardno", cardno);
			param.put("ttermno", ttermno);
			param.put("tmerno", tmerno);
			txn = getTransactionAmtTotal(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000899, "查询交易汇总出错了。");
		}
		return txn;
	}

	/**
	 * 查询提现汇总
	 * 
	 * @param casType
	 *            提现类型
	 * @param custId
	 *            商户号
	 * @return 汇总金额
	 * @throws TranException
	 */
	private Map<String, Object> getCasTotal(String casType, String custId) throws TranException {
		Map<String, Object> txntotal = null;

		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String date = TdExpBasicFunctions.GETDATE();
			param.put("custId", custId);
			param.put("date", date);
			// 只T+0金额汇总
			txntotal = getTransactionCasTotal(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000899, "查询提现汇总出错了。");
		}
		return txntotal;
	}

	/**
	 * 查询体现汇总
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getTransactionCasTotal(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> casTotalMap = rcsTransactionDao.transactionCasTotal(paramMap);
		log.info("提现汇总数据：{}", casTotalMap);

		return casTotalMap;
	}

	/**
	 * 查询交易汇总
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getTransactionAmtTotal(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> txnTotalMap = rcsTransactionDao.transactionAmtTotal(paramMap);
		log.info("交易汇总数据：{}", txnTotalMap);

		return txnTotalMap;
	}

	@Override
	public Map<String, Object> checkOrgTerm(Map<String, Object> paramMap) throws TranException {
		Map<String, Object> reMap = null;
		try {
			log.info("可用大商户查询, 参数:{}", paramMap);
			Map<String, Object> rcsMap = new HashMap<String, Object>();
			rcsMap.put("rateType", paramMap.get("rateType"));
			rcsMap.put("agentId", paramMap.get("agentId"));
			rcsMap.put("amt", paramMap.get("payAmt"));
			rcsMap.put("day", DateUtil.getCurrentDay());
			rcsMap.put("month", DateUtil.getCurrentMonth());

			reMap = orgTermLimitService.queryTxnAmt(rcsMap);
			log.debug("可用大商户查询信息完毕");
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000825, "大商户限额检查异常.");
		}
		return reMap;
	}

	@Override
	public int updateOrgTermTxnAmt(Map<String, Object> paramMap) throws TranException {
		try {
			log.info("修改每日,每月交易金额,参数{}", paramMap);
			Map<String, Object> rcsMap = new HashMap<String, Object>();
			rcsMap.put("limitOrgId", paramMap.get("orgNo"));
			rcsMap.put("limitMerNo", paramMap.get("TMercId"));
			rcsMap.put("limitTermNo", paramMap.get("TTermId"));
			rcsMap.put("amt", paramMap.get("payAmt"));
			rcsMap.put("txnDate", System.currentTimeMillis());
			if (orgTermLimitService.update(rcsMap) <= 0) {
				log.info("【合作机构:{},大商户:{},终端号:{}】 没有可用限额记录,添加", paramMap.get("orgNo"), paramMap.get("TMercId"), paramMap.get("TTermId"));
				addOrgTermTxnAmt(paramMap);
			}
			return 1;
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000826, e);
		}
	}

	@Override
	public int addOrgTermTxnAmt(Map<String, Object> paramMap) throws TranException {
		try {
			log.info("添加每日,每月交易金额,参数{}", paramMap);
			String seq = TdExpBasicFunctions.GETDATE() + seqNoService.getSeqNoNew("ORG_TERM_LIMIT_NO", "9", "1");
			Map<String, Object> rcsMap = new HashMap<String, Object>();
			rcsMap.put("limitId", seq);
			rcsMap.put("limitOrgId", paramMap.get("orgNo"));
			rcsMap.put("limitMerNo", paramMap.get("TMercId"));
			rcsMap.put("limitTermNo", paramMap.get("TTermId"));
			rcsMap.put("limitDayAmt", paramMap.get("payAmt"));
			rcsMap.put("limitMonthAmt", paramMap.get("payAmt"));
			rcsMap.put("day", DateUtil.getCurrentDay());
			rcsMap.put("month", DateUtil.getCurrentMonth());
			rcsMap.put("txnDate", System.currentTimeMillis());
			return orgTermLimitService.add(rcsMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000826, e);
		}
	}
	
	@Override
	public int updateOrgTermTxnAmtRoll(Map<String, Object> paramMap) throws TranException {
		try {
			log.info("修改每日,每月交易金额,参数{}", paramMap);
			Map<String, Object> rcsMap = new HashMap<String, Object>();
			rcsMap.put("limitOrgId", paramMap.get("orgNo"));
			rcsMap.put("limitMerNo", paramMap.get("TMercId"));
			rcsMap.put("limitTermNo", paramMap.get("TTermId"));
			rcsMap.put("amt", paramMap.get("payAmt"));
			rcsMap.put("txnDate", System.currentTimeMillis());
			if (orgTermLimitService.updateOrgAmt(rcsMap) <= 0) {
				log.info("【合作机构:{},大商户:{},终端号:{}】 没有可用限额记录", paramMap.get("orgNo"), paramMap.get("TMercId"), paramMap.get("TTermId"));
				//addOrgTermTxnAmt(paramMap);
			}
			return 1;
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000826, e);
		}
	}
}
