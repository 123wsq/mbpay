package com.tangdi.production.mpcoop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.mpcoop.bean.CooporgRouteInf;
import com.tangdi.production.mpcoop.service.CooporgRouteService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * 路由信息维护控制器
 * 
 * @author shanbeiyi
 * 
 */
@Controller
@RequestMapping("/mpcoop/")
public class CooporgRouteController {

	private static Logger log = LoggerFactory.getLogger(CooporgMerController.class);

	/**
	 * 路由信息维护接口
	 */
	@Autowired
	private CooporgRouteService cooporgRouteService;

	/**
	 * 进入路由信息设置管理界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "coopOrgRouteManage")
	public String orgManage() {
		return "mpcoop/cooporgroute/coopOrgRouteManage";
	}

	/**
	 * 查询路由信息列表
	 * 
	 * @param MPCOOP_COOPORG_ROUTE_INF
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/query")
	@ResponseBody
	public ReturnMsg queryCoopOrgRoute(@ModelAttribute("CooporgRouteInf") CooporgRouteInf cooporgRouteInf) throws Exception {
		Integer total = cooporgRouteService.getCount(cooporgRouteInf);
		List<CooporgRouteInf> cooporgmerList = cooporgRouteService.getListPage(cooporgRouteInf);
		return new ReturnMsg(total, cooporgmerList);
	}

	/**
	 * 添加路由信息
	 * 
	 * @param MPCOOP_COOPORG_ROUTE_INF
	 *            路由信息维护
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/addView")
	public String addCoopMerTerView() {
		return "mpcoop/cooporgroute/coopOrgRouteAdd";
	}

	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/add")
	@ResponseBody
	public ReturnMsg addCoopOrgRoute(@ModelAttribute("CooporgRouteInf") CooporgRouteInf cooporgRouteInf,  HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		int num;
		try {
			UAI uai = UID.getUAI(session);
			String uid = uai.getId() + "";
			cooporgRouteInf.setEditUserId(uid);
			cooporgRouteInf.setEditDate(TdExpBasicFunctions.GETDATETIME());
			/*CooporgRouteInf cri = new CooporgRouteInf();
			cri.setRtrid(cooporgRouteInf.getRtrid());*/
			//int count = cooporgRouteService.getCount(cri);
			int count = cooporgRouteService.getCount(cooporgRouteInf);
			if (count > 0) {
				rm.setMsg("203", "路由编号[" + cooporgRouteInf.getRtrid() + "]已存在！");
				return rm;
			}

			num = cooporgRouteService.addEntity(cooporgRouteInf);
			if (num == 1)
				rm.setMsg("200", "路由信息添加成功！");
			else
				rm.setMsg("201", "路由信息添加失败！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "路由添加异常！");
		}
		return rm;
	}

	/**
	 * 进入合作机构商户终端详细信息页面
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/selectinfView")
	public String selectinfCoopOrgMerView() {
		return "mpcoop/cooporgroute/coopOrgRouteSelectInf";
	}

	/**
	 * 查询单条合作机构商户终端详细信息
	 * 
	 * @param MPCOOP_COPMERTER_INF
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/queryCoopOrgRouteById")
	@ResponseBody
	public ReturnMsg queryCoopOrgRouteById(@RequestParam("rtrid") String rtrid) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		CooporgRouteInf cori = new CooporgRouteInf();
		cori.setRtrid(rtrid);
		cori = cooporgRouteService.getEntity(cori);
		msg.setObj(cori);
		return msg;
	}

	/**
	 * 删除路由信息
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/delete")
	@ResponseBody
	public ReturnMsg removeCoopOrgRoutes(@RequestParam("rtrid") String rtrids) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		try {
			String[] rtridset = rtrids.split(",");

			for (int i = 0; i < rtridset.length; i++) {
				CooporgRouteInf entity = new CooporgRouteInf();
				entity.setRtrid(rtridset[i]);
				cooporgRouteService.removeEntity(entity);
			}
			msg.setMsg("200", "路由信息删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}

		return msg;
	}

	/**
	 * 修改路由信息
	 * 
	 */
	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/editView")
	public String editCoopMerTerView() {
		return "mpcoop/cooporgroute/coopOrgRouteEdit";
	}

	@RequestMapping(value = "cooporgroute/coopOrgRouteManage/edit")
	@ResponseBody
	public ReturnMsg editCoopOrgRoute(@ModelAttribute("CooporgRouteInf") CooporgRouteInf cooporgRouteInf,  HttpSession session) {
		ReturnMsg msg = new ReturnMsg();
		try {
			UAI uai = UID.getUAI(session);
			String uid = uai.getId() + "";
			cooporgRouteInf.setEditUserId(uid);
			cooporgRouteInf.setEditDate(TdExpBasicFunctions.GETDATETIME());
			cooporgRouteService.modifyEntity(cooporgRouteInf);
			msg.setMsg("200", "路由信息更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "更新失败");
		}
		return msg;
	}

}
