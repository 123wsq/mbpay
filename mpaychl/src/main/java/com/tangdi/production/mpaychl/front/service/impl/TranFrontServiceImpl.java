package com.tangdi.production.mpaychl.front.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.base.service.TermService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpaychl.trans.process.service.TranProcessService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.context.SpringContext;


/**
 * <b>交易前置接口实现类</b>
 * 终端签到(刷卡器[蓝牙|音频]) 、 刷卡消费 、 银行卡余额查询 . 自动冲正
 * @author zhengqiang  2015/3/25
 *
 */

@Service
public class TranFrontServiceImpl implements TranFrontService{
	private static Logger log = LoggerFactory
			.getLogger(TranFrontServiceImpl.class);
	
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop;
	/**
	 * 合作机构编号
	 */
	private final static String ORG_NAME = "orgNo";

	@Override
	public Map<String, Object> pay(Map<String, Object> param)
			throws TranException {
		log.info("进入支付程序.支付参数:[{}]",param);
		return getTranProcessService(param).pay(param);
	}
	
	@Override
	public Map<String, Object> wxsmPay(Map<String, Object> param)
			throws TranException {
		log.info("进入微信扫码支付程序.支付参数:[{}]",param);
		return getTranProcessService(param).wxsmPay(param);
	}
	
	@Override
	public Map<String, Object> wxsmQuery(Map<String, Object> param)
			throws TranException {
		log.info("进入微信扫码查询程序.查询参数:[{}]",param);
		return getTranProcessService(param).wxsmQuery(param);
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param)
			throws TranException {
		log.info("进入银行卡余额查询程序.查询参数:[{}]",param);
		return getTranProcessService(param).query(param);
	}

	@Override
	public Map<String, Object> banksign(Map<String, Object> param)
			throws TranException {
		log.info("进入通道签到程序.签到参数:[{}]",param);
		return getTranProcessService(param).banksign(param);
	}

	@Override
	public Map<String, Object> termsign(Map<String, Object> param)
			throws TranException {
		log.info("进入设备签到程序.签到参数:[{}]",param);
		ParamValidate.doing(param, "terminalLMK","termType");
		
		String serviceName = null;
		try {
			/**
			 * 终端类型(蓝牙|音频) 对应不同厂商 termType : 01 蓝牙 02 音频
			 */
			String tname = "TERM_"+param.get("termType")+"_"+param.get("terminalCom");
			log.info("厂商配置参数名称:[{}]",tname);
			serviceName = prop.get(tname).toString();
		} catch (Exception e) {
			log.error("选取终端厂商配置信息异常！", e);
			throw new TranException(ExcepCode.EX900002, e);
		}
		log.info("选取终端厂商配置信息[{}={}]", TERM_SERVICE_NAMEKEY ,serviceName);

		Map<String,Object> rmap = null ; //返回结果
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ZMK", param.get("terminalLMK"));
		map.put("termType", param.get("termType"));
		log.info("参数:{}",map);
		TermService terminalService = SpringContext.getBean(serviceName
				, TermService.class);
		rmap = terminalService.getKey(map);
		log.info("下发的工作密钥:{}",rmap);
		return rmap;
	}
	
	@Override
	public Map<String, Object> reverse(Map<String, Object> param)
			throws TranException {
		log.info("系统调用,进入冲正程序.冲正参数:[{}]",param);
		return getTranProcessService(param).reverse(param);
	}
	@Override
	public void redo(Map<String, Object> param) throws TranException {
		log.info("交易异常,进入冲正程序.冲正参数:[{}]",param);
		getTranProcessService(param).redo(param);
	}
	
	/**
	 * 获取通道配置. 支持同时使用多个收单通道。
	 * @param orgNo 合作机构编号
	 * @return
	 * @throws TranException
	 */
	private TranProcessService getTranProcessService(Map<String, Object> param) throws TranException{
		String orgNo = param.get(ORG_NAME).toString();
		log.info("合作机构代码[{}]。",orgNo);
		String corg = prop.getProperty("CORG_TRAN_"+orgNo);
		log.info("通道实现配置:[{}]",corg);
		return SpringContext.getBean(corg, TranProcessService.class);
	}

	@Override
	public String[] decryptTrack(Map<String, Object> pmap)
			throws TranException {
		log.info("进入终端厂商磁道数据解密程序...");
		String serviceName = null;
		try {
			/**
			 * 终端类型(蓝牙|音频) 对应不同厂商 
			 * termType : 01 蓝牙 02 音频
			 * terminalCom : 对应厂商代码
			 */
			String tname = "TERM_"+pmap.get("termType")+"_"+pmap.get("terminalCom");
			log.info("厂商配置参数名称:[{}]",tname);
			serviceName = prop.get(tname).toString();
			pmap.put(TERM_SERVICE_NAMEKEY, serviceName);
		} catch (Exception e) {
			log.error("选取终端厂商配置信息异常！", e);
			throw new TranException(ExcepCode.EX900002, e);
		}
		log.info("选取终端厂商配置信息[{}={}]",TERM_SERVICE_NAMEKEY ,serviceName);
		TermService tservice = SpringContext.getBean(serviceName, TermService.class);
		// 磁道信息 [0] 二磁道 [1] 三磁道(可能为空)
		return tservice.decryptTrack(pmap);
	}

	@Override
	public Map<String, Object> casPay(Map<String, Object> param) throws TranException {
		log.info("进入代付.支付参数:[{}]",param);
		return getTranProcessService(param).casPay(param);
	}

	@Override
	public Map<String, Object> conCasPay(Map<String, Object> param) throws TranException {
		log.info("进入确认代付.支付参数:[{}]",param);
		return getTranProcessService(param).conCasPay(param);
	}
	
	@Override
	public Map<String, Object> limitQuery(Map<String, Object> param) throws TranException {
		log.info("进入额度查询.查询参数:[{}]",param);
		return getTranProcessService(param).limitQuery(param);
	}

	@Override
	public Map<String, Object> tranCancel(Map<String, Object> param)
			throws TranException {
		log.info("进入支付撤销程序.支付撤销参数:[{}]",param);
		return getTranProcessService(param).tranCancel(param);
	}

	@Override
	public void selectTermService(Map<String, Object> pmap)
			throws TranException {
		String serviceName = null;
		try {
			/**
			 * 终端类型(蓝牙|音频) 对应不同厂商 
			 * termType : 01 蓝牙 02 音频
			 * terminalCom : 对应厂商代码
			 */
			String tname = "TERM_"+pmap.get("termType")+"_"+pmap.get("terminalCom");
			log.info("厂商配置参数名称:[{}]",tname);
			serviceName = prop.get(tname).toString();
			pmap.put(TERM_SERVICE_NAMEKEY, serviceName);
		} catch (Exception e) {
			log.error("选取终端厂商配置信息异常！", e);
			throw new TranException(ExcepCode.EX900002, e);
		}
	}
}
