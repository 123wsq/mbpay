package com.tangdi.production.mpbase.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.service.FileUploadService;
import com.tangdi.production.mpbase.util.UID;
import com.tangdi.production.tdbase.domain.ReturnMsg;

/**
 * 文件上传控制器<br/>
 * 
 * @author zhengqiang
 *
 */
@Controller
@RequestMapping("/mpbase/")
public class FileUploadController {
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);
	
	@Autowired
	private FileUploadService fileUploadService;
	/**
	 * 文件上传页面
	 * @return
	 */
	@RequestMapping(value = "fileUploadManage")
	public String fileUpoadView(){
		return "mpbase/upload/fileUploadManage";
	} 
	
	@RequestMapping(value = "fileUploadManage/upload")
	public @ResponseBody ReturnMsg uploadFile(
			@RequestParam(value="id-input-file-3",required=false) MultipartFile file,HttpSession session){
		
		log.debug("文件信息:{}",file);
		HashMap<String,Object> map = null;
		ReturnMsg msg = new ReturnMsg();
		try {
			map = fileUploadService.upload(file,UID.get(session));
			msg.setMsg("200", "文件上传成功！");
			log.debug("文件信息:{}",map);
			msg.putAll(map);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			msg.setMsg("201", "文件上传失败！");
			
		}		
		
		return msg;
	}
	/**
	 * 图片浏览
	 * @return
	 */
	@RequestMapping(value = "pic/view")
	public String picView(){
		return "mpbase/upload/picview";
	} 
	
	/**
	 * 图片浏览
	 * @return
	 */
	@RequestMapping(value = "pic/view2")
	public void  picView2( @RequestParam(value="picid",required=false) String fid,
			HttpServletRequest request,
            HttpServletResponse response){
		
		
		String path = null;
		File file = null;
		try {
			path = fileUploadService.getFilePath(fid, MsgCT.PIC_TYPE_OTHER);
			file = new File(path);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}


        //判断文件是否存在如果不存在就返回默认图标
        if(file == null || !(file.exists() && file.canRead())) {
            file = new File(request.getSession().getServletContext().getRealPath("/")
                    + "resource/icons/auth/root.png");
        }

        FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			byte[] data = new byte[(int)file.length()];
	        int length = inputStream.read(data);
	        log.debug("文件流大小:[{}B]",length);
	        inputStream.close();

	        response.setContentType("image/jpg");

	        OutputStream stream = response.getOutputStream();
	        stream.write(data);
	        stream.flush();
	        stream.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
       

		
	} 


}
