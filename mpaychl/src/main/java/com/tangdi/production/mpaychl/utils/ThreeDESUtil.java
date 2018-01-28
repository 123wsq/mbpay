package com.tangdi.production.mpaychl.utils;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * RSA加解密处理类
 * 
 * @author zlinepay
 * @version 1.0
 * @since 2014年7月29日
 * @copyright zlinepay.com
 */
public class ThreeDESUtil {
	
	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用'DES','DESede','Blowfish'
	
	/**
	 * 3DES加密数据
	 * @param keyStr
	 * @param srcStr
	 * @return
	 */
	public static String encryptData(String keyStr, String srcStr, String charSet) {
		try {
			byte[] key = hex2byte(keyStr,charSet);
			byte[] src = srcStr.getBytes(charSet); // 需要对源数据进行补齐
			int len = src.length;
			int paddingLen = len % 8; // 补齐为8的整数倍
			
			byte[] srcPad = new byte[len + (8 - paddingLen)];
			Arrays.fill(srcPad, (byte)0x0); // 补齐内容为0x0
			
			System.arraycopy(src, 0, srcPad, 0, len); // 将加密数据拷贝到加密数组中
			
			byte[] result = encryptData(key, srcPad);
			return byte2hex(result);
		} catch(Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 3DES解密数据
	 * @param keyStr - hex字符串
	 * @param chiperStr  - hex字符串
	 * @return
	 * @throws Exception 
	 */
	public static String decryptData(String keyStr, String chiperStr, String charSet) throws Exception {
		byte[] key = hex2byte(keyStr, charSet);
		byte[] chiper = hex2byte(chiperStr, charSet);
		
		byte[] decrypt = decryptData(key, chiper);
		return new String(decrypt, charSet);
	}
	
	/**
	 * encrypt data by 3des
	 * @param key
	 * @param src
	 * @return
	 */
	public static byte[] encryptData(byte[] key, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/NoPadding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	/**
	 * decrypt data by 3des
	 * @param key
	 * @param src
	 * @return
	 */
	public static byte[] decryptData(byte[] key, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 反格式化byte[压缩字符串]s的长度必须是偶数
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] hex2byte(String s, String charset) throws UnsupportedEncodingException {
		byte[] src = s.toLowerCase().getBytes(charset);
		byte[] ret = new byte[src.length / 2];
		for (int i = 0; i < src.length; i += 2) {
			byte hi = src[i];
			byte low = src[i + 1];
			hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a') : hi - '0');
			low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a') : low - '0');
			ret[i / 2] = (byte) (hi << 4 | low);
		}
		return ret;
	}

	/**
	 * 格式化byte
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] out = new char[b.length * 2];
		for (int i = 0; i < b.length; i++) {
			byte c = b[i];
			out[i * 2] = Digit[(c >>> 4) & 0X0F];
			out[i * 2 + 1] = Digit[c & 0X0F];
		}

		return new String(out);
	}
}