package com.tangdi.production.mpbatch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ftp {
	private static final Logger logger = LoggerFactory.getLogger(Ftp.class);
	/**
	 * ftpGet FTP协议到远程主机取文件
	 * 
	 * @param ipAdr  远程主机IP地址（必需）
	 * @param ipPort 远程主机PORT端口（可选），缺省值为21
	 * @param usrNam 远程主机用户名（必需）
	 * @param usrPwd 远程主机用户密码（必需）
	 * @param objDir 远程主机文件存放目录（必需）
	 * @param objFil 远程主机文件名（必需）
	 * @param lclDir 本地文件存放目录（必需）
	 * @return 0-成功
	 */
	public static int ftpGet(String ipAdr, int ipPort, String usrNam, String usrPwd, String objDir, String objFil, String lclDir, String lclFil) {
		if (StringUtils.isEmpty(String.valueOf(ipPort))) {
			ipPort = 21;
		}
		if (StringUtils.isEmpty(lclFil)) {
			lclFil = objFil;
		}
		
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;
		try {
			ftpClient.connect(ipAdr, ipPort);
			ftpClient.login(usrNam, usrPwd);
			logger.info("FTPGet {}:{}", ipAdr, ipPort);
			String remoteFileName = objFil;
			//动态创建时间目录
			try {
				new File(lclDir).mkdir();
			} catch (Exception e) {
				logger.info("创建目录失败！！！");
				e.printStackTrace();
			}
			logger.info("本地路径："+lclDir+File.separatorChar+lclFil);
			fos = new FileOutputStream(new File(lclDir+File.separatorChar+lclFil));

			ftpClient.setBufferSize(1024);
			// 设置远程下载目录
			if (!StringUtils.isEmpty(objDir)) {
				ftpClient.changeWorkingDirectory(objDir);
			}
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean b = ftpClient.retrieveFile(remoteFileName, fos);
			if (!b) {
				logger.info("FTP下载文件失败！");
				return -1;
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP客户端出错！");
			return -1;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("关闭FTP连接发生异常！");
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * ftpGet FTP协议上传文件到远程主机
	 * 
	 * @param ipAdr  远程主机IP地址（必需）
	 * @param ipPort 远程主机PORT端口（可选），缺省值为21
	 * @param usrNam 远程主机用户名（必需）
	 * @param usrPwd 远程主机用户密码（必需）
	 * @param objDir 远程主机文件存放目录（必需）
	 * @param objFil 远程主机文件名（必需）
	 * @param lclDir 本地文件存放目录（必需）
	 * @return 0-成功
	 */
	public static int ftpPut(String ipAdr, int ipPort, String usrNam, String usrPwd, String objDir, String objFil, String lclDir, String lclFil) {
		if (StringUtils.isEmpty(String.valueOf(ipPort))) {
			ipPort = 21;
		}
		if (StringUtils.isEmpty(objFil)) {
			objFil = lclFil;
		}
		
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;
		try {
			ftpClient.connect(ipAdr, ipPort);
			ftpClient.login(usrNam, usrPwd);
			logger.info("FTPPut {}:{}", ipAdr, ipPort);
			File inputFile = new File(lclDir+File.separatorChar+lclFil);
			if(!inputFile.exists()){
				logger.info("上传文件不存在！！！");
				return -1;
			}
			
			logger.info("本地路径："+lclDir+File.separatorChar+lclFil);
			fis = new FileInputStream(inputFile);

			ftpClient.setBufferSize(1024);
			// 设置远程上传目录
			if (!StringUtils.isEmpty(objDir)) {
				if(!ftpClient.changeWorkingDirectory(objDir)){
					if(ftpMakeDir(ftpClient, "/", objDir))
						ftpClient.changeWorkingDirectory(objDir);
				}
			}
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean b = ftpClient.storeFile(objFil, fis); 
			if (!b) {
				logger.info("FTP上传文件失败！");
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("FTP客户端出错！");
			return -1;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("关闭FTP连接发生异常！");
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * 创建层级目录
	 * @param ftpClient
	 * @param wkdir 工作目录
	 * @param dir 相对wkdir目录
	 * @return
	 * @throws Exception
	 */
	public static boolean ftpMakeDir(FTPClient ftpClient, String wkdir, String dir) throws Exception{
		if(ftpClient ==null || wkdir == null || dir == null){
			return false;
		}
		while(dir.startsWith("/") && dir.length() > 1){  //去除多个'/'
			dir = dir.substring(1);
		}
		if(dir.equals("/")){
			return true;
		}
		
		String newDir = ""; //当前文件夹
		String subDir = ""; //下级文件夹
		if(dir.indexOf("/") > 0){
			newDir = dir.substring(0, dir.indexOf("/"));
			subDir = dir.substring(dir.indexOf("/"));
		}else{
			newDir = dir;
		}
		newDir = newDir.trim();
		
		if(!"".equals(newDir) && !ftpClient.changeWorkingDirectory(wkdir + "/" + newDir)){
			ftpClient.makeDirectory(wkdir + "/" + newDir);
			if(!ftpClient.changeWorkingDirectory(wkdir + "/" + newDir)){
				logger.error("创建目录{}失败！", wkdir + "/" + newDir);
				throw new Exception("创建目录失败！");
			}
		}
		
		if(!"".equals(subDir)){
			return ftpMakeDir(ftpClient, wkdir + "/" + newDir, subDir); //递归创建下级文件夹
		}else{
			return true;
		}
	}
}
