package com.tangdi.production.tdauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpbase.dao.AgentShieldMenuDao;
import com.tangdi.production.tdauth.bean.MenuInf;
import com.tangdi.production.tdauth.service.AgentShieldMenuService;

/**
 * 屏蔽 代理商系统菜单
 * 
 * @author zhuji
 *
 */
@Service
public class AgentShieldMenuServiceImpl implements AgentShieldMenuService {
	private static Logger log = LoggerFactory.getLogger(AgentShieldMenuServiceImpl.class);
	@Autowired
	private AgentShieldMenuDao agentDao;

	/**
	 * 模块配置文件
	 */
	@Value("#{tdauthConfig}")
	private Properties prop;

	@Override
	public List<MenuInf> shieldMenu(List<MenuInf> menuList, String agentId) {
		List<MenuInf> menuListResult = new ArrayList<MenuInf>();
		log.info("111111");

		List menu = new ArrayList();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("agentId", agentId);

			map = agentDao.selectEntity(map);
			log.info("--------:map={}",map);
			if (map != null) {
				String shield_level = (String) prop.get("agent_shield_level");
				if ("3".equals(shield_level)) {
					if (map.get("agentDgr").equals(shield_level)) {
						String[] aStrings = String.valueOf(prop.get("agent_level_menu")).split(",");
						for (String string : aStrings) {
							menu.add(string);
						}
					}

				}
				shield_level = (String) prop.get("agent_shield_profitsharing");
				if ("1".equals(shield_level)) {
					if (NumberUtils.toInt(map.get("agentDgr").toString()) > NumberUtils.toInt(shield_level)) {
						String[] aStrings = String.valueOf(prop.get("agent_profitsharing_menu")).split(",");
						for (String string : aStrings) {
							menu.add(string);
						}
					}
				}
			}
			log.info(map.get("agentDgr") + "级代理商    需要 屏蔽 的 菜单    menuid=" + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (MenuInf menuInf : menuList) {
			if (!menu.contains(menuInf.getMenuId())) {
				menuListResult.add(menuInf);
			}
		}
		return menuListResult;
	}

	public <T> boolean contains(final T[] array, final T v) {
		for (final T e : array)
			if (e == v || v != null && v.equals(e))
				return true;

		return false;
	}
}
