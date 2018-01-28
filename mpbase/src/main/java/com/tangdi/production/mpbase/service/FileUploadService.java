package com.tangdi.production.mpbase.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.tangdi.production.mpbase.bean.FileInf;

/**
 * 文件上传
 * @author zhengqiang
 *
 */
public interface FileUploadService {
	
	/**
	 * 文件信息添加到数据库
	 * @param file
	 * @return 文件编号
	 * @throws Exception
	 */
	public int addFile(FileInf file) throws Exception;
	/**
	 * 文件上传到服务器（表单）
	 * @param file
	 * @param uid 用户Id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> upload(MultipartFile file,String uid)  throws Exception;
	/**
	 * 文件上传到服务器（APP）
	 * @param img 图片base64
	 * @param type  1 身份证 2银行卡
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> upload(String img,int type)  throws Exception;
	
	/**
	 * 文件上传到服务器（APP）
	 * @param img 图片 MultipartFile
	 * @param type  1 身份证 2银行卡
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> upload(MultipartFile img,int type)  throws Exception;
	
	/**
	 * 文件上传到服务器（APP）
	 * @param fname 文件名称
	 * @param tname 交易类型名称 00:T+0  01:T+1
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> upload(String fname,String tname)  throws Exception;

	
	/**
	 * 获取文件路径
	 * @param fid  文件id
	 * @param type 文件类型 00 图片 01 文件
	 * @return 文件路径
	 * @throws Exception
	 */
	public String getFilePath(String fid,int type)  throws Exception;
	
	/**
	 * 获取文件磁盘路径
	 * @param fid  文件id
	 * @param type 文件类型 00 图片 01 文件
	 * @return 文件路径
	 * @throws Exception
	 */
	public String getFilePath(String fid)  throws Exception;

	/**
	 * 获取图片下载远程地址
	 * @param dbpath 数据库保存地址
	 * @param type 类型
	 * @return
	 * @throws Exception
	 */
	public String getRemotePath(String dbpath,int type)  throws Exception;

}
