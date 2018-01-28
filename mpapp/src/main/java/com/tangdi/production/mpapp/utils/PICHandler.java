package com.tangdi.production.mpapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片转换
 * @author zhengqiang
 *
 */
@SuppressWarnings("restriction")
public class PICHandler {

	// 图片转化成base64字符串
	public static String getImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "F:/logo.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean generateImage(String imgstr,String outputPath) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgstr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgstr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String path=outputPath.substring(0, outputPath.lastIndexOf("/"));
			File file=new File(path); 
			if (!file.exists()&& !file .isDirectory()) {
				file.mkdirs();
			}
			String imgFilePath = outputPath;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
