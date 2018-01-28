package com.tangdi.production.tdauth.service;

import java.util.List;

import com.tangdi.production.tdauth.bean.MenuInf;

/**
 * 
 * 屏蔽 代理商系统菜单
 * 
 * @author zhuji
 *
 */
public interface AgentShieldMenuService {
	/**
	 * 代理商 系统 菜单屏蔽
	 * 
	 * @param menuList
	 * @param agentId
	 * @return
	 */
	public List<MenuInf> shieldMenu(List<MenuInf> menuList, String agentId);

}
