package com.tangdi.production.mpbatch.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.dao.PrdUntreateDao;
import com.tangdi.production.mpbatch.service.PrdTransferService;

@Service
public class PrdTransferServiceImpl implements PrdTransferService {
	private static Logger log = LoggerFactory.getLogger(PrdTransferServiceImpl.class);
	
	@Autowired
	private PrdUntreateDao prdUntreateDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("未处理订单转移开始");
		String endTime = DateUtil.getDay() + MsgCT.DAY_TIME_START;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("endTime", endTime);
		
		prdUntreateDao.insertPrdUntreate(param);
		log.info("未处理订单转移结束");
	}
}
