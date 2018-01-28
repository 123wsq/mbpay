package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.TranSerialRecordInfDao;
import com.tangdi.production.mpapp.service.TranSerialRecordService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 交易信息查询接口
 * author huchunyuan
 *
 */
@Service
public class TranSerialRecordServiceImpl implements TranSerialRecordService {
	private static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	private TranSerialRecordInfDao dao;
	@Autowired
	private GetSeqNoService seqNoService;

	
	@Override
	public int addTranSerialRecordInf(Map<String, Object> param)
			throws TranException {
		log.info("新增交易信息：{}",param);
		int rt=0;
		String recId=null;
		try {
			recId=TdExpBasicFunctions.GETDATE()+seqNoService.getSeqNoNew("REC", "9", "1");
			param.put("recId", recId);
			rt=dao.insertEntity(param);
		} catch (Exception e) {
			log.error(ExcepCode.EX900000,e);
			throw new TranException(ExcepCode.EX900000,"新增交易信息异常",e);
		}
		if (rt==0) {
			//throw new TranException(ExcepCode.EX000815,"新增交易信息失败");
		}
		return rt;
	}

}
