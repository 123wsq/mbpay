package com.tangdi.production.mpbatch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.service.ConPayService;
import com.tangdi.production.mpomng.dao.CasPrdDao;
import com.tangdi.production.mpomng.service.CasPrdService;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

/**
 * 确认代付交易
 * 
 * @author youdd
 */
@Service
public class ConPayServiceImpl implements ConPayService {
	private static Logger log = LoggerFactory.getLogger(ConPayServiceImpl.class);

	@Autowired
	private CasPrdService casPrdService;
	
	@Autowired
	private CasPrdDao casPrdDao;
	
	@Autowired
	private HessinClientBean remoteService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("确认代付批处理     begin");

		Map<String, Object> rmap = null;
		//抓取15分钟前的代付交易
		Map<String, Object> param = new HashMap<String,Object>();
		
		
		param.put("startTime", String.valueOf(DateUtil.get(5))); //5分钟前的pay
		List<Map<String, Object>> conPayList = casPrdDao.selectConPayInf(param);
		for(Map<String, Object> map:conPayList){
			rmap = remoteService.dotran(TranCode.TRAN_CONPAY, map);
			if("00".equals(rmap.get("TCPSCod"))){
				map.put("payStatus", "1");	//确认成功
				map.put("ordStatus", "07"); //清算成功
				map.put("sucDate", DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss"));
			}
			else if("01".equals(rmap.get("TCPSCod"))){ //确认失败后将订单状态置为待清算，客户可以再次发起代付操作   超时不处理，下次发起确认代付时再与第三方核对
				map.put("payStatus", "2");	//确认失败
				map.put("ordStatus", "08"); //待清算（代付失败）
			}else{
				log.info("代付查询超时，等待再次发起！");
				continue;
			}
			//更新确认代付表状态
			casPrdDao.updateConPayStatus(map);
			//更新订单表状态
			casPrdDao.modifyCasPrdBystauts(map);
		}
		
		log.info("确认代付批处理     end");
	}

}
