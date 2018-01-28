package com.tangdi.production.mpsms.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tangdi.production.mpsms.bean.MessageInf;
import com.tangdi.production.mpsms.service.MessageService;
import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.test.BeanDebugger;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpsms/")
public class MessageController {
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	/**
	 * 文件下载管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "massage/messageListView")
	public String messageManageView() {
		return "/mpsms/messageManage";
	}

	/**
	 * 查询列表
	 * 
	 * @param messageInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "massage/queryMessageList")
	@ResponseBody
	public ReturnMsg queryTermCom(@ModelAttribute("messageInf") MessageInf messageInf, HttpServletRequest request, HttpSession session) throws Exception {
		Integer total = messageService.getCount(messageInf);
		List<MessageInf> list = messageService.getListPage(messageInf);
		log.debug("总记录数:{},数据列表：{}", total, list.toString());
		return new ReturnMsg(total, list);

	}

	@RequestMapping(value = "message/manageTypeView")
	public String addView() {
		getResource();
		return "mpsms/manageTypeView";
	}

	@RequestMapping({ "message/queryMessageComById" })
	@ResponseBody
	public ReturnMsg queryMessageComById(HttpServletRequest request) throws Exception {
		String smsId = ServletRequestUtils.getStringParameter(request, "smsId", "");
		log.debug("MessageController line 75:   smsId:" + smsId);

		ReturnMsg rm = new ReturnMsg();
		MessageInf message = new MessageInf();
		message.setSmsId(smsId);
		message = messageService.getEntity(message);
		rm.setObj(message);
		return rm;
	}

	@RequestMapping(value = "message/deleteMessageCom")
	@ResponseBody
	public ReturnMsg remove(HttpServletRequest request, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		String[] smsIds = ServletRequestUtils.getStringParameter(request, "smsIds", "").split(",");
		MessageInf messageInf = new MessageInf();
		for (String smsId : smsIds) {
			messageInf.setSmsId(smsId);
			try {
				messageService.removeEntity(messageInf);
			} catch (Exception e) {
				log.error("删除失败：" + e.getMessage());
				return rm.setFail("删除失败:" + e.getMessage());
			}
		}
		log.info("删除成功");
		return rm.setSuccess("删除成功！");
	}

	@RequestMapping(value = "message/editMessage")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("MessageInf") MessageInf messageInf, HttpSession session) {
		ReturnMsg rm = new ReturnMsg();
		UAI uai = (UAI) session.getAttribute("UID");
		BeanDebugger.dump(messageInf);
		try {
			if (messageInf.getSmsId() == null || messageInf.getSmsId().length() <= 0) {
				messageInf.setCreateUserId(uai.getUserId());
				messageInf.setSmsDate(TdExpBasicFunctions.GETDATETIME());
				messageInf.setSmsStatus("00");
				messageService.addEntity(messageInf);
			} else {
				messageInf.setEditUserId(uai.getUserId());
				messageInf.setEditDate(TdExpBasicFunctions.GETDATETIME());
				messageService.modifyEntity(messageInf);
			}
		} catch (Exception e) {
			log.error("保存失败：" + e.getMessage());
			return rm.setFail("保存失败:" + e.getMessage());
		}
		log.info("保存成功");
		return rm.setSuccess("保存成功！");
	}

	private void getResource() {
		String pathString = this.getClass().getResource("/").getPath() + "mpsms/conf-mpsms/mpsms-smsGroup.dat";
		try {
			LineIterator it = FileUtils.lineIterator(new File(pathString), "UTF-8");
			while (it.hasNext()) {
				String line = it.nextLine();
				System.out.println(line);
				
				FileUtils.write(new File("D:/a/b/cxyapi.txt"),line);    
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		private void setResource() {
			String pathString = this.getClass().getResource("/").getPath() + "mpsms/conf-mpsms/mpsms-smsGroup.dat";
			try {
				LineIterator it = FileUtils.lineIterator(new File(pathString), "UTF-8");
				while (it.hasNext()) {
					String line = it.nextLine();
					System.out.println(line);
					
					FileUtils.write(new File("D:/a/b/cxyapi.txt"),line);    
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


	}

}