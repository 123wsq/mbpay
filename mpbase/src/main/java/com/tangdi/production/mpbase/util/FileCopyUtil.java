package com.tangdi.production.mpbase.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileCopyUtil {
	private static Logger log=LoggerFactory.getLogger("tool");
	
	/**
	 * 复制单个文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception 
	 * @throws IOException
	 */
	   public static void copyFile(String oldPath, String newPath) throws Exception { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
	           File oldfile = new File(oldPath); 
	           if (oldfile.exists()) { //文件存在时 
	        	   log.debug("源文件路径:[{}],目标文件路径:[{}]",oldPath,newPath);
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1024]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread; //字节数 文件大小 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	           } 
	       } 
	       catch (Exception e) { 
	    	   log.error(e.getMessage(), e);
	    	   throw new Exception("复制单个文件操作出错!" + e.getMessage(), e);
	       } 
	   } 

	   /** 
	    * 复制整个文件夹内容 
	    * @param oldPath String 原文件路径 如：c:/fqf 
	    * @param newPath String 复制后路径 如：f:/fqf/ff 
	    * @return boolean 
	    */ 
	  public void copyFolder(String oldPath, String newPath) { 
	      try { 
	          (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
	          File a=new File(oldPath); 
	          String[] file=a.list(); 
	          File temp=null; 
	          for (int i = 0; i < file.length; i++) { 
	              if(oldPath.endsWith(File.separator)){ 
	                  temp=new File(oldPath+file[i]); 
	              } 
	              else{ 
	                  temp=new File(oldPath+File.separator+file[i]); 
	              } 
	              if(temp.isFile()){ 
	                  FileInputStream input = new FileInputStream(temp); 
	                  FileOutputStream output = new FileOutputStream(newPath + "/" + 
	                          (temp.getName()).toString()); 
	                  byte[] b = new byte[1024 * 5]; 
	                  int len; 
	                  while ( (len = input.read(b)) != -1) { 
	                      output.write(b, 0, len); 
	                  } 
	                  output.flush(); 
	                  output.close(); 
	                  input.close(); 
	              } 
	              if(temp.isDirectory()){//如果是子文件夹 
	                  copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
	              } 
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("复制整个文件夹内容操作出错"); 
	          e.printStackTrace(); 
	      } 
	  }
	  
	  /**   
	     *  删除文件   
	     *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt   
	     *  @param  fileContent  String   
	     *  @return  boolean   
	     */    
	   public static void  delFile(String  filePathAndName)  {    
	       try  {    
	           String  filePath  =  filePathAndName;    
	           filePath  =  filePath.toString();    
	           java.io.File  myDelFile  =  new  java.io.File(filePath);    
	           myDelFile.delete();    
	   
	       }    
	       catch  (Exception  e)  {    
	    	   log.debug("删除文件操作出错");
	           e.printStackTrace();    
	   
	       }    
	   
	   }  
	  
	   /**
	    * 修改文件名称
	    * @param file
	    * @param toFile
	    */
	   public static void renameFile(String file, String toFile) {

	        File toBeRenamed = new File(file);
	        //检查要重命名的文件是否存在，是否是文件
	        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
	            log.debug("文件不存在！");
	            return;
	        }

	        File newFile = new File(toFile);

	        //修改文件名
	        if (toBeRenamed.renameTo(newFile)) {
	        	 log.debug("文件名称修改成功！");
	        } else {
	        	 log.debug("文件名称修改 失败！");
	        }

	   }

}
