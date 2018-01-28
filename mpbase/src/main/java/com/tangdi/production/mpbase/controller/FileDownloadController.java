package com.tangdi.production.mpbase.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.FileDownloadInf;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.service.FileDownloadService;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.rpc.AsyService.AsynService;

/**
 * 文件下载控制器层(业务报表数据)
 * @author zhengqiang
 *
 */

@Controller
@RequestMapping("/mpbase/")
public class FileDownloadController {
	private static final Logger log = LoggerFactory
			.getLogger(FileDownloadController.class);

	@Autowired
	private FileDownloadService fileDownloadService;
	@Value("#{mpbaseConfig}")
	private Properties prop;

	// 异步线程处理
	@Autowired
	private AsynService asynService;
	
	/**
	 * 下载当前前10条数据
	 * @return
	 */
	@RequestMapping(value = "fileDownloadManageByCuday/view")
	public String filedownloadByCudayView(){
		return "mpbase/download/fileDownloadByCuday";
	}
	
	@RequestMapping(value = "fileDownloadByCuday/query")
	@ResponseBody
	public ReturnMsg queryByCuday(HttpSession session){
		List<FileDownloadInf> list = null;
		FileDownloadInf file = new FileDownloadInf();
		ReturnMsg rm = new ReturnMsg();
		try {
			UAI uai = (UAI) session.getAttribute("UID");
			file.setUid(uai.getId().toString());
			file.setStartDate(TdExpBasicFunctions.GETDATE()+"000000");
			file.setEndDate(TdExpBasicFunctions.GETDATE()+"235959");
			log.debug("查询参数:{}",file.debug());
			list  = fileDownloadService.getListPage(file);
			log.debug("数据列表：{}",list);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		rm.setObj(list);
		return rm;
	}
	/**
	 * 文件下载管理跳转页面
	 * @return
	 */
	@RequestMapping(value = "fileDownloadManage")
	public String filedownloadView(){
		return "mpbase/download/fileDownloadManage";
	} 
	
	/**
	 *
	 * 文件下载信息列表
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "fileDownloadManage/query")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("fileDownloadInf") FileDownloadInf file,HttpSession session){
		int total =0;
		List<FileDownloadInf> list = null;
		try {
			//设置用户id
			//---------业务特殊处理， 代码生成不要-- 参数,HttpSession session 也不要-------------------------------------
			UAI uai = (UAI) session.getAttribute("UID");
			file.setUid(uai.getId().toString());
			//------------------------------------------------
			log.debug("查询参数:{}",file.debug());
			total = fileDownloadService.getCount(file);
			list  = fileDownloadService.getListPage(file);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	

	/**
	 * 下载
	 * 
	 * @param termMenuGno
	 * @param termInf
	 * @param session
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "fileDownloadManage/downLoad")
	@ResponseBody
	public ReturnMsg downLoad(@RequestParam(value="id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileDownloadInf inf = fileDownloadService.getEntity(new FileDownloadInf(id));
		
		String fileName = inf.getdFileName() + "." + inf.getdType();
		String rootath  = inf.getdPath();
		ReturnMsg msg = new ReturnMsg();
		String downLoadPath = rootath + fileName;
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("500", "找不到文件");
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			return msg;
		}
	}

	/** 业务报表页面 */
	@RequestMapping(value = "fileDownloadManage/openDownload")
	public String openDownload(HttpServletRequest request) {
		request.setAttribute("stime", TdExpBasicFunctions.GETDATETIME());
		return "termmng/report/paymentDig";
	}

	@RequestMapping(value = "fileDownloadManage/hasFile")
	@ResponseBody
	public boolean hasReport(String filePath) {
		boolean has = false;
		String path_1 = prop.get("FILE_DOWNLOAD_PATH_1")+"";
		String path_2 = prop.get("FILE_DOWNLOAD_PATH_2")+"";
		String logicPath = filePath.replace(path_2, "");
		File file = new File(path_1+logicPath);
		if (file.exists()) {
			has = true;
		}
		return has;
	}

	@RequestMapping(value = "fileDownloadManage/report")
	@ResponseBody
	public ReturnMsg report(@RequestBody Map<String,Object> pmap,
			HttpSession session){
		ReturnMsg msg = new ReturnMsg();
		try {
			log.debug("报表请求参数:[{}]",pmap);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("param", pmap);
			map.put("uid", UID.get(session));
			if(pmap.get("server") == null || pmap.get("server").toString().equals("")){
				msg.setMsg("202", "数据提交失败！");
				return msg;
			}
			map.put("service", prop.get("SERVER_REPORT_"+pmap.get("server").toString()));
			// 调用异步线程处理
			asynService.dotran(TranCode.TRAN_REPORT, map);
			 
			msg.setMsg("200", "数据已提交完成,请到下载页面查看文件！");
		} catch (Exception e) {
			 msg.setMsg("201", "数据提交失败！");
			log.error(e.getMessage(),e);
		}
		return msg;
	} 
	
	@RequestMapping(value = "fileDownloadManage/delete")
	@ResponseBody
	public ReturnMsg removeFiles(@RequestParam("id") String ids)throws Exception{
		ReturnMsg msg = new ReturnMsg();
		try {
			fileDownloadService.removeFile(ids);
			msg.setMsg("200", "系统删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
	
}
