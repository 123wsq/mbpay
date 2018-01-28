package com.tangdi.production.mpaychl.schedule.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.mpaychl.dao.CooporgMerInfDao;
import com.tangdi.production.mpaychl.schedule.service.ZLPayBankSignService;
import com.tangdi.production.mpaychl.trans.process.service.TranProcessService;
import com.tangdi.production.tdbase.context.SpringContext;

/**
 * 中联商户自动签到 <br>
 * <br>
 * 
 * @author chenlibo
 */
@Service
public class ZLPayBankSignServiceImpl implements ZLPayBankSignService {
	private static Logger log = LoggerFactory.getLogger(ZLPayBankSignServiceImpl.class);
	
	@Autowired
	private CooporgMerInfDao dao;
	
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpaychlConfig}")
	private Properties prop;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("中联商户自动签到     begin");
		
		CooporgMerInf entity = new CooporgMerInf();
		String orgNo = prop.getProperty("ZL_ORGNO");
		entity.setCooporgNo(orgNo);
		
		List<CooporgMerInf> coopOrgList = dao.selectList(entity);
		
		if(coopOrgList == null || coopOrgList.size() == 0){
			log.info("机构号为" + orgNo + "大商户不存在, 自动签到    end");
			return;
		}
		
		TranProcessService tranProcess = SpringContext.getBean(
				prop.getProperty("CORG_TRAN_" + orgNo), 
				TranProcessService.class);
		for(CooporgMerInf selectEntity: coopOrgList){
			String mercId = selectEntity.getMerNo();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("merno", mercId);
			param.put("orgNo", prop.getProperty("ZL_ORGNO"));
			param.put("rtrcod", prop.getProperty("ZL_SIGN_COD"));
			param.put("rtrsvr", prop.getProperty("ZL_SIGN_SVR"));
			Map<String, Object> rmap = tranProcess.banksign(param);
			log.info("中联商户" + mercId + "签到返回结果:{}",rmap);
			String retcode = String.valueOf(rmap.get(MsgCT.RETCOE));
			log.info("返回码[{}]", retcode);
		}

		log.info("中联商户自动签到     end");
	}
}
