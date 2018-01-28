package com.tangdi.production.mpapp.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpapp.dao.AgentDao;
import com.tangdi.production.mpapp.service.AgentService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * 代理商接口实现类
 * @author zhuji
 *
 */
@Service
public class AgentServiceImpl implements AgentService {
	private static Logger log = LoggerFactory
			.getLogger(AgentServiceImpl.class);
	@Autowired
	private AgentDao agentDao;
	
	@Override
	public Map<String, Object> checkAgent(Map<String, Object> param)
			throws TranException {
		ParamValidate.doing(param, "custId");
		Map<String , Object> resultMap=null;
		try {
			resultMap=agentDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000500,e);
		}
		//验证存在
		if (resultMap==null||resultMap.size()<=0) {
			throw new TranException(ExcepCode.EX000503);
		}
		log.debug("代理商查询信息：{}",resultMap);
		//验证冻结
		if (resultMap.get("frozState").equals("1")) {
			throw new TranException(ExcepCode.EX000502);
		}
		//验证禁用
		if (resultMap.get("ageStatus").equals("1")) {
			throw new TranException(ExcepCode.EX000501);
		}
		return resultMap;
	}
	@Override
	public String getThreeAgentId(Map<String, Object> param) throws TranException {
		ParamValidate.doing(param, "agentId");
		Map<String , Object> resultMap=null;
		try {
			resultMap=agentDao.selectAgentRate(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000500,e);
		}
		
		String agentId = (String) resultMap.get("agentId");//代理商编号
		String agentDgr = (String) resultMap.get("agentDgr");//代理商等级
		String fathAgentId = (String) resultMap.get("fathAgentId");//上级代理商编号
		String firstAgentId = (String) resultMap.get("firstAgentId");//一级代理商编号
		
		agentId = StringUtils.rightPad(agentId, 15, "F");
		String agentId2 = "FFFFFFFFFFFFFFF";
		String agentId3 = "FFFFFFFFFFFFFFF";
		
		if(agentDgr.equals("2")){
			agentId2 = StringUtils.rightPad(fathAgentId, 15, "F");
		}else if(agentDgr.equals("3")){
			agentId2 = StringUtils.rightPad(fathAgentId, 15, "F");
			agentId3 = StringUtils.rightPad(firstAgentId, 15, "F");
		}
		
		return agentId+agentId2+agentId3;
	}
	@Override
	public String checkWebAgent(Map<String, Object> param) throws TranException {
		// TODO Auto-generated method stub
		
		ParamValidate.doing(param, "custId");
		Map<String , Object> resultMap=null;
		String agentId = "";
		try {
			resultMap=agentDao.selectEntity(param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000500,e);
		}
		//验证存在
		if (resultMap==null||resultMap.size()<=0) {
			agentId = "";
		}
		
		agentId = resultMap.get("agentId").toString();
		
		log.debug("代理商查询信息：{}",resultMap);
		
		
		return agentId;
	}


}
