package com.tangdi.production.tdauth.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.tdauth.bean.OrgInf;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.service.OrgService;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.service.DictService;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 机构管理
 * 
 * @author songleiheng
 * 
 */
@Controller
@RequestMapping("/auth/")
public class OrgController {

	private static Logger log = LoggerFactory.getLogger(OrgController.class);

	/**
	 * 机构接口
	 */
	@Autowired
	private OrgService orgService;
	/**
	 * 数据字典
	 */
	@Autowired
	public DictService dictService;

	/**
	 * 机构管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "orgManage")
	public String orgManage(HttpServletRequest req) {
		Map<String, String> tempMap = null;
		OrgInf org = new OrgInf();
		try {
			UAI uai = (UAI) (req.getSession().getAttribute("UID"));
			String orgId = uai.getOrgId();
			org.setOrgId(orgId);
			org = orgService.getEntity(org);
			tempMap = dictService.getOptionTextMap("ORGLEVEL:" + orgLevelAdd(org.getOrgLevel()) + ",ORGSTATUS:0");
			log.info(tempMap.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		req.setAttribute("DICT_ORG_MAP", tempMap);
		req.setAttribute("ORGINF", org);
		return "auth/org/orgmanage";
	}

	private static String orgLevelAdd(String level) {
		Integer le = Integer.parseInt(level);
		le++;
		if (le < 10) {
			return "0" + le;
		} else {
			return le + "";
		}
	}

	/**
	 * 通过角色ID查询角色信息
	 * 
	 * @param rid
	 *            角色ID
	 * @return
	 */
	@RequestMapping(value = "orgmanage/queryOrg")
	public ReturnMsg queryOrg(@RequestParam(value = "orgId") String orgId) {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		OrgInf org = new OrgInf();
		org.setOrgId(orgId);
		OrgInf role = null;
		try {
			role = orgService.getEntity(org);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			rm.setFail(e.getMessage());
			return rm;
		}
		dataMap.put("roleinf", role);
		return rm.setSuccess("机构信息查询成功", dataMap);
	}

	/**
	 * 查询机构列表
	 * 
	 * @param orgInf
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "orgmanage/queryOrgList")
	@ResponseBody
	public Map<String, Object> queryOrgList(@ModelAttribute("orgInf") OrgInf orgInf, HttpServletRequest request, HttpSession session) {
		int pageNo = Integer.parseInt(request.getParameter("page"));
		int numPerPage = Integer.parseInt(request.getParameter("rows"));
		Map<String, Object> hs = new HashMap<String, Object>();
		UAI uai = (UAI) (request.getSession().getAttribute("UID"));
		String currentOrgId = uai.getOrgId();
		try {
			hs = orgService.getOrgForAll(orgInf, pageNo, numPerPage, currentOrgId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return hs;
		}
		return hs;

	}

	/**
	 * 权限auth 部分公用的机构详情展示
	 * 
	 * @param orgInf
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "orgListUtil/showOrg")
	public String showOrg(@ModelAttribute("OrgInf") OrgInf orgInf, HttpServletRequest request) {
		BeanDebugger.dump(orgInf);
		log.info("orgId:=" + orgInf.getOrgId());
		try {
			orgInf = orgService.getEntity(orgInf);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		request.setAttribute("orgInf", orgInf);
		return "auth/util/showOrginf";
	}

	/**
	 * 添加机构信息
	 * 
	 * @param orgInf
	 *            机构
	 * @return
	 */
	@RequestMapping(value = "orgmanage/addOrg")
	@ResponseBody
	public ReturnMsg addOrg(@ModelAttribute("orgInf") OrgInf orgInf, @RequestParam("file") MultipartFile file) {
		ReturnMsg rm = new ReturnMsg();
		if (file.isEmpty()) {
			System.out.println("文件未上传");
		} else {
			try {
				String fileName = TdExpBasicFunctions.GETDATETIME() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				orgInf.setLogo(fileName);
				String realpath = "/orgLogo/";
				File file2 = new File(realpath);
				if (!file2.exists())
					file2.mkdirs();
				String filePath = realpath + fileName;
				file.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int result = 0;
		try {
			result = orgService.addEntity(orgInf);
		} catch (Exception e) {
			rm.setFail(e.getMessage());
			return rm;
		}
		rm.setRspmsg("成功添加" + result + "个机构信息");
		return rm;
	}

	/**
	 * 跳转到添加机构页面
	 * 
	 * @param orgInf
	 * @return
	 */
	@RequestMapping(value = "orgmanage/addOrgView")
	public String addOrgView(@ModelAttribute("orgInf") OrgInf orgInf) {
		return "auth/org/orgAdd";
	}

	/**
	 * 添加机构信息
	 * 
	 * @param orgInf
	 *            机构
	 * @return
	 */
	@RequestMapping(value = "orgmanage/updateOrgView")
	public String updateOrgView(@ModelAttribute("orginf") OrgInf orgInf, HttpServletRequest req) {
		try {
			orgInf = orgService.getEntity(orgInf);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		BeanDebugger.dump(orgInf);
		req.setAttribute("orgInf", orgInf);
		return "auth/org/orgUpdate";
	}

	/**
	 * 更新机构信息
	 * 
	 * @param orgInf
	 *            机构
	 * @return
	 */
	@RequestMapping(value = "orgmanage/modifyOrg")
	@ResponseBody
	public ReturnMsg modifyOrg(@ModelAttribute("orgInf") OrgInf orgInf, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			System.out.println("文件未上传");
		} else {
			try {
				OrgInf oldOrgInf = orgService.getEntity(orgInf);
				File f = new File("/orgLogo/" + oldOrgInf.getLogo());
				if (f.exists())
					f.delete();
				String fileName = TdExpBasicFunctions.GETDATETIME() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				orgInf.setLogo(fileName);
				String realpath = "/orgLogo/";
				File file2 = new File(realpath);
				if (!file2.exists())
					file2.mkdirs();
				String filePath = realpath + fileName;
				file.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {

			}
		}

		ReturnMsg rm = new ReturnMsg();
		int result = 0;
		try {
			result = orgService.modifyEntity(orgInf);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			rm.setFail(e.getMessage());
			return rm;
		}
		rm.setRspmsg("成功修改" + result + "个机构信息");
		return rm;
	}

	/**
	 * 批量禁用角色
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "orgmanage/disableOrg")
	@ResponseBody
	public ReturnMsg disableOrg(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {

		ReturnMsg rm = new ReturnMsg();

		String orgIds = req.getParameter("orgIds") == null ? null : req.getParameter("orgIds");

		// state 1: 禁用 0启用
		int state = 1;

		String[] str = orgIds.split(",");

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				list.add(str[i]);
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("orgStatus", state);

		map.put("list", list);

		int result = 0;
		try {
			result = orgService.modifyList(map);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			rm.setFail(e.getMessage());
			return rm;
		}

		rm.setRspcod("200");

		rm.setRspmsg("禁用" + result + "条记录成功！");

		return rm;
	}

	/**
	 * 批量启动角色
	 * 
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "orgmanage/ableOrg")
	@ResponseBody
	public ReturnMsg ableOrg(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {

		ReturnMsg rm = new ReturnMsg();

		String orgIds = req.getParameter("orgIds") == null ? null : req.getParameter("orgIds");

		// state 1: 禁用 0启用
		int state = 0;

		String[] str = orgIds.split(",");

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i] != "") {
				list.add(str[i]);
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("orgStatus", state);

		map.put("list", list);

		int result = 0;
		try {
			result = orgService.modifyList(map);
		} catch (Exception e) {
			// TODO: handle exception
			rm.setFail(e.getMessage());
			return rm;
		}
		rm.setRspcod("200");

		rm.setRspmsg("启用" + result + "条记录成功！");

		return rm;
	}

	/**
	 * 机构查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryOrgView")
	public String queryOrgView(HttpServletRequest request) {
		String returnId = (String) (request.getParameter("returnId") == null ? "" : request.getParameter("returnId"));
		String returnName = (String) (request.getParameter("returnName") == null ? "" : request.getParameter("returnName"));
		log.info("returnId:" + returnId);
		log.info("returnName:" + returnName);
		String orgId = (String) (request.getParameter("orgId") == null ? "" : request.getParameter("orgId"));

		request.setAttribute("returnId", returnId);
		request.setAttribute("returnName", returnName);
		request.setAttribute("orgId", orgId);
		return "auth/role/orgListUtil";
	}

	@RequestMapping(value = "queryOrgViewForOrg")
	public String queryOrgViewForOrg(HttpServletRequest request) {
		String returnId = (String) (request.getParameter("returnId") == null ? "" : request.getParameter("returnId"));
		String returnName = (String) (request.getParameter("returnName") == null ? "" : request.getParameter("returnName"));
		String orgLevel = (String) (request.getParameter("orgLevel") == null ? "" : request.getParameter("orgLevel"));
		log.debug("returnId:" + returnId);
		log.debug("returnName:" + returnName);
		log.debug("orgLevel:" + orgLevel);

		request.setAttribute("returnId", returnId);
		request.setAttribute("returnName", returnName);
		request.setAttribute("orgLevel", orgLevel);
		return "auth/org/orgListUtil";
	}

	@RequestMapping(value = "orgListUtil/queryOrgForDlg")
	public HashMap<String, Object> queryOrgDlg(@ModelAttribute("OrgInf") OrgInf orgInf, HttpServletRequest request, HttpSession session) {
		UAI uai = (UAI) session.getAttribute("UID");
		String operOrgId = uai.getOrgId();
		int pageNo = Integer.parseInt(request.getParameter("page"));

		int numPerPage = Integer.parseInt(request.getParameter("rows"));
		HashMap<String, Object> hsmap = null;
		try {
			hsmap = orgService.getOrgList(orgInf, operOrgId, pageNo, numPerPage);
			// hsmap = orgService.getListPage(orgInf, pageNo, numPerPage);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return hsmap;
	}

	/**
	 * 机构查询 for 添加修改机构时的父机构信息
	 * 
	 * @param orgInf
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "orgListUtil/queryOrgForDealOrg")
	public HashMap<String, Object> queryOrgForDealOrg(@ModelAttribute("OrgInf") OrgInf orgInf, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int pageNo = Integer.parseInt(request.getParameter("page"));
		int numPerPage = Integer.parseInt(request.getParameter("rows"));
		UAI uai = (UAI) (request.getSession().getAttribute("UID"));
		try {
			resultMap = orgService.getOrgList(orgInf, uai.getOrgId(), pageNo, numPerPage);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "showLogo")
	public String showLogo(HttpServletRequest request) {
		String imageUrl = (String) (request.getParameter("imageUrl") == null ? "" : request.getParameter("imageUrl"));
		request.setAttribute("imageUrl", "/orgLogo/" + imageUrl);
		return "auth/org/showLogo";
	}

	// @RequestMapping("org/logoUpload")
	// public void logoUpLoad(@RequestParam("file") MultipartFile file,
	// HttpServletRequest request) {
	// if (file.isEmpty()) {
	// System.out.println("文件未上传");
	// } else {
	// try {
	// String filePath =
	// request.getSession().getServletContext().getRealPath("/") + "upload/" +
	// file.getOriginalFilename();
	// file.transferTo(new File(filePath));
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	// /** 上传文件页面 */
	// @RequestMapping("org/logoUploadView")
	// public String LogoUploadView(String orgId, HttpServletRequest request) {
	// request.setAttribute("orgInf", orgId);
	// return "auth/org/logoUploadView";
	// }
}
