package com.tangdi.production.mpomng.trans;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.tdcomm.engine.ICode;
import com.tangdi.production.tdcomm.engine.IProcess;

/**
 * 代付交易
 * @author youdd
 *
 */
@Component
@IProcess
public class PayTran {
	private static Logger log = LoggerFactory.getLogger(PayTran.class);

	/**
	 * 实时代付
	 * @throws  
	 */
	@Autowired
	private CasPrdService casPrdService;
	
	@ICode(TranCode.TRAN_PAY_ASYN) 
	public Map<String,Object> trans_800003(Map<String,Object> map)  {
		log.info("进入实时代付交易异步...");
		log.info("进入主控时上下文数据为:{}", map.toString());
		Map<String, Object> rmap = new HashMap<String, Object>();
		try {
			rmap = casPrdService.casPay(String.valueOf(map.get("casOrdNos")), String.valueOf(map.get("cooporgNo")));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("实时代付异步调用完成..." + rmap.toString());
		return rmap;
	}
	
	/**
	 * 额度查询
	 * **/
	@ICode(TranCode.TRAN_LIMIT_QUERY) 
	public Map<String,Object> trans_800004(Map<String,Object> map)  {
		log.info("进入额度查询交易异步...");
		log.info("进入主控时上下文数据为:{}", map.toString());
		Map<String, Object> rmap = new HashMap<String, Object>();
		try {
			rmap = casPrdService.limitQuery(String.valueOf(map.get("casOrdNos")), String.valueOf(map.get("cooporgNo")));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("实时代付异步调用完成..." + rmap.toString());
		return rmap;
	}
	
}
