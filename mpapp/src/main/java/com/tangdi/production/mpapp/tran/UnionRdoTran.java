package com.tangdi.production.mpapp.tran;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpapp.constants.MsgST;
import com.tangdi.production.mpapp.service.PaymentJournalService;
import com.tangdi.production.mpapp.service.RouteService;
import com.tangdi.production.mpapp.service.TermPayService;
import com.tangdi.production.mpapp.service.TermPrdService;
import com.tangdi.production.mpaychl.front.service.TranFrontService;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;
import com.tangdi.production.tdcomm.redoservice.TdRdoAtc;
import com.tangdi.production.tdcomm.util.Msg;

/**
 * 收单通道[银联]冲正交易 。
 * @author zhengqiang 2015/08/19
 *
 */
@Component
@IProcess
public class UnionRdoTran {
	private static Logger log = LoggerFactory.getLogger(UnionRdoTran.class);
	
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

	
	/**
	 * 收单通道[银联]冲正交易
	 * @throws  
	 */
	@ICode(TranCode.TRAN_RDO)
	public void trans_110001()  {
		log.info("进入冲正交易...");
	
		Map<String, Object> map = Msg.getdatas();
		log.info("进入主控时上下文数据为:{}", map.toString());
		
	    int redotms = -1;   //冲正错误次数
	    int maxtms = -1;    //最大冲正次数
	    int isOk = 0;
	    Map<String,Object> rmap = null;
		Map<String,Object> pmap = new HashMap<String,Object>();     //订单
		pmap.put("payordno", map.get("payNo"));
		pmap.put("prdordNo", map.get("prdordNo"));
		try {
			if(map.get("REDOTMS") == null || map.get("MAXTMS") == null){
				log.error("冲正数据有误,冲正交易异常终止！");
				return ;
			}
			redotms = (Integer) map.get("REDOTMS");
			maxtms  = (Integer) map.get("MAXTMS");
			log.info("第[{}]次冲正交易开始.",redotms);
			
			if(redotms == 1){
				//第一次进入冲正交易,状态设置为冲正中G

				pmap.put("paystatus", MsgST.PAYSTATUS_REVERSE_ING);  //冲正中
				try {
					//更新支付订单
					termPayService.modifyPayByRedo(pmap);
				} catch (TranException e) {
					log.error("更新冲正状态出错！{}",e.getMessage());
				}
				
			}
			//冲正路由查询
			map.put("rtrType", MsgST.RTR_TYPE_REVERSE); 
			map.putAll(routeService.route(map));
			
			//调用冲正渠道接口
			rmap = tranFrontservice.reverse(map);

			log.info("冲正交易:{}", rmap.toString());
			//如果MAC错误要重新冲正
			if(rmap.get("TCPSCod").toString().equals("00")){
				//payOrdNo 支付订单号,payStatus 支付状态
				pmap.putAll(rmap);
				pmap.put("paystatus", MsgST.PAYSTATUS_REVERSE_OK);  //冲正成功
				
				try {
					log.info("打印交易数据：{}",pmap.toString());
					//修改商品订单
					pmap.put("ordstatus", MsgST.ORDSTATUS_FAIL);
					termPrdService.modifyPrd(pmap);
					
					//修改支付订单
					termPayService.modifyPayByRedo(pmap);
					
					//修改交易流水
					rmap.put("txnstatus",MsgST.TXNSTATUS_C);
					rmap.put("txnType", MsgST.TXNTYPE_CONSUME);
					paymentJournalService.modifyPaymentJournal(rmap);
					
					TdRdoAtc.TranRollbackWork();
					log.info("消费冲正交易成功.");
					isOk = 1;
				} catch (TranException e) {
					log.error("支付订单更新数据失败！{}",e.getMessage());
				}
			}else{
				//冲正次数已完成
				if(maxtms == (redotms)){
					pmap.put("paystatus", MsgST.PAYSTATUS_REVERSE_NG);  
					try {
						//更新支付订单
						termPayService.modifyPayByRedo(pmap);
						isOk = 1;
					} catch (TranException e) {
						log.error("更新冲正状态出错！{}",e.getMessage());
					}
				}
				
			}
		
		} catch (TranException e) {
			log.info("收单通道[银联]消费冲正交易失败！");
			log.error(e.getMessage(),e);
		}catch(Exception e){
			log.info("冲正交易失败！传值问题");
			log.error(e.getMessage(),e);
		}finally{
			if(isOk == 0 && maxtms == (redotms)){
				pmap.put("paystatus", MsgST.PAYSTATUS_REVERSE_NG);  //冲正失败
				try {
					//更新支付订单
					termPayService.modifyPayByRedo(pmap);
					isOk = 1;
				} catch (Exception e) {
					log.error("更新冲正状态出错！{}",e.getMessage());
				}
			}
			log.info("第[{}]次冲正交易完成.",redotms);
		}
	}
	
}
