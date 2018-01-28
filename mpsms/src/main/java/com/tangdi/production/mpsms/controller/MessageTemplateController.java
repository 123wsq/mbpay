package com.tangdi.production.mpsms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpsms.service.MessageTemplateService;
import com.tangdi.production.mpsms.bean.MessageTemplateInf;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 短信模版控制器
 * @author zhengqiang
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpsms/")
public class MessageTemplateController {
	private static final Logger log = LoggerFactory
			.getLogger(MessageTemplateController.class);

	@Autowired
	private MessageTemplateService messageTemplateService;
	

	/**
	 * 短信模版跳转页面
	 * @return
	 */
	@RequestMapping(value = "messageTemplateManage")
	public String messageTemplateManageView(){
		return "mpsms/messageTemplate/messageTemplateManage";
	} 
	
	/**
	 * 短信模版信息列表
	 * @param messageTemplateInf
	 * @return
	 */
	@RequestMapping(value = "messageTemplateManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("messageTemplateInf") MessageTemplateInf messageTemplateInf){
		int total =0;
		List<MessageTemplateInf> list = null;
		try {
			log.debug("查询参数:{}",messageTemplateInf.debug());
			total = messageTemplateService.getCount(messageTemplateInf);
			list  = messageTemplateService.getListPage(messageTemplateInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	/**
	 * 短信模版新增跳转页面
	 * @return
	 */
	@RequestMapping(value = "messageTemplateManage/addView")
	public String addView() {
		return "mpsms/messageTemplate/messageTemplateAdd";
	}
    /**
     * 短信模版新增控制
     * @param messageTemplateInf
     * @return
     */
	@RequestMapping(value = "messageTemplateAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("MessageTemplateInf") MessageTemplateInf messageTemplateInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			if(messageTemplateService.getEntity(messageTemplateInf) == null){
				rt = messageTemplateService.addEntity(messageTemplateInf);
				if (rt > 0){
					rm.setMsg("200", "短信模版添加成功.");
				}else{
					rm.setMsg("201", "短信模版添加失败.");
				}
			}else{
				rm.setMsg("203", "短信模版类型已存在，请重新选择。");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "短信模版添加异常！");
		}
		return rm;
	}
	/**
	 * 短信模板删除控制
	 * @param messageTemplateInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "messageTemplateManage/delete")
	@ResponseBody
	public ReturnMsg remove(@ModelAttribute("MessageTemplateInf") MessageTemplateInf messageTemplateInf) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = messageTemplateService.removeEntity(messageTemplateInf);
			if (rt > 0){
				msg.setMsg("200", "短信模版删除成功.");
			}else{
				msg.setMsg("201", "短信模版删除失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "短信模版删除异常.");
		}
		return msg;
	}
	
	/**
	 * 短信模版修改跳转页面
	 * @return
	 */
	@RequestMapping(value = "messageTemplateManage/editeView")
	public String editView() {
		return "mpsms/messageTemplate/messageTemplateEdit";
	}
	/**
	 * 短信模版查询单条信息
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "messageTemplateManage/queryById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("MessageTemplateInf") MessageTemplateInf messageTemplateInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		MessageTemplateInf entity = messageTemplateService.getEntity(messageTemplateInf);
		rm.setObj(entity);
		return rm;
	}
    /**
     * 短信模版修改控制器
     * @param messageTemplateInf
     * @return
     */
	@RequestMapping(value = "messageTemplateEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("MessageTemplateInf") MessageTemplateInf messageTemplateInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		String messageContent=messageTemplateInf.getMessageContent();
		if(messageContent.equals("")||messageContent.equals("null")){
			msg.setMsg("201", "短信模板内容不能为空！");
			return msg;
		}
		try {
			rt = messageTemplateService.modifyEntity(messageTemplateInf);
			if (rt > 0){
				msg.setMsg("200", "短信模版更新成功.");
			}else{
				msg.setMsg("201", "短信模版更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "短信模版更新失败");
		}
		return msg;
	}

}
