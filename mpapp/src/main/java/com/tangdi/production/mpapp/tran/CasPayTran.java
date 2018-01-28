package com.tangdi.production.mpapp.tran;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpapp.service.RouteService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.redoservice.TdRdoAtc;
import com.tangdi.production.tdcomm.util.Msg;

/**
 * 收单通道[银联]实时代付交易 。
 * @author youdd 2015/11/05
 *
 */
@Component
@IProcess
public class CasPayTran {
	private static Logger log = LoggerFactory.getLogger(CasPayTran.class);
	
	/**
	 * 前置交易接口
	 */
	@Autowired
	private TranFrontService tranFrontservice;
	/**
	 * 支付订单
	 */
	@Autowired
	private TermPayService termPayService;
	/**
	 * 商品订单接口
	 */
	@Autowired
	private TermPrdService termPrdService;
	
	/**
	 * 交易流水接口
	 */
	@Autowired
	private PaymentJournalService paymentJournalService;

	/**
	 * 路由
	 */
	@Autowired
	private RouteService routeService;
	
	@Value("#{mpappConfig}")
	private Properties prop;
	
	@Autowired
	private GetSeqNoService seqNoService;

	/**
	 * 实时代付
	 * */
	@ICode(TranCode.TRAN_PAY)
	public Map<String,Object> trans_110002(Map<String,Object> map) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		log.info("实时代付  进入主控时上下文数据为{}", map.toString());
		try {
			log.info("开始执行..");
			map.put("daifu", "y");
			//代付路由查询
			map.put("rtrType", MsgST.RTR_TYPE_CASPAY);
			map.put("rateType", prop.getProperty("CAS_PAY_RATE__TYPE"));
			map.put("payAmt",map.get("netrecAmt"));
			map.putAll(routeService.route(map));
			map.put("netrecamt",map.get("netrecAmt"));
			map.put("anoPid", TdExpBasicFunctions.GETDATE()+ seqNoService.getSeqNoNew("ANOPID", "9", "1"));
			rmap.putAll(map);
			rmap.putAll(tranFrontservice.casPay(map));
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("实时代付交易   离开时上下文数据为{}", rmap.toString());
		return rmap;
	}
	
	/**
	 * 确认实时代付
	 * */
	@ICode(TranCode.TRAN_CONPAY)
	public Map<String,Object> trans_110003(Map<String,Object> map) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		log.info("确认实时代付  进入主控时上下文数据为{}", map.toString());
		try {
			log.info("开始执行..");
			//代付路由查询
			map.put("rtrType", MsgST.RTR_TYPE_CONPAY);
			map.put("daifu", "y");
			map.putAll(routeService.route(map));
			rmap = tranFrontservice.conCasPay(map);
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("实时代付交易   离开时上下文数据为{}", rmap.toString());
		return rmap;
	}
	
	/**
	 * 额度查询
	 * */
	@ICode(TranCode.TRAN_LMTQRY)
	public Map<String,Object> trans_110004(Map<String,Object> map) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		log.info("进行额度查询  上下文数据为{}", map.toString());
		try {
			log.info("开始执行..");
			//代付路由查询
			map.put("lmtQry", "y");
			map.put("rtrType", MsgST.LIMIT_QUERY);
			map.put("rateType", MsgST.RATE_TYPE_CH00);
			map.putAll(routeService.route(map));
			rmap = tranFrontservice.limitQuery(map);
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("进行额度查询   离开时上下文数据为{}", rmap.toString());
		return rmap;
	}
	
}
