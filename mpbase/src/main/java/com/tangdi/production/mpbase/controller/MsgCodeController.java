package com.tangdi.production.mpbase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.bean.MsgCodeInf;
import com.tangdi.production.mpbase.service.MsgCodeService;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 错误消息维护控制器
 * @author hujinsong
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpbase/")
public class MsgCodeController {
	private static final Logger log = LoggerFactory
			.getLogger(MsgCodeController.class);
	
	@Autowired
	private MsgCodeService msgCodeService;
	

	/**
	 * 错误消息维护跳转页面
	 * @return String
	 */
	@RequestMapping(value = "msgCodeManage")
	public String msgCodeManageView(){
		return "mpbase/msgCode/msgCodeManage";
	} 
	
	/**
	 * 错误消息维护信息列表
	 * @param msgCodeInf
	 * @return
	 */
	@RequestMapping(value = "msgCodeManage/queryList")
	@ResponseBody
	public ReturnMsg queryList(@ModelAttribute("msgCodeInf") MsgCodeInf msgCodeInf){
		int total =0;
		List<MsgCodeInf> list = null;
		try {
			log.debug("查询参数:{}",msgCodeInf.debug());
			total = msgCodeService.getCount(msgCodeInf);
			list  = msgCodeService.getListPage(msgCodeInf);
			log.debug("总记录数:{},数据列表：{}",total,list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total,list);
	}
	
	/**
	 * 错误消息新增页面跳转
	 * @return
	 */
	@RequestMapping(value = "msgCodeManage/addView")
	public String addView() {
		return "mpbase/msgCode/msgCodeAdd";
	}
	
	 /**
	  * 错误消息新增控制
	  * @param msgCodeInf
	  * @return
	  */
	@RequestMapping(value = "msgCodeAdd/add")
	@ResponseBody
	public ReturnMsg add(@ModelAttribute("MsgCodeInf") MsgCodeInf msgCodeInf) {
		ReturnMsg rm = new ReturnMsg();
		int rt = 0;
		try {
			if(msgCodeService.getEntity(msgCodeInf) == null){
				rt = msgCodeService.addEntity(msgCodeInf);
				if (rt > 0){
					rm.setMsg("200", "错误消息添加成功.");
				}else{
					rm.setMsg("201", "错误消息添加失败.");
				}
			}else{
				rm.setMsg("203", "错误消息编号已存在，请重新填写。");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "错误消息添加异常！");
		}
		return rm;
	}
	
	/**
	 * 查询错误消息信息
	 * @param msgCodeInf
	 * @return
	 * @throws Exception
	 */                    
	@RequestMapping(value = "msgCodeManage/queryMsgCodeById")
	@ResponseBody
	public ReturnMsg query(@ModelAttribute("MsgCodeInf") MsgCodeInf msgCodeInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		MsgCodeInf entity = msgCodeService.getEntity(msgCodeInf);
		rm.setObj(entity);
		return rm;
	}

	
	/**
	 * 错误消息修改跳转页面
	 * @return String
	 */                     
	@RequestMapping(value = "msgCodeManage/editView")
	public String editView() {
		return "mpbase/msgCode/msgCodeEdit";
	}
	

	/**
     * 错误消息修改控制器
     * @param custLevelInf
     * @return
     */
	@RequestMapping(value = "msgCodeEdit/edit")
	@ResponseBody
	public ReturnMsg edit(@ModelAttribute("MsgCodeInf") MsgCodeInf msgCodeInf) {
		ReturnMsg msg = new ReturnMsg();
		int rt = 0;
		try {
			rt = msgCodeService.modifyEntity(msgCodeInf);
			if (rt > 0){
				msg.setMsg("200", "错误消息更新成功.");
			}else{
				msg.setMsg("201", "错误消息更新失败.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "错误消息更新失败");
		}
		return msg;
	}
	
	/**
	 * 错误消息中消息标记切换
	 * @param msgId
	 * @param msgFlag
	 * @return
	 */
	@RequestMapping(value = "msgCodeManage/modifyFlag")
	@ResponseBody
	public ReturnMsg modifyFlag(@RequestParam("msgId") String msgId, @RequestParam("msgFlag") int msgFlag) {
		ReturnMsg msg = new ReturnMsg();
		String strmsg = "";
		try {
			int num = msgCodeService.modifyFlag(msgId, msgFlag);
			if (num > 0) {
					strmsg = "消息标记切换成功！";
				    msg.setMsg("200", strmsg);
			} else {
					strmsg = "消息标记切换失败！";
				    msg.setMsg("201", strmsg);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}
}
