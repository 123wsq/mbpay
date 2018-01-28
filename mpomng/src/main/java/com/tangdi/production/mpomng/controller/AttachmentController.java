package com.tangdi.production.mpomng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.tdbase.domain.Attachment;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.service.AttachmentService;

@Controller("mpomng-attachment")
@RequestMapping("/mpomng/attachment/")
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
	@RequestMapping("list")
	@ResponseBody
	public ReturnMsg queryNoticeList(@RequestParam(value = "attachmentIds", required = false) String attachmentIdsStr) throws Exception {
		List<Attachment> attachments = new ArrayList<Attachment>();
		String[] attachmentIds = attachmentIdsStr.split("\\|");
		try {
			Attachment attachment = null;
			for (String attachmentId : attachmentIds) {
				attachment = new Attachment();
				attachment.setId(attachmentId);
				attachment = attachmentService.getEntity(attachment);
				// attachment为null，跳过
				if (null == attachment) {
					continue;
				}
				attachment.setFjPath("");
				attachments.add(attachment);
			}
		} catch (Exception e) {
			attachments = null;
		}
		return new ReturnMsg(attachmentIds.length, attachments);
	}
}
