package com.tangdi.production.mpbase.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 从数据流中获取数据。
 * @author zhengqiang
 *
 */

public class InputStreamUtil {
	private static Logger log = LoggerFactory.getLogger(InputStreamUtil.class);
	

	/**
	 * 
	 * @param in 输入流对象
	 * @param encoding 字符串编码
	 * @return String
	 * @throws IOException
	 */
	public static String getData(InputStream in, String encoding) throws IOException {
		   int byteSize = 512;
		   int byteBufferSize = 1024;
		try {
			
			byte[] bt = new byte[byteSize];
			ByteBuffer bb = ByteBuffer.allocate(byteBufferSize);
			int k = 0, i = 0;
			int index = 0, tempIndex = 0;
			StringBuffer temp = new StringBuffer();
			
			for (; (k = in.read(bt)) != -1; i++) {
				index = index + k;
				if (index >= (byteBufferSize-tempIndex)) { 
					int pindex = byteBufferSize-(tempIndex+index-k);
					byte[] tempByte = new byte[byteBufferSize];
					bb.put(bt, 0,pindex);
					System.arraycopy(bb.array(), 0, tempByte, 0, byteBufferSize);
					bb = ByteBuffer.allocate(bb.capacity() * 2);
					log.info("缓冲区容量不足,自动扩充大小：{}*2",byteBufferSize);
					bb.put(tempByte, 0, byteBufferSize);
					bb.put(bt, pindex, k-pindex);
					
					tempIndex = byteBufferSize + k-pindex;
					index = 0;
					tempByte = null;
					byteBufferSize = bb.capacity();
					log.info("当前缓冲区容量为：{}b",bb.capacity());
				} else {
					bb.put(bt, 0, k);
					bb.position(tempIndex + index);
				}

				log.info("接收字节数[{}]:{}", i, k);
				bt = new byte[byteSize];
			}
			log.info("共接收字节数：{}b", tempIndex + index);

			temp.append(new String(bb.array(), encoding).trim());
			bb.clear();
			in.close();
			bb = null;
			return temp.toString();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
			
		} finally {
			
		}
	}
}
