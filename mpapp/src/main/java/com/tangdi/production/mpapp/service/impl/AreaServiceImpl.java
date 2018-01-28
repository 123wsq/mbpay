package com.tangdi.production.mpapp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.AreaDao;
import com.tangdi.production.mpapp.service.AreaService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;

/**
 * app登录接口实现类
 * @author zhengqiang
 *
 */
@Service
public class AreaServiceImpl implements  AreaService{
	private static Logger log = LoggerFactory
			.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Map<String, Object>> getProvince() throws TranException {
		List<Map<String, Object>> pro=null;
		try {
			pro=areaDao.getProvince();
			log.debug("省市查询结果：{}",pro);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001008,"省市查询异常",e);
		}
		if (pro==null||pro.size()<=0) {
			throw new TranException(ExcepCode.EX001008,"省市查询失败");
		}
		return pro;
	}

}
