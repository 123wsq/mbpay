package com.tangdi.production.mpamng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.tdbase.domain.Attachment;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.service.AttachmentService;

@Controller("mpamng-attachment")
@RequestMapping("/mpamng/attachment/")
public class AttachmentController {
	@Autowired
	private AttachmentService attachmentService;

	/***
	 * 根据附件ID集合，获取这些ID的附件集合
	 * 
	 * @param attachmentIdStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{attachmentIdStr}/list")
	@ResponseBody
	public ReturnMsg queryNoticeList(@PathVariable String attachmentIdStr) throws Exception {
		List<Attachment> attachments = new ArrayList<Attachment>();
		String[] attachmentIds = attachmentIdStr.split("\\|");
		try {
			for (String attachmentId : attachmentIds) {
				Attachment attachmentTmp = new Attachment();
				attachmentTmp.setId(attachmentId);
				Attachment attachment = attachmentService.getEntity(attachmentTmp);
				// attachment为null，跳过
				if (null == attachment) {
					continue;
				}
				attachments.add(attachment);
			}
		} catch (Exception e) {
			attachments = null;
		}
		return new ReturnMsg(attachmentIds.length, attachments);
	}
}
