package com.tangdi.production.mpbase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.TermStepDesc;
import com.tangdi.production.mpbase.dao.TermStepDao;
import com.tangdi.production.mpbase.service.TermStepService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

@Service
public class TermStepServiceImpl implements TermStepService {
	private static final Logger log = LoggerFactory.getLogger(TermStepServiceImpl.class);
	@Autowired
	private TermStepDao termStepDao;

	@Override
	public void saveTermStepDesc(String termNo, int termStep, String... args) {
		log.info("记录终端信息开始: 终端号{} 步骤{} 其他参数{}", termNo, termStep, args);
		Map<String, Object> param = new HashMap<String, Object>();
		// termNo,termStep,termDesc,termOper,termDate
		try {
			param.put("termNo", termNo);
			param.put("termStep", termStep);
			param.put("termDesc", TermStepDesc.getTermStepDesc(termStep, args));
			param.put("termOper", args[0]);
			param.put("termDate", TdExpBasicFunctions.GETDATETIME());

			insertEntity(param);
		} catch (Exception e) {
			log.info("记录终端信息 异常", e);
		}

		log.info("记录终端信息 结束  -------------");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Integer insertEntity(Map<String, Object> param) throws Exception {
		return termStepDao.insertEntity(param);
	}

	@Override
	public Integer countEntity(Map<String, Object> param) throws Exception {
		return termStepDao.countEntity(param);
	}

	@Override
	public List<Map<String, Object>> selectList(Map<String, Object> param) throws Exception {
		return termStepDao.selectList(param);
	}
}
