package com.tangdi.production.mpaychl.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA加解密处理类
 * 
 * @author zlinepay
 * @version 1.0
 * @since 2014年7月29日
 * @copyright zlinepay.com
 */
public class RSAUtil {
	/**
	 * 密钥长度
	 */
	public static final int KEY_SIZE = 1024;

	private static Provider provider = null;

	private final static String algorithm = "RSA/ECB/PKCS1Padding";

	private final static String RSA = "RSA";
	
	private final static String encoding = "UTF-8";

	static {
		provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
	}

	/**
	 * 生成密钥对
	 * 
	 * @return
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = (KeyPairGenerator) KeyPairGenerator.getInstance(RSA, provider);
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.genKeyPair();
			return keyPair;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		KeyPair kp = RSAUtil.generateKeyPair();
		RSAPublicKey pubKey = (RSAPublicKey) kp.getPublic();
		String pubkey = Base64.encodeBase64String(pubKey.getEncoded());
		System.out.println("base64 公钥:" + pubkey);

		RSAPrivateKey priKey = (RSAPrivateKey) kp.getPrivate();
		String prikey = Base64.encodeBase64String(priKey.getEncoded());
		System.out.println("base64 私钥:" + prikey);
	}

	/**
	 * 用公钥pub对data做RSA加密
	 * 
	 * @param pub
	 *            - base64字符串
	 * @param data
	 *            - 普通字符串
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPub(String pub, String data) throws Exception {
		RSAPublicKey rsaKey = createRSAPublicKey(pub);
		byte[] b = encrypt(rsaKey, data.getBytes(encoding));
		return ThreeDESUtil.byte2hex(b);
	}

	/**
	 * 用私钥pri对data解密
	 * 
	 * @param pri
	 *            - base64字符串
	 * @param data
	 *            - hex字符串
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPri(String pri, String data) throws Exception {
		RSAPrivateKey rsaKey = createRSAPrivateKey(pri);
		byte[] b = decrypt(rsaKey, ThreeDESUtil.hex2byte(data, ""));
		return new String(b, encoding);
	}

	/**
	 * 用私钥pri对字符串data加密
	 * 
	 * @param pri
	 *            - base64字符串
	 * @param data
	 *            - 普通字符串
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPri(String pri, String data) throws Exception {
		RSAPrivateKey rsaKey = createRSAPrivateKey(pri);
		byte[] b = encrypt(rsaKey, data.getBytes(encoding));
		return ThreeDESUtil.byte2hex(b);
	}

	/**
	 * 用公钥pub对data解密
	 * 
	 * @param pub
	 *            - base64字符串
	 * @param data
	 *            - hex字符串
	 * @return
	 * @throws PreException
	 */
	public static String decryptByPub(String pub, String data) throws Exception {
		RSAPublicKey rsaKey = createRSAPublicKey(pub);
		byte[] b = decrypt(rsaKey, ThreeDESUtil.hex2byte(data, encoding));
		return new String(b, encoding);
	}

	/**
	 * 创建公钥对象
	 * 
	 * @param pubKey
	 *            - base64字符串
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey createRSAPublicKey(String pubKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(pubKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	/**
	 * 创建公钥对象
	 * 
	 * @param pubKey
	 *            - base64字符串
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey createRSAPrivateKey(String priKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(priKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(Key key, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(algorithm, provider);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			int outputSize = cipher.getOutputSize(data.length);
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 解密
	 * 
	 * @param key
	 * @param raw
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(algorithm, provider);
			cipher.init(Cipher.DECRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
