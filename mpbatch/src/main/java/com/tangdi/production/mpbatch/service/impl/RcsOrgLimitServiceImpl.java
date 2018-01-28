package com.tangdi.production.mpbatch.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbatch.dao.RcsOrgLimitDao;
import com.tangdi.production.mpbatch.service.RcsOrgLimitService;

/**
 * 
 * @author limiao
 *
 */
@Service
public class RcsOrgLimitServiceImpl implements RcsOrgLimitService {
	private static Logger log = LoggerFactory.getLogger(RcsOrgLimitServiceImpl.class);
	@Autowired
	private RcsOrgLimitDao rcsOrgLimitDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void process() throws Exception {
		log.info("跑批  修改合作机构限额 DAY MONTH     begin");

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		Map<String, Object> resultMap = rcsOrgLimitDao.selectRcsOrgLimit(parameterMap);

		if (resultMap != null && resultMap.size() > 0) {
			log.info("获取到的 day:{},month:{} ", resultMap.get("DAY"), resultMap.get("MONTH"));

			// 如果 当前月和查询出来的不一样那么 修改成当前月
			if (!DateUtil.getCurrentMonth().equals(resultMap.get("MONTH"))) {
				parameterMap.put("month", DateUtil.getCurrentMonth());
			}
			parameterMap.put("day", DateUtil.getCurrentDay());

			Integer result = rcsOrgLimitDao.updateRcsOrgLimit(parameterMap);
			log.info("跑批 修改合作机构限额 DAY MONTH 【" + result + " 】条");
		}

		log.info("跑批  修改合作机构限额 DAY MONTH     end");
	}
}
