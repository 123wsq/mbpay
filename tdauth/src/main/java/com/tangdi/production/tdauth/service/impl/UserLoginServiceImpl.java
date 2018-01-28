package com.tangdi.production.tdauth.service.impl;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.dao.AgentShieldMenuDao;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdauth.bean.OrgInf;
import com.tangdi.production.tdauth.bean.RoleInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdauth.controller.MenuController;
import com.tangdi.production.tdauth.dao.UserDao;
import com.tangdi.production.tdauth.service.OrgService;
import com.tangdi.production.tdauth.service.RoleService;
import com.tangdi.production.tdauth.service.UserLoginService;
import com.tangdi.production.tdauth.service.UserService;
import com.tangdi.production.tdauth.service.exception.CaptchaException;
import com.tangdi.production.tdauth.service.exception.UserLoginException;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ImageUtil;
import com.tangdi.production.tdbase.util.TdExprSecurity;
import com.tangdi.production.termmng.http.client.HttpRequestClient;
import com.tangdi.production.termmng.http.client.HttpResp;

/**
 * 用户登陆接口实现类</br> 用户验证码获取和验证，及用户登陆效验。
 * 
 * @author songleiheng
 * 
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
	private static Logger log = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private UserDao userDao;
	@Value("#{tdauthConfig}")
	private Properties prop;
	private HttpRequestClient httpclient = new HttpRequestClient();

	@Autowired
	private RoleService roleService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;

	@Autowired
	private AgentShieldMenuDao agentShieldMenuDao;

	// @Autowired
	// private ProducerService producerService;

	public String generateCaptcha() {
		final int CAPTCHA_LENGTH = 4; // 验证码长度
		String captcha = "";
		for (int i = 0; i < CAPTCHA_LENGTH; i++) {
			captcha += (new Random()).nextInt(10);
		}
		return captcha;
	}

	public BufferedImage generateCaptchaImg(String captcha) {
		int width = 60;
		int height = 30;
		return ImageUtil.generateCaptchaImg(captcha, width, height, null, null);
	}

	public UAI logIn(UserInf user, String clientCaptcha, String serverCaptcha) throws CaptchaException, UserLoginException, Exception {
		UserInf userInf = null;
		UAI uai = null;
		String roleId = "", roleName = "";
		// 验证码校验
		if (clientCaptcha == null || "".equals(clientCaptcha)) {
			throw new CaptchaException("验证码不能为空！");
		} else if (!clientCaptcha.equals(serverCaptcha)) { //
			throw new CaptchaException("验证码错误！");
		} else {
			if (user.getUserId().equals(Constant.TOPINNER_USER_ID)) {
				userInf = this.logIn2(user);
			} else {
				userInf = this.logIn(user);
			}
			if (userInf.getUserStatus() == 1) {
				throw new UserLoginException("用户已被禁用，请联系管理员！");
			}
			/*** 2015.9.11 limiao*************如果 是代理商系统 操作员登录 需要判断代理商 登录状态 是否 禁用 *************/
			if (!Constant.SYS_AGENT_ID.equals(userInf.getAgentId())) {
				/********* 查询 代理商 登录状态 **********/
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("agentId", userInf.getAgentId());

				param = agentShieldMenuDao.selectEntity(param);
				if (param == null) {
					throw new UserLoginException("代理商信息不存在，请联系业务员！");
				}
				if (!"0".equals(param.get("ageStatus"))) {
					throw new UserLoginException("代理商已禁用，请联系业务员！");
				}
			}

			List<RoleInf> list = roleService.getRoleInfByUser(userInf.getId());
			for (RoleInf ri : list) {
				roleId = roleId + ri.getRoleId() + "|";
				roleName = roleName + ri.getRoleName() + " ";
			}
			// roleName = roleName.substring(0, roleName.lastIndexOf(","));
			log.info("USERINF={}", userInf);
			uai = new UAI(userInf.getId(), userInf.getUserId(), userInf.getUserName(), userInf.getUserRandom(), userInf.getOrgId(), roleId, roleName, userInf.getOrgName(), prop.getProperty("OPEN_PATH_DOWNLOAD"), userInf.getAgentId());
			uai.setSysId(userInf.getSysId());

			/*
			 * 
			 * 2015.9.11 limiao
			 * 
			 * IsFirstLoginFlag=0 首次登录需要修改密码
			 */
			uai.setIsFirstLoginFlag(Constant.SYS_ISFIRSTLOGINFLAG_1);
			if (Constant.SYS_ISFIRSTLOGINFLAG_0 == userInf.getIsFirstLoginFlag()) {
				uai.setIsFirstLoginFlag(Constant.SYS_ISFIRSTLOGINFLAG_0);
			}
			log.info("UAI={}", uai);
		}
		return uai;
	}

	public UserInf logIn(UserInf user) throws UserLoginException, Exception {
		UserInf userInf = null;
		// 用户名密码校验
		if (user.getUserId() == null || "".equals(user.getUserId())) {
			throw new UserLoginException("用户名不能为空！");
		} else if (user.getUserPwd() == null || "".equals(user.getUserPwd())) {
			throw new UserLoginException("用户密码不能空！");
		} else if (user.getOrgId() == null || "".equals(user.getOrgId())) {
			throw new UserLoginException("机构号不能为空！");
		} else if (user.getSysId() == null || "".equals(user.getSysId())) {
			throw new UserLoginException("系统模块ID不能为空！");
		}
		try {
			userInf = userDao.selectEntity(new UserInf(user.getUserId(), TdExprSecurity.MD5STR(user.getUserPwd()), user.getOrgId(), user.getSysId(), user.getAgentId(), 0));
		} catch (Exception e) {
			throw new UserLoginException("用户登陆异常！", e);
		}
		if (userInf == null) {
			throw new UserLoginException("用户名或密码错误！");
		} else if (userInf.getUserStatus() == 1) {
			throw new UserLoginException("用户已禁用！");
		}
		String random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		int num = modifyRandom(new UserInf(userInf.getId(), random, user.getLastLoginTime(), user.getLastLoginIp(),user.getOperId()));
		if (num < 0) {
			throw new UserLoginException("用户登陆异常！");
		}
		OrgInf org = orgService.getEntity(new OrgInf(userInf.getOrgId()));
		if (org == null || org.getOrgStatus().intValue() == 1) {
			throw new UserLoginException("用户所属机构状态异常！");
		}
		userInf.setOrgName(org.getOrgName());
		// producerService.send();
		return userInf;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserInf logIn2(UserInf user) throws UserLoginException, Exception {
		UserInf userInf = null;
		// 用户名密码校验

		user.setOrgId(UAI.TOP_ORG_ID);
		user.setRoleId(UAI.TOP_ROLE_ID);
		if (user.getUserId() == null || "".equals(user.getUserId())) {
			throw new UserLoginException("用户名不能为空！");
		} else if (user.getUserPwd() == null || "".equals(user.getUserPwd())) {
			throw new UserLoginException("用户密码不能空！");
		} else if (user.getOrgId() == null || "".equals(user.getOrgId())) {
			throw new UserLoginException("机构号不能为空！");
		}
		String urlpah = prop.getProperty("TOP_PLAT_URL");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user.getUserId());
		map.put("user_pwd", TdExprSecurity.MD5STR(user.getUserPwd()));
		map.put("org_id", user.getOrgId());
		String ss = JUtil.toJsonString(map);
		log.info("ss={}", ss);
		String RSP_CODE = null;
		try {
			HttpResp httprsp = httpclient.sendPost(urlpah + "login.tran?REQ_MESSAGE=" + ss);
			ss = httprsp.getContent();
			log.info("获取字符串{}", ss);
			map = JUtil.toMap(ss);
			log.info("map={}", map);
			RSP_CODE = String.valueOf(map.get("RSP_CODE"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new UserLoginException("用户名或密码错误！");
		}
		if (!RSP_CODE.equals("00")) {
			throw new UserLoginException("用户名或密码错误！");
		}
		user.setUserId(UAI.TOP_USER_ID);
		try {
			userInf = userDao.selectEntity(new UserInf(user.getUserId(), TdExprSecurity.MD5STR(user.getUserPwd()), user.getOrgId(), user.getSysId(), user.getAgentId(), 0));
		} catch (Exception e) {
			throw new UserLoginException("用户登陆异常！", e);
		}
		if (userInf == null) {
			throw new UserLoginException("用户名或密码错误！");
		} else if (userInf.getUserStatus() == 1) {
			throw new UserLoginException("用户已禁用！");
		}
		String random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		int num = modifyRandom(new UserInf(userInf.getId(), random, user.getLastLoginTime(), user.getLastLoginIp(),user.getOperId()));
		if (num < 0) {
			throw new UserLoginException("用户登陆异常！");
		}
		OrgInf org = orgService.getEntity(new OrgInf(userInf.getOrgId()));
		if (org == null || org.getOrgStatus().intValue() == 1) {
			throw new UserLoginException("用户所属机构状态异常！");
		}
		userInf.setOrgName(org.getOrgName());
		return userInf;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int modifyRandom(UserInf userInf) throws Exception {
		int rv = 0;
		try {

			UserInf user = userService.getEntity(new UserInf(userInf.getId()));
			log.debug(user.debug());
			userInf.setLnum(user.getLnum() + 1);
			log.debug(userInf.debug());

			rv = userDao.updateEntity(userInf);
		} catch (Exception e) {
			log.error("更新用户随机码异常！", e);
			throw new Exception("更新用户随机码异常！");
		}
		return rv;
	}

	public ReturnMsg logOut(UserInf user) {

		return null;
	}
	
	public UAI PosplogIn(UserInf user) throws CaptchaException, UserLoginException, Exception {
		UAI uai = null;
		String roleId = "", roleName = "";
		UserInf userInf=	userService.selectUserInfByUserId(user);
		
			List<RoleInf> list = roleService.getRoleInfByUserId(user);
			for (RoleInf ri : list) {
				roleId = roleId + ri.getRoleId() + "|";
				roleName = roleName + ri.getRoleName() + " ";
			}
			// roleName = roleName.substring(0, roleName.lastIndexOf(","));
			log.info("USERINF={}", userInf);
			uai = new UAI(userInf.getId(), userInf.getUserId(), userInf.getUserName(), userInf.getUserRandom(), userInf.getOrgId(), roleId, roleName, userInf.getOrgName(), prop.getProperty("OPEN_PATH_DOWNLOAD"), userInf.getAgentId());
			uai.setSysId(userInf.getSysId());
			log.info("UAI={}", uai);

		return uai;
	}
	
}
