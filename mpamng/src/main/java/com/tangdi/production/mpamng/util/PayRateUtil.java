package com.tangdi.production.mpamng.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;

import com.tangdi.production.mpamng.bean.AgentFeeInfo;
import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpbase.util.MoneyUtils;

public class PayRateUtil {
	private static Logger log = LoggerFactory.getLogger(PayRateUtil.class);

	/**
	 * 获取 代理商 各个费率
	 * 
	 * 
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static void getAgentRate(HttpServletRequest request, AgentInf agentInf) throws Exception {
		
		agentInf.setRateTCas(ServletRequestUtils.getStringParameter(request, "rateTCas")); //T0提现费率
		agentInf.setRateLivelihood(ServletRequestUtils.getStringParameter(request, "rateLivelihood", "")); // 民生类rateLivelihood
		agentInf.setRateGeneral(ServletRequestUtils.getStringParameter(request, "rateGeneral", "")); // 一般类rateGeneral
		agentInf.setRateEntertain(ServletRequestUtils.getStringParameter(request, "rateEntertain", ""));// 餐娱类rateEntertain
		agentInf.setRateGeneralTop(ServletRequestUtils.getStringParameter(request, "rateGeneralTop", ""));// 批发类rateGeneralTop
		agentInf.setRateEntertainTop(ServletRequestUtils.getStringParameter(request, "rateEntertainTop", ""));// 房产类rateEntertainTop

		// 封顶金额 默认为空
		agentInf.setRateGeneralMaximun("");
		agentInf.setRateEntertainMaximun("");

		if (agentInf.getRateGeneralTop().length() > 0) {
			String rateGeneralMaximun = ServletRequestUtils.getStringParameter(request, "rateGeneralMaximun", "");// 批发类封顶rateGeneralMaximun
			if (rateGeneralMaximun.length() <= 0) {
				log.info("批发类封顶 金额没有填写....  ");
				throw new Exception("批发类封顶 金额没有填写....  ");
			}
			agentInf.setRateGeneralMaximun(MoneyUtils.toStrCent(rateGeneralMaximun));
		}

		if (agentInf.getRateEntertainTop().length() > 0) {
			String rateEntertainMaximun = ServletRequestUtils.getStringParameter(request, "rateEntertainMaximun", ""); // 房产类封顶rateEntertainMaximun
			if (rateEntertainMaximun.length() <= 0) {
				log.info("房产类封顶 金额没有填写....");
				throw new Exception("房产类封顶 金额没有填写....");
			}
			agentInf.setRateEntertainMaximun(MoneyUtils.toStrCent(rateEntertainMaximun));
		}
		log.info("代理商 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}分 封顶,【房产类封顶】:{} {}分 封顶", agentInf.getAgentName(), agentInf.getRateLivelihood(), agentInf.getRateGeneral(), agentInf.getRateEntertain(), agentInf.getRateGeneralTop(), agentInf.getRateGeneralMaximun(), agentInf.getRateEntertainTop(), agentInf.getRateEntertainMaximun());
	}

	/**
	 * 代理商 费率转换
	 * 
	 * @param agentInf
	 */
	public static void conAgentRate(AgentInf agentInf) {
		agentInf.setRateGeneralMaximun(MoneyUtils.toStrYuan(agentInf.getRateGeneralMaximun()));
		agentInf.setRateEntertainMaximun(MoneyUtils.toStrYuan(agentInf.getRateEntertainMaximun()));
		log.info("代理商 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}元 封顶,【房产类封顶】:{} {}元 封顶", agentInf.getAgentName(), agentInf.getRateLivelihood(), agentInf.getRateGeneral(), agentInf.getRateEntertain(), agentInf.getRateGeneralTop(), agentInf.getRateGeneralMaximun(), agentInf.getRateEntertainTop(), agentInf.getRateEntertainMaximun());
	}

	/**
	 * 代理商 费率转换
	 * 
	 * @param agentInf
	 */
	public static void conAgentFeeRate(String agentId, AgentFeeInfo agentFeeInfo) {
		agentFeeInfo.setRateGeneralMaximun(MoneyUtils.toStrYuan(agentFeeInfo.getRateGeneralMaximun()));
		agentFeeInfo.setRateEntertainMaximun(MoneyUtils.toStrYuan(agentFeeInfo.getRateEntertainMaximun()));
		log.info("代理商 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}元 封顶,【房产类封顶】:{} {}元 封顶", agentId, agentFeeInfo.getRateLivelihood(), agentFeeInfo.getRateGeneral(), agentFeeInfo.getRateEntertain(), agentFeeInfo.getRateGeneralTop(), agentFeeInfo.getRateGeneralMaximun(), agentFeeInfo.getRateEntertainTop(), agentFeeInfo.getRateEntertainMaximun());
	}
	
	/**
	 * 代理商 审核  若费率为空，对应的封顶值也为空  这个空无需转换为0
	 * @param agentInf
	 * @param b
	 */
	public static void conAgentRate(AgentInf agentInf, boolean b) {
		if(b){
			if(agentInf.getRateGeneralTop().equals("")||agentInf.getRateGeneralTop().equals(null)){
				
			}else{
				agentInf.setRateGeneralMaximun(MoneyUtils.toStrYuan(agentInf.getRateGeneralMaximun()));
			}
			if(agentInf.getRateEntertainTop().equals("")||agentInf.getRateEntertainTop().equals(null)){
				
			}else{
				agentInf.setRateEntertainMaximun(MoneyUtils.toStrYuan(agentInf.getRateEntertainMaximun()));
			}
			log.info("代理商 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}元 封顶,【房产类封顶】:{} {}元 封顶", agentInf.getAgentName(), agentInf.getRateLivelihood(), agentInf.getRateGeneral(), agentInf.getRateEntertain(), agentInf.getRateGeneralTop(), agentInf.getRateGeneralMaximun(), agentInf.getRateEntertainTop(), agentInf.getRateEntertainMaximun());			
		}
		
	}
	/**
	 * 获取 终端 各个费率
	 * 
	 * 
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static void getTermRate(HttpServletRequest request, TerminalInf terminalInf) throws Exception {
		terminalInf.setRateLivelihood(ServletRequestUtils.getStringParameter(request, "rateLivelihood", "")); // 民生类rateLivelihood
		terminalInf.setRateGeneral(ServletRequestUtils.getStringParameter(request, "rateGeneral", "")); // 一般类rateGeneral
		terminalInf.setRateEntertain(ServletRequestUtils.getStringParameter(request, "rateEntertain", ""));// 餐娱类rateEntertain
		terminalInf.setRateGeneralTop(ServletRequestUtils.getStringParameter(request, "rateGeneralTop", ""));// 批发类rateGeneralTop
		terminalInf.setRateEntertainTop(ServletRequestUtils.getStringParameter(request, "rateEntertainTop", ""));// 房产类rateEntertainTop

		// 封顶金额 默认为空
		terminalInf.setRateGeneralMaximun("");
		terminalInf.setRateEntertainMaximun("");

		if (terminalInf.getRateGeneralTop().length() > 0) {
			String rateGeneralMaximun = ServletRequestUtils.getStringParameter(request, "rateGeneralMaximun", "");// 批发类封顶rateGeneralMaximun
			if (rateGeneralMaximun.length() <= 0) {
				log.info("批发类封顶 金额没有填写....  ");
				throw new Exception("批发类封顶 金额没有填写....  ");
			}
			terminalInf.setRateGeneralMaximun(MoneyUtils.toStrCent(rateGeneralMaximun));
		}

		if (terminalInf.getRateEntertainTop().length() > 0) {
			String rateEntertainMaximun = ServletRequestUtils.getStringParameter(request, "rateEntertainMaximun", ""); // 房产类封顶rateEntertainMaximun
			if (rateEntertainMaximun.length() <= 0) {
				log.info("房产类封顶 金额没有填写....");
				throw new Exception("房产类封顶 金额没有填写....");
			}
			terminalInf.setRateEntertainMaximun(MoneyUtils.toStrCent(rateEntertainMaximun));
		}
		log.info("终端 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}分 封顶,【房产类封顶】:{} {}分 封顶", terminalInf.getAgentName(), terminalInf.getRateLivelihood(), terminalInf.getRateGeneral(), terminalInf.getRateEntertain(), terminalInf.getRateGeneralTop(), terminalInf.getRateGeneralMaximun(), terminalInf.getRateEntertainTop(), terminalInf.getRateEntertainMaximun());
	}

	/**
	 * 终端 费率转换
	 * 
	 * @param agentInf
	 */
	public static void conTermRate(TerminalInf terminalInf) {
		terminalInf.setRateGeneralMaximun(MoneyUtils.toStrYuan(terminalInf.getRateGeneralMaximun()));
		terminalInf.setRateEntertainMaximun(MoneyUtils.toStrYuan(terminalInf.getRateEntertainMaximun()));
		log.info("终端 【{}】,【民生类】:{}, 【一般类】:{},【餐娱类】:{},【批发类封顶】:{} {}元 封顶,【房产类封顶】:{} {}元 封顶", terminalInf.getAgentName(), terminalInf.getRateLivelihood(), terminalInf.getRateGeneral(), terminalInf.getRateEntertain(), terminalInf.getRateGeneralTop(), terminalInf.getRateGeneralMaximun(), terminalInf.getRateEntertainTop(), terminalInf.getRateEntertainMaximun());
	}

	

}
