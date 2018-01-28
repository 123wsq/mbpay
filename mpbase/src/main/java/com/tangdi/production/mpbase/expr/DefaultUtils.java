package com.tangdi.production.mpbase.expr;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.tdcomm.util.Context;

/**
 * 表达式扩展
 * @author xiejinzhong
 *
 */
public class DefaultUtils {
	private static Logger log = LoggerFactory.getLogger(DefaultUtils.class);
	/**
	 * 解包时如果是直接返回asc字符，调用此方法可以避免产生乱码(用item节点会出现乱码)
	 * @param len 要读取的数据长度
	 * @return 读取到的数据内容
	 */
	public static String STR2HEXCHANEW(int len) {
		InputStream in = (InputStream) Context.peekInstance(InputStream.class);
		StringBuffer buf = new StringBuffer();
		byte[] data = new byte[len];
		try {
			in.read(data);
		} catch (IOException e) {
			log.info("接收报文异常");
			return "0000";
		}
		for (int i = 0; i < data.length; ++i) {
			buf.append(String.format("%02X",
					new Object[] { Byte.valueOf(data[i]) }));
		}
		return buf.toString();
	} 
	/**
	 * 获取固定长度的HEX值 
	 * @param len 16进制的长度
	 * @return
	 * @throws IOException
	 */
	public static String GETLENHEXSTR(String len) throws IOException  {
		len = len.trim();
        InputStream in = Context.peekInstance(InputStream.class);
        StringBuffer buf = new StringBuffer();
        int length = Integer.valueOf(len,16);
        byte[] data = new byte[length];
        in.read(data);
        for (int i = 0; i < data.length; i++) {
        	buf.append(String.format("%02X", data[i]));
        }
        return buf.toString();
	}

}
