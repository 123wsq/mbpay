package com.tangdi.production.mpamng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.bean.AgentFeeInfo;
import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.AgentProfitInf;
import com.tangdi.production.mpamng.bean.CityInf;
import com.tangdi.production.mpamng.bean.ProvinceInf;
import com.tangdi.production.mpamng.constants.CT;
import com.tangdi.production.mpamng.dao.AgentDao;
import com.tangdi.production.mpamng.dao.AgentFeeDao;
import com.tangdi.production.mpamng.dao.AgentProfitDao;
import com.tangdi.production.mpamng.dao.AgentTempDao;
import com.tangdi.production.mpamng.service.AgentService;
import com.tangdi.production.mpbase.bean.FileInf;
import com.tangdi.production.mpbase.dao.FileUploadDao;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.dao.UserDao;
import com.tangdi.production.tdauth.service.UserService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.DictUtils;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

@Service
public class AgentServiceImpl implements AgentService {

	private static final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);
	
	
	/**
	 * mpaychl 模块配置文件
	 */
	@Value("#{mpamngConfig}")
	private Properties prop;
	
	@Autowired
	private AgentDao agentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FileUploadDao fileUploadDao;

	@Autowired
	private AgentTempDao agentTempDao;

	@Autowired
	private GetSeqNoService seqNoService;

	@Autowired
	private UserService userService;

	@Autowired
	private AgentFeeDao agentFeeDao;
	
	@Autowired
	private AgentProfitDao agentProfitDao;
	//hessin 传送到posp
	@Autowired
	private HessinClientBean remoteService;
	
	@Override
	public List<AgentInf> getListPage(AgentInf entity) throws Exception {
		if("%".equals(entity.getLogonName())){
		return null;
	}
		return agentDao.selectList(entity);
	}
	@Override
	public Integer getCount(AgentInf entity) throws Exception {
		return agentDao.countEntity(entity);
	}

	@Override
	public AgentInf getEntity(AgentInf entity) throws Exception {
		AgentInf agentInf = agentDao.selectEntity(entity);
		if (agentInf.getOemPicId() != null && !"".equals(agentInf.getOemPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getOemPicId()));
			if (fileInf != null) {
				agentInf.setOemPicName(fileInf.getFjName());
				log.debug("OemPicId = " + agentInf.getOemPicId() + "  OemPicName = " + agentInf.getOemPicName());
			}
		}
		if (agentInf.getOpeningLicensePicId() != null && !"".equals(agentInf.getOpeningLicensePicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getOpeningLicensePicId()));
			if (fileInf != null) {
				agentInf.setOpeningLicensePicName(fileInf.getFjName());
				log.debug("getOpeningLicensePicId = " + agentInf.getOpeningLicensePicId() + "  getOpeningLicensePicName = " + agentInf.getOpeningLicensePicName());
			}
		}
		if (agentInf.getBusinessLicensePicId() != null && !"".equals(agentInf.getBusinessLicensePicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getBusinessLicensePicId()));
			if (fileInf != null) {
				agentInf.setBusinessLicensePicName(fileInf.getFjName());
				log.debug("getBusinessLicensePicId = " + agentInf.getBusinessLicensePicId() + "  getBusinessLicensePicName = " + agentInf.getBusinessLicensePicName());
			}
		}
		if (agentInf.getLegalIdentityPicId() != null && !"".equals(agentInf.getLegalIdentityPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getLegalIdentityPicId()));
			if (fileInf != null) {
				agentInf.setLegalIdentityPicName(fileInf.getFjName());
				log.debug("getLegalIdentityPicId = " + agentInf.getLegalIdentityPicId() + "  getLegalIdentityPicName = " + agentInf.getLegalIdentityPicName());
			}
		}
		if (agentInf.getTaxNoPicId() != null && !"".equals(agentInf.getTaxNoPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getTaxNoPicId()));
			if (fileInf != null) {
				agentInf.setTaxNoPicName(fileInf.getFjName());
				log.debug("getTaxNoPicId = " + agentInf.getTaxNoPicId() + "  getTaxNoPicName = " + agentInf.getTaxNoPicName());
			}
		}
		return agentInf;
	}

	public AgentInf getAgentTemp(AgentInf entity) throws Exception {
		AgentInf agentInf = agentDao.selectTempEntity(entity);
		if (agentInf.getOemPicId() != null && !"".equals(agentInf.getOemPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getOemPicId()));
			if (fileInf != null) {
				agentInf.setOemPicName(fileInf.getFjName());
				log.debug("OemPicId = " + agentInf.getOemPicId() + "  OemPicName = " + agentInf.getOemPicName());
			}
		}
		if (agentInf.getOpeningLicensePicId() != null && !"".equals(agentInf.getOpeningLicensePicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getOpeningLicensePicId()));
			if (fileInf != null) {
				agentInf.setOpeningLicensePicName(fileInf.getFjName());
				log.debug("getOpeningLicensePicId = " + agentInf.getOpeningLicensePicId() + "  getOpeningLicensePicName = " + agentInf.getOpeningLicensePicName());
			}
		}
		if (agentInf.getBusinessLicensePicId() != null && !"".equals(agentInf.getBusinessLicensePicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getBusinessLicensePicId()));
			if (fileInf != null) {
				agentInf.setBusinessLicensePicName(fileInf.getFjName());
				log.debug("getBusinessLicensePicId = " + agentInf.getBusinessLicensePicId() + "  getBusinessLicensePicName = " + agentInf.getBusinessLicensePicName());
			}
		}
		if (agentInf.getLegalIdentityPicId() != null && !"".equals(agentInf.getLegalIdentityPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getLegalIdentityPicId()));
			if (fileInf != null) {
				agentInf.setLegalIdentityPicName(fileInf.getFjName());
				log.debug("getLegalIdentityPicId = " + agentInf.getLegalIdentityPicId() + "  getLegalIdentityPicName = " + agentInf.getLegalIdentityPicName());
			}
		}
		if (agentInf.getTaxNoPicId() != null && !"".equals(agentInf.getTaxNoPicId())) {
			FileInf fileInf = fileUploadDao.selectEntity(new FileInf(agentInf.getTaxNoPicId()));
			if (fileInf != null) {
				agentInf.setTaxNoPicName(fileInf.getFjName());
				log.debug("getTaxNoPicId = " + agentInf.getTaxNoPicId() + "  getTaxNoPicName = " + agentInf.getTaxNoPicName());
			}
		}
		return agentInf;
	}

	@Override
	public int addEntity(AgentInf entity) throws Exception {
		return 0;
	}

	@Override
	public int modifyEntity(AgentInf entity) throws Exception {
		return 0;
	}

	@Override
	public int removeEntity(AgentInf entity) throws Exception {
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReturnMsg modifyAgentInf(AgentInf agentInf,String currentAgentId,HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		String agentIDStr = agentInf.getAgentId();
		if (agentIDStr == null || "".equals(agentIDStr)) {
			log.debug("代理商编号不能为空！");
			rm.setFail("代理商编号不能为空！");
			return rm;
		}
		AgentInf agt = new AgentInf();
		agt.setAgentId(agentIDStr);
		agt.setAuditStatus(CT.AUDIT_STATUS_APPLY);
		int count = agentDao.countStatus(agt);
		if (count > 0) {
			log.debug("修改尚未审批，请稍后再修改！");
			rm.setFail("修改尚未审批，请稍后再修改！");
			return rm;
		}
		agt.setLogonName(agentInf.getLogonName());
		count = agentDao.countEntity(agt);
		if (count == 0) {// agentID和logonName与原本不同，则证明logonName有更改，验证是否重复
			agt = new AgentInf();
			agt.setLogonName(agentInf.getLogonName());
			int count2 = agentDao.countEntity(agt);// 现有代理商logonName是否重复
			agt.setAuditStatus(CT.AUDIT_STATUS_APPLY);
			int count3 = agentDao.countStatus(agt);// 现有申请logonName是否重复
			if (count2 > 0 || count3 > 0) {
				log.info(count2+"-----------"+count3);
				rm.setFail("登录名：" + agentInf.getLogonName() + " 已存在，请更改登录名！");
				return rm;
			}
		}
		
		//判断是否为代理商角色
		if (!currentAgentId.equals(Constant.SYS_AGENT_ID)) {
			// 顶级代理审核和逐级审核
			String dict = DictUtils.get("AUDIT_METHOD");
			if (dict.equals(com.tangdi.production.mpamng.constants.CT.FIRST_AGENT_AUDIT)) {
				agentInf.setAuditAgentid(agentInf.getFirstAgentId());
			} else if (dict.equals(com.tangdi.production.mpamng.constants.CT.LEVEL_AUDIT)) {
				agentInf.setAuditAgentid(agentInf.getFathAgentId());
			} else if (dict.equals(com.tangdi.production.mpamng.constants.CT.ADMIN_AUDIT)) {
				agentInf.setAuditAgentid("0");
			} else {
				agentInf.setAuditAgentid("0");
			}
		}else{
			agentInf.setAuditAgentid("0");
		}
		/*agentDao.deleteTempEntity(agentInf);
		agentDao.insertTempEntity(agentInf);
		rm.setSuccess("申请已提交，等待审核！");*/
		agentDao.updateTempEntity(agentInf);
		agentDao.updateEntity(agentInf);
		modifyAgentProfitInf(request, agentInf.getAgentId());
		modifyAgentFeeInf(request, agentInf.getAgentId());
		rm.setSuccess("代理商信息修改成功！");
		return rm;
	}

	/**
	 * 代理商开户
	 * 1.开户检查，用户名审核表以及代理商表验存
	 * 2.判定当前登陆角色为运营系统角色还是代理商系统角色
	 * 3.更具当前登陆角色分别设置需要开户的代理商属性以及审核步骤
	 * 4.设定费率
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReturnMsg addAgentInf(AgentInf agentInf, String agentId, HttpServletRequest request) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		AgentInf agt = new AgentInf();
		agt.setLogonName(agentInf.getLogonName());
		int count2 = 0;
		int count3 = 0;
		int tempAgeNCount = 0;
		int ageNCount = 0;
		try {
			//判定当前用户是否为代理商
			if (!agentId.equals(Constant.SYS_AGENT_ID)) {
				agt.setFathAgentId(agentId);
				/**
				 * 代理商开户申请：存入临时表；审核通过：1.代理商信息插入主表 2.更新临时表中的信息 3.在用户表中添加用户信息
				 */
				UserInf user=new UserInf();
				user.setUserId(agentInf.getLogonName());
				user.setAgentId(agentId);
				//根据当前代理商ID查询该代理商下的操作员登录名,比较是否重复
				// 现有该代理商下的LogonName是否重复
				count2 = userDao.countLonEntity(user);
				//根据当前代理商ID查询该代理商申请的下级代理商中审核中的代理商登录名，比较是否重复
				// 现有申请未审核代理商LogonName是否重复
				count3 = agentDao.countLoNTempEntity(agt);
			}else{
				// 现有代理商LogonName是否重复
				count2 = agentDao.countEntity(agt);
				agt.setAuditStatus(CT.AUDIT_STATUS_APPLY);
				// 现有申请LogonName是否重复
				count3 = agentDao.countTempEntity(agt);
			}
			//代理商名称重复验证
			agt.setAgentName(agentInf.getAgentName());
			tempAgeNCount=agentDao.countAgeNTempEntity(agentInf);
			ageNCount=agentDao.countAgeNEntity(agentInf);
		} catch (Exception e) {
			log.error("代理商信息验证失败", e);
			throw new Exception("代理商信息验证失败！", e);
		}
		
		log.debug("count2: " + count2 + ", count3: " + count3);
		if (count2 > 0 || count3 > 0) {
			log.debug("登录名：" + agentInf.getLogonName() + " 已存在，请更改登录名！");
			rm.setFail("登录名：" + agentInf.getLogonName() + " 已存在，请更改登录名！");
			return rm;
		}
		
		log.debug("tempAgeNCount: " + tempAgeNCount + ", ageNCount: " + ageNCount);
		if (tempAgeNCount>0||ageNCount>0) {
			log.debug("代理商名称：" + agentInf.getAgentName() + " 已存在，请更改代理商名称！");
			rm.setFail("代理商名称：" + agentInf.getAgentName() + " 已存在，请更改代理商名称！");
			return rm;
		}
		//生成ID以及code
		String agentID = TdExpBasicFunctions.GETDATE().substring(2, 6) + seqNoService.getSeqNoNew("AGENT_ID_SEQ", "5", "1");
		String agentCode = seqNoService.getSeqNoNew("AGENT_CODE_SEQ", "4", "1");
		//根据当前角色创建代理商
		if (!agentId.equals(Constant.SYS_AGENT_ID)) {
			AgentInf param = new AgentInf();
			param.setAgentId(agentId);
			AgentInf currentAgentInf = agentDao.selectEntity(param);
			String dgr = Integer.parseInt(currentAgentInf.getAgentDgr()) + 1 + "";
			agentInf.setAgentDgr(dgr);
			agentInf.setFathAgentId(agentId);
			agentInf.setFirstAgentId(currentAgentInf.getFirstAgentId());
			// 二级代理agentId以0开头
			agentID = "0" + agentID;
			// 新开代理商等级+1
			agentInf.setAgentDgr((Integer.parseInt(currentAgentInf.getAgentDgr()) + 1) + "");
			// 新代理商Code为父代理Code+4位序列
			agentInf.setAgentCode(currentAgentInf.getAgentCode() + agentCode);
			// 父代理商Code为父代理商Code
			String currentCode = currentAgentInf.getAgentCode();
			agentInf.setFatherCode(currentCode);
			agentInf.setAgentCode(currentCode + agentCode);
			// 顶级代理审核和逐级审核
			String dict = DictUtils.get("AUDIT_METHOD");
			if (dict.equals(CT.FIRST_AGENT_AUDIT)) {
				agentInf.setAuditAgentid(currentAgentInf.getFirstAgentId());
			} else if (dict.equals(CT.LEVEL_AUDIT)) {
				agentInf.setAuditAgentid(currentAgentInf.getFathAgentId());
			} else if (dict.equals(CT.ADMIN_AUDIT)) {
				agentInf.setAuditAgentid("0");
			} else {
				agentInf.setAuditAgentid("0");
			}
		}else{
			agentID = "8" + agentID;
			agentInf.setAgentDgr("1");
			agentInf.setAgentCode(agentCode);
			// 一级代理商的父代理Code为0000
			agentInf.setFatherCode("0000");
			agentInf.setFathAgentId(agentID);
			agentInf.setFirstAgentId(agentID);
		}
		//代理商费率设置
//		log.debug("OEM状态为："+agentInf.getOemState());
//		log.debug("是否冻结："+agentInf.getFrozState());
		agentInf.setAgentId(agentID);
		
		agentInf.setAuditStatus(CT.AUDIT_STATUS_APPLY);
		agentDao.deleteTempEntity(agentInf);
		agentDao.insertTempEntity(agentInf);

		// 添加代理商费率信息
		addAgentFeeInf(request, agentID);
		
		// 添加代理商分润阶梯信息
		addAgentProfitInf(request, agentID);
		
		rm.setSuccess("申请已提交，等待审核！");
		return rm;
	}

	@Override
	public Integer countAgentTemp(AgentInf agentInf) throws Exception {
		return agentDao.countTempEntity(agentInf);
	}

	@Override
	public List<AgentInf> getAgentTempPage(AgentInf agentInf) throws Exception {
		return agentDao.selectTempList(agentInf);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReturnMsg agentTempReply(String agentId, String approved,String currentId, String auditFailReason,String operId) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		AgentInf agt = new AgentInf();
		agt.setAgentId(agentId);
		if ("0".equals(approved)) {// 审核不通过时
			agt.setAuditStatus(CT.AUDIT_STATUS_DISAGREE);
			agt.setAuditFailReason(auditFailReason);
			agentDao.updateAuditStatus(agt);
			log.debug("代理商审核成功！审核不通过！agentID为：" + agt.getAgentId());
			rm.setSuccess("代理商审核成功！");
			return rm;
		}
		if (currentId.equals(Constant.SYS_AGENT_ID)) {
			// 审核通过时
			AgentInf agentInf = agentDao.selectTempEntity(agt);
			int count = agentDao.countEntity(agt);
			if (count > 0) {// 代理商修改
				// 删除原有的信息 如果存在的话
				agentDao.deleteEntity(agt);
			} else {
				try {
					userService.initAgentAdmin(agentId, agentInf.getLogonName(),operId,agentInf.getCity(),currentId);
				} catch (Exception e) {
					log.error("添加用户失败：" + e);
					rm.setFail("添加代理商登陆账户失败！");
					return rm;
				}
			}
			// 插入代理商信息
			agentDao.insertEntity(agentInf);
			// 更新原有申请信息
			/*agt.setAuditStatus(CT.AUDIT_STATUS_AGREE);
			agentDao.updateAuditStatus(agt);*/
			
			//删除临时表中状态为审核通过的代理商数据
			agentDao.deleteTempEntity(agentInf);
			rm.setSuccess("代理商审核成功！");
			return rm;
		}else{
			agt = agentDao.selectTempEntity(agt);
			AgentInf agt1 = new AgentInf();
			agt1.setAgentId(agentId);
			AgentInf current = new AgentInf();
			current.setAgentId(currentId);
			current = agentDao.selectEntity(current);
			// 非总后台审核的代理商申请，审核通过后交由一级
			String dict = DictUtils.get("AUDIT_METHOD");
			if (dict.equals(CT.FIRST_AGENT_AUDIT)) {
				agt1.setAuditAgentid("0");
			} else if (dict.equals(CT.LEVEL_AUDIT)) {
				agt1.setAuditAgentid(current.getFathAgentId());
			} else if (dict.equals(CT.ADMIN_AUDIT)) {
				agt1.setAuditAgentid("0");
			} else {
				agt1.setAuditAgentid("0");
			}
			agentDao.updateTempEntity(agt1);
			log.debug("代理商审核成功！等待总代理审核！agentID为：" + agentId);
			rm.setSuccess("代理商审核成功，等待总代理审核！");
			return rm;
		}
	}

	/**
	 * 代理商密码重置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReturnMsg agentPasswdReset(String agentIds,String logonNames,String operId) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		if (agentIds == null || "".equals(agentIds)) {
			log.error("请选择要重置密码的代理商！");
			rm.setFail("请选择要重置密码的代理商");
			return rm;
		}
		String[] agentIdSet = agentIds.split(",");
		String[] logonNameSet = logonNames.split(",");
		for (int i = 0; i < agentIdSet.length; i++) {
			String agentId=agentIdSet[i];
			String logonName=logonNameSet[i];
			userService.resetAgentAdminPwd(agentId,logonName,operId);
		}
		log.debug("代理商密码重置成功！"+Constant.SYS_PWD);
		rm.setSuccess("密码已重置为"+ Constant.SYS_PWD + ",重新登录时生效！");
		return rm;
	}

	@Override
	public String getProvince(String id) {
		try {
			List<ProvinceInf> provinceInfList = agentDao.getProvinceList();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> nmap = new HashMap<String, Object>();
			for (int i = 0; i < provinceInfList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				ProvinceInf provinceInf = provinceInfList.get(i);
				map.put("text", provinceInf.getProvinceName());
				map.put("value", provinceInf.getProvinceId());
				list.add(map);
			}
			nmap.put("options", list);
			return JUtil.toJsonString(nmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCity(String id) {
		try {
			CityInf city = new CityInf();
			city.setProvinceId(id);
			List<CityInf> cityInfList = agentDao.getCityList(city);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> nmap = new HashMap<String, Object>();
			for (int i = 0; i < cityInfList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				CityInf cityInf = cityInfList.get(i);
				map.put("text", cityInf.getCityName());
				map.put("value", cityInf.getCityId());
				list.add(map);
			}
			nmap.put("options", list);
			return JUtil.toJsonString(nmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryOem(Map<String, Object> map) {
		try {
			return agentDao.queryOem(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询运营平台费率
	 * @throws Exception 
	 */
	@Override
	public List<Map<String, Object>> outOfRatePlatform(Map<String, Object> map) throws Exception {
			return agentDao.queryplatform(map);
	}
	/**
	 * 查询代理商费率
	 */
	@Override
	public AgentInf outOfRateAgent(Map<String, Object> map)
			throws Exception {
		return agentDao.queryagentrate(map);
	}

	/***
	 * 修改代理商费率信息
	 * @param request
	 * @param agentId
	 * @throws Exception
	 */
	private void modifyAgentFeeInf(HttpServletRequest request, String agentId) throws Exception {
		log.debug("修改费率开始");
		try{
			String rateTCas = request.getParameter("rateTCas");
			String maxTCas = request.getParameter("maxTCas");
		    String rateLivelihood = request.getParameter("rateLivelihood");
			String rateGeneral = request.getParameter("rateGeneral");
			String rateGeneralTop = request.getParameter("rateGeneralTop");
		    String rateGeneralMaximun = request.getParameter("rateGeneralMaximun");
			String rateEntertain = request.getParameter("rateEntertain");
			String rateEntertainTop = request.getParameter("rateEntertainTop");
			String rateEntertainMaximun = request.getParameter("rateEntertainMaximun");

			String rateTCas_KJ = request.getParameter("rateTCas_KJ");
			String maxTCas_KJ = request.getParameter("maxTCas_KJ");
		    String rateLivelihood_KJ = request.getParameter("rateLivelihood_KJ");
			String rateGeneral_KJ = request.getParameter("rateGeneral_KJ");
			String rateGeneralTop_KJ = request.getParameter("rateGeneralTop_KJ");
		    String rateGeneralMaximun_KJ = request.getParameter("rateGeneralMaximun_KJ");
			String rateEntertain_KJ = request.getParameter("rateEntertain_KJ");
			String rateEntertainTop_KJ = request.getParameter("rateEntertainTop_KJ");
			String rateEntertainMaximun_KJ = request.getParameter("rateEntertainMaximun_KJ");
			
			String rateTCas_SM = request.getParameter("rateTCas_SM");
			String maxTCas_SM = request.getParameter("maxTCas_SM");
		    String rateLivelihood_SM = request.getParameter("rateLivelihood_SM");
			String rateGeneral_SM = request.getParameter("rateGeneral_SM");
			String rateGeneralTop_SM = request.getParameter("rateGeneralTop_SM");
		    String rateGeneralMaximun_SM = request.getParameter("rateGeneralMaximun_SM");
			String rateEntertain_SM = request.getParameter("rateEntertain_SM");
			String rateEntertainTop_SM = request.getParameter("rateEntertainTop_SM");
			String rateEntertainMaximun_SM = request.getParameter("rateEntertainMaximun_SM");
			
			AgentFeeInfo agentFeeInfo = new AgentFeeInfo();
			agentFeeInfo.setRateCode("00");
			agentFeeInfo.setRateId(agentId);
			agentFeeInfo.setRateType("02");
			agentFeeInfo.setRateTCas(rateTCas);
			agentFeeInfo.setMaxTCas(maxTCas);
			agentFeeInfo.setRateLivelihood(rateLivelihood);
			agentFeeInfo.setRateGeneral(rateGeneral);
			agentFeeInfo.setRateGeneralTop(rateGeneralTop);
			agentFeeInfo.setRateGeneralMaximun(rateGeneralMaximun);
			agentFeeInfo.setRateEntertain(rateEntertain);
			agentFeeInfo.setRateEntertainTop(rateEntertainTop);
			agentFeeInfo.setRateEntertainMaximun(rateEntertainMaximun);
			agentFeeInfo.setIpdTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.updateEntity(agentFeeInfo);
			
			AgentFeeInfo agentFeeInfo_KJ = new AgentFeeInfo();
			agentFeeInfo_KJ.setRateCode("00");
			agentFeeInfo_KJ.setRateId(agentId);
			agentFeeInfo_KJ.setRateType("03");
			agentFeeInfo_KJ.setRateTCas(rateTCas_KJ);
			agentFeeInfo_KJ.setMaxTCas(maxTCas_KJ);
			agentFeeInfo_KJ.setRateLivelihood(rateLivelihood_KJ);
			agentFeeInfo_KJ.setRateGeneral(rateGeneral_KJ);
			agentFeeInfo_KJ.setRateGeneralTop(rateGeneralTop_KJ);
			agentFeeInfo_KJ.setRateGeneralMaximun(rateGeneralMaximun_KJ);
			agentFeeInfo_KJ.setRateEntertain(rateEntertain_KJ);
			agentFeeInfo_KJ.setRateEntertainTop(rateEntertainTop_KJ);
			agentFeeInfo_KJ.setRateEntertainMaximun(rateEntertainMaximun_KJ);
			agentFeeInfo_KJ.setIpdTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.updateEntity(agentFeeInfo_KJ);
			
			AgentFeeInfo agentFeeInfo_SM = new AgentFeeInfo();
			agentFeeInfo_SM.setRateCode("00");
			agentFeeInfo_SM.setRateId(agentId);
			agentFeeInfo_SM.setRateType("04");
			agentFeeInfo_SM.setRateTCas(rateTCas_SM);
			agentFeeInfo_SM.setMaxTCas(maxTCas_SM);
			agentFeeInfo_SM.setRateLivelihood(rateLivelihood_SM);
			agentFeeInfo_SM.setRateGeneral(rateGeneral_SM);
			agentFeeInfo_SM.setRateGeneralTop(rateGeneralTop_SM);
			agentFeeInfo_SM.setRateGeneralMaximun(rateGeneralMaximun_SM);
			agentFeeInfo_SM.setRateEntertain(rateEntertain_SM);
			agentFeeInfo_SM.setRateEntertainTop(rateEntertainTop_SM);
			agentFeeInfo_SM.setRateEntertainMaximun(rateEntertainMaximun_SM);
			agentFeeInfo_SM.setIpdTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.updateEntity(agentFeeInfo_SM);			
		}catch(Exception e){
			log.error("费率信息修改失败！", e);
			throw new Exception("费率信息修改失败！", e);
		}
		
		log.debug("修改费率信息成功");
	}
	
	/***
	 * 修改代理商分润阶梯信息
	 * @param request
	 * @param agentId
	 * @throws Exception
	 */
	private void modifyAgentProfitInf(HttpServletRequest request, String agentId) throws Exception {
		log.debug("修改分润阶梯开始");
		try{
			for(int i = 1; i <= 4; i++){
				String gradeStartNum = request.getParameter("grade_" + i + "_start");
				String gradeEndNum = request.getParameter("grade_" + i + "_end").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end");
				String rates = request.getParameter("profitRatio_" + i);
				
				AgentProfitInf agentProfitInfo = new AgentProfitInf();
				agentProfitInfo.setAgentId(agentId);
				agentProfitInfo.setBeginNum(gradeStartNum);
				agentProfitInfo.setEndNum(gradeEndNum);
				agentProfitInfo.setRates(rates);
				agentProfitInfo.setShowNum(String.valueOf(i));
				agentProfitInfo.setStatus("0");
				agentProfitInfo.setRateType("02");
				agentProfitDao.updateEntity(agentProfitInfo);
			}
			
			for(int i = 1; i <= 4; i++){
				String gradeStartNum_KJ = request.getParameter("grade_" + i + "_start_KJ");
				String gradeEndNum_KJ = request.getParameter("grade_" + i + "_end_KJ").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end_KJ");
				String rates_KJ = request.getParameter("profitRatio_" + i + "_KJ");
				
				AgentProfitInf agentProfitInfo_KJ = new AgentProfitInf();
				agentProfitInfo_KJ.setAgentId(agentId);
				agentProfitInfo_KJ.setBeginNum(gradeStartNum_KJ);
				agentProfitInfo_KJ.setEndNum(gradeEndNum_KJ);
				agentProfitInfo_KJ.setRates(rates_KJ);
				agentProfitInfo_KJ.setShowNum(String.valueOf(i));
				agentProfitInfo_KJ.setStatus("0");
				agentProfitInfo_KJ.setRateType("03");
				agentProfitDao.updateEntity(agentProfitInfo_KJ);
			}
			
			for(int i = 1; i <= 4; i++){
				String gradeStartNum_SM = request.getParameter("grade_" + i + "_start_SM");
				String gradeEndNum_SM = request.getParameter("grade_" + i + "_end_SM").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end_SM");
				String rates_SM = request.getParameter("profitRatio_" + i + "_SM");
				
				AgentProfitInf agentProfitInfo_SM = new AgentProfitInf();
				agentProfitInfo_SM.setAgentId(agentId);
				agentProfitInfo_SM.setBeginNum(gradeStartNum_SM);
				agentProfitInfo_SM.setEndNum(gradeEndNum_SM);
				agentProfitInfo_SM.setRates(rates_SM);
				agentProfitInfo_SM.setShowNum(String.valueOf(i));
				agentProfitInfo_SM.setStatus("0");
				agentProfitInfo_SM.setRateType("04");
				agentProfitDao.updateEntity(agentProfitInfo_SM);
			}
		}catch(Exception e){
			log.error("分润阶梯修改失败！", e);
			throw new Exception("分润阶梯修改失败！", e);
		}
		log.debug("修改分润阶梯成功");
	}

	/***
	 * 添加代理商费率信息
	 * @param request
	 * @param agentId
	 * @throws Exception
	 */
	private void addAgentFeeInf(HttpServletRequest request, String agentId) throws Exception {
		log.debug("插入费率开始");
		try{
			String rateTCas = request.getParameter("rateTCas");
			String maxTCas = request.getParameter("maxTCas");
		    String rateLivelihood = request.getParameter("rateLivelihood");
			String rateGeneral = request.getParameter("rateGeneral");
			String rateGeneralTop = request.getParameter("rateGeneralTop");
		    String rateGeneralMaximun = request.getParameter("rateGeneralMaximun");
			String rateEntertain = request.getParameter("rateEntertain");
			String rateEntertainTop = request.getParameter("rateEntertainTop");
			String rateEntertainMaximun = request.getParameter("rateEntertainMaximun");

			String rateTCas_KJ = request.getParameter("rateTCas_KJ");
			String maxTCas_KJ = request.getParameter("maxTCas_KJ");
		    String rateLivelihood_KJ = request.getParameter("rateLivelihood_KJ");
			String rateGeneral_KJ = request.getParameter("rateGeneral_KJ");
			String rateGeneralTop_KJ = request.getParameter("rateGeneralTop_KJ");
		    String rateGeneralMaximun_KJ = request.getParameter("rateGeneralMaximun_KJ");
			String rateEntertain_KJ = request.getParameter("rateEntertain_KJ");
			String rateEntertainTop_KJ = request.getParameter("rateEntertainTop_KJ");
			String rateEntertainMaximun_KJ = request.getParameter("rateEntertainMaximun_KJ");
			
			String rateTCas_SM = request.getParameter("rateTCas_SM");
			String maxTCas_SM = request.getParameter("maxTCas_SM");
		    String rateLivelihood_SM = request.getParameter("rateLivelihood_SM");
			String rateGeneral_SM = request.getParameter("rateGeneral_SM");
			String rateGeneralTop_SM = request.getParameter("rateGeneralTop_SM");
		    String rateGeneralMaximun_SM = request.getParameter("rateGeneralMaximun_SM");
			String rateEntertain_SM = request.getParameter("rateEntertain_SM");
			String rateEntertainTop_SM = request.getParameter("rateEntertainTop_SM");
			String rateEntertainMaximun_SM = request.getParameter("rateEntertainMaximun_SM");
			
			AgentFeeInfo agentFeeInfo = new AgentFeeInfo();
			agentFeeInfo.setRateCode("00");
			agentFeeInfo.setRateId(agentId);
			agentFeeInfo.setRateType("02");
			agentFeeInfo.setRateTCas(rateTCas);
			agentFeeInfo.setMaxTCas(maxTCas);
			agentFeeInfo.setRateLivelihood(rateLivelihood);
			agentFeeInfo.setRateGeneral(rateGeneral);
			agentFeeInfo.setRateGeneralTop(rateGeneralTop);
			agentFeeInfo.setRateGeneralMaximun(rateGeneralMaximun);
			agentFeeInfo.setRateEntertain(rateEntertain);
			agentFeeInfo.setRateEntertainTop(rateEntertainTop);
			agentFeeInfo.setRateEntertainMaximun(rateEntertainMaximun);
			agentFeeInfo.setInsTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.insertEntity(agentFeeInfo);
			
			AgentFeeInfo agentFeeInfo_KJ = new AgentFeeInfo();
			agentFeeInfo_KJ.setRateCode("00");
			agentFeeInfo_KJ.setRateId(agentId);
			agentFeeInfo_KJ.setRateType("03");
			agentFeeInfo_KJ.setRateTCas(rateTCas_KJ);
			agentFeeInfo_KJ.setMaxTCas(maxTCas_KJ);
			agentFeeInfo_KJ.setRateLivelihood(rateLivelihood_KJ);
			agentFeeInfo_KJ.setRateGeneral(rateGeneral_KJ);
			agentFeeInfo_KJ.setRateGeneralTop(rateGeneralTop_KJ);
			agentFeeInfo_KJ.setRateGeneralMaximun(rateGeneralMaximun_KJ);
			agentFeeInfo_KJ.setRateEntertain(rateEntertain_KJ);
			agentFeeInfo_KJ.setRateEntertainTop(rateEntertainTop_KJ);
			agentFeeInfo_KJ.setRateEntertainMaximun(rateEntertainMaximun_KJ);
			agentFeeInfo_KJ.setInsTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.insertEntity(agentFeeInfo_KJ);
			
			AgentFeeInfo agentFeeInfo_SM = new AgentFeeInfo();
			agentFeeInfo_SM.setRateCode("00");
			agentFeeInfo_SM.setRateId(agentId);
			agentFeeInfo_SM.setRateType("04");
			agentFeeInfo_SM.setRateTCas(rateTCas_SM);
			agentFeeInfo_SM.setMaxTCas(maxTCas_SM);
			agentFeeInfo_SM.setRateLivelihood(rateLivelihood_SM);
			agentFeeInfo_SM.setRateGeneral(rateGeneral_SM);
			agentFeeInfo_SM.setRateGeneralTop(rateGeneralTop_SM);
			agentFeeInfo_SM.setRateGeneralMaximun(rateGeneralMaximun_SM);
			agentFeeInfo_SM.setRateEntertain(rateEntertain_SM);
			agentFeeInfo_SM.setRateEntertainTop(rateEntertainTop_SM);
			agentFeeInfo_SM.setRateEntertainMaximun(rateEntertainMaximun_SM);
			agentFeeInfo_SM.setInsTim(TdExpBasicFunctions.GETDATETIME());
			agentFeeDao.insertEntity(agentFeeInfo_SM);			
		}catch(Exception e){
			log.error("费率信息插入失败！", e);
			throw new Exception("费率信息插入失败！", e);
		}
		
		log.debug("插入费率信息成功");
	}
	
	/***
	 * 添加代理商分润阶梯信息
	 * @param request
	 * @param agentId
	 * @throws Exception
	 */
	private void addAgentProfitInf(HttpServletRequest request, String agentId) throws Exception {
		log.debug("插入分润阶梯开始");
		try{
			for(int i = 1; i <= 4; i++){
				String gradeStartNum = request.getParameter("grade_" + i + "_start");
				String gradeEndNum = request.getParameter("grade_" + i + "_end").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end");
				String rates = request.getParameter("profitRatio_" + i);
				
				AgentProfitInf agentProfitInfo = new AgentProfitInf();
				agentProfitInfo.setAgentId(agentId);
				agentProfitInfo.setBeginNum(gradeStartNum);
				agentProfitInfo.setEndNum(gradeEndNum);
				agentProfitInfo.setRates(rates);
				agentProfitInfo.setShowNum(String.valueOf(i));
				agentProfitInfo.setStatus("0");
				agentProfitInfo.setRateType("02");
				agentProfitDao.insertEntity(agentProfitInfo);
			}
			
			for(int i = 1; i <= 4; i++){
				String gradeStartNum_KJ = request.getParameter("grade_" + i + "_start_KJ");
				String gradeEndNum_KJ = request.getParameter("grade_" + i + "_end_KJ").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end_KJ");
				String rates_KJ = request.getParameter("profitRatio_" + i + "_KJ");
				
				AgentProfitInf agentProfitInfo_KJ = new AgentProfitInf();
				agentProfitInfo_KJ.setAgentId(agentId);
				agentProfitInfo_KJ.setBeginNum(gradeStartNum_KJ);
				agentProfitInfo_KJ.setEndNum(gradeEndNum_KJ);
				agentProfitInfo_KJ.setRates(rates_KJ);
				agentProfitInfo_KJ.setShowNum(String.valueOf(i));
				agentProfitInfo_KJ.setStatus("0");
				agentProfitInfo_KJ.setRateType("03");
				agentProfitDao.insertEntity(agentProfitInfo_KJ);
			}
			
			for(int i = 1; i <= 4; i++){
				String gradeStartNum_SM = request.getParameter("grade_" + i + "_start_SM");
				String gradeEndNum_SM = request.getParameter("grade_" + i + "_end_SM").equals("∞") ? 
						String.valueOf(Integer.MAX_VALUE) : request.getParameter("grade_" + i + "_end_SM");
				String rates_SM = request.getParameter("profitRatio_" + i + "_SM");
				
				AgentProfitInf agentProfitInfo_SM = new AgentProfitInf();
				agentProfitInfo_SM.setAgentId(agentId);
				agentProfitInfo_SM.setBeginNum(gradeStartNum_SM);
				agentProfitInfo_SM.setEndNum(gradeEndNum_SM);
				agentProfitInfo_SM.setRates(rates_SM);
				agentProfitInfo_SM.setShowNum(String.valueOf(i));
				agentProfitInfo_SM.setStatus("0");
				agentProfitInfo_SM.setRateType("04");
				agentProfitDao.insertEntity(agentProfitInfo_SM);
			}			
		}catch(Exception e){
			log.error("分润阶梯插入失败！", e);
			throw new Exception("分润阶梯插入失败！", e);
		}
		log.debug("插入分润阶梯成功");
	}
	
	/**
	 * 查询分润阶梯
	 */
	public List<AgentProfitInf> selectAgentProfitGrade(String agentId, String rateType) throws Exception{
		log.debug("查询分润阶梯开始");
		AgentProfitInf entity = new AgentProfitInf();
		entity.setAgentId(agentId);
		entity.setRateType(rateType);
		List<AgentProfitInf> list = agentProfitDao.selectGrade(entity);
		return list;
	}
	
	/**
	 * 查询费率
	 */
	public AgentFeeInfo selectAgentFeeGrade(String agentId, String rateType) throws Exception {
		AgentFeeInfo entity = new AgentFeeInfo();
		entity.setRateId(agentId);
		entity.setRateType(rateType);
		entity.setRateCode("00");
		return agentFeeDao.selectGrade(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyAgentStatus(Map<String,Object> map) throws Exception{
		log.info("更新代理商状态值:[{}]",map);
		return agentDao.updateAgentStatus(map);
	}
	@Override
	public int GetAgentCount(Map<String, Object> map) {
		
		return agentDao.getListAget(map);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateTerminal(AgentInf agentInf) {
		
		return agentDao.updateTerminal(agentInf);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateTerminalSum(AgentInf agentInf) {
		
		return agentDao.updateTerminalSum(agentInf);
	}
	/**
	 * 检查下级代理商的费率是否小于上级上级代理商（T0+T1）
	 * @throws Exception 
	 */
	@Override
	public boolean checkParentFeeInfo(AgentInf agentInf) throws Exception {
		
		Double agentFee = 0.0;
		Double agentFeeP = 0.0;
		
		String agentid = agentInf.getAgentId();
		AgentInf agentInfP = new AgentInf();
		String pagentid =  agentInf.getFathAgentId();
		agentInfP.setAgentId(pagentid);
		
		if(pagentid.equals(agentid)){
			return true;
		}
		
		String rateTCas = agentInf.getRateTCas();//T0提现费率
		String rateLivelihood = agentInf.getRateLivelihood();//民生类
		String rateGeneral = agentInf.getRateGeneral();//一般类
		String rateEntertain = agentInf.getRateEntertain();//餐娱类
		String rateGeneralTop = agentInf.getRateGeneralTop();//批发类
		String rateEntertainTop = agentInf.getRateEntertainTop();//房产类
		
		if(rateTCas!= null && !rateTCas.equals("")){
			agentFee = Double.parseDouble(rateTCas)+agentFee;
		}
		if(rateLivelihood!= null && !rateLivelihood.equals("")){
			agentFee = Double.parseDouble(rateLivelihood)+agentFee;
		}
		if(rateGeneral!= null && !rateGeneral.equals("")){
			agentFee = Double.parseDouble(rateGeneral)+agentFee;
		}
		if(rateEntertain!= null && !rateEntertain.equals("")){
			agentFee = Double.parseDouble(rateEntertain)+agentFee;
		}
		if(rateGeneralTop!= null && !rateGeneralTop.equals("")){
			agentFee = Double.parseDouble(rateGeneralTop)+agentFee;
		}
		if(rateEntertainTop!= null && !rateEntertainTop.equals("")){
			agentFee = Double.parseDouble(rateEntertainTop)+agentFee;
		}
		
		agentInfP = agentDao.selectEntity(agentInfP);
 		
		String rateTCas_p = agentInfP.getRateTCas();
		String rateLivelihood_p = agentInfP.getRateLivelihood();//民生类
		String rateGeneral_p  = agentInfP.getRateGeneral();//一般类
		String rateEntertain_p  = agentInfP.getRateEntertain();//餐娱类
		String rateGeneralTop_p  = agentInfP.getRateGeneralTop();//批发类
		String rateEntertainTop_p  = agentInfP.getRateEntertainTop();//房产类
		
		if(rateTCas_p!= null && !rateTCas_p.equals("")){
			agentFeeP = Double.parseDouble(rateTCas_p)+agentFeeP;
		}
		if(rateLivelihood_p!= null && !rateLivelihood_p.equals("")){
			agentFeeP = Double.parseDouble(rateLivelihood_p)+agentFeeP;
		}
		if(rateGeneral_p!= null && !rateGeneral_p.equals("")){
			agentFeeP = Double.parseDouble(rateGeneral_p)+agentFeeP;
		}
		if(rateEntertain_p!= null && !rateEntertain_p.equals("")){
			agentFeeP = Double.parseDouble(rateEntertain_p)+agentFeeP;
		}
		if(rateGeneralTop_p!= null && !rateGeneralTop_p.equals("")){
			agentFeeP = Double.parseDouble(rateGeneralTop_p)+agentFeeP;
		}
		if(rateEntertainTop_p!= null && !rateEntertainTop_p.equals("")){
			agentFeeP = Double.parseDouble(rateEntertainTop_p)+agentFeeP;
		}
		
		if(agentFee < agentFeeP){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 检查下级代理商的费率是否小于上级上级代理商（T0+T1）,支持不同交易类型
	 * @throws Exception 
	 */
	public boolean checkParentFeeInfoEx(AgentInf agentInf, String rateType) throws Exception {
		
		Double agentFee = 0.0;
		Double agentFeeP = 0.0;
		
		String agentid = agentInf.getAgentId();
		AgentInf agentInfP = new AgentInf();
		String pagentid =  agentInf.getFathAgentId();
		agentInfP.setAgentId(pagentid);
		log.debug("checkParentFeeInfoEx: agentid = " + agentid + ", pagentid = " + agentFeeP + ", rateType = " + rateType);
		
		if(pagentid.equals(agentid)){
			return true;
		}

		AgentFeeInfo agentFeeInfo = new AgentFeeInfo();
		AgentFeeInfo agentFeeInfo_p = new AgentFeeInfo();
		AgentFeeInfo entity = new AgentFeeInfo();
		entity.setRateCode("00");
		entity.setRateType(rateType);
		
		entity.setRateId(agentid);
		agentFeeInfo = agentFeeDao.selectGrade(entity);
		
		String rateTCas = agentFeeInfo.getRateTCas();//T0提现费率
		String rateLivelihood = agentFeeInfo.getRateLivelihood();//民生类
		String rateGeneral = agentFeeInfo.getRateGeneral();//一般类
		String rateEntertain = agentFeeInfo.getRateEntertain();//餐娱类
		String rateGeneralTop = agentFeeInfo.getRateGeneralTop();//批发类
		String rateEntertainTop = agentFeeInfo.getRateEntertainTop();//房产类
		
		if(rateTCas!= null && !rateTCas.equals("")) {
			agentFee = Double.parseDouble(rateTCas) + agentFee;
		}
		if(rateLivelihood!= null && !rateLivelihood.equals("")) {
			agentFee = Double.parseDouble(rateLivelihood) + agentFee;
		}
		if(rateGeneral!= null && !rateGeneral.equals("")) {
			agentFee = Double.parseDouble(rateGeneral) + agentFee;
		}
		if(rateEntertain!= null && !rateEntertain.equals("")) {
			agentFee = Double.parseDouble(rateEntertain) + agentFee;
		}
		if(rateGeneralTop!= null && !rateGeneralTop.equals("")) {
			agentFee = Double.parseDouble(rateGeneralTop) + agentFee;
		}
		if(rateEntertainTop!= null && !rateEntertainTop.equals("")) {
			agentFee = Double.parseDouble(rateEntertainTop) + agentFee;
		}
		
		log.debug("agentFee = " + agentFee);
		entity.setRateId(pagentid);
		agentFeeInfo_p = agentFeeDao.selectGrade(entity);
 		
		String rateTCas_p = agentFeeInfo_p.getRateTCas();
		String rateLivelihood_p = agentFeeInfo_p.getRateLivelihood();
		String rateGeneral_p  = agentFeeInfo_p.getRateGeneral();
		String rateEntertain_p  = agentFeeInfo_p.getRateEntertain();
		String rateGeneralTop_p  = agentFeeInfo_p.getRateGeneralTop();
		String rateEntertainTop_p  = agentFeeInfo_p.getRateEntertainTop();
		
		if(rateTCas_p!= null && !rateTCas_p.equals("")){
			agentFeeP = Double.parseDouble(rateTCas_p) + agentFeeP;
		}
		if(rateLivelihood_p!= null && !rateLivelihood_p.equals("")) {
			agentFeeP = Double.parseDouble(rateLivelihood_p) + agentFeeP;
		}
		if(rateGeneral_p!= null && !rateGeneral_p.equals("")) {
			agentFeeP = Double.parseDouble(rateGeneral_p) + agentFeeP;
		}
		if(rateEntertain_p!= null && !rateEntertain_p.equals("")) {
			agentFeeP = Double.parseDouble(rateEntertain_p) + agentFeeP;
		}
		if(rateGeneralTop_p!= null && !rateGeneralTop_p.equals("")) {
			agentFeeP = Double.parseDouble(rateGeneralTop_p) + agentFeeP;
		}
		if(rateEntertainTop_p!= null && !rateEntertainTop_p.equals("")) {
			agentFeeP = Double.parseDouble(rateEntertainTop_p) + agentFeeP;
		}
		
		log.debug("agentFeeP = " + agentFeeP);
		if(agentFee < agentFeeP){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int checkAgentFee(AgentInf agentInf) throws Exception {
		
		
		agentInf.setRateCode("00");
		int agentFee = agentDao.checkAgentInfFee(agentInf);
		agentInf.setRateType("03");
		int quickFee = agentDao.checkAgentInfFeeEx(agentInf);
		agentInf.setRateType("04");
		int qrFee = agentDao.checkAgentInfFeeEx(agentInf);
		
		if (agentFee ==0 && quickFee ==0 && qrFee == 0 ) {
			return 1;
		}else{
			return 0;
		}
		
		//return agentDao.checkAgentInfFee(agentInf);*/
	}

	


	/**
	 * 查找代理商下属所有代理商
	 * */
	@Override
	public List<AgentInf> queryNextAgent(Map<String, Object> map){
		try {
			List<AgentInf> agentList = agentDao.getAgentList(map);
			return agentList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 查找代理商下商户
	 * */
	@Override
	public List<AgentInf> queryCust(Map<String, Object> map){
		try {
			List<AgentInf> custList = agentDao.queryCust(map);
			return custList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改代理商费率
	 * 
	 * @param 代理商编号
	 * */
	@Override
	public int agentRateAdjust(Map<String, Object> map, String rateAjst) throws Exception{
		log.info("进入指定代理商费率修改...");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		
		//代理商费率修改
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object object = entry.getValue();
			String key = entry.getKey();
			if(object instanceof String){
				paramMap.put("fathAgentId", object);
				agentDao.agentRateAdjust(paramMap);
				agentDao._agentRateAdjust(paramMap);
			}else if (object instanceof List) {
				if(!key.startsWith("_")){
				List<AgentInf> list = (List<AgentInf>) entry.getValue();
					
				for (int i = 0; i < list.size(); i++) {
					paramMap.put("fathAgentId", list.get(i).getAgentId());
					agentDao.agentRateAdjust(paramMap);
					agentDao._agentRateAdjust(paramMap);
					}
				}
			}
		}
		
		return 0;
	}
	
	
	@Override
	public int termRateAdjust(Map<String, Object> map, String rateAjst) throws Exception {
		log.info("进入指定代理商所属终端费率修改...");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		
		//终端费率修改
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object object = entry.getValue();
			
			String agent = entry.getKey();
		    if (entry.getValue() instanceof List) {
		    	if(!agent.startsWith("_")){
		    	paramMap.put("fathAgentId", agent);
		    	agentDao.termRateAdjust(paramMap);
		    	}else if(agent.startsWith("_")){
		    		List<AgentInf> list = (List<AgentInf>) object;
		    		for(int i=0;i<list.size();i++){
		    		paramMap.put("fathAgentId", list.get(i).getAgentId());
			    	agentDao.termRateAdjust(paramMap);
		    		}
		    	}
			}else{
				
			}
		}
		
		return 0;
	}
	@Override
	public int custRateAdjust(Map<String, Object> map, String rateAjst) throws Exception {
		log.info("进入指定代理商所属商户费率修改...");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		
		//商户费率修改
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object value = entry.getValue();
			String key = entry.getKey();
			
		    if (entry.getValue() instanceof List) {
		    	
		    	if (!key.startsWith("_")) {					
		    		paramMap.put("fathAgentId", key);
		    		List<AgentInf> list = agentDao.queryCust(paramMap);
			    	for (int i = 0; i < list.size(); i++) {
						paramMap.put("custId", list.get(i).getCustId());
						agentDao.custRateAdjust(paramMap);
						agentDao._custRateAdjust(paramMap);
			    	}
				}else{
					List<AgentInf> list = (List<AgentInf>) value;
					for (int i = 0; i < list.size(); i++) {
						paramMap.put("fathAgentId", list.get(i).getAgentId());
						List<AgentInf> list2 = agentDao.queryCust(paramMap);
						
				    	for (int j = 0; j < list.size(); j++) {
							paramMap.put("custId", list2.get(j).getCustId());
							agentDao.custRateAdjust(paramMap);
							agentDao._custRateAdjust(paramMap);
				    	}
					}
				}
			}else{
				
			}
		}
		
		return 0;
	}
	@Override
	public int agentRateAdjustAll(Integer num, String rateAjst) throws Exception {
		log.info("根据等级修改代理商费率...");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		for(int i=0;i<num;i++){
			paramMap.put("agentDgr",i+1);
			agentDao.agentRateAdjustAll(paramMap);
			agentDao._agentRateAdjustAll(paramMap);
		}
		return 0;
	}
	@Override
	public int custRateAdjustAll(Integer num, String rateAjst) throws Exception {
		log.info("根据代理商等级修改所属商户费率...");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		for(int i=0;i<num-1;i++){
			paramMap.put("agentDgr", i+1);
			agentDao.custRateAdjustAll(paramMap);
			agentDao._custRateAdjustAll(paramMap);
		}
		return 0;
	}
	@Override
	public int termRateAdjustAll(Integer num, String rateAjst) throws Exception {
		log.info("根据代理商等级修改所属终端费率...");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rateAjst", rateAjst);
		for(int i=0;i<num-1;i++){
			paramMap.put("agentDgr", i+1);
			agentDao.termRateAdjustAll(paramMap);
		}
		return 0;
	}
	
	
	@Override
	public Map<String, Object> getAgentShareFee(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		ParamValidate.doing(param, "agentId","rateType", "rateCode");
		
		Map<String, Object> map = agentDao.queryAgentShareFee(param);
		return map;
	}
	
	
	@Override
	public int setAgentShareFee(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		ParamValidate.doing(param, "agentId","rateType","rateCode");
		
		log.debug("开始验证费率");
		Map<String, Object> map = agentDao.queryAgentShareFee(param);
		//查看代理商的费率
		
		if (map==null || map.size()==0) {
			throw new Exception("代理商费率不存在");
		}
		
		if (map.get("shareRate") == null || map.get("shareRate").equals("") ) {
			throw new Exception("代理商费率不能为空");
		}
		double agentFee = 0;
		double newShareFee = 0;
		try {
			agentFee = Double.parseDouble(map.get("shareRate").toString());
			newShareFee = Double.parseDouble(param.get("rateGeneralTop").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("费率转换出错");
		}
		
		//验证费率
		if (agentFee > newShareFee) {
			throw new Exception("分享费率不能小于代理商费率");
		}
		
		log.debug("费率验证通过，开始修改分享费率");
		agentDao.updateAgentShareFee(param);
		
		return 0;
	}
}
